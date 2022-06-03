import static java.lang.System.out;

class BaseClass {
    protected void baseMethod() {
        out.println("Base Class Method");
    }
}

class DerivedClass extends BaseClass {
    public void derivedMethod() {
    	BaseClass bc = new BaseClass();
        BaseClass bcr;
        DerivedClass dc = new DerivedClass();
	super.baseMethod();
	this.baseMethod();
        baseMethod();        
        bc.baseMethod();  // Compilation Error in C# // Cannot access protected member 'BaseClass.baseMethod()' via a qualifier of type 'BaseClass'; the qualifier must be of type 'CSJava' (or derived from it)
	dc.baseMethod();  
        bcr = dc;         
        bcr.baseMethod(); // Compilation Error in C# // Cannot access protected member 'BaseClass.baseMethod()' via a qualifier of type 'BaseClass'; the qualifier must be of type 'CSJava' (or derived from it)
    }
}

class CSJava {
    public static void main(String... args) {
	DerivedClass dc = new DerivedClass();
	dc.derivedMethod();        
        out.println("Fine");
    }
}