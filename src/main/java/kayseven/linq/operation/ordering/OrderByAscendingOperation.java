/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kayseven.linq.operation.ordering;

import java.util.Comparator;
import kayseven.linq.LINQ;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 * @param <EProperty>
 */
public class OrderByAscendingOperation<E, EProperty> extends OrderedLINQOperation<E, EProperty> {

    public OrderByAscendingOperation(LINQ<E> baseQ, PropertyExpression<E, EProperty> expression, Comparator<EProperty> comparator) {
        super(baseQ, expression, comparator);
    }

    @Override
    public E next() {
        E next = linkedList.getFirst();
        EProperty nextVal = expression.getValue(next);

        if (next != null) {
            EProperty itemVal;

            for (int i = 1; i < linkedList.size(); i++) {
                if (nextVal == null) {
                    break;
                }

                E item = linkedList.get(i);

                if (item == null || (itemVal = expression.getValue(item)) == null || comparator.compare(nextVal, itemVal) > 0) {
                    next = item;
                }
            }

            linkedList.remove(next);
        }

        return next;
    }

}
