package kayseven.linq.accessor;

import java.util.ArrayList;
import java.util.List;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;

/**
 * ToListAccessor
 */
public class ToListAccessor<E> extends LINQAccessor<E, List<E>> {

    public ToListAccessor(LINQ<E> linq) {
        super(linq);
    }

    @Override
    public List<E> getValue() {
        ArrayList<E> list = new ArrayList<E>();

        for (E e : linq) {
            list.add(e);
        }

        return list;
    }
}