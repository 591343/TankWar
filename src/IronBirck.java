import java.awt.Image;

public class IronBirck extends BasicBrick {
    private static Image iron;//Ìú¿éÍ¼Ïñ
    
    public static Image getImage() {
    	return iron;
    }
    
    
	public IronBirck(int x,int y) {
		super(x,y);
		// TODO Auto-generated constructor stub
		iron=GameUtile.getImage("steels.png");
	}

}
