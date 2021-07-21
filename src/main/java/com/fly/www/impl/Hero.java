package com.fly.www.impl;

import java.awt.image.BufferedImage;

/**
 * 英雄 机：是飞行物
 * @author kevin
 *
 */
public class Hero extends FlyingObject {
		private int life; //命
		private int doubleFire; //火力值
		private BufferedImage[] images;
		private int index;//协助图片切换
		public Hero() {
			this.setImage(FlugGame.hero0);
			this.setWidth(getImage().getWidth());
			this.setHeight(getImage().getHeight());
			this.setX(FlugGame.WIDTH/2-this.getWidth()/2);
			this.setY(FlugGame.HEIGHT-this.getHeight()-60);
			this.life=3;//初始化生命3
			this.doubleFire=0;//火力值初始化为0,单发
			this.images=new BufferedImage[]{FlugGame.hero0,FlugGame.hero1};
			index=0;
		}
		public void addDoubleFire(){
			doubleFire+=20;
		}
		public int getDoubleFire(){
			return doubleFire;
		}
		public void getDoubleFireCleanZero(){
			doubleFire=0; 
		}
		public void addLife(){
			life++;
		}
		public int getLife(){
			return life;
		}
		public void subtractLife(){
			life--;
		}
		@SuppressWarnings("unused")
		private int bulletsEnterdIndex=0;
		/**创建子弹**/
		public Bullet[] shoot(){
			int bulletX=this.getWidth()/4;
			int bulletY=20;
			Bullet[] bulletAry;
			if(doubleFire>0){
				bulletAry=new Bullet[2];
				bulletAry[0]=new Bullet(this.getX()+bulletX-FlugGame.bullet.getWidth()/2,this.getY()+FlugGame.bullet.getHeight()/2-bulletY);
				bulletAry[1]=new Bullet(this.getX()+3*bulletX-FlugGame.bullet.getWidth()/2,this.getY()+FlugGame.bullet.getHeight()/2-bulletY);
				doubleFire-=2;
				}else{
				bulletAry=new Bullet[1];
				bulletAry[0]=new Bullet(this.getX()+2*bulletX-FlugGame.bullet.getWidth()/2,this.getY()-bulletY);
			}
			return bulletAry;
		}
		@Override
		public void step() {
			this.setImage(images[index++/10%images.length]);
		}
		@Override
		public boolean outOfBounds() {
			return false;
		}
		/**
		 * 英雄机随鼠标移动
		 * @param x
		 * @param y
		 */
		public void moveTo(int x,int y){
			this.setX(x-this.getWidth()/2);
			this.setY(y-this.getHeight()/2);
		} 
		/**
		 * Airplanen stroßen den Hero
		 * @param flyAirplane
		 * @return
		 */
		public boolean hit(FlyingObject flyAirplane){
			int x1=flyAirplane.getX()-this.getWidth()/2;
			int y1=flyAirplane.getY()-this.getHeight()/2;
			int x2=flyAirplane.getX()+flyAirplane.getWidth()+this.getWidth()/2;
			int y2=flyAirplane.getY()+flyAirplane.getHeight()+this.getHeight()/2;
			int x=this.getX()+this.getWidth()/2;
			int y=this.getY()+this.getHeight()/2;
			if(x>=x1 && x<=x+x2
					&& y>=y1 && y<=y2){
					return true;
			}
			return false;
		}
}
