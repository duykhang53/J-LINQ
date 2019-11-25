package kayseven.linq.accessor;

import java.lang.reflect.Array;
import java.util.List;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;

/**
 * ToArrayAccessor
 */
public class ToArrayAccessor<E> extends LINQAccessor<E, E[]> {
    private final Class<E> clazz;

    public ToArrayAccessor(LINQ<E> linq, Class<E> clazz) {
        super(linq);
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] getValue() {
        List<E> list = new ToListAccessor<E>(linq).getValue();
        E[] ary = (E[]) Array.newInstance(clazz, list.size());
        return list.toArray(ary);
    }

    
}