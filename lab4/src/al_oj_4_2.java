import java.util.*;

public class al_oj_4_2 {

    private static Scanner in;
    private static int n;
    private static int sumScore;
    private static long caseNum;
    private static int[] dif;
    private static int prime =  998244353;
    //private static match[][] matches;
    private static match[] matchCase;
    private static ArrayList<difCase> difList;



    public static class difCase{
        long df[];
        long cnt;
        long length;
        public difCase(int n, long cnt, int length){
            this.df = new long[n];
            this.cnt = cnt;
            this.length = length;
        }
    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        initial();
        input();
        int upperBound = n * (n - 1) * 3 / 2;
        int lowerBound = n * (n - 1);
        if(sumScore == lowerBound) {
            System.out.println(1);
            return;
        }else if(sumScore> upperBound || sumScore< lowerBound){
            System.out.println(0);
            return;
        }else if(sumScore == upperBound){
            initialMatchCase(4);
        }else {
            initialMatchCase(5);
        }

        DFS(1,2);

        System.out.println(caseNum);

        long b = System.currentTimeMillis();
        System.out.println(b-a);
    }

    public static void initial(){
        in = new Scanner(System.in);
        n = 0;
        sumScore = 0;
        caseNum = 0;
        difList = new ArrayList<>();
    }

    public static void initialMatchCase(int length){
        if(length == 4) {
            matchCase = new match[4];
            matchCase[0] = new match(3, 0);
            matchCase[1] = new match(2, 1);
            matchCase[2] = new match(1, 2);
            matchCase[3] = new match(0, 3);
        }else {
            matchCase = new match[5];
            matchCase[0] = new match(3,0);
            matchCase[1] = new match(2,1);
            matchCase[2] = new match(1,1);
            matchCase[3] = new match(1,2);
            matchCase[4] = new match(0,3);
        }
    }

    public static void input(){
        n = in.nextInt();
        dif = new int[n+1];
        //matches = new match[n+1][n+1];
        for(int i = 1;i<n+1;i++){
            dif[i] = in.nextInt();
            sumScore += dif[i];
        }
        /*for(int i = 1;i<n+1;i++){
            for(int j = 1;j<n+1;j++){
                if(j != i){
                    matches[i][j] = new match(0,0);
                }
            }
        }*/
    }

    public static void DFS(int i, int j){

        int x, y;

        for(int c = 0;c<matchCase.length;c++){
            x = matchCase[c].x;
            y = matchCase[c].y;
            if(j!=n) {
                if (x > dif[i]) continue;
                if (y > dif[j]) continue;
            }else {
                if(i!=n-1) {
                    if (x != dif[i]) continue;
                    if (y > dif[j]) continue;
                }else {
                    if (x != dif[i]) continue;
                    if (y != dif[j]) continue;
                }
            }
            dif[i] -= x;
            dif[j] -= y;
            int p,q;
            p = i;
            q = j;
            if(q==n){
                if(p!=n-1){
                    p++;
                    q = p + 1;
                    DFS(p,q);
                    dif[i] += x;
                    dif[j] += y;
                }
                else {
                    caseNum++;
                    dif[i] += x;
                    dif[j] += y;
                    if(caseNum>prime) caseNum %= prime;
                    return;//???
                }
            }else {
                q++;
                DFS(p,q);
                dif[i] += x;
                dif[j] += y;
            }
        }
    }


    public static class match{
        int x,y;
        public match(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void pushUpdate(match m){

    }

    public static void popUpdate(match m){

    }
}
