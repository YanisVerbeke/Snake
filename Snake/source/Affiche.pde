void affiche() {
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

