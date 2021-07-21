package com.fly.www.impl;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	private BufferedImage image;
	// ���÷�����ĸ��࣬ͼƬ�����ߣ�����x,y
	private int width;
	private int height;
	private int x;
	private int y;
	/**
	 * �������߲�
	 */
	public abstract void step();
	/**
	 * �Է����������ٴ���
	 */
	public abstract boolean outOfBounds();
	/**
	 * ���ط�����ͼƬ
	 * 
	 * @return
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * ��ȡ������ͼƬ
	 * 
	 * @param image
	 */

	public BufferedImage getImage() {
		return image;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * ���÷������
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * ���÷������
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * ����x����
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * ��ȡx����
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * ��ȡy����
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * ����y����
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

}
