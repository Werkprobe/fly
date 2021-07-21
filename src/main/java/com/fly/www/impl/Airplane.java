package com.fly.www.impl;

import java.util.Random;

import com.fly.www.Enemy;

public class Airplane extends FlyingObject implements Enemy{
	Random ran=new Random();
	//set drop seed
	private int speed=2;
	public Airplane() {
		this.setImage(FlugGame.airplane);
		this.setWidth(getImage().getWidth());
		this.setHeight(getImage().getHeight());
		Random rand=new Random();
		this.setX(rand.nextInt(FlugGame.WIDTH-this.getWidth()));
		this.setY(-this.getHeight());
	}
	
	@Override
	public void step() {
		this.setY(this.getY()+speed);
		
	}
	@Override
	public boolean outOfBounds() {
		return this.getY()>=this.getHeight()+FlugGame.HEIGHT;
	}

	public int getScore() {
		return 5;//Airplane Point
	}
}
