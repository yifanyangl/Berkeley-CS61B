package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import src.ArrayDeque;

public class ArrayDequeTest {

    private ArrayDeque<Integer> testDeque;

    @Before
    public void setUp() {
        testDeque = new ArrayDeque<>();
    }

    @Test
    public void testAfterInit() {
        assertEquals(0, testDeque.size());
        assertArrayEquals(new Integer[]{}, testDeque.toArray());
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
        assertArrayEquals(new Integer[]{3, 2, 1, 0, 6, 4}, testDeque.toArray());
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
        assertArrayEquals(new Integer[]{4, 6, 0, 1, 2, 3}, testDeque.toArray());
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
        assertArrayEquals(new Integer[]{67, 3, 5, 4, -8, 14, 7}, testDeque.toArray());
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
        assertArrayEquals(new Integer[]{3, 5}, testDeque.toArray());
        assertEquals(2, testDeque.size());
        assertEquals(4, removed);

        assertEquals(Integer.valueOf(3), testDeque.removeFirst());
        assertArrayEquals(new Integer[]{5}, testDeque.toArray());
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
        assertArrayEquals(new Integer[]{4, 3}, testDeque.toArray());
        assertEquals(2, testDeque.size());
        assertEquals(5, removed);

        assertEquals(Integer.valueOf(3), testDeque.removeLast());
        assertArrayEquals(new Integer[]{4}, testDeque.toArray());
    }
}
