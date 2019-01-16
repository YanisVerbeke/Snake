void mange() {
  if (xP==serpent[0][0] && yP==serpent[0][1]) {
    tailleSerpent++;
    xP=int(random(2, 38))*10+5; 
    yP=int(random(2, 38))*10+5;
  }
}

