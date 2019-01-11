package src;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    /* Utility method for printing out empty checks. */
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }

    /* Prints a nice message based on whether a test passed.
     * The \n means newline. */
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }

    public static <T> boolean checkEquals(T expected, T actual) {
        boolean equals = true;
        if (expected == null) {
            if (actual != null) {
                equals = false;
            }
        } else if (!expected.equals(actual)) {
            equals = false;
        }
        if (!equals) {
            System.out.println("Got " + actual + ", but expected: " + expected);
        }
        return equals;
    }

    public static <T extends Throwable> boolean checkThrows(Class<T> expectedType,
        Runnable runnable
    ) {
        try {
            runnable.run();
        } catch (Throwable e) {
            if (!e.getClass().equals(expectedType)) {
                System.out.println("Does not throw expected " + expectedType
                    + ", but " + e);
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a few things to the list, checking isEmpty() and size() are correct, finally printing
     * the results.
     *
     * && is the "and" operation.
     */
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test.");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        passed = checkSize(1, lld1.size()) && passed;
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.addLast("middle");
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addLast("back");
        passed = checkSize(3, lld1.size()) && passed;

        System.out.println("Printing out deque: ");
        lld1.printDeque();

        printTestStatus(passed);
    }

    /**
     * Adds an item, then removes an item, and ensures that dll is empty afterwards.
     */
    public static void addRemoveTest() {

        System.out.println("Running add/remove test.");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        // should be empty
        boolean passed = checkEmpty(true, lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        passed = checkEmpty(false, lld1.isEmpty()) && passed;

        lld1.removeFirst();
        // should be empty
        passed = checkEmpty(true, lld1.isEmpty()) && passed;

        printTestStatus(passed);
    }

    public static void addFirstAndGetAndSizeTest() {
        System.out.println("Running addFirst/get/size test.");
        boolean passed = true;

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        passed = checkThrows(IndexOutOfBoundsException.class, () -> lld1.get(0)) && passed;
        lld1.addFirst(5);
        passed = checkEquals(5, lld1.get(0)) && passed;
        passed = checkSize(1, lld1.size()) && passed;

        lld1.addFirst(3);
        passed = checkEquals(3, lld1.get(0)) && passed;
        passed = checkEquals(5, lld1.get(1)) && passed;
        passed = checkSize(2, lld1.size()) && passed;

        lld1.addFirst(-1);
        lld1.addFirst(10);
        lld1.addFirst(6);
        passed = checkSize(5, lld1.size()) && passed;
        passed = checkEquals(-1, lld1.get(2)) && passed;
        passed = checkEquals(3, lld1.get(3)) && passed;
        passed = checkEquals(5, lld1.get(4)) && passed;

        passed = checkThrows(IndexOutOfBoundsException.class, () -> lld1.get(-1)) && passed;
        passed = checkThrows(IndexOutOfBoundsException.class, () -> lld1.get(5)) && passed;
        printTestStatus(passed);
    }

    public static void addLastTest() {
        System.out.println("Running addLast test.");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addLast(5);
        boolean passed = checkEquals(5, lld1.get(0));

        lld1.addLast(2);
        lld1.addLast(-9);
        lld1.addLast(4);
        passed = checkEquals(2, lld1.get(1)) && passed;
        passed = checkEquals(-9, lld1.get(2)) && passed;
        passed = checkEquals(4, lld1.get(3)) && passed;

        printTestStatus(passed);
    }

    public static void getRecursiveTest() {
        System.out.println("Running getRecursive test.");
        boolean passed = true;

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        passed = checkThrows(IndexOutOfBoundsException.class, () -> lld1.getRecursive(0)) && passed;
        lld1.addLast(5);
        passed = checkEquals(5, lld1.getRecursive(0)) && passed;

        lld1.addLast(2);
        lld1.addLast(-9);
        lld1.addLast(4);
        passed = checkEquals(2, lld1.getRecursive(1)) && passed;
        passed = checkEquals(-9, lld1.getRecursive(2)) && passed;
        passed = checkEquals(4, lld1.getRecursive(3)) && passed;

        passed =
            checkThrows(IndexOutOfBoundsException.class, () -> lld1.getRecursive(-1)) && passed;
        passed = checkThrows(IndexOutOfBoundsException.class, () -> lld1.getRecursive(4)) && passed;

        printTestStatus(passed);
    }

    public static void removeFirstTest() {
        System.out.println("Running removeFirst test.");
        boolean passed = true;

        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        passed = checkEquals(null, lld.removeFirst()) && passed;

        lld.addLast(3);
        passed = checkEquals(3, lld.removeFirst()) && passed;
        passed = checkEquals(0, lld.size()) && passed;

        lld.addLast(2);
        lld.addLast(-3);
        passed = checkEquals(2, lld.removeFirst()) && passed;
        passed = checkEquals(1, lld.size()) && passed;

        printTestStatus(passed);
    }

    public static void removeLastTest() {
        System.out.println("Running removeLast test.");
        boolean passed = true;

        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        passed = checkEquals(null, lld.removeLast()) && passed;

        lld.addLast(3);
        passed = checkEquals(3, lld.removeLast()) && passed;
        passed = checkEquals(0, lld.size()) && passed;

        lld.addLast(2);
        lld.addLast(-3);
        passed = checkEquals(-3, lld.removeLast()) && passed;
        passed = checkEquals(1, lld.size()) && passed;

        printTestStatus(passed);
    }

    public static void main(String[] args) {
        System.out.println("Running tests.\n");
        addFirstAndGetAndSizeTest();
        addLastTest();
        addIsEmptySizeTest();
        getRecursiveTest();
        removeFirstTest();
        removeLastTest();
        addRemoveTest();
    }
} 