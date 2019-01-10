
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/*
 * ���
* drawTank (̹������x,y,����g,����̹������)
* �������ܣ�
* ��������-->̹�˵���ɫ�����ͣ��з�̹�ˣ��ҷ�̹�ˣ������򣬳��ֵ�����
* 
* ���type��default ��Ĭ����ɫΪ������ɫ̹��
* 
* ��װ�ԣ���̹�˷�װ�������С�
* ��������30X30
* ̹�����س�30,��20
* ת������ͳһΪ25X25
*/

@SuppressWarnings("serial")
class MyPanel extends JPanel implements KeyListener,Runnable {

	 private Hero hero=null;//�ҷ�̹��
	 private Vector<EnemyTank> enemies=new Vector<EnemyTank>();//�з�̹��
	 private Vector<Boom> bombs=new Vector<Boom>();//����ը������
	 private final int enSize=10;//�з�̹����������
	 private Vector<Image> blasts=new Vector<Image>();//��ըͼƬ����
	 private boolean flag=true;//Ԥ�ȼ���ը����ֹ��һ�λ���̹�˲����ֱ�ըЧ��
	 private Vector<IronBirck> irons=new Vector<IronBirck>();//��ǽ����
	 private Vector<GrassBrick> grasses=new Vector<GrassBrick>();//�ݵؼ���
	 private Vector<RedBrick> walls=new Vector<RedBrick>();//שǽ����
	 private Vector<WaterBirck> waters=new Vector<WaterBirck>();//ˮǽ����
	 private MainFrot frot;
	 private int roundCount=0;//�غϼ���
	 private int nowTankNum=0;//��ǰ̹�˼���
     private final int initTankNum=5;//��ʼ̹����


	 private  int ironBircks=14;//��ש����
	 private  int wallBircks=12;//שǽ����
	 private  int grassBircks=12;//�ݵؿ���
	 private  int waterBircks=0;//ˮǽ����
	 
	
	 
	 MyPanel() {
		// TODO Auto-generated constructor stub
		hero=new Hero(165,230);//�����ҷ�̹�˳��ֵ�λ��
		
		InitEnemyTank();//��ʼ������̹��
		InitMainFrot();//��ʼ������
		InitMap();//��ʼ����ͼ
	
		
		//��ʼ����ըͼƬ
		for(int i=1;i<=8;i++) {
		  Image image=null;
		   image=Toolkit.getDefaultToolkit().createImage(MyPanel.class.getResource("blast"+i+".gif"));
		   blasts.add(image);//����ըͼƬ��ӽ�����
		}
		
		
	}
	
	@Override
    public  void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 400, 300);//����ʵ�ľ���Ĭ��Ϊ��ɫ
		
		//�����ҷ�̹��
		//System.out.println(hero.getLive());
		if(hero.getLive())
		drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		//���ש��û�б�����Ͳ����»�
		
		
		drawBrick(g);

		
		//�����ӵ�
		for(int i=0;i<hero.getBullets().size();i++) {
			//ȡ���ӵ�
			Bullet myBullet=hero.getBullets().get(i);
		if(myBullet.isBuLive()) {
			g.setColor(Color.yellow);//���ӵ���ɫ����Ϊ��ɫ
		    g.fill3DRect(myBullet.getX(),myBullet.getY() ,2 ,2 , false);
		 }
		
		if(!myBullet.isBuLive()) {//����ӵ��������Ƴ��ӵ�����
			hero.getBullets().remove(myBullet);
		 }
		
		}
		
		 for(int i=0;i<bombs.size();i++) {
		    	Boom b=bombs.get(i);	
		    	
		    	
		    	
		    	new Thread(new BlastPlayer()).start();//̹�˱�ը��Ч
		    	if(b.getLife()>8) {
		    		g.drawImage(blasts.get(0), b.getX(), b.getY(), 30, 30, this);
		    		
		    	}else if(b.getLife()>7) {
		    		g.drawImage(blasts.get(1), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>6) {
		    		g.drawImage(blasts.get(2), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>5) {
		    		g.drawImage(blasts.get(3), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>4) {
		    		g.drawImage(blasts.get(4), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>3) {
		    		g.drawImage(blasts.get(5), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>2) {
		    		g.drawImage(blasts.get(6), b.getX(), b.getY(), 30, 30, this);
		    	}else if(b.getLife()>1) {
		    		g.drawImage(blasts.get(7), b.getX(), b.getY(), 30, 30, this);
		    	}
		    	
		    	
		    	
		    	b.lifeDown();
		    	
		    	if(b.getLife()==0) {
		    		bombs.remove(b);
		    	}
		    	
		    	
		    }
	
		//�����з�̹��
			
		for(int i=0;i<enemies.size();i++) {
			EnemyTank et=enemies.get(i);
			if(et.getLive()) {
			drawTank(et.getX(),et.getY(),g,et.getDirect(),et.getColor());
			
			//��������̹���ӵ�
		    for(int j=0;j<et.getBullets().size();j++) {
		    	Bullet bullet=et.getBullets().get(j);
		    	if(bullet.isBuLive()) {
		    		g.draw3DRect(bullet.getX(), bullet.getY(), 2, 2, false);
		    	}else {
		    		//�������̹������,�ʹ�Vector��ȡ��
		    		et.getBullets().remove(bullet);
		    	}
		    	
		    }
			
		}
	  }
	
		
		
		
	}
	
	/*��̹��
	 * typeΪ̹������
	 * direcΪ̹�˷���
	 */
	
    //��������ש��
	public void drawBrick(Graphics g) {
		for(int i=0;i<irons.size();i++)
		g.drawImage(IronBirck.getImage(),irons.get(i).getX(),irons.get(i).getY(),25,25,this);
		
		for(int i=0;i<grasses.size();i++)
			g.drawImage(GrassBrick.getImage(),grasses.get(i).getX(),grasses.get(i).getY(),25,25,this);
		for(int i=0;i<walls.size();i++)
			if(walls.get(i).getLife()>0)
			g.drawImage(RedBrick.getImage(),walls.get(i).getX(),walls.get(i).getY(),25,25,this);
		for(int i=0;i<waters.size();i++)
			g.drawImage(WaterBirck.getImage(), waters.get(i).getX(),waters.get(i).getY(),25,25,this);
		//��������
		if(frot.getLife()>0)
		g.drawImage(MainFrot.getImage(), frot.getX(), frot.getY(), this);
		
		
	}
	
	//�����ҷ�̹��
	public  void drawTank(int x,int y,Graphics g,int direc,int type) {
		switch(type) {
		case 0:
			g.setColor(Color.cyan);//���û���Ϊ��ɫ
			break;
		
		case 1:
			g.setColor(Color.yellow);//
			
		default:
			break;
		}
		
		switch(direc) {
		case 0://��������
			g.fill3DRect(x, y, 5, 30,false);//һ������ȷ��������͹��ƽ����ʾ���ǰ���ƽ����ʾ�� boolean ֵ
		    g.fill3DRect(x+15, y,5, 30, false);
		    g.fill3DRect(x+5, y+5, 10, 20, false);
		    g.fillOval(x+4, y+10, 10, 10);//ʵ����Բ
		    g.drawLine(x+9, y+15, x+9, y);//��һ��ֱ��
		    break;
		case 1://��������
			g.fill3DRect(x, y, 5, 30,false);//һ������ȷ��������͹��ƽ����ʾ���ǰ���ƽ����ʾ�� boolean ֵ
		    g.fill3DRect(x+15, y,5, 30, false);
		    g.fill3DRect(x+5, y+5, 10, 20, false);
		    g.fillOval(x+4, y+10, 10, 10);//ʵ����Բ
		    g.drawLine(x+9, y+15, x+9, y+30);//��һ��ֱ��
		    break;
		    
		case 2://��������
			g.fill3DRect(x, y, 30, 5,false);//һ������ȷ��������͹��ƽ����ʾ���ǰ���ƽ����ʾ�� boolean ֵ
		    g.fill3DRect(x, y+15,30, 5, false);
		    g.fill3DRect(x+5, y+5, 20, 10, false);
		    g.fillOval(x+9, y+4, 10, 10);//ʵ����Բ
		    g.drawLine(x+5, y+9, x-4, y+9);//��һ��ֱ��
		    break;
		    
		case 3://��������
			g.fill3DRect(x, y, 30, 5,false);//һ������ȷ��������͹��ƽ����ʾ���ǰ���ƽ����ʾ�� boolean ֵ
		    g.fill3DRect(x, y+15,30, 5, false);
		    g.fill3DRect(x+5, y+5, 20, 10, false);
		    g.fillOval(x+9, y+4, 10, 10);//ʵ����Բ
		    g.drawLine(x+15, y+9, x+30, y+9);//��һ��ֱ��
		    break;
			
		default:
	     	break;
		}
	}
	
	
	//����
	
	//�����ҷ�̹���ж�
	private void hitMyTank() {
		
		 for(int i=0;i<enemies.size();i++) {
			 //ȡ��ÿ���з�̹��
			 EnemyTank et=enemies.get(i);
			 
    		for(int j=0;j<et.getBullets().size();j++) {
    			//ȡ��ÿ���ӵ�
    			Bullet bullet=et.getBullets().get(j);
    			hitTank(bullet,hero);//�����ж��Ƿ�����ҷ�̹��
    			
    			
    		}
    			
    	 }
	}
	
	//�ж��ҷ��Ƿ����ש��
	private void myHitBrick() {
		for(int i=0;i<hero.getBullets().size();i++) {
		 Bullet bullet=hero.getBullets().get(i);
		 if(bullet.isBuLive()) {
			 hitBrick(bullet);
		 }
		 		 
		}
		
	}
	//�жϵз��Ƿ����ש��
	private void enemHitBrick() {
		for(int i=0;i<enemies.size();i++) {
			EnemyTank et=enemies.get(i);
			
			if(et.getLive())
			for(int j=0;j<et.getBullets().size();j++) {
				Bullet bullet=et.getBullets().get(j);
				if(bullet.isBuLive())
				hitBrick(bullet);
			}
		}
	}
	
	//�ж��ӵ��Ƿ����ש��
	private  void hitBrick(Bullet s) {
		
		//��ǽ��ײ�ж�
		boolean flag=true;
       //һ���ӵ�ֻ��ѡ��һ��שǽ
			//��ǽ��ײ�ж�
			
		if(flag)
		for(int i=0;i<irons.size();i++) {
			IronBirck iron=irons.get(i);
			if(s.getX()>iron.getX()&&s.getX()<iron.getX()+25&&s.getY()>iron.getY()&&s.getY()<iron.getY()+25) {//����
				//�����ӵ�����
				s.setBuLive(false);
				break;
			}
			
		}
		
	    if(flag)
		for(int i=0;i<walls.size();i++) {
			RedBrick wall=walls.get(i);
			if(wall.getLife()>0&&s.getX()>wall.getX()&&s.getX()<wall.getX()+25&&s.getY()>wall.getY()&&s.getY()<wall.getY()+25) {//����
				//�����ӵ�����
				s.setBuLive(false);
				
			    wall.setLife(wall.getLife()-1);//���������������1
			    break;
			}
			
		}
	    
	    if(flag)
	    	if(frot.getLife()>0&&s.getX()>frot.getX()&&s.getX()<frot.getX()+30&&s.getY()>frot.getY()&&s.getY()<frot.getY()+30) {
	    		s.setBuLive(false);
	    		
	    		frot.setLife(frot.getLife()-1);//�е�����������һ
	    		DataPanel.setSurplusFrot(frot.getLife());
	    		
	    		 
	    		
	    	}
		
	}
	

				
	
	//�ж��ӵ��Ƿ����̹��
	private  void hitTank(Bullet s,Tank tank) {
		//�ж�̹�˷���
		switch(tank.getDirect()) {
		//�����ϻ��߳�����һ����
		case 0:
		case 1:
			if(flag) {
				Boom b=new Boom(tank.getX(),tank.getY());//
			    bombs.add(b);//���뱬ըЧ��
			    flag=false;
			}
			if(s.getX()>tank.getX()&&s.getX()<tank.getX()+20&&s.getY()>tank.getY()&&s.getY()<tank.getY()+30) {
				//�����ӵ�����
				s.setBuLive(false);
				//̹������
				tank.setLive(false);
				
				Boom b=new Boom(tank.getX(),tank.getY());//
				bombs.add(b);//���뱬ըЧ��
				if(tank.getColor()==0)//������е��ǵз�̹��
					nowTankNum--;
				if(hero.getLive()) {
				DataPanel.setSurplusNum(DataPanel.surplusNum-1);
				DataPanel.setScore(DataPanel.score+20);
				}
				
			}
			break;
		//�����������һ����
		case 2:
		case 3:
			if(flag) {
				Boom b=new Boom(tank.getX(),tank.getY());//
			    bombs.add(b);//���뱬ըЧ��
			    flag=false;
			}
			if(s.getX()>tank.getX()&&s.getX()<tank.getX()+30&&s.getY()>tank.getY()&&s.getY()<tank.getY()+20) {
				//�����ӵ�����
				s.setBuLive(false);
				//�з�̹������
				tank.setLive(false);
				Boom b=new Boom(tank.getX(),tank.getY());//
				bombs.add(b);//���뱬ըЧ��
				if(tank.getColor()==0) {
				   nowTankNum--;
				}
				if(hero.getLive()) {
				DataPanel.setSurplusNum(DataPanel.surplusNum-1);
				DataPanel.setScore(DataPanel.score+20);
				}
				
			}
			break;
		default:
			break;
			
		}
	}
	
	//���ез�̹���ж�
	private void hitEnemyTank() {
		
		
		 for(int i=0;i<hero.getBullets().size();i++) {
    		 Bullet bullet=hero.getBullets().get(i);
    		 //�ж��ӵ��Ƿ���Ч
    		 if(bullet.isBuLive()) {
    			 //ȡ��ÿһ��̹�˺����ж�
    			 for(int j=0;j<enemies.size();j++) {
    				 EnemyTank et=enemies.get(j);
    				 if(et.getLive()) {
    					 hitTank(bullet,et);
    				 }
    			 }
    		 }
    	 }
	}
	
	
	
	
	//�ҷ���ײש����
	public void myTouchBrick() {
		touchBrick(hero);
	}
	
	//������ײש����
	public void enemyTouchBrick() {
		for(int i=0;i<enemies.size();i++) {//ȡ��ÿһ���з�̹�˽�����ײ����
			EnemyTank et=enemies.get(i);
			
			if(et.getLive())
			touchBrick(et);
		}
	}
	
	//̹��ש����ײ���
	public void touchBrick(Tank tank) {
		boolean flag=true;
		final int x=tank.getX()+tank.getSpeed();//��¼̹�˽�����λ��
		final int y=tank.getY()+tank.getSpeed();
		
		final int x1=tank.getX()-tank.getSpeed();//ͬ��
		final int y1=tank.getY()-tank.getSpeed();
		
		switch(tank.getDirect()) {
		
		case 0://��
			
			if(flag) {        //��ײ������
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(tank.getX()>iron.getX()-20&&tank.getX()+20<iron.getX()+45&&y1>iron.getY()&&y1<iron.getY()+25) {
						tank.setY(tank.getY()+tank.getSpeed());
						flag=false;
						break;
					   }
				}
			}
			   
			if(flag) {       //��ײשǽ���
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&tank.getX()>wall.getX()-20&&tank.getX()+20<wall.getX()+45&&y1>wall.getY()&&y1<wall.getY()+25) {
						tank.setY(tank.getY()+tank.getSpeed());
					    flag=false;
						break;
						
					}	
				}
			 }
			
			if(flag) {    //��ײˮǽ���
				for(int i=0;i<waters.size();i++) {
					WaterBirck water=waters.get(i);
					if(tank.getX()>water.getX()-20&&tank.getX()+20<water.getX()+45&&y1>water.getY()&&y1<water.getY()+25) {
						tank.setY(tank.getY()+tank.getSpeed());
					    flag=false;
						break;
						
					}	
				}
			}
			
			if(flag) {
				if(frot.getLife()>0&&tank.getX()>frot.getX()-20&&tank.getX()+20<frot.getX()+50&&y+30>frot.getY()&&y+30<frot.getY()+30) {
					tank.setY(tank.getY()-tank.getSpeed());
					break;
				}
			}
			
			break;
			
		case 1://��
			if(flag) {        //��ײ������
				 
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(tank.getX()>iron.getX()-20&&tank.getX()+20<iron.getX()+45&&y+30>iron.getY()&&y+30<iron.getY()+25) {
						tank.setY(tank.getY()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //��ײשǽ���
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&tank.getX()>wall.getX()-20&&tank.getX()+20<wall.getX()+45&&y+30>wall.getY()&&y+30<wall.getY()+25) {
						tank.setY(tank.getY()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //��ײˮǽ���
				for(int i=0;i<waters.size();i++) {
					WaterBirck water=waters.get(i);
					if(tank.getX()>water.getX()-20&&tank.getX()<water.getX()+45&&y+30>water.getY()&&y+30<water.getY()+25) {
						tank.setY(tank.getY()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			
			if(flag) {
				if(frot.getLife()>0&&tank.getX()>frot.getX()-20&&tank.getX()+20<frot.getX()+50&&y+30>frot.getY()&&y+30<frot.getY()+30) {
					tank.setY(tank.getY()-tank.getSpeed());
					break;
				}
			}
				break;
				
		case 2://��
			
			if(flag) {        //��ײ������
			   
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(x1>iron.getX()&&x1<iron.getX()+25&&tank.getY()>iron.getY()-25&&tank.getY()+20<iron.getY()+45) {
						tank.setX(tank.getX()+tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //��ײשǽ���
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&x1>wall.getX()&&x1<wall.getX()+25&&tank.getY()>wall.getY()-25&&tank.getY()+20<wall.getY()+45) {
						tank.setX(tank.getX()+tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //��ײˮǽ���
				for(int i=0;i<waters.size();i++) {
					WaterBirck water=waters.get(i);
					if(x1>water.getX()&&x1<water.getX()+25&&tank.getY()>water.getY()-25&&tank.getY()+20<water.getY()+45) {
						tank.setX(tank.getX()+tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {
				if(frot.getLife()>0&&x1>frot.getX()&&x1<frot.getX()+30&&tank.getY()>frot.getY()-30&&tank.getY()+20<frot.getY()+50) {
					tank.setX(tank.getX()+tank.getSpeed());
					break;
				}
			}
			break;
		case 3://��
			if(flag) {        //��ײ������
				
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(x+30>iron.getX()&&x+30<iron.getX()+25&&tank.getY()>iron.getY()-25&&tank.getY()+20<iron.getY()+45) {
						tank.setX(tank.getX()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //��ײשǽ���
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&x+30>wall.getX()&&x+30<wall.getX()+25&&tank.getY()>wall.getY()-25&&tank.getY()+20<wall.getY()+45) {
						tank.setX(tank.getX()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //��ײˮǽ���
				for(int i=0;i<waters.size();i++) {
					WaterBirck water=waters.get(i);
					if(x+30>water.getX()&&x+30<water.getX()+25&&tank.getY()>water.getY()-25&&tank.getY()+20<water.getY()+45) {
						tank.setX(tank.getX()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			if(flag) {
					if(frot.getLife()>0&&x+30>frot.getX()&&x+30<frot.getX()+30&&tank.getY()>frot.getY()-30&&tank.getY()+20<frot.getY()+50) {
						tank.setX(tank.getX()-tank.getSpeed());
						break;
					}
			}
			
			break;
			
		default:
			
			break;
		}
	}
	
	// ����ĳ����ʱ���ô˷�����
	@Override
	public void keyPressed(KeyEvent e) 	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W) {
			hero.setDirect(0);//����
			 myTouchBrick();//�������������ÿ��������Ӧ���������������ҷ�̹�˳���ש�������ײǽBUG
			hero.moveUp();
			
		}else if(e.getKeyCode()==KeyEvent.VK_S) {
			hero.setDirect(1);//����
			 myTouchBrick();
			hero.moveDown();
		
		}else if(e.getKeyCode()==KeyEvent.VK_A) {
			hero.setDirect(2);//����
			 myTouchBrick();
			hero.moveLeft();
			
		}else if(e.getKeyCode()==KeyEvent.VK_D) {
			hero.setDirect(3);//����
			 myTouchBrick();
			hero.moveRight();
			
		}
		if(e.getKeyCode()==KeyEvent.VK_J) {
			 	//����
			//����ӵ������ڿ����ٴ˷���
		    if(hero.getBullets().size()<4) {
		    	new Thread(new FirePlayer()).start();//����ӵ���Ч
			hero.shotEnemy();
		   }
		}
		
	   repaint();//�ػ�����
		 
	}
	
	//�ڶ��� 
	private void Second() {
		InitData();//��ʼ������
		grassBircks=30;
		wallBircks=28;
		waterBircks=12;
		ironBircks=6;
		//��ʼ������
		int j=0;
		for(int i=0;i<ironBircks;i++) {
			IronBirck iron;
			
			if(i<ironBircks/2)
			 iron=new IronBirck(125,i*25+100);
			else {
		     iron=new IronBirck(250,j*25+100);
		     j++;
			}
			
			irons.add(iron);
		}
		
		//��ʼ���ݵ�
		j=0;
		for(int i=0;i<grassBircks;i++) {
			GrassBrick grass;
			if(i<grassBircks/2)
		    grass=new GrassBrick(0,i*25);
			else {
			grass=new GrassBrick(375,j*25);
			j++;
			}
			grasses.add(grass);
			
		}
		
		//��ʼ��שǽ
		j=0;
		for(int i=0;i<wallBircks;i++) {
			RedBrick wall;
			if(i<wallBircks/2)
			wall=new RedBrick(25+i*25,75);
			else {
			wall=new RedBrick(25+j*25,175);
			j++;
			}
			walls.add(wall);	
		}
		
		//��ʼ��ˮǽ
		j=0;
		for(int i=0;i<waterBircks;i++) {
			WaterBirck water;
			if(i<3) {
				water=new WaterBirck(150,j*25+100);
			}
			else if(i>=3&&i<6) {
				water=new WaterBirck(175,j*25+100);
			}
			else if(i>=6&&i<9) {
				water=new WaterBirck(200,j*25+100);
			}
			else {
				water=new WaterBirck(225,j*25+100);
			}
			
			if(j++==2) {
				j=0;
			}
			waters.add(water);
		}
		
	}
	
	//��ʼ���з�̹��
	private void InitEnemyTank() { 
		for(int i=0;i<initTankNum;i++) {
			EnemyTank et=new EnemyTank((i+1)*50,0 );
			et.setDirect(1);//��������
			et.setColor(0);//����Ϊ��ɫ
			
			et.setEts(enemies);//���з�̹�˽���ȥ������ײ�ж�
			
			//��������̹���߳�
			Thread t=new Thread(et);
			t.start();
			
			//����������ӵ�,ɾ������Ǳ������ͼ�θճ����ͱ����п�Ѫ��BUG
			/*Bullet bullet=new Bullet(et.getX()+9,et.getY(), 1);
			et.getBullets().add(bullet);
			Thread t2=new Thread(bullet);
			t2.start();*/

			
			enemies.add(et);//���̹��
			nowTankNum=enemies.size();
		}
	}
	
	private void InitMainFrot() { 
		//��ʼ������
		frot=new MainFrot(185,230);
				
		//����������ݳ�ʼ��
		DataPanel.surplusFrot=frot.getLife();
		//��ʼ�����˸���
		DataPanel.surplusNum=enSize;
		
		

	}
	
	//��ʼ����ͼ
	private void InitMap() {
		//��ʼ������
				int j=0;
				for(int i=0;i<ironBircks;i++) {
					IronBirck iron;
					
					if(i<ironBircks/2)
					 iron=new IronBirck(100,(i+2)*25);
					else {
				     iron=new IronBirck(275,(j+2)*25);
				     j++;
					}
					
					irons.add(iron);
				}
				
				//��ʼ���ݵ�
				j=0;
				for(int i=0;i<grassBircks;i++) {
					GrassBrick grass;
					if(i<grassBircks/2)
				    grass=new GrassBrick(125+i*25,75);
					else {
					grass=new GrassBrick(125+j*25,100);
					j++;
					}
					grasses.add(grass);
					
				}
				
				//��ʼ��שǽ
				j=0;
				for(int i=0;i<wallBircks;i++) {
					RedBrick wall;
					if(i<wallBircks/2)
					wall=new RedBrick(125+i*25,125);
					else {
					wall=new RedBrick(125+j*25,150);
					j++;
					}
					walls.add(wall);	
				}
				
	}
	
	//��ʼ������
	private void InitData() {
		
		  
		 
		hero.setX(165);
		hero.setY(230);
		hero.setDirect(0);
		irons.removeAllElements();
		grasses.removeAllElements();
		walls.removeAllElements();
		waters.removeAllElements();
		enemies.removeAllElements();
		InitEnemyTank();
		InitMainFrot();
		
	}
	
	//���¿�ʼ��Ϸ
	private void reGame() {
		 hero.setLive(true);//�����ҷ�̹�˴��
		 hero.setDirect(0);//���÷���Ϊ��
		 roundCount=0;//�غ�������
		 ironBircks=14;//��ש����
		 wallBircks=12;//שǽ����
         grassBircks=12;//�ݵؿ���
         DataPanel.score=0;
         InitData();
         InitMap();
		
	}
	//   ����ĳ����ʱ���ô˷�����
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//  �ͷ�ĳ����ʱ���ô˷�����
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
   @Override
   //�ػ��ӵ�
   public void run() {
	// TODO Auto-generated method stub
       while(true) {
    	 try {
    		 Thread.sleep(100);
    	 }catch(InterruptedException e) {
    		 e.printStackTrace();
    	 }
         
    	 
    	 hitEnemyTank();
    	 hitMyTank();
    	 
    
    	 enemyTouchBrick();
    	 
    	 enemHitBrick();
    	 myHitBrick();
    	 
       //ʹһ�������������������������һ���з����ֵ���
       if(nowTankNum!=initTankNum&&DataPanel.surplusNum>=initTankNum) {
    	   EnemyTank et=new EnemyTank((int)(Math.random()*250),0 );
    	    et.setDirect(1);//��������
			et.setColor(0);//����Ϊ��ɫ
		
			et.setEts(enemies);//���з�̹�˽���ȥ������ײ�ж�
			
			//��������̹���߳�
			Thread t=new Thread(et);
			t.start();
			enemies.add(et);
			nowTankNum++;   
       }
       
       
    	//������˶��������ҷ�ʤ��
       if(DataPanel.surplusNum==0&&roundCount==0) {
       		 repaint();
       		 if(bombs.size()==0) {//������ֵ��ڶ��زŻ�ǰһ�ص�ը��
       		 JOptionPane.showMessageDialog(this, "��һ���ۼƵ÷�"+DataPanel.score, "�ڶ���",JOptionPane.INFORMATION_MESSAGE);
           	 Second();       
           	 roundCount++;}
       	}
       
       //�����ض�ͨ��ʱ�˳���Ϸ
       else if(roundCount!=0&&DataPanel.surplusNum==0) {
    	   if(bombs.size()==0) {
    	    JOptionPane.showMessageDialog(this, "�ܵ÷�"+DataPanel.score, "You Win!!!",JOptionPane.INFORMATION_MESSAGE);
    	    String name=TankBattle.name;
    	    int score=DataPanel.score;
    	    new Conndatum().addData(name,score);//��ӵ÷����ݵ����ݿ�
    	    System.exit(0);//������Ϸ
    	    }
       }
    	
       //�ҷ�̹���������߻���Ѫ��Ϊ0
       if(!hero.getLive()||frot.getLife()<=0) {
    	   if(bombs.size()==0) {
    	   int res=JOptionPane.showConfirmDialog(this, "\t ���¿�ʼ?","GameOver",JOptionPane.YES_NO_OPTION);
    	   if(res==JOptionPane.YES_OPTION) {
    		 reGame();//���¿�ʼ��Ϸ  
    	   }
    	   else {
    		  
    		   System.exit(0);
    	   }
    	}
       }
       
       repaint();
   }
  } 
}
   
 
