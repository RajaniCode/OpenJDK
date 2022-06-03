using System;
// using static System.Console; // C# 6.0

class BaseClass 
{ 
    public String instanceMethod() // hiding intended
    {       
        return "instanceMethod() in BaseClass";
    }
}

class DerivedClass : BaseClass
{   
    public new String instanceMethod() // hiding intended
    {       
        return "instanceMethod() in DerivedClass";
    }
}

class CSJava
{
    public static void Main(String[] args)
    {        
	BaseClass bc = new BaseClass();                                                                                                                                                   
        BaseClass bcr;                                                                             
        DerivedClass dc = new DerivedClass();                                                                                                                        
        bcr = dc;                                                                              
          
	Console.WriteLine(bc.instanceMethod());			// BaseClass
	Console.WriteLine(dc.instanceMethod());          	// DerivedClass // if instanceMethod() not implemented in DerivedClass, BaseClass                                                                                                                                      
        Console.WriteLine(bcr.instanceMethod());             	// BaseClass    // Differes from Java as methods are virtual by default in Java 
        Console.WriteLine(((BaseClass)dc).instanceMethod());  	// BaseClass    // Differes from Java as methods are virtual by default in Java
     }
}