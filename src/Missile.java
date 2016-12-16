import java.awt.Image;

/*
=====================
:mod:'Missile' 모듈
===================== 
  
.. moduleauthor:: 하지수 <jsha2913@gmail.com>
  
  
설명
====
 
 플레이어와 적의 미사일을 생성하는 클래스
 
 who 변수의 값이 0인 경우에는 플레이어의 미사일이 생성되고,
 1인 경우에는 적의 미사일이 생성된다.


참고
====

 관련링크:
 * http://blog.naver.com/dosem321/40171473252
 
 
관련 작업자
======== 

 * 하지수 (Jeesoo Ha)
 * 황순근 (Soonguen Hwang)

작업일지
--------

 * 2016.09.15 Jeesoo : 미사일 클래스 구현 

*/

public class Missile{
	Image type;
	int x;
	int y;
	int angle;// 0 is direct.
	int speed;
	int who;// 0: plane, 1: enemy
	Missile(Image img, int x, int y, int speed, int who, int angle) {
		this.type = img;
		this.x = x;
		this.y = y;

		this.angle = angle;

		this.speed = speed;
		this.who = who;
	}

	public void move() {
		if(this.who == 0){
			x += Math.cos(Math.toRadians(angle))*speed;
			y += Math.sin(Math.toRadians(angle))*speed;
		}
		else{
			x -= Math.cos(Math.toRadians(angle))*speed;
			y -= Math.sin(Math.toRadians(angle))*speed;
		}
	}
		
}
