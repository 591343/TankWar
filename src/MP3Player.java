

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import javazoom.jl.player.Player;

 
//BGMȫ����
public class MP3Player implements Runnable{
	public static void playRadio(String path) {

    	try{BufferedInputStream buffer = new BufferedInputStream(

                new FileInputStream(path));

        new Player(buffer).play();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    public void run() {
           playRadio("D:/eclipse workspace/TankBattle/src/tankBattle.mp3");
    }

}

//�����Ч
class FirePlayer extends MP3Player{
	
	public void run() {
	MP3Player.playRadio("D:/eclipse workspace/TankBattle/src/shot.mp3");	
	}
}

//��ը��Ч
class BlastPlayer extends MP3Player{
	public void run() {
		MP3Player.playRadio("D:/eclipse workspace/TankBattle/src/boom.mp3");	
		}
}


