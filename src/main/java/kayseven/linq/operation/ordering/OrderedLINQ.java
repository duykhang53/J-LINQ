package kayseven.linq.operation.ordering;

import java.util.Iterator;
import kayseven.linq.LINQ;

/**
 *
 * @author K7
 * @param <E>
 */
public class OrderedLINQ<E> extends LINQ<E> {
    OrderedLINQ(Iterator<E> iterator) {
        super(iterator);
    }
}
