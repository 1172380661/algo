import basic.ListNode;
import basic.TreeNode;

import java.util.*;

public class day01 {


    /**
     * 最后一个字长度
     * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
     *
     * @param s 年代
     * @return int
     */
    public int lengthOfLastWord(String s) {
        String str = s.trim();
        int result = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            char[] chars = str.toCharArray();
            if (chars[i] != ' ') {
                result++;
            } else {
                break;
            }

        }
        return result;
    }


    /**
     * + 1
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/plus-one
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param digits 数字
     * @return {@link int[]}
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            digits[i] = (digits[i] + 1) % 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[len + 1];
        digits[0] = 1;
        return digits;
    }

    public String addBinary(String a, String b) {
        //从后向前遍历相加，直到有一个字符串为0，最后反转str
        StringBuffer sb = new StringBuffer();
        int aIdx = a.length() - 1;
        int bIdx = b.length() - 1;
        int i = 0;
        while (aIdx >= 0 && bIdx >= 0) {
            int n1 = a.charAt(aIdx--) - '0';
            int n2 = b.charAt(bIdx--) - '0';
            sb.append((n1 + n2 + i) % 2);
            i = (n1 + n2 + i) / 2;
        }

        while (aIdx >= 0) {
            int n1 = a.charAt(aIdx--) - '0';
            sb.append((n1 + i) % 2);
            i = (n1 + i) / 2;
        }

        while (bIdx >= 0) {
            int n1 = b.charAt(bIdx--) - '0';
            sb.append((n1 + i) % 2);
            i = (n1 + i) / 2;
        }
        if (i == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }

    public int mySqrt(int x) {
        if (x < 3 && x > 0) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }
        //二分法，先取中值判断平方是否大于x，如果大于则对中值继续取中值
        int middle = x / 2;
        int temp = 0;
        while (middle > 0) {
            long target = (long) middle * middle;
            if (target == x) {
                return middle;
            } else if (target > x) {
                if (middle - 1 == temp) {
                    return temp;
                }
                middle /= 2;
            } else {
                temp = middle;
                middle = middle + 1;
            }
        }
        return 0;

    }

    /**
     * 公式f(x) = f(x-1) + f(x-2)
     * 同时可以使用滑动窗口避免重复计算
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        int i = 0, j = 1, k = 0;
        for (int m = 1; m <= n; m++) {
            k = i + j;
            i = j;
            j = k;
        }
        return k;
    }

    /**
     * 遍历一次，当发现不同的值时才改变指针
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode p1 = head;
        ListNode p2 = head.next;
        while (p2 != null) {
            if (p1.val != p2.val) {
                p1.next = p2;
                p1 = p2;
            }
            p2 = p2.next;
        }
        p1.next = null;
        return head;
    }

    //合并数组可以考虑开辟新空间然后双指针。
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] ints = new int[n + m];
        int p1 = 0, p2 = 0;
        for (int i = 0; i < m + n; i++) {
            if (p1 >= m) {
                ints[i] = nums2[p2++];
            } else if (p2 >= n) {
                ints[i] = nums1[p1++];
            } else if (nums1[p1] >= nums2[p2]) {
                ints[i] = nums2[p2];
                p2++;
            } else {
                ints[i] = nums1[p1];
                p1++;
            }
        }
        if (m + n >= 0) System.arraycopy(ints, 0, nums1, 0, m + n);
    }

    //可以从大到小比较
    public static void mergeV2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        for (int i = m + n - 1; i >= 0; i--) {
            if (p1 < 0) {
                nums1[i] = nums2[p2--];
            } else if (p2 < 0) {
                nums1[i] = nums1[p1--];
            } else if (nums1[p1] >= nums2[p2]) {
                nums1[i] = nums1[p1--];
            } else {
                nums1[i] = nums2[p2--];
            }
        }
    }

    /**
     * 给点待特殊符号的str，判断不包含特殊符号的str是否是回文
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        StringBuffer sgood = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(Character.toLowerCase(ch));
            }
        }

        int length = sgood.length();
        int l = 0;
        int r = length - 1;
        char[] chars = sgood.toString().toCharArray();
        while (l < r) {
            if (chars[l++] != chars[r--]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindromeV2(String s) {
        char[] chars = s.toCharArray();
        int l = 0, r = chars.length - 1;
        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(chars[l])) {
                l++;
            }
            while (l < r && !Character.isLetterOrDigit(chars[r])) {
                r--;
            }
            if (Character.toLowerCase(chars[l++]) != Character.toLowerCase(chars[r--])) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasCycle(ListNode head) {
        HashSet<ListNode> map = new HashSet<>();
        while (head != null) {
            if (!map.add(head)) {
                return true;
            } else {
                head = head.next;
            }
        }
        return false;
    }

    public static boolean hasCycleV2(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }

        while (headB != null) {
            if (!set.add(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }

    public ListNode getIntersectionNodeV2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (true) {
            if (p1 == p2) {
                return p1;
            }

            p1 = p1.next == null ? headB : p1.next;
            p2 = p2.next == null ? headA : p2.next;
        }
    }

    /**
     * 回文问题：如果是数字可以转成字符串，如果是链表可以转成数组。之后使用双指针解决。时间复杂度和空间复杂度都是o(n)
     *
     * @param head
     * @return boolean
     **/
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        Object[] array = list.toArray();
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            if (array[i] == array[j]) {
                i++;
                j--;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 双指针反转链表 时间复杂度o(n) 空间复杂度o(1)
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode i1 = null;
        ListNode i2 = head;
        while (i2 != null) {
            i2 = head.next;
            head.next = i1;
            i1 = head;
            head = i2;
        }
        return i1;
    }


    /**
     * 中序遍历树
     * 递归
     *
     * @param root 根
     * @return {@link List}<{@link Integer}>
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        add(root, result);
        return result;
    }

    private void add(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        add(root.left, result);
        result.add(root.val);
        add(root.right, result);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else {
            if (p.val == q.val) {
                boolean sameTree = isSameTree(p.left, q.left);
                if (sameTree) {
                    return isSameTree(p.right, q.right);
                }
                return false;
            }
            return false;
        }
    }


    /**
     * 递归判断左右子树是否相等
     *
     * @param root 根
     * @return boolean
     */
    public boolean isSymmetric(TreeNode root) {
        if (root.left == null && root.right == null) {
            return true;
        } else if (root.left == null || root.right == null) {
            return false;
        }else {
            if (root.left.val == root.right.val){
                if (isSymmetric(root.left)){
                    return isSymmetric(root.right);
                }
            }
        }
        return false;
    }


    //[1,2,3,0,0,0]
    //3
    //[2,5,6]
    //3
    public static void main(String[] args) {
        ListNode listNode = new ListNode(2, null);
        hasCycle(new ListNode(1, listNode));
    }

}