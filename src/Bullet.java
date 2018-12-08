import java.util.Vector;

//子弹类，实现坦克发射子弹


public class Bullet implements Runnable {

	private int x;//子弹横坐标
	private int y;//子弹纵坐标
	private int driect;//子弹方向
	private int speed=6;//子弹速度
	private boolean isLive=true;//子弹跑出屏幕需要死亡，增加变量isLive判断是否存活。否则无限制飞行，不断占用内存空间
	
	
	public Bullet(int x,int y,int driect) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.driect=driect;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isBuLive() {
		if(isLive)//子弹存活返回true
		return true;
		
		else //否则返回false;
			return false;
	}
	
	public void setBuLive(boolean isLive) {
		this.isLive=isLive;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(50);//让子弹慢慢飞行，要不然肉眼看不见
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			switch(driect) {//判断子弹的方向
			case 0://向上
				y-=speed;
				break;
			case 1://向下
				y+=speed;
				break;
			case 2://向左
				x-=speed;
				break;
			case 3://向右
				x+=speed;
				break;
			default:
				break;
			}
			
			if(x<0||x>400||y<0||y>300) {
				isLive=false;//当子弹飞出屏幕时立即结束线程
				break;
			}
			
		}
	}

}
