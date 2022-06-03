import static java.lang.System.out;

class ReferenceWrapper<T> {
    T t;
    public ReferenceWrapper(T t){
        this.t = t;
    }    
    public T getter() { return t; }
    public void setter( T t ){ this.t = t; }
   
    public String toString() {
        return t.toString();
    }

    public T getObject()
    {
        return t;
    }
}

class CSJava {
   
    // int j = 2;

    void passByValue(int v) {
        v = 100;
    }
        
    void passByReference(ReferenceWrapper<Integer> r) {
        int n = r.getter(); 
	n = 200;
        r.setter(n);
    }

    void print() {
	int i = 1;
    	int j = 2; //
	int k; //

	passByValue(i);
	out.println("Pass by value: " + i); 
	ReferenceWrapper<Integer> reference = new ReferenceWrapper<Integer>(j);
	passByReference(reference);
	out.println("Pass by reference: " + reference); 
	int n = reference.getObject();
	out.println("Pass by reference int : " + n);  
    }

    public static void main(String[] args) {
	CSJava cj = new CSJava();
	cj.print();
    }
}