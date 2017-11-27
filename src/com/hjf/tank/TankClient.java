package com.hjf.tank;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFrame;

import com.hjf.myEnum.Directory;
import com.hjf.myEnum.SoundType;
import com.hjf.player.Player;

public class TankClient extends JFrame{

	private final int speed = 3;
	private final int winWidth = 900;
	private final int winHeight = 700;
	private final int winlocationX = 50;
	private final int winlocationY = 20;
	private final static int initTankX = 400;
	private final static int initTankY = 550;
	private final int towKeyPressEventGapTime = 500;
	
	private Image iBuffer; 
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
	
	
	private Graphics gBuffer;
	public Directory tankDirectory;
	
	public boolean leftKey = false;
	public boolean rightKey = false;
	public boolean upKey = false;
	public boolean downKey = false;
	//这个类的两个属性x,y
	private int x;
	private int y;
	
	/**
	 * TankClient的构造方法，每个类都会有构造方法
	 * 可以显式的定义，也可以不用定义
	 * @param x
	 * @param y
	 */
	public TankClient(int x,int y,Directory directory){
		this.x = x;
		this.y = y;
		this.tankDirectory= directory;
	}
	
	/**
	 * 对窗口做一些初始化
	 */
	public void init(TankClient tClient){
		this.setLocation(winlocationX, winlocationY);
		//this.getContentPane().setLocation(winlocationX, winlocationY);
		this.setSize(winWidth, winHeight);
		this.getContentPane().setBackground(Color.GRAY);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
		initTankImg();
		this.addKeyListener(new TankDirectory());
		//new出一个Thread【线程】类，这个类需要 传一个实现了Runnable接口的对象
		//并且启动这个线程类
		new Thread(new TankThread(tClient)).start();
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
	 * 一个app的唯一入口
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TankClient tClient = new TankClient(initTankX,initTankY,Directory.up);
		tClient.init(tClient);
		new Thread(new TankSounds(SoundType.start)).start();
		//new Thread(new TankThread()).start();

	}
	/**
	 * 底层方法，我们没有必要显式的去调用，只要在方法里边
	 * 加入自己要画的东西（显示的东西就OK）
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//调用父类的方法
		super.paint(g);
		g.drawImage(tankImg, x, y, this);
		Bullet.paintBullets(g);
	}
	
	@Override
	public void update(Graphics scr)  
	{  
	    if(iBuffer==null)  
	    {  
	       iBuffer=createImage(this.getSize().width,this.getSize().height);  
	       gBuffer=iBuffer.getGraphics();  
	    }  
	      /* gBuffer.setColor(getBackground());  
	       gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height); */ 
	      paint(gBuffer); 
	      gBuffer.dispose();
	      scr.drawImage(iBuffer,0,0,this); 
	         
	}  
	class TankDirectory extends KeyAdapter{
		Long[] keyPressTime = new Long[]{0L,0L};
		int index = 0;
		@Override
		public void keyReleased(KeyEvent e) {
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
				//等待出发后才能开打
				if (false == justStart) {
					Bullet blt = new Bullet();
					blt.createBullet(x, y, tankDirectory, true);
					new Thread(new TankSounds(SoundType.shoot)).start();
				}
				break;
			default:
				break;
			}
			keyPressTime[index++%2] = System.currentTimeMillis();
			setTankDirectory(keyPressTime);
		}
		
	}
	
	/**
	 * 根据按的方向键来设置坦克方向
	 * @param keyPressTime
	 */
	private void setTankDirectory(Long[] keyPressTime) {
		//如果|time1 - time2| < 50ms  
		if (Math.abs((keyPressTime[0]-keyPressTime[1])) < towKeyPressEventGapTime) {
			if (this.tankDirectory == Directory.up && leftKey == true) {
				this.tankDirectory = Directory.leftUp;
				tankImg = tankLeftUpImg;
			}
			if (this.tankDirectory == Directory.up && rightKey == true) {
				this.tankDirectory = Directory.rightUp;
				tankImg = tankRightUpImg;
			}
			if (this.tankDirectory == Directory.down && leftKey == true) {
				this.tankDirectory = Directory.leftDown;
				tankImg = tankLeftDownImg;
			}
			if (this.tankDirectory == Directory.down && rightKey == true) {
				this.tankDirectory = Directory.rightDown;
				tankImg = tankRightDownImg;
			}
		}else {
			if (true == upKey) {
				this.tankDirectory = Directory.up;
				tankImg = this.tankUpImg;
			}
			if (true == downKey) {
				this.tankDirectory = Directory.down;
				tankImg = this.tankDownImg;
			}
			if (true == rightKey) {
				this.tankDirectory = Directory.right;
				tankImg = this.tankRightImg;
			}
			if (true == leftKey) {
				this.tankDirectory = Directory.left;
				tankImg = this.tankLeftImg;
			}

		}
		//重置所有标志变量
		upKey = downKey = leftKey = rightKey = false;
		
	}
	
   /**
    * 自己定义的内部类 实现了Runnable接口
    * @author hujinfu
    *
    */
   class TankThread implements Runnable{
       TankClient tkClient = null;
	   public TankThread(TankClient tkClient){
		   this.tkClient = tkClient;
	   }
	   private void moveTankFowardDirectory(Directory dir){
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
			checkPosition(x,y);
		}
	   
		/**
		 *  检测是否到了边界了
		 * @param x
		 * @param y
		 */
		private void checkPosition(int x, int y) {
			if (x <= 0) {
				tkClient.x = 0;
			}
			if (x >= tkClient.winWidth - 100) {
				tkClient.x = tkClient.winWidth - 100;
			}
			if (y >= tkClient.winHeight - 100) {
				tkClient.y = tkClient.winHeight - 100;
			}
			if (y <= 15) {
				tkClient.y = 15;
			}
			// System.out.println("x:"+tkClient.x+ " y: "+ tkClient.y);
		}

	/**
	  * 实现了Runnable接口就必须实现run方法
	  */
	public void run() {
	    //while（true） 表示死循环，
		while (true) {
			try {
				//模拟刚刚启动的时间
				if (justStart) {
					//线程休眠4000ms
					Thread.sleep(6000);
					justStart = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//重新画
			repaint();
			moveTankFowardDirectory(tankDirectory);
			//System.out.println(System.currentTimeMillis());
			//y +=speed;
			try {
				//线程休眠200ms
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	   
   }

}
