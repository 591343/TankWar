
public class BasicBrick {
    private int x;//横坐标位置
    private int y;//纵坐标位置
    
    
    public void  setX(int x) {
    	this.x=x;
    	
    }
    
    public int getX() {
    	return x;
    }
    
    public void  setY(int y) {
    	this.y=y;
    	
    }
    
    public int getY() {
    	return y;
    }
    
	public BasicBrick(int x,int y) {
		this.x=x;
		this.y=y;
	}

}
