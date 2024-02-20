/*
 * @test
 * @library .
 * @summary Test PowerSort for correctness, time consumption, and memory usage
 * @build PowerSortTest Sorter
 * @run main/timeout=200/othervm -Xmx2048m -XX:+UnlockDiagnosticVMOptions -XX:-TieredCompilation PowerSortTest
 *
 * @author Zhan Jin
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PowerSortTest {

    public static boolean ABORT_IF_RESULT_IS_NOT_SORTED = true;
    public static final boolean TIME_ALL_RUNS_IN_ONE_MEASUREMENT = false;

    public static final boolean COUNT_MERGE_COSTS = true;

    public static <T> void main(String[] args) throws IOException {

        final int[] randomSeeds = new int[]{0xA380, 0xB747};
        final int[] inputLengths = new int[]{0, 10};
        final int[] expectedRunLengths = new int[]{24};

        TestInputs testInputs = new TestInputs(randomSeeds, inputLengths, expectedRunLengths);

        LinkedList<Map.Entry<String, T[]>> inputs = testInputs.generate();

        for (Map.Entry<String, T[]> entry : inputs) {
            System.out.println(entry.getKey() + " " + Arrays.toString(entry.getValue()));
        }






/*        List<Sorter> algos = new ArrayList<>();

        algos.add(new PowerSort(true, false, 24));


        int reps = 100;

        if (args.length >= 1) {
            reps = Integer.parseInt(args[0]);
        }

        List<Integer> sizes = Arrays.asList(1_000_000);
        if (args.length >= 2) {
            sizes = new LinkedList<>();
            for (final String size : args[1].split(",")) {
                sizes.add(Integer.parseInt(size.replaceAll("\\D","")));
            }
        }

        long seed = 42424242;
        if (args.length >= 3) {
            seed = Long.parseLong(args[2]);
        }

        TestInputs.InputGenerator inputs = TestInputs.RANDOM_PERMUTATIONS_GENERATOR;
        if (args.length >= 4) {
            if (args[3].equalsIgnoreCase("rp"))
                inputs = TestInputs.RANDOM_PERMUTATIONS_GENERATOR;
            if (args[3].startsWith("runs"))
                inputs = TestInputs.randomRunsGenerator(Integer.parseInt(
                        args[3].substring(4).replaceAll("\\D","")));
            if (args[3].startsWith("iid"))
                inputs = TestInputs.randomIidIntsGenerator(Integer.parseInt(args[3].substring(3)));
            if (args[3].startsWith("timdrag"))
                inputs = TestInputs.timsortDragGenerator(Integer.parseInt(args[3].substring(7)));
        }

        String outFileName = "PowerSortTest";
        if (args.length >= 5) outFileName = args[4];

        timeSorts(algos, reps, sizes, seed, inputs, outFileName);*/
    }
    /*public static void timeSorts(final List<Sorter> algos, final int reps, final List<Integer> sizes, final long seed, final TestInputs.InputGenerator inputs, String outFileName) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("-yyyy-MM-dd_HH-mm-ss");
        outFileName += format.format(new Date());
        outFileName += "-reps" + reps;
        outFileName += "-ns";
        for (int n : sizes) outFileName += "-" + n;
        outFileName += "-seed" + seed;
        System.out.println(outFileName);

        System.out.println("algos  = " + algos);
        System.out.println("sizes  = " + sizes);
        System.out.println("reps   = " + reps);
        System.out.println("seed   = " + seed);
        System.out.println("inputs = " + inputs);



        int warmupRounds = 12_000;
        System.out.println("Doing warmup (" + warmupRounds + " rounds)");
        // warm up
        Random random = new Random(seed);
        for (int r = 0; r < warmupRounds; ++r) {
            for (final Sorter algo : algos) {
                for (final int size : new int[]{10000, 1000, 1000}) {
                    final int[] A = inputs.next(size, random, null);
                    algo.sort(A,0,size-1);
                }
            }
        }
        System.out.println("Warmup finished!\n");
        System.out.println("Runs with individual timing (skips first run):\n");

        if (COUNT_MERGE_COSTS) {
            System.out.println("algo,ms,n,input,input-num,merge-cost");
        } else {
            System.out.println("algo,ms,n,input,input-num");
        }
        for (final Sorter algo : algos) {
            random = new Random(seed);
            final String algoName = algo.toString();
            for (final int size : sizes) {
                final WelfordVariance samples = new WelfordVariance();
                int total = 0;
                int[] A = inputs.next(size, random, null);
                for (int r = 0; r < reps; ++r) {
                    if (r != 0) A = inputs.next(size, random, A);
                    algo.zeroMergeCost();
                    final long startNanos = System.nanoTime();
                    algo.sort(A);
                    final long endNanos = System.nanoTime();
                    total += A[A.length/2];
                    if (ABORT_IF_RESULT_IS_NOT_SORTED && !Util.isSorted(A)) {
                        System.err.println("RESULT NOT SORTED!");
                        System.exit(3);
                    }
                    final double msDiff = (endNanos - startNanos) / 1e6;
                    if (r != 0) {
                        // Skip first iteration, often slower!
                        samples.addSample(msDiff);
                        if (MergesAndRuns.COUNT_MERGE_COSTS)
                            out.write(algoName+","+msDiff+","+size+","+inputs+","+r +","+ MergesAndRuns.totalMergeCosts+"\n");
                        else
                            out.write(algoName+","+msDiff+","+size+","+inputs+","+r + "\n");
                        out.flush();
                    }
                }
                System.out.println("avg-ms=" + (float) (samples.mean()) + ",\t algo=" + algoName + ", n=" + size + "     (" + total+")\t" + samples);
            }
        }
        out.write("#finished: " + format.format(new Date()) + "\n");
        out.close();

        if (TIME_ALL_RUNS_IN_ONE_MEASUREMENT) {
            System.out.println("\n\n\nRuns with overall timing (incl. input generation):");
            for (final Sorter algo : algos) {
                random = new Random(seed);
                final String algoName = algo.toString();
                for (final int size : sizes) {
                    int[] A = inputs.next(size, random, null);
                    final long startNanos = System.nanoTime();
                    int total = 0;
                    for (int r = 0; r < reps; ++r) {
                        if (r != 0) A = inputs.next(size, random, A);
                        algo.sort(A, 0, size - 1);
                        total += A[A.length / 2];
                        //					if (!Util.isSorted(A)) throw new AssertionError();
                    }
                    final long endNanos = System.nanoTime();
                    final float msDiff = (endNanos - startNanos) / 1e6f;
                    System.out.println("avg-ms=" + (msDiff / reps) + ",\t algo=" + algoName + ", n=" + size + "    (" + total + ")");
                }
            }
        }


    }*/
}
