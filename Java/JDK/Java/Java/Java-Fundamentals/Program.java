class Program{
    static int ui = Integer.parseUnsignedInt("4294967295");
    static long ulng = Long.parseUnsignedLong("18446744073709551615");

    public static void main(String[] args){
        byte byt = 127;
        System.out.println("Type: " + ((Object)byt).getClass().getName());
        short sh = 32767;
        System.out.println("Type: " + ((Object)sh).getClass().getName());
        int i = 2147483647;
        System.out.println("Type: " + ((Object)i).getClass().getName());

        System.out.println(Integer.toUnsignedString(ui));
        System.out.println("Type: " + ((Object)ui).getClass().getName());

        long lng = 9223372036854775807L;
        System.out.println("Type: " + ((Object)lng).getClass().getName());        

        System.out.println(Long.toUnsignedString(ulng));
        System.out.println("Type: " + ((Object)ulng).getClass().getName());        

        float fl = 0.000000001F;
        System.out.println("Type: " + ((Object)fl).getClass().getName());
        double dl = 0.0000000001D;
        System.out.println("Type: " + ((Object)dl).getClass().getName());
        char c = 'a';
        System.out.println("Type: " + ((Object)c).getClass().getName());

        boolean b = true;
        System.out.println("Type: " + (((Object)b)).getClass().getName());    

        String s = "Java Data Types";
        //System.out.println("Type: " + ((Object)s).getClass().getName());
        System.out.println("Type: " + s.getClass().getName());
    }
}

/*
Type: java.lang.Byte
Type: java.lang.Short
Type: java.lang.Integer
4294967295
Type: java.lang.Integer
Type: java.lang.Long
18446744073709551615
Type: java.lang.Long
Type: java.lang.Float
Type: java.lang.Double
Type: java.lang.Character
Type: java.lang.Boolean
Type: java.lang.String
*/