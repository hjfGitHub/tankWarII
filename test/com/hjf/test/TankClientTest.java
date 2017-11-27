package com.hjf.test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.Toolkit;

import org.junit.Test;

import com.hjf.tank.TankClient;

public class TankClientTest {
    TankClient tc;
    Image tankLeftImage = null;
	@Test
	public void testInitTankImg() {
		tankLeftImage = Toolkit.getDefaultToolkit().getImage("F:/java/myeclipse/workspace/train-tankWar/"
				+ "resource/tankPicture/Left.png");
	    System.out.println(tankLeftImage.getWidth(null));
	}

}
