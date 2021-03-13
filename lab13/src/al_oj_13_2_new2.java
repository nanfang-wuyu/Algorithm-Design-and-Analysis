import java.util.*;

public class al_oj_13_2_new2 {



    static long prime = 998244353;
    static long result = 0;
    static long opt[][][];
    static long pre[][];
    static long suf[][];

    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int[] A = new int[n+1];

        opt = new long[n+1][200+1][2];
        pre = new long[200+2][2];
        suf = new long[200+2][2];

        for(int i = 1;i<n+1;i++){
            A[i] = in.nextInt();
        }

        if(A[1]!=-1) opt[1][A[1]][0] = 1;
        else {
            for(int j = 1; j < 201; j++){
                opt[1][j][0] = 1;
            }
        }

        update(1);

        for(int i = 2;i<n+1;i++){
            if(A[i]!=-1){
                opt[i][A[i]][1]=(opt[i][A[i]][1]+opt[i-1][A[i]][0]+opt[i-1][A[i]][1]) % prime;
                opt[i][A[i]][1]=(opt[i][A[i]][1]+suf[A[i]+1][1]) % prime;
                opt[i][A[i]][0]=(opt[i][A[i]][0]+pre[A[i]-1][0]+pre[A[i]-1][1]) % prime;
            }
            else{
                for(int j = 1;j<201;j++){
                    opt[i][j][1]=(opt[i][j][1]+opt[i-1][j][0]+opt[i-1][j][1]) % prime;
                    opt[i][j][1]=(opt[i][j][1]+suf[j+1][1]) % prime;
                    opt[i][j][0]=(opt[i][j][0]+pre[j-1][0]+pre[j-1][1]) % prime;
                }
            }
            update(i);
        }
        for(int i = 1;i<201;i++) {
            result = (result + opt[n][i][1]) % prime;
        }
        System.out.println(result);


    }

    public static void update(int i){
        for(int j = 1;j<201;j++){
            pre[j][0]=(pre[j-1][0]+opt[i][j][0]) % prime;
            pre[j][1]=(pre[j-1][1]+opt[i][j][1]) % prime;
        }
        for(int j = 200;j>0;j--){
            suf[j][0]=(suf[j+1][0]+opt[i][j][0]) % prime;
            suf[j][1]=(suf[j+1][1]+opt[i][j][1]) % prime;
        }
    }

}


