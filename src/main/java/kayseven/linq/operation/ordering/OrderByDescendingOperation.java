package kayseven.linq.operation.ordering;

import java.util.Comparator;

import kayseven.linq.LINQ;
import kayseven.linq.PropertyExpression;

/**
 * OrderByDescendingOperation
 */
public class OrderByDescendingOperation<E, EProperty, OP extends OrderedLINQOperation<E, ?, ?>>
        extends OrderedLINQOperation<E, EProperty, OP> {

    public OrderByDescendingOperation(LINQ<E> linq, OP orderParent, PropertyExpression<E, EProperty> expression,
            Comparator<EProperty> comparator) {
        super(linq, orderParent, expression, comparator);
    }

    @Override
    OrderAction performAction(EProperty left, EProperty right) {
        if (right == null) {
            return new OrderAction(true);
        }

        int compVal = comparator.compare(left, right);

        return new OrderAction(compVal >= 0, compVal != 0);
    }
}