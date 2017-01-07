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
	public int size;
	final Case[][] taableauCases;
	public Case pionSelectionne;
	public HashMap<String, List<String>> codeCouleur;
	private List<Case> casesLibres;
	private Jeu jeu;
	protected String couleurSelectionnee;
	public PlateauJPanel panneau;

	/**
	* Instancie un Plateau carré de taille size
	* @param size la taille de ce Plateau
	*/
	public Plateau(int size){
		this.size = size;
		this.taableauCases = new Case[size][size];
		this.casesLibres = new ArrayList<Case>();
		for (int i=0; i<size; i++){
			for (int j=0; j<size; j++){
				this.taableauCases[i][j] = new Case(i, j);
				this.casesLibres.add(this.taableauCases[i][j]);
//				this.panneau.add(this.taableauCases[i][j].bouton);
			}
		this.panneau = new PlateauJPanel(this);
		}
		//testAlignDouble();
	}
	
	 private void testAlignDouble() {
		 this.taableauCases[0][5] = new Case(0, 5, Couleur.RED);
		 this.taableauCases[1][5] = new Case(1, 5, Couleur.RED);
		 this.taableauCases[2][5] = new Case(2, 5, Couleur.RED);
		 this.taableauCases[3][5] = new Case(3, 5, Couleur.RED);
		 
		 this.taableauCases[4][5] = new Case(4, 5, "");
		 this.taableauCases[5][5] = new Case(5, 5, Couleur.YELLOW);
		 this.taableauCases[6][5] = new Case(6, 5, Couleur.YELLOW);
		 this.taableauCases[7][5] = new Case(7, 5, Couleur.YELLOW);
		 this.taableauCases[8][5] = new Case(8, 5, Couleur.YELLOW);
		 
		 this.taableauCases[4][1] = new Case(4, 1, Couleur.BLACK);
		 this.taableauCases[4][2] = new Case(4, 2, Couleur.BLACK);
		 this.taableauCases[4][3] = new Case(4, 3, Couleur.BLACK);
		 this.taableauCases[4][4] = new Case(4, 4, Couleur.BLACK);
		 this.taableauCases[4][6] = new Case(4, 6, Couleur.BLUE);
		 this.taableauCases[4][7] = new Case(4, 7, Couleur.BLUE);
		 this.taableauCases[4][8] = new Case(4, 8, Couleur.BLUE);
		 this.taableauCases[4][9] = new Case(4, 9, Couleur.BLUE);
		 
		 this.taableauCases[3][4] = new Case(3, 4, Couleur.RAINBOW);
	}
	 public Jeu getJeu(){
		 return jeu;
	 }
	 
	 public void setJeu(Jeu jeu){
		 this.jeu = jeu;
	 }
	
	/**
	* Renvoie la liste des cases libres de ce Plateau
	* @return une liste de Case
	*/
	public List<Case> getCasesLibres(){
		return this.casesLibres;
	}
	
	/**
	* Renvoie true si il existe des cases libres sur ce Plateau.
	* @return True si il existe des cases libres, False sinon
	*/
	public boolean existeCasesLibres(){
		return this.casesLibres.size()>0;
	}
	
	/**
	* Renvoie le nombre de cases libres de ce Plateau
	* @return un entier entre 0 et le nombre de cases de ce Plateau
	*/
	public int getNbCasesLibres(){
		return this.casesLibres.size();
	}
	
	public void setCodeCouleur(HashMap<String, List<String>> h){
		this.codeCouleur = h;
	}
	
	/**
	* Renvoie le nombre de pions alignés avec le pion à la case (i, j)
	* @param i Abscisse de la case
	* @param j Ordonnée de la case
	* @return Un tableau d'entiers de taille 8 [Nord, Sud, Ouest, Est, Nord-Ouest, Sud-Est, Nord-Est, Sud-Ouest] 
	*/
	public int[] getAlign(int i, int j){
		return getAlign(i, j, "", new String[8], new ArrayList<List<Case>>());
	}
	
	private int getAlignWEST(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur n = "+c);
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
		couleurs[2] = c;
		//System.out.println("couleur [2] = "+c);
		casesVisitees.add(cases);
		return align;
	}
	
	private int getAlignEAST(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==this.taableauCases.length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur s = "+c);
		int x = i+1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][j].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][j].getCouleur().equals(special)){
				//System.out.println("bouS");
				c = this.taableauCases[x][j].getCouleur();
			}
			cases.add(this.taableauCases[x][j]);
			align++;
			x++;
		}
		couleurs[3] = c;
		//System.out.println("couleur [3] = "+c);
		casesVisitees.add(cases);
		return align;
	}
	
	private int getAlignNORTH(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur w = "+c);
		int y = j-1;
		int align = 1;
		while (y>=0 && this.codeCouleur.get(c).contains(this.taableauCases[i][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[i][y].getCouleur().equals(special)){
				//System.out.println("bouW");
				c = this.taableauCases[i][y].getCouleur();
			}
			cases.add(this.taableauCases[i][y]);
			align++;
			y--;
		}
		casesVisitees.add(cases);
		couleurs[0] = c;
		//System.out.println("couleur [0] = "+c);
		return align;
	}
	
	private int getAlignSOUTH(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (j==this.taableauCases[i].length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur e = "+c);
		int y = j+1;
		int align = 1;
		while (y<=this.taableauCases[i].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[i][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[i][y].getCouleur().equals(special)){
				//System.out.println("bouE");
				c = this.taableauCases[i][y].getCouleur();
			}
			cases.add(this.taableauCases[i][y]);
			align++;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[1] = c;
		//System.out.println("couleur [1] = "+c);
		return align;
	}

	private int getAlignNW(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0 || j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur NW = "+c);
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
	
	private int getAlignSW(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==0 || j==this.taableauCases[i].length-1){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur NE = "+c);
		int x = i-1;
		int y = j+1;
		int align = 1;
		while (x>=0 && y<=this.taableauCases[i].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				//System.out.println("nouvelle couleur de NE "+c);
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x--;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[7] = c;
		return align;
	}
	
	private int getAlignNE(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		List<Case> cases = new ArrayList<Case>();
		cases.add(this.taableauCases[i][j]);
		if (i==this.taableauCases.length-1 || j==0){
			return 1;
		}
		String c = this.taableauCases[i][j].getCouleur();
		//System.out.println("couleur SW = "+c);
		int x = i+1;
		int y = j-1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && y>=0 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				//System.out.println("nouvelle couleur SW = "+c);
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
		//System.out.println("couleur SE = "+c);
		int x = i+1;
		int y = j+1;
		int align = 1;
		while (x<=this.taableauCases.length-1 && y<=this.taableauCases[x].length-1 && this.codeCouleur.get(c).contains(this.taableauCases[x][y].getCouleur())){
			if (c.equals(special) && !this.taableauCases[x][y].getCouleur().equals(special)){
				c = this.taableauCases[x][y].getCouleur();
				//System.out.println("nouvelle couleur SE = "+c);
			}
			cases.add(this.taableauCases[x][y]);
			align++;
			x++;
			y++;
		}
		casesVisitees.add(cases);
		couleurs[5] = c;
		return align;
	}
	
	/**
	 * Renvoie le nombre de pions alignés pour chacune des 4 directions à partir de la case (i, j).
	 * @param i Abscisse de la case.
	 * @param j Ordonnée de la case.
	 * @param special Couleur spéciale.
	 * @param couleurs Tableau de taille 8 qui contient les couleurs pour chaque direction.
	 * @param casesVisitees Liste de liste de Cases.
	 * @return Un tableau d'entiers de taille 8 [Nord, Sud, Ouest, Est, Nord-Ouest, Sud-Est, Nord-Est, Sud-Ouest]  
	 */
	public int[] getAlign(int i, int j, String special, String[] couleurs, List<List<Case>> casesVisitees){
		//TODO : améliorer la compléxité
		System.out.println(this.taableauCases[i][j].toString());
		for (int n=0; n<couleurs.length; n++){
			couleurs[n] = this.taableauCases[i][j].getCouleur();
		}
		int[] alignements;
		alignements = new int[8];
		alignements[0] = getAlignNORTH(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement N = "+alignements[0]);
		alignements[1] = getAlignSOUTH(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement S = "+alignements[1]);
		alignements[2] = getAlignWEST(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement W = "+alignements[2]);
		alignements[3] = getAlignEAST(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement E = "+alignements[3]);
		alignements[4] = getAlignNW(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement NW = "+alignements[4]);
		alignements[5] = getAlignSE(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement SE = "+alignements[5]);
		alignements[6] = getAlignNE(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement NE = "+alignements[6]);
		alignements[7] = getAlignSW(i, j, special, couleurs, casesVisitees);
		//System.out.println("alignement SW = "+alignements[7]);

		
//		for (int n=7; n>=1; n-=2){
//			if (codeCouleur.get(couleurs[n]).contains(couleurs[n-1])){
//				alignements[n-1] += alignements[n]-1;
//				alignements[n] = 0;
//				List<Case> temp = casesVisitees.get(n);
//				System.out.println(casesVisitees.get(n));
//				casesVisitees.set(n, null);
//				casesVisitees.get(n-1).addAll(temp);
//				
//			} 
//		}
		
		for (int t = 0;t<alignements.length;t++){
			//System.out.println("couleurs ["+t+"] = "+couleurs[t]);
			//System.out.println("alignements ["+t+"] = "+alignements[t]);
		}
		return alignements;
	}

	/**
	* Libère la case (i, j)
	* @param i Abscisse de la case
	* @param j Ordonnée de la case
	*/
	public void libere(int i, int j){
		this.taableauCases[i][j].libere();
		if (! this.casesLibres.contains(this.taableauCases[i][j])){
			this.casesLibres.add(this.taableauCases[i][j]);
		}
		this.printCasesVides();
		
	}
	
	public void printCasesVides(){
		System.out.print("< ");
		for (Case c : this.casesLibres){
			System.out.print(c+" ");
		}
		System.out.print(">\n");
	}
	
	/**
	 * Renvoie true s'il existe un chemin de la case (i,j) à la case (k,l)
	 * @param i Abscisse de la case d'origine.
	 * @param j Ordonnée de la case d'origine.
	 * @param k Abscisse de la case d'arrivée.
	 * @param l Ordonnée de la case d'arrivée.
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
	
	/**
	* Pose un pion de couleur c à la case (i, j)
	* @param i Abscisse de la case
	* @param j Ordonnée de la case
	* @param c Couleur du pion
	* @throws CaseOccupeeException Si un pion est déjà sur la case
	*/
	public void pose(int i, int j, String c) throws CaseOccupeeException{
		if (this.taableauCases[i][j].estLibre()){
			this.casesLibres.remove(this.taableauCases[i][j]);
			this.taableauCases[i][j].pose(c);
			this.printCasesVides();
		} else {
			throw new CaseOccupeeException(this.taableauCases[i][j]);
		}
		panneau.repaint();
	}
	
	/**
	* Déplace le pion de la case (i, j) à la case (k, l)
	* @param i Abscisse de la case de départ
	* @param j Ordonnée de la case de départ
	* @param k Abscisse de la case d'arrivée
	* @param l Ordonnée de la case d'arrivée
	* @throws PasDeCheminException Si il n'existe pas de chemin allant de la case (i, j) à la case (k, l)
	* @throws CaseOccupeeException Si il y a déjà un pion sur la case d'arrivée
	* @throws CaseVideException Si la case de départ est vide
	*/
	public void deplace(int i, int j, int k, int l) throws PasDeCheminException, CaseOccupeeException, CaseVideException{
		if (this.taableauCases[i][j].estLibre()){
			throw new CaseVideException(this.taableauCases[i][j]);
		} else if (!this.taableauCases[k][l].estLibre()){
			throw new CaseOccupeeException(this.taableauCases[k][l]);
		}
		if (existeChemin(i, j, k, l)){
			this.casesLibres.remove(this.taableauCases[k][l]);
			this.casesLibres.add(this.taableauCases[i][j]);
			this.taableauCases[k][l].pose(this.taableauCases[i][j].getCouleur());
			this.taableauCases[i][j].libere();
			this.printCasesVides();
		} else {
			throw new PasDeCheminException(this.taableauCases[i][j], this.taableauCases[k][l]);
		}
		panneau.repaint();
	}
	
	/**
	* Renvoie true si il existe au moins une case vide autour de la case (i, j)
	* @param i Abscisse de la case
	* @param j Ordonnée de la case
	* @return booléen
	*/
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
			System.out.print(" "+i+"");
			for (int j=0; j<this.taableauCases[i].length; j++){
				if (this.taableauCases[i][j].estLibre()){
					System.out.print("[ ]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.BLACK)){
					System.out.print("[N]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.WHITE)){
					System.out.print("[B]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.BLUE)){
					System.out.print("[V]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.RED)){
					System.out.print("[R]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.YELLOW)){
					System.out.print("[J]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.GREEN){
					System.out.print("[G]");
				} else if (this.taableauCases[i][j].getCouleur().equals(Couleur.GREEN){
					System.out.print("[P]");
				} else {
					System.out.print("[?]");
				}
			}
			System.out.println();
		}
	}
	
	/**
	* Renvoie true si la case (caseX, caseY) est vide
	* @param caseX Abscisse de la case
	* @param caseY Ordonnée de la case
	* @return booléen
	*/
	public boolean estVide(int caseX, int caseY) {
		return taableauCases[caseX][caseY].getCouleur().equals("void");
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
		int[] score = null;
		try {
			p.pose(5, 5, Couleur.WHITE);
			p.pose(5, 4, Couleur.WHITE);
			p.pose(5, 3, Couleur.WHITE);
			p.pose(5, 2, Couleur.WHITE);
			p.pose(5, 1, Couleur.WHITE);
			p.pose(5, 0, Couleur.WHITE);
			p.pose(4, 5, Couleur.BLACK);
			p.pose(3, 5, Couleur.BLACK);
			p.pose(2, 5, Couleur.BLACK);
			p.pose(1, 5, Couleur.BLACK);
			p.pose(0, 5, Couleur.BLACK);
			System.out.println("deplace");
			p.deplace(0,5,0,0);
			System.out.println(p.taableauCases[0][0].estLibre());
			System.out.println(p.taableauCases[0][5].estLibre());
			score = p.getAlign(5, 5, Couleur.RAINBOW, new String[8], new ArrayList<List<Case>>());
		} catch (Exception e){
			
		}
		p.afficherPlateau();
		if (score != null ) {
			System.out.println("Nombre d'alignement gagnant :"+score.length);
		}
		
		for (int i=0; i<score.length; i++){
		       if (score[i]>=5){
		    	   System.out.print("a[i]="+score[i]);
		    	   System.out.print("score[i]="+score[i]);

		       } 
		}
	}
	
	
}
