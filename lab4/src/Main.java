
import java.io.*;
import java.util.*;

public class Main {

    private static int n;
    private static int[] dif;
    private static int prime =  998244353;
    private static match[] matchCase;
    private static int sofMatch, drawMatch, totalMatch;
    private static HashMap<Long, Long> hashMap = new HashMap<>();

    public static void initialMatchCase(int length){
        if(length == 4) {
            matchCase = new match[4];
            matchCase[0] = new match(3, 0);
            matchCase[1] = new match(2, 1);
            matchCase[2] = new match(1, 2);
            matchCase[3] = new match(0, 3);
        }else {
            matchCase = new match[5];
            matchCase[0] = new match(3,0);
            matchCase[1] = new match(2,1);
            matchCase[2] = new match(1,1);
            matchCase[3] = new match(1,2);
            matchCase[4] = new match(0,3);
        }
    }

    public static long DFS(int i, int j){

        if(3*(n-j+1)<dif[i]) return 0;

        int x, y;
        long levelNum = 0;

        for(int c = 0;c<matchCase.length;c++){

            x = matchCase[c].x;
            y = matchCase[c].y;

            if(j!=n) {
                if (x > dif[i]) continue;
                if (y > dif[j]) continue;
            }else {
                if(i!=n-1) {
                    if (x != dif[i]) continue;
                    if (y > dif[j]) continue;
                }else {
                    if (x != dif[i]) continue;
                    if (y != dif[j]) continue;
                }
            }

            if(x!=y){
                if(sofMatch == 0) continue;
                else sofMatch --;
            }
            else {
                if(drawMatch == 0) continue;
                else drawMatch --;
            }

            dif[i] -= x;
            dif[j] -= y;


            if(j==n){
                if(i!=n-1){

                    int []arr = dif.clone();

                    quickSort(arr,i+1,n);

                    long hash = 0;

                    for(int a = i+1;a<n+1;a++) hash = hash*31+arr[a];

                    long hashValue = hashMap.getOrDefault(hash,-1L);

                    if(hashValue!=-1L) {
                        levelNum += hashValue;
                        if (levelNum > prime) levelNum %= prime;
                    }

                    else {
                        long d = DFS(i+1, i+2);
                        hashMap.put(hash,d);
                        levelNum += d;
                        if (levelNum > prime) levelNum %= prime;
                    }

                    dif[i] += x;
                    dif[j] += y;
                    if(x!=y) sofMatch++;
                    else drawMatch++;

                }
                else {
                    dif[i] += x;
                    dif[j] += y;
                    if(x!=y) sofMatch++;
                    else drawMatch++;
                    return 1;
                }
            }
            else {
                levelNum+=DFS(i,j+1);
                if (levelNum > prime) levelNum %= prime;
                dif[i] += x;
                dif[j] += y;
                if(x!=y) sofMatch++;
                else drawMatch++;
            }
        }
        return levelNum;
    }

    public static void quickSort(int[]arr,int l,int r){
        if(l>=r){
            return;
        }
        else{Random ran = new Random();
            int i = ran.nextInt(r-l+1)+l;//0~bound-1
            int temp = arr[l];
            arr[l] = arr[i];
            arr[i] = temp;
            int v = arr[l];
            int lt = l;
            int gt = r+1;
            i = l + 1;

            while (i<gt){
                if(arr[i]<v){
                    temp = arr[lt+1];
                    arr[lt+1] = arr[i];
                    arr[i] = temp;
                    lt++;
                    i++;
                }else if(arr[i]>v){
                    temp = arr[gt-1];
                    arr[gt-1] = arr[i];
                    arr[i] = temp;
                    gt--;
                }else {i++;}
            }
            temp = arr[l];
            arr[l] = arr[lt];
            arr[lt] = temp;
            lt--;
            quickSort(arr,l,lt);
            quickSort(arr,gt,r);
        }
    }

    public static class match{
        int x,y;
        public match(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {
            n = in.nextInt();
            int sumScore = 0;
            long caseNum = 0;
            dif = new int[n+1];
            for(int i = 1;i<n+1;i++){
                dif[i] = in.nextInt();
                sumScore += dif[i];
            }

            totalMatch = n * (n-1)/2;
            sofMatch = sumScore - 2 * totalMatch;
            drawMatch = totalMatch - sofMatch;

            if(sofMatch == 0) {
                out.println(1);
                return;
            }else if(drawMatch < 0 || sofMatch < 0){
                out.println(0);
                return;
            }else if(drawMatch == 0){
                initialMatchCase(4);
            }else {
                initialMatchCase(5);
            }

            caseNum = DFS(1,2);

            out.println(caseNum);
        }

        out.close();
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