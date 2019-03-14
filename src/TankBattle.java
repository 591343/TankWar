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
 *15.�����������ͨ��ʱ�䵽���ݿ���
 *16.��ͨ��ʱ�䰴��С��������
 
 */
@SuppressWarnings("serial")
public class TankBattle extends JFrame{

	private  MyPanel p1=null;
	private DataPanel p2=null;
	//�������ݿ�
	private Conndatum  conndatum;
	public static long startTime;
	
	//��ʼ��Ϸ
	private JButton startGame;
	//�鿴���а�
	private JButton checkRank;
	//��Ϸ����
	private JButton helpGame;
	
	//�˳���Ϸ
	private JButton exitGame;
	private javax.swing.JLabel  tankText;
	
	

	private Container container=getContentPane();
	TankBattle() {
		// TODO Auto-generated constructor stub
	   // setLocation(1000, 1000);
		setTitle("̹�˴�ս1.0");
		//setLayout(new );
		
		setLayout(null);
		setBounds(400, 250,600, 300);
		
	   
	    setVisible(true);
	    setResizable(false);
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    tankText=new javax.swing.JLabel("̹�˴�ս1.0");
	    tankText.setFont(new Font("����",Font.BOLD+Font.ITALIC,35));
	    
	    tankText.setBounds(180,10,230,80);
	    startGame=new JButton("��ʼ��Ϸ");
	    startGame.setBounds(230, 90, 100, 30);
	    checkRank=new JButton("�鿴���а�");
	    checkRank.setBounds(230, 125, 100,30);
	    helpGame=new JButton("��Ϸ����");
	    helpGame.setBounds(230, 160, 100, 30);
	    exitGame=new JButton("�˳���Ϸ");
	    exitGame.setBounds(230, 195, 100, 30);
	 
        add(tankText);
	    add(startGame);
	    add(checkRank);
    	add(helpGame);
    	add(exitGame);
    	//�Ӽ�����
    	//��ʼ��Ϸ
    	startGame.addActionListener(new ActionListener() {
			
			//@Override
		public void actionPerformed(ActionEvent e) {
			//	TODO Auto-generated method stub
				DataPanel.name=JOptionPane.showInputDialog("���´���");
				if(DataPanel.name!=null) {
	          	    //�ر����а񴰿�
	        		//conndatum.shutRank();
					remove(tankText);
					remove(startGame);
					remove(checkRank);
					remove(helpGame);
					remove(exitGame);
					setLayout(new BorderLayout());
	          	  new Thread(new MP3Player()).start();//��Ϸ��������
	           	  //���ؿ�ʼͼƬ
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
      	  	
	           	  
	           	  
	           	  //��ȡ��Ϸ��ʼʱ��
	           	  startTime=System.currentTimeMillis();
	           	  addKeyListener(p1);//ע�����  
	           	  
				}
	        
	        	
		}	
			
		});
   
        //�鿴���а�
        checkRank.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 conndatum=new Conndatum();
	        	 conndatum.showRank();
			}
		});
        
        //��Ϸ����
        helpGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HelpJFrame();
			}
		});
        
        //�˳���Ϸ
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


