# This program is free software; you can redistribute it and/or modify
# it under the terms of the (LGPL) GNU Lesser General Public License as
# published by the Free Software Foundation; either version 3 of the 
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Library Lesser General Public License for more details at
# ( http://www.gnu.org/licenses/lgpl.html ).
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
# written by: Jeff Ortel ( jortel@redhat.com )

"""
The I{wsdl} module provides an objectification of the WSDL.
The primary class is I{Definitions} as it represends the root element
found in the document.
"""

from logging import getLogger
from suds import *
from suds.sax import splitPrefix
from suds.sax.parser import Parser
from suds.sax.element import Element
from suds.bindings.document import Document
from suds.bindings.rpc import RPC
from suds.xsd import qualify
from suds.xsd.schema import SchemaCollection
from suds.sudsobject import Object
from suds.sudsobject import Factory as SFactory
from urlparse import urljoin

log = getLogger(__name__)

wsdlns = (None, "http://schemas.xmlsoap.org/wsdl/")
soapns = (None, 'http://schemas.xmlsoap.org/wsdl/soap/')
soap12ns = (None, 'http://schemas.xmlsoap.org/wsdl/soap12/')


class Factory:
    """
    Simple WSDL object factory.
    @cvar tags: Dictionary of tag->constructor mappings.
    @type tags: dict
    """

    tags =\
    {
        'import' : lambda x,y: Import(x,y), 
        'schema' : lambda x,y: Schema(x,y), 
        'message' : lambda x,y: Message(x,y), 
        'portType' : lambda x,y: PortType(x,y),
        'binding' : lambda x,y: Binding(x,y),
        'service' : lambda x,y: Service(x,y),
    }
    
    @classmethod
    def create(cls, root, definitions):
        """
        Create an object based on the root tag name.
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        @return: The created object.
        @rtype: L{WObject} 
        """
        fn = cls.tags.get(root.name)
        if fn is not None:
            return fn(root, definitions)
        else:
            return None


class WObject(Object):
    """
    Base object for wsdl types.
    @ivar root: The XML I{root} element.
    @type root: L{Element}
    """
    
    def __init__(self, root, definitions=None):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        Object.__init__(self)
        self.root = root
        pmd = SFactory.metadata()
        pmd.excludes = ['root']
        pmd.wrappers = dict(qname=lambda x: repr(x))
        self.__metadata__.__print__ = pmd
        
    def resolve(self, definitions):
        """
        Resolve named references to other WSDL objects.
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        pass

        
class NamedObject(WObject):
    """
    A B{named} WSDL object.
    @ivar name: The name of the object.
    @type name: str
    @ivar qname: The I{qualified} name of the object.
    @type qname: (name, I{namespace-uri}).
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        WObject.__init__(self, root, definitions)
        self.name = root.get('name')
        self.qname = (self.name, definitions.tns[1])
        pmd = self.__metadata__.__print__
        pmd.wrappers['qname'] = lambda x: repr(x)


class Definitions(WObject):
    """
    Represents the I{root} container of the WSDL objects as defined
    by <wsdl:definitions/>
    @ivar id: The object id.
    @type id: str
    @ivar url: The URL used to load the object.
    @type url: str
    @ivar tns: The target namespace for the WSDL.
    @type tns: str
    @ivar schema: The collective WSDL schema object.
    @type schema: L{SchemaCollection}
    @ivar children: The raw list of child objects.
    @type children: [L{WObject},...]
    @ivar imports: The list of L{Import} children.
    @type imports: [L{Import},...]
    @ivar messages: The dictionary of L{Message} children key'd by I{qname}
    @type messages: [L{Message},...]
    @ivar port_types: The dictionary of L{PortType} children key'd by I{qname}
    @type port_types: [L{PortType},...]
    @ivar bindings: The dictionary of L{Binding} children key'd by I{qname}
    @type bindings: [L{Binding},...]
    @ivar service: The service object.
    @type service: L{Service}
    """

    def __init__(self, url, opener=None):
        """
        @param url: A URL to the WSDL.
        @type url: str
        @param opener: A urllib2 opener (may be None).
        @type opener: urllib2.Opener
        """
        log.debug('reading wsdl at: %s ...', url)
        p = Parser(opener)
        root = p.parse(url=url).root()
        WObject.__init__(self, root)
        self.id = objid(self)
        self.url = url
        self.tns = self.mktns(root)
        self.schema = None
        self.children = []
        self.imports = []
        self.schemas = []
        self.messages = {}
        self.port_types = {}
        self.bindings = {}
        self.service = None
        self.add_children(self.root)
        self.children.sort()
        pmd = self.__metadata__.__print__
        pmd.excludes.append('children')
        pmd.excludes.append('wsdl')
        pmd.wrappers['schema'] = lambda x: repr(x)
        self.open_imports(opener)
        self.resolve()
        self.build_schema()
        if self.service is not None:
            self.add_methods()
        log.debug("wsdl at '%s' loaded:\n%s", url, self)
        
    def mktns(self, root):
        """ Get/create the target namespace """
        tns = root.get('targetNamespace')
        prefix = root.findPrefix(tns)
        if prefix is None:
            log.debug('warning: tns (%s), not mapped to prefix', tns)
            prefix = 'tns'
        return (prefix, tns)
        
    def add_children(self, root):
        """ Add child objects using the factory """
        paths = \
            ('import', 'types/schema', 'message', 'portType', 'binding', 'service')
        for path in paths:
            for c in root.childrenAtPath(path):
                child = Factory.create(c, self)
                if child is None: continue
                self.children.append(child)
                if isinstance(child, Import):
                    self.imports.append(child)
                    continue
                if isinstance(child, Schema):
                    self.schemas.append(child)
                    continue
                if isinstance(child, Message):
                    self.messages[child.qname] = child
                    continue
                if isinstance(child, PortType):
                    self.port_types[child.qname] = child
                    continue
                if isinstance(child, Binding):
                    self.bindings[child.qname] = child
                    continue
                if isinstance(child, Service):
                    self.service = child
                    continue
                
    def open_imports(self, opener):
        """ Import the I{imported} WSDLs. """
        for imp in self.imports:
            base = self.url
            imp.load(self, opener)
                
    def resolve(self):
        """ Tell all children to resolve themselves """
        for c in self.children:
            c.resolve(self)
                
    def build_schema(self):
        """ Process schema objects and create the schema collection """
        container = SchemaCollection(self)
        for s in self.schemas:
            entry = (s.root, s.definitions)
            container.add(entry)
        if not len(container): # empty
            root = Element.buildPath(self.root, 'types/schema')
            entry = (root, self)
            container.add(entry)
        self.schema = container.load()
                
    def add_methods(self):
        bindings = {
            'document/literal' : Document(self),
            'rpc/literal' : RPC(self),
            'rpc/encoded' : RPC(self).use_encoded()
        }
        for p in self.service.ports:
            binding = p.binding
            ptype = p.binding.type
            operations = p.binding.type.operations.values()
            for name in [op.name for op in operations]:
                m = SFactory.object('Method')
                m.name = name
                m.location = p.location
                m.binding = SFactory.object('binding')
                op = binding.operation(name)
                m.soap = op.soap
                key = '/'.join((op.soap.style, op.soap.input.body.use))
                m.binding.input = bindings.get(key)
                key = '/'.join((op.soap.style, op.soap.output.body.use))
                m.binding.output = bindings.get(key)
                op = ptype.operation(name)
                m.message = SFactory.object('message')
                m.message.input = op.input
                m.message.output = op.output
                m.qname = ':'.join((p.name, name))
                self.service.methods[m.name] = m
                self.service.methods[m.qname] = m


class Import(WObject):
    """
    Represents the <wsdl:import/>.
    @ivar location: The value of the I{location} attribute.
    @type location: str
    @ivar ns: The value of the I{namespace} attribute.
    @type ns: str
    @ivar imported: The imported object.
    @type imported: L{Definitions}
    """
    
    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        WObject.__init__(self, root, definitions)
        self.location = root.get('location')
        self.ns = root.get('namespace')
        self.imported = None
        pmd = self.__metadata__.__print__
        pmd.wrappers['imported'] = ( lambda x: x.id )
        
    def load(self, definitions, opener):
        """ Load the object by opening the URL """
        url = self.location
        log.debug('importing (%s)', url)
        if '://' not in url:
            url = urljoin(definitions.url, url)
        d = Definitions(url, opener)
        definitions.schemas += d.schemas
        definitions.messages.update(d.messages)
        definitions.port_types.update(d.port_types)
        definitions.bindings.update(d.bindings)
        self.imported = d
        
    def __gt__(self, other):
        return False
        

class Schema(WObject):
    """
    Represents <types><schema/></types>.
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        WObject.__init__(self, root, definitions)
        self.definitions = definitions
        
    def __gt__(self, other):
        return isinstance(other, Import)
    

class Part(NamedObject):
    """
    Represents <message><part/></message>.
    @ivar element: The value of the {element} attribute.
        Stored as a I{qref} as converted by L{suds.xsd.qualify}.
    @type element: str
    @ivar type: The value of the {type} attribute.
        Stored as a I{qref} as converted by L{suds.xsd.qualify}.
    @type type: str
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        NamedObject.__init__(self, root, definitions)
        pmd = SFactory.metadata()
        pmd.wrappers = \
            dict(element=lambda x: repr(x), type=lambda x: repr(x))
        self.__metadata__.__print__ = pmd
        tns = definitions.tns
        self.element = self.__getref('element', tns)
        self.type = self.__getref('type', tns)
        
    def xsref(self):
        """
        Get the value of whichever is defined ( I{element} | I{type} ).
        @return: The value of whichever is defined ( I{element} | I{type} ).
        @rtype: (name, I{namespace}).
        """
        if self.element is None:
            return self.type
        else:
            return self.element
        
    def __getref(self, a, tns):
        """ Get the qualified value of attribute named 'a'."""
        s = self.root.get(a)
        if s is None:
            return s
        else:
            return qualify(s, self.root, tns)  


class Message(NamedObject):
    """
    Represents <message/>.
    @ivar parts: A list of message parts.
    @type parts: [I{Part},...]
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        NamedObject.__init__(self, root, definitions)
        self.parts = []
        for p in root.getChildren('part'):
            part = Part(p, definitions)
            self.parts.append(part)
            
    def __gt__(self, other):
        return isinstance(other, (Import, Schema))


class PortType(NamedObject):
    """
    Represents <portType/>.
    @ivar operations: A list of contained operations.
    @type operations: list
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        NamedObject.__init__(self, root, definitions)
        self.operations = {}
        for c in root.getChildren('operation'):
            op = SFactory.object('Operation')
            op.name = c.get('name')
            op.tns = definitions.tns
            input = c.getChild('input')
            op.input = input.get('message')
            output = c.getChild('output', default=input)
            if output is None:
                op.output = None
            else:
                op.output = output.get('message')
            self.operations[op.name] = op
            
    def resolve(self, definitions):
        """
        Resolve named references to other WSDL objects.
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        for op in self.operations.values():
            qref = qualify(op.input, self.root, wsdlns)
            msg = definitions.messages.get(qref)
            if msg is None:
                raise Exception("msg '%s', not-found" % op.input)
            else:
                op.input = msg
            qref = qualify(op.output, self.root, wsdlns)
            msg = definitions.messages.get(qref)
            if msg is None:
                raise Exception("msg '%s', not-found" % op.output)
            else:
                op.output = msg
                
    def operation(self, name):
        """
        Shortcut used to get a contained operation by name.
        @param name: An operation name.
        @type name: str
        @return: The named operation.
        @rtype: Operation
        @raise L{MethodNotFound}: When not found.
        """
        try:
            return self.operations[name]
        except Exception, e:
            raise MethodNotFound(name)
                
    def __gt__(self, other):
        return isinstance(other, (Import, Schema, Message))


class Binding(NamedObject):
    """
    Represents <binding/>
    @ivar operations: A list of contained operations.
    @type operations: list
    """

    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        NamedObject.__init__(self, root, definitions)
        self.operations = {}
        self.type = root.get('type')
        sr = self.soaproot()
        if sr is None:
            self.soap = None
            log.debug('binding: "%s" not a soap binding', self.name)
            return
        soap = SFactory.object('soap')
        self.soap = soap
        self.soap.style = sr.get('style', default='document')
        self.add_operations(self.root, definitions)
        
    def soaproot(self):
        """ get the soap:binding """
        for ns in (soapns, soap12ns):
            sr =  self.root.getChild('binding', ns=ns)
            if sr is not None:
                return sr
        return None
        
    def add_operations(self, root, definitions):
        """ Add <operation/> children """
        for c in root.getChildren('operation'):
            op = SFactory.object('Operation')
            op.name = c.get('name')
            sr = c.getChild('operation')
            soap = SFactory.object('soap')
            soap.action = '"%s"' % sr.get('soapAction', default='')
            soap.style = sr.get('style', default=self.soap.style)
            soap.input = SFactory.object('Input')
            soap.input.body = SFactory.object('Body')
            soap.output = SFactory.object('Output')
            soap.output.body = SFactory.object('Body')
            op.soap = soap
            input = c.getChild('input')
            body = input.getChild('body')
            self.body(definitions, soap.input.body, body)
            output = c.getChild('output')
            body = output.getChild('body')
            self.body(definitions, soap.output.body, output)
            self.operations[op.name] = op
            
    def body(self, definitions, body, root):
        """ add the input/output body properties """
        if root is None:
            body.use = 'literal'
            body.namespace = definitions.tns
            return
        body.use = root.get('use', default='literal')
        ns = root.get('namespace')
        if ns is None:
            body.namespace = definitions.tns
        else:
            prefix = root.findPrefix(ns)
            body.namespace = (prefix, ns)
            
    def resolve(self, definitions):
        """
        Resolve named references to other WSDL objects.
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        ref = qualify(self.type, self.root, wsdlns)
        port_type = definitions.port_types.get(ref)
        if port_type is None:
            raise Exception("portType '%s', not-found" % self.type)
        else:
            self.type = port_type
            
    def operation(self, name):
        """
        Shortcut used to get a contained operation by name.
        @param name: An operation name.
        @type name: str
        @return: The named operation.
        @rtype: Operation
        @raise L{MethodNotFound}: When not found.
        """
        try:
            return self.operations[name]
        except:
            raise MethodNotFound(name)
            
    def __gt__(self, other):
        return ( not isinstance(other, Service) )


class Service(NamedObject):
    """
    Represents <service/>.
    @ivar port: The contained ports.
    @type port: [Port,..]
    @ivar methods: The contained methods for all ports.
    @type methods: [Method,..]
    """
    
    def __init__(self, root, definitions):
        """
        @param root: An XML root element.
        @type root: L{Element}
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        NamedObject.__init__(self, root, definitions)
        self.ports = []
        self.methods = {}
        for p in root.getChildren('port'):
            port = SFactory.object('Port')
            port.name = p.get('name')
            port.binding = p.get('binding')
            address = p.getChild('address')
            port.location = address.get('location').encode('utf-8')
            self.ports.append(port)
            
    def port(self, name):
        """
        Locate a port by name.
        @param name: A port name.
        @type name: str
        @return: The port object.
        @rtype: I{Port} 
        """
        for p in self.ports:
            if p.name == name:
                return p
        return None
    
    def method(self, name):
        """
        Get a method defined an one of the portTypes by name.
        @param name: A method name.
        @type name: str
        @return: The requested method object.
        @rtype: I{Method}
        """
        return self.methods.get(name)
        
    def resolve(self, definitions):
        """
        Resolve named references to other WSDL objects.
        Ports without soap bindings are discarded.
        @param definitions: A definitions object.
        @type definitions: L{Definitions}
        """
        filtered = []
        for p in self.ports:
            ref = qualify(p.binding, self.root, wsdlns)
            binding = definitions.bindings.get(ref)
            if binding is None:
                raise Exception("binding '%s', not-found" % p.binding)
            if binding.soap is None:
                log.debug('binding "%s" - not a soap, discarded', binding.name)
                continue
            p.binding = binding
            filtered.append(p)
        self.ports = filtered
        
    def __gt__(self, other):
        return True
