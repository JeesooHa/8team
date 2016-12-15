/*
=====================
:mod:'Explosion' 모듈
===================== 
  
.. moduleauthor:: 하지수 <jsha2913@gmail.com>
  
  
설명
====
 
 플레이어와 적들의 미사일 폭발 이벤트를 위한 클래스 정의


참고
====

 관련링크:
 * http://blog.naver.com/dosem321/40171029559
 
 
관련 작업자
======== 

 * 하지수 (Jeesoo Ha)
 * 황순근 (Soonguen Hwang)

작업일지
--------

 * 2016.09.12 Jeesoo : 폭발 클래스 구현 

 */




public class Explosion 
{
	int x,y;
	int ex_cnt;
	int damage; 

	Explosion(int x, int y, int damage)
	{
		this.x = x;
		this.y = y;
		this.damage = damage;
	}
	
	public void effect()
	{
		ex_cnt ++;
	}
}
