package kayseven.linq.operation.ordering;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Stack;

import kayseven.linq.LINQ;
import kayseven.linq.LINQPropertyBaseOperation;
import kayseven.linq.PropertyExpression;
import kayseven.linq.accessor.ToLinkedListAccessor;

/**
 *
 * @author K7
 * @param <E>
 * @param <EProperty>
 */
public abstract class OrderedLINQOperation<E, EProperty, OP extends OrderedLINQOperation<E, ?, ?>>
        extends LINQPropertyBaseOperation<E, E, OrderedLINQ<E>, EProperty> {

    private final Object qLock = new Object();
    private LinkedList<E> linkedList;

    protected final Comparator<EProperty> comparator;
    protected final OP orderParent;
    @SuppressWarnings("rawtypes")
    private Iterable<OrderedLINQOperation> iteratedOperations;

    public OrderedLINQOperation(LINQ<E> linq, OP orderParent, PropertyExpression<E, EProperty> expression,
            Comparator<EProperty> comparator) {
        super(linq, expression);
        this.orderParent = orderParent;
        this.comparator = comparator;
    }

    LINQ<E> linq() {
        return linq;
    }

    protected final LinkedList<E> linkedList() {
        synchronized (qLock) {
            if (linkedList == null) {
                linkedList = new ToLinkedListAccessor<E>(linq).getValue();
            }

            return linkedList;
        }
    }

    public final OrderedLINQ<E> getValue() {
        return new OrderedLINQ<E>(this);
    }

    @Override
    public boolean hasNext() {
        synchronized (qLock) {
            return linkedList == null ? linq.iterator().hasNext() : !linkedList.isEmpty();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected synchronized <IteProperty> Iterable<OrderedLINQOperation<E, IteProperty, ?>> iterateOperation() {
        if (iteratedOperations == null) {
            final LinkedList<OrderedLINQOperation> ops = new LinkedList<OrderedLINQOperation>();

            ops.push(this);
            while (ops.peek().orderParent != null) {
                ops.addFirst(ops.peek().orderParent);
            }

            iteratedOperations = new Iterable() {
                @Override
                public Iterator iterator() {
                    return ops.iterator();
                }
            };
        }

        return (Iterable) iteratedOperations;
    }

    @Override
    public E next() {
        LinkedList<E> ll = linkedList();

        if (ll.isEmpty()) {
            throw new NoSuchElementException();
        }

        E next = ll.getFirst();

        ListIterator<E> listIterator = ll.listIterator(1);

        while (listIterator.hasNext()) {
            E item = listIterator.next();
            boolean assign = false;

            for (OrderedLINQOperation<E, Object, ?> op : iterateOperation()) {
                Object left = op.getProperty(item);
                Object right = op.getProperty(next);

                OrderAction action = op.performAction(left, right);
                assign = action.assignNext;

                if (action.breakOperation) {
                    break;
                }
            }

            if (assign) {
                next = item;
            }
        }

        ll.remove(next);

        return next;
    }

    abstract OrderAction performAction(EProperty left, EProperty right);

    protected static class OrderAction {
        public final boolean assignNext;
        public final boolean breakOperation;

        public OrderAction(boolean assignNext, boolean breakOperation) {
            this.assignNext = assignNext;
            this.breakOperation = breakOperation;
        }

        public OrderAction(boolean assignNext) {
            this(assignNext, assignNext);
        }
    }
}
