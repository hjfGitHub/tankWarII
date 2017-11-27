package com.hjf.tank;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;

import com.hjf.myEnum.SoundType;
import com.hjf.player.Player;

/**
 * 用线程来播放射击声音
 * @author hujinfu
 *
 */
public class TankSounds implements Runnable {

	private  Player player = null;
	private File tankMoveSounds = null;
	private File tankShootSounds = null;
	private File tankBgSound = null;
	private SoundType sound = null;
    public TankSounds(SoundType soundType) {
    	initTankSounds();
    	sound = soundType;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
        if (SoundType.start == sound) {
			player = new Player();
			player.play(tankMoveSounds);
			while (true) {
				player.play(tankBgSound);
			}
		} else if (SoundType.shoot == sound) {
			player = new Player();
			player.play(tankShootSounds);
		}		
	}
	
	/**
	 * 初始化坦克声音
	 */
	public void initTankSounds() {
		if (null == tankMoveSounds) {
			tankMoveSounds = new File("resource/sound/tankSounds/start.wav");
		}
		if (null == tankShootSounds) {
			tankShootSounds = new File("resource/sound/tankSounds/shoot.wav");
		}
		if (null == tankBgSound) {
			tankBgSound = new File("resource/sound/tankSounds/bgSound.wav");
		}
	}
	
}
