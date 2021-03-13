import java.util.*;

public class al_oj_13_1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int C = in.nextInt();

        card[][] cards = new card[n+1][k+1];

        for(int i = 1;i<n+1;i++){
            for(int j = 1;j<k+1;j++){
                cards[i][j] = new card(in.nextInt(),in.nextInt()+in.nextInt());
            }
        }

        int opt[][] = new int[n+1][C+1];

        for(int w = 0;w<C;w++){
            opt[0][w] = 0;
        }

        for(int i = 1;i<n+1;i++) {
            for (int w = 1; w < C+1; w++) {
                for (int j = 1; j < k+1; j++) {
                    if (cards[i][j].w>w){
                        opt[i][w] = Math.max(opt[i][w],opt[i-1][w]);
                    }else {
                        int max = Math.max(opt[i-1][w],
                                opt[i-1][w-cards[i][j].w]+cards[i][j].v);
                        if(opt[i][w]<max)
                        opt[i][w] = max;
                    }
                }
            }
        }

        System.out.println(opt[n][C]);
    }

    public static class card{

        int w;
        int v;
        public card(int w, int v){
            this.w = w;
            this.v = v;
        }
    }

}
