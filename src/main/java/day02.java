import basic.ListNode;
import basic.TreeNode;

import java.util.*;

/**
 * 刷算法题day2
 *
 * @author 11723
 * @date 2023/03/19
 */
public class day02 {

    /**
     * 逆位
     *
     * @param n n
     * @return int
     */
    public int reverseBits(int n) {
        int reverse = 0;
        for (int i = 0; i < 32 && n != 0; i++) {
            int temp = (n & 1) << (31 - i);
            reverse |= temp;
            n = n >>> 1;
        }
        return reverse;
    }

    /**
     * reverse2 jdk中的写法，用了分治的思想，每次只对一部分进行翻转
     *
     * @param i 我
     * @return int
     */
    public int reverse2(int i) {
        // HD, Figure 7-1
        i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
        i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
        i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
        i = (i << 24) | ((i & 0xff00) << 8) |
                ((i >>> 8) & 0xff00) | (i >>> 24);
        return i;
    }

    /**
     * 汉明重量
     * 查找1的个数。对1取与如果为1则计数+1
     *
     * @param n n
     * @return int
     */
    public int hammingWeight(int n) {
        int cnt = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & 1) == 1) {
                cnt++;
            }
            n >>>= 1;
        }
        return cnt;
    }

    /**
     * 树是否沿中轴对称
     * 节点上的值相同并且结构相同就算对称
     *
     * @param root 根
     * @return boolean
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetricChild(root.left, root.right);
    }

    private boolean isSymmetricChild(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        } else if (left == null || right == null || left.val != right.val) {
            return false;
        }
        return isSymmetricChild(left.left, right.right) && isSymmetricChild(left.right, right.left);
    }

    /**
     * 树是否沿中轴对称v2
     * 不递归而是使用队列进行
     *
     * @param root 根
     * @return boolean
     */
    public boolean isSymmetricV2(TreeNode root) {
        if (root == null) {
            return true;
        }
        LinkedList<TreeNode> q = new LinkedList<>();
        q.offer(root.left);
        q.offer(root.right);
        while (q.size() > 0) {
            TreeNode q1 = q.poll();
            TreeNode q2 = q.poll();
            if (q1 == null && q2 == null) {
                continue;
            } else if (q1 == null || q2 == null || q1.val != q2.val) {
                return false;
            }
            q.offer(q1.right);
            q.offer(q2.left);
            q.offer(q2.right);
            q.offer(q1.left);
        }
        return true;
    }

    /**
     * 最大深度
     * 递归写法
     *
     * @param root 根
     * @return int
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public int maxDepthV2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            ans++;
        }
        return ans;
    }

    /**
     * 排序数组转换成二叉搜索树
     * 递归解法
     *
     * @param nums 数组
     * @return {@link TreeNode}
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        if (nums.length == 1) {
            return new TreeNode(nums[0]);
        }
        int middle = nums.length / 2;
        TreeNode node = new TreeNode(nums[middle]);

        int[] sub1 = Arrays.copyOfRange(nums, 0, middle);

        node.left = sortedArrayToBST(sub1);

        int next = middle + 1;
        if (middle + 1 > nums.length) {
            next = nums.length;
        }
        int[] sub2 = Arrays.copyOfRange(nums, next, nums.length);

        node.right = sortedArrayToBST(sub2);
        return node;
    }

    /**
     * 反向打印
     * 计算长度，反向打印
     *
     * @param head 头
     * @return {@link int[]}
     */
    public int[] reversePrint(ListNode head) {
        ListNode cur = head;
        int cnt = 0;
        while (cur != null) {
            cur = cur.next;
            cnt++;
        }
        int[] ints = new int[cnt];
        for (int i = cnt - 1; i >= 0; i--) {
            ints[i] = head.val;
            head = head.next;
        }
        return ints;
    }

    /**
     * 反向打印v2
     * 用栈辅助
     *
     * @param head 头
     * @return {@link int[]}
     */
    public int[] reversePrintV2(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        int cnt = 0;
        while (head != null) {
            stack.push(head.val);
            head = head.next;
            cnt++;
        }
        int[] ints = new int[cnt];
        int i = 0;
        while (!stack.isEmpty()) {
            ints[i++] = stack.pop();
        }
        return ints;
    }

    /**
     * 用两个栈实现一个队列
     *
     * @author 11723
     * @date 2023/03/19
     */
    class CQueue {
        Stack<Integer> deleteStack;
        Stack<Integer> appendStack;

        public CQueue() {
            deleteStack = new Stack<>();
            appendStack = new Stack<>();
        }

        public void appendTail(int value) {
            appendStack.add(value);
        }

        public int deleteHead() {
            if (deleteStack.isEmpty()) {
                while (!appendStack.isEmpty()) {
                    deleteStack.add(appendStack.pop());
                }
            }
            if (deleteStack.isEmpty()) {
                return -1;
            }
            return deleteStack.pop();
        }
    }


    class MinStack {

        private ListNode head;

        private Deque<Integer> minStack;


        /**
         * initialize your data structure here.
         */
        public MinStack() {
            minStack = new ArrayDeque<>();
        }

        public void push(int x) {
            head = new ListNode(x, head);
            if (minStack.peek() != null) {
                int min = minStack.peek();
                min = Math.min(x, min);
                minStack.push(min);
            } else {
                minStack.push(x);
            }

        }

        public void pop() {
            if (head != null) {
                ListNode temp = head;
                head = head.next;
                temp.next = null;
                minStack.pop();
            }
        }

        public int top() {
            if (head != null) {
                return head.val;
            }
            return -1;
        }

        public int min() {
            if (!minStack.isEmpty()) {
                return minStack.peek();
            }
            return -1;
        }
    }

    public boolean isPalindrome(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        Object[] arr = list.toArray();

        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            if (!arr[left++].equals(arr[right--])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给你一棵二叉搜索树，请 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
     *
     * @param root 根
     * @return {@link TreeNode}
     */
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>();

        dfs(root, list);

        TreeNode first = null;
        TreeNode temp = null;
        for (Integer num : list) {
            if (temp == null) {
                first = new TreeNode(num);
                temp = first;
            }else {
                temp.right = new TreeNode(num);
                temp = temp.right;
            }
        }
        return first;

    }

    private void dfs(TreeNode root, List<Integer> list) {
        if (root == null){
            return;
        }
        dfs(root.left, list);
        list.add(root.val);
        dfs(root.right, list);
    }

}
