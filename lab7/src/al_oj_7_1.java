

import java.util.*;

public class al_oj_7_1 {

    static Vertex[][] V;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        V = new Vertex[n+1][m+1];
        int key = 1;
        for(int i = 1;i<n+1;i++){
            for(int j = 1;j<m+1;j++){
                V[i][j] = new Vertex(key,in.nextInt());
                key++;
            }
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>(edgeComparator);
        for(int i = 1;i<n+1;i++){
            for(int j = 1;j<m;j++){
                int u = V[i][j].key;
                int v = V[i][j+1].key;
                int weight = V[i][j].magic ^ V[i][j+1].magic;
                pq.add(new Edge(u,v,weight));
            }
        }
        for(int j = 1;j<m+1;j++){
            for(int i = 1;i<n;i++){
                int u = V[i][j].key;
                int v = V[i+1][j].key;
                int weight = V[i][j].magic ^ V[i+1][j].magic;
                pq.add(new Edge(u,v,weight));
            }
        }

        System.out.println(Kruskal(pq, n*m));
    }

    public static class Edge{
        int u;
        int v;
        int weight;
        public Edge(int u, int v, int weight){
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }

    public static class Vertex{
        int key;
        int magic;
        boolean known = false;
        public Vertex(int key, int magic){
            this.key = key;
            this.magic = magic;
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
