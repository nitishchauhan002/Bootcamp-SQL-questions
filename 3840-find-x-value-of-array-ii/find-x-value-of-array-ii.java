class Solution {
    static class SegmentTree {
        int n;
        int k;
        int[] treeProd;
        int[][] treeCount;

        public SegmentTree(int[] nums, int k) {
            this.n = nums.length;
            this.k = k;
            this.treeProd = new int[4 * n];
            this.treeCount = new int[4 * n][k];
            build(1, 0, n - 1, nums);
        }

        private void merge(int node, int left, int right) {
            treeProd[node] = (treeProd[left] * treeProd[right]) % k;

            for (int i = 0; i < k; i++) {
                treeCount[node][i] = treeCount[left][i];
            }

            int leftProd = treeProd[left];
            for (int i = 0; i < k; i++) {
                if (treeCount[right][i] > 0) {
                    int newRem = (leftProd * i) % k;
                    treeCount[node][newRem] += treeCount[right][i];
                }
            }
        }

        private void build(int node, int start, int end, int[] nums) {
            if (start == end) {
                int val = nums[start] % k;
                treeProd[node] = val;
                treeCount[node][val] = 1;
                return;
            }
            int mid = start + (end - start) / 2;
            build(2 * node, start, mid, nums);
            build(2 * node + 1, mid + 1, end, nums);
            merge(node, 2 * node, 2 * node + 1);
        }

        public void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                int rem = val % k;
                treeProd[node] = rem;
                for (int i = 0; i < k; i++) {
                    treeCount[node][i] = 0;
                }
                treeCount[node][rem] = 1;
                return;
            }
            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                update(2 * node, start, mid, idx, val);
            } else {
                update(2 * node + 1, mid + 1, end, idx, val);
            }
            merge(node, 2 * node, 2 * node + 1);
        }

        public int queryRemainder(int l, int r, int targetX) {
            int[] resCount = new int[k];
            queryHelper(1, 0, n - 1, l, r, resCount, new int[]{1});
            return resCount[targetX];
        }

        private void queryHelper(int node, int start, int end, int l, int r, int[] resCount, int[] currentProd) {
            if (l <= start && end <= r) {
                for (int i = 0; i < k; i++) {
                    if (treeCount[node][i] > 0) {
                        int rem = (currentProd[0] * i) % k;
                        resCount[rem] += treeCount[node][i];
                    }
                }
                currentProd[0] = (currentProd[0] * treeProd[node]) % k;
                return;
            }
            int mid = start + (end - start) / 2;
            if (l <= mid) {
                queryHelper(2 * node, start, mid, l, r, resCount, currentProd);
            }
            if (r > mid) {
                queryHelper(2 * node + 1, mid + 1, end, l, r, resCount, currentProd);
            }
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        SegmentTree st = new SegmentTree(nums, k);
        int[] result = new int[queries.length];
        int n = nums.length;

        for (int i = 0; i < queries.length; i++) {
            int idx = queries[i][0];
            int val = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            st.update(1, 0, n - 1, idx, val);
            result[i] = st.queryRemainder(start, n - 1, x);
        }

        return result;
    }
}