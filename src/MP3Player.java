

import java.io.BufferedInputStream;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import javazoom.jl.player.Player;

 

public class MP3Player implements Runnable{

    public void run() {

    	try{BufferedInputStream buffer = new BufferedInputStream(

                new FileInputStream("D:/eclipse workspace/TankBattle/src/tankBattle.mp3"));

        new Player(buffer).play();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
