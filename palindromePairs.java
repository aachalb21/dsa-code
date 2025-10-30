import java.util.*;  
public class Solution {

    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        if (words == null || words.length == 0) {
            return res;
        }

        // Build the map: word -> index
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        // Special case: "" (empty string)
        if (map.containsKey("")) {
            int blankIdx = map.get("");
            for (int i = 0; i < words.length; i++) {
                if (i == blankIdx) continue;
                if (isPalindrome(words[i])) {
                    res.add(Arrays.asList(blankIdx, i));
                    res.add(Arrays.asList(i, blankIdx));
                }
            }
        }

        // Case 1: full reverse match
        for (int i = 0; i < words.length; i++) {
            String reversed = reverseStr(words[i]);
            if (map.containsKey(reversed)) {
                int j = map.get(reversed);
                if (i != j) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        // Case 2: split and match halves
        for (int i = 0; i < words.length; i++) {
            String cur = words[i];
            for (int cut = 1; cut < cur.length(); cut++) {
                String left = cur.substring(0, cut);
                String right = cur.substring(cut);

                // If left part is palindrome, find reversed right
                if (isPalindrome(left)) {
                    String reversedRight = reverseStr(right);
                    if (map.containsKey(reversedRight) && map.get(reversedRight) != i) {
                        res.add(Arrays.asList(map.get(reversedRight), i));
                    }
                }

                // If right part is palindrome, find reversed left
                if (isPalindrome(right)) {
                    String reversedLeft = reverseStr(left);
                    if (map.containsKey(reversedLeft) && map.get(reversedLeft) != i) {
                        res.add(Arrays.asList(i, map.get(reversedLeft)));
                    }
                }
            }
        }

        return res;
    }

    // Helper function to reverse a string
    public String reverseStr(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    // Helper function to check if a string is a palindrome
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }


    public static void main(String[] args) {
        Solution sol = new Solution();
        String[] words = {"bat", "tab", "cat", "aba", ""};
        List<List<Integer>> pairs = sol.palindromePairs(words);
        System.out.println(pairs);
    }
}
