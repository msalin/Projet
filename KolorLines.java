import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class KolorLines extends Jeu {
  
  private List<String> prochainsCoups;
  
  public KolorLines(Joueur j1, int taille){
    super(j1, new JoueurIA(), new Plateau(taille));
    HashMap<String, List<String>> h = new HashMap<String, List<String>>();
    String[] couleurs = new String[] {"black", "white", "blue", "red", "yellow"};
    List<String> lrainbow = new ArrayList<String>();
    lrainbow.add("rainbow");
    for(int i=0; i<5; i++){
      List<String> l = new ArrayList<String>();
      l.add(couleurs[i]);
      l.add("rainbow");
      h.put(couleurs[i], l);
      lrainbow.add(couleurs[i]);
    }
    h.put("rainbow", lrainbow);
    this.plateau.setCodeCouleur(h);
    this.setProchainsCoups(taille*taille);
    
    this.frame.setTitle("KolorLines");
    this.scores.setName(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+" pts");
    this.frame.setVisible(true);
    
  }
  
  private void setProchainsCoups(int nbCases){
	  this.prochainsCoups = new ArrayList<String>();
	  Random r = new Random();
	  for (int i=0; i<nbCases; i++){
		  int n = r.nextInt(100);
		  if (n<5){
			  this.prochainsCoups.add("rainbow");
		  } else if (n<24){
			  this.prochainsCoups.add("black");
		  } else if (n<43){
			  this.prochainsCoups.add("white");
		  } else if (n<62){
			  this.prochainsCoups.add("blue");
		  } else if (n<81){
			  this.prochainsCoups.add("red");
		  } else {
			  this.prochainsCoups.add("yellow");
		  }
	  }
  }
  
  public void jouer(){
	  
	  while (this.plateau.existeCasesLibres()){
		  int nbCoups = 0;
		  if (this.prochainsCoups.size()>=3){
			  nbCoups = 3;
		  } else if (this.prochainsCoups.size()==2){
			  nbCoups = 2;
		  } else {
			  nbCoups = 1;
		  }
		  List<Plateau.Case> cases = tourOrdinateur(nbCoups);
		  afficherPlateau();
		  if (this.plateau.existeCasesLibres()){
			  cases = tourHumain(cases);
		  } else {
			  boolean libre = false;
			  for (Plateau.Case c : cases){
				  if (this.plateau.existeCaseLibreAutour(c.i, c.j)){
					  libre = true;
				  }
			  }
			  if (libre){
				  cases = tourHumain(cases);
			  }
		  }
		  for (Plateau.Case c : cases){
			  calculScore(c);
		  }
		  afficherPlateau();
		  
	  }
  }
  
  public void calculScore(Plateau.Case c){
	  int[] score = this.plateau.getAlign(c.i, c.j, "rainbow");
	  for (int i=0; i<score.length; i++){
		  if (score[i]>=5){
			  this.joueur1.addScore(score[i]);
		  }
	  }
  }
  
  public void afficherPlateau(){
	  System.out.println(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+" points");
	  this.plateau.afficherPlateau();
  }
  
  public List<Plateau.Case> tourHumain(List<Plateau.Case> casesDepart){
	  Plateau.Case depart = joueur1.getCoup(casesDepart);
	  System.out.println(this.plateau.getCasesLibres().size());
	  Plateau.Case arrivee = joueur1.getCoup(this.plateau.getCasesLibres());
	  try {
		  this.plateau.deplace(depart.i, depart.j, arrivee.i, arrivee.j);
		  casesDepart.remove(depart);
		  casesDepart.add(arrivee);
		  return casesDepart;
	  } catch (PasDeCheminException e){
		  System.out.println(e);
		  return tourHumain(casesDepart);
	  } catch (CaseOccupeeException e){
		  System.out.println(e);
		  return tourHumain(casesDepart);
	  } catch (CaseVideException e){
		  System.out.println(e);
		  return tourHumain(casesDepart);
	  }
  }
  
  public List<Plateau.Case> tourOrdinateur(int nbCases){
	  List<Plateau.Case> cases = new ArrayList<Plateau.Case>(nbCases);
	  for (int i=0; i<nbCases; i++){
		  String couleur = this.prochainsCoups.get(0);
		  this.prochainsCoups.remove(0);
		  Plateau.Case c = this.joueur2.getCoup(this.plateau.getCasesLibres());
		  cases.add(c);
		  try {
			  this.plateau.pose(c.i, c.j, couleur);
		  } catch (CaseOccupeeException e){
			  System.out.println(e);
		  }
	  }
	  return cases;
  }
  
  public void actionAFaire(Plateau.Case c){
	  
  }
  
  public static void main(String[] args){
	  KolorLines kl = new KolorLines(new JoueurHumain("Toto"), 3);
	  try{
		  kl.plateau.pose(0, 0, "red");
		  kl.plateau.pose(0, 1, "yellow");
	  } catch (CaseOccupeeException e){
		  
	  }
  }

}
