import java.awt.Image;

public class RedBrick extends BasicBrick{
	   private static Image wall;//��ǽͼ��
	   
	   private int life=2;//��¼��ǽ����
	   public static Image getImage() {
	    	return wall;
	   }
	   
	   public int  getLife() {
		   return life;
	   }
	   
	   public void   setLife(int life) {
		   this.life=life;
	   }
	    
	    
	public RedBrick(int x,int y) {
		super(x,y);
		// TODO Auto-generated constructor stub
		wall=GameUtile.getImage("walls.png");
	}

}
