// Java 8
/*
1. Lambda Expression
2. Method References
3. Default Interface Methods
4. Static Interface Methods
5. Functional Interfaces
6. Stream
7. Optional
*/

import static java.lang.System.out;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 7. Optional
class UtilOptional {
    public void print() {
        out.println("7. Optional");
        Optional<String> optionalString = Optional.of("Alpha");
        out.println(optionalString.isPresent()); // true
        out.println(optionalString); //  Optional[Alpha]
        out.println(optionalString.get()); // Alpha
        optionalString.ifPresent(x -> out.println("ifPresent: " + x)); // ifPresent: Alpha
	
        optionalString = Optional.empty();
        out.println(optionalString.isPresent()); // false
        out.println(optionalString.orElse("Beta")); // Beta
        out.println(optionalString.isPresent()); // false
        out.println(optionalString); // Optional.empty
	// NoSuchElementException: No value present
        // out.println(optionalString.get());
        optionalString.ifPresent(x -> out.println("ifPresent: " + x)); // Will not print

        out.println(optionalString.orElseGet(() -> "Gamma")); // Gamma
        out.println(optionalString.isPresent()); // false
        out.println(optionalString); // Optional.empty
	// NoSuchElementException: No value present
        // out.println(optionalString.get());
        optionalString.ifPresent(x -> out.println("ifPresent: " + x));  // Will not print

	OptionalInt optionalIntOf = OptionalInt.of(555);
        out.println(optionalIntOf.isPresent()); // true
        out.println(optionalIntOf); // OptionalInt[555]
	out.println(optionalIntOf.getAsInt()); // 555
	optionalIntOf.ifPresent( x -> out.println("ifPresent: " + x)); // ifPresent: 555

	optionalIntOf = OptionalInt.empty();
        out.println(optionalIntOf.isPresent()); // false
        out.println(optionalIntOf); // OptionalInt.empty
	// NoSuchElementException: No value present
	// out.println(optionalIntOf.getAsInt());
	optionalIntOf.ifPresent(x -> out.println("ifPresent: " + x));  // Will not print

	OptionalDouble optionalDoubleOf = OptionalDouble.of(7.5D);        
        out.println(optionalDoubleOf.isPresent()); // true
        out.println(optionalDoubleOf); // OptionalDouble[7.5]
	out.println(optionalDoubleOf.getAsDouble()); // 7.5
	optionalDoubleOf.ifPresent( x -> out.println("ifPresent: " + x)); // ifPresent: 1

	optionalDoubleOf = OptionalDouble.empty();
        out.println(optionalDoubleOf.isPresent()); // false
        out.println(optionalDoubleOf); // OptionalDouble.empty	
	// NoSuchElementException: No value present
	// out.println(optionalDoubleOf.getAsDouble());
	optionalDoubleOf.ifPresent(x -> out.println("ifPresent: " + x));  // Will not print

	OptionalLong optionalLongOf = OptionalLong.of(9223372036854775807L);
        out.println(optionalLongOf.isPresent()); // true
        out.println(optionalLongOf); // OptionalLong[9223372036854775807]
	out.println(optionalLongOf.getAsLong()); // 9223372036854775807
	optionalLongOf.ifPresent( x -> out.println("ifPresent: " + x)); // ifPresent: 9223372036854775807

	optionalLongOf = OptionalLong.empty();
        out.println(optionalLongOf.isPresent()); // false
        out.println(optionalLongOf); // OptionalLong.empty
	// NoSuchElementException: No value present
	// out.println(optionalLongOf.getAsLong());
	optionalLongOf.ifPresent(x -> out.println("ifPresent: " + x));  // Will not print
    }
}

// 6. Stream
class UtilStream {
    public void print() {
        out.println("6. Stream");
        Person per = new Person();
        List<Person> personList = per.getPersons();
        out.println("Person List");
        personList.forEach(p -> out.printf("Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", p.getId(), p.getName(), p.getGender().toString(), p.getDob().toString(), p.getIncome()));

        out.println("Filtering by Stream");
        List<Person> listPerson = personList.stream().filter(p -> p.getIncome() > 50000.0d).collect(Collectors.toList());
        out.println("Person Income > 50000");
        for(Person p : listPerson) {
            out.printf("Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", p.getId(), p.getName(), p.getGender().toString(), p.getDob().toString(), p.getIncome());
        }

        out.println("Stream Iterating");
        out.println("Alphabet");
        Stream.iterate(65, x -> x + 1)
        .filter(x -> (x < 91  | x > 96))
        .limit(52)
        // .forEach(x -> out.printf("%c ", x));
        .forEach(x -> out.print(String.format("%c ", x)));
        out.println();

        out.println("Filtering and Iterating by Stream");
        out.println("Person Income > 50000");
        personList.stream().filter(p -> p.getIncome() > 50000.0d).forEach(p -> out.printf("Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", p.getId(), p.getName(), p.getGender().toString(), p.getDob().toString(), p.getIncome()));

        out.println("Stream reduce() Method");
        double total = personList.stream()  
                    .map(p -> p.getIncome())  
                    .reduce(0.0d, (x, y) -> x + y);
        out.printf("Sum Person Income: %.2f\n", total);

        out.println("Stream reduce() Method by referring sum method of Double class"); 
        total = personList.stream()  
                .map(p -> p.getIncome())  
                .reduce(0.0d, Double::sum);
        out.printf("Sum Person Income: %.2f\n", total);

        out.println("Stream Collectors summingDouble() Method");
        total = personList.stream().collect(Collectors.summingDouble(p -> p.getIncome()));  
        out.printf("Sum Person Income: %.2f\n", total);

        out.println("Stream Max, Min, and Count");
        out.println("Stream max() method");  
        Person personIncomeMax = personList.stream().max((x, y) -> x.getIncome() > y.getIncome() ? 1: -1).get(); 
        out.printf("Maximum Person Income: %.2f\n", personIncomeMax.getIncome());

        out.println("Stream min() method");  
        Person personIncomeMin = personList.stream().min((x, y) -> x.getIncome() > y.getIncome() ? 1: -1).get(); 
        out.printf("Minimum Person Income: %.2f\n", personIncomeMin.getIncome());

        out.println("Stream count() Method");
        long counter = personList.stream().filter(p -> p.getIncome() > 50000.0d).count();  
        out.printf("Count Person Income > 50000: %d\n", counter);

        out.println("List Stream To Set");
        out.println("Person Set");
        personList.stream().collect(Collectors.toSet()) .forEach(p -> out.printf("Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", p.getId(), p.getName(), p.getGender().toString(), p.getDob().toString(), p.getIncome()));

        out.println("List Stream into Map");
        Map<Long, Person> personIdMap = personList.stream().collect(Collectors.toMap(p->p.getId(), p->p));
        out.println("Person Id - Map Entry");

        for(Entry<Long, Person> mapEntry : personIdMap.entrySet()) {
            Long id = mapEntry.getKey();
            Person pr = mapEntry.getValue();
            out.printf("Id: %d - Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", id, pr.getId(), pr.getName(), pr.getGender().toString(), pr.getDob().toString(), pr.getIncome());
        }

        out.println("Person Id - Map");
        personList.stream().collect(Collectors.toMap(p->p.getId(), p->p)).forEach((x, y) -> out.printf("Id: %d - Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", x, y.getId(), y.getName(), y.getGender().toString(), y.getDob().toString(), y.getIncome()));

        out.println("Stream Method Reference by referring getIncome method of Person class");
        out.println("Person Income List");
        personList.stream().map(Person::getIncome).collect(Collectors.toList()).forEach(x -> out.printf("%.2f\n", x));

        // Note
        // Method Reference by referring getPersons method of Person class will Map to List<List<Person>>
        out.println("Stream Method Reference by referring getPerson method of Person class");
        out.println("Person List ");
        personList.stream().map(Person::getPerson).collect(Collectors.toList()) .forEach(p -> out.printf("Id: %d, Name: %s, Gender: %s, Date of birth: %s, Income: %.2f\n", p.getId(), p.getName(), p.getGender().toString(), p.getDob().toString(), p.getIncome()));

        out.println("Parallel Stream");
        String namesParallel = personList.parallelStream().filter(Person::isMale).map(Person::getName).collect(Collectors.joining(", "));
        out.println(namesParallel);

        out.println("Mixed Mode: Serial and Parallel Streams");
        String namesMixed = personList.stream().filter(Person::isMale).parallel().map(Person::getName).collect(Collectors.joining(", "));
        out.println(namesMixed);
        out.println();

        out.println("5 Collections Factory Methods: Stream.of");
	Set<String> streamOf = Stream.of("Alpha", "Bata", "Gamma", "Delta", "Epsilon")
	                             .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
	// UnsupportedOperationException  
	// streamOf.add("Zeta");
        for(String s : streamOf) {  
            out.println(s);  
        }
	out.println();
    } 
}

class Person {
    public static enum Gender { MALE, FEMALE }

    private long id;
    private String name;
    private Gender gen;
    private LocalDate dob;
    private double income;

    public Person() { }

    public Person(long id, String name, Gender gen, LocalDate dob, double income) {
        this.id = id;
        this.name = name;
        this.gen = gen;
        this.dob = dob;
        this.income = income;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gen;
    }

    public boolean isMale() {
        return this.gen == Gender.MALE;
    }

    public boolean isFemale() {
        return this.gen == Gender.FEMALE;
    }

    public void setGender(Gender gen) {
        this.gen = gen;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Person getPerson() {
        Person per = new Person(id, name, gen, dob, income);
        return per;
    }

    public List<Person> getPersons() {
        Person arete = new Person(1L, "Arete", Gender.MALE, LocalDate.of(1990, Month.JANUARY, 1), 10000.0d);
        Person ersa = new Person(2L, "Ersa", Gender.FEMALE, LocalDate.of(1989, Month.FEBRUARY, 2), 20000.0d);
        Person hera = new Person(3L, "Hera", Gender.FEMALE, LocalDate.of(1988, Month.MARCH, 3), 30000.0d);
        Person elpis = new Person(4L, "Elpis", Gender.MALE, LocalDate.of(1987, Month.APRIL, 4), 40000.0d);
        Person soter = new Person(5L, "Soter", Gender.MALE, LocalDate.of(1986, Month.MAY, 5), 50000.0d);
        Person nyx = new Person(6L, "Nyx", Gender.FEMALE, LocalDate.of(1985, Month.JUNE, 6), 60000.0d);
        Person aura = new Person(7L, "Aura", Gender.FEMALE, LocalDate.of(1984, Month.JULY, 7), 70000.0d);
        Person selene = new Person(8L, "Selene", Gender.FEMALE, LocalDate.of(1983, Month.AUGUST, 8), 80000.0d);
        Person gelos = new Person(9L, "Gelos", Gender.MALE, LocalDate.of(1982, Month.SEPTEMBER, 9), 90000.0d);
        Person techne = new Person(10L, "Techne", Gender.MALE, LocalDate.of(1981, Month.OCTOBER, 10), 1000000.0d);
        List<Person> persons = Arrays.asList(arete, ersa, hera, elpis, soter, nyx, aura, selene, gelos, techne);
        return persons;
   }

   @Override
   public String toString() {
        String str = String.format("(%d, %s, %s, %s, %.2f)", id, name, gen, dob, income);
        return str;
    }
}

// 5. Functional Interfaces
interface FunctionalNumber {
    double getNumber();
}

class FunctionalInterfaces {
    private static int sum = 0;
    public void print() {
        out.println("5. Functional Interfaces");
        FunctionalNumber number;
        number = () -> 123.45D;
        out.println("Number: " + number.getNumber());
        number = () -> Math.random() * 100;
        out.println("Random Number: " + number.getNumber());
        // int sum = 100;
        List<Integer> listPrint = new ArrayList<Integer>();
        listPrint.add(555);
        listPrint.add(77);
        listPrint.add(3);

        out.println("List");
        listPrint.forEach(out::println);
        out.println("Sum");
        // listPrint.forEach(n -> out.printf("%d ", (sum += n)));	
        // listPrint.forEach(n -> out.printf("%d\n", (sum += n)));
        listPrint.forEach(n -> out.printf("%d%n", (sum += n)));

        List<Integer> lst = Arrays.asList(555, 77, 3); 	
        out.println("forEach printf:");       
        lst.forEach(i -> out.printf("%d ", i));  // lst.forEach(i -> System.out.printf("%d, ", i));
        out.println();
        out.println("forEach format:");
        lst.forEach(i -> out.format("%d ", i));  // lst.forEach(i -> System.out.format("%d, ", i));	
        out.println();
        out.println("format:");
        int i = 123;
        int j = 456;
        out.format("i = %d, j = %d",  i, j); 
        out.println();
        out.println("printf:");
        out.printf("i = %d, j = %d",  i, j);
        out.println("\n");
    }
}

// Predicate
// Predicate exists from before Java 8
class PredicateInterface {
    public void print() {
        out.println("Note: Predicate predates Java 8");
        List<Integer> lst = Arrays.asList(1, 2, 3, 4, 5);
        out.println("Integers");
	evaluate(lst, n -> true);
	
	out.println("Odd Integers");
        evaluate(lst, n -> n % 2 != 0 );
	out.println("Even Integers");
	evaluate(lst, n -> n % 2 == 0);

        List<Integer> ilst = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // Predicate<Integer> predicate = n -> true
        // n is passed as parameter to test method of Predicate interface
        // Test method will always return true no matter what value n has
        out.println("Print all numbers:");
        // pass n as parameter
        eval(ilst, n->true);
        // Predicate<Integer> predicate1 = n -> n%2 == 0
        // n is passed as parameter to test method of Predicate interface
        // Test method will return true if n%2 comes to be zero
        out.println("Print even numbers:");
        eval(ilst, n-> n%2 == 0 );
        // Predicate<Integer> predicate2 = n -> n > 3
        // n is passed as parameter to test method of Predicate interface
        // Test method will return true if n is greater than 3
        out.println("Print numbers greater than 3:");
        eval(ilst, n-> n > 3 );
        out.println();
    }

    private void evaluate(List<Integer> lst, Predicate<Integer> pred) {
        for(int i: lst) {
            if(pred.test(i)) {
                out.println(i);
            }
        }
    }

    private void eval(List<Integer> list, Predicate<Integer> predicate) {
        for(Integer n: list) {
            if(predicate.test(n)) {
                out.println(n + " ");
            }
        }
    }
}

// 4. Static Interface Methods
interface StaticService {
    int getNumber();
    // Static Method In Interface
    static String getString() {
        return "This is the return from Static Method In Interface";
    }
}

class StaticImplementation implements StaticService {
    public int getNumber() {
        return 100;
    }   
}

class StaticInterfaceMethods {
    public void print() {
        out.println("4. Static Interface Methods");
        StaticImplementation statimp = new StaticImplementation();
        out.println(statimp.getNumber());
        out.println(StaticService.getString());
        out.println();
    }
}

// 3. Default Interface Methods
interface DefaultService {
    int getNumber();
    // Default Method
    default String getString() {
        return "This is the return from Default Interface Method";
    }
}

class DefaultImplementation implements DefaultService {
    public int getNumber() {
        return 100;
    }   
}

class DefaultInterfaceMethods {
    public void print() {
        out.println("3. Default Interface Methods");
        DefaultImplementation implementationDefault = new DefaultImplementation();
        out.println(implementationDefault.getNumber());
        out.println(implementationDefault.getString());
        out.println();
    }
}

// 2. Method References
interface ReverseFunc {
    String func(String str);
}

class StringReverse {
    // static String reverse(String str) { 
    String reverse(String str) {
        String result = "";
        for(int i = str.length() - 1; i >= 0; i--) {
	          result += str.charAt(i);            
        }
        return result;
    }   
}

class MethodReferences {
    String stringFunc(ReverseFunc revFunc, String str) {
        return revFunc.func(str);
    }

    public void print() {
        out.println("2. Method References");
        String str = "Hello World!";
        String rstr = "";
        StringReverse reverseString = new StringReverse(); //
        // Method References
        // rstr = stringFunc(StringRevers::reverse, str);
        // Method References
        rstr = stringFunc(reverseString::reverse, str);
        out.println(rstr);

        List<String> names = new ArrayList<String>();
        names.add("Delta");
        names.add("Alpha");
        names.add("Epsilon");
        names.add("Beta");
        names.add("Gamma");
        out.println("Print using forEach");
        names.forEach(out::println);
        out.println();
   }
}

// 1. Lambda Expression
class LambdaExpression {
    interface Mathematics {
        int maths(int x, int y);
    }

    interface Greetings {
        void greeting(String message);
    } 

    int calculation(int x, int y, Mathematics m) {
        return m.maths(x, y);
    }

    public void print() {
        out.println("1. Lambda Expression");
        // Lambda Expression
        Mathematics addition = (int x, int y) -> x + y;
        Mathematics subtraction = (x, y) -> x - y;
        Mathematics multiplication = (x, y) -> { return x * y; };
        Mathematics division = (x, y) -> x / y;
        out.println("10 + 5 = " + calculation(10, 5, addition));
        out.println("10 - 5 = " + calculation(10, 5, subtraction));
        out.println("10 * 5 = " + calculation(10, 5, multiplication));
        out.println("10 / 5 = " + calculation(10, 5, division));

        // Lambda Expression
        Greetings greetHello = (message) -> out.println("Hello " + message);
        Greetings greetWhat = message -> out.println("What's " + message);
        greetHello.greeting("World!");
        greetWhat.greeting("up?");

        List<String> greek = new ArrayList<String>();
        greek.add("Delta");
        greek.add("Alpha");
        greek.add("Epsilon");
        greek.add("Beta");
        greek.add("Gamma");

        List<String> euclidean = new ArrayList<String>();
        euclidean.add("Delta");
        euclidean.add("Alpha");
        euclidean.add("Epsilon");
        euclidean.add("Beta");
        euclidean.add("Gamma");

        out.println("Sort using Pre-Java 8:");
        sortUsingPreJava8(greek);
        out.println(greek);

        out.println("Sort using Java 8:");
        sortUsingJava8(euclidean);
        out.println(euclidean);
        out.println();
    }

    // Sort using pre Java 8
    private void sortUsingPreJava8(List<String> names) {
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

   // Sort using Java 8
   private void sortUsingJava8(List<String> names) {
      Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
   }
}

class Java8 {
    public static void main(String... args) {
	out.printf("Java Version: %s%n%n", System.getProperty("java.version"));

        LambdaExpression expressionLambda = new LambdaExpression();
        expressionLambda.print();

        MethodReferences referencesMethod = new MethodReferences();
        referencesMethod.print();
        
        DefaultInterfaceMethods interfaceMethodsDefault = new DefaultInterfaceMethods();
        interfaceMethodsDefault.print();

        StaticInterfaceMethods interfaceMethodsStatic = new StaticInterfaceMethods();
        interfaceMethodsStatic.print();
        
        FunctionalInterfaces interfacesFunctional = new FunctionalInterfaces();
        interfacesFunctional.print();

        PredicateInterface interfacePredicate = new PredicateInterface();
        interfacePredicate.print();
        
        UtilStream streamUtil = new UtilStream();
        streamUtil.print();

	UtilOptional optionalUtil = new UtilOptional();
	optionalUtil.print();
    }
}

// Output
/*
Java Version: 1.8.0_41

1. Lambda Expression
10 + 5 = 15
10 - 5 = 5
10 * 5 = 50
10 / 5 = 2
Hello World!
What's up?
Sort using Pre-Java 8:
[Alpha, Beta, Delta, Epsilon, Gamma]
Sort using Java 8:
[Alpha, Beta, Delta, Epsilon, Gamma]

2. Method References
!dlroW olleH
Print using forEach
Delta
Alpha
Epsilon
Beta
Gamma

3. Default Interface Methods
100
This is the return from Default Interface Method

4. Static Interface Methods
100
This is the return from Static Method In Interface

5. Functional Interfaces
Number: 123.45
Random Number: 49.18437447351336
List
555
77
3
Sum
555
632
635
forEach printf:
555 77 3
forEach format:
555 77 3
format:
i = 123, j = 456
printf:
i = 123, j = 456

Note: Predicate predates Java 8
Integers
1
2
3
4
5
Odd Integers
1
3
5
Even Integers
2
4
Print all numbers:
1
2
3
4
5
6
7
8
9
Print even numbers:
2
4
6
8
Print numbers greater than 3:
4
5
6
7
8
9

6. Stream
Person List
Id: 1, Name: Arete, Gender: MALE, Date of birth: 1990-01-01, Income: 10000.00
Id: 2, Name: Ersa, Gender: FEMALE, Date of birth: 1989-02-02, Income: 20000.00
Id: 3, Name: Hera, Gender: FEMALE, Date of birth: 1988-03-03, Income: 30000.00
Id: 4, Name: Elpis, Gender: MALE, Date of birth: 1987-04-04, Income: 40000.00
Id: 5, Name: Soter, Gender: MALE, Date of birth: 1986-05-05, Income: 50000.00
Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Filtering by Stream
Person Income > 50000
Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Stream Iterating
Alphabet
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z
Filtering and Iterating by Stream
Person Income > 50000
Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Stream reduce() Method
Sum Person Income: 1450000.00
Stream reduce() Method by referring sum method of Double class
Sum Person Income: 1450000.00
Stream Collectors summingDouble() Method
Sum Person Income: 1450000.00
Stream Max, Min, and Count
Stream max() method
Maximum Person Income: 1000000.00
Stream min() method
Minimum Person Income: 10000.00
Stream count() Method
Count Person Income > 50000: 5
List Stream To Set
Person Set
Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 1, Name: Arete, Gender: MALE, Date of birth: 1990-01-01, Income: 10000.00
Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 2, Name: Ersa, Gender: FEMALE, Date of birth: 1989-02-02, Income: 20000.00
Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Id: 3, Name: Hera, Gender: FEMALE, Date of birth: 1988-03-03, Income: 30000.00
Id: 5, Name: Soter, Gender: MALE, Date of birth: 1986-05-05, Income: 50000.00
Id: 4, Name: Elpis, Gender: MALE, Date of birth: 1987-04-04, Income: 40000.00
Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
List Stream into Map
Person Id - Map Entry
Id: 1 - Id: 1, Name: Arete, Gender: MALE, Date of birth: 1990-01-01, Income: 10000.00
Id: 2 - Id: 2, Name: Ersa, Gender: FEMALE, Date of birth: 1989-02-02, Income: 20000.00
Id: 3 - Id: 3, Name: Hera, Gender: FEMALE, Date of birth: 1988-03-03, Income: 30000.00
Id: 4 - Id: 4, Name: Elpis, Gender: MALE, Date of birth: 1987-04-04, Income: 40000.00
Id: 5 - Id: 5, Name: Soter, Gender: MALE, Date of birth: 1986-05-05, Income: 50000.00
Id: 6 - Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7 - Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8 - Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9 - Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10 - Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Person Id - Map
Id: 1 - Id: 1, Name: Arete, Gender: MALE, Date of birth: 1990-01-01, Income: 10000.00
Id: 2 - Id: 2, Name: Ersa, Gender: FEMALE, Date of birth: 1989-02-02, Income: 20000.00
Id: 3 - Id: 3, Name: Hera, Gender: FEMALE, Date of birth: 1988-03-03, Income: 30000.00
Id: 4 - Id: 4, Name: Elpis, Gender: MALE, Date of birth: 1987-04-04, Income: 40000.00
Id: 5 - Id: 5, Name: Soter, Gender: MALE, Date of birth: 1986-05-05, Income: 50000.00
Id: 6 - Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7 - Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8 - Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9 - Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10 - Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Stream Method Reference by referring getIncome method of Person class
Person Income List
10000.00
20000.00
30000.00
40000.00
50000.00
60000.00
70000.00
80000.00
90000.00
1000000.00
Stream Method Reference by referring getPerson method of Person class
Person List
Id: 1, Name: Arete, Gender: MALE, Date of birth: 1990-01-01, Income: 10000.00
Id: 2, Name: Ersa, Gender: FEMALE, Date of birth: 1989-02-02, Income: 20000.00
Id: 3, Name: Hera, Gender: FEMALE, Date of birth: 1988-03-03, Income: 30000.00
Id: 4, Name: Elpis, Gender: MALE, Date of birth: 1987-04-04, Income: 40000.00
Id: 5, Name: Soter, Gender: MALE, Date of birth: 1986-05-05, Income: 50000.00
Id: 6, Name: Nyx, Gender: FEMALE, Date of birth: 1985-06-06, Income: 60000.00
Id: 7, Name: Aura, Gender: FEMALE, Date of birth: 1984-07-07, Income: 70000.00
Id: 8, Name: Selene, Gender: FEMALE, Date of birth: 1983-08-08, Income: 80000.00
Id: 9, Name: Gelos, Gender: MALE, Date of birth: 1982-09-09, Income: 90000.00
Id: 10, Name: Techne, Gender: MALE, Date of birth: 1981-10-10, Income: 1000000.00
Parallel Stream
Arete, Elpis, Soter, Gelos, Techne
Mixed Mode: Serial and Parallel Streams
Arete, Elpis, Soter, Gelos, Techne

5 Collections Factory Methods: Stream.of
Gamma
Bata
Delta
Alpha
Epsilon

7. Optional
true
Optional[Alpha]
Alpha
ifPresent: Alpha
false
Beta
false
Optional.empty
Gamma
false
Optional.empty
true
OptionalInt[555]
555
ifPresent: 555
false
OptionalInt.empty
true
OptionalDouble[7.5]
7.5
ifPresent: 7.5
false
OptionalDouble.empty
true
OptionalLong[9223372036854775807]
9223372036854775807
ifPresent: 9223372036854775807
false
OptionalLong.empty
*/

/*
Courtesies:
https://openjdk.java.net
https://docs.oracle.com
*/