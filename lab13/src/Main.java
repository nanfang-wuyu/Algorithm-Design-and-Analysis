//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static long prime = 998244353;
    static long result = 0;
    static long opt[][][];
    static long pre[][];
    static long suf[][];

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            int n = in.nextInt();
            int[] A = new int[n+1];

            opt = new long[n+1][200+1][2];
            pre = new long[200+2][2];
            suf = new long[200+2][2];

            for(int i = 1;i<n+1;i++){
                A[i] = in.nextInt();
            }

            if(A[1]!=-1) opt[1][A[1]][0] = 1;
            else {
                for(int j = 1; j < 201; j++){
                    opt[1][j][0] = 1;
                }
            }

            update(1);

            for(int i = 2;i<n+1;i++){
                if(A[i]!=-1){
                    opt[i][A[i]][1]=(opt[i][A[i]][1]+opt[i-1][A[i]][0]+opt[i-1][A[i]][1]) % prime;
                    opt[i][A[i]][1]=(opt[i][A[i]][1]+suf[A[i]+1][1]) % prime;
                    opt[i][A[i]][0]=(opt[i][A[i]][0]+pre[A[i]-1][0]+pre[A[i]-1][1]) % prime;
                }
                else{
                    for(int j = 1;j<201;j++){
                        opt[i][j][1]=(opt[i][j][1]+opt[i-1][j][0]+opt[i-1][j][1]) % prime;
                        opt[i][j][1]=(opt[i][j][1]+suf[j+1][1]) % prime;
                        opt[i][j][0]=(opt[i][j][0]+pre[j-1][0]+pre[j-1][1]) % prime;
                    }
                }
                update(i);
            }
            for(int i = 1;i<201;i++) {
                result = (result + opt[n][i][1]) % prime;
            }
            out.println(result);
        }

        out.close();
    }

    public static void update(int i){
        for(int j = 1;j<201;j++){
            pre[j][0]=(pre[j-1][0]+opt[i][j][0]) % prime;
            pre[j][1]=(pre[j-1][1]+opt[i][j][1]) % prime;
        }
        for(int j = 200;j>0;j--){
            suf[j][0]=(suf[j+1][0]+opt[i][j][0]) % prime;
            suf[j][1]=(suf[j+1][1]+opt[i][j][1]) % prime;
        }
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
