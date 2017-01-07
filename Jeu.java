import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public abstract class Jeu {
  protected Joueur joueur1;
  protected Joueur joueur2;
  protected Plateau plateau;
  protected JFrame frame;
  protected JPanel boutonsCouleurs;
  protected JPanel scores;
  protected boolean fini;
  
  public Jeu(Joueur j1, Joueur j2, Plateau p){
    this.joueur1 = j1;
    this.joueur1.reset();
    this.joueur2 = j2;
    this.joueur2.reset();
    this.plateau = p;
    p.setJeu(this);
    this.fini = false;
    this.frame = new JFrame();
    this.frame.setSize(new Dimension(500, 500));
    this.frame.setLayout(new BoxLayout(this.frame.getContentPane(),BoxLayout.PAGE_AXIS));
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.scores = new JPanel();
    this.scores.setMaximumSize(new Dimension(500, 200));
    this.scores.setMinimumSize(new Dimension(500, 100));
    
    this.boutonsCouleurs = new JPanel();
    this.boutonsCouleurs.setMaximumSize(new Dimension(500, 200));
    this.boutonsCouleurs.setMinimumSize(new Dimension(500, 100));
//    JButton btnBlack = new JButton(new ImageIcon(PlateauJPanel.imaageBlack));
//    btnBlack.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.BLACK;
//		}
//	});
//    JButton btnBlue = new JButton(new ImageIcon(PlateauJPanel.imaageBlue));
//    btnBlack.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.BLUE;
//		}
//	});
//    JButton btnRainbow = new JButton(new ImageIcon(PlateauJPanel.imaageRainbow));
//    btnRainbow.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.RAINBOW;
//		}
//	});
//    JButton btnRed = new JButton(new ImageIcon(PlateauJPanel.imaageRed));
//    btnRed.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.RED;
//		}
//	});
//    JButton btnWhite = new JButton(new ImageIcon(PlateauJPanel.imaageWhite));
//    btnWhite.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.WHITE;
//		}
//	});
//    JButton btnYellow = new JButton(new ImageIcon(PlateauJPanel.imaageYellow));
//    btnYellow.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			plateau.couleurSelectionnee = Couleur.YELLOW;
//		}
//	});
//    this.boutonsCouleurs.add(btnBlack);
//    this.boutonsCouleurs.add(btnBlue);
//    this.boutonsCouleurs.add(btnRainbow);
//    this.boutonsCouleurs.add(btnRed);
//    this.boutonsCouleurs.add(btnWhite);
//    this.boutonsCouleurs.add(btnYellow);
    this.frame.add(this.scores);
//    this.frame.add(this.boutonsCouleurs);
    this.frame.add(this.plateau.panneau);
    this.frame.setSize(new Dimension(this.plateau.size*PlateauJPanel.TAILLE_CASE+5, this.plateau.size*PlateauJPanel.TAILLE_CASE+60));
    this.frame.setMinimumSize(new Dimension(this.plateau.size*PlateauJPanel.TAILLE_CASE+5, this.plateau.size*PlateauJPanel.TAILLE_CASE+60));
    this.frame.setMaximumSize(new Dimension (this.plateau.size*PlateauJPanel.TAILLE_CASE+5, this.plateau.size*PlateauJPanel.TAILLE_CASE+60));
    this.frame.setLocationRelativeTo(null);
    this.frame.setResizable(false);
  }
  
/**
* Affiche un message d'information
**/
  public void afficheMessage(String m){
	  //JFrame message = new JFrame();
	  //message.add(new JLabel(m));
	  //message.setSize(500, 100);
	  //message.setLocationRelativeTo(this.frame);
	  //message.setVisible(true);
	  JOptionPane.showMessageDialog(this.frame, m, "Information", JOptionPane.INFORMATION_MESSAGE);
  }
  
/**
* Affiche un message d'erreur
**/
  public void afficheErreur(String m){
	  JOptionPane.showMessageDialog(this.frame, m, "Erreur", JOptionPane.ERROR_MESSAGE);
  }
  public abstract void actionAFaire(Case c);
  
  //Abstract ?
  public static void afficheRegles(){
  }
  
  public abstract void jouer();
  
  public abstract void calculScore(Case c);
  
  public abstract List<Case> tourOrdinateur(int nbCases);
  
}

