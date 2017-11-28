package com.hjf.tank;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.lang.model.type.IntersectionType;
import javax.swing.JFrame;

import com.hjf.myEnum.Directory;
import com.hjf.myInterface.ConstValue;

/**
 * everything will be show in TankClient
 * @author hujinfu
 *
 */
public class TankClient extends JFrame implements ConstValue{
	
	private Image iBuffer; 
	private Graphics gBuffer;
	private Tank tank = null;
	private Image wallImg =null;
	
	
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
		this.addKeyListener(new PressKeyListener(tank));
		this.wallImg = Toolkit.getDefaultToolkit().getImage("resource/tankPicture/wall.png");
		new Thread(new PaintThread()).start();
	}
	
	
	
	/**
	 * 一个app的唯一入口
	 * @param args
	 */
	public static void main(String[] args) {
		TankClient tClient = new TankClient();
		tClient.tank = new Tank(startTankX, startTankY, Directory.up);
		tClient.init(tClient);
		tClient.tank.init(tClient.tank);
		
	}
	
	/**
	 * 监听按键事件
	 * @author hujinfu
	 *
	 */
    class PressKeyListener extends KeyAdapter{
    	Tank tk = null;
       public PressKeyListener(Tank tk) {
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
		super.paint(g);
		paintBarrack(g);
		tank.paint(g);
		Bullet.paintBullets(g);
		paintWalls(g);
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
	class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(threadSleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 画出一个矩形深灰色营房
	 */
	private void paintBarrack(Graphics g){
		//拿到 Graphics g 
		Color color = g.getColor();
		//g 画出深灰色矩形框框
		g.setColor(Color.PINK);
		g.drawRect(barrackX,barrackY,barrackWidth,barrackHeight);
		//g.drawRect();
		g.setColor(color);
	}
	
	/**
	 * 画出营房前方的墙
	 */
	private void paintWalls(Graphics g){
		if (this.wallImg != null) {
			g.drawImage(this.wallImg, barrackX, barrackY, wallWidth, wallHeight, null);
		}
	}
	

}
