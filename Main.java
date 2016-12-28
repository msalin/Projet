
	import javax.swing.JFrame;
	import javax.swing.JButton;
	import javax.swing.JPanel;
	import javax.swing.JOptionPane;
	import java.awt.event.*;
	import javax.swing.SpinnerNumberModel;
	import javax.swing.SpinnerModel;
	import javax.swing.JSpinner;

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
			public PanelMenuJeu(String game){
				super();
				this.game = game;
				this.st = new SpinnerTaille();
				this.add(st);
				
				JButton launch = new JButton("OK");
				final class Lancer extends MouseAdapter{
					
					public void mousePressed(MouseEvent e){
						int size = (int) st.getValue();
						if (game.equals("Gomuku")){
							new Gomuku(new JoueurHumain("Toto"), size);
						} else if (game.equals("KolorLines")){
							KolorLines kl = new KolorLines(new JoueurHumain("Toto"), size);
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
}


