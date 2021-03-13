//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main_1 {

    static int[][] res;
    static int[] pre;
    static boolean[] used;
    static int[] stack;
    static int verNum;
    static int edgeNum;
    static int top;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
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

        out.close();
    }

    static void init() {
        res = new int[verNum + 5][verNum + 5];
        used = new boolean[verNum + 5];
        pre = new int[verNum + 5];
        stack = new int[verNum + 5];
        top = -1;
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

    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

}
