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
 *����:̹�˴�ս1.0\
 *Ŀǰ��ʵ�ֵĹ���
 *1.����̹��
 *2.̹�˵��ƶ�(W,S,A,D�ֱ�������������ƶ�)
 *3.�����з�̹��
 *4.�ӵ��ķ���(��J������)
 *5.ʵ���ӵ���������Ļ���ӵ������ɿ��ƣ�+����Ŀ��+����Ч��
 *6.ʹ���˿��Թ��������ƶ�
 *7.̹���ص�BUG���޸�
 *8.ʵ�ֵ�ͼ,�����ӵ���̹����ײ���
 *9.��������
 *10.������ֵз�̹��
 *11.��ӱ�ը���ӵ��������Ϸ������Ч
 *12.�Ƿְ�(��¼����̹��ʣ���������ҷ��������������ҵ÷��ķ���)  Ŀǰ����һ��BUG
 *13.������Ѫ��Ϊ0ʱ������Ϸʧ�ܵ�����̹�������������Ϸʤ��
 *14.��ؿ������¿�ʼ��Ϸ
 
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private  MyPanel p1=null;
	private DataPanel p2=null;
	
    static  String name=null;//�û�����
	//private Image image 

	private Container container=getContentPane();
	TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		setTitle("̹�˴�ս1.0");
		setLayout(new BorderLayout());
		
		
		setBounds(400, 250,600, 300);
		
	   
	    setVisible(true);
	    setResizable(false);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    
	    Graphics p=getGraphics();
    	p.drawImage(GameUtile.getImage("startfgame.png"), 0, 0, this);
    	
        int res=JOptionPane.showConfirmDialog(null, "��ʼ��Ϸ?", "TankBattle",JOptionPane.YES_NO_OPTION);
        new Conndatum().showRank();//չʾ���а�
        name=JOptionPane.showInputDialog("���´���");
  	  
        if(res==JOptionPane.YES_OPTION){ 
        	
        	 new Thread(new MP3Player()).start();//��Ϸ��������
        	  //���ؿ�ʼͼƬ
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
            System.exit(0);    //�˳���Ϸ
           
        }
	    
	    addKeyListener(p1);//ע�����
	  
	   
	}

   public static void main(String[] args) {
	new TankBattle();
}
   
}


