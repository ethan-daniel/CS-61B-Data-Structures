public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> lld = new LinkedListDeque<>();
        for (int i = 0; i != word.length(); ++i) {
            lld.addLast(word.charAt(i));
        }
        return lld;
    }

    public boolean isPalindrome(String word) {
        Deque d = wordToDeque(word);
        while (d.size() > 1) {
            char first = (char) d.removeFirst();
            char last = (char) d.removeLast();
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);

        while (d.size() > 1) {
            char first = (char) d.removeFirst();
            char last = (char) d.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
        }
        return true;
    }
}
