import java.util.*;

public class Demo {


    public static void main(String[] args) {
        TestObject[] objects = new TestObject[10_000_000];  // 10 million objects
        Random random = new Random(0xC390); // 0xC390 is for COMP390

        // Generate 10 million random objects
        for (int i = 0; i < objects.length; i++) {
            int value = random.nextInt(10_000_000); // Limit to within 10_000_000 to increase the occurrence of duplicate values
            String identifier = "ID" + i; // Create a unique identifier for each object
            objects[i] = new TestObject(value, identifier);
        }

        // Print part of the objects' information before sorting
//        System.out.println("Before sorting:");
//        System.out.println(Arrays.toString(Arrays.copyOfRange(objects, 0, 10)));

        // Sort using the Comparator, and measure the time taken
        final long startNanos = System.nanoTime();
        Arrays.sort(objects, new TestObjectComparator());
        final long endNanos = System.nanoTime();

        // Print the time taken
        final float msDiff = (endNanos - startNanos) / 1e6f;
        System.out.println("Length: " + objects.length + ". Time taken: " + msDiff + "ms. JDK Version: " + System.getProperty("java.version"));

        // Print part of the objects' information after sorting
//        System.out.println("After sorting:");
//        System.out.println(Arrays.toString(Arrays.copyOfRange(objects, 0, 10)));
    }
}

class TestObject {
    private final int value;
    private final String identifier;

    public TestObject(int value, String identifier) {
        this.value = value;
        this.identifier = identifier;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + identifier + ", " + value + "}";
    }
}

class TestObjectComparator implements Comparator<TestObject> {
    @Override
    public int compare(TestObject o1, TestObject o2) {
        return Integer.compare(o1.getValue(), o2.getValue());
    }
}