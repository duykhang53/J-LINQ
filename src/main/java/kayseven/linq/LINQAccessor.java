package kayseven.linq;

import java.util.Collection;

/**
 * LINQAccessor
 */
public abstract class LINQAccessor<E, T> {
    protected final LINQ<E> linq;

    public LINQAccessor(LINQ<E> linq) {
        this.linq = linq;
    }

    public abstract T getValue();

    protected boolean primitive() {
        return true;
    }

    protected boolean isConstructed() {
        return linq.isConstructed;
    }

    protected Collection<E> collection(){
        return linq.collection;
    }
}