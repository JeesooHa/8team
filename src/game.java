import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class game {
	
	public static void main(String[] ar){
		
		game_Frame fms = new game_Frame();
	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 

	int f_width = 800;
	int f_height = 600;
 
	int x, y; // position of plane
	
	//variable for keybord input
	boolean KeyUp = false; 
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;

	Thread th; 
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image plane_img = tk.getImage("images/plane_img.png"); //image path
	
	//for double buffering
	Image buffImage; 
	Graphics buffg; 
	
	game_Frame(){	//conductor

		init();
		start();
  
		setTitle("Shooting Game");
		setSize(f_width, f_height);
		
		Dimension screen = tk.getScreenSize();

		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
		
	}
	
	
	public void init(){	//initial position
		x = 100; 
		y = 100;
	}
	
	
	public void start(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addKeyListener(this); //keyboard event	
		th = new Thread(this);  // make thread
		th.start();  // thread start

	}

	public void run(){ 

		try{ 		
			while(true){ 
				KeyProcess(); //get the keyboard value to update position
				repaint(); 		//repaint plane using new position
				Thread.sleep(20); //delay time
			}
			
		}catch (Exception e){}
		
	}	

	public void paint(Graphics g){		
		buffImage = createImage(f_width, f_height); //set double buffer size
		buffg = buffImage.getGraphics();
		update(g);
	}
	
	public void update(Graphics g){
		Draw_Char();
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}

	public void Draw_Char(){ 
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(plane_img, x, y, this);
	}

	public void keyPressed(KeyEvent e){	//keyboard push event

		switch(e.getKeyCode()){
			case KeyEvent.VK_UP :
				KeyUp = true;
				break;
			case KeyEvent.VK_DOWN :
				KeyDown = true;
				break;
			case KeyEvent.VK_LEFT :
				KeyLeft = true;
				break;
			case KeyEvent.VK_RIGHT :
				KeyRight = true;
				break;
		}
	}
	
	public void keyReleased(KeyEvent e){	//keyboard released event

		switch(e.getKeyCode()){
			case KeyEvent.VK_UP :
				KeyUp = false;
				break;
			case KeyEvent.VK_DOWN :
				KeyDown = false;
				break;
			case KeyEvent.VK_LEFT :
				KeyLeft = false;
				break;
			case KeyEvent.VK_RIGHT :
				KeyRight = false;
				break;
		}
	}
	
	public void keyTyped(KeyEvent e){}	//dealing key event

	public void KeyProcess(){	//plane's move scale

		if(KeyUp == true) y -= 5;
		if(KeyDown == true) y += 5;
		if(KeyLeft == true) x -= 5;
		if(KeyRight == true) x += 5;
		
	}
}