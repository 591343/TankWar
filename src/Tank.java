import java.util.Random;
import java.util.Vector;



/**
 * 
 * @author LENOVO
 *̹�˸��� ��������̹�˳��ֵ�λ��
 */

public class Tank {
    private  int x=0;//Ĭ��λ��
    private  int y=0;
    private  int speed =5;//�ƶ��ٶ�
    private  int direct=0;//�ƶ�����
    private  int color=1;//Ĭ��Ϊ�ҷ�̹����ɫ����ɫ);
    private boolean isLive=true;//̹�˴��
    
    public Tank(int x,int y) {
    	this.x=x;
    	this.y=y;
    }
    
    public void setLive(boolean isLive) {
		this.isLive=isLive;
	}
	
	
	public boolean getLive() {
		return isLive;
	}
    
    public void setColor(int color) {
    	this.color=color;
    }
    
    public int getColor() {
    	return color;
    }
    
    public int getDirect() {
    	return direct;
    }
    
    public void setDirect(int direct) {
    	this.direct=direct;
    }
    
    public int getSpeed() {
    	return speed;
    }
    
    public void setSpeed(int speed) {
    	this.speed=speed;
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
    
    //�ƶ�
    public void moveUp() {
    	y-=speed;
    	if(y<=0) {
    		y=0;
    	}
    	
    }
    
    public void moveDown() {
    	
    	y+=speed;
    	if(y>=240)
    		y=240;
    	
    }
    
    public void moveLeft() {
    	
    	x-=speed;
    	if(x<=0)
    		x=0;
    }
    
    public void moveRight() {
    	
    	x+=speed;
    	if(x>=360)
    		x=360;
    }
    
    
}

/*
 * �ҷ�̹��
 */
class Hero extends Tank{
	private  Vector<Bullet> bullets=new Vector<Bullet>();//�ӵ�����
	private Bullet bullet=null;//�ӵ���
	
	public Hero(int x,int y) {
		super(x,y);
	}
	
	public Vector<Bullet> getBullets() {
		return bullets;
	}
	

	
	public void shotEnemy() {
		switch(getDirect()) {
		case 0://����
			//����һ���ӵ�
			bullet=new Bullet(getX()+8,getY()-4,0);
			//���ӵ����뼯����
			bullets.add(bullet);
		 
		   break;
		case 1://����
			bullet=new Bullet(getX()+9,getY()+32,1);
			bullets.add(bullet);
			break;
		case 2:
			bullet=new Bullet(getX()-8,getY()+8,2);
			bullets.add(bullet);
			break;
		case 3:
			bullet=new Bullet(getX()+32,getY()+9,3);
			bullets.add(bullet);
			break;
		default:
			break;	
		}
		//����һ���߳�
		Thread t=new Thread(bullet);//���һ���ӵ�
		t.start();//�����߳�
		
	}
	
	 
}

/*
 * �з�̹��
 */
class EnemyTank extends Tank implements Runnable{
	private int time=0; //���Ƶз�̹�˷���һ���ӵ��ļ��
	private Vector<Bullet> bullets=new Vector<Bullet>();//���з�����ӵ�����
	public EnemyTank(int x,int y) {
		super(x,y);
	}
	
	public Vector<Bullet> getBullets(){
		return bullets;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	   while(true) {
		   try {
			   Thread.sleep(50);     //��Ϊ�߳�����̫�쵼�µз�̹���ƶ��ܿ�����Ҫ����ʹ���ƶ��ٶȱ���
		   }catch(InterruptedException e){
			   e.printStackTrace();
		   }
		   switch(getDirect()) {
		   case 0://����
			   for(int i=0;i<3;i++) {
				   if(getY()>0) {   //�ж��Ƿ���߽�
					     setY(getY()-getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5��
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 1://����
			   for(int i=0;i<3;i++) {
				   if(getY()<300) {   //�ж��Ƿ���߽�
					     setY(getY()+getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5��
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 2://����
			   for(int i=0;i<3;i++) {
				   if(getX()>0) {   //�ж��Ƿ���߽�
					     setX(getX()-getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5��
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 3://����
			   for(int i=0;i<3;i++) {
				   if(getX()<400) {   //�ж��Ƿ���߽�
					     setX(getX()+getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5��
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
			   
		   default:
			   
			   break;
		   }
		   
		   time++;//�涨ÿ3s����һ���ӵ�
		   if(time%2==0) {
			   if(getLive()) {
				   //����ӵ�װ��������8�������
				   if(bullets.size()<8) {
					   Bullet bullet=null;
					   
					   switch (getDirect()) {
					case 0:
						bullet=new Bullet(getX()+8,getY()-4, 0);
						bullets.add(bullet);
						break;
						
					case 1:
						bullet=new Bullet(getX()+9,getY()+32, 1);
						bullets.add(bullet);
						break;
					case 2:
						bullet=new Bullet(getX()-8,getY()+8, 2);
						bullets.add(bullet);
						break;
						
					case 3:
						bullet=new Bullet(getX()+32,getY()+9, 3);
						bullets.add(bullet);
						break;

					default:
						break;
					}
					   //�����ӵ��߳�
					  Thread t=new Thread(bullet);
					  t.start();
				   }
			   }
		   }
		   
		   setDirect((int)(Math.random()*4));    //ÿ�η�����һ���ӵ���з�̹�����ѡ����������ƶ�
		   
		   //����з�̹���������˳��߳�
		   if(!getLive()) {
			   break;
		   }
	   }
	}
	
	
	
}
