//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main1 {


    public static class Vertex{
        ArrayList<Edge>list;
        int key,color;
        double dist;
        public Vertex(int key){
            this.key = key;
            this.color = 0;
            this.dist = max;
            this.list = new ArrayList<>();
        }
    }

    public static class Edge{
        int target,weight;
        public Edge(int target, int weight){
            this.target = target;
            this.weight = weight;
        }
    }

    static Vertex[] vertices;
    static int n, m, start, target;
    static int prime = 19260817;
    static double max = Double.MAX_VALUE;
    static Scanner in = new Scanner(System.in);
    static PriorityQueue<Vertex>priorityQueue;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            input();
            print();
        }

        out.close();
    }

    public static void input(){

        n = in.nextInt();
        m = in.nextInt();
        vertices = new Vertex[n+1];
        priorityQueue = new PriorityQueue<>(vertexComparator);
        for(int i = 1;i<n+1;i++){
            vertices[i] = new Vertex(i);
        }
        for(int i = 1;i<m+1;i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int w = in.nextInt();
            if(a==n) continue;
            vertices[a].list.add(new Edge(b,w));
            vertices[b].list.add(new Edge(a,w));
        }
        start = 1;
        target = n;
        vertices[start].color = 1;
        vertices[start].dist = 0;
        priorityQueue.add(vertices[start]);
        Dijkstra(start);
    }

    public static Comparator<Vertex> vertexComparator = new Comparator<Vertex>(){
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return (int) Math.ceil (v1.dist-v2.dist);
        }
    };

    public static void Dijkstra(int start){
        int min = start;
        for(int j = 1;j<n+1;j++) {
            for (int i = 0; i < vertices[min].list.size(); i++) {
                int t = vertices[min].list.get(i).target;
                updateDist(min, t, vertices[min].list.get(i).weight);
            }

            if(priorityQueue.size()>0) {
                do {
                    min = priorityQueue.poll().key;
                } while (vertices[min].color == 1&&priorityQueue.size()>0);
                vertices[min].color = 1;
            }else return;
        }
    }

    public static void updateDist(int u, int t, int weight){
        double logWeight = Math.log(weight)/Math.log(2);

        if(vertices[t].dist>vertices[u].dist + logWeight) {
            vertices[t].dist = vertices[u].dist + logWeight;
            priorityQueue.add(vertices[t]);
        }

    }

    public static void print(){

        if(vertices[target].dist!=max){
            double pathLength = 1;
            double i = 0;
            for(i = 1; i<=vertices[target].dist;i++){
                pathLength *= Math.pow(2,1);
                if(pathLength>prime) pathLength%=prime;
            }
            if(vertices[target].dist-i+1>0)
                pathLength *= Math.pow(2,vertices[target].dist-i+1);
            long longPath = Math.round(pathLength);
            if(longPath>prime) longPath%=prime;
            System.out.println(longPath);
        }
        else System.out.println(-1);
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
