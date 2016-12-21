import java.util.List;
public abstract class Joueur {
  public final String nom;
  private int nbPointsCourants;
  protected List<Plateau.Case> coupsPossibles;
  
  public Joueur(String nom){
    this.nom = nom;
    this.nbPointsCourants = 0;
  }
  
  public void setCoupsPossibles(List<Plateau.Case> cp){
	  this.coupsPossibles = cp;
  }
  public void reset(){
    this.nbPointsCourants = 0;
  }
  
  public void addScore(int i){
	  this.nbPointsCourants += i;
  }
  
  public int getNbPoints(){
    return this.nbPointsCourants;
  }
  
  public abstract int[] getCoup();
}
