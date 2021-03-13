//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static Vertex[] V;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            int N = in.nextInt();
            V = new Vertex[N+2];
            for(int i = 1;i<N+2;i++){
                V[i] = new Vertex(i);
            }
            PriorityQueue<Edge> pq = new PriorityQueue<>(edgeComparator);
            for(int i = 1;i<N+1;i++){
                for(int j = 1;j<N-i+2;j++){
                    pq.add(new Edge(i,j+i,in.nextLong()));
                }
            }

            out.println(Kruskal(pq, N+1));
        }

        out.close();
    }

    public static class Edge{
        int u;
        int v;
        long weight;
        public Edge(int u, int v, long weight){
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    public static class Vertex{
        int key;
        boolean known = false;
        public Vertex(int key){
            this.key = key;
        }
    }

    public static class DisjSets{
        private int s[];
        public DisjSets(int elements){
            s = new int[elements+1];
            for(int i = 1;i<elements+1;i++) s[i]=-1;
        }
        public void union(int root1, int root2){
            if(s[root1]<s[root2]){
                s[root2] = root1;
            }else if(s[root1]==s[root2]){
                s[root1]--;
                s[root2] = root1;
            }else s[root1] = root2;
        }
        public int find(int x){
            if(s[x]<0) return x;
            else return s[x] = find(s[x]);
        }
    }

    public static long Kruskal(PriorityQueue<Edge>pq, int vertices){
        DisjSets ds = new DisjSets(vertices);
        int cnt = 0;
        long sum = 0;
        while(cnt!=vertices-1){
            Edge e = pq.poll();
            int uSet = ds.find(e.u);
            int vSet = ds.find(e.v);
            if(uSet!=vSet) {
                ds.union(uSet,vSet);
                sum+=e.weight;
                cnt++;
            }
        }
        return sum;
    }

    public static Comparator<Edge> edgeComparator = new Comparator<Edge>(){
        @Override
        public int compare(Edge e1, Edge e2) {
            return (int) (e1.weight-e2.weight);
        }
    };

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
