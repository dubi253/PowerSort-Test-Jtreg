import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.AbstractMap;

public class TestInputsGenerator<T> {
    private final List<Integer> randomSeeds;
    private final List<Integer> inputLengths;

    private final int[] expectedRunLengths;

    public TestInputsGenerator(List<Integer> randomSeeds, List<Integer> inputLengths, int[] expectedRunLengths) {
        this.randomSeeds = randomSeeds;
        this.inputLengths = inputLengths;
        this.expectedRunLengths = expectedRunLengths;
    }

    /**
     * Generates a list of test inputs for the given random seeds, input lengths, and expected run lengths.
     *
     * @return a list of test inputs
     */
    public LinkedList<TestInput<?>> generate() {
        LinkedList<TestInput<?>> testInputs = new LinkedList<>();
        for (int seed : randomSeeds) {
            for (int len : inputLengths) {
                for (int expRunLen : expectedRunLengths) {
                    for (PermutationRules rule : PermutationRules.values()) {
                        T[] temp = rule.build(len, seed, expRunLen); // in order to make toString() work
                        testInputs.add(new TestInput<>(rule.toString(), temp, rule.getComparator()));
                    }
                }
            }
        }
        return testInputs;
    }
}
