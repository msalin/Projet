import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;
public class KolorLines extends Jeu {
  
  private List<String> prochainsCoups;
  private Plateau.Case pionSelectionne;
  private List<Plateau.Case> casesDepart;
  
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
    h.put("", new ArrayList<String>());
    this.plateau.setCodeCouleur(h);
    this.prochainsCoups = this.creerProchainsCoups(taille*taille);
    
    this.frame.setTitle("KolorLines");
    this.scores.add(new JLabel(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+" points"));
    this.frame.setVisible(true);
    
  }
  
  private List<String> creerProchainsCoups(int nbCases){
	  List<String> pc= new ArrayList<String>();
	  Random r = new Random();
	  for (int i=0; i<nbCases; i++){
		  int n = r.nextInt(100);
		  if (n<5){
			  pc.add("rainbow");
		  } else if (n<24){
			  pc.add("black");
		  } else if (n<43){
			  pc.add("white");
		  } else if (n<62){
			  pc.add("blue");
		  } else if (n<81){
			  pc.add("red");
		  } else {
			  pc.add("yellow");
		  }
	  }
	  return pc;
  }
  
  public void jouer(){
	  
	  while (this.plateau.existeCasesLibres()){
		  int nbCases = this.plateau.getNbCasesLibres();
		  if (this.prochainsCoups.size()<=3){
			  this.prochainsCoups.addAll(this.creerProchainsCoups(nbCases));
		  }
		  int nbCoups = 3;
		  if (nbCases<3){
			  nbCoups = nbCases;
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
	  List<List<Plateau.Case>> casesVisitees = new ArrayList<List<Plateau.Case>>();
	  String[] couleurs = new String[8];
	  int[] score = this.plateau.getAlign(c.i, c.j, "rainbow", couleurs, casesVisitees);
	  /**
	  for (int i=0; i<score.length; i++){
		  if (score[i]>=5){
			  this.joueur1.addScore(score[i]);
			  for (Plateau.Case caseSuppr : casesVisitees.get(i)){
				  this.plateau.libere(caseSuppr.i, caseSuppr.j);
			  }
		  }
	  }
	  **/
	  for (int i=0; i<8; i++){
		  System.out.println(couleurs[i]+" "+score[i]);
	  }
	  for (int n=0; n<8; n+=2){
			if (couleurs[n].equals(couleurs[n+1]) || couleurs[n].equals("rainbow") || couleurs[n+1].equals("rainbow")){
				int s = score[n]+score[n+1]-1;
				if (s >=5){
					this.joueur1.addScore(s);
					for (Plateau.Case caseSuppr : casesVisitees.get(n)){
						  this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
					for (Plateau.Case caseSuppr : casesVisitees.get(n+1)){
						  this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
				}	
			} 
	  }
	  JLabel text = (JLabel) this.scores.getComponent(0);
      this.scores.remove(text);
      this.scores.add(new JLabel(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+" points"));
  }
  
  public void launch(){
	  this.casesDepart = tourOrdinateur(3);
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
			  this.frame.setVisible(true);
		  } catch (CaseOccupeeException e){
			  System.out.println(e);
			  afficheMessage(e.toString());
		  }
	  }
	  return cases;
  }
  
  private boolean canStart(){
	  if (! this.plateau.existeCasesLibres()){
		  return false;
	  }
	  boolean libre = false;
	  for (Plateau.Case c : this.casesDepart){
		  if (this.plateau.existeCaseLibreAutour(c.i, c.j)){
			  libre = true;
		  }
	  }
	  return libre;
  }
  
  public synchronized void actionAFaire(Plateau.Case c){
	  if (! this.fini){
		  if (this.pionSelectionne == null){
				  this.pionSelectionne = c;
		  } else {
			  try {
				  this.plateau.deplace(this.pionSelectionne.i, this.pionSelectionne.j, c.i, c.j);
				  this.frame.setVisible(true);
				  this.casesDepart.remove(this.pionSelectionne);
				  this.casesDepart.add(c);
				  for (Plateau.Case pion : casesDepart){
					  calculScore(pion);
				  }
				  int nbCases = this.plateau.getNbCasesLibres();
				  if (this.prochainsCoups.size()<=3){
					  this.prochainsCoups.addAll(this.creerProchainsCoups(nbCases));
				  }
				  int nbCoups = 3;
				  if (nbCases<3){
					  nbCoups = nbCases;
				  }
				  this.casesDepart = tourOrdinateur(nbCoups);
				  this.pionSelectionne = null;
				  if (!this.canStart()){
					  this.fini = true;
					  afficheMessage("La partie est finie");
				  }
			  } catch (PasDeCheminException e){
				  System.out.println(e);
				  this.pionSelectionne = null;
				  this.afficheMessage(e.toString());
			  } catch (CaseOccupeeException e){
				  System.out.println(e);
				  this.pionSelectionne = null;
				  this.afficheMessage(e.toString());
			  } catch (CaseVideException e){
				  System.out.println(e);
				  this.pionSelectionne = null;
				  this.afficheMessage(e.toString());
				  
			  }
		  }
	  }
  }
  
  public static void main(String[] args){
	  KolorLines kl = new KolorLines(new JoueurHumain("Toto"), 10);
	  kl.launch();
  }

}
