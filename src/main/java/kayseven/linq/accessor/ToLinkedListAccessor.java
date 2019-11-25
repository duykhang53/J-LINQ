package kayseven.linq.accessor;

import java.util.LinkedList;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;

/**
 * ToLinkedListAccessor
 */
public class ToLinkedListAccessor<E> extends LINQAccessor<E, LinkedList<E>> {

    public ToLinkedListAccessor(LINQ<E> linq) {
        super(linq);
    }

    @Override
    public LinkedList<E> getValue() {
        LinkedList<E> list = new LinkedList<E>();

        for (E e : linq) {
            list.add(e);
        }

        return list;
    }
}