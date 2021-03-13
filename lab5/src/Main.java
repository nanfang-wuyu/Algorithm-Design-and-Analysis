//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    public static int begin, last, clock,trickNum;
    public static PriorityQueue<trick>pqX,pqY;
    public static int[] magicShowTime;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            trickNum = in.nextInt();
            begin = 10001;
            last = 0;
            pqX = new PriorityQueue<>(firstComparator);
            for(int i = 0;i<trickNum;i++){
                int x,y;
                x = in.nextInt();
                y = in.nextInt();
                pqX.add(new trick(x,y,i));
                if(x<begin) begin = x;
                if(y>last) last = y;
            }
            int timeRange = last-begin+1;
            int trickCnt = timeRange / trickNum;

            if(trickCnt == 0)
                out.println(0);
            else
                out.println(binary_search(1,trickCnt));
        }

        out.close();
    }

    public static class trick{
        int x,y,length,key;
        public trick(int x, int y, int key){
            this.x = x;
            this.y = y;
            this.length = y-x+1;
            this.key = key;
        }
    }

    public static Comparator<trick> firstComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            return t1.x-t2.x;
        }
    };

    public static Comparator<trick> secondComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            return t1.y-t2.y;
        }
    };

    public static int binary_search(int left, int right){

        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            PriorityQueue<trick>PQx = new PriorityQueue<>(pqX);
            //System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
            if (beArranged(mid,PQx)) {
                left = mid + 1;
            } else {
                right = mid - 1;
                if(right==left-1)mid--;
            }//System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
        }
        return mid;

    }

    public static boolean beArranged(int time, PriorityQueue<trick> PQx){

        magicShowTime = new int[trickNum];

        clock = begin;

        pqY = new PriorityQueue<>(secondComparator);

        while(clock!=last+1){

            while(PQx.peek()!=null) {
                if(clock == PQx.peek().x) pqY.add(PQx.poll());
                else break;
            }

            if(pqY.peek()!=null) {
                magicShowTime[pqY.peek().key] ++;
                while(pqY.peek()!=null){
                    if(magicShowTime[pqY.peek().key] == time) {
                        pqY.poll();
                    }
                    else if(clock >= pqY.peek().y) return false;
                    else break;
                }
            }

            clock ++;
        }

        if(pqY.size()!=0) return false;
        else return true;


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
