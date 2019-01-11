/*
Project 1A.1
As your first deque implementation, you’ll build the LinkedListDeque class, which will be linked
list based.

Your operations are subject to the following rules:
- add and remove operations must not involve any looping or recursion.
  A single such operation must take “constant time”,
  i.e. execution time should not depend on the size of the deque.
- get must use iteration, not recursion.
- size must take constant time.
- The amount of memory that your program uses at any given time must be proportional to the number
  of items. For example, if you add 10,000 items to the deque, and then remove 9,999 items,
  the resulting size should be more like a deque with 1 item than 10,000. Do not maintain
  references to items that are no longer in the deque.

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

- public LinkedListDeque(): Creates an empty linked list deque.
- public T getRecursive(int index): Same as get, but uses recursion.

Your class should accept any generic type (not just integers).
You may add any private helper classes or methods in LinkedListDeque.java if you deem it necessary.
 */

public class LinkedListDeque<T> {

    private DequeNode<T> sentinel;
    private int size;

    private static class DequeNode<T> {

        T item;
        DequeNode<T> prev;
        DequeNode<T> next;

        DequeNode() {
        }

        DequeNode(T item, DequeNode<T> prev, DequeNode<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    public LinkedListDeque() {
        sentinel = new DequeNode<>();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound.", index));
        }
        DequeNode<T> node = sentinel.next;
        int i = 0;
        while (i < index && node != sentinel) {
            i++;
            node = node.next;
        }

        if (node == sentinel) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound.", index));
        }
        return node.item;
    }

    public T getRecursive(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound.", index));
        }
        return getRecursiveHelper(sentinel.next, index).item;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DequeNode<T> node = sentinel.next;
        while (node != sentinel) {
            System.out.print(node.item + " ");
            node = node.next;
        }
        if (size > 0) {
            System.out.println();
        }
    }

    public void addFirst(T item) {
        DequeNode<T> oldFirst = sentinel.next;
        DequeNode<T> newFirst = new DequeNode<>(item, sentinel, oldFirst);
        oldFirst.prev = newFirst;
        sentinel.next = newFirst;
        size += 1;
    }

    public void addLast(T item) {
        DequeNode<T> oldLast = sentinel.prev;
        DequeNode<T> newLast = new DequeNode<>(item, oldLast, sentinel);
        sentinel.prev = newLast;
        oldLast.next = newLast;
        size += 1;
    }

    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            size -= 1;
            return remove(sentinel.next);
        }
    }

    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            size -= 1;
            return remove(sentinel.prev);
        }
    }

    private DequeNode<T> getRecursiveHelper(DequeNode<T> node, int index) {
        if (node == sentinel) {
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bound.", index));
        }
        if (index == 0) {
            return node;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    private T remove(DequeNode<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
        return node.item;
    }
}
