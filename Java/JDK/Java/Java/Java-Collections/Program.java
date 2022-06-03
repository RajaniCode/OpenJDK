import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.HashMap;

public class Program {
    public static void main(String[] args) {	
        //Warning: ArrayList is a raw type. References to generic type ArrayList<E> should be parameterized
        ArrayList aList = new ArrayList();
        aList.add("Alpha");
        aList.add("Beta");
        aList.add("Gamma");
        aList.add("Delta");
        aList.add("Epsilon");	
        aList.add("Beta");
        aList.add("Zeta");
        aList.add("Gamma");
        aList.add("Eta");
        aList.add(null);
        aList.add(1);
        aList.add("Theta");
        out.println("1 ArrayList Warning:");
        out.println("Number of elements: " + aList.size());
        // aList.forEach(out::println);
        aList.forEach(out::println); 
        
        ArrayList<String> arrayListP = new ArrayList<String>();
        arrayListP.add("Alpha");
        arrayListP.add("Beta");
        arrayListP.add("Gamma");
        arrayListP.add("Delta");
        arrayListP.add("Epsilon");	
        arrayListP.add("Beta");
        arrayListP.add("Zeta");
        arrayListP.add("Gamma");
        arrayListP.add("Eta");
        arrayListP.add(null);
        // arrayListP.add(1); // Compilation Error
        arrayListP.add("Theta");
        out.println("2 ArrayList Parameterized:");
        out.println("Number of elements: " + arrayListP.size());
        arrayListP.forEach(out::println);
	
        //Warning: ArrayList is a raw type. References to generic type ArrayList<E> should be parameterized
        List listArrayList = new ArrayList();        
        listArrayList.add("Alpha");
        listArrayList.add("Beta");
        listArrayList.add("Gamma");
        listArrayList.add("Delta");
        listArrayList.add("Epsilon");	
        listArrayList.add("Beta");
        listArrayList.add("Zeta");
        listArrayList.add("Gamma");
        listArrayList.add("Eta");
        listArrayList.add(null);
        listArrayList.add(1);
        listArrayList.add("Theta");
        out.println("3 List = ArrayList Warning:");
        out.println("Number of elements: " + listArrayList.size());
        listArrayList.forEach(out::println);
	     
        //Warning: List is a raw type. References to generic type List<E> should be parameterized
        List listArrayListP = new ArrayList<String>();        
        listArrayListP.add("Alpha");
        listArrayListP.add("Beta");
        listArrayListP.add("Gamma");
        listArrayListP.add("Delta");
        listArrayListP.add("Epsilon");	
        listArrayListP.add("Beta");
        listArrayListP.add("Zeta");
        listArrayListP.add("Gamma");
        listArrayListP.add("Eta");
        listArrayListP.add(null);
        listArrayListP.add(1);
        listArrayListP.add("Theta");	
        out.println("4 List = ArrayList Parameterized Warning:");
        out.println("Number of elements: " + listArrayListP.size());
        listArrayListP.forEach(out::println);
        
        //Warning: ArrayList is a raw type. References to generic type ArrayList<E> should be parameterized        
        List<String> listPArrayList = new ArrayList();        
        listPArrayList.add("Alpha");
        listPArrayList.add("Beta");
        listPArrayList.add("Gamma");
        listPArrayList.add("Delta");
        listPArrayList.add("Epsilon");	
        listPArrayList.add("Beta");
        listPArrayList.add("Zeta");
        listPArrayList.add("Gamma");
        listPArrayList.add("Eta");
        listPArrayList.add(null);
        // listPArrayList.add(1); // Compilation Error
        listPArrayList.add("Theta");
        out.println("5 List Parameterized = ArrayList Warning:");
        out.println("Number of elements: " + listPArrayList.size());
        listPArrayList.forEach(out::println);        
        
        List<String> listPArrayListP = new ArrayList<String>();        
        listPArrayListP.add("Alpha");
        listPArrayListP.add("Beta");
        listPArrayListP.add("Gamma");
        listPArrayListP.add("Delta");
        listPArrayListP.add("Epsilon");	
        listPArrayListP.add("Beta");
        listPArrayListP.add("Zeta");
        listPArrayListP.add("Gamma");
        listPArrayListP.add("Eta");
        listPArrayListP.add(null);
        // listPArrayListP.add(1); // Compilation Error
        listPArrayListP.add("Theta");	
        out.println("6 List Parameterized = ArrayList Parameterized:");
        out.println("Number of elements: " + listPArrayListP.size());
        listPArrayListP.forEach(out::println);
                
        // Compiler Error
        // List<String> listPArraysasList = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Beta", "Zeta", "Gamma", "Eta", null, 1, "Theta");
        List<String> listPArraysasList = Arrays.asList("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Beta", "Zeta", "Gamma", "Eta", null, "Theta");	
        out.println("7 List = Arrays as List:");
        out.println("Number of elements: " + listPArraysasList.size());
        listPArraysasList.forEach(out::println);
                
        Set<String> setPHashSetP = new HashSet<String>();
        setPHashSetP.add("Alpha");
        setPHashSetP.add("Beta");
        setPHashSetP.add("Gamma");
        setPHashSetP.add("Delta");
        setPHashSetP.add("Epsilon");	
        setPHashSetP.add("Beta"); // Will not add
        setPHashSetP.add("Zeta");
        setPHashSetP.add("Gamma"); // Will not add
        setPHashSetP.add("Eta");
        setPHashSetP.add(null);
        // setPHashSetP.add(1); // Compilation Error
        setPHashSetP.add("Theta");
        out.println("8 Set = HashSet:");
        out.println("Number of elements (Elements are unique): " + setPHashSetP.size());
        setPHashSetP.forEach(out::println);
        	
        HashSet<String> hashSetP = new HashSet<String>();
        hashSetP.add("Alpha");
        hashSetP.add("Beta");
        hashSetP.add("Gamma");
        hashSetP.add("Delta");
        hashSetP.add("Epsilon");	
        hashSetP.add("Beta"); // Will not add
        hashSetP.add("Zeta");
        hashSetP.add("Gamma"); // Will not add
        hashSetP.add("Eta");
        hashSetP.add(null);
        // hashSetP.add(1); // Compilation Error
        hashSetP.add("Theta");
        out.println("9 HashSet:");
        out.println("Number of elements (Elements are unique): " + hashSetP.size());
        hashSetP.forEach(out::println);
        
        Set<String> setPTreeSetP = new TreeSet<String>();
        setPTreeSetP.add("Alpha");
        setPTreeSetP.add("Beta");
        setPTreeSetP.add("Gamma");
        setPTreeSetP.add("Delta");
        setPTreeSetP.add("Epsilon");	
        setPTreeSetP.add("Beta");
        setPTreeSetP.add("Zeta");
        setPTreeSetP.add("Gamma");
        setPTreeSetP.add("Eta");
        // setPTreeSetP.add(null); // java.lang.NullPointerException
        // setPTreeSetP.add(1); // Compilation Error
        setPTreeSetP.add("Theta");
        out.println("10 Set = TreeSet:");
        out.println("Number of elements (Elements are unique): " + setPTreeSetP.size());
        setPTreeSetP.forEach(out::println);
        
        TreeSet<String> treeSetP = new TreeSet<String>();
        treeSetP.add("Alpha");
        treeSetP.add("Beta");
        treeSetP.add("Gamma");
        treeSetP.add("Delta");
        treeSetP.add("Epsilon");	
        treeSetP.add("Beta"); // Will not add
        treeSetP.add("Zeta");
        treeSetP.add("Gamma"); // Will not add
        treeSetP.add("Eta");
        // treeSetP.add(null); // java.lang.NullPointerException
        // treeSetP.add(1); // Compilation Error
        treeSetP.add("Theta");
        out.println("11 TreeSet:");
        out.println("Number of elements (Elements are unique): " + treeSetP.size());
        treeSetP.forEach(out::println);
        
        //Warning: HashMap is a raw type. References to generic type HashMap<K,V> should be parameterized
        HashMap hash = new HashMap();
        hash.put(1, "Alpha");
        hash.put(2, "Beta");
        hash.put(3, "Gamma");
        hash.put(4, "Delta");
        hash.put(5, "Epsilon");	
        hash.put(2, "Beta"); // Will not put
        hash.put(6, "Zeta");
        hash.put(7, "Gamma");
        hash.put(8, "Eta");
        hash.put(9, "Theta");
        hash.put(10, null);
        hash.put(11, "Iota");
        hash.put(null, "Kappa"); // Will not put
        hash.put(12, "Lambda");
        hash.put(null, null);
        hash.put("13", "Mu");
        hash.put(14, "Nu");
        out.println("12 HashMap Warning:");
        out.println("Number of elements (Keys are Unique): " + hash.size());
        out.println("for Entry");
        //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
        //Warning: Type safety: The expression of type Set needs unchecked conversion to conform to Set<Map.Entry>
        Set<Entry> hashentrySet = hash.entrySet();
        //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
        for(Entry hashentry : hashentrySet) {	 
        	out.println("Key: " + hashentry.getKey() + ", Value: " + hashentry.getValue());
        }
        out.println("for Iterator<Entry>:");
        //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
        for (Iterator<Entry> hashentrySetiterator = hashentrySet.iterator(); hashentrySetiterator.hasNext(); ) {
            //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
            Entry hashentrySetiteratornext = (Entry) hashentrySetiterator.next();
            out.println("Key: " + hashentrySetiteratornext.getKey() + ", Value: " + hashentrySetiteratornext.getValue());
        }
        
        HashMap<Integer, String> hashMapP = new HashMap<Integer, String>();
        hashMapP.put(1, "Alpha");
        hashMapP.put(2, "Beta");
        hashMapP.put(3, "Gamma");
        hashMapP.put(4, "Delta");
        hashMapP.put(5, "Epsilon");	
        hashMapP.put(2, "Beta"); // Will not put
        hashMapP.put(6, "Zeta");
        hashMapP.put(7, "Gamma");
        hashMapP.put(8, "Eta");
        hashMapP.put(9, "Theta");
        hashMapP.put(10, null);
        hashMapP.put(11, "Iota");
        hashMapP.put(null, "Kappa"); // Will not put
        hashMapP.put(12, "Lambda");
        hashMapP.put(null, null);
        // hashMapP.put("13", "Mu"); // Compilation Error
        hashMapP.put(14, "Nu");
        out.println("13 HashMap Parameterized:");
        out.println("Number of elements (Keys are Unique): " + hashMapP.size());
        out.println("for Entry<Integer, String>:");
        for(Entry<Integer, String> hashMapPentrySet : hashMapP.entrySet()) {
	    out.println("Key: " + hashMapPentrySet.getKey() + ", Value: " + hashMapPentrySet.getValue());
        }
        out.println("for Iterator<Entry<Integer, String>>:");
        Set<Entry<Integer, String>> hashMapPentrySet = hashMapP.entrySet();
        for (Iterator<Entry<Integer, String>> hashMapPentrySetiterator = hashMapPentrySet.iterator(); hashMapPentrySetiterator.hasNext(); ) {
	    Entry<Integer, String> hashMapPentrySetiteratornext = (Entry<Integer, String>) hashMapPentrySetiterator.next();
	    out.println("Key: " + hashMapPentrySetiteratornext.getKey() + ", Value: " + hashMapPentrySetiteratornext.getValue());
	}

	//Warning: HashMap is a raw type. References to generic type HashMap<K,V> should be parameterized
	Map mapHashMap = new HashMap();
        mapHashMap.put(1, "Alpha");
        mapHashMap.put(2, "Beta");
        mapHashMap.put(3, "Gamma");
        mapHashMap.put(4, "Delta");
        mapHashMap.put(5, "Epsilon");	
        mapHashMap.put(2, "Beta"); // Will not put
        mapHashMap.put(6, "Zeta");
        mapHashMap.put(7, "Gamma");
        mapHashMap.put(8, "Eta");
        mapHashMap.put(9, "Theta");
        mapHashMap.put(10, null);
        mapHashMap.put(11, "Iota");
        mapHashMap.put(null, "Kappa"); // Will not put
        mapHashMap.put(12, "Lambda");
        mapHashMap.put(null, null);
        mapHashMap.put("13", "Mu");
        mapHashMap.put(14, "Nu");
        out.println("14 Map = HashMap Warning:");
        out.println("Number of elements (Keys are Unique): " + mapHashMap.size());
        out.println("for Entry");
        //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
        //Warning: Type safety: The expression of type Set needs unchecked conversion to conform to Set<Map.Entry>
        Set<Entry> mapHashMapentrySet = mapHashMap.entrySet();        
	for(Entry mapHashMapentry : mapHashMapentrySet) {	 
            out.println("Key: " + mapHashMapentry.getKey() + ", Value: " + mapHashMapentry.getValue());
        }
	out.println("for Iterator<Entry>:");
	//Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
	for (Iterator<Entry> mapHashMapentrySetiterator = mapHashMapentrySet.iterator(); mapHashMapentrySetiterator.hasNext(); ) {
	    //Warning: Map.Entry is a raw type. References to generic type Map<K,V>.Entry<K,V> should be parameterized
	    Entry mapHashMapentrySetiteratornext = (Entry) mapHashMapentrySetiterator.next();
	    out.println("Key: " + mapHashMapentrySetiteratornext.getKey() + ", Value: " + mapHashMapentrySetiteratornext.getValue());
	}
        
        Map<Integer, String> mapPHashMapP = new HashMap<Integer, String>();
        mapPHashMapP.put(1, "Alpha");
        mapPHashMapP.put(2, "Beta");
        mapPHashMapP.put(3, "Gamma");
        mapPHashMapP.put(4, "Delta");
        mapPHashMapP.put(5, "Epsilon");	
        mapPHashMapP.put(2, "Beta"); // Will not put
        mapPHashMapP.put(6, "Zeta");
        mapPHashMapP.put(7, "Gamma");
        mapPHashMapP.put(8, "Eta");
        mapPHashMapP.put(9, "Theta");
        mapPHashMapP.put(10, null);
        mapPHashMapP.put(11, "Iota");
        mapPHashMapP.put(null, "Kappa"); // Will not put
        mapPHashMapP.put(12, "Lambda");
        mapPHashMapP.put(null, null);
        // mapPHashMapP.put("13", "Mu"); // Compilation Error
        mapPHashMapP.put(14, "Nu");
        out.println("15 Map Parameterized = HashMap Parameterized:");
        out.println("Number of elements (Keys are Unique): " + mapPHashMapP.size());
        out.println("for Entry<Integer, String>:");
        for(Entry<Integer, String> mapPHashMapPentrySet : mapPHashMapP.entrySet()) {        	
            out.println("Key: " + mapPHashMapPentrySet.getKey() + ", Value: " + mapPHashMapPentrySet.getValue());
        }
        out.println("for Iterator<Entry<Integer, String>>:");
        Set<Entry<Integer, String>> mapPHashMapPentrySet = mapPHashMapP.entrySet();
        
        for (Iterator<Entry<Integer, String>> mapPHashMapPentrySetiterator = mapPHashMapPentrySet.iterator(); mapPHashMapPentrySetiterator.hasNext(); ) {
 	    Entry<Integer, String> mapPHashMapPentrySetiteratornext = (Entry<Integer, String>) mapPHashMapPentrySetiterator.next();
	    out.println("Key: " + mapPHashMapPentrySetiteratornext.getKey() + ", Value: " + mapPHashMapPentrySetiteratornext.getValue());
	}
        
        out.println("Hello World!");
    }	
}

/*
Output:
1 ArrayList Warning:
Number of elements: 12
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
1
Theta
2 ArrayList Parameterized:
Number of elements: 11
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
Theta
3 List = ArrayList Warning:
Number of elements: 12
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
1
Theta
4 List = ArrayList Parameterized Warning:
Number of elements: 12
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
1
Theta
5 List Parameterized = ArrayList Warning:
Number of elements: 11
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
Theta
6 List Parameterized = ArrayList Parameterized:
Number of elements: 11
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
Theta
7 List = Arrays as List:
Number of elements: 11
Alpha
Beta
Gamma
Delta
Epsilon
Beta
Zeta
Gamma
Eta
null
Theta
8 Set = HashSet:
Number of elements (Elements are unique): 9
null
Gamma
Zeta
Eta
Delta
Alpha
Epsilon
Theta
Beta
9 HashSet:
Number of elements (Elements are unique): 9
null
Gamma
Zeta
Eta
Delta
Alpha
Epsilon
Theta
Beta
10 Set = TreeSet:
Number of elements (Elements are unique): 8
Alpha
Beta
Delta
Epsilon
Eta
Gamma
Theta
Zeta
11 TreeSet:
Number of elements (Elements are unique): 8
Alpha
Beta
Delta
Epsilon
Eta
Gamma
Theta
Zeta
12 HashMap Warning:
Number of elements (Keys are Unique): 15
for Entry
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 13, Value: Mu
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
for Iterator<Entry>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 13, Value: Mu
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
13 HashMap Parameterized:
Number of elements (Keys are Unique): 14
for Entry<Integer, String>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
for Iterator<Entry<Integer, String>>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
14 Map = HashMap Warning:
Number of elements (Keys are Unique): 15
for Entry
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 13, Value: Mu
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
for Iterator<Entry>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 13, Value: Mu
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
15 Map Parameterized = HashMap Parameterized:
Number of elements (Keys are Unique): 14
for Entry<Integer, String>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
for Iterator<Entry<Integer, String>>:
Key: null, Value: null
Key: 1, Value: Alpha
Key: 2, Value: Beta
Key: 3, Value: Gamma
Key: 4, Value: Delta
Key: 5, Value: Epsilon
Key: 6, Value: Zeta
Key: 7, Value: Gamma
Key: 8, Value: Eta
Key: 9, Value: Theta
Key: 10, Value: null
Key: 11, Value: Iota
Key: 12, Value: Lambda
Key: 14, Value: Nu
Hello World! 
*/