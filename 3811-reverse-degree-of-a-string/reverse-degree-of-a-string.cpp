class Solution {
public:
    int reverseDegree(string s) {
        int totalSum = 0;
        for (int i = 0; i < s.length(); i++) {
            int reversedVal = 26 - (s[i] - 'a');
            totalSum += reversedVal * (i + 1);
        }
        return totalSum;
    }
};