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

    public boolean isPalindrome(String word) {
        return isDequePalindrome(wordToDeque(word), (x, y) -> x == y);
    }


    /**
     * Returns true if the word is a palindrome according to the character comparison test provided
     * by the CharacterComparator passed in.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isDequePalindrome(wordToDeque(word), new OffByOne());
    }

    private boolean isDequePalindrome(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() == 1 || deque.size() == 0) {
            return true;
        }
        return cc.equalChars(deque.removeFirst(), deque.removeLast()) && isDequePalindrome(deque,
            cc);
    }
}
