// Java 10
/*
1. Local-Variable Type Inference
2. Time-Based Release Versioning
3. Unmodifiable Collections
4. Optional Methods
*/

import static java.lang.System.out;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Runtime.Version;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 4. Optional Methods
class OptionalMethods {
    public void print() {
  	Optional<String> optionalString = Optional.of("Alpha");
        // out.println(optionalString.isPresent()); // true
        // out.println(optionalString); // Optional[Alpha]
        // out.println(optionalString.get()); // Alpha
        // optionalString.ifPresent(x -> out.println("ifPresent: " + x)); // ifPresent: Alpha

	out.println("4. Optional Methods: Optional: orElseThrow");
	out.println(optionalString.orElseThrow()); // Optional[Alpha]

	out.println("4. Optional Methods: OptionalInt: orElseThrow");
        OptionalInt optionalIntOf = OptionalInt.of(555);
	out.println(optionalIntOf.orElseThrow()); // Optional[555]

	out.println("4. Optional Methods: OptionalDouble: orElseThrow");
	OptionalDouble optionalDoubleOf = OptionalDouble.of(7.5D);
	out.println(optionalDoubleOf.orElseThrow()); // Optional[7.5]

	out.println("4. Optional Methods: OptionalLong: orElseThrow");
	OptionalLong optionalLongOf = OptionalLong.of(9223372036854775807L);
	out.println(optionalLongOf.orElseThrow()); // Optional[9223372036854775807]

        optionalString = Optional.empty();
	// out.println(optionalString.orElseThrow()); // NoSuchElementException: No value present

	String text = null;
        optionalString = Optional.ofNullable(text);
	// out.println(optionalString.orElseThrow()); // NoSuchElementException: No value present

	Stream<Integer> numberStream = Stream.iterate(0, x -> x + 1)
                                        .filter(x -> (x % 2 == 0))
                                        .limit(6); // .limit(5); // NoSuchElementException
	// numberStream.forEach(out::println); // IllegalStateException: stream has already been operated upon or closed

	out.println("4. Optional Methods: Stream: orElseThrow");
        int firstDoubleDigit = numberStream
                            .filter(i -> i >= 10)
			    .findFirst()
			    .orElseThrow(); // .limit(5); // NoSuchElementException
        out.println("First double digit number");
        out.println(firstDoubleDigit);

	/*
	for(BigDecimal n = new BigDecimal(0.0); n.compareTo(new BigDecimal(1.0)) <=  0; n = n.add(new BigDecimal(0.1))) {
	     // BigDecimal r = n.remainder(new BigDecimal(0.2));
             // if (r.compareTo(new BigDecimal(0.0)) ==  0) {
             if ( n.remainder(new BigDecimal(0.2)).compareTo(new BigDecimal(0.0)) ==  0 ) {
                out.println(n.setScale(1, RoundingMode.DOWN));
	     }
        }
        */

        Stream<BigDecimal> streamNumbers = Stream.iterate(new BigDecimal(0.0), x -> x.add(new BigDecimal(0.1)))
                                        .filter(x -> (x.remainder(new BigDecimal(0.2)).compareTo(new BigDecimal(0.0)) ==  0))
                                        .limit(6); // .limit(5); // NoSuchElementException
        // streamNumbers.forEach(x -> out.println(x.setScale(1, RoundingMode.DOWN))); // IllegalStateException: stream has already been operated upon or closed

        BigDecimal first = streamNumbers
                            .filter(x -> x.compareTo(new BigDecimal(1.0)) >=  0)
			    .findFirst()
			    .orElseThrow(); // .limit(5); // NoSuchElementException
        out.println("First number");
        out.println(first.setScale(0, RoundingMode.DOWN));
    }
}

// 3. Unmodifiable Collections
class UnmodifiableCollections {
    public void print() {        
        out.println("3. Unmodifiable Collections");
        // copyOf()
        // java.util.List, java.util.Map and java.util.Set get a new static method copyOf() each    
        // Set, List, Map collections are added with a static copyOf() method 
        // And return unmodifiable Set, List, Map containing the entries provided
        // For a List, if the given List is subsequently modified, the returned List will not reflect such modifications
        // Collectors class gets various methods for collecting unmodifiable collections (Set, List, Map)
         
        List<String> greek = new ArrayList<>();
        greek.add("Alpha");
        greek.add("Beta");
        greek.add("Gamma");
        greek.add("Delta");
        greek.add("Epsilon");        
        out.println(greek); // [Alpha, Beta, Gamma, Delta, Epsilon]
        // static copyOf() method creates an unmodifiable List from a List
        out.println("copyOf()");
        List<String> copyOfGreek = List.copyOf(greek);
        out.println(copyOfGreek); // [Alpha, Beta, Gamma, Delta, Epsilon]
        
        // UnsupportedOperationException
        // copyOfGreek.add("Zeta"); 
        
        greek.add("Eta");
        out.println(greek); // [Alpha, Beta, Gamma, Delta, Epsilon, Eta]
        out.println(copyOfGreek); // [Alpha, Beta, Gamma, Delta, Epsilon]

        // static Collectors.toUnmodifiableList() method makes collections unmodifiable
        List<String> collector = greek.stream().collect(Collectors.toUnmodifiableList());

        // UnsupportedOperationException
        // collector.add("Theta");
        out.println();
    }
}

// 2. Time-Based Release Versioning
class TimeBasedReleaseVersioning {
    public void print() {        
        out.println("2. Time-Based Release Versioning");
        // The new pattern of the Version number is: $FEATURE.$INTERIM.$UPDATE.$PATCH
        /*
        $FEATURE: counter will be incremented every 6 months and will be based on feature release versions, e.g: JDK 10, JDK 11.
        $INTERIM: counter will be incremented for non-feature releases that contain compatible bug fixes and enhancements but no incompatible changes. Usually this will be zero, as there will be no interim release in a six month period. This kept for future revision to the release model.
        $UPDATE: counter will be incremented for compatible update releases that fix security issues, regressions, and bugs in newer features. This is updated one month after the feature release and every 3 months thereafter. The April 2018 release is JDK 10.0.1, the July release is JDK 10.0.2, and so forth
        $PATCH: counter will be incremented for an emergency release to fix a critical issue.
        */
        // New API's have been added to get these counter values programmatically
        Version version = Runtime.version();
        out.println(version.feature());
        out.println(version.interim());
        out.println(version.update());
        out.println(version.patch());
        out.println();
    }
}

// 1. Local-Variable Type Inference
class LocalVariableTypeInference {
    public void print() {
        out.println("1. Local-Variable Type Inference");
        // var x; // error: cannot infer type for local variable x
        // var y, z = 0; // error: 'var' is not allowed in a compound declaration
        // var xyz = null; //  error: cannot infer type for local variable xyz
        var fileName = "";
        var userDirectory = System.getProperty("user.dir");
        out.println("User directory: " + userDirectory);
 
        try {
	    var filePath = Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"); // infers Path	    
            fileName = filePath.getFileName().toString();
	    out.println("File name: " + fileName);

            // URL url = new URL("https://www.oracle.com");
            // URLConnection conn = url.openConnection();
            // Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));        
            var url = new URL("https://www.oracle.com");
            var conn = url.openConnection();
            var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));   

            var list = new ArrayList<String>(); // infers ArrayList<String>
            var stream = list.stream(); // infers Stream<String>

            var path = Paths.get(fileName); // infers Path
            var bytes = Files.readAllBytes(path); // infers bytes[]

	    filePath.toFile().deleteOnExit();
        }
        catch(MalformedURLException e){
            e.printStackTrace(); 
        }
        catch(IOException e){
            e.printStackTrace(); 
        }

        // infers FileInputStream<string>
        try (var input = new FileInputStream(fileName)) {
            
        } 
        catch(IOException e){
            e.printStackTrace(); 
        }

        // infers ArrayList<String>
        var numbers = List.of(1, 2, 3, 4, 5);
        out.println("Numbers");
        // Index of Enhanced For Loop
        for (var number : numbers) {
	    out.println(number);
        }

        // Local variable declared loop
        out.println("Numbers");
        for (var index = 0; index < numbers.size(); index++) {
	    out.println(numbers.get(index));
        }
        out.println();
    }
}

class Java10 {
    public static void main(String... args) {
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

        LocalVariableTypeInference localVariableTypeInfer = new LocalVariableTypeInference();
        localVariableTypeInfer.print();

        TimeBasedReleaseVersioning timeBasedReleaseVersion = new TimeBasedReleaseVersioning();
        timeBasedReleaseVersion.print();

        UnmodifiableCollections unmodifiableCollection = new UnmodifiableCollections();
        unmodifiableCollection.print();

	OptionalMethods methodsOptional = new OptionalMethods();
	methodsOptional.print();
    }
}

// Output
/*
Java Version: 10.0.2

1. Local-Variable Type Inference
User directory: D:\Rajani\Java\Java 2020\Java 2020\Java10\Java10
File name: Temp-4557072479437978961.txt
Numbers
1
2
3
4
5
Numbers
1
2
3
4
5

2. Time-Based Release Versioning
10
0
2
0

3. Unmodifiable Collections
[Alpha, Beta, Gamma, Delta, Epsilon]
copyOf()
[Alpha, Beta, Gamma, Delta, Epsilon]
[Alpha, Beta, Gamma, Delta, Epsilon, Eta]
[Alpha, Beta, Gamma, Delta, Epsilon]

4. Optional Methods: Optional: orElseThrow
Alpha
4. Optional Methods: OptionalInt: orElseThrow
555
4. Optional Methods: OptionalDouble: orElseThrow
7.5
4. Optional Methods: OptionalLong: orElseThrow
9223372036854775807
4. Optional Methods: Stream: orElseThrow
First double digit number
10
First number
1
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/