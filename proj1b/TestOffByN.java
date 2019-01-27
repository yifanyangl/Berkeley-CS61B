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

}
