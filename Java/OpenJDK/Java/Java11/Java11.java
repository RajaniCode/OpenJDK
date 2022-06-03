// Java 11
/*
1. Launch Single-File Source-Code Programs
2. Preview Switch
3. Nest-Based Access Control
4. Nestmate Reflection
5. Local-Variable Syntax for Lambda Parameters
6. String Methods
7. File Methods
8. Collection to Array
9. Predicate.not
10. HTTP Client
11. Optional Methods
*/

import static java.lang.System.out;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 1. Launch Single-File Source-Code Programs
// 2. Preview Switch
class Java11 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));
       
        out.println("1. Launch Single-File Source-Code Programs");
	out.println("Only for class with main method");
	out.println("With main method class being the topmost class in order in the containing file");
	out.println("In Java 11, without any class file (of main method class or other classes in the containing file if any) found on application class path");
        out.println("java Java11.java");
	
        out.println("1. Launch Single-File Source-Code Programs: Class Data Sharing (CDS) off");
	out.println("Only for class with main method");
	out.println("With main method class being the topmost class in order in the containing file");
	out.println("In Java 11, without any class file (of main method class or other classes in the containing file if any) found on application class path");
	out.println("java -Xshare:off Java11.java");
	out.println();

        out.println("2. Preview Switch");
        out.println("javac -Xlint:preview --enable-preview -source 11 Java11.java");
        out.println("java --enable-preview Java11");
        out.println("Note: Preview Switch will not work for Single-File Source-Code Programs Launcher");
	out.println();

	NestBasedAccessControlClient clientNestBasedAccessControl = new NestBasedAccessControlClient();
	clientNestBasedAccessControl.print();

	NestmateReflection reflectionNestmate = new NestmateReflection();
	reflectionNestmate.print();

	Lambda lamb = new Lambda();
	lamb.print();

        StringMethods methodsString = new StringMethods();
        methodsString.print();

        FileMethods methodsFile = new FileMethods();
        methodsFile.print();

        CollectionToArray collectionArray = new CollectionToArray();
        collectionArray.print();

        PredicateNot notPredicate = new PredicateNot();
	notPredicate.print();

	HTTPClient clientHTTP = new HTTPClient();
	clientHTTP.print();

	OptionalMethods methodsOptional = new OptionalMethods();
    	methodsOptional.print();
    }
}

// 11. Optional Methods
class OptionalMethods {
    public void print() {
  	Optional<String> optionalString = Optional.of("Alpha");
        out.println("11. Optional Methods: isEmpty");
        out.println(optionalString.isEmpty()); // false
	
        out.println("11. Optional Methods: orElseThrow");
        out.println(optionalString.orElseThrow()); // Alpha

        optionalString = Optional.empty();
        out.println(optionalString.isEmpty()); // true
        // out.println(optionalString.orElseThrow()); // NoSuchElementException: No value present
    }
}

// 10. HTTP Client
class HTTPClient {
    public void print() {
        out.println("10. HTTP Client");
        HttpClient httpClient = HttpClient.newBuilder()
  				.version(HttpClient.Version.HTTP_2)
  				.connectTimeout(Duration.ofSeconds(20))
  				.build();
        int port = 3000;
	HttpRequest httpRequest = HttpRequest.newBuilder()
  				  .GET()
  				  // .uri(URI.create("http://localhost:" + port))
  				  .uri(URI.create("http://example.com"))
  				  .build();
        out.println(httpRequest);
        try {
	    HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
	    out.println(httpResponse.body());
            out.println();
	} catch(IOException ex) { 
	    ex.printStackTrace();
	} catch(InterruptedException ex) { 
	    ex.printStackTrace();
	}
    }
}

// 9. Predicate.not
class PredicateNot {
    public void print() {
	out.println("9. Predicate.not");
        Stream<Integer> integerStream = Stream.iterate(0, x -> x + 1)
                                        // .filter(x -> x % 2 == 0)
                                        // .filter(Predicate.not(x -> x % 2 == 0))
                                        .filter(Predicate.not(x -> x % 2 != 0))
                                        // .filter(Predicate.not((var x) -> x % 2 != 0)) // Local-Variable Syntax for Lambda Parameters
                                        .limit(6); // .limit(5); // NoSuchElementException
        int firstDoubleDigit = integerStream
                            .filter(i -> i >= 10)
			    .findFirst()
			    .orElseThrow(); // .limit(5); // NoSuchElementException
        out.println("First double digit number");
        out.println(firstDoubleDigit);
        out.println();
    }
}

// 8. Collection to Array
class CollectionToArray {
    public void print() {        
        List<String> arraysAsList = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        out.println("8. Collection to Array: toArray");
        out.println(arraysAsList); 

	// List objects = Arrays.asList("Alpha", 1, true);
        // out.println(objects);

        // List to Array
        String[] array = arraysAsList.toArray(String[]::new);  
        // Object[] arry = arraysAsList.toArray();
        // Integer[] arr = Arrays.asList(1, 2, 3, 4, 5).toArray(new Integer[0]);
        // Boolean[] ar = Arrays.asList(true, false, false, true, true).toArray(Boolean[]::new);
 	// Character[] a = Arrays.asList('A', 'B', 'C', 'D', 'E').toArray(Character[]::new);
	// char[] a = Arrays.asList('A', 'B', 'C', 'D', 'E').stream().map(Object::toString).collect(Collectors.joining()).toCharArray();
	// out.println(a.getClass().getName());

        // Array to List
        // out.println(Arrays.asList(array));
        // out.println(Arrays.asList(new Integer[] {6, 7, 8, 9, 0}));
        out.println();
    }
}

// 7. File Methods
class FileMethods {
    public void print() {
        out.println("7. File Methods: writeString, readString");
        try {
            Path directory = Files.writeString(Files.createTempFile(Paths.get(System.getProperty("user.dir")), "Temp-", ".txt"), "writeString and readString");
            String content = Files.readString(directory);	    
            out.println(content);
            out.println();
            directory.toFile().deleteOnExit();
	} catch(IOException ex) { 
	    ex.printStackTrace();
	}
    }
}

// 6. String Methods
class StringMethods {
    public void print() {
        out.println("6. String Methods: isBlank, lines, strip, stripLeading, stripTrailing, repeat");
        out.printf("isBlank: %b\n", "Not Blank".isBlank());
        out.println("Multi\nLines Count: " + "Multi\nLines".lines().count());
        out.println(" strip is Unicode standard to identify whitespace ".strip());
        out.println(" strip leading whitespace ".stripLeading());
        out.println(" strip trailing whitespace ".stripTrailing());
        out.println("repeat $# 5 times: " + "$#".repeat(5));
        out.println();
    }
}

// 5. Local-Variable Syntax for Lambda Parameters
class Lambda {
    interface Greetings {
        String greeting(String message);
    }
 
    interface Wishes {
        String wish(String prefix, String suffix);
    }

    public void print() {
        out.println("5. Local-Variable Syntax for Lambda Parameters");
        // Java 8
        // Greetings greet = (message) -> "Hello, " + message;
        Greetings greet = (var message) -> "Hello, " + message;
        out.println(greet.greeting("World!"));
	
        // Cannot mix 'var' and explicitly-typed parameters
        // Wishes wi = (var prefix, String suffix) -> prefix + suffix;
        // Java 8
        // Wishes wi = (prefix, suffix) -> prefix + suffix;
        Wishes wi = (var prefix, var suffix) -> prefix + suffix;
        out.println(wi.wish("Hola, ", "Java World!"));
	
	List<String> arraysAsList = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        out.println(arraysAsList);

        String arraysAsListStream = arraysAsList.stream()
					    	.map((var x) -> x.toUpperCase())
 					  	.collect(Collectors.joining(", "));
        out.println(arraysAsListStream);
        out.println();
    }
}

// 4. Nestmate Reflection
class NestmateReflection {
    public void print() {
        out.println("4. Nestmate Reflection: isNestmateOf");
        out.println(NestBasedAccessControl.class.isNestmateOf(NestBasedAccessControl.NestedClass.class)); 
	out.println(NestBasedAccessControl.NestedClass.class.isNestmateOf(NestBasedAccessControl.class));

        out.println("4. Nestmate Reflection: getNestHost");
        out.println(NestBasedAccessControl.NestedClass.class.getNestHost());

	out.println("4. Nestmate Reflection: getNestMembers");
	Set<String> nestMembers = Arrays.stream(NestBasedAccessControl.NestedClass.class.getNestMembers())
  				    .map(Class::getName)
  				    .collect(Collectors.toSet());
        out.println(nestMembers);
        out.println();
    }
}

// javap NestBasedAccessControl
// javap -c NestBasedAccessControl
// javap NestBasedAccessControl\$NestedClass
// javap -c NestBasedAccessControl\$NestedClass
class NestBasedAccessControl {
    private String message = "Accessed no more via auto-generated bridge method access$000";
    
    public void enclosingPublic() {
	out.println("enclosing public method");
    }

    private void enclosingPrivate() {
	out.println("enclosing private method");
    }

    class NestedClass {
        public void nestedPublic() {     
            out.println(message);
	    out.println("nested public method");
            enclosingPrivate();
        }

        public void nestedPublicReflection(NestBasedAccessControl ob) {  
	    try {
	        Method method = ob.getClass().getDeclaredMethod("enclosingPrivate");
	        method.invoke(ob);
	    } catch(Exception ex) { 
	        ex.printStackTrace();
	    }
        }
    }    
}

// 3. Nest-Based Access Control
class NestBasedAccessControlClient {
    public void print() {
        out.println("3. Nest-Based Access Control");

        NestBasedAccessControl enclosingInstance = new NestBasedAccessControl();
        NestBasedAccessControl.NestedClass nestedInstance = enclosingInstance.new NestedClass();
        nestedInstance.nestedPublic();
        // Java 10 // java.lang.IllegalAccessException: class NestBasedAccessControl$NestedClass cannot access a member of class NestBasedAccessControl with modifiers "private"
	nestedInstance.nestedPublicReflection(enclosingInstance);
        out.println();
    }    
}

// Output
/*
Java Version: 11.0.2

1. Launch Single-File Source-Code Programs
Only for class with main method
With main method class being the topmost class in order in the containing file
In Java 11, without any class file (of main method class or other classes in the containing file if any) found on application class path
java Java11.java
1. Launch Single-File Source-Code Programs: Class Data Sharing (CDS) off
Only for class with main method
With main method class being the topmost class in order in the containing file
In Java 11, without any class file (of main method class or other classes in the containing file if any) found on application class path
java -Xshare:off Java11.java

2. Preview Switch
javac -Xlint:preview --enable-preview -source 11 Java11.java
java --enable-preview Java11
Note: Preview Switch will not work for Single-File Source-Code Programs Launcher

3. Nest-Based Access Control
Accessed no more via auto-generated bridge method access$000
nested public method
enclosing private method
enclosing private method

4. Nestmate Reflection: isNestmateOf
true
true
4. Nestmate Reflection: getNestHost
class NestBasedAccessControl
4. Nestmate Reflection: getNestMembers
[NestBasedAccessControl, NestBasedAccessControl$NestedClass]

5. Local-Variable Syntax for Lambda Parameters
Hello, World!
Hola, Java World!
[Alpha, Beta, Gamma, Delta, Epsilon]
ALPHA, BETA, GAMMA, DELTA, EPSILON

6. String Methods: isBlank, lines, strip, stripLeading, stripTrailing, repeat
isBlank: false
Multi
Lines Count: 2
strip is Unicode standard to identify whitespace
strip leading whitespace
 strip trailing whitespace
repeat $# 5 times: $#$#$#$#$#

7. File Methods: writeString, readString
writeString and readString

8. Collection to Array: toArray
[Alpha, Beta, Gamma, Delta, Epsilon]

9. Predicate.not
First double digit number
10

10. HTTP Client
http://example.com GET
<!doctype html>
<html>
<head>
    <title>Example Domain</title>

    <meta charset="utf-8" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style type="text/css">
    body {
        background-color: #f0f0f2;
        margin: 0;
        padding: 0;
        font-family: -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;

    }
    div {
        width: 600px;
        margin: 5em auto;
        padding: 2em;
        background-color: #fdfdff;
        border-radius: 0.5em;
        box-shadow: 2px 3px 7px 2px rgba(0,0,0,0.02);
    }
    a:link, a:visited {
        color: #38488f;
        text-decoration: none;
    }
    @media (max-width: 700px) {
        div {
            margin: 0 auto;
            width: auto;
        }
    }
    </style>
</head>

<body>
<div>
    <h1>Example Domain</h1>
    <p>This domain is for use in illustrative examples in documents. You may use this
    domain in literature without prior coordination or asking for permission.</p>
    <p><a href="https://www.iana.org/domains/example">More information...</a></p>
</div>
</body>
</html>


11. Optional Methods: isEmpty
false
11. Optional Methods: orElseThrow
Alpha
true
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/