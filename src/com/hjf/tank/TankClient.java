package com.hjf.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.hjf.myEnum.Directory;
import com.hjf.myEnum.SoundType;
import com.hjf.myInterface.Window;

public class TankClient extends JFrame implements Window{

	/*private final int winWidth = 900;
	private final int winHeight = 700;
	private final int winlocationX = 50;
	private final int winlocationY = 20;*/
	
	private Image iBuffer; 
	private Graphics gBuffer;
	private Tank tank = null;
	
	
	/**
	 * 对窗口做一些初始化
	 */
	public void init(TankClient tClient){
		this.setLocation(winlocationX, winlocationY);
		this.setSize(winWidth, winHeight);
		this.getContentPane().setBackground(Color.GRAY);
		this.setResizable(false);
		this.setTitle("坦克大战");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(new TankDirectory(tank));
		this.tank.initTankImg();
		//new出一个Thread【线程】类，这个类需要 传一个实现了Runnable接口的对象
		//并且启动这个线程类
		new Thread(new TankThread()).start();
	}
	
	
	
	/**
	 * 一个app的唯一入口
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tClient = new TankClient();
		tClient.tank = new Tank(400, 550, Directory.up);
		tClient.init(tClient);
		new Thread(new TankSounds(SoundType.tankStart)).start();

	}
	
    class TankDirectory extends KeyAdapter{
    	Tank tk = null;
       public TankDirectory(Tank tk) {
    	   this.tk = tk;
       }
		@Override
		public void keyPressed(KeyEvent e) {
			tk.listenKeyEvent(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			tk.resetDirectionFlag(e);
		}
		
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
		tank.paint(g);
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
	      paint(gBuffer); 
	      gBuffer.dispose();
	      scr.drawImage(iBuffer,0,0,this); 
	         
	}  
   /**
    * 自己定义的内部类 实现了Runnable接口
    * @author hujinfu
    *
    */
	class TankThread implements Runnable {
		/**
		 * 实现了Runnable接口就必须实现run方法
		 */
		public void run() {
			while (true) {
				// 重新画
				repaint();
				tank.moveTankFowardDirectory(tank.tankDirectory);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
