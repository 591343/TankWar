
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
 * 面板
* drawTank (坦克坐标x,y,画笔g,方向，坦克类型)
* 方法介绍：
* 可以设置-->坦克的颜色（类型：敌方坦克，我方坦克），方向，出现的坐标
* 
* 如果type是default 则默认颜色为画出黑色坦克
* 
* 封装性：将坦克封装到方法中。
* 基地像素30X30
* 坦克像素长30,宽20
* 转块像素统一为25X25
*/

@SuppressWarnings("serial")
class MyPanel extends JPanel implements KeyListener,Runnable {

	 private Hero hero=null;//我方坦克
	 private Vector<EnemyTank> enemies=new Vector<EnemyTank>();//敌方坦克
	 private Vector<Boom> bombs=new Vector<Boom>();//定义炸弹集合
	 private final int enSize=10;//敌方坦克上限数量
	 private Vector<Image> blasts=new Vector<Image>();//爆炸图片集合
	 private boolean flag=true;//预先加载炸弹防止第一次击打坦克不出现爆炸效果
	 private Vector<IronBirck> irons=new Vector<IronBirck>();//铁墙集合
	 private Vector<GrassBrick> grasses=new Vector<GrassBrick>();//草地集合
	 private Vector<RedBrick> walls=new Vector<RedBrick>();//砖墙集合
	 private Vector<WaterBirck> waters=new Vector<WaterBirck>();//水墙集合
	 private MainFrot frot;
	 private int roundCount=0;//回合计数
	 private int nowTankNum=0;//当前坦克计数
     private final int initTankNum=5;//初始坦克数


	 private  int ironBircks=14;//铁砖块数
	 private  int wallBircks=12;//砖墙块数
	 private  int grassBircks=12;//草地块数
	 private  int waterBircks=0;//水墙块数
	 
	
	 
	 MyPanel() {
		// TODO Auto-generated constructor stub
		hero=new Hero(165,230);//设置我方坦克出现的位置
		
		InitEnemyTank();//初始化敌人坦克
		InitMainFrot();//初始化基地
		InitMap();//初始化地图
	
		
		//初始化爆炸图片
		for(int i=1;i<=8;i++) {
		  Image image=null;
		   image=Toolkit.getDefaultToolkit().createImage(MyPanel.class.getResource("blast"+i+".gif"));
		   blasts.add(image);//将爆炸图片添加进集合
		}
		
		
	}
	
	@Override
    public  void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 400, 300);//绘制实心矩形默认为黑色
		
		//画出我方坦克
		//System.out.println(hero.getLive());
		if(hero.getLive())
		drawTank(hero.getX(),hero.getY(),g,hero.getDirect(),1);
		//如果砖块没有被打掉就不重新画
		
		
		drawBrick(g);

		
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
		
		 for(int i=0;i<bombs.size();i++) {
		    	Boom b=bombs.get(i);	
		    	
		    	
		    	
		    	new Thread(new BlastPlayer()).start();//坦克爆炸音效
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
	
    //画出各种砖块
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
		//画出基地
		if(frot.getLife()>0)
		g.drawImage(MainFrot.getImage(), frot.getX(), frot.getY(), this);
		
		
	}
	
	//画出我方坦克
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
	
	
	//击中
	
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
	
	//判断我方是否击中砖块
	private void myHitBrick() {
		for(int i=0;i<hero.getBullets().size();i++) {
		 Bullet bullet=hero.getBullets().get(i);
		 if(bullet.isBuLive()) {
			 hitBrick(bullet);
		 }
		 		 
		}
		
	}
	//判断敌方是否击中砖块
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
	
	//判断子弹是否击中砖块
	private  void hitBrick(Bullet s) {
		
		//铁墙碰撞判断
		boolean flag=true;
       //一发子弹只能选中一块砖墙
			//铁墙碰撞判断
			
		if(flag)
		for(int i=0;i<irons.size();i++) {
			IronBirck iron=irons.get(i);
			if(s.getX()>iron.getX()&&s.getX()<iron.getX()+25&&s.getY()>iron.getY()&&s.getY()<iron.getY()+25) {//击中
				//击中子弹死亡
				s.setBuLive(false);
				break;
			}
			
		}
		
	    if(flag)
		for(int i=0;i<walls.size();i++) {
			RedBrick wall=walls.get(i);
			if(wall.getLife()>0&&s.getX()>wall.getX()&&s.getX()<wall.getX()+25&&s.getY()>wall.getY()&&s.getY()<wall.getY()+25) {//击中
				//击中子弹死亡
				s.setBuLive(false);
				
			    wall.setLife(wall.getLife()-1);//如果被击中生命减1
			    break;
			}
			
		}
	    
	    if(flag)
	    	if(frot.getLife()>0&&s.getX()>frot.getX()&&s.getX()<frot.getX()+30&&s.getY()>frot.getY()&&s.getY()<frot.getY()+30) {
	    		s.setBuLive(false);
	    		
	    		frot.setLife(frot.getLife()-1);//中弹基地生命减一
	    		DataPanel.setSurplusFrot(frot.getLife());
	    		
	    		 
	    		
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
				if(tank.getColor()==0)//如果击中的是敌方坦克
					nowTankNum--;
				if(hero.getLive()) {
				DataPanel.setSurplusNum(DataPanel.surplusNum-1);
				DataPanel.setScore(DataPanel.score+20);
				}
				
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
	
	
	
	
	//我方碰撞砖块检测
	public void myTouchBrick() {
		touchBrick(hero);
	}
	
	//敌人碰撞砖块检测
	public void enemyTouchBrick() {
		for(int i=0;i<enemies.size();i++) {//取出每一个敌方坦克进行碰撞测试
			EnemyTank et=enemies.get(i);
			
			if(et.getLive())
			touchBrick(et);
		}
	}
	
	//坦克砖块碰撞检测
	public void touchBrick(Tank tank) {
		boolean flag=true;
		final int x=tank.getX()+tank.getSpeed();//记录坦克接下来位置
		final int y=tank.getY()+tank.getSpeed();
		
		final int x1=tank.getX()-tank.getSpeed();//同上
		final int y1=tank.getY()-tank.getSpeed();
		
		switch(tank.getDirect()) {
		
		case 0://上
			
			if(flag) {        //碰撞铁块检测
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(tank.getX()>iron.getX()-20&&tank.getX()+20<iron.getX()+45&&y1>iron.getY()&&y1<iron.getY()+25) {
						tank.setY(tank.getY()+tank.getSpeed());
						flag=false;
						break;
					   }
				}
			}
			   
			if(flag) {       //碰撞砖墙检测
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&tank.getX()>wall.getX()-20&&tank.getX()+20<wall.getX()+45&&y1>wall.getY()&&y1<wall.getY()+25) {
						tank.setY(tank.getY()+tank.getSpeed());
					    flag=false;
						break;
						
					}	
				}
			 }
			
			if(flag) {    //碰撞水墙检测
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
			
		case 1://下
			if(flag) {        //碰撞铁块检测
				 
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(tank.getX()>iron.getX()-20&&tank.getX()+20<iron.getX()+45&&y+30>iron.getY()&&y+30<iron.getY()+25) {
						tank.setY(tank.getY()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //碰撞砖墙检测
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&tank.getX()>wall.getX()-20&&tank.getX()+20<wall.getX()+45&&y+30>wall.getY()&&y+30<wall.getY()+25) {
						tank.setY(tank.getY()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //碰撞水墙检测
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
				
		case 2://左
			
			if(flag) {        //碰撞铁块检测
			   
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(x1>iron.getX()&&x1<iron.getX()+25&&tank.getY()>iron.getY()-25&&tank.getY()+20<iron.getY()+45) {
						tank.setX(tank.getX()+tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //碰撞砖墙检测
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&x1>wall.getX()&&x1<wall.getX()+25&&tank.getY()>wall.getY()-25&&tank.getY()+20<wall.getY()+45) {
						tank.setX(tank.getX()+tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //碰撞水墙检测
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
		case 3://右
			if(flag) {        //碰撞铁块检测
				
				for(int i=0;i<irons.size();i++) {
					IronBirck iron=irons.get(i);
					if(x+30>iron.getX()&&x+30<iron.getX()+25&&tank.getY()>iron.getY()-25&&tank.getY()+20<iron.getY()+45) {
						tank.setX(tank.getX()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
	   
			if(flag) {       //碰撞砖墙检测
				for(int i=0;i<walls.size();i++) {
					RedBrick wall=walls.get(i);
					if(wall.getLife()>0&&x+30>wall.getX()&&x+30<wall.getX()+25&&tank.getY()>wall.getY()-25&&tank.getY()+20<wall.getY()+45) {
						tank.setX(tank.getX()-tank.getSpeed());
						flag=false;
						break;
					}
				}
			}
			
			if(flag) {       //碰撞水墙检测
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
	
	// 按下某个键时调用此方法。
	@Override
	public void keyPressed(KeyEvent e) 	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W) {
			hero.setDirect(0);//向上
			 myTouchBrick();//将这个函数放在每个按键响应里面解决连续按键我方坦克冲破砖块和连续撞墙BUG
			hero.moveUp();
			
		}else if(e.getKeyCode()==KeyEvent.VK_S) {
			hero.setDirect(1);//向下
			 myTouchBrick();
			hero.moveDown();
		
		}else if(e.getKeyCode()==KeyEvent.VK_A) {
			hero.setDirect(2);//向左
			 myTouchBrick();
			hero.moveLeft();
			
		}else if(e.getKeyCode()==KeyEvent.VK_D) {
			hero.setDirect(3);//向右
			 myTouchBrick();
			hero.moveRight();
			
		}
		if(e.getKeyCode()==KeyEvent.VK_J) {
			 	//开火
			//如果子弹不存在可以再此发射
		    if(hero.getBullets().size()<4) {
		    	new Thread(new FirePlayer()).start();//射击子弹音效
			hero.shotEnemy();
		   }
		}
		
	   repaint();//重绘此组件
		 
	}
	
	//第二关 
	private void Second() {
		InitData();//初始化数据
		grassBircks=30;
		wallBircks=28;
		waterBircks=12;
		ironBircks=6;
		//初始化铁块
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
		
		//初始化草地
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
		
		//出始化砖墙
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
		
		//初始化水墙
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
	
	//初始化敌方坦克
	private void InitEnemyTank() { 
		for(int i=0;i<initTankNum;i++) {
			EnemyTank et=new EnemyTank((i+1)*50,0 );
			et.setDirect(1);//方向向下
			et.setColor(0);//设置为青色
			
			et.setEts(enemies);//将敌方坦克交过去进行碰撞判断
			
			//启动敌人坦克线程
			Thread t=new Thread(et);
			t.start();
			
			//给敌人添加子弹,删掉这段是避免出现图形刚出来就被击中扣血的BUG
			/*Bullet bullet=new Bullet(et.getX()+9,et.getY(), 1);
			et.getBullets().add(bullet);
			Thread t2=new Thread(bullet);
			t2.start();*/

			
			enemies.add(et);//添加坦克
			nowTankNum=enemies.size();
		}
	}
	
	private void InitMainFrot() { 
		//初始化基地
		frot=new MainFrot(185,230);
				
		//数据面板数据初始化
		DataPanel.surplusFrot=frot.getLife();
		//初始化敌人个数
		DataPanel.surplusNum=enSize;
		
		

	}
	
	//初始化地图
	private void InitMap() {
		//初始化铁块
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
				
				//初始化草地
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
				
				//出始化砖墙
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
	
	//初始化数据
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
	
	//重新开始游戏
	private void reGame() {
		 hero.setLive(true);//设置我方坦克存活
		 hero.setDirect(0);//设置方向为上
		 roundCount=0;//回合数置零
		 ironBircks=14;//铁砖块数
		 wallBircks=12;//砖墙块数
         grassBircks=12;//草地块数
         DataPanel.score=0;
         InitData();
         InitMap();
		
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
    	 
    
    	 enemyTouchBrick();
    	 
    	 enemHitBrick();
    	 myHitBrick();
    	 
       //使一个敌人死亡后还能随机的在任意一个敌方出现敌人
       if(nowTankNum!=initTankNum&&DataPanel.surplusNum>=initTankNum) {
    	   EnemyTank et=new EnemyTank((int)(Math.random()*250),0 );
    	    et.setDirect(1);//方向向下
			et.setColor(0);//设置为青色
		
			et.setEts(enemies);//将敌方坦克交过去进行碰撞判断
			
			//启动敌人坦克线程
			Thread t=new Thread(et);
			t.start();
			enemies.add(et);
			nowTankNum++;   
       }
       
       
    	//如果敌人都死亡则我方胜利
       if(DataPanel.surplusNum==0&&roundCount==0) {
       		 repaint();
       		 if(bombs.size()==0) {//避免出现到第二关才画前一关的炸弹
       		 JOptionPane.showMessageDialog(this, "第一关累计得分"+DataPanel.score, "第二关",JOptionPane.INFORMATION_MESSAGE);
           	 Second();       
           	 roundCount++;}
       	}
       
       //当两关都通过时退出游戏
       else if(roundCount!=0&&DataPanel.surplusNum==0) {
    	   if(bombs.size()==0) {
    	    JOptionPane.showMessageDialog(this, "总得分"+DataPanel.score, "You Win!!!",JOptionPane.INFORMATION_MESSAGE);
    	    String name=TankBattle.name;
    	    int score=DataPanel.score;
    	    new Conndatum().addData(name,score);//添加得分数据到数据库
    	    System.exit(0);//结束游戏
    	    }
       }
    	
       //我方坦克死亡或者基地血量为0
       if(!hero.getLive()||frot.getLife()<=0) {
    	   if(bombs.size()==0) {
    	   int res=JOptionPane.showConfirmDialog(this, "\t 重新开始?","GameOver",JOptionPane.YES_NO_OPTION);
    	   if(res==JOptionPane.YES_OPTION) {
    		 reGame();//重新开始游戏  
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
   
 
