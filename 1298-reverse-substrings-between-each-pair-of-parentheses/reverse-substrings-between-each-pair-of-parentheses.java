class Solution {
    public String reverseParentheses(String s) {
        StringBuilder sb = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (c == ')') {
                int lastOpen = sb.lastIndexOf("(");
                // Extract the substring inside the parentheses and reverse it
                String sub = sb.substring(lastOpen + 1);
                String reversed = new StringBuilder(sub).reverse().toString();
                // Replace the section starting at '(' with the reversed substring
                sb.replace(lastOpen, sb.length(), reversed);
            } else {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}