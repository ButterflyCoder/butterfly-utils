package com.github.butterflycoder.butterflyutils;



import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toConcurrentMap;

public class CollectionUtils {

    public static <T> List<T> toList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
            .collect(Collectors.toList());
    }


    public static <T> List<T> flatList(List<T>... args) {
        ArrayList<T> finalList = new ArrayList<>();
        for (List<T> subList : args) {
            finalList.addAll(subList);
        }
        return Collections.unmodifiableList(finalList);
    }

    public static <T> List<T> flatMlist(List<T>... args) {
        ArrayList<T> finalList = new ArrayList<>();
        for (List<T> subList : args) {
            finalList.addAll(subList);
        }
        return finalList;
    }

    public static <T> Set<T> difference(Set<T> set1, Set<T> set2) {
        final Set<T> larger = set1.size() > set2.size() ? set1 : set2;
        final Set<T> smaller = larger.equals(set1) ? set2 : set1;
        return larger.stream().filter(n -> !smaller.contains(n)).collect(Collectors.toSet());
    }
    public static <T> Set<T> intersection(Set<T> s1, Set<T> s2) {
        Set<T> intersection = new HashSet<T>(s1); // use the copy constructor
        intersection.retainAll(s2);
        return intersection;
    }

    public static <T> Set<T> set(T ... arr) {
        HashSet<T> tmpSet = new HashSet<>();
        for (T t : arr) {
            tmpSet.add(t);
        }
        return Collections.unmodifiableSet(tmpSet) ;
    }

    public static <T> Set<T> toSet(List<T> inputList) {
        return Collections.unmodifiableSet(new HashSet<>(inputList)) ;
    }

    public static <T> Set<T> mset(T ... arr) {
        HashSet<T> tmpSet = new HashSet<>();
        for (T t : arr) {
            tmpSet.add(t);
        }
        return tmpSet;
    }

    public static <T> Set<T> mset(Set<T> immutableSet) {
        return new HashSet<>(immutableSet);
    }



    public static <T> List<T> mutable(List<T> immutable) {
        return new ArrayList<>(immutable);
    }

    public static <T> List<T> copy(List<T> immutable) {
        return new ArrayList<>(immutable);
    }

    public static <K,V> Map<K,V> copy(Map<K,V> immutableMap) {
        return new HashMap<>(immutableMap);
    }

    public static <T> Set<T> mutable(Set<T> immutable) {
        return new HashSet<>(immutable);
    }

    public static <T> List<T> sort(List<T> mutable) {
        mutable.sort(null);
        return mutable;
    }

    public static <T> List<T> sort(List<T> mutable,Comparator<T> comparator) {
        mutable.sort(comparator);
        return mutable;
    }


    public static <T> Collector<T, ?, List<T>> toSortedListCollector(Comparator<T> comparator) {
        return Collectors.collectingAndThen(
            Collectors.toCollection(ArrayList::new),
            l->{l.sort(comparator);return l;}
        );
    }



    public static final class Pair<L,R> {
        public final L left;
        public final R right;
        public Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }
    }

    public static <L,R> Pair<L,R> pair(L left, R right) {
        Pair pair = new Pair(left, right);
        return pair;
    }

    public static <T> List<T> list(T... args) {
        return Collections.unmodifiableList(Arrays.asList(args));
    }

    public static <T extends Comparable> T min(T... args) {
        List<T> list = list(args);
        Optional<T> min = list.stream().min(Comparator.comparing(Function.identity()));
        return min.orElse(null);
    }

    public static <T extends Comparable> T max(T... args) {
        List<T> list = list(args);
        Optional<T> min = list.stream().max(Comparator.comparing(Function.identity()));
        return min.orElse(null);
    }

    public static <T> List<T> tolist(Collection<T> coll) {
        return Collections.unmodifiableList(new ArrayList<>(coll));
    }

    public static <T,K> List<T> distinct(List<T> duplicatedList, Function<? super T, ? extends K> keyExtractor) {
        Collection<T> distinctValues = duplicatedList.stream()
            .collect(toConcurrentMap(keyExtractor, Function.identity(), (p, q) -> p)).values();
        List<T> list = tolist(distinctValues);
        return list;
    }

    public static <K,V> Map<K,V> mmap(Map<K,V> immutableMap) {
        HashMap<K, V> mutableMap = new HashMap<>();
        mutableMap.putAll(immutableMap);
        return mutableMap;
    }


    public static <T> List<T> mlist(T... args) {
        List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(args));
        return list;
    }

    public static <T> List<T> mlist(List<T> unmodifiableList) {
        List<T> list = new ArrayList<>();
        list.addAll(unmodifiableList);
        return list;

    }

    public static <K,V> Map<K,V> concat(Map<K,V>... inputMaps) {
        Map<K,V> finalMap = new HashMap<>();

        for (Map<K,V> inputMap : inputMaps) {
            if (inputMap != null) {
                finalMap.putAll(inputMap);
            }
        }
        return Collections.unmodifiableMap(finalMap);
    }

    public static <T> List<T> concat(List<T>... inputLists) {
        List<T> finalList = new ArrayList<>();
        for (List<T> inputList : inputLists) {
            if (inputList != null) {
                finalList.addAll(inputList);
            }
        }
        return Collections.unmodifiableList(finalList);
    }

    public static <T> List<T> concat(T[]... inputLists) {
        List<T> finalList = new ArrayList<>();
        for (T[] inputList : inputLists) {
            if (inputList != null) {
                finalList.addAll(list(inputList));
            }
        }
        return Collections.unmodifiableList(finalList);
    }

    public static <T> Set<T> concat(Set<T>... inputSets) {
        Set<T> finalList = new HashSet<>();
        for (Set<T> inputList : inputSets) {
            if (inputList != null) {
                finalList.addAll(inputList);
            }
        }
        return Collections.unmodifiableSet(finalList);
    }

    public static <T> List<T> list(Set<T> set) {
        return Collections.unmodifiableList(new ArrayList<>(set));
    }

    public static <T> T[] array(T... args) {
        return args;
    }

    public static <K, V> Map<K, V> map(K key, V value,
            K key1, V value1,
            K key2, V value2,
            K key3, V value3,
            K key4, V value4,
            K key5, V value5,
            K key6, V value6,
            K key7, V value7,
            K key8, V value8,
            K key9, V value9,
            K key10, V value10,
            K key11, V value11,
            K key12, V value12,
            K key13, V value13,
            K key14, V value14,
            K key15, V value15,
            K key16, V value16
   

       
   
) {
HashMap<K, V> returnMap = new HashMap<>();
returnMap.put(key, value);
returnMap.put(key1, value1);
returnMap.put(key2, value2);
returnMap.put(key3, value3);
returnMap.put(key4, value4);
returnMap.put(key5, value5);
returnMap.put(key6, value6);
returnMap.put(key7, value7);
returnMap.put(key8, value8);
returnMap.put(key9, value9);
returnMap.put(key10, value10);
returnMap.put(key11, value11);
returnMap.put(key12, value12);
returnMap.put(key13, value13);
returnMap.put(key14, value14);
returnMap.put(key15, value15);
returnMap.put(key16, value16);



return Collections.unmodifiableMap(returnMap);
}

    
    
    
    
    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9,
                                       K key10, V value10,
                                       K key11, V value11,
                                       K key12, V value12,
                                       K key13, V value13,
                                       K key14, V value14
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        returnMap.put(key10, value10);
        returnMap.put(key11, value11);
        returnMap.put(key12, value12);
        returnMap.put(key13, value13);
        returnMap.put(key14, value14);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9,
                                       K key10, V value10,
                                       K key11, V value11,
                                       K key12, V value12,
                                       K key13, V value13
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        returnMap.put(key10, value10);
        returnMap.put(key11, value11);
        returnMap.put(key12, value12);
        returnMap.put(key13, value13);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9,
                                       K key10, V value10,
                                       K key11, V value11,
                                       K key12, V value12
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        returnMap.put(key10, value10);
        returnMap.put(key11, value11);
        returnMap.put(key12, value12);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9,
                                       K key10, V value10,
                                       K key11, V value11
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        returnMap.put(key10, value10);
        returnMap.put(key11, value11);
        return Collections.unmodifiableMap(returnMap);
    }
    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9,
                                       K key10, V value10
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        returnMap.put(key10, value10);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8,
                                       K key9, V value9
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        returnMap.put(key9, value9);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7,
                                       K key8, V value8
                                       ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        returnMap.put(key8, value8);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6,
                                       K key7, V value7
    ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        returnMap.put(key7, value7);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5,
                                       K key6, V value6
                                       ) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        returnMap.put(key6, value6);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value,
                                       K key1, V value1,
                                       K key2, V value2,
                                       K key3, V value3,
                                       K key4, V value4,
                                       K key5, V value5) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        returnMap.put(key5, value5);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value, K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        returnMap.put(key4, value4);
        return Collections.unmodifiableMap(returnMap);
    }
    public static <K, V> Map<K, V> map(K key, V value, K key1, V value1, K key2, V value2, K key3, V value3) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        returnMap.put(key3, value3);
        return Collections.unmodifiableMap(returnMap);
    }
    public static <K, V> Map<K, V> map(K key, V value, K key1, V value1, K key2, V value2) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        returnMap.put(key2, value2);
        return Collections.unmodifiableMap(returnMap);
    }
    public static <K, V> Map<K, V> map(K key, V value, K key1, V value1) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        returnMap.put(key1, value1);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map(K key, V value) {
        HashMap<K, V> returnMap = new HashMap<>();
        returnMap.put(key, value);
        return Collections.unmodifiableMap(returnMap);
    }

    public static <K, V> Map<K, V> map() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> mmap() {
        return new HashMap<>();
    }

}