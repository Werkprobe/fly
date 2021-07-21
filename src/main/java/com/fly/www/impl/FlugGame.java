package com.fly.www.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.fly.www.Award;
import com.fly.www.Enemy;

public class FlugGame extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;// Fenster breit
	public static final int HEIGHT = 654;// Fenster Hoch
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gemover;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int PAUSE=2;
	public static final int GAME_OVER=3;
	private int state=START;
	private Hero hero = new Hero();
	private int source=0;
	private FlyingObject[] flyings;// Airplane array und nimmt die Bienen an。
	private Bullet[] bullets = {};

	public FlugGame() {
		flyings = new FlyingObject[1];
		flyings[0] = new Airplane();
		bullets =new Bullet[0];
		// bullets[0]=new
		// Bullet(hero.getX()+hero.getWidth()/2-bullet.getWidth()/2,hero.getY());
	}

	static {//init 
		try {
			background = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"background.png"));
			start = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"start.png"));
			pause = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"pause.png"));
			gemover = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"gameover.png"));
			airplane = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"airplane.png"));
			bee = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"bee.png"));
			bullet = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"bullet.png"));
			hero0 = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"hero0.png"));
			hero1 = ImageIO.read(FlugGame.class.getClassLoader().getResource("img"+File.separator+"hero1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * erstellt Airplane Object
	 */
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type < 5) {// erstellt Biene(0-5） 随机数为0时，生成小蜜蜂
			return new Bee();
		} else {// 生成敌机
			return new Airplane();
		}

	}

	private int flyEnterdIndex = 0;

	/**
	 *erscheinen Airplane 敌机入场
	 */
	public void enterAction() {//pro 10 Millisekunde funktioniert ein mal. 10毫秒走一下 
		//  erstellt ein Airplane 生成敌机对象
		flyEnterdIndex++;
		if (flyEnterdIndex % 40 == 0) {// pro 400 Millisekunde erstellt ein Airplane .400毫秒走一次下面代码 
			FlyingObject airplane = this.nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = airplane;
		}
	}

	/**
	 * set Fliegenzeug Action
	 * 设置飞行物的走动,英雄机 和敌机,子弹
	 */
	public void stepAction() {
		hero.step();
		for (FlyingObject fly : flyings) {
			fly.step();
		}
		for (Bullet bullet : bullets) {
			bullet.step();
		}
	}

	@SuppressWarnings("unused")
	private int bulletsEnterdIndex = 0;

	private void shootAction() {
		// erstellt eine Patrone 生成子弹
		bulletsEnterdIndex++;
		if (flyEnterdIndex % 30 == 0) {//pro 300 Millisekunde erstellt eine Patrone . 每300毫秒生成一个子弹
			Bullet[] bulletObj = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bulletObj.length);
			System.arraycopy(bulletObj, 0, bullets, bullets.length - bulletObj.length, bulletObj.length);
		}
	}
	/**
	 * löschst Fliegenzeug der überschrien Grenze
	 *删除越界的飞行物
	 */
	private void outOfBunds() {
		int index=0;
		//löschst Airplane der überschritten Grenze 删除越界的敌人
		FlyingObject[] flyingArryLife=new FlyingObject[flyings.length];
		for (FlyingObject fly : flyings) {
			   if(!fly.outOfBounds()){
				   flyingArryLife[index]=fly;
				   index++;
			   }
		}
		flyings=Arrays.copyOf(flyingArryLife, index);
		//löschst Patrone der überschrien Grenze 删除越界的子弹
		index=0;
		Bullet[] bulletLife=new Bullet[bullets.length];
		for (Bullet bullet : bullets) {
			if(!bullet.outOfBounds()){
				bulletLife[index]=bullet;
				index++;
			}	
		}
		bullets=Arrays.copyOf(bulletLife, index);
	}
	/**
	 * die Patronen stoßen Airplane
	 * 子弹打敌机
	 */
	private void bangAction() {
			Bullet[] bulletTemp=new Bullet[bullets.length];
			int index=0;
			for (Bullet bullet : bullets) {
				  boolean bool=bang(bullet);
				  if(!bool){
					  bulletTemp[index++]=bullet;
				  }
			}
			//System.out.println(bulletTemp.length);
			bullets=Arrays.copyOf(bulletTemp, index);
			
	}
	/**
	 * ob die Patrone stößt eine Airplane
	 * @param bullet
	 * @return
	 */
	public boolean bang(Bullet bullet){
		boolean bool=false;
		 int index=-1;
		for (int i=0;i<flyings.length;i++) {
			FlyingObject fly =flyings[i];
			if(bullet.shootBy(fly)){//gestoßen 撞上了
				index=i;//schreibt Zahl auf  记录撞上的敌机 
				bool=true;
				break;
			}
		}
		if(index!=-1){
			//gewinnen unterschiede auszeichnung 判断敌人类型，获得不同奖励
			if(flyings[index] instanceof Enemy){
				int score=((Enemy) flyings[index]).getScore();
				source+=score;
			}else if(flyings[index] instanceof Award){
				Award award=(Award) flyings[index];
				int type=award.getType();
				switch(type){
				case Award.Double_FIRE:
					//Hero feuer 英雄机加火力
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					//Hero Leben 英雄机加命
					hero.addLife();
					break;
				}
			}
			FlyingObject flyTemp=flyings[index];
			flyings[index]=flyings[flyings.length-1];
			flyings[flyings.length-1]=flyTemp;
			flyings=Arrays.copyOf(flyings, flyings.length-1);
		}
		return bool;
	}
	/**
	 * ob GameOver
	 * 判断游戏是否结束
	 */
	private boolean isGameOver(){
		int index=-1;
		for(FlyingObject flyAirplane:flyings){
			index++;
			if(hero.hit(flyAirplane)){
				hero.subtractLife();//Hero reduziert Leben 英雄机减命
				hero.getDoubleFireCleanZero();//empty Feuer清空火力
				//将被撞敌人与数姐最后一个元素交换
				flyings[index]=flyings[flyings.length-1];
				flyings[flyings.length-1]=flyAirplane;
				flyings=Arrays.copyOf(flyings, flyings.length-1);
				break;
			}
		}
		return hero.getLife()<=0;//ob Leben des Heros英雄机就否还有命
	}
	/**
	 * ob check Game Over
	 * 检查游戏是否结束
	 */
	private void checkGameOverAction() {
		if(isGameOver()){//游戏结束
			state=GAME_OVER;
		}
		
	}
	/**
	 * Game Eingang
	 */
	private void action() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(state){//根据点击切换状诚
					case START:
					 state=RUNNING;
					 break;
					case GAME_OVER:
						source=0;
						hero=new Hero();
						flyings=new FlyingObject[0];
						bullets=new Bullet[0];
						state=START;
						break;
					}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE){
					state=RUNNING;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING){
					state=PAUSE;
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING){
					int x=e.getX();
					int y=e.getY();
					hero.moveTo(x, y);
				}
			}
		};
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		Timer timer = new Timer();
		int intervel = 10;// 时间间隔，以毫秒计算
		// schedule方法参数，要执行的任务，第一次开始时门，每次时间间隔
		timer.schedule(new TimerTask() {
			// 重写run方法，启动创建敌机线程
			@Override
			public void run() {// 10毫秒启动线程，创建一次敌机
				if(state==RUNNING){
					enterAction();//baut Airplane创建敌机
					shootAction();//baut Patrone 通过子弹
					stepAction();//bewegen Fliegenzeug 设置飞行物移动
					outOfBunds();//löschen Airplane der überschritten Grenze  删除没有用的飞行物
					bangAction();//Airplane wird von den Patronen gestoßen  子弹打到敌机
					checkGameOverAction();//ob Hero ohne Leben检查项雄机是否没命
				}
				repaint();//noch Malen 重画画布
			}


		}, intervel, intervel);

	}


	/**
	 *  malen Object 画飞行物
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(FlugGame.background, 0, 0, null);
		// g.setColor(Color.BLACK);
		// g.fillRect(0, 0, WIDTH, HEIGHT);
		paintHero(g);//画飞机
		paintFlyingObject(g);//画敌人
		paintBullets(g);//画子弹
		paintScoreAndLife(g);//画飞和生命
		paintState(g);//画状态
	}

	/**
	 * Malen Hero
	 * 画英雄英雄机
	 * 
	 * @param g
	 */
	private void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}

	/**
	 * Malen Airplane
	 * 画敌机
	 * 
	 * @param g
	 */
	private void paintFlyingObject(Graphics g) {
		for (FlyingObject fly : flyings) {
			g.drawImage(fly.getImage(), fly.getX(), fly.getY(), null);

		}

	}

	/**
	 * Malen spiel status
	 * 画游戏状态
	 */
	private void paintState(Graphics g){
		switch (state) {//画启动图
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gemover,0,0,null);
			break;
		}
		
	}
	/**
	 * Male Bullet
	 * 画子弹
	 * 
	 * @param g
	 */
	public void paintBullets(Graphics g) {
		for (Bullet bullet : bullets) {
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), null);
		}

	}
	/**
	 * 画分数
	 * @param args
	 */
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xff0000));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD,20));
		g.drawString("SCORE:"+source,10, 25);
		g.drawString("Life:"+hero.getLife(),10, 45);
		g.drawString("Fire:"+hero.getDoubleFire(),10,65);
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");// 窗建窗口框架
		FlugGame game = new FlugGame();// 创建面板
		frame.add(game);// 将面板加到窗口上
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);// 总在最前
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置默认关闭
		frame.setLocationRelativeTo(null);
		game.action();
		frame.setVisible(true);
	}
}
