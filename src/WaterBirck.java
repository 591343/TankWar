import java.awt.Image;

public class WaterBirck extends BasicBrick {

	 private static Image water;//Ìú¿éÍ¼Ïñ
	    
	    public static Image getImage() {
	    	return water;
	    }
	    
	    
		public WaterBirck(int x,int y) {
			super(x,y);
			// TODO Auto-generated constructor stub
			water=GameUtile.getImage("water.png");
		}


}
