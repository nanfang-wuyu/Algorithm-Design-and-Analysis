import java.util.*;

public class al_oj_4_1 {

    static Scanner in;
    static int n, validPath, path, obs, cnt, top;
    static Vertex [][] V;
    static Vertex []arr;

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        initial();
        input();
        pushUpdate(V[1][1]);
        DFS(V[1][1]);
        System.out.println(cnt);
        long b = System.currentTimeMillis();
        System.out.println(b-a);
    }

    public static void initial(){
        in = new Scanner(System.in);
        n = in.nextInt();
        validPath = 0;
        path = 0;
        obs = 0;
        cnt = 0;
        top = -1;
        V = new Vertex[n+1][n+1];
        arr = new Vertex[n*n+1];
    }

    public static void input(){
        String str = "";
        //System.out.println("change this");
        for(int i = 1;i<n+1;i++){
            str = in.next();
            for(int j = 1;j<n+1;j++){
                if(str.charAt(j-1) == 'x'){
                    V[i][j] = new Vertex(i,j,false);
                    obs ++;
                }
                else V[i][j] = new Vertex(i,j,true);
            }
        }

        for(int i = 1;i<n+1;i++){
            for(int j = 1;j<n+1;j++){
                if(i!=1) if(V[i-1][j].beRoad) V[i][j].list.add(V[i-1][j]);
                if(i!=n) if(V[i+1][j].beRoad) V[i][j].list.add(V[i+1][j]);
                if(j!=1) if(V[i][j-1].beRoad) V[i][j].list.add(V[i][j-1]);
                if(j!=n) if(V[i][j+1].beRoad) V[i][j].list.add(V[i][j+1]);
                V[i][j].totalList = new ArrayList<>(V[i][j].list);
            }
        }
        validPath = n*n-obs;
    }

    public static void DFS(Vertex u){
        Vertex a = u;
        while (top != -1) {
            //System.out.println(a.x+" "+a.y);
            if(a == V[n][1]) {
                //System.out.println("path:"+path);
                if(path == validPath) {
                    cnt++;
                }
                popUpdate(a);
                if(top==-1) return;
                a = arr[top];
                continue;
            }

            if(cntReachable(a) == 0) {
                //System.out.println("case1");
                popUpdate(a);
                if(top==-1) return;
                a = arr[top];
            }

            else {
                int onlyOne = 0;
                Vertex vOnlyOne = null;
                for(int i = 0;i<a.list.size();i++){
                    Vertex v = a.list.get(i);
                    if(v.color==1) {
                        a.list.remove(v);
                        i--;
                    }
                }

                for(int i = 0;i<a.list.size();i++){
                    Vertex v = a.list.get(i);
                    if(cntReachable(v)==1) {
                        if(v!=V[n][1] || path==validPath-1){
                            onlyOne++;
                            vOnlyOne = v;
                        }else a.list.remove(v);
                    }
                }

                if(onlyOne==1) {
                    //System.out.println("case2.1");
                    a.list.clear();
                    pushUpdate(vOnlyOne);
                    a = vOnlyOne;
                }

                else if(onlyOne>1){
                    //System.out.println("case2.2");
                    popUpdate(a);
                    if(top==-1) return;
                    a = arr[top];
                }

                else {

                    for(int i = 0;i<a.list.size();i++){
                        Vertex v = a.list.get(i);
                        if (cntReachable(v) == 0 && v != V[n][1]) {
                            a.list.remove(v);
                            i--;
                        }
                    }

                    if (a.list.size() > 0) {
                        //System.out.println("case3.1");

                        Vertex v = a.list.get(0);
                        a.list.remove(v);
                        pushUpdate(v);
                        a = v;

                    }

                    else {
                        //System.out.println("case3.2");
                        popUpdate(a);
                        if(top==-1) return;
                        a = arr[top];
                    }
                }
            }
        }
    }

    public static int cntReachable(Vertex u){

        int reachable = 0;
        for(Vertex v : u.list)
            if(v.color == 0) reachable ++;
        return reachable;

    }

    public static class Vertex{
        ArrayList<Vertex>list = new ArrayList<>();
        ArrayList<Vertex>totalList;
        int x, y, color;
        boolean beRoad;
        public Vertex(int x, int y, boolean beRoad){
            this.x = x;
            this.y = y;
            this.color = 0;
            this.beRoad = beRoad;
        }
    }

    public static void pushUpdate(Vertex v){
        if(top<arr.length) {
            top++;
            arr[top] = v;
        }
        path++;
        v.color = 1;
    }

    public static void popUpdate(Vertex v){
        top--;
        path--;
        v.color = 0;
        v.list = new ArrayList<>(v.totalList);
    }
}
