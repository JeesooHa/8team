#Get start the Project for OSS
###8team - Shooting Game


##Project Name

Air Force 2017

##Game Story

Long Long time ago in a galaxy far, far away...

There is an Anxiety factor in the Galactic senate, thousands of solar systems are declaring the passing of a republic.

The things make Earth Oss Knights which is small and limited size, and the rebel army take place for this weakness.

Mr.shin who catched up Mutation Galaxy DNA and his rebel force do a ruthless attack. 

To prevent this devastated situation, the movement of the air force has begun.

##Member && Role

Manager:

[Jeesoo Ha](https://jeesooha.github.io/) (Department of Robotics 13)

Translater:

[Daehan Kim](http://KimDaehan.github.io) (Department of Newspaper Broadcasting 09)

Designer :

[Woojin Jo](https://woojin-jo.github.io/) (Department of Economics 12)

Developer:

[Soonguen Hwang](http://zoowx321.github.io/) (Department of Robotics 13)

Documentator : 

[Joohee Lim](http://jssngg.github.io) (Computer Science 16)


##Introduction

This game is projected by "Team8" and name is "Air force 2017"
The game has Three enemies and main jet should defeat them.
Every playing time get new 3 HP points and The game will finished if all that points are burned out.
You can use this source wherever you want. Just give us a comment please.
 

##Description

This project is start to Practice OpenSource Software.

We did these all things as a Desktop open source team. 

The main project term was from september 20th to December 20th.

 
##Language

JAVA



##License

MIT



##Environment

* Windows

* Eclipse

* jre 1.8.0_74



# How to Start 

##Download

clone [this project](https://github.com/JeesooHa/8team.git) to your computer.



##Run

jre is too big. so I delete it.


Please, download java before start game.


And don't move .jar file to another folder or directory.


###Windows

click the start.jar file.


###Linux

~ (8team directory path)$ java -jar start.jar 




##Key

* Move - Arrow keys

* Shoot - Space bar

* Ultimate Skill - Q (every enemy and missile disappeared)

* Skip opening - S

* Select plane - 1 or 2


##Execute Image

### Game Start

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result1.JPG" height = "400">



### Select Character

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result2.JPG" height = "400">



### Start Stage

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result3.JPG" height = "400">



### Stage 1 Boss

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result4.JPG" height = "400">


### Stage 2 Boss

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result5.JPG" height = "400">


### Stage 3 Boss

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result6.JPG" height = "400">



### Game Over

<img src ="https://github.com/JeesooHa/8team/blob/master/images/result_img/result7.JPG" height = "400">



## Code

###game.java - main

* Event from using keyboard
 
* Use Plane_img.png, plane_img2.png 

* Use missile_img.png

* Shooting missile use spacebar 

* Add enemies image - Enemy disappeared by own missiles

* Add enemies missile

* Add Background music and sound effect

* Add stage and stage clear message

* Add stage 1 boss,  stage 2 boss,  stage 3 boss

* Add opening like starwars

* Add plane select stage


###Enemy.java - enemy class
 - enemy position, speed
 - enemy type(1 = enemy1, 2 = enemy2, 3 = enemy3, 4 = stage 1 boss, 5 = stage 2 boss, 6 = stage 3 boss) 
 - enemy move

###Explosion.java
 - explosion position
 - explosion damage
 
###Missile.java
 - missile position, angle, speed
 - enemy owner
 - enemy move


## What we did after Midterm

1. Apeear randomly of enemies. (done)

2. How to end the game - All the HP points burned out (done)

3. Adding Boss monster - one more image  (done)

4. stage 2 - Background : Image which has long length of width (done) 

5. Ultimate skill -> Remove all bullets of enemies (done) 

6. Adding two more characters which user can select (done)

7. Storytelling like starwars (done)

8. Restart after Gameover (done)








##Reference
>[link_to_source_code_reference](http://blog.naver.com/dosem321/40170781167#)
