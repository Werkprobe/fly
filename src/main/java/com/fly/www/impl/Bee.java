package com.fly.www.impl;

import java.util.Random;

import com.fly.www.Award;

public class Bee extends FlyingObject implements Award {
	private int xspeed=1;//X speed
	private int yspeed=2;//y speed
	private int awrdType;//gewinnen type 
	public Bee() {
		this.setImage(FlugGame.bee);
		this.setWidth(getImage().getWidth());
		this.setHeight(getImage().getHeight());
		Random rand=new Random();
		this.setX(rand.nextInt(FlugGame.WIDTH-this.getWidth()));
		this.setY(-this.getHeight());
		this.awrdType=rand.nextInt(2);//0��1������������������������
	}
	public int getType() {
		return awrdType;
	}
	@Override
	public void step() {
		int x=this.getX();
		x+=xspeed;
		if(x>=FlugGame.WIDTH-this.getWidth()){
			xspeed=-1;
		}else if(x<=0){
			xspeed=1;
		}
		this.setX(x);
		this.setY(this.getY()+yspeed);
	}
	@Override
	public boolean outOfBounds() {
		return this.getY()>=this.getHeight()+FlugGame.HEIGHT;
		
	}

}
