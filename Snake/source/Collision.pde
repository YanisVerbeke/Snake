void collision() {
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

