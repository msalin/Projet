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

}
