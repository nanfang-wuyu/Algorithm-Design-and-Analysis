import java.util.*;

public class al_oj_13_2 {

    static long prime = 998244353;
    static long result = 0;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int[] A = new int[n+1];

        long[][][] opt = new long[n+1][200+1][2];

        for(int i = 1;i<n+1;i++){
            A[i] = in.nextInt();
        }


        for(int i = 1;i<n+1;i++){

            if(A[i]==-1){
                if(i==1){
                    for(int j = 1;j<201;j++){
                        opt[i][j][0] = 1;
                    }
                }
                else if(i==n){
                    if(A[i-1]==-1){
                        for(int p = 1;p<201;p++) {

                            opt[i][p][1] = (opt[i][p][1] + opt[i - 1][p][0] + opt[i - 1][p][1]);
                            if(opt[i][p][1] > prime) opt[i][p][1] %= prime;

                            for (int j = 1; j < p; j++) {
                                opt[i][j][1] = (opt[i][j][1] + opt[i - 1][p][1]);
                                if(opt[i][j][1]>prime) opt[i][j][1] %= prime;
                            }
                        }
                    }else {
                        int p = A[i-1];

                        opt[i][p][1] = (opt[i][p][1] + opt[i - 1][p][0] + opt[i - 1][p][1]);
                        if(opt[i][p][1]>prime) opt[i][p][1] %= prime;

                        for (int j = 1; j < p; j++) {
                            opt[i][j][1] = opt[i][j][1] + opt[i - 1][p][1];
                            if(opt[i][j][1]>prime) opt[i][j][1] %= prime;
                        }
                    }

                    for(int q = 1;q<201;q++){
                        result = (result + opt[i][q][1]);
                        if(result>prime) result %= prime;
                    }
                    System.out.println(result);
                    return;

                }
                else {
                    if(A[i-1]==-1){
                        for(int p = 1;p<201;p++) {
                            for (int j = p+1; j < 201; j++) {
                                opt[i][j][0] = (opt[i][j][0] + opt[i - 1][p][0] + opt[i - 1][p][1]);
                                if(opt[i][j][0]>prime) opt[i][j][0]%=prime;
                            }
                            opt[i][p][1] = (opt[i][p][1] + opt[i-1][p][0] + opt[i-1][p][1]);
                            if(opt[i][p][1]>prime) opt[i][p][1] %= prime;
                            for (int j = 1; j < p; j++) {
                                opt[i][j][1] = (opt[i][j][1] + opt[i - 1][p][1]);
                                if(opt[i][j][1]>prime) opt[i][j][1] %= prime;
                            }
                        }
                    }else {
                        for(int j = A[i-1]+1;j<201;j++){
                            opt[i][j][0] = (opt[i][j][0] + opt[i-1][A[i-1]][0]+opt[i-1][A[i-1]][1]);
                            if(opt[i][j][0]>prime) opt[i][j][0] %= prime;
                        }
                        opt[i][A[i-1]][1] = (opt[i][A[i-1]][1] + opt[i-1][A[i-1]][1] + opt[i-1][A[i-1]][0]);
                        if(opt[i][A[i-1]][1]>prime) opt[i][A[i-1]][1] %= prime;
                        for(int j = 1;j<A[i-1];j++){
                            opt[i][j][1] = opt[i][j][1] + opt[i-1][A[i-1]][1];
                            if(opt[i][j][1]>prime) opt[i][j][1] %= prime;
                        }
                    }
                }
            }
            else {
                if(i==1){
                    opt[i][A[i]][0] = 1;
                }
                else if(i==n){
                    if(A[i-1]!=-1){
                        if(A[i]>A[i-1]){
                            System.out.println(0);
                            return;
                        }
                        else{
                            if(A[i]==A[i-1]) {
                                opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i - 1][A[i - 1]][0] + opt[i - 1][A[i - 1]][1]);
                                if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                            }
                            else {
                                opt[i][A[i]][1] = opt[i][A[i]][1] + opt[i - 1][A[i - 1]][1];
                                if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                            }
                        }
                    }
                    else {
                        for(int p = A[i]+1;p<201;p++) {
                            opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i-1][p][1]);
                            if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                        }
                        opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i-1][A[i]][0]+opt[i-1][A[i]][1]) ;
                        if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                    }


                    System.out.println(opt[n][A[n]][1] % prime);
                    return;


                }
                else {
                    if(A[i-1]!=-1) {
                        if(A[i-1]<A[i]) {
                            if(A[i]>A[i+1]){
                                System.out.println(0);
                                return;
                            }else {
                                opt[i][A[i]][0] = (opt[i][A[i]][0] + opt[i-1][A[i-1]][0]+opt[i-1][A[i-1]][1]);
                                if(opt[i][A[i]][0]>prime) opt[i][A[i]][0]%=prime;
                            }
                        }
                        else if(A[i-1]==A[i]){
                            opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i-1][A[i-1]][0]+opt[i-1][A[i-1]][1]);
                            if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                        }
                        else{
                            opt[i][A[i]][1] = opt[i][A[i]][1] + opt[i-1][A[i-1]][1];
                            if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                        }
                    }else {

                        for(int p = A[i]+1;p<201;p++) {
                            opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i-1][p][1]);
                            if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                        }
                        for(int p = 1;p<A[i]+1;p++) {
                            opt[i][A[i]][1] = (opt[i][A[i]][1] + opt[i - 1][p][0] + opt[i - 1][p][1]);
                            if(opt[i][A[i]][1]>prime) opt[i][A[i]][1] %= prime;
                        }


                    }
                }
            }

        }
    }


}
