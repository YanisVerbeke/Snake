void nouvelle() {      //Nouvelle partie
  sauvegarde = createWriter("Save.txt");
  tailleSerpent=5;
  dX=10;
  dY=0;
  for (int i=0; i<tailleSerpent; i++) {
    serpent[i][0]=90-(i*10)+5;
    serpent[i][1]=95;
  }
  xP=int(random(2, 38))*10+5; 
  yP=int(random(2, 38))*10+5;
}

