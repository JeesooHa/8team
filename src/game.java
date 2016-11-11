import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class game {
	
	public static void main(String[] ar){
		
		game_Frame fms = new game_Frame();
	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 

	int f_width;
	int f_height;
 
	int x, y; // position of plane
	
	//variable for keybord input
	boolean KeyUp = false; 
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeySpace = false; //missile

	Thread th; 
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image plane_img; 
	Image missile_img;
	
	//to save shot missile
	ArrayList Missile_List = new ArrayList();
	
	//for double buffering
	Image buffImage; 
	Graphics buffg; 
	
	Missile ms;	
	
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
		f_width = 800;
		f_height = 600;
		
		//load images
		plane_img = tk.getImage("images/plane_img.png"); 
		missile_img = tk.getImage("images/missile_img.png");
	
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
				MissileProcess();
				repaint(); 		//repaint plane using new position
				Thread.sleep(20); //delay time
			}
			
		}catch (Exception e){}
		
	}	
	
	public void MissileProcess(){ 
		if ( KeySpace == true ){ 
			ms = new Missile(x, y); //set missile position
			Missile_List.add(ms);   //add missile to list
		}
	}
	
	public void paint(Graphics g){		
		buffImage = createImage(f_width, f_height); //set double buffer size
		buffg = buffImage.getGraphics();
		update(g);
	}
	
	public void update(Graphics g){
		Draw_Char();
		Draw_Missile(); 
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}

	public void Draw_Char(){ 
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(plane_img, x, y, this);
	}
	
	public void Draw_Missile(){ 
		for (int i = 0 ; i < Missile_List.size(); ++i){
			
			//get missile position
			ms = (Missile) (Missile_List.get(i)); 
			
			//draw missile image to the current position
			buffg.drawImage(missile_img, ms.pos.x + 150, ms.pos.y + 30, this); 

			ms.move();	//move missile
	
			if ( ms.pos.x > f_width ){ 
				Missile_List.remove(i); 
			}
		}
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
			case KeyEvent.VK_SPACE : //missile
				KeySpace = true;
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
			case KeyEvent.VK_SPACE : //missile
				KeySpace = false;
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


class Missile{ 

	Point pos; //missile position variable
 
	Missile(int x, int y){ //get missile position
		pos = new Point(x, y); 
	}

	public void move(){ //move missile
		pos.x += 20; 
	}
}
