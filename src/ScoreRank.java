import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JTextArea;



import javafx.scene.layout.Border;

public class ScoreRank extends JFrame{
    public JTextArea rankArea=new JTextArea(); //����չʾ���а�����
   
	public ScoreRank() {
		// TODO Auto-generated constructor stub 
		
	}
	
	public void init() {
		    setTitle("TankBattle Ranking");
		    setBounds(70,70,500,300);
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    rankArea.setEditable(false);//�����޸�
		    rankArea.setFont(new Font("����",Font.BOLD, 20));
		    rankArea.setForeground(Color.yellow);
		    rankArea.setBackground(Color.black);
		   
		    getContentPane().add(rankArea, BorderLayout.CENTER);
		    setVisible(true);
	}
	

	
	
	

}
