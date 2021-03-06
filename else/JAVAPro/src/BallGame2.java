/**
 * 桌球游戏2
 */

import java.awt.*;
import javax.swing.*;

public class BallGame2 extends JFrame{
	
	// 加载图片
	Image ball = Toolkit.getDefaultToolkit().getImage("image/ball.png");
	Image desk = Toolkit.getDefaultToolkit().getImage("image/desk.jpg");
	
	double x = 100;		// 小球的横坐标
	double y = 100;		// 小球的纵坐标
	double degree = 3.14/3;		// 弧度，此处是60度
	
	
	// 绘制窗口的方法
	public void paint(Graphics g){
		System.out.println("窗口被画了一次");
		g.drawImage(desk, 0, 0, null);
		g.drawImage(ball, (int)x, (int)y, null);
		
		x = x + 20 * Math.cos(degree);
		y = y + 20 * Math.sin(degree);
		
		if(y>500-40-40 || y<40+40){
			degree = -degree;
		}
		
		if(x<40 || x>856-40-30){
			degree = 3.14-degree;
		}

	}
	
	// 窗口加载
	void launchFrame(){
		setSize(856, 500);
		setLocation(50, 50);
		setVisible(true);
		
		// 重画窗口
		while(true){
			repaint();
			try{
				Thread.sleep(40);		// 40ms，大约1s绘制25次
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		System.out.println("Happy Game！");
		BallGame2 game = new BallGame2();
		game.launchFrame();
	}

}
