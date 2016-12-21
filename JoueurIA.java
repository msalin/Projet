import java.util.Random;
import java.util.List;
public class JoueurIA extends Joueur{

  public JoueurIA(){
    super("Ordinateur");
  }
  
  public Plateau.Case getCoup(List<Plateau.Case> coupsPossibles){
	  Random r = new Random();
	  int index = r.nextInt(coupsPossibles.size());
	  Plateau.Case c = coupsPossibles.get(index);
	  return c;
  }

}
