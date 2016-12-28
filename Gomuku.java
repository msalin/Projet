import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;

public class Gomuku extends Jeu{
	private boolean joueur1plays;
	public Gomuku(Joueur j1, Joueur j2, int taille){
	    super(j1, j2, new Plateau(taille));
	    HashMap<String, List<String>> h = new HashMap<String, List<String>>();
	    List<String> l1 = new ArrayList<String>();
	    l1.add(Couleur.BLACK);
	    h.put(Couleur.BLACK, l1);
	    List<String> l2 = new ArrayList<String>();
	    l2.add(Couleur.WHITE);
	    h.put(Couleur.WHITE,l2);
	    h.put("void", new ArrayList<String>());
	    this.plateau.setCodeCouleur(h);
	    
	    this.frame.setTitle("Gomuku");
	    this.scores.add(new JLabel(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+"   "+this.joueur2.nom+" : "+this.joueur2.getNbPoints()));
	    this.frame.setVisible(true);
	    this.joueur1plays = true;
	  }
	  
	  public Gomuku(Joueur j1, int taille){
		  this(j1, new JoueurIA(), taille);
	  }
	  
	  public Gomuku(int taille){
		  this(new JoueurIA(), new JoueurIA(), taille);
	  }
	  
	  public void jouer(){
	    while(this.plateau.existeCasesLibres()){
	      tour(this.joueur1, Couleur.BLACK);
	      this.affichePlateau();
	      if (this.plateau.existeCasesLibres()){
	        tour(this.joueur2, Couleur.WHITE);
	        this.affichePlateau();
	      }
	    }
	    if (joueur1.getNbPoints()>joueur2.getNbPoints()){
	    	System.out.println("Noir a gagné");
	    	afficheMessage("Blanc a gagné");
	    } else if (joueur2.getNbPoints()>joueur1.getNbPoints()){
	    	System.out.println("Blanc a gagné");
	    	afficheMessage("Blanc a gagné");
	    } else {
	    	System.out.println("Egalité");
	    	afficheMessage("Blanc a gagné");
	    }
	  }
	  
	  public void launch(){
		  if (this.joueur1plays && this.joueur1 instanceof JoueurIA){
			  tour(this.joueur1, Couleur.BLACK);
			  this.joueur1plays = false;
		  } else if (!this.joueur1plays && this.joueur2 instanceof JoueurIA){
			  tour(this.joueur2, Couleur.WHITE);
			  this.joueur1plays = true;
		  }
	  }
	  
	  
	  
	  private void tour(Joueur j, String couleur){
	    Case coordonnees = j.getCoup(this.plateau.getCasesLibres());
	    try {
	      this.plateau.pose(coordonnees.i, coordonnees.j, couleur); 
	      int[] score = this.plateau.getAlign(coordonnees.i, coordonnees.j);
	      for (int i=0; i<score.length; i++){
	       if (score[i]>=5){
	        j.addScore(1); 
	       }
	      }
	      JLabel text = (JLabel) this.scores.getComponent(0);
	      this.scores.remove(text);
	      text = new JLabel(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+"   "+this.joueur2.nom+" : "+this.joueur2.getNbPoints());
	      this.scores.add(text);
	    } catch (CaseOccupeeException e){
	      System.out.println(e);
	      tour(j, couleur);
	    }
	  }
	  
	  private boolean poser(Joueur joueur, String couleur, int i, int j){
		  try{
			  this.plateau.pose(i, j, couleur); 
			  int[] score = this.plateau.getAlign(i, j);
			  for (int n=0; n<score.length; n++){
				  if (score[n]>=5){
					  joueur.addScore(1); 
				  }
			  }
			  JLabel text = (JLabel) this.scores.getComponent(0);
			  this.scores.remove(text);
			  text = new JLabel(this.joueur1.nom+" : "+this.joueur1.getNbPoints()+"   "+this.joueur2.nom+" : "+this.joueur2.getNbPoints());
			  this.scores.add(text);
			  return true;
		  } catch (CaseOccupeeException e){
			  System.out.println(e);
			  afficheMessage(e.toString());
			  return false;
		  }
	  }
	  
	  private void afficheFin(){
		  if (joueur1.getNbPoints()>joueur2.getNbPoints()){
		    	System.out.println("Noir a gagné");
		    	afficheMessage("Noir a gagné");
		    } else if (joueur2.getNbPoints()>joueur1.getNbPoints()){
		    	System.out.println("Blanc a gagné");
		    	afficheMessage("Blanc a gagné");
		    } else {
		    	System.out.println("Egalité");
		    	afficheMessage("Egalité");
		    }
	  }
	  
	  
	  public void actionAFaire(Case c){
		System.out.println(c.toString());
		if (! this.fini){
			if (this.joueur1plays){
				if (poser(this.joueur1, Couleur.BLACK, c.i, c.j)) {
					this.frame.setVisible(true);
					System.out.println(c.getCouleur());
					if (! this.plateau.existeCasesLibres()){
						afficheFin();
						this.fini = true;
					} else if (this.joueur2 instanceof JoueurIA){
						Case coordonnees = this.joueur2.getCoup(this.plateau.getCasesLibres());
						poser(this.joueur2, Couleur.WHITE, coordonnees.i, coordonnees.j);
						this.frame.setVisible(true);
						System.out.println(coordonnees.toString());
						System.out.println(coordonnees.getCouleur());
						if (! this.plateau.existeCasesLibres()){
							afficheFin();
							this.fini = true;
						}
					} else {
						this.joueur1plays = false;
					}
				}
			} else {
				if (poser(this.joueur2, Couleur.WHITE, c.i, c.j)){
					this.frame.setVisible(true);
					this.joueur1plays = true;
					if (! this.plateau.existeCasesLibres()){
						afficheFin();
						this.fini = true;
					}
				}
			}
		}
	  }
	  
	  public void affichePlateau(){
		System.out.printf("%s : %d alignements      %s : %d alignements\n", this.joueur1.nom, this.joueur1.getNbPoints(), this.joueur2.nom, this.joueur2.getNbPoints());
		this.plateau.afficherPlateau();
	  }
	  

		@Override
		public List<Case> tourOrdinateur(int nbCases) {
			// TODO Auto-generated method stub
			return null;
		}
		
	  public static void main(String[] args){
		  Gomuku g = new Gomuku(new JoueurHumain("Toto"),10);
		  
	  }

	@Override
	public void calculScore(Case c) {
		// TODO Auto-generated method stub
		
	}

}
