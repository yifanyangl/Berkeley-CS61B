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
package src;

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
    }

    public void addLast(T item) {
        items[nextLast] = item;
        advanceNextLast();
    }

    public T removeFirst() {
        if (incIndexCircular(nextFirst) == nextLast) {
            return null;
        }
        retreatNextFirst();
        T itemRemoved = items[nextFirst];
        items[nextFirst] = null;
        return itemRemoved;
    }

    public T removeLast() {
        if (decIndexCircular(nextLast) == nextFirst) {
            return null;
        }
        retreatNextLast();
        T itemRemoved = items[nextLast];
        items[nextLast] = null;
        return itemRemoved;
    }

    public T get(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    public T[] toArray() {
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

    private int incIndexCircular(int index) {
        return (index + 1) % items.length;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.printDeque();
    }
}
