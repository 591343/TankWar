import java.util.Random;
import java.util.Vector;


/**
 * 
 * @author LENOVO
 *̹�˸��� ��������̹�˳��ֵ�λ��
 */



public class Tank {
    private  int x=0;//Ĭ��
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
	private int le=140;  //�з�̹�˵�������Χ
	
	
	
	
	private int time=0; //���Ƶз�̹�˷���һ���ӵ��ļ��
	private Vector<Bullet> bullets=new Vector<Bullet>();//���з�����ӵ�����
	private Vector<EnemyTank> ets=new Vector<EnemyTank>();//����һ���������Է���Mypanel�����е�̹��
	public EnemyTank(int x,int y) {
		super(x,y);
	}
	
	public Vector<Bullet> getBullets(){
		return bullets;
	}
	
	public void setEts(Vector<EnemyTank> ets) {
		this.ets=ets;
	}
	
	
	private  boolean isTouchOtherEnemy(){
        boolean b = false;

        switch (getDirect()) {
        case 0:
            //�ҵ�̹������
            //ȡ�����еĵ���̹��
            for(int i=0;i<ets.size();i++){
                //ȡ����һ��̹��
                EnemyTank et = ets.get(i);
                //��������Լ�
                if(et!=this){
                    //������˵ķ��������»�������
                    if(et.getDirect()==0||et.getDirect()==1){
                        if(getX()>=et.getX()&&getX()<=et.getX()+20&&getY()>=et.getY()&&getY()<=et.getY()+30){
                            return true;
                        }
                        if(getX()+20>=et.getX()&&getX()+20<=et.getX()+20&&getY()>=et.getY()&&getY()<=et.getY()+30){
                            return true;
                        }
                    }
                    if(et.getDirect()==3||et.getDirect()==2){
                        if(getX()>=et.getX()&&getX()<=et.getX()+30&&getY()>=et.getY()&&getY()<=et.getY()+20){
                            return true;
                        }
                        if(getX()+20>=et.getX()&&getX()+20<=et.getX()+30&&getY()>=et.getY()&&getY()<=et.getY()+20) {
                            return true;
                        }
                    }
                }
            }
            break;
        case 1:
            //�ҵ�̹������
            //ȡ�����еĵ���̹��
            for(int i=0;i<ets.size();i++){
                //ȡ����һ��̹��
                EnemyTank et = ets.get(i);
                //��������Լ�
                if(et!=this){
                    //������˵ķ��������»�������
                    if(et.getDirect()==0||et.getDirect()==1){
                        if(getX()>=et.getX()&&getX()<=et.getX()+20&&getY()+30>=et.getY()&&getY()+30<=et.getY()+30){
                            return true;
                        }
                        if(getX()+20>=et.getX()&&getX()+20<=et.getX()+20&&getY()+30>=et.getY()&&getY()+30<=et.getY()+30){
                            return true;
                        }
                    }
                    if(et.getDirect()==3||et.getDirect()==2){
                        if(getX()>=et.getX()&&getX()<=et.getX()+30&&getY()+30>=et.getY()&&getY()+30<=et.getY()+20){
                            return true;
                        }
                        if(getX()+20>=et.getX()&&getX()+20<=et.getX()+30&&getY()+30>=et.getY()&&getY()+30<=et.getY()+20){
                            return true;
                        }
                    }
                }
            }

            break;
        case 2:
            //�ҵ�̹������
            //ȡ�����еĵ���̹��
            for(int i=0;i<ets.size();i++){
                //ȡ����һ��̹��
                EnemyTank et = ets.get(i);
                //��������Լ�
                if(et!=this){
                    //������˵ķ��������»�������
                    if(et.getDirect()==0||et.getDirect()==1){
                        if(getX()>=et.getX()&&getX()<=et.getX()+20&&getY()>=et.getY()&&getY()<=et.getY()+30){
                            return true;
                        }
                        if(getX()>=et.getX()&&getX()<=et.getX()+20&&getY()+20>=et.getY()&&getY()+20<=et.getY()+30){
                            return true;
                        }
                    }
                    if(et.getDirect()==3||et.getDirect()==2){
                        if(getX()>=et.getX()&&getX()<=et.getX()+30&&getY()>=et.getY()&&getY()<=et.getY()+20){
                            return true;
                        }
                        if(getX()>=et.getX()&&getX()<=et.getX()+30&&getY()+20>=et.getY()&&getY()+20<=et.getY()+20){
                            return true;
                        }
                    }
                }
            }

            break;
        case 3:
            //̹������
            for(int i=0;i<ets.size();i++){
                //ȡ����һ��̹��
                EnemyTank et = ets.get(i);
                //��������Լ�
                if(et!=this){
                    //������˵ķ��������»�������
                    if(et.getDirect()==0||et.getDirect()==1){
                        if(getX()+30>=et.getX()&&getX()+30<=et.getX()+20&&getY()>=et.getY()&&getY()<=et.getY()+30){
                            return true;
                        }
                        if(getX()+30>=et.getX()&&getX()+30<=et.getX()+20&&getY()+20>=et.getY()&&getY()+20<=et.getY()+30){
                            return true;
                        }
                    }
                    if(et.getDirect()==2||et.getDirect()==3){
                        if(getX()+30>=et.getX()&&getX()+30<=et.getX()+30&&getY()>=et.getY()&&getY()<=et.getY()+20){
                            return true;
                        }
                        if(getX()+30>=et.getX()&&getX()+30<=et.getX()+30&&getY()+20>=et.getY()&&getY()+20<=et.getY()+20){
                            return true;
                        }
                    }
                }
            }
            break;


        default:
            break;
        }
        return b;
    }

	
	public void run() {
		// TODO Auto-generated method stub
		int count=0;
		Random r=new Random();
		int flag=r.nextInt(2);    //��������������������һ������
	   while(true) {
		   try {
			   Thread.sleep(0);     //��Ϊ�߳�����̫�쵼�µз�̹���ƶ��ܿ�����Ҫ����ʹ���ƶ��ٶȱ���
		   }catch(InterruptedException e){
			   e.printStackTrace();
		   }
		
		   
		if(MyPanel.hx<getX()-le||MyPanel.hx>getX()+le||MyPanel.hy<getY()-le||MyPanel.hy>getY()+le) {   
		   switch(getDirect()) {
		   case 0://����
			   for(int i=0;i<3;i++) {
				   if(getY()>0&&!isTouchOtherEnemy()) {   //�ж��Ƿ���߽�
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
				   if(getY()<240&&!isTouchOtherEnemy()) {   //�ж��Ƿ���߽�
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
				   if(getX()>0&&!isTouchOtherEnemy()) {   //�ж��Ƿ���߽�
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
				   if(getX()<360&&!isTouchOtherEnemy()) {   //�ж��Ƿ���߽�
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
		   if(count++==4) {
			   setDirect((int)(Math.random()*4));
			   count=0;
			   }
		}
		
		//��ǽʧ��
		/*else if(MyPanel.touchBrick(this)) {      
			if(getDirect()==0) {
				if(getX()>180) {
					moveRight();
				}
				else {
					moveLeft();
				}
			}
			else if(getDirect()==1) {
				if(getX()>180) {
					moveRight();
				}
				else {
					moveLeft();
				}
			}
			else if(getDirect()==2) {
				if(getY()>120) {
					moveUp();
				}
				else {
					moveDown();
				}
			}
			else if(getDirect()==3) {
				if(getY()>120) {
					moveUp();
				}
				else {
					moveDown();
				}
			}
			try {
		    	 Thread.sleep(300);//0.5��
		     }catch(InterruptedException e) {
		    	 e.printStackTrace();
		     }
		}*/
		
		else {
			
			if(MyPanel.hx<getX()&&MyPanel.hy<getY()) {
				/*if(MyPanel.hx<getX()-20) {             //�ȳ�һ�������ƶ���Ŀ����뷶Χ��ת����һ������
					setDirect(2);
					 moveLeft();
				}
				else if(MyPanel.hx>=getX()-20&&MyPanel.hx<=getX()+20){
					setDirect(0);
					 moveUp();
				}*/
				if(flag==0) {                          //���ѡ��һ����������ƶ�
					setDirect(2);
					if(!isTouchOtherEnemy()) moveLeft();
					if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {    //���ײǽ��ֱ��ת�䷽��
						flag=r.nextInt(2);
					}
				}
				else if(flag==1) {
					setDirect(0);
					if(!isTouchOtherEnemy())  moveUp();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
				if(count++==4) {
					flag=r.nextInt(2);
					count=0;
				}
				
			}
			else if(MyPanel.hx>getX()&&MyPanel.hy<getY()) {
				if(flag==0) {
					setDirect(3);
					if(!isTouchOtherEnemy()) moveRight();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
				
				else if(flag==1){
					setDirect(0);
					if(!isTouchOtherEnemy()) moveUp();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
				
			
			}
			else if(MyPanel.hx<getX()&&MyPanel.hy>getY()) {
				if(flag==0) {
					setDirect(2);
					if(!isTouchOtherEnemy()) moveLeft();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
				else if(flag==1){
					setDirect(1);
					if(!isTouchOtherEnemy()) moveDown();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
				
			}
			else {
				if(flag==0) {
					setDirect(3);
					if(!isTouchOtherEnemy()) moveRight();
					if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						flag=r.nextInt(2);
					}
				}
				else if(flag==1){
					setDirect(1);
					if(!isTouchOtherEnemy()) moveDown();
					 if(MyPanel.touchBrick(this)||isTouchOtherEnemy()) {   
						 flag=r.nextInt(2);
					}
				}
			}
			try {
		    	 Thread.sleep(300);//0.5��
		     }catch(InterruptedException e) {
		    	 e.printStackTrace();
		     }
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
		  /* if(count++==4) {
		   setDirect((int)(Math.random()*4));
		   count=0;
		   }*/
		   
		   //ÿ�η�����һ���ӵ���з�̹�����ѡ����������ƶ�
		   
		   //����з�̹���������˳��߳�
		   if(!getLive()) {
			   break;
		   }
	   }
	}	
}
