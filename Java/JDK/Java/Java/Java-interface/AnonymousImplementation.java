// anonymous interface implementation

interface Foo {

    void f();
}

interface Bar {

    void f();
}

public class AnonymousImplementation {

    private String foo = "foo", bar = "bar";

    Foo getFoo() {
        return new Foo() {

            @Override
            public void f() {
                System.out.println(foo);
            }
        };
    }

    Bar getBar() {
        return new Bar() {

            @Override
            public void f() {
                System.out.println(bar);
            }
        };
    }

    public static void main(String... args) {
        AnonymousImplementation imp = new AnonymousImplementation();
        imp.getFoo().f();
        imp.getBar().f();
    }
}