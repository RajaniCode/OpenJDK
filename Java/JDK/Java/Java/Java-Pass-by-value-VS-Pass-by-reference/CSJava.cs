using System;

class CSJava 
{
    void PassByValue(int v)
    {
        v = 100;
    }
    
    void PassByReference(ref int r)
    {
        r = 200;
    }

    void PassByOut(out int o)
    {
        o = 300;
    }

    void Print() 
    {
    	int i = 1;
    	int j = 2;
	int k;

	PassByValue(i);
	Console.WriteLine("Pass by value: " + i); 
	PassByReference(ref j);	 
	Console.WriteLine("Pass by reference: " + j); 
	PassByOut(out k);
	Console.WriteLine("Pass by out: " + k);   
    }

    static void Main()
    {
	CSJava cj = new CSJava();
	cj.Print();
    }
}