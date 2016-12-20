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
:mod:'game' 모듈
===================== 
  
.. moduleauthor:: 하지수 <jsha2913@gmail.com>
  
  
설명
====
 
 슈팅게임 메인 실행 클래스


참고
====

 관련링크:
 * http://blog.naver.com/dosem321/40170781167
 
 
관련 작업자
======== 

 * 하지수 (Jeesoo Ha)
 * 황순근 (Soongeun Hwang)

작업일지
--------

 * 2016.09.07 Jeesoo : 메인 클래스 구현 시작
 * 2016.12.20 Jeesoo : sphinx 문법으로 주석 수정

*/


public class game {	
	public static void main(String[] ar){		
		game_Frame fms = new game_Frame();	
	}
}

class game_Frame extends JFrame implements KeyListener, Runnable{ 	
	/*game_Frame 클래스	
	게임 실행 main 클래스	
	*/
	
	/*
	:param all_stop: 게임 종료를 나타내는 변수  
	:param stage_clear: 스테이지 클리어를 나타내는 변수
	:param stage: 스테이지를 나타내는 변수  
	:param Q_available: 사용할 수 있는 궁극기를 나타내는 변수  
	*/
	private static boolean all_stop = false;
	private static boolean stage_clear = false;
	private static int stage = 1;
	private static int Q_available = 0;
	
	/*
	:param op : 오프닝 재생 유무 판단 -> 키 S를 누를시에 opening이 스킵된다.
	:param f_width, f_height : 게임창의 가로, 세로 크기
	:param x,y : 플레이어 캐릭터의 위치 저장 변수
	:param bx : 캐릭터 이동 효과를 위한 배경 이동 위치 변수
	*/
	boolean op = false;
	int f_width = 1200;
	int f_height = 600;
	int x = 100;
	int y = 300;;
	int bx = 0;
	
	/*
	키보드 이벤트 변수
	:param KeyUp: 캐릭터 위로 이동  
	:param KeyDown: 캐릭터 아래로 이동  
	:param KeyLeft: 캐릭터 위로 이동    
	:param KeyRight: 캐릭터 위로 이동    
	:param KeySpace: 미사일 발사  
	:param KeyQ: 궁극기 사용
	:param Key1: 캐릭터 선택시 1번 캐릭터 선택
	:param Key2: 캐릭터 선택시 2번 캐릭터 선택  
	:param start_key: 게임 시작 버튼  
	
	:param KeyT : 테스트키, 점수를 더해줌
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
	:param cnt : 적 생성 주기 변수
	:param stage_status : 스테이지 상태를 나타내는 변수, 1부터 3까지 스테이지를 표시
	:param stage_draw : 화면에 stage를 나타냄
	:param boss_stage : boss가 출몰하면 true가 되면서 보스를 하나만 출몰시킴. false : normal stage, true : boss stage
	:param selected : 캐릭터 선택시 캐릭터가 선택됨을 표시
	:param selected_player : 선택된 캐릭터 저장 변수
	*/
	int cnt;	
	int stage_status = 1;
	boolean stage_draw = true;
	boolean boss_stage = false;
	boolean selected = false;
	int selected_player;
	
	/*
	초기 게임 설정 
	:param stage_Score : 각 스테이지의 획득 점수. 다음 스테이지로 넘어갈 시 0이 됨
	:param total_Score : 종합 점수. 다음 스테이지로 넘어갈 시 stage_Score 점수가 더해짐
	
	:param player_Speed : 플레이어 캐릭터의 이동 속도
	:param player_missile_Speed : 플레이어 캐릭터의 미사일 속도
	:param player_fire_Speed : 플레이어 캐릭터의 미사일 생성 주기
	:param player_Hitpoint : 플레이어의 생명력
	
	:param enemy_Speed : 적의 이동 속력 
	:param enemy_missile_Speed : 적 미사일 속력	
	:param enemy_Hitpoint : 적의 생명력
	
	:param boss_Hitpoint : 보스의 생명력. 스테이지 별로 다르게 할당 가능
	:param boss_Status : 보스의 상태 변수. 0: not appeared, 1: appeared, 2: destroyed
	
	:param save_cnt,sub_cnt : 실행 시간 저장을 위한 변수들
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
	게임에서 사용되는 이미지들 
	 
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
	게임 사운드 clip
	
	* clip : 메인 사운드. opening 사운드, 게임 실행 중 사운드, game over 사운드
	* sub_sound : 서브 사운드. 미사일 발사음, 궁극기 사용 사운드, 폭파 사운드
	
	:param clip_start : 메인 사운드의 재생 여부를 판단하는 변수

	*/
	
	Clip clip;	//BGM
	Clip sub_sound;
	boolean clip_start = false;
	
	//to save shot missile
	ArrayList<Missile> Missile_List = new ArrayList();
	ArrayList<Enemy> Enemy_List = new ArrayList();	
	ArrayList<Explosion> Explosion_List = new ArrayList();

	/*
	이미지가 사라졌다가 나타났다하는 것을 방지하기 위해 사용된 더블 버퍼링을 위한 변수들
	*/
	Image buffImage; 
	Graphics buffg; 
	
	/*	
	게임을 위한 클래스들 사용
	
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
		게임창 설정
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
		사용할 이미지 불러오기
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
			게임 시작
			*/
			MainSound("sound/StarWars_Main_Theme.wav",false);
			
			/*
			게임 오프닝 재생
			*/
			while(!op){
				KeyProcess();
				repaint();
				Thread.sleep(20);
			}	

			MainSound("sound/BGM01.wav",true);
			
			/*
			캐릭터 선택 단계
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
			게임 실행 - 게임 종료가 될때 끝남  
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
	# 게임 Process 
	*/
	public void MissileProcess(){ 
		/*
		## 미사일 생성 Process
		*/
		
		/*
		* 플레이어 캐릭터의 미사일 생성 : spacebar를 누를 경우 미사일이 생성되고 이를 Missile_List에 저장한다. 적의 미사일과 구분하기 위해 ms.who = 0 으로 값 할당
		*/
		if ( KeySpace == true && (cnt % player_fire_Speed) == 0 ){ 	//player shooting					
			ms = new Missile(missile_img, x + 155, y + 32, player_missile_Speed, 0, 0); //set missile position
			Missile_List.add(ms);   //add missile to list						
		}
		/*
		* Missile_List에 저장된 미사일들을 화면에 그림 : 화면에서 나가거나 적이나 플레이어 캐릭터와 충돌한 미사일들은 삭제한다.  
		*/
		
		for ( int i = 0 ; i < Missile_List.size() ; ++i){
			ms = (Missile) Missile_List.get(i);
			ms.move();
			
			/*
			- 화면에서 나간 미사일 삭제
			 */
			if ( ms.x > f_width - 20||ms.x<0||ms.y<0||ms.y>f_height){
				Missile_List.remove(i);
			}
			
			/*
			- 캐릭터가 적의 미사일을 맞을 경우 생명력을 1을 감소하고 폭발 프로세스 한번 실행 
			- 캐릭터의 생명령이 0이 되었을 경우 게임을 종료하기 위해 GameOver함수로 상태 검사
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
			- 각 미사일에 맞은 적을 판별하여 보통의 적은 삭제, 보스는 생명력을 감소시킨다.	
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
				- 적이 플레이어 캐릭터의 미사일을 맞은 경우
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
		## 적 생성 Process
		*/
		
		/*
		* Enemy_List에 존재하는 적들을 화면에 그림
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
			- 보통의 적일 경우의 적 미사일 생성
			*/
			if ( cnt % 100 == 0 && en.type <4){
				ms = new Missile (enemy_missile_img, en.x, en.y + 10, enemy_missile_Speed, 1,0);
				Missile_List.add(ms);
			}
			
			/*
			- 보스일 경우의 미사일 생성
			- 3방향으로 미사일을 쏨
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
			- 적이 플레이어의 캐릭터와 충돌할 경우 플레이어의 생명력 감소
			- 플레이어 캐릭터과 보스와 충돌할 경우는 게임이 종료된다.
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
		* 각 스테이지별 보스 생성 조건
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
		* 보통의 적 생성 - 위치를 random 함수로 해줌으로써 여러 위치에서 적이 나타난다.
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
		 # 폭발 Process
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
		## 충돌 검사 함수
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
	
	// ## Main Sound 함수
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
	
	// ## SubSound 함수
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
	
	// ## GameOver 판별 함수
	public void GameOver(int fd){
		if(fd <= 0){
			all_stop = true;
			MainSound("sound/Game_Over_sound_effect.wav",false);
		}
	}	

	/*
	# 그리기 함수
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
	
	// ## opening 재생 함수
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
	
	// ## 캐릭터 선택시 그리기 함수
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
	
	// ## Stage 그리기 함수
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
	
	// ## GameOver 그리기 함수
	public void Draw_GameOver(Graphics g){
		buffg.clearRect(0, 0, f_width, f_height);
		buffg.drawImage(gameover_img, 0, 0, this);
		g.drawImage(buffImage, 0, 0, this); //draw image from buffer
	}
	// ## Stage Clear 그리기 함수
	public void Draw_StageClear(){				
		buffg.drawImage(stage_clear_img, f_width/2 - stage_clear_img.getWidth(null)/2, f_height/2- stage_clear_img.getHeight(null)/2, this);
	}
	
	// ## Stage number 그리기 함수
	public void Draw_StageNum(Image img){			
		buffg.drawImage(img, f_width/2 - 295, f_height/2, this);
	}
	
	// ## Background 그리기 함수 
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
	// ## 플레이어 캐릭터 그리기 함수
	public void Draw_Player(){ 
		buffg.drawImage(player_img, x, y, this);
	}
	// ## 미사일 그리기 함수
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

	// ## 적 그리기 함수
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
	
	// ## 폭발 그리기 함수
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
	
	// ## 게임 상태 그리기 함수
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
	
	
	// # 키보드 이벤트 함수

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
		// * 방향이동 - 화살표
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
		
		// * 궁극기 사용 - Q
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
		
		// * 테스트키 - T
		if(KeyT == true) {
			stage_Score += 100;
			Q_available += 1;
			KeyT = false;
		}
		
		// * 캐릭터 선택키 - 1, 2
		if(Key1 == true){
			selected_player = 1;
		}
		if(Key2 == true){
			selected_player = 2;
		}
		// * 게임 시작키 - S
		if(start_key == true)
			op = true;
	}
	
	
}

