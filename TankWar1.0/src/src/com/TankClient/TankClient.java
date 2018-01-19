package src.com.TankClient;

import java.awt.Color;

/**
 * ��0.5�����Ӽ����¼��������࣬Ϊ������Ӽ����¼�������,Tank�������������ƶ���
 * ������Ͳ,����Ctrl������һ���ڵ�.
 */
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{

		Tank t=new Tank(50,50,this);//���봫����
		Missile m=null;
		List<Missile> missiles=new ArrayList<Missile>();//��ǿ���������η�������ǿ���Ǿֲ���������Ա�������������5
		//Missile m=new Missile(50, 50,Tank.Direction.R);
		public static final int GAME_WIDTH=800;//Ϊ�����ع�(�޸�)��׼��,������Ҫ�ദ�޸ĵ�������Ϊ�������޸�һ������ദ�ı��������Ϊ������������һ���д��ͨ����public static final��
		public static final int GAME_HEIGHT=600;
		
		Image offScreenImage=null;
		
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.launchFrame();
		
}
	
	public void launchFrame() {
		//Frame frame=new Frame();//this ����frameͼ�ξ���ʾ�������ˣ�����Ϊʲô��
		this.setLocation(300,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN); 
		this.setVisible(true);
		
		this.addKeyListener(new KeyMonitor());//�������¼���������KeyMonitor�Ķ�����ӵ�������ȥ��Ϊ������Ӽ��̼�������
		
		RepaintThread rt=new RepaintThread();
		Thread t=new Thread(rt);
		t.start();
		//new Thread(new RepaintThread()).start();
		
		this.addWindowListener(new WindowAdapter() {//�����࣬��϶�.
			public void windowClosing(WindowEvent e) {
				System.out.println("window is closing!");
				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics g) {//paint �����ᱻ�Զ�����
		t.drawTank(g);
		g.drawString("count number shoted:"+missiles.size(), 50, 50);
			for(int i=0;i<missiles.size();i++) {
				Missile m=missiles.get(i);
				m.drawMissile(g);
			}
	
		
		//x+=5;
		//y+=5;//int x=50;int y=50,д�ڸ÷���������ô���С�ԭ����ִ���귽������Ȼx,yֵ�ı䣬�����ڴ汻�ͷţ�x,y������ˡ�д��ȫ�ֱ����򲻻���������⣬������������ڴ�ű��ͷš�
	}
	public void update(Graphics g) {//ʹ��˫������Ƶ������
		if(offScreenImage==null) {//���Ȼ���һ�ſհ�ͼƬ���õ���ͼƬ�Ļ��ʴ���paint()�������ڸ�ͼƬ������,�������ǰ̨��Ȼ����ͼƬһֱ��������
			offScreenImage=this.createImage(800,600);//�����Ϊ�գ�ͼƬ�����ٴ���һ��ͼƬ
			}
			
			Graphics gOffScreenImage=offScreenImage.getGraphics();//�õ��½�ͼƬ�Ļ���
			Color c=gOffScreenImage.getColor();//�õ�ǰ��ɫ
			gOffScreenImage.setColor(Color.GREEN);//����ͼƬǰ��ɫΪ�̣�����ͼƬ
			gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//���Ƹ�ͼƬ
			gOffScreenImage.setColor(c);//��ԭǰ��ɫ
			paint(gOffScreenImage);//�ѻ��ʴ���paint���������½���ͼƬ��������Ŀǰֻ�ǻ�����Tank	
			
			g.drawImage(offScreenImage, 0, 0, null);//������ͼƬ��������ǰ���ͼƬ
			
		
		
	}
	private class RepaintThread implements Runnable{

		@Override
		public void run() {
			while(true) {
				repaint();//����repaint�������� �������Զ�����paint������
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	private class KeyMonitor extends KeyAdapter{//�������̼������࣬ʹ��keyPressed()������ü������룬����Tank�ķ���
		public void keyPressed(KeyEvent e) {
			t.keyPushDown(e);
		}
		public void keyReleased(KeyEvent e) {
			t.keyRelease(e);
		}
	
	
	}
}