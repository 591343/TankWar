import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HelpJFrame extends JFrame {
	private JLabel label;
	private JTextArea jTextArea;
  public HelpJFrame(){
	    setTitle("��Ϸ����");
	    setBounds(70,70,500,300);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    getContentPane().setBackground(Color.white);
	    jTextArea=new JTextArea(); jTextArea.setEditable(false);
	    jTextArea.setWrapStyleWord(true);
	    jTextArea.setAutoscrolls(true);
	    jTextArea.setFont(new Font("����", Font.BOLD+Font.ITALIC, 19));
	    jTextArea.setText("��ҿ�ͨ��W A S D����ʹ�ҷ�̹�˽������������ƶ�,"+"\n"+"����J������ӵ���һֱ��J������������ӵ�");
	   
	    //label=new JLabel();
	    //label.setFont(new Font("����", Font.BOLD+Font.ITALIC, 10));
	    getContentPane().add(jTextArea, BorderLayout.NORTH);
	    setVisible(true);
  }
}
