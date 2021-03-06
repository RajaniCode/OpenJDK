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

from suds import *
from suds.sax import Parser

class Binding:
    """ The soap binding base class """

    def __init__(self, wsdl, faults):
        self.wsdl = wsdl
        self.faults = faults
        self.log = logger('binding')
        self.parser = Parser()
        self.nil_supported = True
        
    def get_method_descriptions(self):
        """get a list of methods provided by this service"""
        list = []
        ops = self.wsdl.get_operations()
        for op in self.wsdl.get_operations():
            ptypes = self.get_ptypes(op.attribute('name'))
            params = ['%s{%s}' % (t[0], t[1]) for t in ptypes]
            m = '%s(%s)' % (op.attribute('name'), ', '.join(params))
            list.append(m)
        return list




