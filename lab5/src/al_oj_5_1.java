import java.util.*;

public class al_oj_5_1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int T = in.nextInt();

        while(T-->0){
            int trickNum = in.nextInt();
            trick []tricks = new trick[trickNum];
            for(int i = 0;i<trickNum;i++){
                int x,y;
                x = in.nextInt();
                y = in.nextInt();
                tricks[i] = new trick(x,y);
            }
            PriorityQueue<trick>pq = new PriorityQueue<>(trickComparator);
            pq.addAll(Arrays.asList(tricks));

            System.out.println(selectTricks(pq,trickNum));
        }
    }

    public static Comparator<trick> trickComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            return (t1.y-t2.y);
        }
    };

    public static int selectTricks(PriorityQueue<trick>pq, int trickNum){

        int trickSelectedCnt = 0;
        int lastFinishedTime = 0;

        while(pq.size()>0) {
            trick t = pq.poll();
            if(lastFinishedTime<t.x) {
                trickSelectedCnt++;
                lastFinishedTime = t.y;
            }
        }
        return trickSelectedCnt;

    }

    public static class trick{
        int x,y;
        public trick(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

}
