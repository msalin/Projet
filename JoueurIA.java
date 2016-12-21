import java.util.Random;
public class JoueurIA extends Joueur{

  public JoueurIA(){
    super("Ordinateur");
  }
  
  public int[] getCoup(){
	  Random r = new Random();
	  int index = r.nextInt(this.coupsPossibles.size());
	  Plateau.Case c = this.coupsPossibles.get(index);
	  return new int[] {c.i, c.j};
  }

}

