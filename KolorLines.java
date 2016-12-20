import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class KolorLines extends Jeu {
  
  private List<String> prochainsCoups;
  
  public KolorLines(Joueur j1, int taille){
    super(j1, new JoueurIA(), new Plateau(taille));
    HashMap<String, List<String>> h = new HashMap();
    String[] couleurs = new String[] {"black", "white", "blue", "red", "yellow"}
    for(int i=0; i<5; i++){
      List<String> l = new ArrayList();
      l.add(couleurs[i]);
      l.add("rainbow");
      h.put(couleurs[i], l);
    }
    this.plateau.setCodeCouleur(h);

}
