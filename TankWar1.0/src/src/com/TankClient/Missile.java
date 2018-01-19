package src.com.TankClient;

import java.awt.Color;
import java.awt.Graphics;

public class Missile {
	private int x;
	private int y;
	private TankClient tc;
	private boolean live=true;
	

	public static final int XSPEED=10;
	public static final int YSPEED=10;
	public static final int MISSILE_WIDTH=10;
	public static final int MISSILE_HEIGHT=10;
	
	
	Tank.Direction ptDir;//Ϊ�˻��Tank��Ͳ�ķ�����Ϊ�Լ��ķ���
	public Missile(int x,int y,Tank.Direction ptDir) {//Tank������Ͳ�����ӵ�����һ�£�
		this.x=x;
		this.y=y;
		this.ptDir=ptDir;
	}
	public Missile(int x,int y,Tank.Direction ptdir,TankClient tc) {
		this(x,y,ptdir);
		this.tc=tc;//
	}
	public void drawMissile(Graphics g) {
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, MISSILE_WIDTH, MISSILE_HEIGHT);
		g.setColor(c);
		move();//�ܵ���move�ķ�����move()���������Ͳ���˽�е���
	}
	private void move() {
		switch(ptDir) {
		case U:
			y-=YSPEED;
			break;
		case D:
			y+=YSPEED;
			break;
		case L:
			x-=XSPEED;
			break;
		case R:
			x+=XSPEED;
			break;
		case LU:
			x-=XSPEED;
			y-=YSPEED;
			break;
		case LD:
			x-=XSPEED;
			y+=YSPEED;
			break;
		case RU:
			x+=XSPEED;
			y-=YSPEED;
			break;
		case RD:
			x+=XSPEED;
			y+=YSPEED;
			break;
		
		}
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) {
			live=false;
			tc.missiles.remove(this);
		}
		
	}
	public boolean isLive() {// live��������Ϊ˽�У��ⲿ�����ͨ�����ʸ÷�������ӵ���״̬��
		return live;
	}
	
}
