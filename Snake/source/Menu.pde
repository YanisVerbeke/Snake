color c1, c2;
void menu() {
  if (dist(mouseX, mouseY, width/4, height/3)<=75) {      //Passer la souris sur un bouton change la couleur
    c1=(#C6C6C6);
  } else c1=#FFFFFF;
  if (charge.length==0) {      //Si il n'y a aucune sauvegarde, on ne peut pas appuyer sur "Charger"
    c2=#7E7E7E;
  } else {
    if (dist(mouseX, mouseY, width/4*3, height/3*2)<=75) {
      c2=#C6C6C6;
    } else c2=#FFFFFF;
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

