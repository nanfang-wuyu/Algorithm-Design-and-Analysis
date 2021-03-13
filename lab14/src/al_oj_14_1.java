

import java.util.*;

class al_oj_14_1 {
    static int[][] res;
    static int[] pre;
    static boolean[] used;
    static int[] stack;
    static int verNum;
    static int edgeNum;
    static int top;

    static int ford_fulkerson(int s, int t) {
        int cfp = Integer.MAX_VALUE;
        int maxflow = 0;
        while (dfs(s, t)) {
            for (int i = t; i != s; i = pre[i]) {
                cfp = Math.min(cfp, res[pre[i]][i]);
            }
            for (int i = t; i != s; i = pre[i]) {
                res[i][pre[i]] += cfp;
                res[pre[i]][i] -= cfp;
            }
            maxflow += cfp;
        }
        return maxflow;
    }

    static boolean dfs(int s, int t) {
        for (int i = 0; i < verNum; i++)
            used[i] = false;

        used[s] = true;
        top = -1;
        push(s);
        int[] rec = new int[verNum + 5];
        boolean flag;
        int cur;
        int i;
        while (!isEmpty()) {
            flag = false;
            cur = peek();
            for (i = rec[cur]+1; i < verNum; i++) {
                if ((!used[i]) && (res[cur][i] > 0)) {
                    flag = true;
                    pre[i] = cur;
                    used[i] = true;
                    if (i == t) return true;
                    push(i);
                    rec[cur] = i;
                    break;
                }
            }

            if (!flag) used[pop()] = false;
        }
        return false;
    }

    static void push(int u) {
        top++;
        stack[top] = u;
    }

    static int pop() {
        top--;
        return stack[top + 1];
    }

    static int peek() {
        return stack[top];
    }

    static boolean isEmpty() {
        return top == -1;
    }

    static void init() {
        res = new int[verNum + 5][verNum + 5];
        used = new boolean[verNum + 5];
        pre = new int[verNum + 5];
        stack = new int[verNum + 5];
        top = -1;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        verNum = in.nextInt();
        edgeNum = in.nextInt();
        int u, v;
        init();
        for (int i = 1; i < verNum + 1; i++) {
            if (in.nextInt() == 1) res[0][i] = 1;
            else res[i][verNum + 1] = 1;
        }

        for (int i = 1; i < edgeNum + 1; i++) {
            u = in.nextInt();
            v = in.nextInt();
            res[u][v] = 1;
            res[v][u] = 1;
        }
        edgeNum += verNum;
        verNum += 2;
        System.out.println(ford_fulkerson(0, verNum-1));
    }
}
