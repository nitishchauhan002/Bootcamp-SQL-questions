class Solution {
public:
    vector<string> maxNumOfSubstrings(string s) {
        int n = s.length();
        vector<int> l(26, INT_MAX), r(26, INT_MIN);
        
        // Step 1: Find first and last occurrences of each character
        for (int i = 0; i < n; ++i) {
            int ch = s[i] - 'a';
            l[ch] = min(l[ch], i);
            r[ch] = max(r[ch], i);
        }

        // Helper function to expand range starting from s[i]
        auto getValidRight = [&](int i) {
            int right = r[s[i] - 'a'];
            for (int j = i; j <= right; ++j) {
                if (l[s[j] - 'a'] < i) return -1; // Overlaps with a previous character
                right = max(right, r[s[j] - 'a']);
            }
            return right;
        };

        // Step 2 & 3: Find valid substrings and greedily pick
        vector<string> result;
        int last_end = -1;

        // Iterate through valid end boundaries
        for (int i = 0; i < n; ++i) {
            if (i == l[s[i] - 'a']) {
                int right = getValidRight(i);
                if (right != -1) {
                    if (i > last_end) {
                        result.push_back("");
                    }
                    last_end = right;
                    result.back() = s.substr(i, right - i + 1);
                }
            }
        }

        return result;
    }
};