package kayseven.linq.accessor;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;

/**
 * ContainsAccessor
 */
public class ContainsAccessor<E> extends LINQAccessor<E, Boolean> {
    private final E obj;

    public ContainsAccessor(LINQ<E> linq, E obj) {
        super(linq);
        this.obj = obj;
    }

    @Override
    public Boolean getValue() {
        for (E e : super.linq) {
            if (e == obj || (obj != null && obj.equals(e))) {
                return true;
            }
        }

        return false;
    }

}