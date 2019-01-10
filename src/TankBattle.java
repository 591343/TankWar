import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author ChenXiao
 *功能:坦克大战1.0\
 *目前已实现的功能
 *1.画出坦克
 *2.坦克的移动(W,S,A,D分别控制上下左右移动)
 *3.画出敌方坦克
 *4.子弹的发射(按J键开火)
 *5.实现子弹连发（屏幕中子弹数量可控制）+击中目标+爆照效果
 *6.使敌人可以攻击可以移动
 *7.坦克重叠BUG的修复
 *8.实现地图,敌我子弹和坦克碰撞检测
 *9.画出基地
 *10.随机出现敌方坦克
 *11.添加爆炸，子弹射出，游戏进行音效
 *12.记分板(记录敌人坦克剩余数量，我方基地生命数，我得方的分数)  目前遗留一个BUG
 *13.当基地血量为0时宣布游戏失败当敌人坦克死光后宣布游戏胜利
 *14.多关卡和重新开始游戏
 
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private  MyPanel p1=null;
	private DataPanel p2=null;
	
    static  String name=null;//用户姓名
	//private Image image 

	private Container container=getContentPane();
	TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		setTitle("坦克大战1.0");
		setLayout(new BorderLayout());
		
		
		setBounds(400, 250,600, 300);
		
	   
	    setVisible(true);
	    setResizable(false);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    Graphics p=getGraphics();
    	p.drawImage(GameUtile.getImage("startfgame.png"), 0, 0, this);
    	
        int res=JOptionPane.showConfirmDialog(null, "开始游戏?", "TankBattle",JOptionPane.YES_NO_OPTION);
        new Conndatum().showRank();//展示排行榜
        name=JOptionPane.showInputDialog("留下大名");
  	  
        if(res==JOptionPane.YES_OPTION){ 
        	
        	 new Thread(new MP3Player()).start();//游戏声音加载
        	  //加载开始图片
        	  p1=new MyPanel();
        	 
    		  p2=new DataPanel();
    		  p1.setBounds(0,0,400, 300);
      		  p2.setBounds(400,0,400,300);
    		  container.add(p1);
    	  	  container.add(p2);
    	  	
    	    
        	  
        	  
        	  Thread t=new Thread(p1);
        	  Thread t1=new Thread(p2);
        	  t.start();
        	  t1.start();
        	  
      		 
        	  
        }else{
            System.exit(0);    //退出游戏
           
        }
	    
	    addKeyListener(p1);//注册监听
	  
	   
	}

   public static void main(String[] args) {
	new TankBattle();
}
   
}


