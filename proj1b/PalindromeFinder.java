import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class outputs all palindromes in the words file in the current directory.
 */
public class PalindromeFinder {

    public static void main(String[] args) {
        int minOffset = 1;
        int maxOffset = 25;
        int minLength = 2;

        In in = new In("../library-sp18/data/words.txt");
        List<String> allWords = new ArrayList<>();
        while (!in.isEmpty()) {
            allWords.add(in.readString());
        }

        Map<Integer, List<String>> allPalindromes = new LinkedHashMap<>();
        for (int n = minOffset; n < maxOffset + 1; n++) {
            List<String> found = findPalindromesOffByN(n, minLength, allWords);
            if (!found.isEmpty()) {
                allPalindromes.put(n, found);
            }
        }

        Entry<Integer, List<String>> mostPalindrome = allPalindromes.entrySet().stream()
            .max((e1, e2) -> (e1.getValue().size() - e2.getValue().size()))
            .orElseThrow(() -> new IllegalStateException("No palindromes found"));
        System.out.println(String.format("Most palindromes: N = %d, number = %s\n",
            mostPalindrome.getKey(), mostPalindrome.getValue().size()));

        String longestPalin = allPalindromes.values().stream()
            .map(lst -> lst.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new IllegalStateException("No palindromes found")))
            .max(Comparator.comparingInt(String::length))
            .orElseThrow(() -> new IllegalStateException("No palindromes found"));
        System.out.println(String.format("Longest palindromes for any N: %s", longestPalin));
    }

    private static List<String> findPalindromesOffByN(int N, int minLength, List<String> allWords) {
        Palindrome palindrome = new Palindrome();

        List<String> result = new ArrayList<>();
        for (String word : allWords) {
            if (word.length() >= minLength && palindrome.isPalindrome(word, new OffByN(N))) {
                result.add(word);
            }
        }

        return result;
    }
}
