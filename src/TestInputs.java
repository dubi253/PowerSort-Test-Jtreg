import java.util.*;

public class TestInputs <T> {
    private final int[] randomSeeds;
    private final int[] inputLengths;

    private final int[] expectedRunLengths;

    public TestInputs(int[] randomSeeds, int[] inputLengths, int[] expectedRunLengths) {
        this.randomSeeds = randomSeeds;
        this.inputLengths = inputLengths;
        this.expectedRunLengths = expectedRunLengths;
    }

    public LinkedList<Map.Entry<String, T[]>> generate() {
        LinkedList<Map.Entry<String, T[]>> testInputs = new LinkedList<>();
        for (int seed : randomSeeds) {
            for (int len : inputLengths) {
                for (int expRunLen : expectedRunLengths) {
                    for (PermutationRules rule : PermutationRules.values()) {
                        T[] temp = rule.build(len, seed, expRunLen); // in order to make toString() work
                        testInputs.add(new AbstractMap.SimpleEntry<>(rule.toString(), temp));
                    }
                }
            }
        }
        return testInputs;
    }
}
