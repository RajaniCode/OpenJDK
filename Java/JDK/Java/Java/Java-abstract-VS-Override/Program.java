// abstract vs @Override
// In Java, all functions are virtual by default, hence NO 'new' modifier as in C#

import static java.lang.System.out;

abstract class BaseClass 
{ 
    public String instanceMethod() // hiding in C# // default virtual in Java
    {       
        return "instanceMethod() in abstract BaseClass";
    }

    public static String staticMethod() // hiding in C# // static hence NO @Override
    {
        return "staticMethod() in abstract BaseClass"; 
    }

    public abstract String abstractMethod(); 

    public String defaultMethod()     
    {
        return "defaultMethod() in abstract BaseClass"; 
    } 
}

class DerivedClass extends BaseClass
{
    @Override
    public String instanceMethod() // hiding in C# // static hence NO @Override
    {       
        return "instanceMethod() in DerivedClass";
    }
    
    public static String staticMethod() // hiding in C# // virtualby default in Java hence @Override
    {
        return "staticMethod() in DerivedClass"; 
    }

    @Override
    // final public String abstractMethod() // Can be final    
    public String abstractMethod()
    {
        return "abstractMethod() must be implemented (overridden) in all derived classes at 1st level derivation and can be 'final' in DerivedClass";
    }
    
    @Override
    // final public String defaultMethod()  // Can be final
    public String defaultMethod()  
    {        
        return "defaultMethod() implementation (overriding) NOT A MUST in DerivedClass, however can be 'final' in DerivedClass"; 
    }
}

class Program
{
    public static void main(String... args)
    {   
        out.println("# 1\n");
        BaseClass[] bcarray = new BaseClass[1];
        bcarray[0] = new DerivedClass();  
        out.println(bcarray[0].instanceMethod());  	// DerivedClass // like bcr // Differes from C# regardless of 'new' modifier as methods are virtual by default in Java // if instanceMethod() not implemented in DerivedClass, BaseClass
        out.println(BaseClass.staticMethod());     	// BaseClass     
        out.println(DerivedClass.staticMethod());  	// DerivedClass // if staticMethod() not implemented, BaseClass
        out.println(bcarray[0].abstractMethod());  	// DerivedClass // like bcr // abstractMethod() MUST be implemented
        out.println(bcarray[0].defaultMethod());   	// DerivedClass // like bcr // if defaultMethod() not implemented, BaseClass  
        out.println();                
                                                                                                                                    
        BaseClass bcr;
	DerivedClass dc = new DerivedClass();
	bcr = dc;                                                                     
                                                                                                 
        out.println("# 2\n");                                                                                                                     
        out.println(dc.instanceMethod());               // DerivedClass // if instanceMethod() not implemented in DerivedClass, BaseClass 
	out.println(bcr.instanceMethod());            	// DerivedClass // Differes from C# regardless of 'new' modifier as methods are virtual by default in Java // if instanceMethod() not implemented in DerivedClass, BaseClass
        out.println(((BaseClass)dc).instanceMethod());  // DerivedClass // Differes from C# regardless of 'new' modifier as methods are virtual by default in Java // if instanceMethod() not implemented in DerivedClass, BaseClass
        out.println();                                                                                                                                                        
                                                                                                      
        out.println("# 3\n");                                                                                                                                                                                  
        out.println(BaseClass.staticMethod());          // BaseClass 
        out.println();                                                                                                                                                                 
                                                                                                   
        out.println("# 4\n");                                                                                                
        out.println(DerivedClass.staticMethod());       // DerivedClass // if defaultMethod() not implemented in DerivedClass, BaseClass
        out.println();                                                                  
                                                                                                                                                                                                                                                
        out.println("# 5\n");                                                                                      
        out.println(dc.abstractMethod());               // DerivedClass // abstractMethod() MUST be implemented in DerivedClass
        out.println(bcr.abstractMethod());              // DerivedClass // abstractMethod() MUST be implemented in DerivedClass
        out.println(((BaseClass)dc).abstractMethod());  // DerivedClass // abstractMethod() MUST be implemented in DerivedClass
        out.println();                                                            
                                                                                                   
        out.println("# 6\n");
        out.println(dc.defaultMethod());                // DerivedClass // if defaultMethod() not implemented in DerivedClass, BaseClass
        out.println(bcr.defaultMethod());               // DerivedClass // if defaultMethod() not implemented in DerivedClass, BaseClass
        out.println(((BaseClass)dc).defaultMethod());   // DerivedClass // if defaultMethod() not implemented in DerivedClass, BaseClass
        out.println();              
     }
}