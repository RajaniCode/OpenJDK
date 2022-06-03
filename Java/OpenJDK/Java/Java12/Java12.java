// Java 12
/*
1. Switch Expressions (Preview)
2. String Methods
3. Class Methods
4. File Mismatch
5. Compact Number Format
6. Teeing Collectors
*/

import static java.lang.System.out;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Java12 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

        SwitchExpressions expressionsSwitch = new SwitchExpressions();
        expressionsSwitch.print();

        StringMethods methodsString = new StringMethods();
        methodsString.print();

	ClassMethods methodsClass = new ClassMethods();
	methodsClass.print();

	FileMismatch mismatchFile = new FileMismatch();
	mismatchFile.print();

	CompactNumberFormat formatCompactNumber = new CompactNumberFormat();
	formatCompactNumber.print();

	TeeingCollectors collectorsTeeing = new TeeingCollectors();
	collectorsTeeing.print();
    }
}

// 6. Teeing Collectors
class TeeingCollectors {
    // private static volatile int counter;
    // private volatile int counter;
    public void print() {
        out.println("6. Teeing Collectors");

        // final Integer[] counter = { 0 };
        AtomicInteger counter = new AtomicInteger(0);
        
	Stream.iterate(65, x -> x + 1)
        .filter(x -> (x < 91 && x % 2 != 0)  || (x > 96 && x % 2 == 0) )
        .limit(26).collect(Collectors.teeing(
                        Collectors.partitioningBy(x -> (x < 91 && x % 2 != 0)),
                        Collectors.partitioningBy(x -> (x > 96 && x % 2 == 0)),
                        (var partitioningUpper, var partitioningLower) -> (
			    Arrays.asList(IntStream
				  .range(0, Math.min(partitioningUpper.get(true).size(), partitioningLower.get(true).size()))
				  .mapToObj(i -> String.format("%c", partitioningUpper.get(true).get(i)) + ", " + String.format("%c", partitioningLower.get(true).get(i)))
				  .collect(Collectors.toList()).toString().replace("[", "").replace("]", "").split(", "))
			          .stream().collect(Collectors.toList())
			)))
        .forEach(x -> {
                // counter++;
                // counter[0]++;
        	counter.getAndIncrement();
                
                // if (counter < 26) {
                // if (counter[0] < 26) {
        	if (counter.get() < 26) {        	
        	    out.print(x + ", ");
        	}
        	else {
        	    out.print(x);
        	}

        });
        out.println();
    }
}

// 5. Compact Number Format
class CompactNumberFormat {
    public void print() {
        out.println("5. Compact Number Format");
        out.println("Compact Number Format - SHORT");
        NumberFormat formatNumber = NumberFormat.getCompactNumberInstance(new Locale("de", "DE"), NumberFormat.Style.SHORT);
	formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(1000));
	formatNumber = NumberFormat.getCompactNumberInstance(new Locale("fr", "FR"), NumberFormat.Style.SHORT);
	// formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(1000));
	formatNumber = NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.SHORT);
	// formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(1000));
        out.println("Compact Number Format - LONG");
	formatNumber = NumberFormat.getCompactNumberInstance(new Locale("de", "DE"), NumberFormat.Style.LONG);
	// formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(-1000));
	formatNumber = NumberFormat.getCompactNumberInstance(new Locale("fr", "FR"), NumberFormat.Style.LONG);
	// formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(-1000));
	formatNumber = NumberFormat.getCompactNumberInstance(new Locale("en", "US"), NumberFormat.Style.LONG);
	// formatNumber.setMaximumFractionDigits(2);
        out.println(formatNumber.format(-1000));
	out.println();
    }
}

// 4. File Mismatch
class FileMismatch {
    public void print() {
        out.println("4. File Mismatch");
        try {
            Path filePathAlpha = Files.writeString(Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"), "File Mismatch");
	    Path filePathBeta = Files.writeString(Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"), "File Mismatch");

            if (Files.mismatch(filePathAlpha, filePathBeta) == -1L) {
		out.println("No Mismatch");
	    } else {
		out.println("Mismatch");
	    }

	    filePathAlpha.toFile().deleteOnExit();
            filePathBeta.toFile().deleteOnExit();

            filePathAlpha = Files.writeString(Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"), "file Mismatch");
	    filePathBeta = Files.writeString(Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"), "File mismatch");

            if (Files.mismatch(filePathAlpha, filePathBeta) == -1L) {
		out.println("No Mismatch");
	    } else {
		out.println("Mismatch");
	    }
	    out.println();

	    filePathAlpha.toFile().deleteOnExit();
            filePathBeta.toFile().deleteOnExit();
	} catch(IOException ex) { 
	    ex.printStackTrace();
	}
    }
}

// 3. Class Methods
class ClassMethods {
    public void print() {
	out.println("3. Class Methods: descriptorString, arrayType, componentType, getComponentType");
        Integer n = 1;
	// out.println(n.getClass().getName());
	out.println(n.getClass().descriptorString());
	out.println(n.getClass().arrayType());
	out.println(n.getClass().componentType());
	out.println(n.getClass().getComponentType());

	Integer[] a = { 1 };
	// out.println(a.getClass().getName());
	out.println(a.getClass().descriptorString());
	out.println(a.getClass().arrayType());
	out.println(a.getClass().componentType());
	out.println(a.getClass().getComponentType());
        out.println();
    }
}

// 2. String Methods
class StringMethods {
    public void print() {
        out.println("2. String Methods: indent, transform, describeConstable, resolveConstantDesc");
	out.println("2. String Methods: indent");
        out.printf("indent(10)".indent(10));
        out.printf("indent(-10)".indent(-10));
        out.printf("indent(5).indent(-4)".indent(5).indent(-4));

        out.println("2. String Methods: transform");
	out.println("transform".transform(x -> new StringBuilder(x)).append("ed").toString());
	out.println("transform".transform(x -> new String(x.concat("ed"))).toString());

        out.println("2. String Methods: describeConstable");
        Optional<String> optionalString = "describe Constable".describeConstable();
        optionalString.ifPresent(x -> out.println("ifPresent: " + x));

        optionalString = "".describeConstable();
        optionalString.ifPresent(x -> out.println("ifPresent: " + x.getClass().descriptorString()));

        out.println("2. String Methods: resolveConstantDesc"); 
        out.println("resolveConstantDesc".resolveConstantDesc(MethodHandles.lookup()));
        out.println("resolveConstantDesc".resolveConstantDesc(null));
        out.println("resolveConstantDesc".resolveConstantDesc(MethodHandles.lookup()).describeConstable().get());
        out.println("resolveConstantDesc".resolveConstantDesc(null).describeConstable().get());
        out.println();
    }
}

// 1. Switch Expressions (Preview)
class SwitchExpressions {    
    public void switchExpression(String greek) {
	out.println("1. Switch Expressions (Preview)");
        // Pre-Java 12 Preview
	int number;
        switch (greek.toLowerCase()) {
	    case "epsilon":
	    case "omicron":
	    case "upsilon":
	        number = 7;
	    case "lambda":
	        number = 6;
	    case "alpha":
	    case "gamma":
	    case "delta":
	    case "theta":
	    case "kappa":
	    case "sigma":
	    case "omega":
	        number = 5;
		break;
	    case "beta":
	    case "zeta":
	    case "iota":
	        number = 4;
		break;
 	    case "eta":
	    case "rho":
	    case "tau":
	    case "phi":
	    case "chi":
	    case "psi":
	        number = 3;
		break;
	    case "mu":
	    case "nu":
	    case "xi":
	    case "pi":
	        number = 2;
	    default:
	        throw new IllegalArgumentException("Invalid Greek alphabet: " + greek);
        }
        out.println(number);
	        	
	/*
        // Java 14
        // Java 12 Preview, Java 13 Preview    
        // javac -Xlint:preview --enable-preview -source 12 Java12.java
        // javac -Xlint:preview --enable-preview -source 13 Java12.java
        // java --enable-preview Java12
        // warning: [preview] switch expressions are a preview feature and may be removed in a future release
	number = switch (greek.toLowerCase()) {
            // warning: [preview] multiple case labels are a preview feature and may be removed in a future release
	    case "epsilon", "omicron", "upsilon" -> 7;
	    case "lambda" -> 6;		
	    case "alpha", "gamma", "delta", "theta", "kappa", "sigma", "omega" -> 5;
	    case "beta", "zeta", "iota" -> 4;
 	    case "eta", "rho", "tau", "phi", "chi", "psi" -> 3;
	    case "mu", "nu", "xi", "pi" -> 2;
	    default -> throw new IllegalArgumentException("Invalid Greek alphabet: " + greek);
        };
        out.println(number);
        */

        out.println();
    }

    public void print() {
	switchExpression("Alpha");
    }
}

// Output
/*
Java Version: 12.0.2

1. Switch Expressions (Preview)
5

2. String Methods: indent, transform, describeConstable, resolveConstantDesc
2. String Methods: indent
          indent(10)
indent(-10)
 indent(5).indent(-4)
2. String Methods: transform
transformed
transformed
2. String Methods: describeConstable
ifPresent: describe Constable
ifPresent: Ljava/lang/String;
2. String Methods: resolveConstantDesc
resolveConstantDesc
resolveConstantDesc
resolveConstantDesc
resolveConstantDesc

3. Class Methods: descriptorString, arrayType, componentType, getComponentType
Ljava/lang/Integer;
class [Ljava.lang.Integer;
null
null
[Ljava/lang/Integer;
class [[Ljava.lang.Integer;
class java.lang.Integer
class java.lang.Integer

4. File Mismatch
No Mismatch
Mismatch

5. Compact Number Format
Compact Number Format - SHORT
1.000
1▒k
1K
Compact Number Format - LONG
-1 Tausend
-1 millier
-1 thousand

6. Teeing Collectors
A, b, C, d, E, f, G, h, I, j, K, l, M, n, O, p, Q, r, S, t, U, v, W, x, Y, z
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/