import static java.lang.System.out;

final class Number {
    public int x;
}

class CSJava {
    void print() {
	Number a = new Number();
        Number b = new Number();

	a.x = 10;
	b.x = 20;

	out.format("a.x %d, b.x %d", a.x, b.x);

	out.println();

	a = b;
	b.x = 30;

	out.format("a.x %d, b.x %d", a.x, b.x);
    }

    public static void main(String[] args) {
	CSJava cj = new CSJava();
        cj.print();
    }
}