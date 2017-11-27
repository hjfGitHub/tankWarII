package com.hjf.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.hjf.myEnum.Directory;
import com.hjf.myInterface.Window;

public class Bullet implements Window {
	
	private final int width = 10;
	private final int height = 10;
	public boolean isGood;
	public int positionX;
	public int positionY;
	private final int speed = 8;
	private boolean justCreated = true;
	public Directory directory;
	
	//存放所有子弹
	public static final List<Bullet> bullets =  Collections.synchronizedList(new ArrayList<Bullet>());
	
	
	//打出子弹
	public void createBullet(int x,int y,Directory dir,boolean isGood){
		setDirectory(dir);
		setPositionX(x);
		setPositionY(y);
		setGood(isGood);
		bullets.add(this);
		
	}
	//显示所有的子弹
	public static void paintBullets(Graphics g){
		Color color = g.getColor();
		for(int i = 0; i < bullets.size(); i ++ ) {
			Bullet blt = bullets.get(i);
			Color color2 = blt.isGood() == true ? Color.RED:Color.BLACK;
			g.setColor(color2);
			if (checkBullet(blt)){
				//刚刚打出去时重新调整坐标
				if (blt.isGood == true && blt.justCreated == true) {
					reposition(blt);
				}
				g.fillOval(blt.getPositionX(), blt.getPositionY(), blt.width, blt.height);
			}
			moveBullet(blt);
		}
		g.setColor(color);
	}
	
	private static void reposition(Bullet blt) {
		// TODO Auto-generated method stub
		switch (blt.directory) {
		case leftDown:
            blt.setPositionX( blt.getPositionX() + 10);
            blt.setPositionY(blt.getPositionY() + 100);
			break;
		case left:
			blt.setPositionX( blt.getPositionX() - 10);
	        blt.setPositionY(blt.getPositionY() + 22);
			break;
		case down:
			blt.setPositionX( blt.getPositionX() + 24);
	        blt.setPositionY(blt.getPositionY() + 110);
			break;
		case up:
			blt.setPositionX( blt.getPositionX() + 20);
	        blt.setPositionY(blt.getPositionY() - 10);
			break;
		case right:
			blt.setPositionX( blt.getPositionX() + 110);
	        blt.setPositionY(blt.getPositionY() + 23);
			break;
		case rightDown:
			blt.setPositionX( blt.getPositionX() + 100);
	        blt.setPositionY(blt.getPositionY() + 98);
			break;
		case leftUp:
			blt.setPositionX( blt.getPositionX() + 10);
	        blt.setPositionY(blt.getPositionY() + 12);
			break;
		case rightUp:
			blt.setPositionX( blt.getPositionX() + 100);
	        blt.setPositionY(blt.getPositionY() + 8);
			break;
		}
		blt.justCreated = false;
	}
	/**
	 * 移动子弹类
	 * @param blt
	 */
	private static void moveBullet(Bullet blt) {
		// TODO Auto-generated method stub
		switch (blt.getDirectory()) {
		case leftDown:
            blt.positionX -= blt.speed;
            blt.positionY += blt.speed;
			break;
		case left:
			blt.positionX -= blt.speed;
			break;
		case down:
			blt.positionY += blt.speed;
			break;
		case up:
			blt.positionY -= blt.speed;
			break;
		case right:
            blt.positionX += blt.speed;
			break;
		case rightDown:
			blt.positionX += blt.speed;
	        blt.positionY += blt.speed;
			break;
		case leftUp:
			blt.positionX -= blt.speed;
	        blt.positionY -= blt.speed;
			break;
		case rightUp:
			blt.positionX += blt.speed;
	        blt.positionY -= blt.speed;
			break;
		}
	}
	//超出边界则移除
	public static boolean checkBullet(Bullet blt){
		if (blt.getPositionX() < 0 ||blt.getPositionX() > blt.winWidth
				|| blt.getPositionY() < 0 || blt.getPositionY() > blt.winHeight) {
			bullets.remove(blt);
			return false;
		}
		//System.out.println("还有效子弹个数： "+bullets.size());
		return true;
	}
	
	public boolean isGood() {
		return isGood;
	}
	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	public int getSpeed() {
		return speed;
	}

}
