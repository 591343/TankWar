import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author LENOVO
 *����:̹�˴�ս2.0
 *Ŀǰ��ʵ�ֵĹ���
 *1.����̹��
 *2.̹�˵��ƶ�(W,S,A,D�ֱ�������������ƶ�)
 *3.�����з�̹��
 *4.�ӵ��ķ���(��J������)
 *5.ʵ���ӵ���������Ļ���ӵ������ɿ��ƣ�+����Ŀ��+����Ч��
 *6.ʹ���˿��Թ��������ƶ�
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
		t.start();//���и��߳�
		
	    setSize(400, 300);
	    setVisible(true);
	    setLayout(new BorderLayout());
	    container.add(p1,BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    addKeyListener(p1);//ע�����
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      new TankBattle();
	}

}
