import java.util.*;

public class test {

    public static void main(String[] args) {

       long power[] = new long[20];
       for(int i = 9;i<19;i++) power[i] = (long) Math.pow(10,i) % 998244353;
       power[19] = (long) ((Math.pow(10,10) % 998244353) * (Math.pow(10,9) % 998244353)) % 998244353;
    }

}
