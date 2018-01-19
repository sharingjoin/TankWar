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
	public Tank(int x,int y,TankClient tc) {//���췽��������
		this(x,y);//������һ�����칹�췽����ʹ��this�ؼ��ֵ��ñ����е��������췽��
		this.tc=tc;//��ʼ��tc����,tc�Ǹ���ܼң�����Tank,�ڵ���Ѫ��ȣ���һ�����ģʽ������mediator|'mi:dieita|��ͣ�ߣ������ˣ��н���ģʽ��
	}
	public void drawTank(Graphics g) {
		Color c=g.getColor();//�û��ʶ���õ�ǰ��ɫ��
		g.setColor(Color.RED);//�û�������Tank����ɫΪ��ɫ
		g.fillOval(x, y, TANK_WIDTH,TANK_HEIGHT);//������ɫTank
		g.setColor(c);//��ԭǰ��ɫ
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
	public void keyPushDown(KeyEvent e) {//��һ�������ǵ����¼�֮��,u,d,l,r�ͻ�����Զ��Ϊtrue,���԰������κ��ٰ���������Ч����һֱ��һ�������ߡ�����̧�������u,d,r,l�ٱ�Ϊfalse;
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
		case KeyEvent.VK_CONTROL://���ͷ�Ctrl����Ϊ������������Ϊ�����뷢���ӵ�̫�ܼ���û��д���գ������ʵ�ڴ�
			tc.missiles.add(fire());//fire()�����ķ���ֵΪmissile���͵Ķ�����ӵ�missiles�б���
			//tc.m=fire();tc.m���жԷ������ã�tc�������Missile���������m,m�ɿ���tc�ĳ�Ա����
			break;//����break����ֵ��͵�case��͸����Ϊ����break����һֱ���ִ�У�ִ�к������䡣
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
		//move();�����������λ�ã�����ֻ�ܰ�һ�¼��̣�����λ�øı�һ�Ρ���Ϊ������draw()�е��õģ�repaint()ÿ��50ms,����һ��paint()����,move()���ں���ᱻ���ö�Σ���һ�μ���������������ı䡣
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
			this.ptDir=this.dir;//д��this.ptDir=dir,ptDir=dirҲ�С����Tankλ����Ϊ�˻�����Ͳ
		}
		
	}
	public Missile fire() {
		
		
		int missileX=x+TANK_WIDTH/2-Missile.MISSILE_WIDTH/2;
		int missileY=y+TANK_HEIGHT/2-Missile.MISSILE_HEIGHT/2;//����ֱ��ʹ�������������ľ�̬��Ա�����ͺ;�̬������
		Missile m=new Missile(missileX,missileY,ptDir,this.tc);//����Tank��Ͳ�����λ�ã�����TankClient���������tc,mediator �н���ģʽ��
		//tc.missiles.add(m);//ֱ��д������Ҳ���ԣ��Ǹ��õķ���������KeyEvent_CONTROL��ֱ�ӵ���fire()���������ˡ�
		return m;
		
	}
	
}
