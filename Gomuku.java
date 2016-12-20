import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Gomuku extends Jeu {
  
  public Gomuku(Joueur j1, Joueur j2, int taille){
    super(j1, j2, new Plateau(taille));
    HashMap<String, List<String>> h = new HashMap();
    List<String> l1 = new ArrayList();
    l1.add("black");
    h.put("black", l1);
    List<String> l2 = new ArrayList();
    l2.add("white");
    h.put("white",l2);
    this.plateau.setCodeCouleur(h);
  }
  
  public void jouer(){
    while(this.plateau.existeCasesLibres()){
      tour(this.joueur1, "black");
      if (this.plateau.existeCasesLibres()){
        tour(this.joueur2, "white");
      }
    }
  }
  
  private void tour(Joueur j, String couleur){
    int[] coordonnees = j.getCoup();
    try {
      this.plateau.pose(coordonnees[0], coordonnees[1], couleur); 
      int[] score = this.plateau.getAlign(coordonnees[0], coordonnees[1]);
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

}
