import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

import java.awt.GridLayout;
import java.awt.event.*;
public class Plateau {

	final Case[][] taableauCases;
	public Case pionSelectionne;
	public HashMap<String, List<String>> codeCouleur;
	private List<Case> casesLibres;
	public PlateauJPanel panneau;
	private Jeu jeu;
	public int size;
	protected String couleurSelectionnee;
	
	public Plateau(int size){
		this.size = size;
		this.taableauCases = new Case[size][size];
		this.casesLibres = new ArrayList<Case>();
		this.panneau = new PlateauJPanel(this);
		for (int i=0; i<size; i++){
			for (int j=0; j<size; j++){
				this.taableauCases[i][j] = new Case(i, j);
				this.casesLibres.add(this.taableauCases[i][j]);
//				this.panneau.add(this.taableauCases[i][j].bouton);
			}
		}
	}
	
	 public Jeu getJeu(){
		 return jeu;
	 }
	 
	 public void setJeu(Jeu jeu){
		 this.jeu = jeu;
	 }
	
	public List<Case> getCasesLibres(){
		return this.casesLibres;
	}
	public boolean existeCasesLibres(){
		return this.casesLibres.size()>0;
	}
	
	public int getNbCasesLibres(){
		return this.casesLibres.size();
	}
	
	public void setCodeCouleur(HashMap<String, List<String>> h){
		this.codeCouleur = h;
	}
	
	public int[] getAlign(int i, int j){
		return getAlign(i, j, "", new String[8], new ArrayList<List<Case>>());
	}
	
	/**
	 * Renvoie le nombre de pions align�s au nord
	 * @param i
	 * @param j
	 * @param special
	 * @return
	 */
	private int getAlignN(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur n = "+c);
		int x = i-1;
		int align = 1;
		while (x>=0 && this.codeCouleur.get(c).contains(this.taableauCases[x][j].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][j].getCouleur().equals(special)){
				c = this.taableauCases[x][j].getCouleur();
			}
			cases.add(this.taableauCases[x][j]);
			align++;
			x--;
		}
		couleurs[0] = c;
		casesVisitees.add(cases);
		return align;
	}
	
	private int getAlignS(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==this.taableauCases.length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur s = "+c);
		int x = i+1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][j].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][j].getCouleur().equals(special)){
				c = this.taableauCases[x][j].getCouleur();
			}
			cases.add(this.taableauCases[x][j]);
			align++;
			x++;
		}
		couleurs[1] = c;
		casesVisitees.add(cases);
		return align;
	}
	
	private int getAlignW(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur w = "+c);
		int y = j-1;
		int align = 1;
		while (y>=0 && this.codeCouleur.get(c).contains(this.taableauCases[i][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[i][y].getCouleur().equals(special)){
				c = this.taableauCases[i][y].getCouleur();
			}
			cases.add(this.taableauCases[i][y]);
			align++;
			y--;
		}
		casesVisitees.add(cases);
		couleurs[2] = c;
		return align;
	}
	
	private int getAlignE(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (j==this.taableauCases[i].length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur E = "+c);
		int y = j+1;
		int align = 1;
		while (y<=this.taableauCases[i].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[i][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[i][y].getCouleur().equals(special)){
				c = this.taableauCases[i][y].getCouleur();
			}
			cases.add(this.taableauCases[i][y]);
			align++;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[3] = c;
		return align;
	}

	private int getAlignNW(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0 || j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur NW = "+c);
		int x = i-1;
		int y = j-1;
		int align = 1;
		while (x>=0 && y>=0 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x--;
			y--;
		}
		casesVisitees.add(cases);
		couleurs[4] = c;
		return align;
	}
	
	private int getAlignNE(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0 || j==this.taableauCases[i].length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur NE = "+c);
		int x = i-1;
		int y = j+1;
		int align = 1;
		while (x>=0 && y<=this.taableauCases[i].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				System.out.println("nouvelle couleur de NE "+c);
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x--;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[5] = c;
		return align;
	}
	
	private int getAlignSW(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==this.taableauCases.length-1 || j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur SW = "+c);
		int x = i+1;
		int y = j-1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && y>=0 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				System.out.println("nouvelle couleur SW = "+c);
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x++;
			y--;
		}
		casesVisitees.add(cases);
		couleurs[6] = c;
		return align;
	}
	
	private int getAlignSE(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==this.taableauCases.length-1 || j==this.taableauCases[i].length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		System.out.println("couleur SE = "+c);
		int x = i+1;
		int y = j+1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && y<=this.taableauCases[x].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				System.out.println("nouvelle couleur SE = "+c);
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x++;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[7] = c;
		return align;
	}
	
	/**
	 * Renvoie le nombre de pions alignés pour chacune des 4 directions à partir de la case (i, j).
	 * @param i Ordonnée de la case.
	 * @param j Abscisse de la case.
	 * @return Un tableau d'entiers [Nord>Sud, Est>Ouest, Nord-Ouest>Sud-Est, Nord-Est>Sud-Ouest]  
	 */
	public int[] getAlign(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		//TODO : améliorer la compléxité
		System.out.println(this.taableauCases[i][j].toString());
		for (int n=0; n<couleurs.length; n++){
			couleurs[n] = this.taableauCases[i][j].getCouleur();
		}
		int[] alignements;
		alignements = new int[8];
		alignements[0] = getAlignN(i, j, special, couleurs, casesVisitees);
		alignements[1] = getAlignS(i, j, special, couleurs, casesVisitees);
		alignements[2] = getAlignW(i, j, special, couleurs, casesVisitees);
		alignements[3] = getAlignE(i, j, special, couleurs, casesVisitees);
		alignements[4] = getAlignNW(i, j, special, couleurs, casesVisitees);
		alignements[5] = getAlignNE(i, j, special, couleurs, casesVisitees);
		alignements[6] = getAlignSW(i, j, special, couleurs, casesVisitees);
		alignements[7] = getAlignSE(i, j, special, couleurs, casesVisitees);
		/**
		for (int n=7; n>=1; n-=2){
			if (codeCouleur.get(couleurs[n]).contains(couleurs[n-1])){
				alignements[n-1] += alignements[n]-1;
				alignements[n] = 0;
				List<Case> temp = casesVisitees.get(n);
				casesVisitees.remove(n);
				casesVisitees.get(n-1).addAll(temp);	
			} 
		}
		**/
		return alignements;
	}

	public void libere(int i, int j){
		this.taableauCases[i][j].libere();
		this.casesLibres.add(this.taableauCases[i][j]);
	}
	
	/**
	 * Renvoie true s'il existe un chemin de la case (i,j) à la case (k,l)
	 * @param i Ordonnée de la case d'origine.
	 * @param j Abscisse de la case d'origine.
	 * @param k Ordonnée de la case d'arrivée.
	 * @param l Abscisse de la case d'arrivée.
	 * @return True s'il existe un chemin, false sinon.
	 */
	public boolean existeChemin(int i, int j, int k, int l){
		ArrayList<Case> casesVisitees = new ArrayList<Case>();
		ArrayList<Case> chemin = new ArrayList<Case>();
		return existeChemin(i, j, k, l, casesVisitees, chemin);
	}
	
	private boolean existeChemin(int i, int j, int k, int l, List<Case> casesVisitees, List<Case> chemin){
		if (i==k && j==l){
			chemin.add(this.taableauCases[i][j]);
			return true;
		}
		if (casesVisitees.contains(this.taableauCases[i][j])){
			return false;
		}
		casesVisitees.add(this.taableauCases[i][j]);
		String[] directions = getDirections(i, j, k, l);
		for (int n=0; n<4; n++){
			int iprim = getI(i, directions[n]);
			if (iprim!=-1){
				int jprim = getJ(j, directions[n]);
				if (jprim!=-1){
					if (this.taableauCases[iprim][jprim].estLibre()){
						if (existeChemin(iprim, jprim, k, l, casesVisitees,chemin)){
							chemin.add(this.taableauCases[i][j]);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private String[] getDirections(int i, int j, int k, int l){
		String[] directions = new String[4];
		if (i==k){
			if (j<l){
				return new String[] {"E","N","S","W"};
			} else {
				return new String[] {"W","N","S","E"};
			}
		}
		if (j==l){
			if (i<k){
				return new String[] {"S","E","W","N"};
			} else {
				return new String[] {"N","E","W","S"};
			}
		}
		if (i<k){
			directions[1] = "S";
			directions[3] = "N";
		} else {
			directions[1] = "N";
			directions[3] = "S";
		}
		if (j<l){
			directions[0] = "E";
			directions[2] = "W";
		} else {
			directions[0] = "W";
			directions[2] = "E";
		}
		return directions;
	}
	
	private int getI(int i, String dir){
		switch(dir){
		case "W":
			return i;
		case "E":
			return i;
		case "N":
			if (i>0){
				return i-1;
			} else {
				return -1;
			}
		case "S":
			if (i<this.taableauCases.length-1){
				return i+1;
			} else {
				return -1;
			}
		default:
			return -1;
		}
	}

	private int getJ(int j, String dir){
		switch(dir){
		case "N":
			return j;
		case "S":
			return j;
		case "W":
			if (j>0){
				return j-1;
			} else {
				return -1;
			}
		case "E":
			if (j<this.taableauCases.length-1){ // on part du principe que le plateau est carré
				return j+1;
			} else {
				return -1;
			}
		default:
			return -1;
		}
	}
	
	public void pose(int i, int j, String c) throws CaseOccupeeException{
		//TODO
		if (this.taableauCases[i][j].estLibre()){
			this.taableauCases[i][j].pose(c);
			this.casesLibres.remove(this.taableauCases[i][j]);
		} else {
			throw new CaseOccupeeException(this.taableauCases[i][j]);
		}
		panneau.repaint();
	}
	
	public void deplace(int i, int j, int k, int l) throws PasDeCheminException, CaseOccupeeException, CaseVideException{
		//TODO
		if (this.taableauCases[i][j].estLibre()){
			throw new CaseVideException(this.taableauCases[i][j]);
		} else if (!this.taableauCases[k][l].estLibre()){
			throw new CaseOccupeeException(this.taableauCases[k][l]);
		}
		if (existeChemin(i, j, k, l)){
			this.taableauCases[k][l].pose(this.taableauCases[i][j].getCouleur());
			this.taableauCases[i][j].libere();
			this.casesLibres.remove(this.taableauCases[k][l]);
			this.casesLibres.add(this.taableauCases[i][j]);
		} else {
			throw new PasDeCheminException(this.taableauCases[i][j], this.taableauCases[k][l]);
		}
		panneau.repaint();
	}
	
	public boolean existeCaseLibreAutour(int i, int j){
		if (i>0 && this.taableauCases[i-1][j].estLibre()){
			return true;
		}
		if (j>0 && this.taableauCases[i][j-1].estLibre()){
			return true;
		}
		if (i<this.taableauCases.length-1 && this.taableauCases[i+1][j].estLibre()){
			return true;
		}
		if (j<this.taableauCases[i].length-1 && this.taableauCases[i][j+1].estLibre()){
			return true;
		}
		if (i>0 && j>0 && this.taableauCases[i-1][j-1].estLibre()){
			return true;
		}
		if (i<this.taableauCases.length-1 && j<this.taableauCases[i].length-1 && this.taableauCases[i+1][j+1].estLibre()){
			return true;
		}
		if (i>0 && j<this.taableauCases[i].length-1 && this.taableauCases[i-1][j+1].estLibre()){
			return true;
		}
		if (i<this.taableauCases.length-1 && j>0 && this.taableauCases[i+1][j-1].estLibre()){
			return true;
		}
		return false;
	}
	
	//fonction test toute pourrie
	public void afficherPlateau(){
		for (int i=0; i<this.taableauCases.length; i++){
			for (int j=0; j<this.taableauCases[i].length; j++){
				if (this.taableauCases[i][j].estLibre()){
					System.out.print("[ ]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.BLACK)){
					System.out.print("[1]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.WHITE)){
					System.out.print("[2]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.BLUE)){
					System.out.print("[3]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.RED)){
					System.out.print("[4]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.YELLOW)){
					System.out.print("[5]");
				}else {
					System.out.print("[?]");
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		Plateau p = new Plateau(10);
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		List<String> blanc = new ArrayList<String>();
		blanc.add(Couleur.WHITE);
		blanc.add(Couleur.RAINBOW);
		h.put(Couleur.WHITE,blanc);
		List<String> noir = new ArrayList<String>();
		noir.add(Couleur.BLACK);
		noir.add(Couleur.RAINBOW);
		h.put(Couleur.BLACK, noir);
		List<String> arc = new ArrayList<String>();
		arc.add(Couleur.WHITE);
		arc.add(Couleur.RAINBOW);
		arc.add(Couleur.BLACK);
		h.put(Couleur.RAINBOW, arc);
		p.setCodeCouleur(h);
		try {
			p.pose(5, 5, Couleur.RAINBOW);
			p.pose(5, 4, Couleur.WHITE);
			p.pose(5, 6, Couleur.WHITE);
			p.pose(5, 7, Couleur.WHITE);
			p.pose(5, 8, Couleur.WHITE);
			p.pose(4, 4, Couleur.WHITE);
			p.pose(6, 6, Couleur.WHITE);
			p.pose(4, 5, Couleur.BLACK);
			p.pose(6, 5, Couleur.WHITE);
			p.getAlign(5, 5, Couleur.RAINBOW, new String[8], new ArrayList<List<Case>>());
		} catch (CaseOccupeeException e){
			
		}
	}
	
	public boolean estVide(int caseX, int caseY) {
		return taableauCases[caseX][caseY].getCouleur().equals("");
	}
}
