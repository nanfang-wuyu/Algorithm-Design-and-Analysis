//快读模板1：更快，但没有next()用于读字符串

import java.io.*;
import java.util.*;

public class Main {

    static point[] pd;

    public static void main(String[] args) throws IOException {
        Reader in=new Reader();
        PrintWriter out=new PrintWriter(System.out);

        {

            int n = in.nextInt();
            int d = in.nextInt();
            pd = new point[n];
            if(d==1) {
                for(int i = 0;i<n;i++){
                    pd[i] = new point(in.nextLong());
                }
                pair closestPair = closest_point_d1(pd);
                closestPair = lexOrder(closestPair.p0,closestPair.p1);
                out.println(closestPair.p0.x);
                out.println(closestPair.p1.x);
            }
            else if(d==2){
                for(int i = 0;i<n;i++){
                    pd[i] = new point(in.nextLong(),in.nextLong());
                }
                pair closestPair = closest_point_d2(pd);
                closestPair = lexOrder(closestPair.p0,closestPair.p1);
                out.println(closestPair.p0.x+" "+closestPair.p0.y);
                out.println(closestPair.p1.x+" "+closestPair.p1.y);
            }
            else if(d==3){
                for(int i = 0;i<n;i++){
                    pd[i] = new point(in.nextLong(),in.nextLong(),in.nextLong());
                }
                pair closestPair = closest_point_d3(pd);
                closestPair = lexOrder(closestPair.p0,closestPair.p1);
                out.println(closestPair.p0.x+" "+closestPair.p0.y+" "+closestPair.p0.z);
                out.println(closestPair.p1.x+" "+closestPair.p1.y+" "+closestPair.p1.z);
            }
        }

        out.close();
    }

    public static pair lexOrder(point p0, point p1){
        if(p0.x<p1.x) return new pair(p0,p1);
        else if(p0.x>p1.x) return new pair(p1,p0);
        else {
            if(p0.y<p1.y) return new pair(p0,p1);
            else if(p0.y>p1.y) return new pair(p1,p0);
            else {
                if(p0.z<p1.z) return new pair(p0,p1);
                else return new pair(p1,p0);
            }
        }
    }

    public static pair closest_point_d3(point []P){
        point []Px = P.clone();
        point []Pz = P;
        Arrays.sort(Px, (o1, o2) -> {
            if(o1.x<o2.x) return -1;
            else return 1;
        });

        Arrays.sort(Pz, (o1, o2) -> {
            if(o1.z<o2.z) return -1;
            else return 1;
        });

        pair closestPair = closest_point_rec_d3(Px, Pz);
        return closestPair;
    }

    public static pair closest_point_d2(point []P){
        point []Px = P.clone();
        point []Py = P;
        Arrays.sort(Px, (o1, o2) -> {
            if(o1.x<o2.x) return -1;
            else return 1;
        });

        Arrays.sort(Py, (o1, o2) -> {
            if(o1.y<o2.y) return -1;
            else return 1;
        });

        pair closestPair = closest_point_rec_d2(Px, Py);
        return closestPair;
    }

    public static pair closest_point_d1(point []P){
        Arrays.sort(P, (o1, o2) -> {
            if(o1.x<o2.x) return -1;
            else return 1;
        });
        pair closestPair = closest_point_rec_d1(P);
        return closestPair;
    }

    public static pair closest_point_rec_d3(point []Px, point []Pz){
        if(Px.length<=3){
            if(Px.length==3){
                if(cald(Px[0],Px[1],3)<cald(Px[1],Px[2],3)){
                    return new pair(Px[0],Px[1]);
                }else return new pair(Px[1],Px[2]);
            }else if(Px.length == 2){
                return new pair(Px[0],Px[1]);
            }
        }

        int mid = Px.length/2;
        point []Qx = new point[mid];
        point []Rx = new point[Px.length-mid];

        System.arraycopy(Px, 0, Qx, 0, mid);
        System.arraycopy(Px, mid, Rx, 0, Px.length-mid);

        point []Qz = Qx.clone();
        point []Rz = Rx.clone();

        Arrays.sort(Qz, (o1, o2) -> {
            if(o1.z<o2.z) return -1;
            else return 1;
        });
        Arrays.sort(Rz, (o1, o2) -> {
            if(o1.z<o2.z) return -1;
            else return 1;
        });

        pair q = closest_point_rec_d3(Qx,Qz);
        pair r = closest_point_rec_d3(Rx,Rz);

        double min;
        double d1 = cald(q.p0,q.p1,3);
        double d2 = cald(r.p0,r.p1,3);

        if(d1<d2) min = d1;
        else min = d2;

        point x = Qx[mid-1];


        point minY = Px[0];
        point maxY = Px[0];
        for(int i = 1;i<Px.length;i++){
            if(Px[i].y<minY.y) minY = Px[i];
            if(Px[i].y>maxY.y) maxY = Px[i];
        }

        double y_min = minY.y;
        double y_max = maxY.y;
        int segNum = (int)Math.ceil((y_max-y_min)/min);
        int k = 0;
        List<List<point>>S1 = new LinkedList<>();
        for(int i= 0;i<segNum+1;i++){
            List<point> s = new LinkedList<>();
            S1.add(s);
        }
        List<List<point>>S2 = new LinkedList<>();
        for(int i= 0;i<segNum;i++){
            List<point> s = new LinkedList<>();
            S2.add(s);
        }

        for(int i = 0;i<Pz.length;i++){
            point p = Pz[i];
            if(Math.abs(p.x-x.x)<min){
                if(p.x<=x.x){
                    double y = p.y;
                    k = (int)Math.floor(Math.abs(y-y_min)/min+1)/2;
                    S1.get(k).add(p);
                }
                else {
                    double y = p.y;
                    k = (int)Math.floor(Math.abs(y-y_min)/(2*min));
                    S2.get(k).add(p);
                }
            }
        }
        //constructS1andS2(Px, Pz, x*,!,S1,S2) //见上上页PPT
        double dMin = min;
        pair pairMin = null;

        /*Foreach(s 属于S1)
        根据s纪录的S2的区段信息，到S2相应的位置前后(z差值不超过!)找s’
        If(dmin >d(s,s’))
        dmin = d(s,s’)
        pairmin=(s,s’)
        Endif
        Endforeach*/



        for(int i = 0;i<S1.size();i++){
            List<point> s1 = S1.get(i);
            List<point> s21 = null;
            if(i!=0) s21 = S2.get(i-1);
            List<point> s22 = null;
            if(i!=S1.size()-1) s22 = S2.get(i);

            for(int m = 0;m<s1.size();m++){
                point s = s1.get(m);
                if(s.x<=x.x){
                    if(i!=0)
                        for(int j = 0;j<s21.size();j++){
                            point sn = s21.get(j);
                            if(sn.x>x.x && Math.abs(sn.y-s.y)<=min){
                                double dn = cald(s,sn,3);
                                if(dMin>dn){
                                    dMin = dn;
                                    pairMin = new pair(s,sn);
                                }
                            }else if(sn.y-s.y>min){
                                break;
                            }
                        }
                    if(i!=S1.size()-1)
                        for(int j = 0;j<s22.size();j++){
                            point sn = s22.get(j);
                            if(sn.x>x.x && Math.abs(sn.y-s.y)<=min){
                                double dn = cald(s,sn,3);
                                if(dMin>dn){
                                    dMin = dn;
                                    pairMin = new pair(s,sn);
                                }
                            }else if(sn.y-s.y>min){
                                break;
                            }
                        }
                }
            }
        }

        if(pairMin!=null) return pairMin;
        else if(d1<d2) return q;
        else return r;

    }

    public static pair closest_point_rec_d2(point []Px, point []Py){
        if(Px.length<=3){
            if(Px.length==3){
                if(cald(Px[0],Px[1],2)<cald(Px[1],Px[2],2)){
                    return new pair(Px[0],Px[1]);
                }else return new pair(Px[1],Px[2]);
            }else if(Px.length == 2){
                return new pair(Px[0],Px[1]);
            }
        }

        int mid = Px.length/2;
        point []Qx = new point[mid];
        point []Rx = new point[Px.length-mid];

        System.arraycopy(Px, 0, Qx, 0, mid);
        System.arraycopy(Px, mid, Rx, 0, Px.length-mid);

        point []Qy = Qx.clone();
        point []Ry = Rx.clone();

        Arrays.sort(Qy, (o1, o2) -> {
            if(o1.y<o2.y) return -1;
            else return 1;
        });
        Arrays.sort(Ry, (o1, o2) -> {
            if(o1.y<o2.y) return -1;
            else return 1;
        });

        pair q = closest_point_rec_d2(Qx,Qy);
        pair r = closest_point_rec_d2(Rx,Ry);

        double min;
        double d1 = cald(q.p0,q.p1,2);
        double d2 = cald(r.p0,r.p1,2);

        if(d1<d2) min = d1;
        else min = d2;

        point x = Qx[mid-1];
        List<point>s0 = new LinkedList<>();
        for(point p : Py) {
            if (Math.abs(x.x - p.x) <= min)
                s0.add(p);
        }

        double dMin = min;
        pair pairMin = null;

        for(int i = 0;i<s0.size();i++){
            point s = s0.get(i);
            if(s.x<=x.x){
                for(int j = i+1;j<s0.size();j++){
                    point sn = s0.get(j);
                    if(sn.x>x.x && Math.abs(sn.y-s.y)<=min){
                        double dn = cald(s,sn,2);
                        if(dMin>dn){
                            dMin = dn;
                            pairMin = new pair(s,sn);
                        }
                    }
                }
                for(int j = i-1;j>-1;j--){
                    point sn = s0.get(j);
                    if(sn.x>x.x && Math.abs(sn.y-s.y)<=min){
                        double dn = cald(s,sn,2);
                        if(dMin>dn){
                            dMin = dn;
                            pairMin = new pair(s,sn);
                        }
                    }
                }
            }
        }

        if(pairMin!=null) return pairMin;
        else if(d1<d2) return q;
        else return r;
    }

    public static pair closest_point_rec_d1(point []Px){
        if(Px.length<=3){
            if(Px.length==3){
                if(cald(Px[0],Px[1],1)<cald(Px[1],Px[2],1)){
                    return new pair(Px[0],Px[1]);
                }else return new pair(Px[1],Px[2]);
            }else if(Px.length == 2){
                return new pair(Px[0],Px[1]);
            }
        }

        int mid = Px.length/2;
        point []Qx = new point[mid];
        point []Rx = new point[Px.length-mid];

        System.arraycopy(Px, 0, Qx, 0, mid);
        System.arraycopy(Px, mid, Rx, 0, Px.length-mid);

        pair q = closest_point_rec_d1(Qx);
        pair r = closest_point_rec_d1(Rx);

        double min;
        double d1 = cald(q.p0,q.p1,1);
        double d2 = cald(r.p0,r.p1,1);

        if(d1<d2) min = d1;
        else min = d2;

        point x0 = Qx[mid-1];
        point s0 = Rx[0];
        if(cald(x0,s0,1)<min) return new pair(x0,s0);
        else if(d1<d2) return q;
        else return r;
    }

    public static double cald(point p0, point p1, int d){
        if(d==1) return Math.abs(p0.x - p1.x);
        else if(d==2) return Math.sqrt(Math.pow((p0.x-p1.x),2)+Math.pow((p0.y-p1.y),2));
        else return Math.pow((Math.pow((p0.x-p1.x),2)+Math.pow((p0.y-p1.y),2)+Math.pow((p0.z-p1.z),2)),1.0/3);
    }

    static class point{
        long x;
        long y;
        long z;
        public point(long x, long y, long z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public point(long x, long y){
            this.x = x;
            this.y = y;
            this.z = 0;
        }

        public point(long x){
            this.x = x;
            this.y = 0;
            this.z = 0;
        }
    }

    static class pair{
        point p0;
        point p1;
        double d;
        public pair(point p0, point p1){
            this.p0 = p0;
            this.p1 = p1;
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
