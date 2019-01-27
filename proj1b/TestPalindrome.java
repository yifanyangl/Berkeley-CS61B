import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPalindrome {

    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    private static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
}
