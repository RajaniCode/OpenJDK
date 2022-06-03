//using static System.Console;

using System;

class BaseClass 
{
    protected void baseMethod() 
    {
        //WriteLine("Base Class Method");
        Console.WriteLine("Base Class Method");
    }
}

class DerivedClass : BaseClass 
{
    public void derivedMethod() 
    {
    	BaseClass bc = new BaseClass();
	BaseClass bcr;
        DerivedClass dc = new DerivedClass();
        base.baseMethod();
        this.baseMethod();
        baseMethod();        
        //bc.baseMethod(); // Compilation Error in C# // Cannot access protected member 'BaseClass.baseMethod()' via a qualifier of type 'BaseClass'; the qualifier must be of type 'CSJava' (or derived from it)
	dc.baseMethod();   
	bcr = dc;          
	//bcr.baseMethod();  // Compilation Error in C# // Cannot access protected member 'BaseClass.baseMethod()' via a qualifier of type 'BaseClass'; the qualifier must be of type 'CSJava' (or derived from it)
    }
}

class CSJava 
{
    public static void Main(String[] args) 
    {
	DerivedClass dc = new DerivedClass();
	dc.derivedMethod();
	//WriteLine("Fine");        
        Console.WriteLine("Fine");
    }
}