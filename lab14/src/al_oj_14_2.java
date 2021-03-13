import java.util.*;

public class al_oj_14_2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int []A = new int[n+1];
        int []B = new int[n+1];

        for(int i = 1;i<n+1;i++){
            A[i] = in.nextInt();
            B[i] = in.nextInt();
        }

        int []arr = B.clone();
        Arrays.sort(arr);
        int sum = 0;
        for(int i = 1;i<n+1;i++){
            sum += A[i];
        }

        int curSum = 0;
        int m = 0;
        for(int i = n;i>0;i--){
            if(curSum<sum){
                curSum += arr[i];
                m++;
            }
        }
        int C = 100;
        int P = m * C - sum;
        int [][][]opt = new int[n+1][m+1][P+1];

        for(int i = 1;i<n+1;i++){
            for(int j = 1;j<m+1;j++){
                for(int k = 1;k<P+1;k++){
                    if(C-B[i]>k) opt[i][j][k] = opt[i-1][j][k];
                    else {
                        opt[i][j][k] = Math.max(opt[i-1][j][k],A[i]+opt[i-1][j-1][k-(C-B[i])]);
                    }
                }
            }
        }

        /*System.out.println(opt[n][m][P]);*/

        System.out.println(sum - opt[n][m][P]);


    }

}
