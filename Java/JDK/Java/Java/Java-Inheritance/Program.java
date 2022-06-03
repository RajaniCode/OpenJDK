import static java.lang.System.out;

class BaseClass {
    public void baseMethod() {
        out.println("Base Class Method");
    }
}

class DerivedClass extends BaseClass {
    public void derivedMethod() {
        out.println("Derived Class Method");
    }
}

class Program
{
    public static <T> T as(Class<T> t, Object o) {
        return t.isInstance(o) ? t.cast(o) : null;
    }

    public static void main(String... args) {
        BaseClass bc = new BaseClass();
        DerivedClass dc = new DerivedClass();

        bc = dc; // Fine
        bc.baseMethod(); // Fine

        //dc = bc; // Won't compile

        //dc = (DerivedClass)bc; //java.lang.ClassCastException unless previously bc = dc; 
        
        dc = as(DerivedClass.class, bc); // dc will be null unless it was previously: bc = dc; 
                
        if (dc != null) // if (dc instanceof BaseClass) // if (dc instanceof DerivedClass) // in case of previously: bc = dc; 
        {
            dc.baseMethod(); //java.lang.NullPointerException unless previously bc = dc; 
            dc.derivedMethod(); //java.lang.NullPointerException unless previously bc = dc; 
        }

        out.println("Fine");
    }
}