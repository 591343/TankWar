
public class Boom {

	private int x;
	private int y;
	private int life =9;//ͼƬЧ������
	private boolean isLive=true;//��������
	
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
	//��������ֵ
	public void lifeDown() {
		if(life>0) {
			life--;//�������ͼƬ�ͼ���������һ��ֱ��û��
		
	    }else {
	    	isLive=false;//��û�б���ͼƬ�ɲ���ʱ�ͽ���ը��������Ϊfalse
	    }
		
     }
  }
