import java.util.Date;

class Tuple<A, B> {

    public final A a;
    public final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

}

class Triplet<C, D, E> {

    public final C c;
    public final D d;
    public final E e;

    public Triplet(C c, D d, E e) {
        this.c = c;
        this.d = d;
        this.e = e;
    }

}

class Polygon<F, G, H, I, J, K, L, M, N, O, P, Q> {

    public final F f;
    public final G g;
    public final H h;
    public final I i;
    public final J j;
    public final K k;
    public final L l;
    public final M m;
    public final N n;
    public final O o;
    public final P p;
    public final Q q;

    public Polygon(F f, G g, H h, I i, J j, K k, L l, M m, N n, O o, P p, Q q) {
        this.f = f;
        this.g = g;
        this.h = h;
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
        this.m = m;
        this.n = n;
        this.o = o;
        this.p = p;
        this.q = q;
    }

}

class X {

    private char ch;

    public char getChar() {
        return ch;
    }

    public void setChar(char ch) {
       this.ch = ch;
    }
}

class Program {

    public static void main(String[] args) {
        Tuple<String, Integer> t = new Tuple<>("Alpha", 1);
        String s = String.format("String = %s, Integer = %d", t.a, t.b);
        System.out.println(s);

        Triplet<String, Integer, Boolean> tri = new Triplet<>("Beta", 2, false);
        s = String.format("String = %s, Integer = %d, Boolean = %b", tri.c, tri.d, tri.e);
        System.out.println(s);

        X x = new X();
        x.setChar('Y');

        Date dt = new Date();
  
        Polygon<String, Double, Boolean, X, Date, Tuple, Tuple, Triplet, Triplet, Triplet, Float, Long> poly = new Polygon<>("Gamma", 3D, true, x, dt, t, t, tri, tri, tri, 11F, 12L);

        s = String.format("String = %s, Double = %.2f, Boolean = %b, char = %c, Date = %tc, Tuple.String = %s, Tuple.Integer = %d, Triplet.String = %s, Triplet.Integer = %d, Triplet.Boolean = %b, Float = %.2f, Long = %d", 
	poly.f, poly.g, poly.h, poly.i.getChar(), poly.j, poly.k.a, poly.l.b, poly.m.c, poly.n.d, poly.o.e, poly.p, poly.q);
        System.out.println(s);
    }

}