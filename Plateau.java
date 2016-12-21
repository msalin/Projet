import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
public class Plateau {

	final Case[][] plateau;
	public HashMap<String, List<String>> codeCouleur;
	private List<Case> casesLibres;
	
	public Plateau(int size){
		this.plateau = new Case[size][size];
		this.casesLibres = new ArrayList<Case>();
		for (int i=0; i<size; i++){
			for (int j=0; j<size; j++){
				this.plateau[i][j] = new Case(i, j);
				this.casesLibres.add(this.plateau[i][j]);
			}
		}
	}
	
	public List<Case> getCasesLibres(){
		return this.casesLibres;
	}
	public boolean existeCasesLibres(){
		return this.casesLibres.size()>0;
	}
	
	public void setCodeCouleur(HashMap<String, List<String>> h){
		this.codeCouleur = h;
	}
	
	/**
	 * Renvoie le nombre de pions alignés pour chacune des 4 directions à partir de la case (i, j).
	 * @param i Ordonnée de la case.
	 * @param j Abscisse de la case.
	 * @return Un tableau d'entiers [Nord>Sud, Est>Ouest, Nord-Ouest>Sud-Est, Nord-Est>Sud-Ouest]  
	 */
	public int[] getAlign(int i, int j){
		//TODO : améliorer la compléxité
		int[] alignements = new int[4];
		String c = this.plateau[i][j].getCouleur();
		int x = i-1;
		while (x>=0 && this.codeCouleur.get(c).contains(this.plateau[x][j].getCouleur())){
				alignements[0] += 1;
				x--;
		}
		x = i+1;
		while (x<this.plateau.length && this.codeCouleur.get(c).contains(this.plateau[x][j].getCouleur())){
			alignements[0] += 1;
			x++;
		}
		x = j-1;
		while (x>=0 && this.codeCouleur.get(c).contains(this.plateau[i][x].getCouleur())){
			alignements[1] += 1;
			x--;
		}
		x = j+1;
		while (x<this.plateau[i].length && this.codeCouleur.get(c).contains(this.plateau[i][x].getCouleur())){
			alignements[1] += 1;
			x++;
		}
		x = i-1;
		int y = j-1;
		while (x>=0 && y>=0 && this.codeCouleur.get(c).contains(this.plateau[x][y].getCouleur())){
			alignements[2] += 1;
			x--;
			y--;
		}
		x = i-1;
		y = j+1;
		while (x>=0 && y<this.plateau[x].length && this.codeCouleur.get(c).contains(this.plateau[x][y].getCouleur())){
			alignements[3] += 1;
			x--;
			y++;
		}
		x = i+1;
		y = j+1;
		while (x<this.plateau.length && y<this.plateau[x].length && this.codeCouleur.get(c).contains(this.plateau[x][y].getCouleur())){
			alignements[2] += 1;
			x++;
			y++;
		}
		x = i+1;
		y = j-1;
		while (x<this.plateau.length && y>=0 && this.codeCouleur.get(c).contains(this.plateau[x][y].getCouleur())){
			alignements[3] += 1;
			x++;
			y--;
		}
		return alignements;
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
			chemin.add(this.plateau[i][j]);
			return true;
		}
		if (casesVisitees.contains(this.plateau[i][j])){
			return false;
		}
		casesVisitees.add(this.plateau[i][j]);
		String[] directions = getDirections(i, j, k, l);
		for (int n=0; n<4; n++){
			int iprim = getI(i, directions[n]);
			if (iprim!=-1){
				int jprim = getJ(j, directions[n]);
				if (jprim!=-1){
					if (this.plateau[iprim][jprim].estLibre()){
						if (existeChemin(iprim, jprim, k, l, casesVisitees,chemin)){
							chemin.add(this.plateau[i][j]);
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
			if (i<this.plateau.length-1){
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
			if (j<this.plateau.length-1){ // on part du principe que le plateau est carré
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
		if (this.plateau[i][j].estLibre()){
			this.plateau[i][j].pose(c);
			this.casesLibres.remove(this.plateau[i][j]);
		} else {
			throw new CaseOccupeeException(this.plateau[i][j]);
		}
		
	}
	
	public void deplace(int i, int j, int k, int l) throws PasDeCheminException, CaseOccupeeException, CaseVideException{
		//TODO
		if (this.plateau[i][j].estLibre()){
			throw new CaseVideException(this.plateau[i][j]);
		} else if (!this.plateau[k][l].estLibre()){
			throw new CaseOccupeeException(this.plateau[k][l]);
		}
		if (existeChemin(i, j, k, l)){
			this.plateau[k][l].pose(this.plateau[i][j].getCouleur());
			this.plateau[i][j].libere();
			this.casesLibres.remove(this.plateau[k][l]);
			this.casesLibres.add(this.plateau[i][j]);
		} else {
			throw new PasDeCheminException(this.plateau[i][j], this.plateau[k][l]);
		}
	}
	
	public boolean existeCaseLibreAutour(int i, int j){
		if (i>0 && this.plateau[i-1][j].estLibre()){
			return true;
		}
		if (j>0 && this.plateau[i][j-1].estLibre()){
			return true;
		}
		if (i<this.plateau.length-1 && this.plateau[i+1][j].estLibre()){
			return true;
		}
		if (j<this.plateau[i].length-1 && this.plateau[i][j+1].estLibre()){
			return true;
		}
		if (i>0 && j>0 && this.plateau[i-1][j-1].estLibre()){
			return true;
		}
		if (i<this.plateau.length-1 && j<this.plateau[i].length-1 && this.plateau[i+1][j+1].estLibre()){
			return true;
		}
		if (i>0 && j<this.plateau[i].length-1 && this.plateau[i-1][j+1].estLibre()){
			return true;
		}
		if (i<this.plateau.length-1 && j>0 && this.plateau[i+1][j-1].estLibre()){
			return true;
		}
		return false;
	}
	
	public class Case{
		public final int i, j;
		private String couleur;
		
		public Case(int i, int j){
			this.i = i;
			this.j = j;
			this.couleur = "";
		}
		
		/**
		 * A COMMENTER
		 * @param c
		 */
		public void pose(String c){
			//TODO
			this.couleur = c;
		}
		
		/**
		 * A COMMENTER
		 */
		public void libere(){
			//TODO
			this.couleur = "";
		}
		
		/**
		 * 
		 * @return True si cette Case est libre, false sinon.
		 */
		public boolean estLibre(){
			return this.couleur.equals("");
		}
		
		/**
		 * Renvoie la couleur de cette Case.
		 * @return
		 */
		public String getCouleur(){
			return this.couleur;
		}
		
		public String toString(){
			return "("+this.i+", "+this.j+")";
		}
	}
	
	//fonction test toute pourrie
	public void afficherPlateau(){
		for (int i=0; i<this.plateau.length; i++){
			for (int j=0; j<this.plateau[i].length; j++){
				if (this.plateau[i][j].estLibre()){
					System.out.print("[ ]");
				} else if (this.plateau[i][j].getCouleur().equals("black")){
					System.out.print("[1]");
				} else if (this.plateau[i][j].getCouleur().equals("white")){
					System.out.print("[2]");
				} else if (this.plateau[i][j].getCouleur().equals("blue")){
					System.out.print("[3]");
				} else if (this.plateau[i][j].getCouleur().equals("red")){
					System.out.print("[4]");
				} else if (this.plateau[i][j].getCouleur().equals("yellow")){
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
		blanc.add("WHITE");
		blanc.add("RAINBOW");
		h.put("WHITE",blanc);
		List<String> noir = new ArrayList<String>();
		noir.add("BLACK");
		noir.add("RAINBOW");
		h.put("BLACK", noir);
		p.setCodeCouleur(h);
	}
}
