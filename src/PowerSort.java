import java.util.Comparator;


public class PowerSort implements Sorter {
    private final boolean useMsbMergeType;
    private final boolean onlyIncreasingRuns;
    private final int myMinRunLen;

    private static int minRunLen = 24;

    public PowerSort(final boolean useMsbMergeType, final boolean onlyIncreasingRuns, final int minRunLen) {
        this.useMsbMergeType = useMsbMergeType;
        this.onlyIncreasingRuns = onlyIncreasingRuns;
        this.myMinRunLen = minRunLen;
    }

    @Override
    public <T > void sort(T[] A, int left, int right, Comparator<? super T> comp) {
        if (comp == null)  comp = Sorter.NaturalOrder.INSTANCE;
        java.util.PowerSort.sort(A, 0, A.length - 1, comp, null, 0, 0, useMsbMergeType, onlyIncreasingRuns, minRunLen);
    }

    @Override
    public void zeroMergeCost() {
        java.util.PowerSort.totalMergeCosts = 0;
    }

    @Override
    public long getMergeCost() {
        return java.util.PowerSort.totalMergeCosts;
    }

    @Override
    public String toString() {
        return "PowerSort";
    }

}
