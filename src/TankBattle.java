import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author ChenXiao
 *����:̹�˴�ս1.0
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
 *10.̹��������ײǽ�ںʹ�ǽBUG�޸�
 *11.��ӱ�ը���ӵ��������Ϸ������Ч
 *12.�Ƿְ�(��¼����̹��ʣ���������ҷ��������������ҵ÷��ķ���)
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private  MyPanel p1=null;
	//private Image image 

	private Container container=getContentPane();
	TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		
		p1=new MyPanel();
		
		
	    setBounds(400, 250, 400, 300);
	    setVisible(true);
	    setResizable(false);
	    setLayout(new BorderLayout());
	    container.add(p1,BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //���ؿ�ʼͼƬ
		Graphics p=getGraphics();
    	p.drawImage(GameUtile.getImage("startgame .png"), 0, 0, this);
    	
    	
	    int res=JOptionPane.showConfirmDialog(null, "��ʼ��Ϸ?", "TankBattle",JOptionPane.YES_NO_OPTION);
        if(res==JOptionPane.YES_OPTION){ 
        
        	new Thread(new MP3Player()).start();//��Ϸ��������
        	  Thread t=new Thread(p1);
      		 t.start();//���и��߳�    //������ǡ���ִ����������
      		 
        }else{
            System.exit(0);    //�˳���Ϸ
           
        }
	    
	    addKeyListener(p1);//ע�����
	  
	   
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      new TankBattle();
	}
	
   

}
