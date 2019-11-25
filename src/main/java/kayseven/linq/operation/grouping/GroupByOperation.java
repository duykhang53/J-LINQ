
package kayseven.linq.operation.grouping;

import java.util.LinkedList;
import java.util.List;

import kayseven.linq.EqualityComparer;
import kayseven.linq.LINQ;
import kayseven.linq.LINQPropertyBaseOperation;
import kayseven.linq.Predicate;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 * @param <EProperty>
 */
public class GroupByOperation<E, EProperty>
        extends LINQPropertyBaseOperation<E, Grouping<EProperty, E>, LINQ<Grouping<EProperty, E>>, EProperty> {

    protected final LinkedList<E> ll;
    protected final EqualityComparer<EProperty> equalityComparer;

    public GroupByOperation(LINQ<E> baseQ, PropertyExpression<E, EProperty> expression,
            EqualityComparer<EProperty> equalityComparer) {
        super(baseQ, expression);
        ll = baseQ.toLinkedList();
        this.equalityComparer = equalityComparer;
    }

    @Override
    public LINQ<Grouping<EProperty, E>> getValue() {
        return LINQ.create(this);
    }

    @Override
    public boolean hasNext() {
        return !ll.isEmpty();
    }

    @Override
    public Grouping<EProperty, E> next() {
        E first = ll.removeFirst();
        final EProperty prop = getProperty(first);

        List<E> lst = LINQ.create(ll).where(new Predicate<E>() {
            @Override
            public boolean test(E t) {
                return equalityComparer.isEquals(prop, getProperty(t));
            }
        }).toList();
        lst.add(0, first);
        ll.removeAll(lst);

        return new Grouping<EProperty, E>(prop, lst);
    }
}
