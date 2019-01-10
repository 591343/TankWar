import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * 1.记录敌方坦克剩余数量
 * 2.记录我方基地剩余血量
 * 3.记录我方分数(击毁一辆敌方坦克20分)
 * 
 */

public class DataPanel extends JPanel implements Runnable {
     static int surplusNum;//敌方剩余坦克数量
     static int surplusFrot;//我方母堡剩余血量
     static int score;//我方得分
    
    static JLabel num=null;
    static JLabel blood=null;
    static JLabel myScore=null;
    
    static {
    	
    }
    
	public DataPanel() {
		// TODO Auto-generated constructor stub
		this.setLayout(null);//使用绝对布局
		this.setBounds(400, 0, 200, 300);
		num=new JLabel("坦克数量:"+surplusNum);
		blood=new JLabel("基地血量:"+surplusFrot);
		myScore=new JLabel("我方得分:"+score);
	    
		num.setBounds(410,10,100, 20);
		blood.setBounds(410,40,100,20);
		myScore.setBounds(410,70,100,20);
		
		this.add(num);
		this.add(blood);
		this.add(myScore);
	}

	
	public int getSurplusNum() {
		return surplusNum;
	}

	public  static void setSurplusNum(int SurplusNum) {
		surplusNum = SurplusNum;
	}
	
	//刷新数据
	private  void freshData() {
		num.setText("坦克数量:"+surplusNum);
 		blood.setText("基地血量:"+surplusFrot);
 		myScore.setText("我方得分:"+score);
	}
	


	public int getSurplusFrot() {
		return surplusFrot;
	}

	public static void setSurplusFrot(int SurplusFrot) {
		surplusFrot = SurplusFrot;
	}

	public int getScore() {
		return score;
	}

	public static  void setScore(int Score) {
		   score = Score;
	}

   
   
    @Override
   /* public void paint(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paint(g);
    	 g.drawString("坦克数量:"+surplusNum, 410, 10);
         g.drawString("基地血量:"+surplusFrot, 410, 40);
         g.drawString("我方得分:"+score, 410, 70);
         
 		
     
    }*/
    
    public void run() {
		// TODO Auto-generated method stub
    	while(true) {
    	freshData();//刷新数据
    	}
	}
    	
    
}
