import java.util.Scanner;
public class al_oj_9_2 {

    static long[] lPos;
    static long[] rPos;
    static long[] powers;
    static long[] cnt;

    public static void main(String[] args) {

        powers = new long[61];
        powers[0] = 1;
        for(int i = 1;i<61;i++){
            powers[i] = powers[i-1] * 2;
        }

        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        lPos = new long[n];
        rPos = new long[n];
        cnt = new long[n];

        for(int i = 0;i<n;i++){
            lPos[i] = in.nextLong();
            rPos[i] = in.nextLong();
        }

        for(int i = 0;i<n;i++){
            int pow = findPower(rPos[i]);
            if(powers[pow]>rPos[i]) pow--;
            cnt[i] = mirror(pow,lPos[i],rPos[i],0);// v:0 c:1
        }

        for(int i = 0;i<n;i++){
            System.out.println(cnt[i]);
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

    public static long mirror(int midPow, long left, long right, int target){
        long leftCnt = 0;
        long rightCnt = 0;
        long mid = powers[midPow];

        if(left==1&&right==powers[midPow+1]-1){
            //System.out.println("case0");
            if(target==0) return mid;
            else return mid-1;
        }

        if(left<mid&&right>mid) {
            leftCnt = mirror(midPow-1,left,mid-1,target);
            rightCnt = mirror(midPow-1,2*mid-right,mid-1,~target);
            //System.out.println("case1"+leftCnt+" "+rightCnt);
            if(target==0) return leftCnt+rightCnt+1;
            else return leftCnt+rightCnt;
        }else if(left<mid){
            if(right!=mid) {
                //System.out.println("case2.1");
                leftCnt = mirror(midPow-1,left,right,target);
                return leftCnt;
            }
            else {
                //System.out.println("case2.2");
                leftCnt = mirror(midPow-1,left,mid-1,target);
                if(target==0) return leftCnt+1;
                else return leftCnt;
            }

        }else if(right>mid){
            if(left!=mid) {
                //System.out.println("case3.1");
                rightCnt = mirror(midPow-1,2*mid-right,2*mid-left,~target);
                return rightCnt;
            }
            else {
                //System.out.println("case3.2");
                rightCnt = mirror(midPow-1,2*mid-right,mid-1,~target);
                if(target==0) return rightCnt+1;
                else return rightCnt;
            }
        }
        System.out.println("wrong");
        return -1;
    }


}












