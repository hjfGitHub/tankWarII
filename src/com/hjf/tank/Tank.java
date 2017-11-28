package com.hjf.tank;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.hjf.myEnum.Directory;
import com.hjf.myEnum.SoundType;

public class Tank {
	private final int winWidth = 900;
	private final int winHeight = 700;
	private final int speed = 3;
	private final static int initTankX = 400;
	private final static int initTankY = 550;
	private final int towKeyPressEventGapTime = 500;
	
	private Image tankLeftImg;
	private Image tankRightImg;
	private Image tankUpImg;
	private Image tankDownImg;
	private Image tankLeftUpImg;
	private Image tankLeftDownImg;
	private Image tankRightUpImg;
	private Image tankRightDownImg;
	private Image tankImg = null;
	private boolean justStart = true;
	/*Long[] keyPressTime = null;
	int index = 0;*/
	public Directory tankDirectory;
	
	public boolean leftKey = false;
	public boolean rightKey = false;
	public boolean upKey = false;
	public boolean downKey = false;
	//这个类的两个属性x,y
	private int x;
	private int y;
	
	
	/**
	 * Tank的构造方法，每个类都会有构造方法
	 * 可以显式的定义，也可以不用定义
	 * @param x
	 * @param y
	 */
	public Tank(int x,int y,Directory directory){
		this.x = x;
		this.y = y;
		this.tankDirectory= directory;
	}
	/**
	 * 加载坦克图片
	 */
	public void initTankImg(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		tankLeftImg = toolkit.getImage("resource/tankPicture/left.png");
	    tankRightImg = toolkit.getImage("resource/tankPicture/right.png");
	    tankUpImg = toolkit.getImage("resource/tankPicture/up.png");
	    tankDownImg = toolkit.getImage("resource/tankPicture/down.png");
		tankLeftUpImg =toolkit.getImage("resource/tankPicture/leftUp.png");
		tankLeftDownImg = toolkit.getImage("resource/tankPicture/leftDown.png");
		tankRightUpImg = toolkit.getImage("resource/tankPicture/rightUp.png");
		tankRightDownImg = toolkit.getImage("resource/tankPicture/rightDown.png");
	    tankImg = tankUpImg;
	}
	
	/**
	 * 底层方法，我们没有必要显式的去调用，只要在方法里边
	 * 加入自己要画的东西（显示的东西就OK）
	 */
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//调用父类的方法
		//super.paint(g);
		g.drawImage(tankImg, x, y, null);
		//Bullet.paintBullets(g);
	}
	
	
	public void listenKeyEvent(KeyEvent e) {
		//keyPressTime= new Long[] { 0L, 0L };
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_UP:
			upKey = true;
			break;
		case KeyEvent.VK_DOWN:
			downKey = true;
			break;
		case KeyEvent.VK_LEFT:
			leftKey = true;
			break;
		case KeyEvent.VK_RIGHT:
			rightKey = true;
			break;
		case KeyEvent.VK_SPACE:
			// 等待出发后才能开打
			if (false == justStart) {
				Bullet blt = new Bullet();
				blt.createBullet(x, y, tankDirectory, true);
				new Thread(new TankSounds(SoundType.tankShoot)).start();
			}
			break;
		default:
			break;
		}
		//keyPressTime[index++ % 2] = System.currentTimeMillis();
		setTankDirectory();
	}

	/**
	 * 根据按的方向键来设置坦克方向
	 * @param keyPressTime
	 */
	private void setTankDirectory() {
		
		if (leftKey && !upKey && !downKey && !rightKey) {
			this.tankDirectory = Directory.left;
			tankImg = tankLeftImg;
		}else if (!leftKey && upKey && !downKey && !rightKey) {
			this.tankDirectory = Directory.up;
			tankImg = tankUpImg;
		}else if (!leftKey && !upKey && downKey && !rightKey) {
			this.tankDirectory = Directory.down;
			tankImg = tankDownImg;
		}else if (!leftKey && !upKey && !downKey && rightKey) {
			this.tankDirectory = Directory.right;
			tankImg = tankRightImg;
		}else if (leftKey && upKey && !downKey && !rightKey) {
			this.tankDirectory = Directory.leftUp;
			tankImg = tankLeftUpImg;
		}else if (leftKey && !upKey && downKey && !rightKey) {
			this.tankDirectory = Directory.leftDown;
			tankImg = tankLeftDownImg;
		}else if (!leftKey && upKey && !downKey && rightKey) {
			this.tankDirectory = Directory.rightUp;
			tankImg = tankRightUpImg;
		}else if (!leftKey && !upKey && downKey && rightKey) {
			this.tankDirectory = Directory.rightDown;
			tankImg = tankRightDownImg;
		}
	}
	
	
	public void moveTankFowardDirectory(Directory dir) {
		//模拟刚刚启动的过程
		if (justStart) {
		   justStart = false;
		}
		switch (dir) {
		case leftDown:
			x -= speed;
			y += speed;
			break;
		case left:
			x -= speed;
			break;
		case down:
			y += speed;
			break;
		case up:
			y -= speed;
			break;
		case right:
			x += speed;
			break;
		case rightDown:
			x += speed;
			y += speed;
			break;
		case leftUp:
			x -= speed;
			y -= speed;
			break;
		case rightUp:
			x += speed;
			y -= speed;
			break;
		}
		checkPosition(x, y);
	}

	/**
	 * 检测是否到了边界了
	 * 
	 * @param x
	 * @param y
	 */
	private void checkPosition(int x, int y) {
		if (x <= 0) {
			this.x = 0;
		}
		if (x >= this.winWidth - 100) {
			this.x = this.winWidth - 100;
		}
		if (y >= this.winHeight - 100) {
			this.y = this.winHeight - 100;
		}
		if (y <= 15) {
			this.y = 15;
		}
		// System.out.println("x:"+tkClient.x+ " y: "+ tkClient.y);
	}

	public static int getInittankx() {
		return initTankX;
	}

	public static int getInittanky() {
		return initTankY;
	}
	public boolean isJustStart() {
		return justStart;
	}
	public void setJustStart(boolean justStart) {
		this.justStart = justStart;
	}
	public void resetDirectionFlag(KeyEvent e) {
		//重置所有标志变量
	   upKey = downKey = leftKey = rightKey = false;
	}

	
}
