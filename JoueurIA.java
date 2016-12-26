import java.util.Random;
import java.util.List;
public class JoueurIA extends Joueur{

  public JoueurIA(){
    super("Ordinateur");
  }
  
  public Case getCoup(List<Case> coupsPossibles){
	  Random r = new Random();
	  int index = r.nextInt(coupsPossibles.size());
	  Case c = coupsPossibles.get(index);
	  return c;
  }

}
