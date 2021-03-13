//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main2 {

    private static pair[] pairs;
    private static long reserveCnt;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            int n = in.nextInt();
            long t = n;
            long total = t*(t-1)/2;
            pairs = new pair[n];
            for(int i = 0;i<n;i++){
                pairs[i] = new pair(in.nextLong(),in.nextLong());
            }
            Arrays.sort(pairs, new Comparator<pair>() {
                @Override
                public int compare(pair o1, pair o2) {
                    if(o1.a<o2.a) return -1;
                    else if(o1.a==o2.a){
                        if(o1.b<o2.b) return -1;
                        else return 1;
                    }
                    else return 1;
                }
            });
            reserveCnt = 0;
            long []list = new long[n];
            for(int i = 0;i<n;i++)
                list[i] = pairs[i].b;
            long []temp = new long[n];
            mergeSort(list,temp,0,n-1);
            out.println(total-reserveCnt);
        }

        out.close();
    }

    public static class pair{
        long a;
        long b;
        public pair(long a, long b){
            this.a = a;
            this.b = b;
        }
    }

    public static void mergeSort(long[] list, long[] temp_array, int left, int right) {

        if(left<right){
            int center = (left+right)/2;
            mergeSort(list,temp_array,left,center);
            mergeSort(list,temp_array,center+1,right);
            if(list[center]>=list[center+1])
                merge(list,temp_array,left,center+1,right);
        }

    }

    public static void merge(long[] list, long[] temp_array, int left_pos, int right_pos, int right_end){

        int left_end = right_pos - 1;
        int temp_pos = left_pos;
        int begin = left_pos;
        int end = right_end;

        while(left_pos<=left_end&&right_pos<=right_end)
        {
            if(list[left_pos]<=list[right_pos])
                temp_array[temp_pos++] = list[left_pos++];
            else {
                temp_array[temp_pos++] = list[right_pos++];
                reserveCnt += left_end-left_pos+1;
            }
        }

        while(left_pos<=left_end)
            temp_array[temp_pos++] = list[left_pos++];

        while(right_pos<=right_end)
            temp_array[temp_pos++] = list[right_pos++];

        for(int i = begin;i<end+1;i++)
            list[i] = temp_array[i];
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
