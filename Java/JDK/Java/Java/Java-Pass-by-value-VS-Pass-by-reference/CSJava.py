class Program(object):
    def __init__(self):
        print("Init")
    def method(self, i):
        i = 5;

def function(i):
    i = 500;
    
i = 1
print(i)
p = Program()
p.method(i)
print(i)
function(i)
print(i)
