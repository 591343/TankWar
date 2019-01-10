import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import javafx.scene.layout.Border;

public class ScoreRank extends JFrame{
    public JTextArea rankArea=new JTextArea(); //用于展示排行榜数据
	public ScoreRank() {
		// TODO Auto-generated constructor stub 
	}
	
	public void init() {
		    setTitle("TankBattle Ranking");
		    setBounds(70,70,400,300);
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		   
		    getContentPane().add(rankArea, BorderLayout.CENTER);
		    setVisible(true);
	}

}
