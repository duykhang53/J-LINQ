package kayseven.linq;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kayseven.linq.LINQ;
import kayseven.linq.LINQAccessor;
import kayseven.linq.PropertyExpression;

/**
 * LINQPropertyBaseOperation
 */
public abstract class LINQPropertyBaseOperation<E, E2, TQ extends Iterable<E2>, EProperty> extends LINQAccessor<E, TQ>
        implements Iterator<E2> {
    protected final PropertyExpression<E, EProperty> propertyExpression;
    private final Map<E, EProperty> propertyMap = Collections.synchronizedMap(new HashMap<E, EProperty>());

    public LINQPropertyBaseOperation(LINQ<E> linq, PropertyExpression<E, EProperty> propertyExpression) {
        super(linq);
        this.propertyExpression = propertyExpression;
    }

    protected final EProperty getProperty(E item) {
        if (item == null) {
            return null;
        }

        synchronized (item) {
            EProperty p;

            if (propertyMap.containsKey(item)) {
                p = propertyMap.get(item);
            } else {
                propertyMap.put(item, p = propertyExpression.getValue(item));
            }

            return p;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}