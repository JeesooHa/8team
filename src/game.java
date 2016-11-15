import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.awt.image.*;

public class game {	
	public static void main(String[] ar){		
		game_Frame fms = new game_Frame();	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 

	int f_width;
	int f_height;
 
	int x, y; // position of plane
	
	//background
	int bx = 0;
	
	//variable for keybord input
	boolean KeyUp = false; 
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeySpace = false; //missile
	
	int cnt;	//enemy made loop
	
	//speed setting
	int player_Speed;
	int missile_Speed; 
	int fire_Speed; 
	int enemy_Speed; 
	
	//plane state  
	//0 : normal, 1 : missile shoot, 2 : crashed
	int player_Status = 0; 

	int game_Score;
	int player_Hitpoint; 

	Thread th; 
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	//for animation
	Image plane_img;
	Image background_img; 
	Image explo_img; 
	Image missile_img;
	Image enemy_img;
	Image enemy_missile_img;
	
	//to save shot missile
	ArrayList Missile_List = new ArrayList();
	ArrayList Enemy_List = new ArrayList();	//multiple enemy
	ArrayList Explosion_List = new ArrayList();

	//for double buffering
	Image buffImage; 
	Graphics buffg; 
	
	Missile ms;	
	Enemy en;
	Explosion ex;
	
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
		f_width = 1200;
		f_height = 600;
		
		//load images
		plane_img = new ImageIcon("images/plane_img.png").getImage(); 
		missile_img = new ImageIcon("images/missile_img.png").getImage();
		enemy_img = new ImageIcon("images/enemy2.png").getImage();	
		background_img = new ImageIcon("images/background1.jpg").getImage();
		explo_img = new ImageIcon("images/enemy_explosion.png").getImage();
		enemy_missile_img = new ImageIcon("images/enemy_shot.png").getImage();
		
		//setting
		game_Score = 0;	//initialize game score
		player_Hitpoint = 3;	
		  
		player_Speed = 5; 
		missile_Speed = 11; 
		fire_Speed = 10; 
		enemy_Speed = 3;

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
				EnemyProcess();
				MissileProcess();
				ExplosionProcess();
				repaint(); 		//repaint plane using new position
				Thread.sleep(20); //delay time
				cnt++;
			}			
		}catch (Exception e){}		
	}	
	
	public void MissileProcess(){ 

		if ( KeySpace == true ){ //shooting
			player_Status = 1;
			
			if( ( cnt % fire_Speed ) == 0){			
				ms = new Missile(x + 155, y + 32,missile_Speed,0); //set missile position
				Missile_List.add(ms);   //add missile to list
			}		
			
		}
		
		for ( int i = 0 ; i < Missile_List.size() ; ++i){
			ms = (Missile) Missile_List.get(i);
			ms.move();
			
			//end of main frame
			if ( ms.x > f_width - 20||ms.x<0||ms.y<0||ms.y>f_height){
				Missile_List.remove(i);
			}
			
			//enemy missile shot
			if (Crash(x, y, ms.x, ms.y, plane_img, missile_img) && ms.who == 1 ) {
				player_Hitpoint --;
				ex = new Explosion(x + plane_img.getWidth(null) / 2, y + plane_img.getHeight(null) / 2, 1);
				Explosion_List.add(ex);
				Missile_List.remove(i);
			}
				
			for (int j = 0 ; j < Enemy_List.size(); ++ j){
				en = (Enemy) Enemy_List.get(j);
				if (Crash(ms.x, ms.y, en.x, en.y, missile_img, enemy_img)&&ms.who==0){
					Missile_List.remove(i);
					Enemy_List.remove(j);
					
					game_Score += 10; //get score
					
					//explision effect
					ex = new Explosion(en.x + enemy_img.getWidth(null) / 2, en.y + enemy_img.getHeight(null) / 2 , 0);				
					Explosion_List.add(ex); 
				}
			}			
		}
	}
	
	
	public void EnemyProcess(){

		for (int i = 0 ; i < Enemy_List.size() ; ++i ){ 
			en = (Enemy)(Enemy_List.get(i)); 
	
			en.move(); //move enemy
			if(en.x < -200){
				Enemy_List.remove(i); 
			}
			
			//enemy shoot
			if ( cnt % 50 == 0){
				ms = new Missile (en.x, en.y + 10, missile_Speed, 1);
				Missile_List.add(ms);			
			}
			
			//crashed with enemy
			if(Crash(x, y, en.x, en.y, plane_img, enemy_img)){

				player_Hitpoint --; 
				Enemy_List.remove(i); 
				game_Score += 10; 

				ex = new Explosion(en.x + enemy_img.getWidth(null) / 2, en.y + enemy_img.getHeight(null) / 2, 0 );
				Explosion_List.add(ex); 

				ex = new Explosion(x+plane_img.getWidth(null) / 2, y+plane_img.getHeight(null)/ 2, 1 );
				Explosion_List.add(ex);
			}
		}
		
		if ( cnt % 200 == 0 ){ //make enemy
			en = new Enemy(f_width + 100, 100, enemy_Speed);
			Enemy_List.add(en); 
		
			en = new Enemy(f_width + 100, 300, enemy_Speed);
			Enemy_List.add(en);

			en = new Enemy(f_width + 100, 500, enemy_Speed);
			Enemy_List.add(en);
		}

	}
	
	
	 public void ExplosionProcess(){		  
		  for (int i = 0 ;  i < Explosion_List.size(); ++i){
			  ex = (Explosion) Explosion_List.get(i);
			  ex.effect();
		  }
	}

	
	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2){

		boolean check = false;
		
		if ( Math.abs( ( x1 + img1.getWidth(null) / 2 )  - ( x2 + img2.getWidth(null) / 2 ))  < ( img2.getWidth(null) / 2 + img1.getWidth(null) / 2 )
			&& Math.abs( ( y1 + img1.getHeight(null) / 2 )  - ( y2 + img2.getHeight(null) / 2 ))  < ( img2.getHeight(null)/2 + img1.getHeight(null)/2 ) ){
			check = true;
		}
		else{
			check = false;
		}
		return check; 
	}

	public void paint(Graphics g){		
		buffImage = createImage(f_width, f_height); //set double buffer size
		buffg = buffImage.getGraphics();
		update(g);
	}
	
	public void update(Graphics g){
		Draw_Background(); 
		Draw_Player();
		Draw_Enemy();
		Draw_Missile(); 
		Draw_Explosion();
		Draw_StatusText();
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
		
	public void Draw_Background(){
		buffg.clearRect(0, 0, f_width, f_height);
		if ( bx > - 600){		
			bx -= 1;
		}else {			
			bx = 0;
		}
		buffg.drawImage(background_img, bx, 0, this);

	}

	public void Draw_Player(){ 
		buffg.drawImage(plane_img, x, y, this);
	}
	
	public void Draw_Missile(){ 
		for (int i = 0 ; i < Missile_List.size(); ++i){			
			//get missile position
			ms = (Missile) (Missile_List.get(i)); 			
			//draw missile image to the current position
			
			if(ms.who == 0) //plane
				buffg.drawImage(missile_img, ms.x, ms.y, this); 
			
			if(ms.who == 1) //enemy
				buffg.drawImage(enemy_missile_img, ms.x, ms.y, this); 
		}
	}

	
	public void Draw_Enemy(){ 
		for (int i = 0 ; i < Enemy_List.size() ; ++i ){
			en = (Enemy)(Enemy_List.get(i));
			buffg.drawImage(enemy_img, en.x, en.y, this);	
		}
	}
	
	
	public void Draw_Explosion(){
		for (int i = 0 ; i < Explosion_List.size() ; ++i ){
			ex = (Explosion)Explosion_List.get(i);
			if ( ex.ex_cnt < 20 ){
				buffg.drawImage( explo_img, ex.x - explo_img.getWidth(null) / 2, ex.y - explo_img.getHeight(null) / 2, this);
			}
			else{
				Explosion_List.remove(i);
				ex.ex_cnt = 0;
			}
		
		}
	}
	
	public void Draw_StatusText(){
		Color white = new Color(255, 255, 255);
		buffg.setColor(white);
		buffg.setFont(new Font("Defualt", Font.BOLD, 20));
		buffg.drawString("SCORE : " + game_Score, 1000, 70);
		buffg.drawString("HitPoint : " + player_Hitpoint, 1000, 90);
		buffg.drawString("Missile Count : " + Missile_List.size(), 1000, 110);
		buffg.drawString("Enemy Count : " + Enemy_List.size(), 1000, 130);
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

		if(KeyUp == true) {
			if( y > 20 ) y -= 5;
			player_Status = 0;
		}

		if(KeyDown == true) {
			if( y+ plane_img.getHeight(null) < f_height ) y += 5;		
			player_Status = 0;
		}

		if(KeyLeft == true) {
			if ( x > 0 ) x -= 5;
			player_Status = 0;
		}

		if(KeyRight == true) {
			if ( x + plane_img.getWidth(null) < f_width ) x += 5;
			player_Status = 0;
		}
	}
}


class Missile{ 

	//missile position variable
	int x,y,speed;
	int who;	// 0: plane, 1: enemy
	
	Missile(int x, int y, int speed,int who){ //get missile position
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.who = who;
	}

	public void move(){ //move missile
		if(this.who == 0)
			x += speed; 
		else
			x -= speed;

	}
}

class Enemy{ 
	int x,y,speed;
	
	Enemy(int x, int y, int speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move(){
		x -= speed;
	}
}

class Explosion{ 

	int x,y;
	int ex_cnt;
	int damage; 

	Explosion(int x, int y, int damage){
		this.x = x;
		this.y = y;
		this.damage = damage;
	}
	
	public void effect(){
		ex_cnt ++;
	}		
}
	