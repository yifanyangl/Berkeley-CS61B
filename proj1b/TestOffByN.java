import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestOffByN {

    private CharacterComparator cc;

    @Test
    public void testOffByFive() {
        cc = new OffByN(5);
        assertTrue(cc.equalChars('a', 'f'));
        assertTrue(cc.equalChars('f', 'a'));
        assertFalse(cc.equalChars('f', 'h'));
    }

    @Test
    public void testTakingUppercaseLetters() {
        cc = new OffByN(7);
        assertTrue(cc.equalChars('A', 'H'));
        assertTrue(cc.equalChars('H', 'A'));
        assertTrue(cc.equalChars('Z', 'a'));
        assertFalse(cc.equalChars('a', 'H'));
    }

    @Test
    public void testTakingNonAlpha() {
        cc = new OffByN(2);
        assertTrue(cc.equalChars(']', '_'));
        assertTrue(cc.equalChars('a', '_'));
        assertFalse(cc.equalChars(']', '^'));
        assertFalse(cc.equalChars('a', '`'));
        assertFalse(cc.equalChars('A', '_'));
    }
}
