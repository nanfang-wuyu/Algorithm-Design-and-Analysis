import java.util.*;

public class al_oj_5finalTry {

    public static int begin, last, clock,trickNum;
    public static PriorityQueue<trick>pqX,pqY;
    public static int[] magicShowTime;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        trickNum = in.nextInt();
        begin = 10001;
        last = 0;
        pqX = new PriorityQueue<>(firstComparator);
        for(int i = 0;i<trickNum;i++){
            int x,y;
            x = in.nextInt();
            y = in.nextInt();
            pqX.add(new trick(x,y,i));
            if(x<begin) begin = x;
            if(y>last) last = y;
        }
        int timeRange = last-begin+1;
        int trickCnt = timeRange / trickNum;

        if(trickCnt == 0)
            System.out.println(0);
        else
            System.out.println(binary_search(1,trickCnt));
    }

    public static class trick{
        int x,y,length,key;
        public trick(int x, int y, int key){
            this.x = x;
            this.y = y;
            this.length = y-x+1;
            this.key = key;
        }
    }

    public static Comparator<trick> firstComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            return t1.x-t2.x;
        }
    };

    public static Comparator<trick> secondComparator = new Comparator<trick>(){
        @Override
        public int compare(trick t1, trick t2) {
            return t1.y-t2.y;
        }
    };

    public static int binary_search(int left, int right){

        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            PriorityQueue<trick>PQx = new PriorityQueue<>(pqX);
            //System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
            if (beArranged(mid,PQx)) {
                left = mid + 1;
            } else {
                right = mid - 1;
                if(right==left-1)mid--;
            }//System.out.println("mid"+mid);System.out.println("left"+left);System.out.println("right"+right);
        }
        return mid;

    }

    public static boolean beArranged(int time, PriorityQueue<trick> PQx){

        magicShowTime = new int[trickNum];

        clock = begin;

        pqY = new PriorityQueue<>(secondComparator);

        while(clock!=last+1){

            while(PQx.peek()!=null) {
                if(clock == PQx.peek().x) pqY.add(PQx.poll());
                else break;
            }

            if(pqY.peek()!=null) {
                magicShowTime[pqY.peek().key] ++;
                while(pqY.peek()!=null){
                    if(magicShowTime[pqY.peek().key] == time) {
                        pqY.poll();
                    }
                    else if(clock >= pqY.peek().y) return false;
                    else break;
                }
            }

            clock ++;
        }

        if(pqY.size()!=0) return false;
        else return true;


    }

}
