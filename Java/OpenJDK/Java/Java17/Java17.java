// Java 17
/*
# JDK 17
1 Restore Always-Strict Floating-Point Semantics #
2 Enhanced Pseudo-Random Number Generators #
3 New macOS Rendering Pipeline
4 macOS/AArch64 Port
5 Deprecate the Applet API for Removal
6 Strongly Encapsulate JDK Internals #
7 Pattern Matching for switch (Preview) #
8 Remove RMI Activation
9 Sealed Classes #
10 Remove the Experimental AOT and JIT Compiler
11 Deprecate the Security Manager for Removal
12 Foreign Function & Memory API (Incubator) #
13 Vector API (Second Incubator) #
14 Context-Specific Deserialization Filters
*/

// import static java.lang.System.out;
import java.lang.System;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;
import java.util.stream.IntStream;

// Java 17
// Default OpenJDK 17
// /usr/bin/java
// Microsoft Buildof OpenJDK 17
// $ export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/MicrosoftBuildofOpenJDK/jdk-17.0.3+7/Contents/Home/bin/":$PATH

// Java 16
// OpenJDK 16
// $ export PATH="/Users/rajaniapple/Downloads/Software/OpenJDK/JDK16.0.2/jdk-16.0.2.jdk/Contents/Home/bin/":$PATH

class Java17 {
    public static void main(String[] args) { 
    // public static void main(String... args) { 
        System.out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

	EnhancedPseudoRandomNumberGenerators pseudoRandomNumberGeneratorsEnhanced = new EnhancedPseudoRandomNumberGenerators();
	pseudoRandomNumberGeneratorsEnhanced.print();	
    }
}

class EnhancedPseudoRandomNumberGenerators {
    public void print() { 
        // Java 17
 	RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.of("Random");
        
        System.out.println("Enhanced Pseudo-Random Number Generators: RandomGenerator");
	RandomGenerator generatorRandom = factory.create();
        // nextInt() returns a pseudorandomly chosen int value
	System.out.println(generatorRandom.nextInt());

        int size = 5;
        int lowerBoundInclusive = 1;
        int upperBoundExclusive = 10;

        System.out.println("Enhanced Pseudo-Random Number Generators: RandomGeneratorFactory");
        // ints() returns an effectively unlimited stream of pseudorandomly chosen int values
        IntStream streamInt = factory.create().ints(size, lowerBoundInclusive , upperBoundExclusive).sorted();
	streamInt.forEach(x -> System.out.printf("%d ", x));
        System.out.println();
    }
}

// Output
/*
Java Version: 17.0.2

Enhanced Pseudo-Random Number Generators: RandomGenerator
1820335437
Enhanced Pseudo-Random Number Generators: RandomGeneratorFactory
1 3 5 6 7
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
https://aka.ms/download-jdk
*/