
	import javax.swing.JFrame;
	import javax.swing.JButton;
	import javax.swing.JPanel;
	import javax.swing.JOptionPane;
	import java.awt.event.*;
	import javax.swing.SpinnerNumberModel;
	import javax.swing.SpinnerModel;
	import javax.swing.JSpinner;
	import javax.swing.BoxLayout;
	import javax.swing.JTextField;
	import javax.swing.JLabel;
	import javax.swing.JCheckBox;
	
	public class Main {

		static JFrame fenetre = new JFrame();
		static JPanel menuPrincipal = new JPanel();
		static JPanel menuGomuku = new PanelMenuJeu("Gomuku");
		static JPanel menuKolorLines = new PanelMenuJeu("KolorLines");
		public static void start(){
			fenetre.setSize(500, 500);
			JButton boutonGomuku = new JButton("Gomuku");
			boutonGomuku.addMouseListener(new MouseListener(){
				public void mousePressed(MouseEvent e){
					fenetre.setContentPane(menuGomuku);
					fenetre.setVisible(true);
				}
				public void mouseExited(MouseEvent e){}
				public void mouseClicked(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
			});
			JButton boutonKolorLines = new JButton("KolorLines");
			boutonKolorLines.addMouseListener(new MouseListener(){
				public void mousePressed(MouseEvent e){
					fenetre.setContentPane(menuKolorLines);
					fenetre.setVisible(true);
				}
				public void mouseExited(MouseEvent e){}
				public void mouseClicked(MouseEvent e){}
				public void mouseReleased(MouseEvent e){}
				public void mouseEntered(MouseEvent e){}
			});
			menuPrincipal.add(boutonGomuku);
			menuPrincipal.add(boutonKolorLines);
			fenetre.setContentPane(menuPrincipal);
			
			
			fenetre.setVisible(true);
		}
		public static void main(String[] args){
			
			start();
		}
		
		public static class SpinnerTaille extends JSpinner{
			public SpinnerTaille(){
				super(new SpinnerNumberModel(10, 5, 20, 1));
			}
		}
		
		public static class PanelMenuJeu extends JPanel{
			public final String game;
			private final SpinnerTaille st;
			private final PanelJoueur joueurs;
			public PanelMenuJeu(String game){
				super();
				this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
				this.game = game;
				JPanel taillePlateau = new JPanel();
				taillePlateau.add(new JLabel("Taille du plateau : "));
				this.st = new SpinnerTaille();
				taillePlateau.add(this.st);
				this.add(taillePlateau);
				

				this.joueurs = new PanelJoueur(game.equals("Gomuku"));
				this.add(this.joueurs);
				
				JButton launch = new JButton("OK");
				final class Lancer extends MouseAdapter{
					
					public void mousePressed(MouseEvent e){
						int size = (int) st.getValue();
						String nom_j1 = joueurs.getJ1().getText();
						if (game.equals("Gomuku")){
							if (joueurs.secondJoueurSelectionne()){
								String nom_j2 = joueurs.getJ2().getText();
								new Gomuku(new JoueurHumain(nom_j1), new JoueurHumain(nom_j2), size);
							} else {
								new Gomuku(new JoueurHumain(nom_j1), size);
							}
						} else if (game.equals("KolorLines")){
							KolorLines kl = new KolorLines(new JoueurHumain(nom_j1), size);
							kl.launch();
						}
					}
				}
				launch.addMouseListener(new Lancer());
				this.add(launch);
				
				JButton goBack = new JButton("Retour");
				goBack.addMouseListener(new MouseListener(){
					public void mousePressed(MouseEvent e){
						fenetre.setContentPane(menuPrincipal);
						fenetre.setVisible(true);
					}
					public void mouseExited(MouseEvent e){}
					public void mouseClicked(MouseEvent e){}
					public void mouseReleased(MouseEvent e){}
					public void mouseEntered(MouseEvent e){}
				});
				this.add(goBack);
				this.setVisible(true);
			}
		}
		
		public static class PanelJoueur extends JPanel{
			
			private JTextField nomJ1;
			private JTextField nomJ2;
			private JCheckBox secondJoueur;
			
			public PanelJoueur(boolean secondJoueur){
				super();
				this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
				
				JPanel joueur1 = new JPanel();
				joueur1.add(new JLabel("Nom du joueur : "));
				this.nomJ1 = new JTextField(20);
				joueur1.add(this.nomJ1);
				this.add(joueur1);
				
				if (secondJoueur){
					this.secondJoueur = new JCheckBox("Jouer avec un deuxième joueur humain ");
					this.add(this.secondJoueur);
					JPanel joueur2 = new JPanel();
					joueur2.add(new JLabel("Nom du 2nd joueur : "));
					this.nomJ2 = new JTextField(20);
					joueur2.add(this.nomJ2);
					this.add(joueur2);
				}
			}
			
			public JTextField getJ1(){
				return this.nomJ1;
			}
			
			public JTextField getJ2(){
				return this.nomJ2;
			}
			
			public boolean secondJoueurSelectionne(){
				if (this.secondJoueur == null){
					return false;
				}
				return this.secondJoueur.isSelected();
			}
		}
}


