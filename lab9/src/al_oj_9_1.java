import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class al_oj_9_1 {


    private static pair[] pairs;
    private static long reserveCnt;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long t = n;
        long total = t*(t-1)/2;
        pairs = new pair[n];
        for(int i = 0;i<n;i++){
            pairs[i] = new pair(in.nextInt(),in.nextInt());
        }
        Arrays.sort(pairs, Comparator.comparingInt(o -> o.a));
        reserveCnt = 0;
        int []list = new int[n];
        for(int i = 0;i<n;i++)
            list[i] = pairs[i].b;
        int []temp = new int[n];
        mergeSort(list,temp,0,n-1);
        System.out.println(total-reserveCnt);
    }

    public static class pair{
        int a;
        int b;
        public pair(int a, int b){
            this.a = a;
            this.b = b;
        }
    }

    public static void mergeSort(int[] list, int[] temp_array, int left, int right) {

        if(left<right){
            int center = (left+right)/2;
            mergeSort(list,temp_array,left,center);
            mergeSort(list,temp_array,center+1,right);
            if(list[center]>=list[center+1])
                merge(list,temp_array,left,center+1,right);
        }

    }

    public static void merge(int[] list, int[] temp_array, int left_pos, int right_pos, int right_end){

        int left_end = right_pos - 1;
        int temp_pos = left_pos;
        int begin = left_pos;
        int end = right_end;

        while(left_pos<=left_end&&right_pos<=right_end)
        {
            if(list[left_pos]<=list[right_pos])
                temp_array[temp_pos++] = list[left_pos++];
            else {
                temp_array[temp_pos++] = list[right_pos++];
                reserveCnt += left_end-left_pos+1;
            }
        }

        while(left_pos<=left_end)
            temp_array[temp_pos++] = list[left_pos++];

        while(right_pos<=right_end)
            temp_array[temp_pos++] = list[right_pos++];

        for(int i = begin;i<end+1;i++)
            list[i] = temp_array[i];
    }

}
