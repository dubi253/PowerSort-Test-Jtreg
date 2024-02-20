import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public enum PermutationRules {
    RANDOM_INTEGER("Random_Integer") {

        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = rnd.nextInt();
            return result;
        }
    },

    DESCENDING_INTEGER("Descending_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = len - i;
            return result;
        }
    },

    ASCENDING_INTEGER("Ascending_Integer"){
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = i;
            return result;
        }
    },

    ASCENDING_3_RND_EXCH_INTEGER("Ascending_3_Rnd_Exch_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            if (len == 0) return new Integer[0];
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = i;
            for (int i = 0; i < 3; i++)
                swap(result, rnd.nextInt(result.length),
                        rnd.nextInt(result.length));
            return result;
        }
    },

    ASCENDING_10_RND_AT_END_INTEGER("Ascending_10_Rnd_At_End_Integer") {  // 10 random integers at the end of the array

        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            if (len == 0) return new Integer[0];
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            int endStart = len - 10;
            for (int i = 0; i < endStart; i++)
                result[i] = i;
            for (int i = endStart; i < len; i++)
                result[i] = rnd.nextInt(endStart + 10);
            return result;
        }
    },

    ALL_EQUAL_INTEGER("All_Equal_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Integer[] result = new Integer[len];
            Arrays.fill(result, 666);
            return result;
        }
    },

    DUPS_GALORE_INTEGER("Dups_Galore_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = rnd.nextInt(4);
            return result;
        }
    },

    RANDOM_WITH_DUPS_INTEGER("Random_With_Dups_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = rnd.nextInt(len);
            return result;
        }
    },

    RANDOM_RUNS_INTEGER("Random_Runs_Integer") {
        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int expRunLen) {
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            for (int i = 0; i < len; i++)
                result[i] = rnd.nextInt(len);
            sortRandomRuns(result, 0, len - 1, expRunLen, rnd);
            return result;
        }
    },

    /**
     * Fills the given array A with a Timsort drag input of the correct length
     * where all lengths are multiplied by minRunLen.
     */
    TIMSORT_DRAG_RUNS_INTEGER("Timsort_Drag_Runs_Integer") {

        private static LinkedList<Integer> RTimCache = null;
        private static int RTimCacheN = -1;

        @SuppressWarnings("unchecked")
        public Integer[] buildImpl(int len, long randomSeed, int minRunLen) {
            Random rnd = new Random(randomSeed);
            Integer[] result = new Integer[len];
            int n = len / minRunLen;
            if (RTimCacheN != n || RTimCache == null) {
                RTimCacheN = n;
                RTimCache = timsortDragRunlengths(n);
            }
            LinkedList<Integer> RTim = RTimCache;
            fillWithUpAndDownRunsInteger(result, RTim, minRunLen, rnd);
            return result;
        }

        public static <T> void fillWithUpAndDownRunsInteger(final Integer[] A, final List<Integer> runLengths,
                                                            final int runLenFactor, final Random random) {
            int n = A.length;
            assert total(runLengths) * runLenFactor == n;
            for (int i = 0; i < n; ++i) A[i] = i + 1;
            shuffle(A, 0, n - 1, random);
            boolean reverse = false;
            int i = 0;
            for (int l : runLengths) {
                int L = l * runLenFactor;
                Arrays.sort(A, Math.max(0, i - 1), i + L);
                if (reverse) reverseRange(A, Math.max(0, i - 1), i + L - 1);
                reverse = !reverse;
                i += L;
            }
        }
    };

    protected int len;

    protected long randomSeed;

    protected int expRunLen;

    private final String name;

    PermutationRules(String name){
        this.name = name;
    }

    public <T> T[] build(int len, long randomSeed, int expRunLen) {
        this.len = len;
        this.randomSeed = randomSeed;
        this.expRunLen = expRunLen;
        return buildImpl(len, randomSeed, expRunLen);
    }

    protected abstract <T> T[] buildImpl(int len, long randomSeed, int expRunLen);

    public String toString(){
        return name + "-Len:" + len + "-Seed:" + String.format("0x%X",randomSeed) + "-ExpRunLen:" + expRunLen;
    }


    private static <T> void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static <T> void sortRandomRuns(final T[] A, final int left, int right, final int expRunLen, final Random random) {
        for (int i = left; i < right; ) {
            int j = 1;
            while (random.nextInt(expRunLen) != 0) ++j;
            j = Math.min(right, i + j);
            Arrays.sort(A, i, j + 1);
            i = j + 1;
        }
    }

    /**
     * Recursively computes R_Tim(n) (see Buss and Knop 2018)
     */
    public static LinkedList<Integer> timsortDragRunlengths(int n) {
        LinkedList<Integer> res;
        if (n <= 3) {
            res = new LinkedList<>();
            res.add(n);
        } else {
            int nPrime = n / 2;
            int nPrimePrime = n - nPrime - (nPrime - 1);
            res = timsortDragRunlengths(nPrime);
            res.addAll(timsortDragRunlengths(nPrime - 1));
            res.add(nPrimePrime);
        }
        return res;
    }

    public static int total(List<Integer> l) {
        return l.stream().mapToInt(Integer::intValue).sum();
    }

    public static <T> void shuffle(final T[] A, final int left, final int right, final Random random) {
        int n = right - left + 1;
        for (int i = n; i > 1; i--)
            swap(A, left + i - 1, left + random.nextInt(i));
    }

    private static <T> void reverseRange(T[] a, int lo, int hi) {
        while (lo < hi) {
            T t = a[lo];
            a[lo++] = a[hi];
            a[hi--] = t;
        }
    }

}