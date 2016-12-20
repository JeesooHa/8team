import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

/*
=====================
:mod:'game' ���
===================== 
  
.. moduleauthor:: ������ <jsha2913@gmail.com>
  
  
����
====
 
 ���ð��� ���� ���� Ŭ����


����
====

 ���ø�ũ:
 * http://blog.naver.com/dosem321/40170781167
 
 
���� �۾���
======== 

 * ������ (Jeesoo Ha)
 * Ȳ���� (Soongeun Hwang)

�۾�����
--------

 * 2016.09.07 Jeesoo : ���� Ŭ���� ���� ����
 * 2016.12.20 Jeesoo : sphinx �������� �ּ� ����

*/


public class game {	
	public static void main(String[] ar){		
		game_Frame fms = new game_Frame();	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 	
	/*game_Frame Ŭ����	
	���� ���� main Ŭ����	
	*/
	
	/*
	:param all_stop: ���� ���Ḧ ��Ÿ���� ����  
	:param stage_clear: �������� Ŭ��� ��Ÿ���� ����
	:param stage: ���������� ��Ÿ���� ����  
	:param Q_available: ����� �� �ִ� �ñر⸦ ��Ÿ���� ����  
	*/
	private static boolean all_stop = false;
	private static boolean stage_clear = false;
	private static int stage = 1;
	private static int Q_available = 0;
	
	/*
	:param op : ������ ��� ���� �Ǵ� -> Ű S�� �����ÿ� opening�� ��ŵ�ȴ�.
	:param f_width, f_height : ����â�� ����, ���� ũ��
	:param x,y : �÷��̾� ĳ������ ��ġ ���� ����
	:param bx : ĳ���� �̵� ȿ���� ���� ��� �̵� ��ġ ����
	*/
	boolean op = false;
	int f_width = 1200;
	int f_height = 600;
	int x = 100;
	int y = 300;;
	int bx = 0;
	
	/*
	Ű���� �̺�Ʈ ����
	:param KeyUp: ĳ���� ���� �̵�  
	:param KeyDown: ĳ���� �Ʒ��� �̵�  
	:param KeyLeft: ĳ���� ���� �̵�    
	:param KeyRight: ĳ���� ���� �̵�    
	:param KeySpace: �̻��� �߻�  
	:param KeyQ: �ñر� ���
	:param Key1: ĳ���� ���ý� 1�� ĳ���� ����
	:param Key2: ĳ���� ���ý� 2�� ĳ���� ����  
	:param start_key: ���� ���� ��ư  
	
	:param KeyT : �׽�ƮŰ, ������ ������
	*/
	boolean KeyUp = false; 
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;
	boolean KeySpace = false;
	boolean KeyQ = false; 
	boolean Key1 = false;
	boolean Key2 = false;
	boolean start_key = false;
	
	boolean KeyT = false;
	
	/*
	:param cnt : �� ���� �ֱ� ����
	:param stage_status : �������� ���¸� ��Ÿ���� ����, 1���� 3���� ���������� ǥ��
	:param stage_draw : ȭ�鿡 stage�� ��Ÿ��
	:param boss_stage : boss�� ����ϸ� true�� �Ǹ鼭 ������ �ϳ��� �����Ŵ. false : normal stage, true : boss stage
	:param selected : ĳ���� ���ý� ĳ���Ͱ� ���õ��� ǥ��
	:param selected_player : ���õ� ĳ���� ���� ����
	*/
	int cnt;	
	int stage_status = 1;
	boolean stage_draw = true;
	boolean boss_stage = false;
	boolean selected = false;
	int selected_player;
	
	/*
	�ʱ� ���� ���� 
	:param stage_Score : �� ���������� ȹ�� ����. ���� ���������� �Ѿ �� 0�� ��
	:param total_Score : ���� ����. ���� ���������� �Ѿ �� stage_Score ������ ������
	
	:param player_Speed : �÷��̾� ĳ������ �̵� �ӵ�
	:param player_missile_Speed : �÷��̾� ĳ������ �̻��� �ӵ�
	:param player_fire_Speed : �÷��̾� ĳ������ �̻��� ���� �ֱ�
	:param player_Hitpoint : �÷��̾��� �����
	
	:param enemy_Speed : ���� �̵� �ӷ� 
	:param enemy_missile_Speed : �� �̻��� �ӷ�	
	:param enemy_Hitpoint : ���� �����
	
	:param boss_Hitpoint : ������ �����. �������� ���� �ٸ��� �Ҵ� ����
	:param boss_Status : ������ ���� ����. 0: not appeared, 1: appeared, 2: destroyed
	
	:param save_cnt,sub_cnt : ���� �ð� ������ ���� ������
	*/	
	int stage_Score = 0;
	int total_Score = 0;
	
	int player_Speed = 5;
	int player_missile_Speed = 7;
	int player_fire_Speed = 10; 
	int player_Hitpoint = 3; 
	
	int enemy_Speed = 3; 
	int enemy_missile_Speed = 5; 	
	int enemy_Hitpoint = 1;
	
	int boss_Hitpoint;
	int boss_Status = 0;
	
	int save_cnt = 0;
	int sub_cnt = 0;
	
	
	Thread th; 
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	/*
	���ӿ��� ���Ǵ� �̹����� 
	 
	*/
	Image player_img;
	Image player_cand1;
	Image player_cand2;
	
	Image background_img; 
	Image background_img2; 
	Image background_img3; 
	
	Image explo_img; 
	Image missile_img;
	Image enemy_missile_img;
	Image boss_missile_img;
	
	Image enemy_img1;
	Image enemy_img2;
	Image enemy_img3;

	Image boss1;
	Image boss2;
	Image boss3;

	Image start;
	Image opening_play;
	Image gameover_img;
	Image stage_clear_img;
	
	Image stage1;
	Image stage2;
	Image stage3;
	
	/*
	���� ���� clip
	
	* clip : ���� ����. opening ����, ���� ���� �� ����, game over ����
	* sub_sound : ���� ����. �̻��� �߻���, �ñر� ��� ����, ���� ����
	
	:param clip_start : ���� ������ ��� ���θ� �Ǵ��ϴ� ����

	*/
	
	Clip clip;	//BGM
	Clip sub_sound;
	boolean clip_start = false;
	
	//to save shot missile
	ArrayList<Missile> Missile_List = new ArrayList();
	ArrayList<Enemy> Enemy_List = new ArrayList();	
	ArrayList<Explosion> Explosion_List = new ArrayList();

	/*
	�̹����� ������ٰ� ��Ÿ�����ϴ� ���� �����ϱ� ���� ���� ���� ���۸��� ���� ������
	*/
	Image buffImage; 
	Graphics buffg; 
	
	/*	
	������ ���� Ŭ������ ���
	
	* ms : Missile class
	* en : Enemy class
	* ex : Explosion class
	
	*/
	Missile ms;	
	Enemy en;
	Explosion ex;
	
	game_Frame(){
		init();
		start();
  
		/*
		����â ����
		*/
		setTitle("Shooting Game");
		setSize(f_width, f_height);
		
		Dimension screen = tk.getScreenSize();

		int f_xpos = (int)(screen.getWidth() / 2 - f_width / 2);
		int f_ypos = (int)(screen.getHeight() / 2 - f_height / 2);

		setLocation(f_xpos, f_ypos);
		setResizable(false);
		setVisible(true);
	}
	
	public void init(){

		/*
		����� �̹��� �ҷ�����
		*/
		player_cand1 = new ImageIcon("images/plane_img.png").getImage();
		player_cand2 = new ImageIcon("images/plane_img2.png").getImage();
		
		missile_img = new ImageIcon("images/missile_img.png").getImage();
		
		enemy_img1 = new ImageIcon("images/enemy1.png").getImage();
		enemy_img2 = new ImageIcon("images/enemy2.png").getImage();	
		enemy_img3 = new ImageIcon("images/enemy3.png").getImage();	
		
		background_img = new ImageIcon("images/background1.jpg").getImage();
		background_img2 = new ImageIcon("images/background2.png").getImage();
		background_img3 = new ImageIcon("images/background3.jpg").getImage();
		
		explo_img = new ImageIcon("images/enemy_explosion.png").getImage();
		enemy_missile_img = new ImageIcon("images/enemy_shot.png").getImage();
		
		gameover_img = new ImageIcon("images/game over.png").getImage();
		stage_clear_img = new ImageIcon("images/stage_clear.png").getImage();			
		start = new ImageIcon("images/start.png").getImage();	
		opening_play = new ImageIcon("images/opening_img.gif").getImage();
		
		boss1 = new ImageIcon("images/ufo_img.png").getImage();
		boss2 = new ImageIcon("images/boss2.png").getImage();
		boss3 = new ImageIcon("images/boss_body.png").getImage();
		boss_missile_img = new ImageIcon("images/boss_laser.png").getImage();
		
		stage1 = new ImageIcon("images/stage01.png").getImage();
		stage2 = new ImageIcon("images/stage02.png").getImage();
		stage3 = new ImageIcon("images/stage03.png").getImage();

	}
		
	public void start(){	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);	//keyboard event	
		th = new Thread(this);  //make thread
		th.start();	// thread start	
	}

	public void run(){ 
		try{
			/*
			���� ����
			*/
			MainSound("sound/StarWars_Main_Theme.wav",false);
			
			/*
			���� ������ ���
			*/
			while(!op){
				KeyProcess();
				repaint();
				Thread.sleep(20);
			}	

			MainSound("sound/BGM01.wav",true);
			
			/*
			ĳ���� ���� �ܰ�
			*/
			while(!selected){			
				KeyProcess();
				repaint();				
				Thread.sleep(20);
				if(selected_player == 1){
					player_img = player_cand1;
					selected = true;
				}
				else if(selected_player == 2){
					player_img = player_cand2;
					selected = true;
				}
			}
			
			/*
			���� ���� - ���� ���ᰡ �ɶ� ����  
			*/
			while(!all_stop){ 		
				KeyProcess();		
				StageDrawProcess();
				EnemyProcess();
				MissileProcess();
				ExplosionProcess();				
				repaint(); 		
				StageClearProcess();				
				Thread.sleep(20);
				cnt++;
			}			
		}catch (Exception e){}		
	}	
	/*
	# ���� Process 
	*/
	public void MissileProcess(){ 
		/*
		## �̻��� ���� Process
		*/
		
		/*
		* �÷��̾� ĳ������ �̻��� ���� : spacebar�� ���� ��� �̻����� �����ǰ� �̸� Missile_List�� �����Ѵ�. ���� �̻��ϰ� �����ϱ� ���� ms.who = 0 ���� �� �Ҵ�
		*/
		if ( KeySpace == true && (cnt % player_fire_Speed) == 0 ){ 	//player shooting					
			ms = new Missile(missile_img, x + 155, y + 32, player_missile_Speed, 0, 0); //set missile position
			Missile_List.add(ms);   //add missile to list						
		}
		/*
		* Missile_List�� ����� �̻��ϵ��� ȭ�鿡 �׸� : ȭ�鿡�� �����ų� ���̳� �÷��̾� ĳ���Ϳ� �浹�� �̻��ϵ��� �����Ѵ�.  
		*/
		
		for ( int i = 0 ; i < Missile_List.size() ; ++i){
			ms = (Missile) Missile_List.get(i);
			ms.move();
			
			/*
			- ȭ�鿡�� ���� �̻��� ����
			 */
			if ( ms.x > f_width - 20||ms.x<0||ms.y<0||ms.y>f_height){
				Missile_List.remove(i);
			}
			
			/*
			- ĳ���Ͱ� ���� �̻����� ���� ��� ������� 1�� �����ϰ� ���� ���μ��� �ѹ� ���� 
			- ĳ������ ������� 0�� �Ǿ��� ��� ������ �����ϱ� ���� GameOver�Լ��� ���� �˻�
			*/
			if (Crash(x, y, ms.x, ms.y, player_img, ms.type) && ms.who == 1 ) {
				player_Hitpoint --;
				GameOver(player_Hitpoint);
				
				ex = new Explosion(x + player_img.getWidth(null) / 2, y + player_img.getHeight(null) / 2, 1);
				Explosion_List.add(ex);
				Missile_List.remove(i);
				SubSound("sound/explosion_sound.wav");
			}
			/*
			- �� �̻��Ͽ� ���� ���� �Ǻ��Ͽ� ������ ���� ����, ������ ������� ���ҽ�Ų��.	
			*/
			for (int j = 0 ; j < Enemy_List.size(); ++ j){
				en = (Enemy) Enemy_List.get(j);
				
				Image tmp = enemy_img1;				
				if(en.type == 2)	tmp = enemy_img2;
				else if(en.type == 3)	tmp = enemy_img3;
				else if(en.type == 4)	tmp = boss1;
				else if(en.type == 5)	tmp = boss2;
				else if(en.type == 6)	tmp = boss3;
				
				/*
				- ���� �÷��̾� ĳ������ �̻����� ���� ���
				*/					
				if (Crash(ms.x, ms.y, en.x, en.y, ms.type, tmp) && ms.who==0){
					Missile_List.remove(i);
					
					if((en.type >= 4 ) && boss_Status == 1){	//boss
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
					
					//explosion effect
					ex = new Explosion(en.x + tmp.getWidth(null) / 2, en.y + tmp.getHeight(null) / 2 , 0);				
					Explosion_List.add(ex); 
					SubSound("sound/explosion_sound.wav");
				}
				
			}//for Enemy
		}//for Missile
	
	}
	
	public void EnemyProcess(){
		/*
		## �� ���� Process
		*/
		
		/*
		* Enemy_List�� �����ϴ� ������ ȭ�鿡 �׸�
		*/
		for (int i = 0 ; i < Enemy_List.size() ; ++i ){ 
			en = (Enemy)(Enemy_List.get(i)); 
	
			en.move(); //move enemy
			if(en.x < -10){
				Enemy_List.remove(i); 
			}		
			
			Image tmp = enemy_img1;				
			if(en.type == 2)	tmp = enemy_img2;
			else if(en.type == 3)	tmp = enemy_img3;
			else if(en.type == 4)	tmp = boss1;
			else if(en.type == 5)	tmp = boss2;
			else if(en.type == 6)	tmp = boss3;
			
			/*
			- ������ ���� ����� �� �̻��� ����
			*/
			if ( cnt % 100 == 0 && en.type <4){
				ms = new Missile (enemy_missile_img, en.x, en.y + 10, enemy_missile_Speed, 1,0);
				Missile_List.add(ms);
			}
			
			/*
			- ������ ����� �̻��� ����
			- 3�������� �̻����� ��
			*/
			if(en.type >=4)	{
				int a;
				Random random_shot = new Random();
				a = random_shot.nextInt(3);
				if(cnt % 120 == 0){
					int tmp_a;
					tmp_a = a+1;
					ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed+3, 1,360 - tmp_a*10);//upward direction
					Missile_List.add(ms);
					ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed+3, 1,tmp_a*10);//downward direction
					Missile_List.add(ms);
				}
				
				if(cnt % 80 == 0){		
					if(a == 0)
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/6, enemy_missile_Speed+3, 1,0);//direct direction
					else if(a == 1)
						ms = new Missile (boss_missile_img, en.x, en.y + boss1.getHeight(null)/2, enemy_missile_Speed+3, 1,0);//direct direction
					else
						ms = new Missile (boss_missile_img, en.x, en.y + 5*boss1.getHeight(null)/6, enemy_missile_Speed+3, 1,0);//direct direction				
					Missile_List.add(ms);
				}			
			}
			
			
			/*
			- ���� �÷��̾��� ĳ���Ϳ� �浹�� ��� �÷��̾��� ����� ����
			- �÷��̾� ĳ���Ͱ� ������ �浹�� ���� ������ ����ȴ�.
			*/
			if(Crash(x, y, en.x, en.y, player_img, tmp)){
				player_Hitpoint --; 
				GameOver(player_Hitpoint);	
				
				if(en.type >= 4&& boss_Status == 1){	//boss
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
				ex = new Explosion(x+player_img.getWidth(null) / 2, y+player_img.getHeight(null)/ 2, 1 );
				Explosion_List.add(ex);
				
				SubSound("sound/explosion_sound.wav");
			}
		}
	
		/*
		* �� ���������� ���� ���� ����
		*/
		//stage 1 boss appeared
		if(stage_Score>200 && boss_Status == 0 && stage_clear == false && stage == 1){
			boss_stage = true;
			boss_Hitpoint = 30;		
			en = new Enemy(f_width , f_height/3 , enemy_Speed, 4);
			Enemy_List.add(en);
			boss_Status = 1;
		}
		//stage 2 boss appeared
		else if(stage_Score>200 && boss_Status == 0 && stage_clear == false&& stage ==2){
			boss_stage = true;
			boss_Hitpoint = 50;		
			en = new Enemy(f_width , f_height/3 , enemy_Speed, 5);
			Enemy_List.add(en);
			boss_Status = 1;
		}
		//stage 3 boss appeared
		else if(stage_Score>200 && boss_Status == 0 && stage_clear == false&& stage == 3){
			boss_stage = true;
			boss_Hitpoint = 100;		
			en = new Enemy(f_width , f_height/3 , enemy_Speed, 6);
			Enemy_List.add(en);
			boss_Status = 1;
		}
		
		
		/*
		* ������ �� ���� - ��ġ�� random �Լ��� �������ν� ���� ��ġ���� ���� ��Ÿ����.
		 */
		Random random = new Random();
		if(boss_stage == false){
			if ( cnt % 100 == 0 && KeyQ == false){ //make enemy
				en = new Enemy(f_width + random.nextInt(20)*10 + 20, random.nextInt(550) + 25, enemy_Speed,random.nextInt(3) + 1);
				Enemy_List.add(en); 				
			}		
			if ( cnt % 170 == 0 && KeyQ == false ){ //make enemy
				en = new Enemy(f_width + random.nextInt(7)*10 + 20, random.nextInt(550) + 25, enemy_Speed,random.nextInt(3) + 1);
				Enemy_List.add(en); 			
			}
		}

	}
	
	
	 public void ExplosionProcess(){
		 /*
		 # ���� Process
		 */
		  for (int i = 0 ;  i < Explosion_List.size(); ++i){
			  ex = (Explosion) Explosion_List.get(i);
			  ex.effect();		  
		  }
	 }
	 
	 public void StageClearProcess(){
		 /*
		 # Stage Clear Process
		 */
		if(stage_clear == true){
			stage++;
			save_cnt = cnt;			
			total_Score += stage_Score;
			stage_Score = 0;
			Enemy_List.clear();
			Missile_List.clear();
			
			/////////difficulty up setting////////////////
			enemy_Speed += 2;
			enemy_missile_Speed += 2;
			enemy_Hitpoint += 1;
			boss_Hitpoint += 1;
			//////////////////////////////////////////////
			
			boss_Status = 0;		
			sub_cnt = 0;
			//approximately 3 second wait to prepare
			for (int i = 0; i< 150; i++){
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
		 /*
		 # Stage Draw Process
		 */
			if(stage_draw == true){
				//approximately 3 second wait to prepare
				for (int i = 0; i< 150; i++){
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
	 
	/*
	# Functions
	*/
	 

	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2){
		/*
		## �浹 �˻� �Լ�
		*/
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
	
	// ## Main Sound �Լ�
	public void MainSound(String file, boolean Loop){

		try {
			if(clip_start == true){
				clip.stop();
				clip_start = false;
			}
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			clip_start =true;

			if (Loop) clip.loop(-1);	//loop : true - endless

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ## SubSound �Լ�
	public void SubSound(String file){

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			sub_sound = AudioSystem.getClip();
			sub_sound.open(ais);
			sub_sound.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// ## GameOver �Ǻ� �Լ�
	public void GameOver(int fd){
		if(fd <= 0){
			all_stop = true;
			MainSound("sound/Game_Over_sound_effect.wav",false);
		}
	}	

	/*
	# �׸��� �Լ�
	*/
	
	public void paint(Graphics g){		
		buffImage = createImage(f_width, f_height); //set double buffer size
		buffg = buffImage.getGraphics();
		
		if(all_stop == true)
			Draw_GameOver(g);
		else if(op == false){
			OPENING(g);
		}
		else if(selected == false)
		{
			InitUpdate(g);
		}
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
	
	// ## opening ��� �Լ�
	public void OPENING(Graphics g){
		Draw_InitBackground();
		Draw_opening();
		Draw_Choice();
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
	public void Draw_opening(){
		buffg.drawImage(opening_play, f_width/2 - opening_play.getWidth(null)/2, f_height/2 - opening_play.getHeight(null)/2, this);
	}
	public void Draw_Choice(){
		Color white = new Color(255, 255, 255);
		buffg.setColor(white);
		buffg.setFont(new Font("Defualt", Font.BOLD, 20));
		buffg.drawString("Game Start Key", 500, 500);
		buffg.drawString("Press Key: S", 500, 530);
	}
	
	// ## ĳ���� ���ý� �׸��� �Լ�
	public void InitUpdate(Graphics g){
		Draw_InitBackground();
		Draw_Cand();
		Draw_Text();
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
	
	public void Draw_InitBackground(){
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(background_img, 0, 0, this);
	}
	public void Draw_Cand(){
		buffg.drawImage(player_cand1, f_width/2 - 300, f_height/2, this);
		buffg.drawImage(player_cand2, f_width/2 + 150, f_height/2, this);
	}
	public void Draw_Text(){
		Color white = new Color(255, 255, 255);
		buffg.setColor(white);
		buffg.setFont(new Font("Defualt", Font.BOLD, 25));
		buffg.drawString("Select your player", 500, 450);
		buffg.drawString("Press Key: 1 - left or 2 - right", 450, 480);
		buffg.drawString("Move - Arrow keys  /  Shoot - Space bar  /  Ultimate Skill - Q", 255, 550);
	}
	
	// ## Stage �׸��� �Լ�
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
	
	// ## GameOver �׸��� �Լ�
	public void Draw_GameOver(Graphics g){
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(gameover_img, 0, 0, this);
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
	// ## Stage Clear �׸��� �Լ�
	public void Draw_StageClear(){				
		buffg.drawImage(stage_clear_img, f_width/2 - stage_clear_img.getWidth(null)/2, f_height/2- stage_clear_img.getHeight(null)/2, this);
	}
	
	// ## Stage number �׸��� �Լ�
	public void Draw_StageNum(Image img){			
		buffg.drawImage(img, f_width/2 - 295, f_height/2, this);
	}
	
	// ## Background �׸��� �Լ� 
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
			buffg.drawImage(background_img3, bx, 0, this);		
	}
	// ## �÷��̾� ĳ���� �׸��� �Լ�
	public void Draw_Player(){ 
		buffg.drawImage(player_img, x, y, this);
	}
	// ## �̻��� �׸��� �Լ�
	public void Draw_Missile(){ 
		for (int i = 0 ; i < Missile_List.size(); ++i){			
			//get missile position
			ms = (Missile) (Missile_List.get(i)); 			
			//draw missile image to the current position			
			if(ms.who == 0) //player
				buffg.drawImage(missile_img, ms.x, ms.y, this); 			
			if(ms.who == 1)//enemy				
				buffg.drawImage(ms.type, ms.x, ms.y, this);	
		}
	}

	// ## �� �׸��� �Լ�
	public void Draw_Enemy(){ 
		for (int i = 0 ; i < Enemy_List.size() ; ++i ){
			en = (Enemy)(Enemy_List.get(i));
			
			Image tmp = enemy_img1;				
			if(en.type == 2)	tmp = enemy_img2;
			else if(en.type == 3)	tmp = enemy_img3;
			else if(en.type == 4)	tmp = boss1; 
			else if(en.type == 5)	tmp = boss2;
			else if(en.type == 6)	tmp = boss3;
			
			buffg.drawImage(tmp, en.x, en.y, this);	
		}
	}
	
	// ## ���� �׸��� �Լ�
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
	
	// ## ���� ���� �׸��� �Լ�
	public void Draw_StatusText(){
		Color white = new Color(255, 255, 255);
		buffg.setColor(white);
		buffg.setFont(new Font("Defualt", Font.BOLD, 20));
		buffg.drawString("Total Score : " + total_Score, 1000, 50);
		buffg.drawString("Stage Score : " + stage_Score, 1000, 70);
		buffg.drawString("HitPoint : " + player_Hitpoint, 1000, 90);
		buffg.drawString("Ultimate Skill : " + Q_available, 1000, 110);
		if(stage_Score >= 200 && cnt % 10 < 5 && sub_cnt < 80){
			Color red = new Color(255,0,0);
			buffg.setColor(red);
			buffg.drawString("Warning!", 500, 100);
			sub_cnt += 1;	
		}
		if(boss_Status == 1){
			buffg.drawString("Boss HP : " + boss_Hitpoint , 1000, 130);
		}		
	}
	
	
	// # Ű���� �̺�Ʈ �Լ�

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
			case KeyEvent.VK_SPACE :
				KeySpace = true;
				break;	
			case KeyEvent.VK_Q : 
				KeyQ = true;
				break;
			case KeyEvent.VK_T : 
				KeyT = true;
				break;
			case KeyEvent.VK_1 :
				Key1 = true;
				break;
			case KeyEvent.VK_2 :
				Key2 = true;
				break;
			case KeyEvent.VK_S :
				start_key = true;
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
			case KeyEvent.VK_SPACE :
				KeySpace = false;
				break;
			case KeyEvent.VK_Q :
				KeyQ = false;
				break;
			case KeyEvent.VK_T : 
				KeyT = false;
				break;
			case KeyEvent.VK_1 :
				Key1 = false;
				break;
			case KeyEvent.VK_2 :
				Key2 = false;
				break;
			case KeyEvent.VK_S :
				start_key = false;
				break;
		}
	}
	
	public void keyTyped(KeyEvent e){}	//dealing key event

	public void KeyProcess(){	//player's move scale
		// * �����̵� - ȭ��ǥ
		if(KeyUp == true) {
			if( y > 20 ) y -= 5;
		}

		if(KeyDown == true) {
			if( y+ player_img.getHeight(null) < f_height ) y += 5;		
		}

		if(KeyLeft == true) {
			if ( x > 0 ) x -= 5;
		}

		if(KeyRight == true) {
			if ( x + player_img.getWidth(null) < f_width ) x += 5;
		}
		
		// * �ñر� ��� - Q
		if(KeyQ == true && Q_available > 0){
			SubSound("sound/ultimate_skill.wav");
			
			Enemy tmp = null;

			for(int i = 0; i < Enemy_List.size(); i++){
				Enemy en = ((Enemy) Enemy_List.get(i));
	
				if(en.type >= 4)	{
					tmp = en;
					boss_Hitpoint -= 5;
					if(boss_Hitpoint < 1){
						Enemy_List.remove(i);
						boss_Status = 2;	//disappeared
						tmp = null;
						stage_clear = true;
					}
					boss_Hitpoint -= 1;
				}		
			}			
			
			Enemy_List.clear();
			Missile_List.clear();
			if(tmp !=null)
				Enemy_List.add(tmp);
			Q_available -= 1;
			KeyQ = false;
		}
		
		// * �׽�ƮŰ - T
		if(KeyT == true) {
			stage_Score += 100;
			Q_available += 1;
			KeyT = false;
		}
		
		// * ĳ���� ����Ű - 1, 2
		if(Key1 == true){
			selected_player = 1;
		}
		if(Key2 == true){
			selected_player = 2;
		}
		// * ���� ����Ű - S
		if(start_key == true)
			op = true;
	}
	
	
}

