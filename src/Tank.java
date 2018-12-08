import java.util.Random;
import java.util.Vector;



/**
 * 
 * @author LENOVO
 *坦克父类 可以设置坦克出现的位置
 */

public class Tank {
    private  int x=0;//默认位置
    private  int y=0;
    private  int speed =5;//移动速度
    private  int direct=0;//移动方向
    private  int color=1;//默认为我方坦克颜色（黄色);
    private boolean isLive=true;//坦克存活
    
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
    
    //移动
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
 * 我方坦克
 */
class Hero extends Tank{
	private  Vector<Bullet> bullets=new Vector<Bullet>();//子弹集合
	private Bullet bullet=null;//子弹类
	
	public Hero(int x,int y) {
		super(x,y);
	}
	
	public Vector<Bullet> getBullets() {
		return bullets;
	}
	

	
	public void shotEnemy() {
		switch(getDirect()) {
		case 0://向上
			//创建一颗子弹
			bullet=new Bullet(getX()+8,getY()-4,0);
			//将子弹加入集合中
			bullets.add(bullet);
		 
		   break;
		case 1://向下
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
		//创建一个线程
		Thread t=new Thread(bullet);//射出一颗子弹
		t.start();//启动线程
		
	}
	
	 
}

/*
 * 敌方坦克
 */
class EnemyTank extends Tank implements Runnable{
	private int time=0; //控制敌方坦克发射一次子弹的间隔
	private Vector<Bullet> bullets=new Vector<Bullet>();//给敌方添加子弹集合
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
			   Thread.sleep(50);     //因为线程运行太快导致敌方坦克移动很快所以要休眠使其移动速度变慢
		   }catch(InterruptedException e){
			   e.printStackTrace();
		   }
		   switch(getDirect()) {
		   case 0://向上
			   for(int i=0;i<3;i++) {
				   if(getY()>0) {   //判断是否出边界
					     setY(getY()-getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5秒
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 1://向下
			   for(int i=0;i<3;i++) {
				   if(getY()<300) {   //判断是否出边界
					     setY(getY()+getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5秒
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 2://向左
			   for(int i=0;i<3;i++) {
				   if(getX()>0) {   //判断是否出边界
					     setX(getX()-getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5秒
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
		   case 3://向右
			   for(int i=0;i<3;i++) {
				   if(getX()<400) {   //判断是否出边界
					     setX(getX()+getSpeed());
					     try {
					    	 Thread.sleep(200);//0.5秒
					     }catch(InterruptedException e) {
					    	 e.printStackTrace();
					     }
				   }
			   }
			   break;
			   
		   default:
			   
			   break;
		   }
		   
		   time++;//规定每3s发射一颗子弹
		   if(time%2==0) {
			   if(getLive()) {
				   //如果子弹装填量不够8发就添加
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
					   //启动子弹线程
					  Thread t=new Thread(bullet);
					  t.start();
				   }
			   }
		   }
		   
		   setDirect((int)(Math.random()*4));    //每次发射完一颗子弹后敌方坦克随机选个方向进行移动
		   
		   //如果敌方坦克死亡就退出线程
		   if(!getLive()) {
			   break;
		   }
	   }
	}
	
	
	
}
