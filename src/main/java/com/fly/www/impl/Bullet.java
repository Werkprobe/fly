package com.fly.www.impl;


/**
 * �ӵ��Ƿ�����
 * @author likun Ren
 *
 */
public class Bullet extends FlyingObject {
	private int speed=2;///y�����߲�����
	/**���췽��:x:�ӵ�x,y: �ӵ� ��Y**/
	public Bullet(int x, int y) {
		this.setImage(FlugGame.bullet);
		this.setWidth(getImage().getWidth());
		this.setHeight(getImage().getHeight());
		this.setX(x);
		this.setY(y);
	}
	@Override
	public void step() {
		this.setY(this.getY()-speed);
	}
	@Override
	public boolean outOfBounds() {
		return this.getY()<=-this.getHeight();
	}
	/**
	 * �ӵ��������
	 * @param obj
	 * @return
	 */
	public boolean shootBy(FlyingObject obj){
		boolean bool=false;
		if(this.getX()>=obj.getX() && this.getX()<=obj.getX()+obj.getWidth()
			&&
		this.getY()>=obj.getY() && this.getY()<=obj.getY()+obj.getHeight()){
			bool=true;
		}
		return bool;
	}
}
