//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static int n;
    static long []targets;
    static long[] powers;
    static long[][] VCN;
    static long[][] cnt;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            n = in.nextInt();

            targets = new long[n];
            long max_t = 0;
            for(int i = 0;i<n;i++){
                targets[i] = in.nextLong();
                if(max_t<targets[i]) max_t = targets[i];
            }
            //initial targets

            powers = new long[61];
            powers[0] = 1;
            for(int i = 1;i<61;i++){
                powers[i] = powers[i-1] * 2;
            }
            //initial powers

            VCN = new long[61][3];
            VCN[0][0] = 1;
            VCN[0][1] = 0;
            VCN[0][2] = 0;
            for(int i = 1;i<61;i++){
                VCN[i][0] = VCN[i-1][0]+VCN[i-1][2];
                VCN[i][1] = VCN[i-1][1]+VCN[i-1][0];
                VCN[i][2] = VCN[i-1][2]+VCN[i-1][1];
            }
            //initial VCN

            cnt = new long[n][3];
            for(int i = 0;i<n;i++){
                int pow = findPower(targets[i]);
                if(powers[pow]>targets[i]) pow--;
                cnt[i] = mirrorPlus(pow,1,targets[i]);// v:0 c:1 n:2
            }
            //calculate cnt


            for(int i = 0;i<n;i++){
                out.println(cnt[i][0]+" "+cnt[i][1]+" "+cnt[i][2]);
            }

        }

        out.close();
    }

    public static int findPower(long target){
        int mid;
        int left = 0;
        int right = 60;
        while(left<=right) {
            mid = (left+right)/2;
            if(powers[mid] == target)return mid;
            else if(powers[mid] > target)right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    public static long[] mirrorPlus(int midPow, long left, long right){
        //System.out.println(left+" "+right);

        long[] cnts = new long[3];
        long[] mirror_cnts_left = new long[3];
        long[] mirror_cnts_right = new long[3];

        if(left==1&&right==powers[midPow+1]){
            cnts = VCN[midPow+1];
            return cnts;
        }

        long mid = powers[midPow];

        if(left<=mid&&right>mid) {
            mirror_cnts_left = mirrorPlus(midPow-1,left,mid);
            mirror_cnts_right = mirrorPlus(midPow-1,1,right-mid);
        }else if(left<=mid){
            mirror_cnts_left = mirrorPlus(midPow-1,left,right);
        }else if(right>mid){
            mirror_cnts_right = mirrorPlus(midPow-1,left-mid,right-mid);
        }

        cnts[0] = mirror_cnts_left[0]+mirror_cnts_right[2];
        cnts[1] = mirror_cnts_left[1]+mirror_cnts_right[0];
        cnts[2] = mirror_cnts_left[2]+mirror_cnts_right[1];

        return cnts;
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
