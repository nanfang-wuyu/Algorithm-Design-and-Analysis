
import java.util.*;

class Ford_Fulkerson {
    static int[][] res;    //残留矩阵
    static int[] pre;
    static boolean[] used;
    static int[] stack; //用于dfs，底部为0，顶部为top
    static int ver;
    static int edge;

    static int ford_Fulkerson(int s, int t) {
        //res默认初始化为0
        int cfp = Integer.MAX_VALUE;
        int maxflow = 0;
        while (dfs(s, t)) {
            int d = Integer.MAX_VALUE;
            for (int i = t; i != s; i = pre[i])
                d = Math.min(d, res[pre[i]][i]);
            for (int i = t; i != s; i = pre[i]) {
                res[pre[i]][i] -= d;
                res[i][pre[i]] += d;
            }
            maxflow += d;
        }
        return maxflow;
    }

    static boolean dfs(int s, int t) {
        for (int i = 0; i <= ver; i++) used[i] = false;
        used[s] = true;
        int top = 0;
        stack[0] = s;
        top++;
        int cur = s, i = 1;
        int[] rec = new int[ver + 5]; //记录第i个节点遍历到的位置
        boolean flag = false;
        //采用非递归的形式，找到一条增广路径后立刻终止
        while (top != 0) { //stack 不为空
            flag = false;
            cur = stack[top - 1]; //相当于peek操作
            for (i = rec[cur]+1; i <= ver; i++) {
                //取与其相邻的并且没有访问过的值大于0的点入栈
                if (used[i] || res[cur][i] <= 0) continue;
                flag = true;
                pre[i] = cur;
                used[i] = true;
                if (i == t) return true; //找到目标节点，返回true
                stack[top++] = i;    //入栈
                rec[cur] = i;
                break;
            }
            //////////////////////////////////////////////////////////////////
            //////////////////////////////////////////////////////////////////
            if (!flag) {
                used[stack[--top]] = false;
            }//回溯过程恢复设置,并pop
        }
        return false;
    }

    static void init() {
        res = new int[ver + 5][ver + 5];
        used = new boolean[ver + 5];
        pre = new int[ver + 5];
        stack = new int[ver + 5];
    }

    static void input() {
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入 点数 边数");
        ver = cin.nextInt();
        edge = cin.nextInt();
        int s, e, w;
        init();
        System.out.println("起点 终点 权值");
        for (int i = 0; i < edge; i++) {
            s = cin.nextInt();
            e = cin.nextInt();
            w = cin.nextInt();
            res[s][e] = w;
        }
    }

    public static void main(String[] args) {
        input();
        System.out.println(ford_Fulkerson(0, 4));
    }
}

/*0 1 1
        1 2 1
        2 1 1
        1 3 1
        3 1 1
        3 4 1
        2 4 1
        1*/

/*0 1 1
        0 3 1
        1 2 1
        2 1 1
        1 3 1
        3 1 1
        3 5 1
        5 3 1
        2 4 1
        4 2 1
        4 5 1
        5 4 1
        2 6 1
        4 6 1
        5 6 1*/
