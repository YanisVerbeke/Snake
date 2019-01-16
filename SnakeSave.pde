//Jeu Snake avec sauvegarde Yanis V. 24/03/17 ISN Première
//Déclaration des variables
float[][] serpent;
int tailleSerpent, dX, dY, xP, yP;
boolean New=false, Fin=false;          //New sert à savoir si une partie est en cours, Fin sert à savoir si le joueur a perdu
PrintWriter sauvegarde;
String[] charge = new String[144405];    //Fichier de sauvegarde, 14405 etant le nombre maximale de caractère que le fichier peut avoir sur une grille de 380, 380 (surface jouable)

void setup() {
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

void draw() {
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
  }                                                                                   // ▼ Il ne faut pas que le fichier de sauvegarde soit vide
  if (dist(mouseX, mouseY, width/4*3-50, height/3*2+10)<=75 && mousePressed && !New && charge.length!=0) {  //Charger
    tailleSerpent=int(charge[0]);
    for (int j=0; j<tailleSerpent+1; j++) {
      serpent[j][0]=float(charge[j+1]);
    }
    for (int j=0; j<tailleSerpent+1; j++) {
      serpent[j][1]=float(charge[j+1+tailleSerpent]);
    }
    xP=int(charge[(tailleSerpent*2+1)]);
    yP=int(charge[(tailleSerpent*2+2)]);
    dX=int(charge[(tailleSerpent*2+3)]);
    dY=int(charge[(tailleSerpent*2+4)]);
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
        sauvegarde.println(serpent[i][0]);            //Je préfère sauvegarder les x à la suite puis les y, plus simple à charger ensuite
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

void keyPressed() {
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
    New=true;        //Sans ces booleens, le menu plante après avoir perdu
    Fin=false;       //
    nouvelle();
  }
}

