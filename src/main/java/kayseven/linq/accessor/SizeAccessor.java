package kayseven.linq.accessor;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;

/**
 * SizeAccessor
 */
public class SizeAccessor<E> extends LINQAccessor<E, Integer> {

    public SizeAccessor(LINQ<E> linq) {
        super(linq);
    }

    @Override
    @SuppressWarnings("unused")
    public Integer getValue() {
        if (isConstructed()) {
            return collection().size();
        }

        int cnt = 0;

        for (E e : linq) {
            cnt++;
        }

        return cnt;
    }
}