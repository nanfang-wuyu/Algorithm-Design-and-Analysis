import java.util.*;

public class al_oj_5_2new {

    public static trick tricks[];
    public static int begin, last;
    public static PriorityQueue<trick>pq;
    public static boolean[]beUsed;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int trickNum = in.nextInt();
        begin = 10001;
        last = 0;
        tricks = new trick[trickNum];
        for(int i = 0;i<trickNum;i++){
            int x,y;
            x = in.nextInt();
            y = in.nextInt();
            tricks[i] = new trick(x,y);
            if(x<begin) begin = x;
            if(y>last) last = y;
        }
        pq = new PriorityQueue<>(trickComparator);
        pq.addAll(Arrays.asList(tricks));
        int timeRange = last-begin+1;
        int trickCnt = timeRange / trickNum;
        beUsed = new boolean[last+1];
        //System.out.println("begin:"+begin+"last:"+last);
        //System.out.println(trickCnt);
        if(trickCnt == 0)
            System.out.println(0);
        else
            System.out.println(binary_search(1,trickCnt));
    }

    public static class trick{
        int x,y,length;
        public trick(int x, int y){
            this.x = x;
            this.y = y;
            this.length = y-x+1;
        }
    }

    public static Comparator<trick> trickComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            if(t1.y!=t2.y)
                return (t1.y-t2.y);
            else return (t1.x-t2.x);
        }
    };

    public static int binary_search(int left, int right){

        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            //beUsed = new boolean[last+1];
            PriorityQueue<trick>PQ = new PriorityQueue<>(pq);
            //System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
            if (beArranged(mid,PQ,begin,begin)) {
                left = mid + 1;
            } else {
                right = mid - 1;
                if(right==left-1)mid--;
            }//System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
        }
        return mid;
    }

    public static boolean beArranged(int time, PriorityQueue<trick>pq,int begin, int last){

        if(pq.size() == 0) return true;

        trick minTrick = pq.poll();

        if(minTrick.y>last) {
            last = minTrick.y;
        }

        int temp = begin+time-1;
        if(temp <= last){
            begin = temp+1;
            return beArranged(time, pq, begin, last);
        }else {
            return false;
        }

    }

}
