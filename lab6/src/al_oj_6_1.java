import java.util.*;

public class al_oj_6_1 {

    public static class Vertex{
        ArrayList<Edge>list;
        int key,color,actWeight;
        double dist;
        Vertex parent;
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
    static double logPrime = Math.log(19260817)/ Math.log(2);
    static long prime = 19260817;
    static double max = Double.MAX_VALUE;
    static Scanner in = new Scanner(System.in);
    static PriorityQueue<Vertex>priorityQueue;


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
            if(v1.dist-v2.dist>0) return 1;
            else if(v1.dist == v2.dist) return 0;
            else return -1;
        }
    };

    public static void Dijkstra(int start){
        int min = start;
        for(int j = 1;j<n+1;j++) {
            for (int i = 0; i < vertices[min].list.size(); i++) {
                int t = vertices[min].list.get(i).target;
                if(vertices[t].color!=1)
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

        double logWeight = Math.log(weight) / Math.log(2);
        if (vertices[t].dist > vertices[u].dist + logWeight) {
            vertices[t].dist = vertices[u].dist + logWeight;
            priorityQueue.add(vertices[t]);
            vertices[t].parent = vertices[u];
            vertices[t].actWeight = weight;
        }

    }

    public static void print(){

            Vertex v = vertices[target];
            long finalPath = 1;
            while(v.parent!=null){
                finalPath *= v.actWeight;
                finalPath %= prime;
                v = v.parent;
            }
            System.out.println(finalPath);

    }

    public static void main(String[] args) {
        input();
        print();
    }
}