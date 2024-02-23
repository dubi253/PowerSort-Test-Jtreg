import java.util.Comparator;


public class PowerSort implements Sorter {
    private final boolean useMsbMergeType;
    private final boolean onlyIncreasingRuns;
    private final int myMinRunLen;

    private long mergeCost = 0;

    private static int minRunLen = 24;

    public PowerSort(final boolean useMsbMergeType, final boolean onlyIncreasingRuns, final int minRunLen) {
        this.useMsbMergeType = useMsbMergeType;
        this.onlyIncreasingRuns = onlyIncreasingRuns;
        this.myMinRunLen = minRunLen;
    }

    @Override
    public <T > void sort(T[] A, int left, int right, Comparator<? super T> comp) {
        if (comp == null)  comp = Sorter.NaturalOrder.INSTANCE;
        mergeCost = java.util.PowerSort.sort(A, left, right, comp, null, 0, 0, useMsbMergeType, onlyIncreasingRuns, minRunLen);
    }

    @Override
    public long getMergeCost() {
        return mergeCost;
    }

    @Override
    public String toString() {
        return "PowerSort";
    }

}
