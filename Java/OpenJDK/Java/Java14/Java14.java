// Java 14
/*
1. Pattern Matching for instanceof (Preview)
2. Records (Preview)
3. Text Blocks (Preview) Escape Sequences
4. Switch Expressions
*/

import static java.lang.System.out;

class Java14 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));
	
	/*
	PatternMatchingInstanceOf instanceOfPatternMatching = new PatternMatchingInstanceOf();
	instanceOfPatternMatching.print();	

	RecordsClient clientRecords = new RecordsClient();
	clientRecords.print();

	TextBlocks blocksText = new TextBlocks();
	blocksText.print();
	*/

        SwitchExpressions expressionsSwitch = new SwitchExpressions();
        expressionsSwitch.print();
    }
}

// 4. Switch Expressions
class SwitchExpressions {    
    public void switchExpression(String greek) {
	out.println("4. Switch Expressions");	
	int number = switch (greek.toLowerCase()) {
   	    case "epsilon", "omicron", "upsilon" -> 7;
	    case "lambda" -> 6;		
	    case "alpha", "gamma", "delta", "theta", "kappa", "sigma", "omega" -> 5;
	    case "beta", "zeta", "iota" -> 4;
 	    case "eta", "rho", "tau", "phi", "chi", "psi" -> 3;
	    case "mu", "nu", "xi", "pi" -> 2;
	    default -> throw new IllegalArgumentException("Invalid Greek alphabet: " + greek);
        };
        out.println(number);
        out.println();
    }

    public void print() {
	switchExpression("Alpha");
    }
}

/*
// 3. Text Blocks (Preview) Escape Sequences
// Java 15
// Java 14 Preview
// javac -Xlint:preview --enable-preview -source 14 Java14.java
// java --enable-preview Java14
class TextBlocks {
    public void print() {
	out.println("3. Text Blocks (Preview) Escape Sequences");
	
	String twoDimensionalTextBlock = """
		Text blocks Java 14 Preview got two new escape sequences - \
		1. Backslash: \\ and \
		2. Backslash\ss: \\s""";

	out.println(twoDimensionalTextBlock);
        out.println();
    }
}

// 2. Records (Preview)
// Java 16
// Java 14 Preview, Java 15 Preview,   
// javac -Xlint:preview --enable-preview -source 14 Java14.java
// javac -Xlint:preview --enable-preview -source 15 Java14.java
// java --enable-preview Java14
// warning: [preview] records are a preview feature and may be removed in a future release.
record Record (int number, String text) { }
record Records(int number, String text) {
    // field declaration must be static
    // static field
    static String suffix;

    // static initializer
    static {
        suffix = "suffixed in static initializer";
    }

    // instance initializers not allowed in records
    // {
        // suffix = "cannot be suffixed in instance initializer"; // can be initialized in canonical constructor though 
    // }

    // canonical constructor
    public Records {
	out.println("canonical constructor call");
	suffix = "suffixed in canonical constructor";	
        if(number < 0) {            
            // throwing from canonical constructor
            throw new IllegalArgumentException("Number cannot be negative");
        }
    }

    // public accessor method
    public int number() {
        // out.println("Number: " + number);
        return number;
    }

     // public accessor method
    public String text() {
        // out.println("Text: " + text);
        return text;
    }

    // static method
    public static Records append(String message) {
        return new Records(2, message + " - "  + suffix);
    }

    // virtual method
    public Records virtualAppend(String message) {
        return new Records(2, message + " - "  + suffix);
    }
}

// 2. Records (Preview)
class RecordsClient {
    public void print() {        
        out.println("2. Records (Preview)");
	
	Record rec = new Record(-1, "Omega");
        out.println(rec.number());
      	out.println(rec.text());

        var recs = new Records(1, "Alpha");
        out.println(recs.number());
        out.println(recs.text());
	
	// static method call
        out.println(Records.append("Static"));

	// virtual method call
        out.println(recs.append("Virtual"));

	// java.lang.IllegalArgumentException: Number cannot be negative
        // recs = new Records(-1, "Beta");

	out.println("Note: record cannot be duplicated by varying in parameters");

        out.println();
    }
}

// 1. Pattern Matching for instanceof (Preview)
class PatternMatchingInstanceOf {
    public void print() {        
        out.println("1. Pattern Matching for instanceof (Preview)");
       
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
*/

// Output excluding Preview
/*
Java Version: 14.0.2

4. Switch Expressions
5
*/

// Output including Preview
/*
Java Version: 14.0.2

1. Pattern Matching for instanceof (Preview)
pattern instanceof String
((String)pattern).length():11
pattern instanceof String s
s.length():11

2. Records (Preview)
-1
Omega
canonical constructor call
1
Alpha
canonical constructor call
Records[number=2, text=Static - suffixed in canonical constructor]
canonical constructor call
Records[number=2, text=Virtual - suffixed in canonical constructor]
Note: record cannot be duplicated by varying in parameters

3. Text Blocks (Preview) Escape Sequences
Text blocks Java 14 Preview got two new escape sequences - 1. Backslash: \ and 2. Backslash s: \s

4. Switch Expressions
5
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/