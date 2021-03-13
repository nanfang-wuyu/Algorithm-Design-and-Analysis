import java.util.*;

public class al_oj_8_1 {

    static long sum;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for(int i = 0;i<n;i++) {
            sum = 0;
            String str = in.next();
            PriorityQueue<treeNode>pq = createPQ(str);
            if(pq.size()>1) {
                Huffman(pq);
                System.out.println(sum);
            }else System.out.println(pq.peek().frequency);
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

}
