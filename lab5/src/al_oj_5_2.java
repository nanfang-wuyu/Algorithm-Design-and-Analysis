import java.util.*;

public class al_oj_5_2 {

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
            if(t1.length!=t2.length)
            return (t1.length-t2.length);
            else return (t1.x-t2.x);
        }
    };

    public static int binary_search(int left, int right){

            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                beUsed = new boolean[last+1];
                PriorityQueue<trick>PQ = new PriorityQueue<>(pq);
                //System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
                if (beArranged(mid,PQ)) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                    if(right==left-1)mid--;
                }//System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
            }
            return mid;

    }

    public static boolean beArranged(int time, PriorityQueue<trick> pq){
        //System.out.println(pq.size());
        if(pq.size()==0) return true;
        trick minTrick = pq.poll();
        if(minTrick.length<time) {
            pq.add(minTrick);
            return false;
        }
        else {
            for(int i = minTrick.x; i+time-1<=minTrick.y;i++){
                boolean useful = true;
                int j;
                for (j = i;j<i+time;j++){
                    if(beUsed[j]) {
                        useful = false;
                        break;
                    }
                }
                if(!useful) {
                    i = j;
                    continue;
                }
                for (j = i;j<i+time;j++){
                    beUsed[j] = true;
                }
                if(beArranged(time,pq)){
                    return true;
                }else {
                    for (j = i;j<i+time;j++){
                        beUsed[j] = false;
                    }
                }
            }
            pq.add(minTrick);
            return false;
        }
    }
}
