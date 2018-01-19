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
	
	
	Tank.Direction ptDir;//为了获得Tank炮筒的方向，作为自己的方向。
	public Missile(int x,int y,Tank.Direction ptDir) {//Tank方向，炮筒方向，子弹方向一致，
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
		move();//能调用move的方法，move()方法的类型不是私有的吗
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
	public boolean isLive() {// live变量声明为私有，外部对象可通过访问该方法获得子弹的状态，
		return live;
	}
	
}
