package kayseven.linq.operation.grouping;

import java.util.Collection;
import kayseven.linq.LINQ;

/**
 *
 * @author K7
 * @param <TKey> key type of group
 * @param <E> group item type
 */
public class Grouping<TKey, E> extends LINQ<E> {

    private final TKey key;

    public TKey getKey() {
        return key;
    }

    public Grouping(TKey key, Collection<E> collection) {
        super(collection);
        this.key = key;
    }
}
