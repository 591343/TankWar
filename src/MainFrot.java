import java.awt.Image;

/**
 * ������
 * @author LENOVO
 *
 */
public class MainFrot extends  BasicBrick{
    private static Image headquarter;//����ͼƬ
    private int life=4;//��������
    
    
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
