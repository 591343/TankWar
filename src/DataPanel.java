import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * 1.��¼�з�̹��ʣ������
 * 2.��¼�ҷ�����ʣ��Ѫ��
 * 3.��¼�ҷ�����(����һ���з�̹��20��)
 * 
 */

public class DataPanel extends JPanel implements Runnable {
     static int surplusNum;//�з�ʣ��̹������
     static int surplusFrot;//�ҷ�ĸ��ʣ��Ѫ��
     static int score;//�ҷ��÷�
    
    static JLabel num=null;
    static JLabel blood=null;
    static JLabel myScore=null;
    
    static {
    	
    }
    
	public DataPanel() {
		// TODO Auto-generated constructor stub
		this.setLayout(null);//ʹ�þ��Բ���
		this.setBounds(400, 0, 200, 300);
		num=new JLabel("̹������:"+surplusNum);
		blood=new JLabel("����Ѫ��:"+surplusFrot);
		myScore=new JLabel("�ҷ��÷�:"+score);
	    
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
	
	//ˢ������
	private  void freshData() {
		num.setText("̹������:"+surplusNum);
 		blood.setText("����Ѫ��:"+surplusFrot);
 		myScore.setText("�ҷ��÷�:"+score);
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
    	 g.drawString("̹������:"+surplusNum, 410, 10);
         g.drawString("����Ѫ��:"+surplusFrot, 410, 40);
         g.drawString("�ҷ��÷�:"+score, 410, 70);
         
 		
     
    }*/
    
    public void run() {
		// TODO Auto-generated method stub
    	while(true) {
    	freshData();//ˢ������
    	}
	}
    	
    
}
