package kayseven.linq.accessor;

import java.util.Collection;
import java.util.List;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;
import kayseven.linq.operation.SkipOperation;

/**
 * ElementAtAccessor
 */
public class ElementAtAccessor<E> extends LINQAccessor<E, E> {
    private final int index;

    public ElementAtAccessor(LINQ<E> linq, int index) {
        super(linq);
        this.index = index;
    }

    @Override
    public E getValue() {
        Collection<E> collection = collection();

        if (collection instanceof List && index < collection.size()) {
            return ((List<E>) collection).get(index);
        }

        return new SkipOperation<E>(linq.iterator(), index).getLINQ().iterator().next();
    }
}