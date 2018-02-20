package topology;


public class Color{ 			// Color (B,G,R)
	public byte[] val;

	//Constructeurs
	public Color(byte b1 ,byte b2 ,byte b3) {
		val = new byte[3];
		val[0] = b1;
		val[1] = b2;
		val[2] = b3;
	}

	public Color(int c1,int c2 ,int c3) throws ArithmeticException {
		if(c1 < 0 || c2 < 0 || c3 < 0 || c1 > 255 || c2 > 255 || c3 > 255) throw new ArithmeticException("L'un des paramètres du constructeur n'est pas compris entre 0 et 255");
		val = new byte[3];
		val[0] = (byte)c1;
		val[1] = (byte)c2;
		val[2] = (byte)c3;

	}

	//Fonctions
	public byte getB(){
		return val[0];
	}
	public byte getG(){
		return val[1];
	}
	public byte getR(){
		return val[2];
	}


	/**
	 * Cette fonction calcule la distance cartesienne avec conversion des bytes en int non signés
	 * @param c1 La première couleur
	 * @param c2 La deuxième couleur
	 * @return
	 */
	public static int dist(Color c1 ,Color c2) {

		int b =(int) Math.pow((c2.getB()&0xFF) - (c1.getB() &0xFF),2);
		int g = (int) Math.pow((c2.getG() &0xFF) - (c1.getG() &0xFF),2);
		int r = (int) Math.pow((c2.getR() &0xFF) - (c1.getR() &0xFF),2);
		return b+g+r;
	}

	/**
	 * Fonction qui convertis
	 * @param rgb
	 * @return
	 */
	public static Color rgbToColor(int rgb){
		int red = (rgb >> 16) & 0xff;
		int green = (rgb >> 8) & 0xff;
		int blue = (rgb) & 0xff;
		return new Color(blue,green,red);
	}
	public  int colorToRgb(){
		int rgb = ((this.getR()&0x0ff)<<16)|((this.getG()&0x0ff)<<8)|(this.getB()&0x0ff);
		return rgb;

	}

	public void set(Color c1) {
		val[0] = c1.getB();
		val[1] = c1.getG();
		val[2] = c1.getR();
	}


	boolean isequalto(Color c1) {

		if(dist(this,c1) == 0) return true; // Si la distance entre deux couleurs est 0 alors les couleurs sont identiques
		else return false;
	}



	@Override public String toString() {


		return "Colors value : (" + Byte.toUnsignedInt(val[0])+","+Byte.toUnsignedInt(val[1])+","+Byte.toUnsignedInt(val[2])+")";
	}

}
