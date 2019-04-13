package kayseven.linq;


import java.util.Comparator;


/**
 *
 * @author K7
 */
class DefaultComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }

        if (o1.equals(o2)) {
            return 0;
        }

        if (o1 instanceof String && o2 instanceof String) {
            return ((String) o1).compareTo((String) o2);
        }
        if (o1 instanceof Double && o2 instanceof Double) {
            return ((Double) o1).compareTo((Double) o2);
        }
        if (o1 instanceof Float && o2 instanceof Float) {
            return ((Float) o1).compareTo((Float) o2);
        }
        if (o1 instanceof Long && o2 instanceof Long) {
            return ((Long) o1).compareTo((Long) o2);
        }
        if (o1 instanceof Integer && o2 instanceof Integer) {
            return ((Integer) o1).compareTo((Integer) o2);
        }
        if (o1 instanceof Short && o2 instanceof Short) {
            return ((Short) o1).compareTo((Short) o2);
        }
        if (o1 instanceof Character && o2 instanceof Character) {
            return ((Character) o1).compareTo((Character) o2);
        }
        if (o1 instanceof Byte && o2 instanceof Byte) {
            return ((Byte) o1).compareTo((Byte) o2);
        }
        if (o1 instanceof Boolean && o2 instanceof Boolean) {
            return ((Boolean) o1).compareTo((Boolean) o2);
        }

        return o1.hashCode() - o2.hashCode();
    }
}
