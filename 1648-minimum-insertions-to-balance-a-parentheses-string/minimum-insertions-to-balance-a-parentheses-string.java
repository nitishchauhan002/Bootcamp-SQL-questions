class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int open = 0; // Represents the number of required ')'

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                // If open is odd, we have a single ')' waiting for a pair.
                // We must insert a ')' right now to complete the "))" pair.
                if (open % 2 != 0) {
                    insertions++;
                    open--; // Balanced one ')'
                }
                open += 2; // Each '(' needs two ')'
            } else {
                open--; // Consumed one ')'
                
                // If open goes below 0, we received a ')' without an opening '('
                if (open < 0) {
                    insertions++; // Insert '('
                    open += 2;   // The inserted '(' adds requirement for 2 ')', 1 is already fulfilled by current ')'
                }
            }
        }

        return insertions + open;
    }
}