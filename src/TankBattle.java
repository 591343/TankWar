import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;



import javafx.scene.control.Button;

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
 *15.玩家姓名分数通关时间到数据库中
 *16.将通关时间按从小到达排行
 
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private  MyPanel p1=null;
	private DataPanel p2=null;
	//连接数据库
	private Conndatum  conndatum;
	public static long startTime;
	
	//开始游戏
	private JButton startGame;
	//查看排行榜
	private JButton checkRank;
	//游戏帮助
	private JButton helpGame;
	
	//退出游戏
	private JButton exitGame;
	private javax.swing.JLabel  tankText;
	
	

	private Container container=getContentPane();
	TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		setTitle("坦克大战1.0");
		//setLayout(new );
		
		setLayout(null);
		setBounds(400, 250,600, 300);
		
	   
	    setVisible(true);
	    setResizable(false);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    tankText=new javax.swing.JLabel("坦克大战1.0");
	    tankText.setFont(new Font("宋体",Font.BOLD+Font.ITALIC,35));
	    
	    tankText.setBounds(180,10,230,80);
	    startGame=new JButton("开始游戏");
	    startGame.setBounds(230, 90, 100, 30);
	    checkRank=new JButton("查看排行榜");
	    checkRank.setBounds(230, 125, 100,30);
	    helpGame=new JButton("游戏帮助");
	    helpGame.setBounds(230, 160, 100, 30);
	    exitGame=new JButton("退出游戏");
	    exitGame.setBounds(230, 195, 100, 30);
	 
        add(tankText);
	    add(startGame);
	    add(checkRank);
    	add(helpGame);
    	add(exitGame);
    	//加监听器
    	//开始游戏
    	startGame.addActionListener(new ActionListener() {
			
			//@Override
		public void actionPerformed(ActionEvent e) {
			//	TODO Auto-generated method stub
				DataPanel.name=JOptionPane.showInputDialog("留下大名");
				if(DataPanel.name!=null) {
	          	    //关闭排行榜窗口
	        		//conndatum.shutRank();
					remove(tankText);
					remove(startGame);
					remove(checkRank);
					remove(helpGame);
					remove(exitGame);
					setLayout(new BorderLayout());
	          	  new Thread(new MP3Player()).start();//游戏声音加载
	           	  //加载开始图片
	           	      p1=new MyPanel();
	           	 
	        		  p2=new DataPanel();
	        		  p1.setBounds(0,0,400, 300);
	         		  p2.setBounds(400,0,400,300);
	         		 
	        		  
	        	    
	           	  
	           	  
	           	  Thread t=new Thread(p1);
	           	  Thread t1=new Thread(p2);
	           	  t.start();
	           	  t1.start();
	           	  
	             	container.add(p1);
      	  	      container.add(p2);
      	  	
	           	  
	           	  
	           	  //获取游戏开始时间
	           	  startTime=System.currentTimeMillis();
	           	  addKeyListener(p1);//注册监听  
	           	  
				}
	        
	        	
		}	
			
		});
   
        //查看排行榜
        checkRank.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 conndatum=new Conndatum();
	        	 conndatum.showRank();
			}
		});
        
        //游戏帮助
        helpGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HelpJFrame();
			}
		});
        
        //退出游戏
        exitGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
        
        
	  
	  
	   
	}

   public static void main(String[] args) {
	new TankBattle();
}
   
}


