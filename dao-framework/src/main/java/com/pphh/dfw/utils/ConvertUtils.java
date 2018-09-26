package com.pphh.dfw.utils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * convert utils
 *
 * @author huangyinhuang
 * @date 7/12/2018
 */
public class ConvertUtils {

    /**
     * convert Map<K, V> into Collection<V>
     *
     * @param map     map object
     * @param factory a fatory to create collection object
     * @param <K>     Key Object Class
     * @param <V>     Value Object Class
     * @param <U>     Collection type
     * @return collection
     */
    public static <K, V, U extends Collection<V>> U collect(Map<K, V> map, CollectionFactory<V, U> factory) {
        U collection = factory.createCollection();
        map.values().iterator().forEachRemaining(collection::add);
        return collection;
    }

    /**
     * convert iterable List<S> into Map<K, S>
     *
     * @param iterable     iterable object
     * @param keyGenerator a key generation function, which takes S object as input and generate K value
     * @param <S>          Source Object Class
     * @return Map
     */
    public static <S> Map<String, S> map(Iterable<S> iterable,
                                         Function<? super S, String> keyGenerator) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toMap(keyGenerator, Function.identity()));
    }

    /**
     * convert iterable List<S> into Map<K, V>
     *
     * @param iterable       iterable object
     * @param keyGenerator   a key generation function, which takes S object as input and generate K object as key
     * @param valueGenerator a value generation function, which takes S object as input and generate V object as value
     * @param factory        a fatory to create map object
     * @param <S>            Source Object Class
     * @param <K>            Key Object Class
     * @param <V>            Value Object Class
     * @param <U>            Map<K, V> object
     * @return Map
     */
    public static <S, K, V, U extends Map<K, V>> U map(Iterable<S> iterable,
                                                       Function<? super S, K> keyGenerator,
                                                       Function<? super S, V> valueGenerator,
                                                       MapFactory<K, V, U> factory) {
        U newMap = factory.createMap();
        iterable.forEach(entry -> newMap.put(keyGenerator.apply(entry), valueGenerator.apply(entry)));
        return newMap;
    }

    public interface CollectionFactory<T, U extends Collection<T>> {
        U createCollection();
    }

    public interface MapFactory<K, T, U extends Map<K, T>> {
        U createMap();
    }
}
