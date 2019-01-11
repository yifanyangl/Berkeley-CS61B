import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ArrayDequeTest {

    private ArrayDeque<Integer> testDeque;

    private Integer[] toArray(ArrayDeque<Integer> deque) {
        Method methodToArray = null;
        try {
            methodToArray = deque.getClass().getDeclaredMethod("toArray");
        } catch (NoSuchMethodException e) {
            fail("Method toArray not found in ArrayDeque");
        }
        methodToArray.setAccessible(true);

        Object[] retMethod = null;
        try {
            retMethod = (Object[]) methodToArray.invoke(deque);
        } catch (IllegalAccessException | InvocationTargetException e) {
            fail("Failed to call toArray() on an instance of ArrayDeque: " + e.getMessage());
        }
        return Arrays.copyOf(retMethod, retMethod.length, Integer[].class);
    }

    private int length(ArrayDeque<Integer> deque) {
        Field fieldItems = null;
        try {
            fieldItems = deque.getClass().getDeclaredField("items");
        } catch (NoSuchFieldException e) {
            fail("Fields items not found in ArrayDeque");
        }
        fieldItems.setAccessible(true);

        try {
            return ((Object[]) fieldItems.get(deque)).length;
        } catch (IllegalAccessException e) {
            fail("Failed to access field items on an instance of ArrayDeque: " + e.getMessage());
        }
        return -1;
    }

    @Before
    public void setUp() {
        testDeque = new ArrayDeque<>();
    }

    @Test
    public void testAfterInit() {
        assertEquals(0, testDeque.size());
        assertArrayEquals(new Integer[]{}, toArray(testDeque));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void throwsExceptionWhenGettingItemOutOfBound() {
        testDeque.get(0);
    }

    @Test
    public void testAddFirstBeforeResizing() {
        testDeque.addFirst(4);
        assertEquals(Integer.valueOf(4), testDeque.get(0));
        assertEquals(1, testDeque.size());

        testDeque.addFirst(6);
        assertEquals(Integer.valueOf(6), testDeque.get(0));
        assertEquals(Integer.valueOf(4), testDeque.get(1));
        assertEquals(2, testDeque.size());

        for (int i = 0; i < 4; i++) {
            testDeque.addFirst(i);
        }
        assertArrayEquals(new Integer[]{3, 2, 1, 0, 6, 4}, toArray(testDeque));
    }

    @Test
    public void testAddLastBeforeResizing() {
        testDeque.addLast(4);
        assertEquals(Integer.valueOf(4), testDeque.get(0));
        assertEquals(1, testDeque.size());

        testDeque.addLast(6);
        assertEquals(Integer.valueOf(4), testDeque.get(0));
        assertEquals(Integer.valueOf(6), testDeque.get(1));
        assertEquals(2, testDeque.size());

        for (int i = 0; i < 4; i++) {
            testDeque.addLast(i);
        }
        assertArrayEquals(new Integer[]{4, 6, 0, 1, 2, 3}, toArray(testDeque));
    }

    @Test
    public void testMixAddFirstLastBeforeResizing() {
        testDeque.addLast(4);
        testDeque.addFirst(5);
        testDeque.addFirst(3);
        testDeque.addLast(-8);
        testDeque.addLast(14);
        testDeque.addFirst(67);
        testDeque.addLast(7);
        assertArrayEquals(new Integer[]{67, 3, 5, 4, -8, 14, 7}, toArray(testDeque));
    }

    @Test
    public void testRemoveFirstFromEmptyArray() {
        assertNull(testDeque.removeFirst());
    }

    @Test
    public void testRemoveFirstBeforeResizing() {
        testDeque.addLast(4);
        testDeque.addLast(3);
        testDeque.addLast(5);
        int removed = testDeque.removeFirst();
        assertArrayEquals(new Integer[]{3, 5}, toArray(testDeque));
        assertEquals(2, testDeque.size());
        assertEquals(4, removed);

        assertEquals(Integer.valueOf(3), testDeque.removeFirst());
        assertArrayEquals(new Integer[]{5}, toArray(testDeque));
    }

    @Test
    public void testRemoveLastFromEmptyArray() {
        assertNull(testDeque.removeLast());
    }

    @Test
    public void testRemoveLastBeforeResizing() {
        testDeque.addLast(4);
        testDeque.addLast(3);
        testDeque.addLast(5);
        int removed = testDeque.removeLast();
        assertArrayEquals(new Integer[]{4, 3}, toArray(testDeque));
        assertEquals(2, testDeque.size());
        assertEquals(5, removed);

        assertEquals(Integer.valueOf(3), testDeque.removeLast());
        assertArrayEquals(new Integer[]{4}, toArray(testDeque));
    }

    @Test
    public void testMegaAddFirst() {
        // Test resizing while adding to front
        int N = 1000000;
        for (int i = 0; i < N; i++) {
            testDeque.addFirst(i);
        }

        for (int i = 0; i < N; i++) {
            assertEquals(Integer.valueOf(N - 1 - i), testDeque.get(i));
        }
    }

    @Test
    public void testMegaAddLast() {
        int N = 1000000;
        for (int i = 0; i < N; i++) {
            testDeque.addLast(i);
        }

        for (int i = 0; i < N; i++) {
            assertEquals(Integer.valueOf(i), testDeque.get(i));
        }
    }

    @Test
    public void testMegaRemoveFirst() {
        int N = (int) Math.pow(2, 20);
        for (int i = 0; i < N; i++) {
            testDeque.addLast(i);
        }

        for (int i = 0; i < N - N / 4 + 1; i++) {
            testDeque.removeFirst();
        }

        assertEquals(N / 2, length(testDeque));
        assertEquals(N / 4 - 1, testDeque.size());
    }

    @Test
    public void testMegaRemoveLast() {
        int N = (int) Math.pow(2, 20);
        for (int i = 0; i < N; i++) {
            testDeque.addLast(i);
        }

        for (int i = 0; i < N - N / 4 + 1; i++) {
            testDeque.removeLast();
        }

        assertEquals(N / 2, length(testDeque));
        assertEquals(N / 4 - 1, testDeque.size());
    }

    @Test
    public void testIsEmpty() {
        assertTrue(testDeque.isEmpty());
        testDeque.addFirst(1);
        assertFalse(testDeque.isEmpty());
        testDeque.removeFirst();
        assertTrue(testDeque.isEmpty());

        int N = 100;
        for (int i = 0; i < N; i++) {
            testDeque.addLast(i);
            assertFalse(testDeque.isEmpty());
        }
        for (int i = 0; i < N; i++) {
            testDeque.removeFirst();
        }

        assertTrue(testDeque.isEmpty());
    }
}
