class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                minOpen++;
                maxOpen++;
            } else if (c == ')') {
                minOpen--;
                maxOpen--;
            } else { // c == '*'
                minOpen--; // treating '*' as ')'
                maxOpen++; // treating '*' as '('
            }

            // More ')' than available '(' and '*' combined
            if (maxOpen < 0) {
                return false;
            }

            // minOpen cannot be negative since we can treat excess '*' as empty strings
            if (minOpen < 0) {
                minOpen = 0;
            }
        }

        // Valid if it's possible to balance all parentheses (minOpen reaches 0)
        return minOpen == 0;
    }
}