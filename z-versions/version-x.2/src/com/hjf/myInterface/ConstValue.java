package com.hjf.myInterface;

public interface ConstValue {

	//设置主界面的位置和大小
	final int winWidth = 900;
	final int winHeight = 700;
	final int winlocationX = 50;
	final int winlocationY = 20;
	
	//设置营房的位置和大小
	final int barrackX = (winWidth/8)*3;
	final int barrackY = winHeight - 155;
	final int barrackWidth = (winWidth/8)*2;
	final int barrackHeight = 150;
	
	//设置坦克的位置
	final int startTankX = winWidth/2 -30;
	final int startTankY = winHeight -120;
	
	//线程休眠时间
	final int threadSleepTime = 50;
	final int tankStartTime = 6000;
	
	//营房前墙的宽和高
	final int wallWidth = barrackWidth;
	final int wallHeight = 20;
	
	//坦克作战信息窗口
	final int tankInfoX = winlocationX + winWidth;
	final int tankInfoY = winlocationY;
	final int tankInfoWinWidth = 80;
	final int tankInfoWinHeight = 120;
}
