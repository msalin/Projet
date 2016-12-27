import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlateauJPanel extends JPanel implements MouseListener {
	private static int TAILLE_CASE = 50;
	private BufferedImage backGround;
	private Plateau plateau;
	public static BufferedImage imaageBlack;
	public static BufferedImage imaageBlue;
	public static BufferedImage imaageRainbow;
	public static BufferedImage imaageRed;
	public static BufferedImage imaageWhite;
	public static BufferedImage imaageYellow;
	public static BufferedImage imaageGreen;
	public static BufferedImage imaageViolet;
	
	public PlateauJPanel(Plateau plateau) {
		addMouseListener(this);
		this.plateau = plateau;
		try {
			backGround = ImageIO.read(new File("images/plateau.jpg"));
			imaageBlack = ImageIO.read(new File("images/"+Couleur.BLACK+".png"));
			imaageBlue = ImageIO.read(new File("images/"+Couleur.BLUE+".png"));
			imaageRainbow = ImageIO.read(new File("images/"+Couleur.RAINBOW+".png"));
			imaageRed = ImageIO.read(new File("images/"+Couleur.RED+".png"));
			imaageWhite = ImageIO.read(new File("images/"+Couleur.WHITE+".png"));
			imaageYellow = ImageIO.read(new File("images/"+Couleur.YELLOW+".png"));
			imaageGreen = ImageIO.read(new File("images/"+Couleur.GREEN+".png"));
			imaageViolet = ImageIO.read(new File("images/"+Couleur.VIOLET+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getPionImage(String color) {
		switch (color) {
		case Couleur.BLACK:
			return imaageBlack;
		case Couleur.BLUE:
			return imaageBlue;
		case Couleur.RAINBOW:
			return imaageRainbow;
		case Couleur.RED:
			return imaageRed;
		case Couleur.WHITE:
			return imaageWhite;
		case Couleur.YELLOW:
			return imaageYellow;
		case Couleur.GREEN:
			return imaageGreen;
		case Couleur.VIOLET:
			return imaageViolet;
		default:
			break;
		}
		return null;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(backGround, 0, 0, plateau.size*TAILLE_CASE, plateau.size*TAILLE_CASE, null);
		for (int i = 0; i < plateau.size; i++) {
			for (int j = 0; j < plateau.size; j++) {
				Case c = plateau.taableauCases[i][j];
				g.drawImage(getPionImage(c.getCouleur()), i*TAILLE_CASE, j*TAILLE_CASE, TAILLE_CASE, TAILLE_CASE, null);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int caseX = e.getX()/TAILLE_CASE;
		int caseY = e.getY()/TAILLE_CASE;
		plateau.getJeu().actionAFaire(plateau.taableauCases[caseX][caseY]);
//		if (! plateau.estVide(caseX, caseY)) {
//			plateau.pionSelectionne = plateau.taableauCases[caseX][caseY];
//		} else {
//			try {
//				if (plateau.pionSelectionne != null	) {
//					plateau.deplace(plateau.pionSelectionne.i, plateau.pionSelectionne.j, caseX, caseY);
//					plateau.getJeu().calculScore(plateau.taableauCases[caseX][caseY]);
//					plateau.getJeu().tourOrdinateur(3);
//					
//				}
//	
//			} catch (PasDeCheminException e1) {
//				e1.printStackTrace();
//			} catch (CaseOccupeeException e1) {
//				e1.printStackTrace();
//			} catch (CaseVideException e1) {
//				e1.printStackTrace();
//			}
//		}
	}
	
}
