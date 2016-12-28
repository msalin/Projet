import java.util.List;
public abstract class Joueur {
  public final String nom;
  private transient int nbPointsCourants;
  
  public Joueur(String nom){
    this.nom = nom;
    this.nbPointsCourants = 0;
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
  
  public abstract Case getCoup(List<Case> coupsPossibles);
}
