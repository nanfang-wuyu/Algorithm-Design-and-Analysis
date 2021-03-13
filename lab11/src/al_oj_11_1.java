
import java.util.*;

public class al_oj_11_1 {


    static int n;
    static long []targets;
    static long[] powers;
    static long[][] VCN;
    static long[][] cnt;
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        n = in.nextInt();

        targets = new long[n];
        long max_t = 0;
        for(int i = 0;i<n;i++){
            targets[i] = in.nextLong();
            if(max_t<targets[i]) max_t = targets[i];
        }
        //initial targets

        powers = new long[61];
        powers[0] = 1;
        for(int i = 1;i<61;i++){
            powers[i] = powers[i-1] * 2;
        }
        //initial powers

        VCN = new long[61][3];
        VCN[0][0] = 1;
        VCN[0][1] = 0;
        VCN[0][2] = 0;
        for(int i = 1;i<61;i++){
            VCN[i][0] = VCN[i-1][0]+VCN[i-1][2];
            VCN[i][1] = VCN[i-1][1]+VCN[i-1][0];
            VCN[i][2] = VCN[i-1][2]+VCN[i-1][1];
        }
        //initial VCN

        cnt = new long[n][3];
        for(int i = 0;i<n;i++){
            int pow = findPower(targets[i]);
            if(powers[pow]>targets[i]) pow--;
            cnt[i] = mirrorPlus(pow,1,targets[i]);// v:0 c:1 n:2
        }
        //calculate cnt


        for(int i = 0;i<n;i++){
            System.out.println(cnt[i][0]+" "+cnt[i][1]+" "+cnt[i][2]);
        }

    }

    public static int findPower(long target){
        int mid;
        int left = 0;
        int right = 60;
        while(left<=right) {
            mid = (left+right)/2;
            if(powers[mid] == target)return mid;
            else if(powers[mid] > target)right = mid - 1;
            else left = mid + 1;
        }
        return left;
    }

    /*      1 1 0
            1 2 1
            2 3 3
            5 5 6
            11 10 11
            22 21 21
            */

    public static long[] mirrorPlus(int midPow, long left, long right){
        //System.out.println(left+" "+right);

        long[] cnts = new long[3];
        long[] mirror_cnts_left = new long[3];
        long[] mirror_cnts_right = new long[3];

        if(left==1&&right==powers[midPow+1]){
            cnts = VCN[midPow+1];
            return cnts;
        }

        long mid = powers[midPow];

        if(left<=mid&&right>mid) {
            mirror_cnts_left = mirrorPlus(midPow-1,left,mid);
            mirror_cnts_right = mirrorPlus(midPow-1,1,right-mid);
        }else if(left<=mid){
            mirror_cnts_left = mirrorPlus(midPow-1,left,right);
        }else if(right>mid){
            mirror_cnts_right = mirrorPlus(midPow-1,left-mid,right-mid);
        }

        cnts[0] = mirror_cnts_left[0]+mirror_cnts_right[2];
        cnts[1] = mirror_cnts_left[1]+mirror_cnts_right[0];
        cnts[2] = mirror_cnts_left[2]+mirror_cnts_right[1];

        return cnts;
    }

}
