// Java 15
/*
1. Sealed Classes (Preview)
2. Text Blocks
3. Hidden Classes
*/

package com.example.org;

import static java.lang.System.err;
import static java.lang.System.out;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

class Java15 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

        SealedClasses classesSealed = new SealedClasses();
	classesSealed.print();

	TextBlocks blocksText = new TextBlocks();
	blocksText.print();

	HiddenClasses classesHidden = new HiddenClasses();
	classesHidden.print();
    }
}

// 3. Hidden Classes
class HiddenClasses {
    static final String UtilBase64 = "yv66vgAAADsAKgoAAgADBwAEDAAFAAYBABBqYXZhL2xhbmcvT2JqZWN0AQAGPGluaXQ+AQADKClWCAAIAQARU3RhdGljIERpc2NvdmVyeSESAAAACgwACwAMAQAXbWFrZUNvbmNhdFdpdGhDb25zdGFudHMBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwgADgEAElZpcnR1YWwgRGlzY292ZXJ5IRIAAQAKBwARAQAWY29tL2V4YW1wbGUvb3JnL0hpZGRlbgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAA5kaXNjb3ZlclN0YXRpYwEAFCgpTGphdmEvbGFuZy9TdHJpbmc7AQAPZGlzY292ZXJWaXJ0dWFsAQAKU291cmNlRmlsZQEAC0hpZGRlbi5qYXZhAQAQQm9vdHN0cmFwTWV0aG9kcw8GABsKABwAHQcAHgwACwAfAQAkamF2YS9sYW5nL2ludm9rZS9TdHJpbmdDb25jYXRGYWN0b3J5AQCYKExqYXZhL2xhbmcvaW52b2tlL01ldGhvZEhhbmRsZXMkTG9va3VwO0xqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvaW52b2tlL01ldGhvZFR5cGU7TGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL2ludm9rZS9DYWxsU2l0ZTsIACEBABNTdGF0aWMgRGlzY292ZXJ5OiABCAAjAQAUVmlydHVhbCBEaXNjb3Zlcnk6IAEBAAxJbm5lckNsYXNzZXMHACYBACVqYXZhL2xhbmcvaW52b2tlL01ldGhvZEhhbmRsZXMkTG9va3VwBwAoAQAeamF2YS9sYW5nL2ludm9rZS9NZXRob2RIYW5kbGVzAQAGTG9va3VwACEAEAACAAAAAAAFAAEABQAGAAEAEgAAAB0AAQABAAAABSq3AAGxAAAAAQATAAAABgABAAAAAwAJABQAFQABABIAAAAbAAEAAAAAAAMSB7AAAAABABMAAAAGAAEAAAAFAAkAFAAMAAEAEgAAAB8AAQABAAAAByq6AAkAALAAAAABABMAAAAGAAEAAAAJAAEAFgAVAAEAEgAAABsAAQABAAAAAxINsAAAAAEAEwAAAAYAAQAAAA0AAQAWAAwAAQASAAAAHwABAAIAAAAHK7oADwAAsAAAAAEAEwAAAAYAAQAAABEAAwAXAAAAAgAYABkAAAAOAAIAGgABACAAGgABACIAJAAAAAoAAQAlACcAKQAZ";

    public void print() {
        /*
	try {
            byte[] bytes = Files.readAllBytes(Paths.get("com/example/org/Hidden.class"));
            String encoder = Base64.getEncoder().encodeToString(bytes);
            out.println(encoder);
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
        */

        lookupHiddenClassMethods();
    }

    public void lookupHiddenClassMethods() {	
	try {
            byte[] classInBytes = Base64.getDecoder().decode(UtilBase64);

	    // Java 15 // defineHiddenClass
	    out.println("3. Hidden Classes: defineHiddenClass");
            Class<?> lookupClass = MethodHandles.lookup().defineHiddenClass(classInBytes, true, MethodHandles.Lookup.ClassOption.NESTMATE).lookupClass();
            Constructor<?> lookupConstructor = lookupClass.getConstructor();
            Object objectInstance = lookupConstructor.newInstance();
	    Class objectClass = objectInstance.getClass();

            Method[] methods = objectClass.getDeclaredMethods();

	    for (Method m : methods) {
		String methodName = m.getName();
		out.println("Method Name: " + methodName); 
		out.println("Method Modifiers: " + Modifier.toString(m.getModifiers()));
		List.of(m.getParameterTypes()).forEach(x -> out.println("Method Parameter Types: " + x.getName()));
		out.println("Method Return Type: " + m.getReturnType()); 

		try {
		    Object methodInvoke = null;
		    if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0].getName() == "java.lang.String") {
		        methodInvoke = m.invoke(objectInstance, "Hola!");
  	 	    }
		    else {
			methodInvoke = m.invoke(objectInstance);
		    }			
		    out.println("Method Invoke: " + methodInvoke);  
		    out.println();
		} catch (InvocationTargetException x) {
		    Throwable cause = x.getCause();
		    err.format("invocation of %s failed: %s%n", methodName, cause.getMessage());
		}
	    }
	
	    MethodHandle handleMethod; 
	    MethodType typeMethod;
	    MethodHandles.Lookup lookup;
    	    String output;
	
       	    out.println("Find static");
            lookup = MethodHandles.lookup();
            typeMethod = MethodType.methodType(String.class);
            handleMethod = lookup.findStatic(lookupClass, "discoverStatic", typeMethod);
            output = (String) handleMethod.invoke();
            out.println(output);
            typeMethod = MethodType.methodType(String.class, String.class);
            handleMethod = lookup.findStatic(lookupClass, "discoverStatic", typeMethod);
            output = (String) handleMethod.invoke("Hola!");
            out.println(output);

	    out.println();

	    out.println("Find virtual");
            lookup = MethodHandles.lookup();
            typeMethod = MethodType.methodType(String.class);
            handleMethod = lookup.findVirtual(lookupClass, "discoverVirtual", typeMethod);
            output = (String) handleMethod.invoke(objectInstance);
            out.println(output);
            typeMethod = MethodType.methodType(String.class, String.class);
            handleMethod = lookup.findVirtual(lookupClass, "discoverVirtual", typeMethod);
            output = (String) handleMethod.invoke(objectInstance, "Hola!");
            out.println(output);

	} catch (Throwable ex) {
	    ex.printStackTrace();
	}
    }
}

// 2. Text Blocks
class TextBlocks {
    public void print() {
	out.println("2. Text Blocks");

	out.println("Two-dimensional block of text:");
 	String twoDimensionalTextBlock =
	    """
		Developer(s): Oracle Corporation, OpenJDK and Java Community, Red Hat, Azul Systems, IBM, Microsoft, Amazon, Apple Inc, SAP SE
		Initial release: May 8, 2007; 14 years ago
		Repository: github.com/openjdk/jdk.git
		Written in: C++ and Java
		Operating system: Linux, FreeBSD, macOS, Microsoft Windows, OpenIndiana; several other ports in progress
		Type: Java platform
		License: GPLv2 with linking exception
		Website: openjdk.java.net
  	    """;		
	out.println(twoDimensionalTextBlock);

	out.println("HTML:");
 	twoDimensionalTextBlock = 
	    """ 
		<html>
		    <head>
		        <title>Page Title</title>
		    </head>
		    <body>
		        <h1>Heading</h1>
		        <p>Paragraph.</p>
		    </body>
		</html>
  	    """;		
	out.println(twoDimensionalTextBlock);

	out.println("JSON:");
 	twoDimensionalTextBlock = 
	    """ 
		{
		    "Programming Language": "Java",
		    "Version": "13.0.2"	     
		}
  	    """;		
	out.println(twoDimensionalTextBlock);

	out.println("SQL:");
 	twoDimensionalTextBlock = 
	    """ 
		SELECT COUNTRY FROM ISD
		WHERE CODE = '1'
  	    """;		
	out.println(twoDimensionalTextBlock);

	out.println("Programming Language:");
 	twoDimensionalTextBlock = 
	    """
		const http = require('http');
		const hostname = '127.0.0.1';
		const port = 3000;
		const server = http.createServer((req, res) => {
		    res.statusCode = 200;
		    res.setHeader('Content-Type', 'text/plain');
		    res.end('Hello, World!');
		});
		server.listen(port, hostname, () => {
		    console.log(`Server running at http://${hostname}:${port}/`);
		});
  	    """;		
	out.println(twoDimensionalTextBlock);
	
	out.println("Formatted");
	twoDimensionalTextBlock =
	    """
		Formats: 
		String: %s
		Character: %c
		Integer: %d
		Long: %d
		Float: %.2f
		Double: %.2f
		Boolean: %b
		Octal: %o
		Hexadecimal: %x
		Scientific: %e
  	    """.formatted("Alpha", (char)126, 2147483647, 9223372036854775807L, 5.6789F, 66.789D, true, 8, 16, Math.pow(2, 1023));		
	out.println(twoDimensionalTextBlock);   
    }
}

/*
// 1. Sealed Classes (Preview)
// Java 15 Preview, Java 16 Preview
// javac -Xlint:preview --enable-preview -source 15 com/example/org/Java15.java
// javac -Xlint:preview --enable-preview -source 16 com/example/org/Java15.java // Check for lines to be commented out for -source 16
// java --enable-preview com.example.org.Java15
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
sealed class SealedClass {    
    public void print() {
	out.println("1. Sealed Classes (Preview): sealed");
	out.println("sealed class must have subclasses");
	out.println("sealed class subclass(es) must be sealed (with further inheritance), non-sealed or final");
	out.println("sealed class may not be abstract");
    }
}

// sealed, non-sealed or final modifiers expected
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
sealed class SealedClassExtender extends SealedClass {
    @Override
    public void print() {
	out.println("sealed SealedClassExtender");
    }
}

// sealed, non-sealed or final modifiers expected
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class SealedClassFutherExtender extends SealedClassExtender {
    @Override
    public void print() {
	out.println("1. Sealed Classes (Preview): non-sealed");
	out.println("non-sealed SealedClassFutherExtender");
    }
}

interface MethodInterface { 
    default void printDefaultMethodInterface() {
	out.println("DefaultMethodInterface");
    }
}

interface AnotherMethodInterface { 
    default void printDefaultAnotherMethodInterface() {
	out.println("DefaultAnotherMethodInterface");
    }
}

// sealed, non-sealed or final modifiers expected
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
sealed class SealedClassFuthermoreExtenderPermit 
    extends SealedClassFutherExtender
    implements MethodInterface, AnotherMethodInterface
    permits PermittedClassExtender, AnotherPermittedClassExtender {
    @Override
    public void print() {
	out.println("1. Sealed Classes (Preview): permits");
	out.println("sealed SealedClassFuthermoreExtenderPermit");
	printDefaultMethodInterface();
	printDefaultAnotherMethodInterface();
    }
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
sealed class PermittedClassExtender extends SealedClassFuthermoreExtenderPermit {
    @Override
    public void print() {
	out.println("sealed PermittedClassExtender");
    }
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class AnotherPermittedClassExtender extends SealedClassFuthermoreExtenderPermit {
    @Override
    public void print() {
	out.println("non-sealed AnotherPermittedClassExtender");
    }
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class FurtherExtender extends PermittedClassExtender {
    @Override
    public void print() {
	out.println("non-sealed FurtherExtender");
    }
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
sealed interface SealedInterface { 
    default void printDefaultSealedInterface() {
	out.println("1. Sealed Classes (Preview): Sealed Interface");
	out.println("SealedInterface");
    }
}

// sealed or non-sealed modifiers expected
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed interface SealedInterfaceExtender extends SealedInterface { 
    default void printDefaultSealedInterfaceExtender() {
	out.println("SealedInterfaceExtender");
    }
}

// can be sealed, non-sealed, final
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
// error: non-sealed modifier not allowed here // when implementing SealedInterfaceExtender without SealedInterface
// non-sealed class SealedInterfaceImplementer implements SealedInterfaceExtender {
// sealed class SealedInterfaceImplementer implements SealedInterfaceExtender {
non-sealed class SealedInterfaceImplementer implements SealedInterface, SealedInterfaceExtender {
    public void print() {
	out.println("non-sealed SealedInterfaceImplementer");
	printDefaultSealedInterface();
	printDefaultSealedInterfaceExtender();
    }
}
// non-sealed class SealedInterfaceImplementerExtender extends SealedInterfaceImplementer { }

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
// permitted classes and permitted interfaces order does not matter for 
sealed interface SealedInterfacePermit
    permits PermittedClassImplementer, AnotherPermittedClassImplementer, PermittedInterfaceExtender, AnotherPermittedInterfaceExtender { 
    default void printDefaultSealedInterfacePermit() {
	out.println("1. Sealed Classes (Preview): interface: permit");
	out.println("SealedInterfacePermit");
    }
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed interface PermittedInterfaceExtender extends SealedInterfacePermit { }
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed interface AnotherPermittedInterfaceExtender extends SealedInterfacePermit { }

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class PermittedClassImplementer implements SealedInterfacePermit { }
// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class AnotherPermittedClassImplementer implements SealedInterfacePermit { }

class SealedClassMethods {
    public void print() {
	out.println("1. Sealed Classes (Preview): Methods: isSealed");
	SealedClass classSealed = new SealedClass();
	// warning: [preview] isSealed() is an API that is part of a preview feature
        out.println(classSealed.getClass().isSealed());
        classSealed = new SealedClassFutherExtender();
	// warning: [preview] isSealed() is an API that is part of a preview feature
        out.println(classSealed.getClass().getSuperclass().isSealed());

	out.println("1. Sealed Classes (Preview): Methods: getPermittedSubclasses");
	classSealed = new SealedClassFuthermoreExtenderPermit();
	// warning: [preview] permittedSubclasses() is an API that is part of a preview feature
	// javac -Xlint:preview --enable-preview -source 16 com/example/org/Java15.java // Comment out the following line for -source 16
        List.of(classSealed.getClass().permittedSubclasses()).forEach(out::println);
	classSealed = new PermittedClassExtender();
	// warning: [preview] permittedSubclasses() is an API that is part of a preview feature
        // javac -Xlint:preview --enable-preview -source 16 com/example/org/Java15.java // Comment out the following line for -source 16
        List.of(classSealed.getClass().getSuperclass().permittedSubclasses()).forEach(out::println);

	out.println("1. Sealed Classes (Preview): interface: Methods: isSealed");
	SealedInterface interfaceSealed = new SealedInterfaceImplementer();
	// warning: [preview] isSealed() is an API that is part of a preview feature
        out.println(interfaceSealed.getClass().isSealed());
	// warning: [preview] isSealed() is an API that is part of a preview feature
	List.of(interfaceSealed.getClass().getInterfaces()).forEach(x -> out.println(x.isSealed()));

	SealedInterfaceExtender extenderSealedInterface = new SealedInterfaceImplementer();
	// warning: [preview] isSealed() is an API that is part of a preview feature
        out.println(extenderSealedInterface.getClass().isSealed());
	// warning: [preview] isSealed() is an API that is part of a preview feature
	List.of(extenderSealedInterface.getClass().getInterfaces()).forEach(x -> out.println(x.isSealed()));

	out.println("1. Sealed Classes (Preview): interface: Methods: getPermittedSubclasses");
	SealedInterfacePermit permitSealedInterface = new AnotherPermittedClassImplementer();
	// warning: [preview] permittedSubclasses() is an API that is part of a preview feature
	// javac -Xlint:preview --enable-preview -source 16 com/example/org/Java15.java // Comment out the following line for -source 16
        // List.of(permitSealedInterface.getClass().getInterfaces()).forEach(x -> List.of(x.permittedSubclasses()).forEach(out::println));     
    }	
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
abstract sealed class AbstractSealedClass {
    public AbstractSealedClass() { 
        out.println("abstract sealed class called via super"); 
    }

    public AbstractSealedClass(String overload) { 
        out.println("abstract sealed class called via super - " + overload); 
    }

    public abstract void print();
}

// warning: [preview] sealed classes are a preview feature and may be removed in a future release.
non-sealed class AbstractSealedClassExtender extends AbstractSealedClass {
    public AbstractSealedClassExtender() { 
        super();
    }

    public AbstractSealedClassExtender(String overload) { 
        super(overload);
    }

    @Override
    public void print() {
	out.println("abstract methods must be overridden at all first levels of inheritance");
    }
}
*/

// 1. Sealed Classes (Preview)
class SealedClasses {
    public void print() {
 
        out.println("1. Sealed Classes (Preview)");
        /*       
	SealedClass classSealed = new SealedClass();
	classSealed.print();
	classSealed = new SealedClassExtender();
	classSealed.print();
	classSealed = new SealedClassFutherExtender();
	classSealed.print();
	classSealed = new SealedClassFuthermoreExtenderPermit();
	classSealed.print();
	classSealed = new PermittedClassExtender();
	classSealed.print();
	classSealed = new AnotherPermittedClassExtender();
	classSealed.print();
	classSealed = new FurtherExtender();
	classSealed.print();

	SealedClassMethods methodsSealedClass = new SealedClassMethods();
	methodsSealedClass.print();

	AbstractSealedClass sealedClassAbstract = new AbstractSealedClassExtender();
	sealedClassAbstract.print();
 	sealedClassAbstract = new AbstractSealedClassExtender("Overloaded or Parameterized");
	sealedClassAbstract.print();
	*/
	out.println();
    }
}

// $ mkdir -p "com/example/org" # > mkdir "com\example\org"
// $ touch com/example/org/Java15.java # > copy NUL com\example\org\Java15.java # OR > copy /b NUL com\example\org\Java15.java
// $ cat com/example/org/Java15.java # OR $ tail com/example/org/Java15.java # > type com\example\org\Java15.java
// $ touch com/example/org/Hidden.java # > copy NUL com\example\org\Hidden.java # OR > copy /b NUL com\example\org\Hidden.java
// $ cat com/example/org/Hidden.java # OR $ tail com/example/org/Hidden.java # > type com\example\org\Hidden.java
// javac com/example/org/Hidden.java
/*
package com.example.org;

public class Hidden {
    public static String discoverStatic() {
        return "Static Discovery!";
    }

    public static String discoverStatic(String input) {
        return "Static Discovery: " + input;
    }

    public String discoverVirtual() {
        return "Virtual Discovery!";
    }

    public String discoverVirtual(String input) {
        return "Virtual Discovery: " + input;
    }
}
*/

// Output excluding Preview
// javac com/example/org/Java15.java
// java com.example.org.Java15
// OR
// java com/example/org/Java15.java
/*
Java Version: 15.0.2

1. Sealed Classes (Preview)

2. Text Blocks
Two-dimensional block of text:
Developer(s): Oracle Corporation, OpenJDK and Java Community, Red Hat, Azul Systems, IBM, Microsoft, Amazon, Apple Inc, SAP SE
Initial release: May 8, 2007; 14 years ago
Repository: github.com/openjdk/jdk.git
Written in: C++ and Java
Operating system: Linux, FreeBSD, macOS, Microsoft Windows, OpenIndiana; several other ports in progress
Type: Java platform
License: GPLv2 with linking exception
Website: openjdk.java.net

HTML:
<html>
    <head>
        <title>Page Title</title>
    </head>
    <body>
        <h1>Heading</h1>
        <p>Paragraph.</p>
    </body>
</html>

JSON:
{
    "Programming Language": "Java",
    "Version": "13.0.2"
}

SQL:
SELECT COUNTRY FROM ISD
WHERE CODE = '1'

Programming Language:
const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;
const server = http.createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('Hello, World!');
});
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});

Formatted
Formats:
String: Alpha
Character: ~
Integer: 2147483647
Long: 9223372036854775807
Float: 5.68
Double: 66.79
Boolean: true
Octal: 10
Hexadecimal: 10
Scientific: 8.988466e+307

3. Hidden Classes: defineHiddenClass
Method Name: discoverStatic
Method Modifiers: public static
Method Parameter Types: java.lang.String
Method Return Type: class java.lang.String
Method Invoke: Static Discovery: Hola!

Method Name: discoverStatic
Method Modifiers: public static
Method Return Type: class java.lang.String
Method Invoke: Static Discovery!

Method Name: discoverVirtual
Method Modifiers: public
Method Return Type: class java.lang.String
Method Invoke: Virtual Discovery!

Method Name: discoverVirtual
Method Modifiers: public
Method Parameter Types: java.lang.String
Method Return Type: class java.lang.String
Method Invoke: Virtual Discovery: Hola!

Find static
Static Discovery!
Static Discovery: Hola!

Find virtual
Virtual Discovery!
Virtual Discovery: Hola!
*/

// Output including Preview
// javac -Xlint:preview --enable-preview -source 15 com/example/org/Java15.java
// java --enable-preview com.example.org.Java15
/*
Java Version: 15.0.2

1. Sealed Classes (Preview)
1. Sealed Classes (Preview): sealed
sealed class must have subclasses
sealed class subclass(es) must be sealed (with further inheritance), non-sealed or final
sealed class may not be abstract
sealed SealedClassExtender
1. Sealed Classes (Preview): non-sealed
non-sealed SealedClassFutherExtender
1. Sealed Classes (Preview): permits
sealed SealedClassFuthermoreExtenderPermit
DefaultMethodInterface
DefaultAnotherMethodInterface
sealed PermittedClassExtender
non-sealed AnotherPermittedClassExtender
non-sealed FurtherExtender
1. Sealed Classes (Preview): Methods: isSealed
true
true
1. Sealed Classes (Preview): Methods: getPermittedSubclasses
ClassDesc[PermittedClassExtender]
ClassDesc[AnotherPermittedClassExtender]
ClassDesc[PermittedClassExtender]
ClassDesc[AnotherPermittedClassExtender]
1. Sealed Classes (Preview): interface: Methods: isSealed
false
true
false
false
true
false
1. Sealed Classes (Preview): interface: Methods: getPermittedSubclasses
abstract sealed class called via super
abstract methods must be overridden at all first levels of inheritance
abstract sealed class called via super - Overloaded or Parameterized
abstract methods must be overridden at all first levels of inheritance

2. Text Blocks
Two-dimensional block of text:
Developer(s): Oracle Corporation, OpenJDK and Java Community, Red Hat, Azul Systems, IBM, Microsoft, Amazon, Apple Inc, SAP SE
Initial release: May 8, 2007; 14 years ago
Repository: github.com/openjdk/jdk.git
Written in: C++ and Java
Operating system: Linux, FreeBSD, macOS, Microsoft Windows, OpenIndiana; several other ports in progress
Type: Java platform
License: GPLv2 with linking exception
Website: openjdk.java.net

HTML:
<html>
    <head>
        <title>Page Title</title>
    </head>
    <body>
        <h1>Heading</h1>
        <p>Paragraph.</p>
    </body>
</html>

JSON:
{
    "Programming Language": "Java",
    "Version": "13.0.2"
}

SQL:
SELECT COUNTRY FROM ISD
WHERE CODE = '1'

Programming Language:
const http = require('http');
const hostname = '127.0.0.1';
const port = 3000;
const server = http.createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('Hello, World!');
});
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});

Formatted
Formats:
String: Alpha
Character: ~
Integer: 2147483647
Long: 9223372036854775807
Float: 5.68
Double: 66.79
Boolean: true
Octal: 10
Hexadecimal: 10
Scientific: 8.988466e+307

3. Hidden Classes: defineHiddenClass
Method Name: discoverStatic
Method Modifiers: public static
Method Parameter Types: java.lang.String
Method Return Type: class java.lang.String
Method Invoke: Static Discovery: Hola!

Method Name: discoverStatic
Method Modifiers: public static
Method Return Type: class java.lang.String
Method Invoke: Static Discovery!

Method Name: discoverVirtual
Method Modifiers: public
Method Return Type: class java.lang.String
Method Invoke: Virtual Discovery!

Method Name: discoverVirtual
Method Modifiers: public
Method Parameter Types: java.lang.String
Method Return Type: class java.lang.String
Method Invoke: Virtual Discovery: Hola!

Find static
Static Discovery!
Static Discovery: Hola!

Find virtual
Virtual Discovery!
Virtual Discovery: Hola!
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/