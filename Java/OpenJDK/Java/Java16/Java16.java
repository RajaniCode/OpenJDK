// Java 16
/*
1. Pattern Matching for instanceof
2. Records
*/

import static java.lang.System.out;

class Java16 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

	PatternMatchingInstanceOf instanceOfPatternMatching = new PatternMatchingInstanceOf();
	instanceOfPatternMatching.print();	

	RecordsClient clientRecords = new RecordsClient();
	clientRecords.print();
    }
}

// 2. Records
// Java 16
// Java 14 Preview, Java 15 Preview,   
// javac -Xlint:preview --enable-preview -source 14 Java14.java
// javac -Xlint:preview --enable-preview -source 15 Java14.java
// java --enable-preview Java14
// warning: [preview] records are a preview feature and may be removed in a future release.
record Records(int number, String text) { 
    // Public accessor method
    public int number() {
        //out.println("Number is " + number);
        return number;
    }

     // Public accessor method
    public String text() {
        //out.println("Text is " + text);
        return text;
    }

    // Static field
    static Character period;

    // Static initializer
    static {
        period = '.';
    }

    // No instance initializer
    // {
        // period = '.';
    // }

    // Static method
    public static Records append(String message) {
        return new Records(2, message + period);
    }
}

// 2. Records
class RecordsClient {
    public void print() {        
        out.println("2. Records");


	var rec = new Records(1, "Final");
        out.println(rec.number());
        out.println(rec.text());
        out.println(Records.append("Final"));

        out.println();
    }
}

// 1. Pattern Matching for instanceof
class PatternMatchingInstanceOf {
    public void print() {        
        out.println("1. Pattern Matching for instanceof");
       
        Object pattern = "^[a-zA-Z]*$";

	// Pre-Java 14 Preview
        if (pattern instanceof String) {
	    // String s = (String)pattern;
            out.println("pattern instanceof String");
            out.println("((String)pattern).length():" + ((String)pattern).length());
        }
        
	// Java 16
        // Java 14 Preview, Java 15 Preview,   
        // javac -Xlint:preview --enable-preview -source 14 Java14.java
	// javac -Xlint:preview --enable-preview -source 15 Java14.java
        // java --enable-preview Java14
        // warning: [preview] pattern matching in instanceof is a preview feature and may be removed in a future release.
        if (pattern instanceof String s) {
	    // error: pattern binding s may not be assigned
	    // s = "^[a-zA-Z0-9]*$";
            out.println("pattern instanceof String s");
            out.println("s.length():" + s.length());
        }

	out.println();
    }
}

// Output
/*
Java Version: 16
1. Pattern Matching for instanceof
pattern instanceof String
((String)pattern).length():11
pattern instanceof String s
s.length():11
2. Records
1
Final
Records[number=2, text=Final.]
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/