package kayseven.linq.operation;

import java.util.Iterator;
import kayseven.linq.LINQOperation;
import kayseven.linq.Predicate;

/**
 *
 * @author K7
 * @param <E>
 * @param <EType>
 */
public class OfTypeOperation<E, EType> extends LINQOperation<EType> {
    private final Iterator<E> eIte;

    public OfTypeOperation(Iterator<E> iterator, final Class<EType> clazz) {
        super(null);

        eIte = new WhereOperation<E>(iterator, new TypePredicate<E, EType>(clazz));
    }

    @Override
    public boolean hasNext() {
        return eIte.hasNext();
    }

    @Override
    @SuppressWarnings("unchecked")
    public EType next() {
        return (EType) eIte.next();
    }

    private class TypePredicate<T, TClass> implements Predicate<T> {

        private final Class<TClass> clazz;

        public TypePredicate(Class<TClass> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean test(T data) {
            return clazz.isInstance(data);
        }

    }
}
