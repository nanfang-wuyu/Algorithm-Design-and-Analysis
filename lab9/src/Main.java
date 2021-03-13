//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static long[] lPos;
    static long[] rPos;
    static long[] powers;
    static long[] cnt;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            powers = new long[61];
            powers[0] = 1;
            for(int i = 1;i<61;i++){
                powers[i] = powers[i-1] * 2;
            }


            int n = in.nextInt();
            lPos = new long[n];
            rPos = new long[n];
            cnt = new long[n];

            for(int i = 0;i<n;i++){
                lPos[i] = in.nextLong();
                rPos[i] = in.nextLong();
            }

            for(int i = 0;i<n;i++){
                int pow = findPower(rPos[i]);
                if(powers[pow]>rPos[i]) pow--;
                cnt[i] = mirror(pow,lPos[i],rPos[i],0);// v:0 c:1
            }

            for(int i = 0;i<n;i++){
                out.println(cnt[i]);
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

    public static long mirror(int midPow, long left, long right, int target){
        long leftCnt = 0;
        long rightCnt = 0;
        long mid = powers[midPow];

        if(left==1&&right==powers[midPow+1]-1){
            //System.out.println("case0");
            if(target==0) return mid;
            else return mid-1;
        }

        if(left<mid&&right>mid) {
            leftCnt = mirror(midPow-1,left,mid-1,target);
            rightCnt = mirror(midPow-1,2*mid-right,mid-1,~target);
            //System.out.println("case1"+leftCnt+" "+rightCnt);
            if(target==0) return leftCnt+rightCnt+1;
            else return leftCnt+rightCnt;
        }else if(left<mid){
            if(right!=mid) {
                //System.out.println("case2.1");
                leftCnt = mirror(midPow-1,left,right,target);
                return leftCnt;
            }
            else {
                //System.out.println("case2.2");
                leftCnt = mirror(midPow-1,left,mid-1,target);
                if(target==0) return leftCnt+1;
                else return leftCnt;
            }

        }else if(right>mid){
            if(left!=mid) {
                //System.out.println("case3.1");
                rightCnt = mirror(midPow-1,2*mid-right,2*mid-left,~target);
                return rightCnt;
            }
            else {
                //System.out.println("case3.2");
                rightCnt = mirror(midPow-1,2*mid-right,mid-1,~target);
                if(target==0) return rightCnt+1;
                else return rightCnt;
            }
        }
        System.out.println("wrong");
        return -1;
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
