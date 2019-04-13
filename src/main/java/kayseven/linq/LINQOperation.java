package kayseven.linq;

import java.util.Iterator;

/**
 *
 * @author K7
 * @param <E> item type
 */
public abstract class LINQOperation<E> implements Iterator<E> {

    protected boolean hasNext;
    protected final Iterator<E> iterator;

    public LINQOperation(Iterator<E> iterator) {
        this.iterator = iterator;
        hasNext = false;
    }


    public final LINQ<E> getLINQ() {
        return new LINQ<E>(this);
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
