import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class game {	
	public static void main(String[] ar){		
		game_Frame fms = new game_Frame();	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 
	private static boolean all_stop = false;
	private static boolean stage_clear = false;
	private static int Q_available = 0;
	
	int f_width, f_height;	//frame size
	int x, y;	//position of plane
	int bx = 0;	//background move
	
	//variable for keyboard input
	boolean KeyUp = false; 
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeySpace = false; //missile
	boolean KeyQ = false; //ultimate skill
	boolean KeyT = false; //test key. score add
	
	int cnt;	//enemy made loop
	
	int stage_status = 1;
	boolean stage_draw = true;
	
	boolean boss_stage = false;//false : normal stage, true : boss stage
	
	double m_angle = 0;
	double d_xy;
	double dx;
	//speed setting
	int player_Speed;
	int missile_Speed;
	int enemy_missile_Speed; 
	int fire_Speed; 
	int enemy_Speed; 
	int stage_Score;
	int total_Score;
	int player_Hitpoint; 
	int Tangle = 0;
	int boss_Hitpoint;
	int boss_Status = 0;	//0: not appeared, 1: appeared, 2: destroyed
	int save_cnt = 0;
	Thread th; 
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	//for animation
	Image plane_img;
	Image background_img; 
	Image background_img2; 
	Image explo_img; 
	Image missile_img;
	Image enemy_img1;
	Image enemy_img2;
	Image enemy_img3;
	Image enemy_missile_img;
	Image gameover_img;
	Image boss1;
	Image stage_clear_img;
	Image stage1;
	Image stage2;
	Image stage3;
	Image start;
	Image boss_missile_img;
	
	
	//to save shot missile
	ArrayList<Missile> Missile_List = new ArrayList();
	ArrayList<Enemy> Enemy_List = new ArrayList();	
	ArrayList<Explosion> Explosion_List = new ArrayList();

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
		enemy_img1 = new ImageIcon("images/enemy1.png").getImage();
		enemy_img2 = new ImageIcon("images/enemy2.png").getImage();	
		enemy_img3 = new ImageIcon("images/enemy3.png").getImage();	
		background_img = new ImageIcon("images/background1.jpg").getImage();
		background_img2 = new ImageIcon("images/background2.png").getImage();
		explo_img = new ImageIcon("images/enemy_explosion.png").getImage();
		enemy_missile_img = new ImageIcon("images/enemy_shot.png").getImage();
		gameover_img = new ImageIcon("images/game over.png").getImage();
		boss1 = new ImageIcon("images/boss2.png").getImage();
		stage_clear_img = new ImageIcon("images/stage_clear.png").getImage();	
		stage1 = new ImageIcon("images/stage01.png").getImage();
		stage2 = new ImageIcon("images/stage02.png").getImage();
		stage3 = new ImageIcon("images/stage03.png").getImage();
		start = new ImageIcon("images/start.png").getImage();
		boss_missile_img = new ImageIcon("images/boss_laser.png").getImage();
		
		//setting
		stage_Score = 0;
		total_Score = 0;	//initialize game score
		player_Hitpoint = 10;	
		boss_Hitpoint = 3;
		  
		player_Speed = 5; 
		missile_Speed = 7;
		enemy_missile_Speed = 7;
		fire_Speed = 10; 
		enemy_Speed = 3;

		Sound("sound/BGM01.wav",true);
	}
		
	public void start(){	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);	//keyboard event	
		th = new Thread(this);  //make thread
		th.start();	// thread start
	}

	public void run(){ 
		try{
			while(!all_stop){ 		
				KeyProcess();	//get the keyboard value to update position
				StageDrawProcess();
				EnemyProcess();
				MissileProcess();
				ExplosionProcess();
				repaint(); 		//repaint plane using new position
				StageClearProcess();				
				Thread.sleep(20);	//delay time
				cnt++;
			}			
		}catch (Exception e){}		
	}	
	
	////////// Process ///////////
	public void MissileProcess(){ 
		
		if ( KeySpace == true && (cnt % fire_Speed) == 0 ){ 	//plane shooting					
			ms = new Missile(missile_img, x + 155, y + 32, missile_Speed, 0, 0); //set missile position
			Missile_List.add(ms);   //add missile to list						
		}
		
		for ( int i = 0 ; i < Missile_List.size() ; ++i){
			ms = (Missile) Missile_List.get(i);
			ms.move();
			
			//end of main frame
			if ( ms.x > f_width - 20||ms.x<0||ms.y<0||ms.y>f_height){
				Missile_List.remove(i);
			}
			
			//enemy missile shot
			if (Crash(x, y, ms.x, ms.y, plane_img, ms.type) && ms.who == 1 ) {
				player_Hitpoint --;
				GameOver(player_Hitpoint);
				
				ex = new Explosion(x + plane_img.getWidth(null) / 2, y + plane_img.getHeight(null) / 2, 1);
				Explosion_List.add(ex);
				Missile_List.remove(i);
				Sound("sound/explosion_sound.wav",false);
			}
				
			for (int j = 0 ; j < Enemy_List.size(); ++ j){
				en = (Enemy) Enemy_List.get(j);
				
				Image tmp = enemy_img1;				
				if(en.type == 2)	tmp = enemy_img2;
				else if(en.type == 3)	tmp = enemy_img3;
				else if(en.type == 4)	tmp = boss1;
				
				//plane missile
				if (Crash(ms.x, ms.y, en.x, en.y, ms.type, tmp) && ms.who==0){
					Missile_List.remove(i);
					
					if(en.type == 4 && boss_Status == 1){	//boss
						if(boss_Hitpoint < 1){
							Enemy_List.remove(j);
							boss_Status = 2;	//disappeared
							stage_clear = true;
						}
						boss_Hitpoint -= 1;
					}
					else
						Enemy_List.remove(j);
								
					stage_Score += 10; //get score
					
					if(stage_Score % 200 == 0)	Q_available += 1;
					
					//explision effect
					ex = new Explosion(en.x + tmp.getWidth(null) / 2, en.y + tmp.getHeight(null) / 2 , 0);				
					Explosion_List.add(ex); 
					Sound("sound/explosion_sound.wav",false);
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
			
			Image tmp = enemy_img1;				
			if(en.type == 2)	tmp = enemy_img2;
			else if(en.type == 3)	tmp = enemy_img3;
			else if(en.type == 4)	tmp = boss1;
			
			//enemy shoot
			if ( cnt % 100 == 0){
				if(en.type != 4)
				{
					ms = new Missile (enemy_missile_img, en.x, en.y + 10, enemy_missile_Speed, 1,0);
					Missile_List.add(ms);
				}	
			}
			if(en.type == 4)	//boss shot
			{
				int a;
				Random random_shot = new Random();
				a = random_shot.nextInt(3);
				if(cnt % 80 == 0)
				{
					int tmp_a;
					tmp_a = a+1;
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed + 3, 1,360 - tmp_a*10);//upward direction
						Missile_List.add(ms);
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed + 3, 1,tmp_a*10);//downward direction
						Missile_List.add(ms);
				}
				////////////get wrong value when get dx and dy////////////////////
				/*if(cnt % 100 == 0 && en.x == 900 && en.y == 200)//if there is only boss and user
				{

					//double dx;
					double dy;
					//double d_xy;
					int in_angle = 0;
					dx = (double) (x - ex.x);
					dy = (double) (y - ex.y);
					d_xy = Math.sqrt((dx*dx) + (dy*dy));
					m_angle = Math.acos(dx/d_xy);
			
					in_angle = (int)Math.toDegrees(m_angle);
					Tangle = in_angle;
					if(in_angle < 0)
						in_angle = 360 + in_angle;
					
					ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed + 3, 1, in_angle);//to user direction
					Missile_List.add(ms);
				}*/

				if(cnt % 40 == 0)
				{
					
					if(a == 0)
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/6, enemy_missile_Speed + 3, 1,0);//direct direction
					else if(a == 1)
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed + 3, 1,0);//direct direction
					else
						ms = new Missile (boss_missile_img, en.x, en.y + 5*boss1.getHeight(null)/6, enemy_missile_Speed + 3, 1,0);//direct direction
					
					Missile_List.add(ms);
				}
				
			}
			//crashed with enemy
			if(Crash(x, y, en.x, en.y, plane_img, tmp)){

				player_Hitpoint --; 
				GameOver(player_Hitpoint);	
				
				if(en.type == 4 && boss_Status == 1){	//boss
					if(boss_Hitpoint < 1){
						Enemy_List.remove(i);
						boss_Status = 2;	//disappeared
						stage_clear = true;
					}
					boss_Hitpoint -= 1;
				}
				else
					Enemy_List.remove(i);
				
							
				ex = new Explosion(en.x + tmp.getWidth(null) / 2, en.y + tmp.getHeight(null) / 2, 0 );
				Explosion_List.add(ex); 
				ex = new Explosion(x+plane_img.getWidth(null) / 2, y+plane_img.getHeight(null)/ 2, 1 );
				Explosion_List.add(ex);
				
				Sound("sound/explosion_sound.wav",false);
			}
		}
		
		Random random = new Random();
	
		//stage 1 boss appeared
		if(stage_Score>200 && boss_Status == 0 && stage_clear == false)//make boss
		{
			boss_stage = true;
			boss_Hitpoint = 50;
			
			en = new Enemy(f_width , f_height/3 , enemy_Speed, 4);
			Enemy_List.add(en);
			boss_Status = 1;
		}
		if(boss_stage == false)
		{
			if ( cnt % 100 == 0 ){ //make enemy
				en = new Enemy(f_width + random.nextInt(20)*10 + 20, random.nextInt(550) + 25, enemy_Speed,random.nextInt(3) + 1);
				Enemy_List.add(en); 				
			}		
			if ( cnt % 170 == 0 ){ //make enemy
				en = new Enemy(f_width + random.nextInt(7)*10 + 20, random.nextInt(550) + 25, enemy_Speed,random.nextInt(3) + 1);
				Enemy_List.add(en); 			
			}
		}

	}
	
	
	 public void ExplosionProcess(){		  
		  for (int i = 0 ;  i < Explosion_List.size(); ++i){
			  ex = (Explosion) Explosion_List.get(i);
			  ex.effect();
		  }
	}
	 
	 public void StageClearProcess(){
		if(stage_clear == true){		
			save_cnt = cnt;			
			total_Score += stage_Score;
			stage_Score = 0;
			Enemy_List.clear();
			Missile_List.clear();
			/////////difficulty up setting////////////////
			enemy_Speed += 2;
			enemy_missile_Speed += 4;	
			//////////////////////////////////////////////
			boss_Status = 0;
			
			for (int i = 0; i< 150; i++)//approximately 3 second wait to prepare
			{
				KeyProcess();
				repaint();
				try{
					Thread.sleep(20);	//delay time
				}
				catch (Exception e){}
			}
			boss_stage = false;
			stage_clear = false;	
			stage_draw = true;
			stage_status +=1;
		}
		
	}
	 
	 public void StageDrawProcess(){
			if(stage_draw == true){
				
				for (int i = 0; i< 150; i++)//approximately 3 second wait to prepare
				{
					KeyProcess();
					repaint();
					try{
						Thread.sleep(20);	//delay time
					}
					catch (Exception e){}
				}
				stage_draw = false;
			}
			
		}
	 
	 //////////// functions ////////////
	 
	
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

	//////////// draw //////////////
	
	public void paint(Graphics g){		
		buffImage = createImage(f_width, f_height); //set double buffer size
		buffg = buffImage.getGraphics();
		
		if(all_stop == true)
			Draw_GameOver(g);
		else
			update(g);
		
	}
	
	public void update(Graphics g){
		Draw_Background(); 
		Draw_Player();
		if(stage_draw == true)
			Draw_Stage();	
		Draw_Enemy();
		Draw_Missile(); 
		Draw_Explosion();
		Draw_StatusText();
		if(stage_clear == true)
			Draw_StageClear();
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
		
	}
	
	public void Draw_Stage(){		
		if(stage_status == 1){			
			buffg.drawImage(stage1, f_width/2 - stage1.getWidth(null)/2, f_height/2  - stage1.getHeight(null), this);
		}else if(stage_status == 2){			
			buffg.drawImage(stage2, f_width/2 - stage2.getWidth(null)/2, f_height/2 - stage2.getHeight(null), this);
		}else{			
			buffg.drawImage(stage3, f_width/2 - stage3.getWidth(null)/2, f_height/2 - stage3.getHeight(null), this);
		}			
		buffg.drawImage(start, f_width/2 - start.getWidth(null)/2, f_height/2  + start.getHeight(null), this);	
	}
	
	public void Draw_GameOver(Graphics g){
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(gameover_img, 0, 0, this);
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
	public void Draw_StageClear(){				
		buffg.drawImage(stage_clear_img, f_width/2 - stage_clear_img.getWidth(null)/2, f_height/2- stage_clear_img.getHeight(null)/2, this);
	}
	
	public void Draw_StageNum(Image img)
	{			
		buffg.drawImage(img, f_width/2 - 295, f_height/2, this);
	}
	public void Draw_Background(){		
		buffg.clearRect(0, 0, f_width, f_height);
		if ( bx > - 600){		
			bx -= 1;
		}else {			
			bx = 0;
		}
		if(stage_status == 1)
			buffg.drawImage(background_img, bx, 0, this);
		else if(stage_status == 2)
			buffg.drawImage(background_img2, bx, 0, this);
		else
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
			if(ms.who == 1)//enemy				
				buffg.drawImage(ms.type, ms.x, ms.y, this);
				
			
			
		}
	}

	
	public void Draw_Enemy(){ 
		for (int i = 0 ; i < Enemy_List.size() ; ++i ){
			en = (Enemy)(Enemy_List.get(i));
			
			Image tmp = enemy_img1;				
			if(en.type == 2)	tmp = enemy_img2;
			else if(en.type == 3)	tmp = enemy_img3;
			else if(en.type == 4)	tmp = boss1; 
			
			buffg.drawImage(tmp, en.x, en.y, this);	
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
		buffg.drawString("Total Score : " + total_Score, 1000, 50);
		buffg.drawString("Stage Score : " + stage_Score, 1000, 70);
		buffg.drawString("HitPoint : " + player_Hitpoint, 1000, 90);
		buffg.drawString("Ultimate Skill : " + Q_available, 1000, 110);
		if(boss_Status == 1)	
		{
			buffg.drawString("Boss HP : " + boss_Hitpoint , 1000, 130);
			
			//////display for debugging/////////////
			/*buffg.drawString("Tangle : " + Tangle , 1000, 150);
			buffg.drawString("x : " + x , 1000, 170);
			buffg.drawString("dx : " + dx , 1000, 190);
			buffg.drawString("enx : " + en.x , 1000, 210);
			buffg.drawString("dxt : " + d_xy , 1000, 230);*/
		}

	}
	
	
	//////////////// key event ///////////////
	
	
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
			case KeyEvent.VK_Q : //ultimate skill
				KeyQ = true;
				break;
			case KeyEvent.VK_T : //Test key, add 100 score
				KeyT = true;
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
			case KeyEvent.VK_Q : //ultimate
				KeyQ = false;
				break;
			case KeyEvent.VK_T : //test key
				KeyT = false;
				break;
		}
	}
	
	public void keyTyped(KeyEvent e){}	//dealing key event

	public void KeyProcess(){	//plane's move scale

		if(KeyUp == true) {
			if( y > 20 ) y -= 5;
		}

		if(KeyDown == true) {
			if( y+ plane_img.getHeight(null) < f_height ) y += 5;		
		}

		if(KeyLeft == true) {
			if ( x > 0 ) x -= 5;
		}

		if(KeyRight == true) {
			if ( x + plane_img.getWidth(null) < f_width ) x += 5;
		}
		
		if(KeyQ == true && Q_available > 0){
			Sound("sound/ultimate_skill.wav",false);
			for(int i = 0; i < Enemy_List.size(); i++){
				Enemy en = ((Enemy) Enemy_List.get(i));
				if(en.type != 4)
					Enemy_List.remove(i);
			}
			
			Missile_List.clear();
			Q_available -= 1;
			KeyQ = false;
		}
		if(KeyT == true) 
		{
			stage_Score += 100;
			KeyT = false;
		}
	}
	
	
	
	
	public void Sound(String file, boolean Loop){

		Clip clip;

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();

			if (Loop) clip.loop(-1);	//loop : true - endless

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void GameOver(int fd){
		if(fd <= 0){
			all_stop = true;
			Sound("sound/Game_Over_sound_effect.wav",false);
		}
	}	
}
/*
class Enemy{ 
	int x,y,speed;
	int type;
	
	Enemy(int x, int y, int speed,int type){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.type = type;
	}
	
	public void move(){
		if(this.type != 4)
			x -= speed;
		else
			if(x>900)	x -= speed;
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
*/
