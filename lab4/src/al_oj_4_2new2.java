import java.util.*;

public class al_oj_4_2new2 {

    private static Scanner in;
    private static int n;
    private static int sumScore;
    private static long caseNum;
    private static int[] dif;
    private static int prime =  998244353;
    private static int b = 100000007;
    private static match[] matchCase;
    private static long[] hashList;

    public static void main(String[] args) {
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
    }

    public static void initial(){
        in = new Scanner(System.in);
        n = 0;
        sumScore = 0;
        caseNum = 0;
        hashList = new long[b+1];
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
        for(int i = 1;i<n+1;i++){
            dif[i] = in.nextInt();
            sumScore += dif[i];
        }
    }

    public static long DFS(int i, int j){

        int x, y;
        long levelNum = 0;

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

                    long hash = 0;
                    for(int a = i+1;a<n+1;a++){
                        hash = (hash*521+dif[a])%b;
                    }
                    //System.out.println(hash);
                    long hashValue = hashList[(int)hash] ;
                    if(hashValue!=0) {
                        if(hashValue!=-1) {
                            levelNum += hashValue;
                            if (levelNum > prime) levelNum %= prime;
                        }
                    }
                    else {
                        p++;
                        q = p + 1;
                        long d = DFS(p, q);
                        //System.out.println("d:"+d);
                        levelNum += d;
                        if(levelNum>prime) levelNum %= prime;
                        hash = 0;
                        for(int a = i+1;a<n+1;a++){
                            hash = (hash*31+dif[a])%b;
                        }
                        if(d!=0) hashList[(int)hash] = d;
                        else hashList[(int)hash] = -1;
                    }
                    dif[i] += x;
                    dif[j] += y;
                }
                else {
                    levelNum++;
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
}
