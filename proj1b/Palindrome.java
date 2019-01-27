public class Palindrome {

    /**
     * Given a String, wordToDeque returns a Deque where the characters appear in the same order as
     * in the String.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (char c : word.toCharArray()) {
            deque.addLast(c);
        }
        return deque;
    }
}
