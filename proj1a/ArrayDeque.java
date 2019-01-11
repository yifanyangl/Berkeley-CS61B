/*
Project 1A.2

For this implementation, your operations are subject to the following rules:

- add and remove must take constant time, except during resizing operations.
- get and size must take constant time.
- The starting size of your array should be 8.
- The amount of memory that your program uses at any given time must be proportional to the number
  of items. For example, if you add 10,000 items to the deque, and then remove 9,999 items, you
  shouldn’t still be using an array of length 10,000ish. For arrays of length 16 or more, your
  usage factor should always be at least 25%. For smaller arrays, your usage factor can be
  arbitrarily low.

Implement all the methods in “The Deque API”:

- public void addFirst(T item): Adds an item of type T to the front of the deque.
- public void addLast(T item): Adds an item of type T to the back of the deque.
- public boolean isEmpty(): Returns true if deque is empty, false otherwise.
- public int size(): Returns the number of items in the deque.
- public void printDeque(): Prints the items in the deque from first to last, separated by a space.
- public T removeFirst(): Removes and returns the item at the front of the deque.
                          If no such item exists, returns null.
- public T removeLast(): Removes and returns the item at the back of the deque.
                         If no such item exists, returns null.
- public T get(int index): Gets the item at the given index, where 0 is the front,
                           1 is the next item, and so forth.
                           If no such item exists, returns null. Must not alter the deque!

In addition, you also need to implement:

    public ArrayDeque(): Creates an empty array deque.

We strongly recommend that you treat your array as circular for this exercise. In other words,
if your front pointer is at position zero, and you addFirst, the front pointer should loop back
around to the end of the array (so the new front item in the deque will be the last item in the
underlying array).
 */

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A deque implemented using an array internally.
 *
 * <p>Invariants:
 * <ul>
 * <li>Gets all items by traversing from index nextFirst - 1 until nextLast - 1.
 * <li>nextFirst is never equal to nextLast.
 * </ul>
 */
public class ArrayDeque<T> {

    private static int INIT_LENGTH = 8;
    private static double MIN_USAGE_RATIO = 0.25;
    private static int MIN_LENGTH_USAGE_THRESHOLD = 16;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[INIT_LENGTH];
        nextFirst = (INIT_LENGTH - 1) / 2;
        nextLast = nextFirst + 1;
    }

    public int size() {
        if (nextLast > nextFirst) {
            return nextLast - nextFirst - 1;
        } else {
            return nextLast + (items.length - nextFirst - 1);
        }
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        advanceNextFirst();
        if (nextFirst == nextLast) {
            expandArray();
        }
    }

    public void addLast(T item) {
        items[nextLast] = item;
        advanceNextLast();
        if (nextFirst == nextLast) {
            expandArray();
        }
    }

    public T removeFirst() {
        if (incIndexCircular(nextFirst) == nextLast) {
            return null;
        }
        retreatNextFirst();
        T itemRemoved = items[nextFirst];
        items[nextFirst] = null;

        if (isUsageLow()) {
            shrinkArray();
        }

        return itemRemoved;
    }

    public T removeLast() {
        if (decIndexCircular(nextLast) == nextFirst) {
            return null;
        }
        retreatNextLast();
        T itemRemoved = items[nextLast];
        items[nextLast] = null;

        if (isUsageLow()) {
            shrinkArray();
        }

        return itemRemoved;
    }

    public T get(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    private T[] toArray() {
        T[] ret = (T[]) new Object[size()];
        if (nextFirst < nextLast) {
            System.arraycopy(items, nextFirst + 1, ret, 0, nextLast - nextFirst - 1);
        } else {
            int lenFirstHalf = items.length - nextFirst - 1;
            System.arraycopy(items, (nextFirst + 1) % items.length, ret, 0, lenFirstHalf);
            System.arraycopy(items, 0, ret, lenFirstHalf, nextLast);
        }
        return ret;
    }

    public void printDeque() {
        System.out.println(Arrays.stream(toArray())
            .map(String::valueOf)
            .collect(Collectors.joining(" "))
        );
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    private void advanceNextFirst() {
        nextFirst = decIndexCircular(nextFirst);
    }

    private void retreatNextFirst() {
        nextFirst = incIndexCircular(nextFirst);
    }

    private void advanceNextLast() {
        nextLast = incIndexCircular(nextLast);
    }

    private void retreatNextLast() {
        nextLast = decIndexCircular(nextLast);
    }

    private int decIndexCircular(int index) {
        index = (index - 1) % items.length;
        if (index == -1) {
            index = items.length - 1;
        }
        return index;
    }

    /**
     * Expands array multiplicatively where the factor is 2.
     *
     * <p>After allocating more memory boxes, moves items starting from index nextFirst to the very
     * end of the new array, "leaving room" for the two "colliding" indices nextFirst and nextLast.
     *
     * <p>Assumes before expanding, nextFirst and nextLast are pointing to the same position
     * which is the last null "spot" before the array becomes full. After expanding, nextFirst will
     * be set to new position.
     */
    private void expandArray() {
        int oldLen = items.length;
        int newLen = oldLen * 2;
        int lenToMove = oldLen - nextFirst - 1;
        T[] newItems = (T[]) new Object[newLen];
        // Copies items from old array to the new one up to nextFirst - 1
        System.arraycopy(items, 0, newItems, 0, nextFirst);
        // Copies items that need to be put towards the end
        System.arraycopy(
            items, incIndexCircular(nextFirst),
            newItems, newLen - lenToMove,
            lenToMove
        );
        // nextLast does not change
        nextFirst = newLen - lenToMove - 1;
        items = newItems;
    }

    /**
     * Halves the length of array.
     *
     * <p>Assumes size is less than or equal to length/2 - 2 (2 positions are for the two indices).
     */
    private void shrinkArray() {
        int oldLen = items.length;
        int newLen = oldLen / 2;
        int lenToMove;
        T[] newItems = (T[]) new Object[newLen];

        if (nextFirst < nextLast) {
            lenToMove = nextLast - nextFirst - 1;
            int newFirst = (newLen - lenToMove - 1) / 2;
            System.arraycopy(items, incIndexCircular(nextFirst), newItems, newFirst, lenToMove);
            nextFirst = decIndexCircular(newFirst);
            nextLast = incIndexCircular(nextFirst + lenToMove);
        } else {
            lenToMove = oldLen - nextFirst - 1;
            System.arraycopy(items, 0, newItems, 0, nextLast);
            System.arraycopy(
                items, incIndexCircular(nextFirst),
                newItems, newLen - lenToMove,
                lenToMove
            );
            nextFirst = newLen - lenToMove - 1;
        }
        items = newItems;
    }

    private boolean isUsageLow() {
        if (items.length >= MIN_LENGTH_USAGE_THRESHOLD) {
            return (double) size() / items.length < MIN_USAGE_RATIO;
        }
        return false;
    }

    private int incIndexCircular(int index) {
        return (index + 1) % items.length;
    }
}
