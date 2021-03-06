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
The I{resolver} module provides a collection of classes that
provide wsdl/xsd named type resolution.
"""

import re
from logging import getLogger
from suds import *
from suds.sax import splitPrefix, Namespace
from suds.sudsobject import Object
from suds.xsd.query import BlindQuery, qualify

log = getLogger(__name__)


class Resolver:
    """
    An I{abstract} schema-type resolver.
    @ivar schema: A schema object.
    @type schema: L{xsd.schema.Schema}
    """

    def __init__(self, schema):
        """
        @param schema: A schema object.
        @type schema: L{xsd.schema.Schema}
        """
        self.schema = schema


class PathResolver(Resolver):
    """
    Resolveds the definition object for the schema type located at the specified path.
    The path may contain (.) dot notation to specify nested types.
    @ivar wsdl: A wsdl object.
    @type wsdl: L{wsdl.Definitions}
    """
    
    altp = re.compile('({)(.+)(})(.+)')
    splitp = re.compile('({.+})*[^.]+')
    
    def __init__(self, wsdl):
        """
        @param wsdl: A schema object.
        @type wsdl: L{wsdl.Definitions}
        """
        Resolver.__init__(self, wsdl.schema)
        self.wsdl = wsdl

    def find(self, path, resolved=True):
        """
        Get the definition object for the schema type located at the specified path.
        The path may contain (.) dot notation to specify nested types.
        @param path: A (.) separated path to a schema type.
        @type path: basestring
        @param resolved: A flag indicating that the fully resolved type
            should be returned.
        @type resolved: boolean
        @return: The found schema I{type}
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        result = None
        parts = self.split(path)
        try:
            result = self.root(parts)
            if len(parts) > 1:
                result = result.resolve(nobuiltin=True)
            if len(parts) > 2:
                result = self.branch(result, parts)
                result = self.leaf(result, parts)
            if resolved:
                result = result.resolve(nobuiltin=True)
        except PathResolver.BadPath:
            log.error('path: "%s", not-found' % path)
        return result
    
    def root(self, parts):
        """
        Find the path root.
        @param parts: A list of path parts.
        @type parts: [str,..]
        @return: The root.
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        result = None
        name = parts[0]
        log.debug('searching schema for (%s)', name)
        qref = self.qualify(parts[0])
        query = BlindQuery(qref)
        result = query.execute(self.schema)
        if result is None:
            log.error('(%s) not-found', name)
            raise PathResolver.BadPath(name)
        else:
            log.debug('found (%s) as (%s)', name, Repr(result))
        return result
    
    def branch(self, root, parts):
        """
        Traverse the path until the leaf is reached.
        @param parts: A list of path parts.
        @type parts: [str,..]
        @param root: The root.
        @type root: L{xsd.sxbase.SchemaObject}
        @return: The end of the branch.
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        result = root
        for part in parts[1:-1]:
            name = splitPrefix(part)[1]
            log.debug('searching parent (%s) for (%s)', Repr(result), name)
            result = result.get_child(name)
            if result is None:
                log.error('(%s) not-found', name)
                raise PathResolver.BadPath(name)
            else:
                result = result.resolve(nobuiltin=True)
                log.debug('found (%s) as (%s)', name, Repr(result))
        return result
    
    def leaf(self, parent, parts):
        """
        Find the leaf.
        @param parts: A list of path parts.
        @type parts: [str,..]
        @param parent: The leaf's parent.
        @type parent: L{xsd.sxbase.SchemaObject}
        @return: The leaf.
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        name = splitPrefix(parts[-1])[1]
        if name.startswith('@'):
            result = parent.get_attribute(name[1:])
        else:
            result = parent.get_child(name)
        if result is None:
            raise PathResolver.BadPath(name)
        return result
    
    def qualify(self, name):
        """
        Qualify the name as either:
          - plain name
          - ns prefixed name (eg: ns0:Person)
          - fully ns qualified name (eg: {http://myns-uri}Person)
        @param name: The name of an object in the schema.
        @type name: str
        @return: A qualifed name.
        @rtype: qname
        """
        m = self.altp.match(name)
        if m is None:
            return qualify(name, self.wsdl.root, self.wsdl.tns)
        else:
            return (m.group(4), m.group(2))
        
    def split(self, s):
        """
        Split the string on (.) while preserving any (.) inside the
        '{}' alternalte syntax for full ns qualification.
        @param s: A plain or qualifed name.
        @type s: str
        @return: A list of the name's parts.
        @rtype: [str,...]
        """
        parts = []
        b = 0
        while 1:
            m = self.splitp.match(s, b)
            if m is None:
                break
            b,e = m.span()
            parts.append(s[b:e])
            b = e+1
        return parts
    
    class BadPath(Exception): pass
        


class TreeResolver(Resolver):
    """
    The tree resolver is a I{stateful} tree resolver
    used to resolve each node in a tree.  As such, it mirrors
    the tree structure to ensure that nodes are resolved in
    context.
    @ivar stack: The context stack.
    @type stack: list
    """
    
    def __init__(self, schema):
        """
        @param schema: A schema object.
        @type schema: L{xsd.schema.Schema}
        """
        Resolver.__init__(self, schema)
        self.stack = Stack()
        
    def reset(self, primer=()):
        """
        Reset the resolver's state.
        @param primer: Items used to initialize the stack.
        @type primer: [L{xsd.sxbase.SchemaObject},...]
        """
        self.stack = Stack()
        for item in primer:
            self.push(item)
            
    def findattr(self, name, resolved=True):
        """
        Find an attribute type definition.
        @param name: An attribute name.
        @type name: basestring
        @param resolved: A flag indicating that the fully resolved type should be
            returned.
        @type resolved: boolean
        @return: The found schema I{type}
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        attr = '@%s'%name
        parent = self.top(1)
        result = self.__find(attr, parent)
        if result is None:
            return result
        if resolved:
            result = result.resolve()
        return result
            
    def push(self, item):
        """
        Push a type I{item} onto the stack where I{item} is a tuple
        as (I{type},I{resolved}).
        @param item: An item to push.
        @type item: L{xsd.sxbase.SchemaObject}
        @return: The pushed item.
        @rtype: (I{type},I{resolved})
        """
        if isinstance(item, tuple):
            item = (item[0], item[1].resolve())
        else:
            item = (item, item.resolve())
        self.stack.append(item)
        log.debug('push: (%s)\n%s', Repr(item), Repr(self.stack))
        return item
    
    def top(self, index=1):
        """
        Get the I{item} at the top of the stack where I{item} is a tuple
        as (I{type},I{resolved}).
        @param index: The index (0|1) into the tuple.  {0=type, 1=resolved}.
        @type index: int
        @return: The top I{item}, else None.
        @rtype: I{type}
        """
        if len(self.stack):
            return self.stack[-1][index]
        else:
            return None
        
    def pop(self):
        """
        Pop the I{item} at the top of the stack where I{item} is a tuple
        as (I{type},I{resolved}).
        @return: The popped I{item}, else None.
        @rtype: (I{type},I{resolved})
        """
        if len(self.stack):      
            popped = self.stack.pop()
            log.debug('pop: (%s)\n%s', Repr(popped), Repr(self.stack))
            return popped
        else:
            log.debug('stack empty, not-popped')
        return None

    def __find(self, name, parent):
        """ find the type for name and optional parent """
        if parent is None:
            log.debug('searching schema for (%s)', name)
            schema = self.schema
            wsdl = self.__wsdl()
            if wsdl is None:
                qref = qualify(name, schema.root, schema.tns)
            else:
                qref = qualify(name, wsdl.root, wsdl.tns)
            query = BlindQuery(qref)
            result = query.execute(schema)
        else:
            log.debug('searching parent (%s) for (%s)', Repr(parent), name)
            if name.startswith('@'):
                result = parent.get_attribute(name[1:])
            else:
                result = parent.get_child(name)
        if result is None:
            log.error('(%s) not-found', name)
        else:
            log.debug('(%s) found as (%s)', name, Repr(result))
        return result
    
    def __wsdl(self):
        container = self.schema.container
        if container is None:
            return None
        else:
            return container.wsdl



class NodeResolver(TreeResolver):
    """
    The node resolver is a I{stateful} XML document resolver
    used to resolve each node in a tree.  As such, it mirrors
    the tree structure to ensure that nodes are resolved in
    context.
    """
    
    def __init__(self, schema):
        """
        @param schema: A schema object.
        @type schema: L{xsd.schema.Schema}
        """
        TreeResolver.__init__(self, schema)
        
    def find(self, node, resolved=False, push=True):
        """
        @param node: An xml node to be resolved.
        @type node: L{sax.element.Element}
        @param resolved: A flag indicating that the fully resolved type should be
            returned.
        @type resolved: boolean
        @param push: Indicates that the resolved type should be
            pushed onto the stack.
        @type push: boolean
        @return: The found schema I{type}
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        name = node.name
        parent = self.top(1)
        result = self._TreeResolver__find(name, parent)
        if result is None and parent is None:
            name = node.get('type', Namespace.xsins)
            if name is not None:
                result = self._TreeResolver__find(name, None)
        if result is None:
            return result
        if push:
            pushed = self.push(result)
        if resolved:
            result = pushed[1]
        return result

class GraphResolver(TreeResolver):
    """
    The graph resolver is a I{stateful} L{Object} graph resolver
    used to resolve each node in a tree.  As such, it mirrors
    the tree structure to ensure that nodes are resolved in
    context.
    """
    
    def __init__(self, schema):
        """
        @param schema: A schema object.
        @type schema: L{xsd.schema.Schema}
        """
        TreeResolver.__init__(self, schema)
        
    def find(self, name, object, resolved=False, push=True):
        """
        @param name: The name of the object to be resolved.
        @type name: basestring
        @param object: The name's value.
        @type object: (any|L{Object}) 
        @param resolved: A flag indicating that the fully resolved type
            should be returned.
        @type resolved: boolean
        @param push: Indicates that the resolved type should be
            pushed onto the stack.
        @type push: boolean
        @return: The found schema I{type}
        @rtype: L{xsd.sxbase.SchemaObject}
        """
        known = None
        parent = self.top(1)
        result = self._TreeResolver__find(name, parent)
        if result is None:
            return None
        if isinstance(object, Object):
            known = self.known(object)
        if push:
            if known is None:
                pushed = self.push(result)
            else:
                pushed = self.push((result, known))
        if resolved:
            result = pushed[1]
        return result
    
    def known(self, object):
        try:
            md = object.__metadata__
            known = md.__type__
            return known
        except:
            pass


class Stack(list):
    def __repr__(self):
        result = []
        for item in self:
            result.append(repr(item))
        return '\n'.join(result)