import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'd'));
        assertFalse(offByOne.equalChars('d', 'a'));
    }

    @Test
    public void testTakingUppercase() {
        assertFalse(offByOne.equalChars('B', 'a'));
        assertFalse(offByOne.equalChars('b', 'A'));
        assertTrue(offByOne.equalChars('B', 'A'));
    }

    @Test
    public void testTakingNonAlpha() {
        assertFalse(offByOne.equalChars('_', ']'));
        assertFalse(offByOne.equalChars('a', '_'));
        assertTrue(offByOne.equalChars('`', 'a'));
        assertTrue(offByOne.equalChars('_', '^'));
        assertFalse(offByOne.equalChars('`', 'A'));
    }
}
