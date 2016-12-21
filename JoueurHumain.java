import java.util.List;
import java.util.Scanner;
public class JoueurHumain extends Joueur {
  
  public JoueurHumain(String nom){
    super(nom);
  }
  
  public Plateau.Case getCoup(List<Plateau.Case> coupsPossibles){
	  int index = 0;
	  for (Plateau.Case c : coupsPossibles){
		  System.out.print(index+" "+c+" ");
		  index++;
	  }
	  System.out.println("\nEntrez l'indice de la case:");
	  Scanner sc = new Scanner(System.in);
	  int indice = sc.nextInt();
	  while (indice >coupsPossibles.size() || indice <0){
		  System.out.println("\nEntrez l'indice de la case:");
		  indice = sc.nextInt();
	  }
	  return coupsPossibles.get(indice);
	  
  }
}