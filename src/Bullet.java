import java.util.Vector;

//�ӵ��࣬ʵ��̹�˷����ӵ�


public class Bullet implements Runnable {

	private int x;//�ӵ�������
	private int y;//�ӵ�������
	private int driect;//�ӵ�����
	private int speed=6;//�ӵ��ٶ�
	private boolean isLive=true;//�ӵ��ܳ���Ļ��Ҫ���������ӱ���isLive�ж��Ƿ�����������Ʒ��У�����ռ���ڴ�ռ�
	
	
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
		if(isLive)//�ӵ�����true
		return true;
		
		else //���򷵻�false;
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
				Thread.sleep(50);//���ӵ��������У�Ҫ��Ȼ���ۿ�����
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			switch(driect) {//�ж��ӵ��ķ���
			case 0://����
				y-=speed;
				break;
			case 1://����
				y+=speed;
				break;
			case 2://����
				x-=speed;
				break;
			case 3://����
				x+=speed;
				break;
			default:
				break;
			}
			
			if(x<0||x>400||y<0||y>300) {
				isLive=false;//���ӵ��ɳ���Ļʱ���������߳�
				break;
			}
			
		}
	}

}
