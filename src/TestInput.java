import java.util.Comparator;

public class TestInput<T> {
    private final String name;
    private final T[] testInput;
    private final Comparator<? super T> comp;

    public TestInput(String name, T[] testInput, Comparator<? super T> comp) {
        this.name = name;
        this.testInput = testInput;
        this.comp = comp;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public T[] getInput() {
        return testInput;
    }

    public Comparator<? super T> getComparator() {
        return comp;
    }

    public boolean isSorted() {
        for (int i = 0; i < testInput.length - 1; i++) {
            if (comp.compare(testInput[i + 1], testInput[i]) < 0) {
                System.out.println(this);
                System.out.println("Unsorted: " + testInput[i] + " " + testInput[i + 1] + " at " + i);
                return false;
            }
        }
        return true;
    }
}
