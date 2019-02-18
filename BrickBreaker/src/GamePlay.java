import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener,ActionListener {

	private boolean play=false;
	private int score=0;
	private int total_bricks=21;
	private Timer timer;
	private int delay=15;
	private int playerX=316;
	
	//Initial Ball Position
	private int ballX=120;
	private int ballY=350;
	private int speed=1;
	private int ballxdir=-2;
	private int ballydir=-4;
	private MapGenerator map;
	
	public GamePlay()
	{
		map=new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer=new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		
		
		//for Background
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//Border
		g.setColor(Color.YELLOW);
		//left border
		g.fillRect(0, 0, 3, 592);
		//upper border
		g.fillRect(0, 0, 692, 3);
		//right border
		g.fillRect(691, 0, 3, 592);
		
		// paddle
		g.setColor(Color.GREEN);
		g.fillRect(playerX, 550, 100, 8);
		
		map.draw((Graphics2D)g);
		//Score
		g.setColor(Color.WHITE);
	    g.setFont(new Font("serif",Font.BOLD,25));
	    g.drawString("Score :  "+score, 560, 30);
		
		// Ball
		g.setColor(Color.YELLOW);
		g.fillOval(ballX, ballY, 20, 20);
		
		if(total_bricks<=0)
		{
			g.setColor(Color.YELLOW);
		    g.setFont(new Font("serif",Font.BOLD,30));
		    g.drawString("Congratulation ! You win ... ", 190, 300);
		    g.setFont(new Font("serif",Font.BOLD,30));
		    g.drawString("Press Enter to Play Again ", 230, 350);

		    
		    speed++;
		    
		}
		if(ballY>570)
		{
			play=false;
			ballxdir=0;
		    ballydir=0;
		    g.setColor(Color.RED);
		    g.setFont(new Font("serif",Font.BOLD,30));
		    g.drawString("Game Over ", 190, 300);
		    g.setFont(new Font("serif",Font.BOLD,30));
		    g.drawString("Press Enter to restart", 230, 350);
		}
		
		g.dispose();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(playerX>=590)
			{
				playerX=590;
			}
			else
			{
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(playerX<=10)
				playerX=10;
			else
				moveLeft();
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(total_bricks!=0&&play)
			{
				play=false;
			}
			else
			{
		 
			play=true;
			playerX=310;
			ballX=120;
			ballY=350;
			ballxdir=-2;
			ballydir=-4;
		
			total_bricks=21;
			map=new MapGenerator(3,7);
			}
			repaint();
			
		}
		
	}

	private void moveLeft() {
		// TODO Auto-generated method stub
		play=true;
		playerX-=20;
		
		
	}

	private void moveRight() {
		// TODO Auto-generated method stub
		play=true;
		playerX+=20;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play)
		{
			if(new Rectangle(ballX,ballY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballydir=-ballydir;
			}
			
			A: for(int i=0;i<map.map.length;i++)
			{
				for(int j=0;j<map.map[0].length;j++)
				{
					if(map.map[i][j]>0)
					{
					int brickx=j*map.brickwidth+80;
					int bricky=i*map.brickheight+50;
					int brickwidth=map.brickwidth;
					int brickheight=map.brickheight;
					Rectangle rect=new Rectangle(brickx,bricky,brickwidth,brickheight);
					
					Rectangle ball=new Rectangle(ballX,ballY,20,20);
					if(ball.intersects(rect))
					{
						map.setBrickValue(0, i, j);
						total_bricks--;
						score+=5;
					
					if(ballX+19<=rect.x||ballX+1>=rect.x+rect.width)
						ballxdir=-ballxdir;
					else 
						ballydir=-ballydir;
					break A;
					}
					}
				}
			}
			ballX+=ballxdir;
			ballY+=ballydir;
			if(ballX<0)
				ballxdir=-ballxdir;
			if(ballY<0)
				ballydir=-ballydir;
			if(ballX>670)
			  ballxdir=-ballxdir;
		}
		repaint();
		
		
	}
	

}
