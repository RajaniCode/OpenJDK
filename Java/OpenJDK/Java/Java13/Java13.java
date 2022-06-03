// Java 13
/*
1. Text Blocks (Preview)
2. Switch Expressions (Second Preview)
3. FileSystems Methods
*/

import java.io.File;
import java.io.IOException;
import java.lang.ClassLoader;
import java.lang.Math;
import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static java.lang.System.out;

class Java13 {
    public static void main(String... args) { 
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

	TextBlocks blocksText = new TextBlocks();
	blocksText.print();

	SwitchExpressions expressionsSwitch = new SwitchExpressions();
        expressionsSwitch.print();
	
	FileSystemsMethods methodsFileSystems = new FileSystemsMethods();
	methodsFileSystems.print();	
    }
}

class FileSystemsMethods {

    public void print() {
	out.println("3. FileSystems Methods");

        try {
	    Path currentPath = Paths.get(".");
	    Path filePath = currentPath.resolve("Notepad.jar");
	    Path pathFileName = filePath.getFileName();

	    Map<String, String> mapHashMap = new HashMap<String, String>();

            ClassLoader loader = Thread.currentThread().getContextClassLoader();

	    FileSystem systemFile = FileSystems.newFileSystem(pathFileName);
	    systemFile = FileSystems.newFileSystem(pathFileName, mapHashMap);
	    systemFile = FileSystems.newFileSystem(pathFileName, mapHashMap, loader);

	    // pathFileName.toFile().deleteOnExit();

        } catch(IOException ex) { 
	    ex.printStackTrace();
	}
    }
}

// 2. Switch Expressions (Second Preview)
class SwitchExpressions {
    public void switchExpression(Character choice) {
	out.println("2. Switch Expressions (Second Preview)");

        /*
        // Java 14
        // Java 13 Preview  
        // javac -Xlint:preview --enable-preview -source 13 Java13.java
        // java --enable-preview Java13
        // warning: [preview] text blocks are a preview feature and may be removed in a future release.
	Character c = switch (choice) {
	    case 'R', 'G', 'B':
	        yield choice;
	    default:
	        yield '0';		
	};
	out.println("RGB: " + c);
        */

	out.println();
    }

    public void print() {
	switchExpression('R');
    }
}

// 1. Text Blocks (Preview)
class TextBlocks {
    public void print() {
	out.println("1. Text Blocks (Preview)");

	/*
        // Java 15
        // Java 13 Preview, Java 14 Preview    
        // javac -Xlint:preview --enable-preview -source 13 Java13.java
        // javac -Xlint:preview --enable-preview -source 14 Java13.java
        // java --enable-preview Java13
        // warning: [preview] text blocks are a preview feature and may be removed in a future release.
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
	// warning: [removal] formatted(Object...) in String has been deprecated and marked for removal
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
	*/
   
	out.println();
    }
}

// Output
/*
Java Version: 13.0.2

1. Text Blocks (Preview)

2. Switch Expressions (Second Preview)

3. FileSystems Methods
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/