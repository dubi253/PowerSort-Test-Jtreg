import java.util.Comparator;
public class TimSort implements Sorter {

    @Override
    public <T > void sort(T[] A, int left, int right, Comparator<? super T> comp) {
        if (comp == null)  comp = Sorter.NaturalOrder.INSTANCE;
        java.util.TimSort.sort(A, left, right + 1, comp, null, 0, 0);
    }

    @Override
    public void zeroMergeCost() {
        java.util.TimSort.totalMergeCosts = 0;
    }

    @Override
    public long getMergeCost() {
        return java.util.TimSort.totalMergeCosts;
    }

    @Override
    public String toString() {
        return "TimSort";
    }

}
