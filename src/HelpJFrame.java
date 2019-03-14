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
	    setTitle("游戏操作");
	    setBounds(70,70,500,300);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    getContentPane().setBackground(Color.white);
	    jTextArea=new JTextArea(); jTextArea.setEditable(false);
	    jTextArea.setWrapStyleWord(true);
	    jTextArea.setAutoscrolls(true);
	    jTextArea.setFont(new Font("宋体", Font.BOLD+Font.ITALIC, 19));
	    jTextArea.setText("玩家可通过W A S D键来使我方坦克进行上下左右移动,"+"\n"+"单按J键射出子弹，一直按J键，持续射出子弹");
	   
	    //label=new JLabel();
	    //label.setFont(new Font("宋体", Font.BOLD+Font.ITALIC, 10));
	    getContentPane().add(jTextArea, BorderLayout.NORTH);
	    setVisible(true);
  }
}
