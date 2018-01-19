package src.com.TankClient;

import java.awt.Color;

/**
 * 与0.5相比添加键盘事件监听器类，为窗体添加键盘事件监听器,Tank可以上下左右移动。
 * 画出炮筒,按下Ctrl键发出一发炮弹.
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

		Tank t=new Tank(50,50,this);//必须传参数
		Missile m=null;
		List<Missile> missiles=new ArrayList<Missile>();//类强调访问修饰符，方法强调是局部变量，成员变量，类变量。5
		//Missile m=new Missile(50, 50,Tank.Direction.R);
		public static final int GAME_WIDTH=800;//为代码重构(修改)做准备,将来需要多处修改的量定义为常量，修改一处引起多处改变的量定义为常量，常量名一般大写，通常是public static final的
		public static final int GAME_HEIGHT=600;
		
		Image offScreenImage=null;
		
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.launchFrame();
		
}
	
	public void launchFrame() {
		//Frame frame=new Frame();//this 换成frame图形就显示不出来了，这是为什么？
		this.setLocation(300,100);
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setResizable(false);
		this.setTitle("TankWar");
		this.setBackground(Color.GREEN); 
		this.setVisible(true);
		
		this.addKeyListener(new KeyMonitor());//将键盘事件监听器类KeyMonitor的对象添加到窗体上去，为窗体添加键盘监听器。
		
		RepaintThread rt=new RepaintThread();
		Thread t=new Thread(rt);
		t.start();
		//new Thread(new RepaintThread()).start();
		
		this.addWindowListener(new WindowAdapter() {//匿名类，类较短.
			public void windowClosing(WindowEvent e) {
				System.out.println("window is closing!");
				System.exit(0);
			}
		});
	}
	
	public void paint(Graphics g) {//paint 方法会被自动调用
		t.drawTank(g);
		g.drawString("count number shoted:"+missiles.size(), 50, 50);
			for(int i=0;i<missiles.size();i++) {
				Missile m=missiles.get(i);
				m.drawMissile(g);
			}
	
		
		//x+=5;
		//y+=5;//int x=50;int y=50,写在该方法里面怎么不行。原因是执行完方法后虽然x,y值改变，但是内存被释放，x,y被清空了。写成全局变量则不会有这个问题，整个程序结束内存才被释放。
	}
	public void update(Graphics g) {//使用双缓存解决频闪现象
		if(offScreenImage==null) {//首先画画一张空白图片，得到该图片的画笔传给paint()方法，在该图片上作画,画完后传入前台。然后解决图片一直拉伸问题
			offScreenImage=this.createImage(800,600);//，如果为空，图片后面再创建一张图片
			}
			
			Graphics gOffScreenImage=offScreenImage.getGraphics();//得到新建图片的画笔
			Color c=gOffScreenImage.getColor();//得到前景色
			gOffScreenImage.setColor(Color.GREEN);//设置图片前景色为绿，整张图片
			gOffScreenImage.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//绘制该图片
			gOffScreenImage.setColor(c);//还原前景色
			paint(gOffScreenImage);//把画笔传给paint方法，在新建的图片上作画，目前只是画出了Tank	
			
			g.drawImage(offScreenImage, 0, 0, null);//把这张图片整个覆盖前面的图片
			
		
		
	}
	private class RepaintThread implements Runnable{

		@Override
		public void run() {
			while(true) {
				repaint();//调用repaint方法，该 方法会自动调用paint方法。
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	private class KeyMonitor extends KeyAdapter{//创建键盘监听器类，使用keyPressed()方法获得键盘输入，控制Tank的方向
		public void keyPressed(KeyEvent e) {
			t.keyPushDown(e);
		}
		public void keyReleased(KeyEvent e) {
			t.keyRelease(e);
		}
	
	
	}
}