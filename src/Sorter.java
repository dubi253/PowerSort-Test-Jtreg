import java.util.*;

public interface Sorter {
    /**
     * Sorts A[left..right] (both endpoints inclusive)
     */
    <T> void sort(T[] A, int left, int right, Comparator<? super T> comp);

    void zeroMergeCost();

    long getMergeCost();

    @Override
    String toString();

    default <T> void sort(T[] A, Comparator<? super T> comp) {
        sort(A, 0, A.length - 1, comp);
    }

    default <T> void sort(T[] A) {
        sort(A, 0, A.length - 1, null);
    }


    /**
     * A comparator that implements the natural ordering of a group of
     * mutually comparable elements. May be used when a supplied
     * comparator is null. To simplify code-sharing within underlying
     * implementations, the compare method only declares type Object
     * for its second argument.
     * <p>
     * Arrays class implementor's note: It is an empirical matter
     * whether ComparableTimSort offers any performance benefit over
     * TimSort used with this comparator.  If not, you are better off
     * deleting or bypassing ComparableTimSort.  There is currently no
     * empirical case for separating them for parallel sorting, so all
     * public Object parallelSort methods use the same comparator
     * based implementation.
     */
    static final class NaturalOrder implements Comparator<Object> {
        @SuppressWarnings("unchecked")
        public int compare(Object first, Object second) {
            return ((Comparable<Object>) first).compareTo(second);
        }

        static final NaturalOrder INSTANCE = new NaturalOrder();
    }
}
