public abstract class Jeu {
  protected Joueur joueur1;
  protected Joueur joueur2;
  protected Plateau plateau;
  
  public Jeu(Joueur j1, Joueur j2, Plateau p){
    this.joueur1 = j1;
    this.joueur1.reset();
    this.joueur2 = j2;
    this.joueur2.reset();
    this.plateau = p;
  }
  
  //Abstract ?
  public static void afficheRegles(){
  }
  
  public abstract void jouer();
}

