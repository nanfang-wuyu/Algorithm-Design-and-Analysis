//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main2 {

    static long prime = 998244353;
    static long result = 0;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            int n = in.nextInt();
            int[] A = new int[n+1];

            long[][][] opt = new long[n+1][200+1][3];

            for(int i = 1;i<n+1;i++){
                A[i] = in.nextInt();
            }

            if(A[1]!=-1) opt[1][A[1]][0] = 1;
            else {
                for(int j = 1; j < 201; j++){
                    opt[1][j][0] = 1;
                }
            }

            for (int i = 2; i < n + 1; i++){
                result = 0;
                for(int j = 1; j < 201; j++){
                    long temp = opt[i-1][j][0] + opt[i-1][j][1] + opt[i-1][j][2];
                    if(A[i] == j || A[i] == -1){
                        opt[i][j][0] = result % prime;
                        opt[i][j][1] = temp % prime;
                    }
                    result += temp;
                }

                result = 0;
                for(int j = 200; j > 0; j--){

                    if(A[i] == j || A[i] == -1){
                        opt[i][j][2] = result % prime;
                    }
                    result += opt[i-1][j][1] + opt[i-1][j][2];
                }
            }
            result = 0;
            for(int j = 1;j<201;j++){
                result += opt[n][j][1] + opt[n][j][2];
            }

            out.println(result %= prime);
        }

        out.close();
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
