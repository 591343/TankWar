import java.awt.Image;

public class GrassBrick extends BasicBrick{
    private static Image grass;//�ݵ�ͼ��
	
	
    public static  Image getImage() {
    	return grass;
   }
    
	public GrassBrick(int x,int y) {
		super(x,y);
		// TODO Auto-generated constructor stub
		grass=GameUtile.getImage("grass.png");
	}

}
