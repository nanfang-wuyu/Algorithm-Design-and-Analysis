import java.util.*;

public class al_oj_13_2_new {

    static long prime = 998244353;
    static long result = 0;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int[] A = new int[n+1];

        long[][][] opt = new long[n+1][200+1][3];

        for(int i = 1;i<n+1;i++){
            A[i] = in.nextInt();
        }

        if(A[1]!=-1) opt[1][A[1]][0] = 1;
        else {
            for(int j = 1; j < 201; j++){
                opt[1][j][0] = 1;
            }
        }

        for (int i = 2; i < n + 1; i++){
            result = 0;
            for(int j = 1; j < 201; j++){
                if(A[i] == j || A[i] == -1){
                    opt[i][j][0] = result;
                    opt[i][j][1] = opt[i-1][j][0] + opt[i-1][j][1] + opt[i-1][j][2];
                    if(opt[i][j][1]>prime) opt[i][j][1] %= prime;

                }
                result += opt[i-1][j][0] + opt[i-1][j][1] + opt[i-1][j][2];
                if(result>prime) result %= prime;
            }
            result = 0;
            for(int j = 200; j > 0; j--){

                if(A[i] == j || A[i] == -1){
                    opt[i][j][2] = result;
                }
                result += opt[i-1][j][1] + opt[i-1][j][2];
                if(result>prime) result %= prime;
            }
        }
        result = 0;
        for(int j = 1;j<201;j++){
            result += opt[n][j][1] + opt[n][j][2];
            if(result>prime) result %= prime;
        }

        System.out.println(result);


    }


}


            /*if (~a[1]) dp[1][a[1]][0] = 1;
            else for (int j = 1; j <= 200; j++) dp[1][j][0] = 1;
            for (int i = 2; i <= n; i++)
            {
                sum = 0;
                for (int j = 1; j <= 200; j++)
                {
                    if (a[i] == -1 || a[i] == j)
                    {
                        dp[i][j][0] = sum;
                        dp[i][j][1] = (dp[i - 1][j][0] + dp[i - 1][j][1] + dp[i - 1][j][2]) % mod;
                    }
                    sum = (sum + dp[i - 1][j][0] + dp[i - 1][j][1] + dp[i - 1][j][2]) % mod;
                }
                sum = 0;
                for (int j = 200; j >= 1; j--)
                {
                    if (a[i] == -1 || a[i] == j) dp[i][j][2] = sum;
                    sum = (sum + dp[i - 1][j][1] + dp[i - 1][j][2]) % mod;
                }
            }
            sum = 0;
            for (int j = 1; j <= 200; j++) sum = (sum + dp[n][j][1] + dp[n][j][2]) % mod;
            printf("%lld\n", sum);
            return 0;*/

