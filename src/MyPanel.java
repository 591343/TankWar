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
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Vector;

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
* 
*/

@SuppressWarnings("serial")
class MyPanel extends JPanel implements KeyListener,Runnable {

	 private Hero hero=null;//�ҷ�̹��
	 private Vector<EnemyTank> enemies=new Vector<EnemyTank>();//�з�̹��
	 private Vector<Boom> bombs=new Vector<Boom>();//����ը������
	 private int enSize=3;//�з�̹����������
	 private Vector<Image> blasts=new Vector<Image>();//��ըͼƬ����
	 private boolean flag=true;//Ԥ�ȼ���ը����ֹ��һ�λ���̹�˲����ֱ�ըЧ��
	
	 
	 MyPanel() {
		// TODO Auto-generated constructor stub
		hero=new Hero(200,150);//����̹�˳��ֵ�λ��
		for(int i=0;i<enSize;i++) {
			EnemyTank et=new EnemyTank((i+1)*80,0 );
			et.setDirect(1);//��������
			et.setColor(0);//����Ϊ��ɫ
			//��������̹���߳�
			Thread t=new Thread(et);
			t.start();
			
			//����������ӵ�
			Bullet bullet=new Bullet(et.getX()+9,et.getY(), 1);
			et.getBullets().add(bullet);
			Thread t2=new Thread(bullet);
			t2.start();

			
			enemies.add(et);//���̹��
		}
		
		//��ʼ����ըͼƬ
		for(int i=1;i<=8;i++) {
		  Image image=null;
		   image=Toolkit.getDefaultToolkit().createImage(MyPanel.class.getResource("blast"+i+".gif"));
		   blasts.add(image);//����ըͼƬ��ӽ�ȥ
		   
		}

	}
	
	@Override
    public  void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.fillRect(0, 0, 400, 300);//����ʵ�ľ���Ĭ��Ϊ��ɫ
		
		//�����ҷ�̹��
		drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		
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
		
		//����ը��
		
	    for(int i=0;i<bombs.size();i++) {
	    	Boom b=bombs.get(i);	
	        
	    	
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
	    	//System.out.println(b.getX()+" "+getY());
	    	//System.out.format("1+%d\n", i);
	    		//System.out.format("1+%d\n", i);
	    	//���lifeΪ0�ͽ�ը����bombs������ȥ����
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
	
	// ����ĳ����ʱ���ô˷�����
	@Override
	public void keyPressed(KeyEvent e) 	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W) {
			hero.setDirect(0);//����
			hero.moveUp();
		}else if(e.getKeyCode()==KeyEvent.VK_S) {
			hero.setDirect(1);//����
			hero.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A) {
			hero.setDirect(2);//����
			hero.moveLeft();
		}else if(e.getKeyCode()==KeyEvent.VK_D) {
			hero.setDirect(3);//����
			hero.moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_J) {
			 	//����
			//����ӵ������ڿ����ٴ˷���
		    if(hero.getBullets().size()<1) {
			hero.shotEnemy();
		   }
		}
		
	   repaint();//�ػ�����
		 
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
    	
    	 repaint();
        }
      } 
	
 }
