import java.util.List;
public abstract class Joueur {
  public final String nom;
  private transient int nbPointsCourants;
  
  public Joueur(String nom){
    this.nom = nom;
    this.nbPointsCourants = 0;
  }
  
	/**
	* Remet le nombre de points courant de ce Joueur à 0
	**/
  public void reset(){
    this.nbPointsCourants = 0;
  }
  
	/**
	* Ajoute i points aux points de ce Joueur
	* @param i le nombre de points à ajouter
	**/
  public void addScore(int i){
	  this.nbPointsCourants += i;
  }
  
	/**
	* Renvoie le nombre de points de ce Joueur
	* @return le score de ce Joueur
	**/
  public int getNbPoints(){
    return this.nbPointsCourants;
  }
  
  public abstract Case getCoup(List<Case> coupsPossibles);
}
