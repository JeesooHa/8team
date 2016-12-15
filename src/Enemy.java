/*
=====================
:mod:'Enemy' 모듈
===================== 
  
.. moduleauthor:: 하지수 <jsha2913@gmail.com>
  
  
설명
====
 
 적을 생성하는 클래스
 
 type 변수의 값이 1~3인 경우 각 종류의 일반 적이 생성되고,
 변수의 값이 4~6인 경우에는 각 stage별 보스가 생성된다.


참고
====

 관련링크:
 * http://blog.naver.com/dosem321/40171017707
 
 
관련 작업자
======== 

 * 하지수 (Jeesoo Ha)
 * 황순근 (Soonguen Hwang)

작업일지
--------

 * 2016.09.09 Jeesoo : 적 클래스 구현 

*/
public class Enemy 
{
	int x,y,speed;
	int type;
	
	Enemy(int x, int y, int speed,int type)
	{
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.type = type;
	}
	
	public void move()
	{
		if(this.type < 4)
			x -= speed;
		else
			if(x>900)	x -= speed;
	}
}
