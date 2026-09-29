class Solution {
    private Boolean[][][] memo;
    private int m, n;

    public boolean hasValidPath(char[][] grid) {
        m = grid.length;
        n = grid[0].length;

        // An odd path length can never form a valid parentheses string
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        // Maximum possible open brackets at any point is (m + n) / 2
        memo = new Boolean[m][n][(m + n) / 2 + 1];

        return dfs(grid, 0, 0, 0);
    }

    private boolean dfs(char[][] grid, int r, int c, int open) {
        // Update balance count
        open += (grid[r][c] == '(') ? 1 : -1;

        // Invalid path if balance is negative or exceeds remaining step capacity
        if (open < 0 || open > (m + n) / 2) {
            return false;
        }

        // Reached destination: balance must be 0 for a valid path
        if (r == m - 1 && c == n - 1) {
            return open == 0;
        }

        // Return memoized result if available
        if (memo[r][c][open] != null) {
            return memo[r][c][open];
        }

        boolean valid = false;

        // Move Down
        if (r + 1 < m) {
            valid = valid || dfs(grid, r + 1, c, open);
        }

        // Move Right
        if (c + 1 < n) {
            valid = valid || dfs(grid, r, c + 1, open);
        }

        return memo[r][c][open] = valid;
    }
}