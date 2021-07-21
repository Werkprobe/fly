package com.fly.www.impl;

import com.fly.www.Enemy;

public class BossAirplane extends FlyingObject implements Enemy {

	public int getScore() {
		return 20;
	}

	@Override
	public void step() {
	}

	@Override
	public boolean outOfBounds() {
		return false;
	}

}
