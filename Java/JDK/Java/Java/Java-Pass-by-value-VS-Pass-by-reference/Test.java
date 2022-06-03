class ReferenceWrapper<E> {
    E ref;
    public ReferenceWrapper( E e ){
        ref = e;
    }
    public E g() { return ref; }
    public void s( E e ){ this.ref = e; }

    public String toString() {
        return ref.toString();
    }
}

public class Test {

    public static void main ( String [] args ) {
        ReferenceWrapper<Integer> iByRef = new ReferenceWrapper<Integer>( 1 );
        addOne( iByRef );
        System.out.println( iByRef ); // prints 2

        ReferenceWrapper<String> sByRef = new ReferenceWrapper<String>( "Hola" );
        reverse( sByRef ); 
        System.out.println( sByRef ); // prints aloH

    }

    // Change the value of ref by adding 1
    public static void addOne( ReferenceWrapper<Integer> ref ) { 
        int i = ref.g();
        ref.s( ++i  );

        // or 
        //int i = ref.g();
        //ref.s( i + 1 );

    }
    // Reverse the vale of a string.
    public static void reverse( ReferenceWrapper<String> otherRef ) { 
        String v = otherRef.g();
        String reversed = new StringBuilder( v ).reverse().toString();
        otherRef.s( reversed );
    }

}