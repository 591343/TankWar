
public class Boom {

	private int x;
	private int y;
	private int life =9;//图片效果张数
	private boolean isLive=true;//爆照生命
	
	public Boom(int x,int y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}	
	
	public int getX() {
		return x;
	
	}
	
	public void setX(int x) {
		this.x=x;
		
	}
	
	public int getY() {
		return y;
	
	}
	
	public void setY(int y) {
		this.y=y;
		
	}
	
	public int getLife() {
		return life;
	}
	
	public boolean isBoomLive() {
		return isLive;
	}
	//减少生命值
	public void lifeDown() {
		if(life>0) {
			life--;//如果还有图片就继续播放下一张直到没有
		
	    }else {
	    	isLive=false;//当没有爆照图片可播放时就将爆炸声明设置为false
	    }
		
     }
  }
