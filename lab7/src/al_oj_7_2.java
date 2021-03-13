

import java.util.*;

public class al_oj_7_2 {

    static Vertex[] V;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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

        System.out.println(Kruskal(pq, N+1));
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









}
