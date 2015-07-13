package io.github.francoiscampbell.collections;

import java.util.HashMap;
import java.util.Iterator;

/**
 * A Map that maps an item to itself to emulate a Set that you can get() from.
 */
public class SelfMap<V> extends HashMap<V, V> implements Iterable<V> {

    /**
     * Puts the item in the SelfMap.
     *
     * @param item The item to put.
     * @return The previous value associated with <tt>item</tt>.
     */
    public V put(V item) {
        return super.put(item, item);
    }

    /**
     * Puts the item in the SelfMap if it does not contain it. Else, returns the copy of the passed-in item that is already in the SelfMap.
     *
     * @param item The item to put.
     * @return The copy of the item in the SelfMap, or the passed-in item if it was not found in the Map
     */
    public V putIfAbsent(V item) {
        if (containsKey(item)) {
            item = get(item);
        } else {
            put(item);
        }
        return item;
    }

    @Override
    public Iterator<V> iterator() {
        return keySet().iterator();
    }
}
