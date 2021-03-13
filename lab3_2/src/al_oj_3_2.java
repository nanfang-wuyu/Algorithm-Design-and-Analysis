import java.io.*;
import java.util.*;

public class al_oj_3_2 {
    static number[] numbers;
    static long[]list,power;
    static long sum;
    static long prime = 998244353;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        list = new long[11];
        power = new long[20];
        int n = in.nextInt();
        numbers = new number[n];
        sum = 0;

        for(int i = 0;i<19;i++) power[i] = (long) Math.pow(10,i) % prime;
        power[19] = (long) ((Math.pow(10,10) % prime) * (Math.pow(10,9) % prime)) % prime;

        for(int i = 0;i<n;i++){
            long number = in.nextLong();
            String str = String.valueOf(number);
            int length = str.length();
            list [length] ++;
            numbers[i] = new number(number,length);
            numbers[i].list.add(null);
            for(int j = 0;j<length;j++){
                long d = number % 10;
                number = number/10;
                numbers[i].list.add(d);
            }
        }

        shuffle(n);

        System.out.println(sum);
    }

    public static class number{

        long value;
        int length;
        ArrayList<Long>list = new ArrayList<>();

        public number(long value, int length){
            this.value = value;
            this.length = length;
        }
    }

    public static void shuffle(int n){
        for(int i = 0;i<n;i++){
            int length = numbers[i].length;
            for(int j = 1;j<length+1;j++){
                if(numbers[i].list.get(j)==0) continue;
                for(int k = 1;k<11;k++){
                    if(list[k]==0) continue;
                    int ld;
                    int hd;
                    if(k>=length) {
                        ld = 2*j-1;
                        hd = 2*j;
                    }else {
                        if(j<=k){
                            ld = 2*j-1;
                            hd = 2*j;
                        }else {
                            ld = j+k;
                            hd = ld;
                        }
                    }
                    sum += list[k]*numbers[i].list.get(j)*(power[ld-1]+power[hd-1]) % prime;
                    sum = sum % prime;
                }
            }
        }
    }




}
