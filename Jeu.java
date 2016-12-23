import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
public abstract class Jeu {
  protected Joueur joueur1;
  protected Joueur joueur2;
  protected Plateau plateau;
  protected JFrame frame;
  protected JPanel scores;
  
  public Jeu(Joueur j1, Joueur j2, Plateau p){
    this.joueur1 = j1;
    this.joueur1.reset();
    this.joueur2 = j2;
    this.joueur2.reset();
    this.plateau = p;
    p.setJeu(this);
    this.frame = new JFrame();
    this.frame.setLayout(new BoxLayout(this.frame.getContentPane(),BoxLayout.PAGE_AXIS));
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.scores = new JPanel();
    this.scores.setMaximumSize(new Dimension(1000, 200));
    this.scores.setMinimumSize(new Dimension(1000, 100));
    this.frame.add(this.scores);
    this.frame.add(this.plateau.panneau);
    this.frame.setSize(new Dimension(1000, 500));
  }
  
  public void afficheMessage(String m){
	  JFrame message = new JFrame();
	  message.add(new JLabel(m));
	  message.setSize(100, 100);
	  message.setVisible(true);
  }
  
  public abstract void actionAFaire(Plateau.Case c);
  
  //Abstract ?
  public static void afficheRegles(){
  }
  
  public abstract void jouer();
  
}

