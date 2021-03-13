//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main2 {

    static long cnt;
    static int top;
    static long startCover;
    static long finishCover;
    static point[] points;
    static point[] stack;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            int n = in.nextInt();
            if(n==3) {
                out.println(3);
                return;
            }
            top = -1;
            cnt = 0;
            points = new point[n];
            stack = new point[n];

            int minIndex = 0;
            long minY = 0;
            for(int i = 0;i<n;i++){
                long x = in.nextLong();
                long y = in.nextLong();
                points[i] = new point(x,y);
                if(i==0){
                    minY = y;
                }else {
                    if(y<minY){
                        minIndex = i;
                        minY = y;
                    }
                }
            }

            calculateCosine(minIndex);
            judgeInside();

            cnt = top+ 1 + startCover+finishCover;
            out.println(cnt);
            /*for(int i = 0;i<top+1;i++){
                System.out.println(stack[i].x+" "+stack[i].y+" "+i);
            }*/

        }

        out.close();
    }

    public static boolean judgeOnLineOrSameSide(point p0, point p1, point p2, point p3){

        double k = 0;
        double b = 0;
        if(p1.x!=p3.x) {
            double py = p1.y - p3.y;
            double px = p1.x - p3.x;
            k = py/px;
            b = p1.y - k * p1.x;
            //System.out.println(k);
            //System.out.println(b);
            //System.out.println(p1.x+" "+p2.x+" "+p3.x);
            if((k*p0.x+b-p0.y)*(k*p2.x+b-p2.y)>0)return true;
            else return false;
        }else {

            if((p0.x-p1.x)*(p2.x-p1.x)>0)return true;
            else return false;
        }

    }

    public static void judgeInside(){

        for(int i = 0;i<points.length;i++){
            if(points[i].cos==-10) break;
            long cover = i;

            int p = i+1;
            while(p<points.length){
                if(points[i].cos==points[p].cos){
                    if(points[i].ls<points[p].ls){
                        i = p;
                    }
                    p++;
                }else break;
            }

            if(cover==0){
                cover = p-cover-1;
                startCover = cover;
            }else if(points[p].cos==-10){
                cover = p-cover-1;
                finishCover = cover;
            }

            if(top<=1){
                push(points[i]);
            }else {
                point p0 = stack[0];
                point p3 = points[i];

                while (true) {
                    point p1 = stack[top - 1];
                    point p2 = stack[top];

                    if (judgeOnLineOrSameSide(p0, p1, p2, p3)) {
                        //System.out.println("pop"+stack[top].x+" "+stack[top].y);
                        pop();
                        if (top <= 1)
                            break;
                    } else break;
                }

                push(p3);
                //if p2 and p0 are in the same side of l13, delete p2, judge again;
                //else add p3 to stack;
            }
            i = p-1;
        }
    }

    public static void calculateCosine(int minIndex){
        for(int i = 0;i<points.length;i++){
            if(points[i].x == points[minIndex].x&&points[i].y == points[minIndex].y){
                points[i].cos = -10;
                continue;
            }
            points[i].ls = Math.sqrt(Math.pow((points[i].y-points[minIndex].y),2)+
                    Math.pow((points[i].x-points[minIndex].x),2));
            points[i].cos = (points[i].x-points[minIndex].x)/points[i].ls;
        }

        push(points[minIndex]);

        Arrays.sort(points, (o1, o2) -> {
            if(o2.cos>o1.cos) return 1;
            else return -1;
        });
    }

    static class point{
        long x;
        long y;
        long cover;
        double cos;
        double ls;
        public point(long x, long y){
            this.x = x;
            this.y = y;
            this.cover = 1;
        }
    }

    public static void push(point p){
        top++;
        stack[top]=p;
    }

    public static point pop(){
        top--;
        return stack[top+1];
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
