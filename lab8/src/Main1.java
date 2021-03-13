//快读模板2：较慢，但有next()

import java.io.*;
import java.math.*;
import java.util.*;

public class Main1 {

    static long sum;

    public static void main(String[] args) {
        InputStream inputStream = System.in;// new FileInputStream("C:\\Users\\wavator\\Downloads\\test.in");
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        Task solver = new Task();
        solver.solve(in, out);
        out.close();
    }

    static class Task {

        public void solve(InputReader in, PrintWriter out) {

            int n = in.nextInt();
            for(int i = 0;i<n;i++) {
                sum = 0;
                String str = in.next();
                PriorityQueue<treeNode>pq = createPQ(str);
                if(pq.size()>1) {
                    Huffman(pq);
                    out.println(sum);
                }else out.println(pq.peek().frequency);
            }
        }
    }


    public static class treeNode{
        long frequency;
        treeNode parent;
        treeNode left;
        treeNode right;
        public treeNode(long frequency){
            this.frequency = frequency;
        }
    }

    public static treeNode Huffman(PriorityQueue<treeNode>pq){
        treeNode a = pq.poll();
        treeNode b = pq.poll();
        treeNode root = new treeNode(a.frequency+b.frequency);
        root.left = a;
        root.right = b;
        a.parent = root;
        b.parent = root;
        sum += a.frequency+b.frequency;
        if(pq.size()==0) {
            return root;
        }
        else {
            pq.add(root);
            return Huffman(pq);
        }
    }

    public static PriorityQueue<treeNode> createPQ(String str){
        PriorityQueue<treeNode> pq = new PriorityQueue<>(
                (o1, o2) -> (int) (o1.frequency-o2.frequency)
        );
        char[] letters = str.toCharArray();
        long[] arr = new long[26];
        for(int i = 0;i<str.length();i++){
            arr[letters[i]-97]++;
        }
        for(int i = 0;i<arr.length;i++){
            if(arr[i]!=0)
                pq.add(new treeNode(arr[i]));
        }
        return pq;
    }



    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public char[] nextCharArray() {
            return next().toCharArray();
        }

        //         public boolean hasNext() {
//             try {
//                 return reader.ready();
//             } catch(IOException e) {
//                 throw new RuntimeException(e);
//             }
//         }
        public boolean hasNext() {
            try {
                String string = reader.readLine();
                if (string == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(string);
                return tokenizer.hasMoreTokens();
            } catch (IOException e) {
                return false;
            }
        }

        public BigInteger nextBigInteger() {
            return new BigInteger(next());
        }

        public BigDecimal nextBigDecinal() {
            return new BigDecimal(next());
        }
    }
}