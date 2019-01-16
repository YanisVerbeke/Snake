import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SnakeSave extends PApplet {

//Jeu Snake avec sauvegarde Yanis V. 24/03/17 ISN Premi\u00e8re
//D\u00e9claration des variables
float[][] serpent;
int tailleSerpent, dX, dY, xP, yP;
boolean New=false, Fin=false;          //New sert \u00e0 savoir si une partie est en cours, Fin sert \u00e0 savoir si le joueur a perdu
PrintWriter sauvegarde;
String[] charge = new String[144405];    //Fichier de sauvegarde, 14405 etant le nombre maximale de caract\u00e8re que le fichier peut avoir sur une grille de 380, 380 (surface jouable)

public void setup() {
  size(400, 400);
  smooth();
  tailleSerpent=5;
  dX=10;
  dY=0;
  serpent=new float[1600][2];
  for (int i=0; i<tailleSerpent; i++) {
    serpent[i][0]=90-(i*10)+5;
    serpent[i][1]=95;
  }
  charge=loadStrings("Save.txt");
}

public void draw() {
  menu();
  if (dist(mouseX, mouseY, width/4, height/3)<=75 && mousePressed && !New) {   //Nouvelle partie
    New=true;
    nouvelle();
  }
  if (New && !Fin) {
    affiche();
    if (frameCount%10==0) {
      deplacement();
      mange();
      collision();                                                               //
    }
  }                                                                                   // \u25bc Il ne faut pas que le fichier de sauvegarde soit vide
  if (dist(mouseX, mouseY, width/4*3-50, height/3*2+10)<=75 && mousePressed && !New && charge.length!=0) {  //Charger
    tailleSerpent=PApplet.parseInt(charge[0]);
    for (int j=0; j<tailleSerpent+1; j++) {
      serpent[j][0]=PApplet.parseFloat(charge[j+1]);
    }
    for (int j=0; j<tailleSerpent+1; j++) {
      serpent[j][1]=PApplet.parseFloat(charge[j+1+tailleSerpent]);
    }
    xP=PApplet.parseInt(charge[(tailleSerpent*2+1)]);
    yP=PApplet.parseInt(charge[(tailleSerpent*2+2)]);
    dX=PApplet.parseInt(charge[(tailleSerpent*2+3)]);
    dY=PApplet.parseInt(charge[(tailleSerpent*2+4)]);
    New=true;
  }                                                                                 //
  if (keyPressed) {                                   //Quitter
    switch(key) {
    case 'q' : 
      exit();
      break;
    case 's' :
      sauvegarde = createWriter("Save.txt");
      sauvegarde.println(tailleSerpent);
      for (int i=0; i<tailleSerpent; i++) {           //
        sauvegarde.println(serpent[i][0]);            //Je pr\u00e9f\u00e8re sauvegarder les x \u00e0 la suite puis les y, plus simple \u00e0 charger ensuite
      }                                               //
      for (int i=0; i<tailleSerpent; i++) {           //
        sauvegarde.println(serpent[i][1]);            //
      }
      sauvegarde.println(xP);  
      sauvegarde.println(yP);
      sauvegarde.println(dX);
      sauvegarde.println(dY);
      sauvegarde.flush();
      sauvegarde.close();
      exit();
      break;
    }
  }                                                    //
}

public void keyPressed() {
  if (key==CODED) {
    if (keyCode==UP && dY!=10) {
      dX=0; 
      dY=-10;
    }
    if (keyCode==DOWN && dY!=-10) {
      dX=0; 
      dY=10;
    }
    if (keyCode==LEFT && dX!=10) {
      dX=-10; 
      dY=0;
    }
    if (keyCode==RIGHT && dX!=-10) {
      dX=10; 
      dY=0;
    }
  }
  if (Fin) {
    loop();
    New=true;        //Sans ces booleens, le menu plante apr\u00e8s avoir perdu
    Fin=false;       //
    nouvelle();
  }
}

public void affiche() {
  background(0);
  fill(255);
  noStroke();
  rectMode(CENTER);
  rect(width/2, height/2, 380, 380);
  fill(255, 0, 0);
  ellipse(xP, yP, 7, 7);
  for (int i=0; i<tailleSerpent; i++) {
    fill(0, 200, 0);
    rect(serpent[i][0], serpent[i][1], 8, 8);
  }
  fill(255, 0, 0);
  textSize(10);
  text("Appuyez sur q pour quitter sans sauvegarder.", 20, 20);
  text("Appuyez sur s pour quitter et sauvegarder.", 20, 30);
}

public void collision() {
  if (serpent[0][0]<10 || serpent[0][0]>width-10 || serpent[0][1]<10 || serpent[0][1]>height-10) {
    textSize(20);
    fill(255, 0, 0);
    text("GAME OVER", width/4, height/2);
    text("Appuyez sur une touche pour rejouer", width/4-80, height/2+50);
    Fin=true;
    New=false;
    noLoop();
  }  
  for (int i=1; i<tailleSerpent; i++) {
    if (serpent[0][0]==serpent[i][0] && serpent[0][1]==serpent[i][1]) {
      textSize(20);
      fill(255, 0, 0);
      text("GAME OVER", width/4, height/2);
      text("Appuyez sur une touche pour rejouer", width/4-80, height/2+50);
      Fin=true;
      New=false;
      noLoop();
    }
  }
}

public void deplacement() {
  for (int i=tailleSerpent-1; i>=0; i--) {
    serpent[i+1][0]=serpent[i][0];
    serpent[i+1][1]=serpent[i][1];
  }
  serpent[0][0]=serpent[0][0]+dX;
  serpent[0][1]=serpent[0][1]+dY;
}

public void mange() {
  if (xP==serpent[0][0] && yP==serpent[0][1]) {
    tailleSerpent++;
    xP=PApplet.parseInt(random(2, 38))*10+5; 
    yP=PApplet.parseInt(random(2, 38))*10+5;
  }
}

int c1, c2;
public void menu() {
  if (dist(mouseX, mouseY, width/4, height/3)<=75) {      //Passer la souris sur un bouton change la couleur
    c1=(0xffC6C6C6);
  } else c1=0xffFFFFFF;
  if (charge.length==0) {      //Si il n'y a aucune sauvegarde, on ne peut pas appuyer sur "Charger"
    c2=0xff7E7E7E;
  } else {
    if (dist(mouseX, mouseY, width/4*3, height/3*2)<=75) {
      c2=0xffC6C6C6;
    } else c2=0xffFFFFFF;
  }
  background(0);
  fill(c1);
  ellipse(width/4, height/3, 150, 150);
  fill(c2);
  ellipse(width/4*3, height/3*2, 150, 150);
  fill(50, 200, 40);
  textSize(20);
  text("NOUVELLE PARTIE", width/4-50, height/3-15, 150, 100);
  text("CHARGER", width/4*3-50, height/3*2+10);
}

public void nouvelle() {      //Nouvelle partie
  sauvegarde = createWriter("Save.txt");
  tailleSerpent=5;
  dX=10;
  dY=0;
  for (int i=0; i<tailleSerpent; i++) {
    serpent[i][0]=90-(i*10)+5;
    serpent[i][1]=95;
  }
  xP=PApplet.parseInt(random(2, 38))*10+5; 
  yP=PApplet.parseInt(random(2, 38))*10+5;
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SnakeSave" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
