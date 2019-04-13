package kayseven.linq.operation;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kayseven.linq.LINQ;
import kayseven.linq.LINQOperation;
import kayseven.linq.PropertyExpression;

/**
 *
 * @author K7
 * @param <E>
 * @param <EProperty>
 */
public class UnionSelectOperation<E, EProperty> extends LINQOperation<EProperty> {

    public static <T, TProperty> LINQ<TProperty> getClassUnionSelect(Iterator<T> ite, final Class<TProperty> clazz) {
        return new UnionSelectOperation(ite, new ClassPropertyExpression()).getLINQ().ofType(clazz);
    }

    private EProperty next;
    private Iterator<EProperty> inner;
    private final Iterator<E> baseIterator;
    private final PropertyExpression<E, Iterable<EProperty>> expression;

    public UnionSelectOperation(Iterator<E> iterator, PropertyExpression<E, Iterable<EProperty>> expression) {
        super(null);
        this.expression = expression;
        this.baseIterator = iterator;
        inner = baseIterator.hasNext() ? expression.getValue(baseIterator.next()).iterator() : EmptyIterator.<EProperty>create();
//        hasNext = inner.hasNext();

        while (!inner.hasNext() && baseIterator.hasNext()) {
            inner = expression.getValue(baseIterator.next()).iterator();
        }
        hasNext = inner.hasNext();

        if (hasNext) {
            next = inner.next();
        }
    }

//    @Override
//    public boolean hasNext() {
//        return inner.hasNext() || baseIterator.hasNext();
//    }
    @Override
    public EProperty next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        EProperty current = next;

        while (!inner.hasNext()) {
            if (!baseIterator.hasNext()) {
                hasNext = false;
                break;
            }

            inner = expression.getValue(baseIterator.next()).iterator();
        }

        if (inner.hasNext()) {
            next = inner.next();
        }

        return current;
    }

    private static class ClassPropertyExpression<T> implements PropertyExpression<T, Iterable<Object>> {

        @Override
        public Iterable<Object> getValue(T obj) {
            if (obj instanceof Iterable) {
                return (Iterable) obj;
            }
//                List<E> ary = Arrays.asList(obj);
//
//                if (ary.size() == 1 && ary.get(0) == obj) {
            return Collections.emptyList();
//                }
//
//                return (Iterable) ary;
        }

    }

}
