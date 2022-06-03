import static java.lang.System.out;

class BaseClass 
{ 
    public String instanceMethod() // hiding intended in C# // virtual by default in Java hence @Override
    {       
        return "instanceMethod() in BaseClass";
    }
}

class DerivedClass extends BaseClass 
{
    @Override
    public String instanceMethod() // hiding intended in C# // virtual by default in Java hence @Override
    {       
        return "instanceMethod() in DerivedClass";
    }
}

class CSJava
{
    public static void main(String... args)
    {     
	BaseClass bc = new BaseClass();                                                                                                                                                      
        BaseClass bcr;                                                                             
        DerivedClass dc = new DerivedClass();                                                                                                                        
        bcr = dc;                                                                              
                   
	out.println(bc.instanceMethod());		// BaseClass
	out.println(dc.instanceMethod());		// DerivedClass  // if instanceMethod() not implemented in DerivedClass, BaseClass                                                                                                                                       
        out.println(bcr.instanceMethod());            	// DerivedClass  // Differes from C# regardless of 'new' modifier as methods are virtual by default in Java // if instanceMethod() not implemented in DerivedClass, BaseClass
        out.println(((BaseClass)dc).instanceMethod());  // DerivedClass  // Differes from C# regardless of 'new' modifier as methods are virtual by default in Java // if instanceMethod() not implemented in DerivedClass, BaseClass
     }
}