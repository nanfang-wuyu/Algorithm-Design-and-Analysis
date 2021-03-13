import java.lang.reflect.Array;
import java.util.*;

public class al_oj_10_1 {


    static long cnt;
    static int top;
    static long startCover;
    static long finishCover;
    static point[] points;
    static point[] stack;
    public static void main(String[] args) {


        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if(n==3) {
            System.out.println(3);
            return;
        }
        top = -1;
        cnt = 0;
        points = new point[n];
        stack = new point[n];

        int minIndex = 0;
        long minY = 0;
        for(int i = 0;i<n;i++){
            long x = in.nextLong();
            long y = in.nextLong();
            points[i] = new point(x,y);
            if(i==0){
                minY = y;
            }else {
                if(y<minY){
                    minIndex = i;
                    minY = y;
                }
            }
        }

        calculateCosine(minIndex);
        /*for(int i = 0;i<points.length;i++){
            point p = points[i];
            System.out.println(p.x+" "+p.y+" "+p.cos);
        }*/
        /*push(points[0]);
        push(points[1]);*/

        judgeInside();

        cnt = top+ 1 + startCover+finishCover;
        //System.out.println(startCover+" "+finishCover);
        System.out.println(cnt);


    }


    public static boolean judgeOnLineOrSameSide(point p0, point p1, point p2, point p3){

        double k = 0;
        double b = 0;
        if(p1.x!=p3.x) {
            k = (p1.y - p3.y) / (p1.x - p3.x);
            b = p1.y - k * p1.x;
            //System.out.println(k);
            //System.out.println(b);
            //System.out.println(p1.x+" "+p2.x+" "+p3.x);
            if((k*p0.x+b-p0.y)*(k*p2.x+b-p2.y)>0)return true;
            else return false;
        }else {

            if((p0.x-p1.x)*(p2.x-p1.x)>0)return true;
            else return false;
        }

    }

    public static void judgeInside(){

        for(int i = 0;i<points.length;i++){
            if(points[i].cos==-10) break;
            long cover = i;

            int p = i+1;
            while(p<points.length){
                if(points[i].cos==points[p].cos){
                    if(points[i].ls<points[p].ls){
                        i = p;
                    }
                    p++;
                }else break;
            }

            if(cover==0){
                cover = p-cover-1;
                startCover = cover;
            }else if(points[p].cos==-10){
                cover = p-cover-1;
                finishCover = cover;
            }

            if(top<=1){
                push(points[i]);
            }else {
                point p0 = stack[0];
                point p3 = points[i];

                while (true) {
                    point p1 = stack[top - 1];
                    point p2 = stack[top];

                    if (judgeOnLineOrSameSide(p0, p1, p2, p3)) {
                        //System.out.println("pop");
                        pop();
                        if (top <= 1)
                            break;
                    } else break;
                }

                push(p3);
                //if p2 and p0 are in the same side of l13, delete p2, judge again;
                //else add p3 to stack;
            }
            i = p-1;
        }
    }

    public static void calculateCosine(int minIndex){
        for(int i = 0;i<points.length;i++){
            if(points[i].x == points[minIndex].x&&points[i].y == points[minIndex].y){
                points[i].cos = -10;
                continue;
            }
            points[i].ls = Math.sqrt(Math.pow((points[i].y-points[minIndex].y),2)+
                    Math.pow((points[i].x-points[minIndex].x),2));
            points[i].cos = (points[i].x-points[minIndex].x)/points[i].ls;
        }

        push(points[minIndex]);

        Arrays.sort(points, (o1, o2) -> {
            if(o2.cos>o1.cos) return 1;
            else return -1;
        });
    }

    static class point{
        long x;
        long y;
        long cover;
        double cos;
        double ls;
        public point(long x, long y){
            this.x = x;
            this.y = y;
            this.cover = 1;
        }
    }

    public static void push(point p){
        top++;
        stack[top]=p;
    }

    public static point pop(){
        top--;
        return stack[top+1];
    }

}
