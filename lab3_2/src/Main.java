//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static number[] numbers;
    static long[]list,power;
    static long sum;
    static long prime = 998244353;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            //Scanner in = new Scanner(System.in);

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

            out.println(sum);
        }

        out.close();
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

    public static class number{

        long value;
        int length;
        ArrayList<Long>list = new ArrayList<>();

        public number(long value, int length){
            this.value = value;
            this.length = length;
        }
    }

    static class Reader
    {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader()
        {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException
        {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException
        {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1)
            {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException
        {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do
            {
                ret = ret * 10 + c - '0';
            }  while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException
        {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException
        {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');

            if (c == '.')
            {
                while ((c = read()) >= '0' && c <= '9')
                {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException
        {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException
        {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException
        {
            if (din == null)
                return;
            din.close();
        }
    }

}
