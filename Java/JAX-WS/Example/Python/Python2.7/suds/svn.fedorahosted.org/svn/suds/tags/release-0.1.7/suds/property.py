# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Library General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
# written by: Jeff Ortel ( jortel@redhat.com )

from suds import tostr

class Property:
    """
    provides an object wrapper around a complex dictionary.
    """
    __self__ = '__self__'    
    __protected__ = \
        ('__data__', '__strict__', '__lazy__', '__type__',
         '__metadata__', '__keylist__', '__printer__')

    def __init__(self, data=None, strict=False, lazy=False):
        """
        data -- a dictionary containing properties.
        strict -- flag indicates whether __getattr__() will throw an exception
        when the name is not found
        """
        if data is None:
            data = {}
        if isinstance(data, Property):
            data = data.dict()
        self.__data__ = data
        self.__strict__ = strict
        self.__lazy__ = lazy
        self.__type__ = None
        self.__keylist__ = []
        self.__printer__ = Printer()
        self.__metadata__ = {}
            
    def get_names(self):
        """get a list of property names"""
        return self.__keylist__
        
    def get_values(self):
        """get a list of property values"""
        return self.__data__.values()

    def get_items(self):
        """ get the property's collection of items."""
        for k in self.__keylist__:
            v = self.__data__[k]
            yield (k,v)
    
    def get(self, **kwargs):
        """get a property(s) value by name while specifying a default: property=default, """
        result = []
        for k in kwargs.keys():
            default = kwargs[k]
            value = self.__data__.get(k, default)
            result.append(self.translate(value))
        if len(result) == 1:
            return result[0]
        else:
            return tuple(result)
        
    def set(self, name, value):
        """set the value of the specified named"""
        self.__setattr__(name, value)
            
    def get_metadata(self, name=__self__):
        """ get the metadata associated with the named property"""
        if name in self.__metadata__:
            md = self.__metadata__[name]
        else:
            md = Property()
            self.__metadata__[name] = md
        return md       

    def dict(self):
        """get the underlying dictionary"""
        return self.__data__
    
    def prune(self):
        """prune the underlying dictionary of entries with value = none"""
        pruned = []
        for k in self.__data__.keys():
            v = self.__data__[k]
            if v is None:
                del self.__data__[k]
                pruned.append(k)
        return pruned
            
    def translate(self, v):
        """
        translate the specified value to ensure that dictionaries and collections
        of dictionaries are returned as a Propety or collection of properties
        """
        if isinstance(v, dict):
            return Property(v)
        if isinstance(v, tuple):
             return self.translate_tuple(v)
        if isinstance(v, list):
            return self.translate_list(v)
        return v
    
    def translate_list(self, collection):
        """
        translate the specified collection of dictionaries
        into a collection of Propety objects.
        """
        i = 0
        for item in collection:
            if isinstance(item, dict):
                collection[i] = Property(item)
            i += 1
        return collection
    
    def translate_tuple(self, collection):
        """
        translate the specified collection of dictionaries
        into a collection of Propety objects.
        """
        list = []
        for item in collection:
            if isinstance(item, dict):
                list.append(Property(item))
            else:
                list.append(item)
        return tuple(list)

    def __getattr__(self, name):
        """get the specified attribute (property).  raise exception based on strict flag"""
        result = None
        if name in Property.__protected__:
            return self.__dict__[name] 
        try:            
            result = self.translate(self.__data__[name])
        except KeyError:
            if self.__strict__:
                raise AttributeError, name
            if self.__lazy__:
                result = Property()
                self.set(name, result)
        return result
    
    def __setattr__(self, name, value):
        """set the value of the specified attribute (property)"""
        if name in Property.__protected__:
            self.__dict__[name] = value
            return
        if name not in self.__keylist__:
            self.__keylist__.append(name)
        if isinstance(value, Property):
            self.__data__[name] = value
            return
        if isinstance(value, list):
            self.__data__[name] = value[:]
            return
        self.__data__[name] = value

    def __getitem__(self, name):
        """ dictionary accessor """
        return self.translate(self.__data__[name])
        
    def __setitem__(self, name, value):
        """ dictionary accessor """
        self.__setattr__(name, value)
    
    def __str__(self):
        """ get a string representation """
        return unicode(self).encode('utf-8')
        
    def __unicode__(self):
        """ get a string representation """
        if self.__type__ is None:
            return self.__printer__.tostr(self)
        else:
            return u'(%s)%s' % \
                (self.__type__, self.__printer__.tostr(self))
    
    def __repr__(self):
        """ get a string representation """
        return self.__str__()
    
    def __neq__(self, other):
        """ not equals operator """
        return not self.__eq__(other)
    
    def __eq__(self, other):
        """ equals operator """
        if isinstance(other, Property):
            return self.__data__ == other.__data__
        else:
            return False
        
    def __len__(self):
        """ len() operator """
        return len(self.__data__)
    
    def __contains__(self, name):
        return name in self.__data__


#
# pretty printing utility
#

class Printer:
    
    """ Pretty printing of a Property object. """
    
    def tostr(self, object, indent=-2):
        """ get the pretty printed string representation of a Property object """
        return self.process(object, indent)
    
    def process(self, object, n=0, nl=False):
        """ print the specified object using the specified indent (n) and newline (nl). """
        if object is None:
            return 'None'
        if self.complex(object):
            if isinstance(object, Property):
                return self.print_property(object, n+2, nl)
            if isinstance(object, dict):
                return self.print_dict(object, n+2, nl)
            if isinstance(object, (list,tuple)):
                return self.print_collection(object, n+2)
        if isinstance(object, Property):
            object = object.dict()
        if isinstance(object, (dict,list,tuple)):
            if len(object) > 0:
                return tostr(object)
            else:
                return '<empty>'
        return '(%s)' % tostr(object)

    def print_property(self, p, n, nl=False):
        """ print the specified property using the specified indent (n) and newline (nl). """
        s = []
        if nl:
            s.append('\n')
            s.append(self.indent(n))
        s.append('{')
        for item in p.get_items():
            s.append('\n')
            s.append(self.indent(n+1))
            if isinstance(item[1], (list,tuple)):            
                s.append(item[0])
                s.append('[]')
            else:
                s.append(item[0])
            s.append(' = ')
            s.append(self.process(item[1], n, True))
        s.append('\n')
        s.append(self.indent(n))
        s.append('}')
        return ''.join(s)
    
    def print_dict(self, d, n, nl=False):
        """ print the specified dictionary using the specified indent (n) and newline (nl). """
        s = []
        if nl:
            s.append('\n')
            s.append(self.indent(n))
        s.append('{')
        for item in d.items():
            s.append('\n')
            s.append(self.indent(n+1))
            if isinstance(item[1], (list,tuple)):            
                s.append(item[0])
                s.append('[]')
            else:
                s.append(item[0])
            s.append(' = ')
            s.append(self.process(item[1], n, True))
        s.append('\n')
        s.append(self.indent(n))
        s.append('}')
        return ''.join(s)

    def print_collection(self, c, n):
        """ print the specified list|tuple using the specified indent (n) and newline (nl). """
        s = []
        for item in c:
            s.append('\n')
            s.append(self.indent(n))
            s.append(self.process(item, n-2))
            s.append(',')
        return ''.join(s)
    
    def complex(self, object):
        """ get whether the object is a complex type """
        if isinstance(object, (Property, dict)):
            if len(object) > 1:
                return True
            for item in object.get_items():
                if self.complex(item[1]):
                    return True
        if isinstance(object, (list,tuple)):
            if len(object) > 1: return True
            for c in object:
                if self.complex(c):
                    return True
            return False
        return False
                
    def indent(self, n):
        """ generate (n) spaces for indent. """
        return '%*s'% (n*3, ' ')

        