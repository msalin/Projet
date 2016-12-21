import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gomuku extends Jeu{
	public Gomuku(Joueur j1, Joueur j2, int taille){
	    super(j1, j2, new Plateau(taille));
	    HashMap<String, List<String>> h = new HashMap<String, List<String>>();
	    List<String> l1 = new ArrayList<String>();
	    l1.add("black");
	    h.put("black", l1);
	    List<String> l2 = new ArrayList<String>();
	    l2.add("white");
	    h.put("white",l2);
	    this.plateau.setCodeCouleur(h);
	  }
	  
	  public Gomuku(Joueur j1, int taille){
		  this(j1, new JoueurIA(), taille);
	  }
	  
	  public Gomuku(int taille){
		  this(new JoueurIA(), new JoueurIA(), taille);
	  }
	  
	  public void jouer(){
	    while(this.plateau.existeCasesLibres()){
	      tour(this.joueur1, "black");
	      this.affichePlateau();
	      if (this.plateau.existeCasesLibres()){
	        tour(this.joueur2, "white");
	        this.affichePlateau();
	      }
	    }
	    if (joueur1.getNbPoints()>joueur2.getNbPoints()){
	    	System.out.println("Noir a gagné");
	    } else if (joueur2.getNbPoints()>joueur1.getNbPoints()){
	    	System.out.println("Blanc a gagné");
	    } else {
	    	System.out.println("Egalité");
	    }
	  }
	  
	  private void tour(Joueur j, String couleur){
	    Plateau.Case coordonnees = j.getCoup(this.plateau.getCasesLibres());
	    try {
	      this.plateau.pose(coordonnees.i, coordonnees.j, couleur); 
	      int[] score = this.plateau.getAlign(coordonnees.i, coordonnees.j);
	      for (int i=0; i<score.length; i++){
	       if (score[i]>=5){
	        j.addScore(1); 
	       }
	      }
	    } catch (CaseOccupeeException e){
	      System.out.println(e);
	      tour(j, couleur);
	    }
	  }
	  
	  public void affichePlateau(){
		System.out.printf("%s : %d alignements      %s : %d alignements\n", this.joueur1.nom, this.joueur1.getNbPoints(), this.joueur2.nom, this.joueur2.getNbPoints());
		this.plateau.afficherPlateau();
	  }
	  
	  public static void main(String[] args){
		  Gomuku g = new Gomuku(10);
		  g.jouer();
	  }
}
