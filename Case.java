import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;


	public class Case{
		public final int i, j;
		private String couleur;
		public BoutonCase bouton;
		
		public Case(int i, int j){
			this.i = i;
			this.j = j;
			this.couleur = "void";
		}
		
		public Case(int i, int j, String couleur){
			this(i,j);
			this.couleur = couleur;
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
			this.couleur = "void";
		}
		
		/**
		 * 
		 * @return True si cette Case est libre, false sinon.
		 */
		public boolean estLibre(){
			return this.couleur.equals("void");
		}
		
		public boolean equals(Object o){
			if (o instanceof Case){
				if (this.i == ((Case)o).i && this.j == ((Case) o).j){
					return true;
				}
			}
			return false;
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
		
		public class BoutonCase extends JButton{
			private Case c;
			
			public BoutonCase(Case c, String filename){
				super(new ImageIcon(filename));
				this.setBorder(BorderFactory.createEmptyBorder());
				//this.setContentAreaFilled(false);
				this.c = c;
				class ML extends MouseAdapter{
					public void mousePressed(MouseEvent e){
//						jeu.actionAFaire(c);
					}
				}
				this.addMouseListener(new ML());
			}
			
			
			
		}
	}