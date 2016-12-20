public abstract class Joueur {
  public final String nom;
  private int nbPointsCourants;
  
  public Joueur(String nom){
    this.nom = nom;
    this.nbPointsCourants = 0;
  }
  
  public void reset(){
    this.nbPointsCourants = 0;
  }
  
  public int getNbPoints(){
    return this.nbPointsCourants;
  }
  
  public abstract int[] getCoup();
}
