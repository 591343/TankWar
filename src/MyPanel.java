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
 * 面板
* drawTank (坦克坐标x,y,画笔g,方向，坦克类型)
* 方法介绍：
* 可以设置-->坦克的颜色（类型：敌方坦克，我方坦克），方向，出现的坐标
* 
* 如果type是default 则默认颜色为画出黑色坦克
* 
* 封装性：将坦克封装到方法中。
* 
*/

@SuppressWarnings("serial")
class MyPanel extends JPanel implements KeyListener,Runnable {

	 private Hero hero=null;//我方坦克
	 private Vector<EnemyTank> enemies=new Vector<EnemyTank>();//敌方坦克
	 private Vector<Boom> bombs=new Vector<Boom>();//定义炸弹集合
	 private int enSize=3;//敌方坦克上限数量
	 private Vector<Image> blasts=new Vector<Image>();//爆炸图片集合
	 private boolean flag=true;//预先加载炸弹防止第一次击打坦克不出现爆炸效果
	
	 
	 MyPanel() {
		// TODO Auto-generated constructor stub
		hero=new Hero(200,150);//设置坦克出现的位置
		for(int i=0;i<enSize;i++) {
			EnemyTank et=new EnemyTank((i+1)*80,0 );
			et.setDirect(1);//方向向下
			et.setColor(0);//设置为青色
			//启动敌人坦克线程
			Thread t=new Thread(et);
			t.start();
			
			//给敌人添加子弹
			Bullet bullet=new Bullet(et.getX()+9,et.getY(), 1);
			et.getBullets().add(bullet);
			Thread t2=new Thread(bullet);
			t2.start();

			
			enemies.add(et);//添加坦克
		}
		
		//初始化爆炸图片
		for(int i=1;i<=8;i++) {
		  Image image=null;
		   image=Toolkit.getDefaultToolkit().createImage(MyPanel.class.getResource("blast"+i+".gif"));
		   blasts.add(image);//将爆炸图片添加进去
		   
		}

	}
	
	@Override
    public  void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.fillRect(0, 0, 400, 300);//绘制实心矩形默认为黑色
		
		//画出我方坦克
		drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		
		//画出子弹
		for(int i=0;i<hero.getBullets().size();i++) {
			//取出子弹
			Bullet myBullet=hero.getBullets().get(i);
		if(myBullet.isBuLive()) {
			g.setColor(Color.yellow);//将子弹颜色设置为白色
		    g.fill3DRect(myBullet.getX(),myBullet.getY() ,2 ,2 , false);
		 }
		
		if(!myBullet.isBuLive()) {//如果子弹死亡就移除子弹集合
			hero.getBullets().remove(myBullet);
		 }
		
		}
		
		//画出炸弹
		
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
	    	//如果life为0就将炸弹从bombs向量中去除掉
	    	if(b.getLife()==0) {
	    		bombs.remove(b);
	    	}
	    	
	    	
	    }
	
		//画出敌方坦克
			
		for(int i=0;i<enemies.size();i++) {
			EnemyTank et=enemies.get(i);
			if(et.getLive()) {
			drawTank(et.getX(),et.getY(),g,et.getDirect(),et.getColor());
			
			//画出敌人坦克子弹
		    for(int j=0;j<et.getBullets().size();j++) {
		    	Bullet bullet=et.getBullets().get(j);
		    	if(bullet.isBuLive()) {
		    		g.draw3DRect(bullet.getX(), bullet.getY(), 2, 2, false);
		    	}else {
		    		//如果敌人坦克死亡,就从Vector中取掉
		    		et.getBullets().remove(bullet);
		    	}
		    	
		    }
			
		}
	  }
	}
	
	/*画坦克
	 * type为坦克类型
	 * direc为坦克方向
	 */
     
	public  void drawTank(int x,int y,Graphics g,int direc,int type) {
		switch(type) {
		case 0:
			g.setColor(Color.cyan);//设置画笔为青色
			break;
		
		case 1:
			g.setColor(Color.yellow);//
			
		default:
			break;
		}
		
		switch(direc) {
		case 0://方向向上
			g.fill3DRect(x, y, 5, 30,false);//一个用于确定矩形是凸出平面显示还是凹入平面显示的 boolean 值
		    g.fill3DRect(x+15, y,5, 30, false);
		    g.fill3DRect(x+5, y+5, 10, 20, false);
		    g.fillOval(x+4, y+10, 10, 10);//实心椭圆
		    g.drawLine(x+9, y+15, x+9, y);//画一条直线
		    break;
		case 1://方向向下
			g.fill3DRect(x, y, 5, 30,false);//一个用于确定矩形是凸出平面显示还是凹入平面显示的 boolean 值
		    g.fill3DRect(x+15, y,5, 30, false);
		    g.fill3DRect(x+5, y+5, 10, 20, false);
		    g.fillOval(x+4, y+10, 10, 10);//实心椭圆
		    g.drawLine(x+9, y+15, x+9, y+30);//画一条直线
		    break;
		    
		case 2://方向向左
			g.fill3DRect(x, y, 30, 5,false);//一个用于确定矩形是凸出平面显示还是凹入平面显示的 boolean 值
		    g.fill3DRect(x, y+15,30, 5, false);
		    g.fill3DRect(x+5, y+5, 20, 10, false);
		    g.fillOval(x+9, y+4, 10, 10);//实心椭圆
		    g.drawLine(x+5, y+9, x-4, y+9);//画一条直线
		    break;
		    
		case 3://方向向右
			g.fill3DRect(x, y, 30, 5,false);//一个用于确定矩形是凸出平面显示还是凹入平面显示的 boolean 值
		    g.fill3DRect(x, y+15,30, 5, false);
		    g.fill3DRect(x+5, y+5, 20, 10, false);
		    g.fillOval(x+9, y+4, 10, 10);//实心椭圆
		    g.drawLine(x+15, y+9, x+30, y+9);//画一条直线
		    break;
			
		default:
	     	break;
		}
	}
	
	
	
	//击中我方坦克判断
	private void hitMyTank() {
		
		 for(int i=0;i<enemies.size();i++) {
			 //取出每个敌方坦克
			 EnemyTank et=enemies.get(i);
			 
    		for(int j=0;j<et.getBullets().size();j++) {
    			//取出每刻子弹
    			Bullet bullet=et.getBullets().get(j);
    			hitTank(bullet,hero);//进行判断是否击中我方坦克
    			
    		}
    			
    	 }
	}
	
	//判断子弹是否击中坦克
	private  void hitTank(Bullet s,Tank tank) {
		//判断坦克方向
		switch(tank.getDirect()) {
		//方向朝上或者朝下是一样的
		case 0:
		case 1:
			if(flag) {
				Boom b=new Boom(tank.getX(),tank.getY());//
			    bombs.add(b);//加入爆炸效果
			    flag=false;
			}
			if(s.getX()>tank.getX()&&s.getX()<tank.getX()+20&&s.getY()>tank.getY()&&s.getY()<tank.getY()+30) {
				//击中子弹死亡
				s.setBuLive(false);
				//坦克死亡
				tank.setLive(false);
				
				Boom b=new Boom(tank.getX(),tank.getY());//
				bombs.add(b);//加入爆炸效果
				
				
			}
			break;
		//方向朝左或朝右是一样的
		case 2:
		case 3:
			if(flag) {
				Boom b=new Boom(tank.getX(),tank.getY());//
			    bombs.add(b);//加入爆炸效果
			    flag=false;
			}
			if(s.getX()>tank.getX()&&s.getX()<tank.getX()+30&&s.getY()>tank.getY()&&s.getY()<tank.getY()+20) {
				//击中子弹死亡
				s.setBuLive(false);
				//敌方坦克死亡
				tank.setLive(false);
				Boom b=new Boom(tank.getX(),tank.getY());//
				bombs.add(b);//加入爆炸效果
				
			}
			break;
		default:
			break;
			
		}
	}
	
	//击中敌方坦克判断
	private void hitEnemyTank() {
		
		
		 for(int i=0;i<hero.getBullets().size();i++) {
    		 Bullet bullet=hero.getBullets().get(i);
    		 //判断子弹是否有效
    		 if(bullet.isBuLive()) {
    			 //取出每一个坦克和它判断
    			 for(int j=0;j<enemies.size();j++) {
    				 EnemyTank et=enemies.get(j);
    				 if(et.getLive()) {
    					 hitTank(bullet,et);
    				 }
    			 }
    		 }
    	 }
	}
	
	// 按下某个键时调用此方法。
	@Override
	public void keyPressed(KeyEvent e) 	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W) {
			hero.setDirect(0);//向上
			hero.moveUp();
		}else if(e.getKeyCode()==KeyEvent.VK_S) {
			hero.setDirect(1);//向下
			hero.moveDown();
		}else if(e.getKeyCode()==KeyEvent.VK_A) {
			hero.setDirect(2);//向左
			hero.moveLeft();
		}else if(e.getKeyCode()==KeyEvent.VK_D) {
			hero.setDirect(3);//向右
			hero.moveRight();
		}
		if(e.getKeyCode()==KeyEvent.VK_J) {
			 	//开火
			//如果子弹不存在可以再此发射
		    if(hero.getBullets().size()<1) {
			hero.shotEnemy();
		   }
		}
		
	   repaint();//重绘此组件
		 
	}
	
	
	//   键入某个键时调用此方法。
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//  释放某个键时调用此方法。
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
	}
	
   @Override
   //重华子弹
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
