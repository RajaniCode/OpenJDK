// Java 9
/*
1. Private Interface Methods
2. Try-With-Resources on Final Variables
3. Diamond Operator for Anonymous Inner Classes
4. SafeVarargs Annotation on Private Methods
5. Collections Factory Methods
6. Optional Methods
*/

import static java.lang.System.out;
import java.io.File;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;
import java.io.IOException;  
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 6. Optional Methods
class OptionalMethods {
    public void print() {
  	Optional<String> optionalString = Optional.of("Alpha");
        // out.println(optionalString.isPresent()); // true
        // out.println(optionalString); //  Optional[Alpha]
        // out.println(optionalString.get()); // Alpha
        // optionalString.ifPresent(x -> out.println("ifPresent: " + x)); // ifPresent: Alpha
      
        // Java 9
 	out.println("6. Optional Methods: or");
	out.println(optionalString.or(() -> Optional.of("Beta")));  // Optional[Alpha]

        out.println("6. Optional Methods: ifPresentOrElse");
	optionalString.ifPresentOrElse(x -> out.println("ifPresent: " + x), () -> out.println("Not Present")); // ifPresent: Alpha

	OptionalInt optionalIntOf = OptionalInt.of(555);
	optionalIntOf.ifPresentOrElse(x -> out.println("ifPresent: " + x), () -> out.println("Not Present")); // ifPresent: 555

	OptionalDouble optionalDoubleOf = OptionalDouble.of(7.5D);
	optionalDoubleOf.ifPresentOrElse(x -> out.println("ifPresent: " + x), () -> out.println("Not Present")); // ifPresent: 7.5

	OptionalLong optionalLongOf = OptionalLong.of(9223372036854775807L);
	optionalLongOf.ifPresentOrElse(x -> out.println("ifPresent: " + x), () -> out.println("Not Present")); // ifPresent: 9223372036854775807

	out.println("6. Optional Methods: stream");
	out.println(optionalString.stream().map(String::toUpperCase).collect(Collectors.toList())); // [ALPHA]

	optionalIntOf.stream().forEach(out::println); // 555
	
	optionalDoubleOf.stream().forEach(out::println); // 7.5

	optionalLongOf.stream().forEach(out::println); // 9223372036854775807

        optionalString = Optional.empty();
	out.println(optionalString.or(() -> Optional.of("Beta")));  // Optional[Beta]        
        optionalString.ifPresentOrElse(x -> out.println("ifPresent: " + x), () -> out.println("Not Present")); // Not Present
	out.println(optionalString.stream().map(String::toUpperCase).collect(Collectors.toList())); // []
	
	optionalIntOf = OptionalInt.empty();
	optionalIntOf.stream().forEach(out::println); // Will not print

	optionalDoubleOf = OptionalDouble.empty();
	optionalDoubleOf.stream().forEach(out::println); // Will not print

	optionalLongOf = OptionalLong.empty();
	optionalLongOf.stream().forEach(out::println); // Will not print
    }
}

// 5. Collections Factory Methods
class CollectionsFactoryMethods {
    public void print() {  
        out.println("5. Collections Factory Methods: List.of");
        List<String> listOf = List.of("Alpha", "Bata", "Gamma", "Delta", "Epsilon");
        // UnsupportedOperationException
	// listOf.add("Zeta");
        for(String s : listOf) {  
            out.println(s);  
        }

	out.println("5. Collections Factory Methods: Set.of");
        Set<String> setOf = Set.of("Alpha", "Bata", "Gamma", "Delta", "Epsilon");
	// UnsupportedOperationException  
	// setOf.add("Zeta");
        for(String s : setOf) {  
            out.println(s);  
        }

        out.println("5. Collections Factory Methods: Map.of");
        Map<Integer,String> mapOf = Map.of(1, "Alpha", 2, "Beta", 3, "Gamma", 4, "Epsilon", 5, "Epsilon"); 
        // UnsupportedOperationException
       	// mapOf.put(6, "Zeta");
        for(Map.Entry<Integer, String> m : mapOf.entrySet()){    
            out.println(m.getKey() + " " + m.getValue());  
        }

        out.println("5. Collections Factory Methods: Map.ofEntries");
        Map.Entry<Integer, String> mapEntry1 = Map.entry(1, "Alpha");  
        Map.Entry<Integer, String> mapEntry2 = Map.entry(2, "Beta");
        Map.Entry<Integer, String> mapEntry3 = Map.entry(3, "Gamma");  
        Map.Entry<Integer, String> mapEntry4 = Map.entry(4, "Delta");  
        Map.Entry<Integer, String> mapEntry5 = Map.entry(5, "Epsilon");  
        // Creating Map using map entries  
        Map<Integer, String> mapOfEntries = Map.ofEntries(mapEntry1, mapEntry2, mapEntry3, mapEntry4, mapEntry5); 
        for(Map.Entry<Integer, String> m : mapOfEntries.entrySet()){    
            out.println(m.getKey() + " " + m.getValue());  
        }
        // UnsupportedOperationException
       	// mapOfEntries.put(6, "Zeta");
	out.println();
    }
}

// 4. SafeVarargs Annotation on Private Methods
// Note
// @SafeVarargs can be used with private instance methods in Java 9
// @SafeVarargs can be used with Final Methods, Static Methods, and Constructors in Pre-Java9
// @SafeVarargs can be applied only to methods that cannot be overridden
class SafeVarargsAnnotation {  
    // Applying @SafeVarargs Annotation
    @SafeVarargs  
    private void display(List<String>... products) {
        out.println("4. SafeVarargs Annotation on Private Methods");
        for (List<String> product : products) {  
            out.println(product);  
        }
        out.println();
    }
  
    public void print() {  
        List<String> lst = new ArrayList<String>();  
        lst.add("Alpha");  
        lst.add("Beta");  
        lst.add("Gamma");  
        lst.add("Delta");  
        lst.add("Epsilon");
        display(lst);
    }     
}

// 3. Diamond Operator for Anonymous Inner Classes
// Note
// Type Inference for Anonymous Inner Classes in Java 9
abstract class Abs<T> {
    abstract T show(T a, T b);  
}

class DiamondOperatorEmpty {  
    public void print() {  
        Abs<String> a = new Abs<>() { // Diamond Operator Empty - Compiler Infer Type
            String show(String a, String b) {  
                return a + b;   
            }  
        };    
        String result = a.show("3. Diamond Operator for Anonymous Inner Classes", " - Diamond Operator Empty - Compiler Infer Type");  
        out.println(result); 
    }  
}

class DiamondOperatorNotEmpty  {  
    public void print() {  
        Abs<String> a = new Abs<String>() { // Diamond Operator Not Empty  
            String show(String a, String b) {  
                return a + b;   
            }  
        };    
        String result = a.show("3. Diamond Operator for Anonymous Inner Classes", " - Diamond Operator Not Empty - Works in Java 9 - Throws Error in Pre-Java 9 - Compiler Couldn't Infer Type");  
        out.println(result);
        out.println();
    }  
}

// 2. Try-With-Resources on Final Variables
class TryWithResourcesFinal {  
    public void print() throws FileNotFoundException {
        out.println("2. Try-With-Resources on Final Variables"); 
        File textFile = new File("File.txt");
        FileOutputStream outputStreamFile = new FileOutputStream(textFile, false);  
        try(outputStreamFile) {  
             String greeting = "Try-With-Resources on Final Variables";      
                byte b[] = greeting.getBytes();       
                outputStreamFile.write(b);      
                out.println("Written to File.txt");		
        } catch(IOException e) {  
            e.printStackTrace();  
        } finally {
	    textFile.delete();
        }          
    }  
} 

// Pre-Java 9
// Try-With-Resources on Final Variables Legacy
class TryWithResourcesFinalLegacy {  
    public void print() throws FileNotFoundException {  
        File textFile = new File("FileLegacy.txt");
        try(FileOutputStream outputStreamFile = new FileOutputStream(textFile, false);) {  
             String greeting = "Try-With-Resources on Final Variables Legacy";      
                byte b[] = greeting.getBytes();       
                outputStreamFile.write(b);      
                out.println("Written to FileLegacy.txt");
        } catch(IOException e) {  
            e.printStackTrace();  
        } finally {
	    textFile.delete();
        }
        out.println();
    }  
}

// 1. Private Interface Methods
interface PrivateService {
    int getNumber();

    // Default Method
    default String getString() {
        String msg = message();
        msg +=  " via Default Method";
        return  msg;
    }

    // Private Method
    private String message() {
        return "This is the return from Private Method";
    }
}

class DefaultPrivateImplementation implements PrivateService {
    public int getNumber() {
        return 100;
    }   
}

class PrivateInterfaceMethods {
    public void print() {
        out.println("1. Private Interface Methods");
        DefaultPrivateImplementation defpvtimp = new DefaultPrivateImplementation();
        out.println(defpvtimp.getNumber());
        out.println(defpvtimp.getString());
        out.println();
    }
}

class Java9 {
    public static void main(String... args) {
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

        PrivateInterfaceMethods privateMethods = new PrivateInterfaceMethods();
        privateMethods.print();
        
        try {
            TryWithResourcesFinal tryWithResources = new TryWithResourcesFinal();
            tryWithResources.print();
        } catch(FileNotFoundException e) {
            e.printStackTrace();  
        }

        try {
             TryWithResourcesFinalLegacy tryWithResourcesLegacy = new TryWithResourcesFinalLegacy();
             tryWithResourcesLegacy.print();
        } catch(FileNotFoundException e) {  
            e.printStackTrace();  
        } 

        DiamondOperatorEmpty diamondEmpty = new DiamondOperatorEmpty();
        diamondEmpty.print();

        DiamondOperatorNotEmpty diamondNotEmpty = new DiamondOperatorNotEmpty();
        diamondNotEmpty.print();

        SafeVarargsAnnotation safeAnnotation = new SafeVarargsAnnotation();
        safeAnnotation.print();

        CollectionsFactoryMethods factoryMethodsCollections = new CollectionsFactoryMethods();
        factoryMethodsCollections.print();

	OptionalMethods methodsOptional = new OptionalMethods();
	methodsOptional.print();
    }
}

// Output
/*
Java Version: 9.0.4

1. Private Interface Methods
100
This is the return from Private Method via Default Method

2. Try-With-Resources on Final Variables
Written to File.txt
Written to FileLegacy.txt

3. Diamond Operator for Anonymous Inner Classes - Diamond Operator Empty - Compiler Infer Type
3. Diamond Operator for Anonymous Inner Classes - Diamond Operator Not Empty - Works in Java 9 - Throws Error in Pre-Java 9 - Compiler Couldn't Infer Type

4. SafeVarargs Annotation on Private Methods
[Alpha, Beta, Gamma, Delta, Epsilon]

5. Collections Factory Methods: List.of
Alpha
Bata
Gamma
Delta
Epsilon
5. Collections Factory Methods: Set.of
Bata
Delta
Alpha
Gamma
Epsilon
5. Collections Factory Methods: Map.of
2 Beta
3 Gamma
1 Alpha
4 Epsilon
5 Epsilon
5. Collections Factory Methods: Map.ofEntries
2 Beta
3 Gamma
1 Alpha
4 Delta
5 Epsilon

6. Optional Methods: or
Optional[Alpha]
6. Optional Methods: ifPresentOrElse
ifPresent: Alpha
ifPresent: 555
ifPresent: 7.5
ifPresent: 9223372036854775807
6. Optional Methods: stream
[ALPHA]
555
7.5
9223372036854775807
Optional[Beta]
Not Present
[]
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/