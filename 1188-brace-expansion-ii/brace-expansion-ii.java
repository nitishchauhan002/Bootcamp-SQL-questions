import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Stack<Character> ops = new Stack<>();
        Stack<Set<String>> values = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '{') {
                // If previous char requires concatenation (e.g., 'a{' or '}{')
                if (i > 0 && (Character.isLetter(expression.charAt(i - 1)) || expression.charAt(i - 1) == '}')) {
                    while (!ops.isEmpty() && ops.peek() == '*') {
                        evaluateTop(ops, values);
                    }
                    ops.push('*');
                }
                ops.push('{');
            } else if (c == ',') {
                while (!ops.isEmpty() && ops.peek() != '{') {
                    evaluateTop(ops, values);
                }
                ops.push('+'); // Union operation
            } else if (c == '}') {
                while (!ops.isEmpty() && ops.peek() != '{') {
                    evaluateTop(ops, values);
                }
                ops.pop(); // Pop '{'
            } else if (Character.isLetter(c)) {
                // Handle implicit concatenation operator '*' before letters if needed
                if (i > 0 && (Character.isLetter(expression.charAt(i - 1)) || expression.charAt(i - 1) == '}')) {
                    while (!ops.isEmpty() && ops.peek() == '*') {
                        evaluateTop(ops, values);
                    }
                    ops.push('*');
                }
                
                Set<String> set = new HashSet<>();
                set.add(String.valueOf(c));
                values.push(set);
            }
        }

        while (!ops.isEmpty()) {
            evaluateTop(ops, values);
        }

        List<String> result = new ArrayList<>(values.pop());
        Collections.sort(result);
        return result;
    }

    private void evaluateTop(Stack<Character> ops, Stack<Set<String>> values) {
        char op = ops.pop();
        Set<String> right = values.pop();
        Set<String> left = values.pop();
        Set<String> res = new HashSet<>();

        if (op == '+') { // Union
            res.addAll(left);
            res.addAll(right);
        } else if (op == '*') { // Cartesian Product Concatenation
            for (String l : left) {
                for (String r : right) {
                    res.add(l + r);
                }
            }
        }
        values.push(res);
    }
}