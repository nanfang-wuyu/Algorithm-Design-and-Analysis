import java.util.*;


public class al_oj_6_2 {

    public static class Vertex{
        ArrayList<Edge>list;
        int key,color,a,b;
        long dist;
        public Vertex(int key){
            this.key = key;
            this.color = 0;
            this.dist = max;
            this.list = new ArrayList<>();
        }

        public void setSecond(int a, int b){
            this.a = a;
            this.b = b;
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
    static long max = Long.MAX_VALUE;
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
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            vertices[u].list.add(new Edge(v,w));
        }
        for(int i = 1;i<n+1;i++){
            vertices[i].setSecond(in.nextInt(),in.nextInt());
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
        long x = vertices[u].dist;
        long diff = (x+weight)%(vertices[t].a+vertices[t].b);
        long actual_distance = weight;
        if(diff<vertices[t].a){
            actual_distance += vertices[t].a - diff;
        }
        if(vertices[t].dist>vertices[u].dist+actual_distance) {
            vertices[t].dist = vertices[u].dist + actual_distance;
            priorityQueue.add(vertices[t]);
        }
    }

    public static void print(){
        System.out.println(vertices[target].dist);
    }

    public static void main(String[] args) {
        input();
        print();
    }

}