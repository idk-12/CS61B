public class Palindrome<T> {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> items = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            items.addLast(word.charAt(i));
        }
        return items;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> items = new LinkedListDeque<>();
        items = wordToDeque(word);
        for (int i = 0, j = items.size() - 1; i < items.size() / 2; i++, j--) {
            if (!(items.get(i) == items.get(j))) {
                return false;
            }
        }
        return true;
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> items = new LinkedListDeque<>();
        items = wordToDeque(word);
        for (int i = 0, j = items.size() - 1; i < items.size() / 2; i++, j--) {
            if (!(cc.equalChars(items.get(i), items.get(j)))) {
                return false;
            }
        }
        return true;
    }
}
