package src.com.TankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	
	private static final int XSPEED=5;
	private static final int YSPEED=5;
	private static final int TANK_WIDTH=30;
	private static final int TANK_HEIGHT=30;
	enum Direction{U,D,L,R,LU,RU,LD,RD,STOP};
	private Direction dir=Direction.STOP;
	private Direction ptDir=Direction.R;
	private boolean u=false,d=false,l=false,r=false;
	private int x;
	private int y;
	TankClient tc;
	
	public Tank(int x,int y) {
		this.x=x;
		this.y=y;
		
	}
	public Tank(int x,int y,TankClient tc) {//构造方法的重载
		this(x,y);//可以在一个构造构造方法中使用this关键字调用本类中的其他构造方法
		this.tc=tc;//初始化tc对象,tc是个大管家，管着Tank,炮弹，血块等，是一种设计模式，叫做mediator|'mi:dieita|调停者，调解人，中介者模式。
	}
	public void drawTank(Graphics g) {
		Color c=g.getColor();//用画笔对象得到前景色。
		g.setColor(Color.RED);//用画笔设置Tank的颜色为红色
		g.fillOval(x, y, TANK_WIDTH,TANK_HEIGHT);//画出红色Tank
		g.setColor(c);//还原前景色
		switch(ptDir) {
		case U:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x+TANK_WIDTH/2, y);
			break;
		case D:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x+TANK_WIDTH/2, y+TANK_HEIGHT);
			break;
		case L:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x, y+TANK_HEIGHT/2);
			break;
		case R:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x+TANK_WIDTH, y+TANK_HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x, y);
			break;
		case LD:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x, y+TANK_HEIGHT);
			break;
		case RU:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x+TANK_WIDTH, y);
			break;
		case RD:
			g.drawLine(x+TANK_WIDTH/2, y+TANK_HEIGHT/2, x+TANK_WIDTH, y+TANK_HEIGHT);
			break;
		case STOP:
			break;
		}
		
		
		move();
	}
	public void keyPushDown(KeyEvent e) {//有一个问题是当按下键之后,u,d,l,r就会变成永远变为true,所以按下两次后再按其他键无效，会一直朝一个方向走。设置抬起键盘让u,d,r,l再变为false;
		int key=e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			u=true;
			break;
		case KeyEvent.VK_DOWN:
			d=true;
			break;
		case KeyEvent.VK_LEFT:
			l=true;
			break;
		case KeyEvent.VK_RIGHT:
			r=true;
			break;
			}
		
		this.locateDirection();
	}
	public void keyRelease(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP:
			u=false;
			break;
		case KeyEvent.VK_DOWN:
			d=false;
			break;
		case KeyEvent.VK_LEFT:
			l=false;
			break;
		case KeyEvent.VK_RIGHT:
			r=false;
			break;
		case KeyEvent.VK_CONTROL://把释放Ctrl键作为触发条件是因为，不想发射子弹太密集。没有写回收，会耗真实内存
			tc.missiles.add(fire());//fire()方法的返回值为missile类型的对象，添加到missiles列表中
			//tc.m=fire();tc.m持有对方的引用，tc对象持有Missile对象的引用m,m可看做tc的成员变量
			break;//不加break会出现典型的case穿透，即为不加break语句会一直向后执行，执行后面的语句。
		}
		this.locateDirection();
			
	}
		
	
	private void locateDirection() {
		if(u==true&&d==false&&l==false&&r==false) dir=Direction.U;
		else if(u==false&&d==true&&l==false&&r==false) dir=Direction.D;
		else if(u==false&&d==false&&l==true&&r==false) dir=Direction.L;
		else if(u==false&&d==false&&l==false&&r==true) dir=Direction.R;
		else if(u==true&&d==false&&l==true&&r==false) dir=Direction.LU;	
		else if(u==false&&d==true&&l==true&&r==false) dir=Direction.LD;
		else if(u==true&&d==false&&l==false&&r==true) dir=Direction.RU;
		else if(u==false&&d==true&&l==false&&r==true) dir=Direction.RD;
		if(u==false&&d==false&&l==false&&r==false) dir=Direction.STOP;
		//move();方法放在这个位置，现在只能按一下键盘，坐标位置改变一次。因为不是在draw()中调用的，repaint()每隔50ms,调用一次paint()方法,move()放在后面会被调用多次，按一次键盘坐标可以连续改变。
		//this.ptDir=dir;
		
	}
	private void move() {
		switch(dir) {
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
		case STOP:
			break;
		}
		if(x<0) x=0;
		if(y<25) y=25;
		if(x>TankClient.GAME_WIDTH-TANK_WIDTH) x=TankClient.GAME_WIDTH-TANK_WIDTH;
		if(y>TankClient.GAME_HEIGHT-TANK_HEIGHT) y=TankClient.GAME_HEIGHT-TANK_HEIGHT;
		
		if(dir!=Direction.STOP) {
			this.ptDir=this.dir;//写成this.ptDir=dir,ptDir=dir也行。获得Tank位置是为了画出炮筒
		}
		
	}
	public Missile fire() {
		
		
		int missileX=x+TANK_WIDTH/2-Missile.MISSILE_WIDTH/2;
		int missileY=y+TANK_HEIGHT/2-Missile.MISSILE_HEIGHT/2;//可以直接使用类名调用它的静态成员变量和和静态方法。
		Missile m=new Missile(missileX,missileY,ptDir,this.tc);//传入Tank炮筒对象的位置，方向，TankClient对象的引用tc,mediator 中介者模式。
		//tc.missiles.add(m);//直接写在这里也可以，是更好的方法，这样KeyEvent_CONTROL中直接调用fire()方法就行了。
		return m;
		
	}
	
}
