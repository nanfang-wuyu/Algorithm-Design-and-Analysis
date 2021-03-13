//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main1 {


    public static class card{

        int w;
        int v;
        public card(int w, int v){
            this.w = w;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            int n = in.nextInt();
            int k = in.nextInt();
            int C = in.nextInt();

            card[][] cards = new card[n+1][k+1];

            for(int i = 1;i<n+1;i++){
                for(int j = 1;j<k+1;j++){
                    cards[i][j] = new card(in.nextInt(),in.nextInt()+in.nextInt());
                }
            }

            int opt[][] = new int[n+1][C+1];

            for(int w = 0;w<C;w++){
                opt[0][w] = 0;
            }

            for(int i = 1;i<n+1;i++) {
                for (int w = 1; w < C+1; w++) {
                    for (int j = 1; j < k+1; j++) {
                        if (cards[i][j].w>w){
                            opt[i][w] = Math.max(opt[i][w],opt[i-1][w]);
                        }else {
                            int max = Math.max(opt[i-1][w],
                                    opt[i-1][w-cards[i][j].w]+cards[i][j].v);
                            if(opt[i][w]<max)
                                opt[i][w] = max;
                        }
                    }
                }
            }

            out.println(opt[n][C]);
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
