//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static List<task>tasks;
    static List<point> points;
    static long payment;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            int n = in.nextInt();
            payment = 0;
            tasks = new ArrayList<>();
            for(int i = 0;i<n;i++){
                tasks.add(new task(in.nextLong(),in.nextLong(),in.nextLong()));
            }
            points = findActive();
            matchTP();
            out.println(addPayment());
        }

        out.close();
    }

    public static long addPayment(){
        for(point p : points) {
            if(p.Task!=null) payment += p.Task.v;
        }
        return payment;
    }

    public static void matchTP(){
        tasks.sort((o1, o2) -> (int) (o2.v-o1.v));
        for(int i = 0;i<tasks.size();i++){
            tasks.get(i).key = i;
        }
        for(int i = 0;i<tasks.size();i++){
            int x = halfSearch(tasks.get(i).s);
            find(i,x);
        }
    }

    public static int halfSearch(long target){
        int mid;
        int left = 0;
        int right = points.size()-1;
        while(left<=right) {
            mid = (left+right)/2;
            if(points.get(mid).time == target)return mid;
            else if(points.get(mid).time > target)right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    public static boolean find(int i, int x){
        task ti = tasks.get(i);
        point p = points.get(x);
        if(p.time>ti.t) return false;

        if(p.Task == null) {
            p.Task = ti;
            return true;
        }

        task tj = p.Task;
        if(ti.t>tj.t){
            return find(i,x+1);
        } else {
            if(find(tj.key,x+1)){
                p.Task = ti;
                return true;
            }
            else return false;
        }
    }

    public static List<point> findActive(){
        tasks.sort((o1, o2) -> (int) (o1.s-o2.s));
        List<point> points = new ArrayList<>();
        long t = 0;
        for(int i = 0;i<tasks.size();i++){
            t = max(t+1,tasks.get(i).s);
            points.add(new point(t));
        }
        return points;
    }

    public static long max(long o1, long o2){
        if(o1>o2) return o1;
        else return o2;
    }

    public static class point{
        int color;
        long time;
        task Task;

        public point(long time){
            this.color = 0;
            this.time = time;
        }
    }

    public static class task{
        long s, t, v;
        int key;
        public task(long s, long t, long v){
            this.s = s;
            this.t = t;
            this.v = v;
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
