using System;

//struct Number 
class Number 
{
    public int x;
}

class CSJava 
{
    void Print()
    {
	Number a = new Number();
        Number b = new Number();

	a.x = 10;
	b.x = 20;

	Console.WriteLine("a.x {0}, b.x {1}", a.x, b.x);

	a = b;
	b.x = 30;

	Console.WriteLine("a.x {0}, b.x {1}", a.x, b.x);
    }

    static void Main()
    {
	CSJava cj = new CSJava();
        cj.Print();
    }
}