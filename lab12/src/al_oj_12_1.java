/*
import java.util.*;

public class al_oj_12_1 {


    static Card[] cards;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int W = in.nextInt();
        cards = new Card[91];

        for(int i = 1;i<91;i++){
            cards[i] = new Card(in.nextInt(),in.nextInt(),in.nextInt());
        }


        int [][][] M = new int[91][W+1][31];

        for(int i = 1;i<91;i++){
            for(int j = 1;j<31;j++){
                for(int w = 1;w<W+1;w++){
                    if(cards[i].w>w || cards[i].d>j){
                        M[i][j][w] = M[i-1][j][w];
                    }else {
                        M[i][j][w] = Math.max(M[i-1][j][w],cards[i].v+M[i-1][j-1][w-cards[i].w]);
                    }

                }

            }

        }

        System.out.println(M[90][30][W]);



    }


    public static class Card{

        int w;
        int d;
        int v;

        public Card(int weight, int attack, int health){
            this.w = weight;
            this.d = 1;
            this.v = attack + health;

        }

    }

}
*/
