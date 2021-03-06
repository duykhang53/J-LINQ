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
public class OrderByAscendingOperation<E, EProperty, OP extends OrderedLINQOperation<E, ?, ?>>
        extends OrderedLINQOperation<E, EProperty, OP> {

    public OrderByAscendingOperation(LINQ<E> linq, OP orderParent, PropertyExpression<E, EProperty> expression,
            Comparator<EProperty> comparator) {
        super(linq, orderParent, expression, comparator);
    }

    @Override
    OrderAction performAction(EProperty left, EProperty right) {
        if (left == null) {
            return new OrderAction(true);
        }

        int compareVal = comparator.compare(left, right);

        return new OrderAction(compareVal < 0, compareVal != 0);
    }
}
