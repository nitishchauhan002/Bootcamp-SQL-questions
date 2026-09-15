#include <string>
#include <vector>

using namespace std;

class Solution {
public:
    int maxPalindromes(string s, int k) {
        int n = s.length();
        int count = 0;
        int last_end = -1; // End index of the last selected palindrome

        for (int i = 0; i < n; ++i) {
            // Expand for both odd and even length centers
            for (int j = 0; j < 2; ++j) {
                int l = i;
                int r = i + j;

                while (l >= 0 && r < n && s[l] == s[r]) {
                    int len = r - l + 1;
                    
                    // Found a valid palindrome starting after the last chosen one
                    if (len >= k && l > last_end) {
                        count++;
                        last_end = r;
                        break; // Move to next position greedily
                    }
                    l--;
                    r++;
                }
            }
        }

        return count;
    }
};