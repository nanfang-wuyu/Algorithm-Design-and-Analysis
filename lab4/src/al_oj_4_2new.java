import java.util.*;

public class al_oj_4_2new {

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

        caseNum = DFS(1,2);

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
    }

    public static long DFS(int i, int j){

        int x, y;
        long levelNum = 0;

        for(int c = 0;c<matchCase.length;c++){
            //System.out.println("i:"+i+" j:"+j+" c:"+c);
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
                    long [] dpNew = new long[n+1];
                    long length = n-p;
                    for(int a = p+1;a<n+1;a++){
                        dpNew[a] = dif[a];
                    }
                    boolean exist = false;
                    for(int b = 0;b<difList.size();b++){
                        //System.out.println("b:"+b);
                        difCase temp = difList.get(b);
                        if(length!=temp.length) continue;
                        for(int a = p+1;a<n+1;a++){
                            if(dpNew[a]!=temp.df[a]) {
                                exist = false;
                                break;
                            }else exist = true;
                        }
                        if(exist) {
                            levelNum+=temp.cnt;
                            //System.out.println(temp.cnt);
                            if(levelNum>prime) levelNum %= prime;
                            break;
                        }
                        //System.out.println(exist);
                    }

                    if(!exist) {
                        p++;
                        q = p + 1;
                        long d = DFS(p, q);
                        //System.out.println("d:"+d);
                        levelNum += d;
                        if(levelNum>prime) levelNum %= prime;
                        difCase dc = new difCase(n+1,d,n-i);
                        for(int a = i+1;a<n+1;a++){
                            dc.df[a] = dif[a];
                        }
                        difList.add(dc);
                    }
                    dif[i] += x;
                    dif[j] += y;
                }
                else {
                    levelNum++;
                    //System.out.println("levelNum:"+levelNum);
                    dif[i] += x;
                    dif[j] += y;
                    if(levelNum>prime) levelNum %= prime;
                    return levelNum;
                }
            }
            else {
                q++;
                levelNum+=DFS(p,q);
                if(levelNum>prime) levelNum %= prime;
                dif[i] += x;
                dif[j] += y;
            }
        }
        return levelNum;
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
