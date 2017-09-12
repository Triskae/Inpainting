package topology;


public class Color{ 			// Color (B,G,R)
    public byte[] val;

    //Constructeurs
    public Color(byte b1 ,byte b2 ,byte b3) {
        val[0] = b1;
        val[1] = b2;
        val[2] = b3;
    }

    public Color(int c1,int c2 ,int c3) {


    }

    //Fonctions
    public static int dist(Color c1 ,Color c2) {

        return 0;
    }

    public void set(Color c1) {

    }

    boolean isequalto(Color c1) {

        return true;
    }



    @Override public String toString() {

        return "a";
    }

}
