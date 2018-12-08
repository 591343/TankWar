import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author LENOVO
 *功能:坦克大战2.0
 *目前已实现的功能
 *1.画出坦克
 *2.坦克的移动(W,S,A,D分别控制上下左右移动)
 *3.画出敌方坦克
 *4.子弹的发射(按J键开火)
 *5.实现子弹连发（屏幕中子弹数量可控制）+击中目标+爆照效果
 *6.使敌人可以攻击可以移动
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private MyPanel p1=null;
	private Container container=getContentPane();
    
	public TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		p1=new MyPanel();
		Thread t=new Thread(p1);
		t.start();//运行该线程
		
	    setSize(400, 300);
	    setVisible(true);
	    setLayout(new BorderLayout());
	    container.add(p1,BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    addKeyListener(p1);//注册监听
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      new TankBattle();
	}

}
