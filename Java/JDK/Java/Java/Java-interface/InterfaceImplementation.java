interface ServiceA {
   String a();
}

interface ServiceB {
   Object b();
}

public class InterfaceImplementation implements ServiceA, ServiceB {
   public static void main(String args[]){
      InterfaceImplementation imp = new InterfaceImplementation();
      System.out.println("Hello" + imp.a() + imp.b());
   }
    
   // @Override
   public String a(){
      return " World ";
   }

   // @Override
   public String b(){
      return "!";
   }
}