import java.awt.Image;

/**
 * 基地类
 * @author LENOVO
 *
 */
public class MainFrot extends  BasicBrick{
    private static Image headquarter;//基地图片
    private int life=4;//基地生命
    
    
    public int  getLife() {
    	return life;
    }
    
    public void setLife(int life) {
    	this.life=life;
    }
    public static Image getImage() {
    	return headquarter;
    }
	public MainFrot(int x,int y) {
		// TODO Auto-generated constructor stub
		super(x,y);
		headquarter=GameUtile.getImage("symbol.png");
	}

}
