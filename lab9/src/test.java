import java.util.ArrayList;

public class test {

    public static void main(String[] args) {
        int []list = {3,5,2,4,1,6,1,7};
        int []temp = new int[8];
        mergeSort(list,temp,0,7);
        System.out.println(reserveCnt);
        for(int i : list)
        System.out.println(i);
    }

    static int reserveCnt;

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
