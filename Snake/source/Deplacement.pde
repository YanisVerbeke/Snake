void deplacement() {
  for (int i=tailleSerpent-1; i>=0; i--) {
    serpent[i+1][0]=serpent[i][0];
    serpent[i+1][1]=serpent[i][1];
  }
  serpent[0][0]=serpent[0][0]+dX;
  serpent[0][1]=serpent[0][1]+dY;
}

