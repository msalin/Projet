import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JLabel;

public class KolorLines extends Jeu {

	private List<String> prochainsCoups;
	private Case pionSelectionne;
	private List<Case> casesDepart;

	public KolorLines(Joueur j1, int taille) {
		super(j1, new JoueurIA(), new Plateau(taille));
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		String[] couleurs = new String[] { Couleur.BLACK, Couleur.WHITE, Couleur.BLUE, Couleur.RED, Couleur.YELLOW, Couleur.GREEN, Couleur.VIOLET };
		List<String> lrainbow = new ArrayList<String>();
		lrainbow.add(Couleur.RAINBOW);
		for (int i = 0; i < couleurs.length; i++) {
			List<String> l = new ArrayList<String>();
			l.add(couleurs[i]);
			l.add(Couleur.RAINBOW);
			h.put(couleurs[i], l);
			lrainbow.add(couleurs[i]);
		}
		h.put(Couleur.RAINBOW, lrainbow);
		h.put("void", new ArrayList<String>());
		this.plateau.setCodeCouleur(h);
		this.prochainsCoups = this.creerProchainsCoups(taille * taille);
		

		this.frame.setTitle("KolorLines");
		this.scores.add(new JLabel(this.joueur1.nom + " : " + this.joueur1.getNbPoints() + " points"));
		this.frame.setSize(1280, 720);
		this.frame.setVisible(true);

	}

	private List<String> creerProchainsCoups(int nbCases) {
		List<String> prochainCoups = new ArrayList<String>();
		Random r = new Random();
		for (int i = 0; i < nbCases; i++) {
			float n = r.nextFloat()*100;
			if (n < 2) {
				prochainCoups.add(Couleur.RAINBOW);
			} else if (n < 16) {
				prochainCoups.add(Couleur.BLACK);
			} else if (n < 30) {
				prochainCoups.add(Couleur.WHITE);
			} else if (n < 44) {
				prochainCoups.add(Couleur.BLUE);
			} else if (n < 58) {
				prochainCoups.add(Couleur.RED);
			} else if (n < 72){
				prochainCoups.add(Couleur.YELLOW);
			} else if (n < 86){
				prochainCoups.add(Couleur.GREEN);
			} else {
				prochainCoups.add(Couleur.VIOLET);
			}
		}
		return prochainCoups;
	}

	public void jouer() {

		while (this.plateau.existeCasesLibres()) {
			int nbCases = this.plateau.getNbCasesLibres();
			if (this.prochainsCoups.size() <= 3) {
				this.prochainsCoups.addAll(this.creerProchainsCoups(nbCases));
			}
			int nbCoups = 3;
			if (nbCases < 3) {
				nbCoups = nbCases;
			}
			List<Case> cases = tourOrdinateur(nbCoups);
			afficherPlateau();
			if (this.plateau.existeCasesLibres()) {
				cases = tourHumain(cases);
			} else {
				boolean libre = false;
				for (Case c : cases) {
					if (this.plateau.existeCaseLibreAutour(c.i, c.j)) {
						libre = true;
					}
				}
				if (libre) {
					cases = tourHumain(cases);
				}
			}
			for (Case c : cases) {
				calculScore(c);
			}
			afficherPlateau();

		}
	}

	public void calculScore(Case c) {
		List<List<Case>> casesVisitees = new ArrayList<List<Case>>();
		String[] couleurs = new String[8];
		int[] score = this.plateau.getAlign(c.i, c.j, Couleur.RAINBOW, couleurs, casesVisitees);
		/**
		 * for (int i=0; i<score.length; i++){ if (score[i]>=5){
		 * this.joueur1.addScore(score[i]); for (Case caseSuppr :
		 * casesVisitees.get(i)){ this.plateau.libere(caseSuppr.i, caseSuppr.j);
		 * } } }
		 **/
		for (int i = 0; i < 8; i++) {
			System.out.println(couleurs[i] + " " + score[i]);
		}
		//for (int n = 0; n < 8; n++) {
		//	int s = score[n];
		//	if (s >= 5) {
		//		for (Case caseSuppr : casesVisitees.get(n)) {
		//			this.plateau.libere(caseSuppr.i, caseSuppr.j);
		//		}
		//	}
		//}

		for (int n = 0; n < 8; n += 2) {
			if (couleurs[n].equals(couleurs[n + 1]) || couleurs[n].equals(Couleur.RAINBOW)
					|| couleurs[n + 1].equals(Couleur.RAINBOW)) {
				int s = score[n] + score[n + 1] - 1;
				if (s >= 5) {
					this.joueur1.addScore(s);
					for (Case caseSuppr : casesVisitees.get(n+1)) {
						this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
					for (Case caseSuppr : casesVisitees.get(n)) {
						this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
				}
			} else {
				if (score[n]>=5){
					this.joueur1.addScore(score[n]);
					for (Case caseSuppr : casesVisitees.get(n)) {
						this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
				}
				if (score[n+1]>=5){
					this.joueur1.addScore(score[n+1]);
					for (Case caseSuppr : casesVisitees.get(n+1)) {
						this.plateau.libere(caseSuppr.i, caseSuppr.j);
					}
				}
			}
		}
		JLabel text = (JLabel) this.scores.getComponent(0);
		this.scores.remove(text);
		this.scores.add(new JLabel(this.joueur1.nom + " : " + this.joueur1.getNbPoints() + " points"));
	}

	public void launch() {
		this.casesDepart = tourOrdinateur(3);
	}

	public void afficherPlateau() {
		System.out.println(this.joueur1.nom + " : " + this.joueur1.getNbPoints() + " points");
		this.plateau.afficherPlateau();
	}

	public List<Case> tourHumain(List<Case> casesDepart) {
		Case depart = joueur1.getCoup(casesDepart);
		System.out.println(this.plateau.getCasesLibres().size());
		Case arrivee = joueur1.getCoup(this.plateau.getCasesLibres());
		try {
			this.plateau.deplace(depart.i, depart.j, arrivee.i, arrivee.j);
			casesDepart.remove(depart);
			casesDepart.add(arrivee);
			return casesDepart;
		} catch (PasDeCheminException e) {
			System.out.println(e);
			return tourHumain(casesDepart);
		} catch (CaseOccupeeException e) {
			System.out.println(e);
			return tourHumain(casesDepart);
		} catch (CaseVideException e) {
			System.out.println(e);
			return tourHumain(casesDepart);
		}
	}

	@Override
	public List<Case> tourOrdinateur(int nbCases) {
		List<Case> cases = new ArrayList<Case>(nbCases);
		for (int i = 0; i < nbCases; i++) {
			String couleur = this.prochainsCoups.get(0);
			this.prochainsCoups.remove(0);
			Case c = this.joueur2.getCoup(this.plateau.getCasesLibres());
			cases.add(c);
			System.out.println(c);
			try {
				this.plateau.pose(c.i, c.j, couleur);
				plateau.getJeu().calculScore(plateau.taableauCases[c.i][c.j]);
				this.frame.setVisible(true);
			} catch (CaseOccupeeException e) {
				System.out.println(e);
				afficheMessage("Ordinateur "+e.toString()+ " "+c.getCouleur());
			}
		}
		return cases;
	}

	private boolean canStart() {
		return this.plateau.existeCasesLibres();
	}

	public synchronized void actionAFaire(Case c) {
		if (!this.fini) {
			if (this.pionSelectionne == null) {
				this.pionSelectionne = c;
			} else {
				try {
					this.plateau.deplace(this.pionSelectionne.i, this.pionSelectionne.j, c.i, c.j);
					this.frame.setVisible(true);
					this.casesDepart.remove(this.pionSelectionne);
					this.casesDepart.add(c);
					for (Case pion : casesDepart) {
						calculScore(pion);
					}
					int nbCases = this.plateau.getNbCasesLibres();
					if (this.prochainsCoups.size() <= 3) {
						this.prochainsCoups.addAll(this.creerProchainsCoups(nbCases));
					}
					int nbCoups = 3;
					if (nbCases < 3) {
						nbCoups = nbCases;
					}
					this.casesDepart = tourOrdinateur(nbCoups);
					this.pionSelectionne = null;
					if (!this.canStart()) {
						this.fini = true;
						afficheMessage("La partie est finie");
					}
				} catch (PasDeCheminException e) {
					System.out.println(e);
					this.pionSelectionne = null;
					this.afficheMessage(e.toString());
				} catch (CaseOccupeeException e) {
					System.out.println(e);
					this.pionSelectionne = null;
					this.afficheMessage(e.toString());
				} catch (CaseVideException e) {
					System.out.println(e);
					this.pionSelectionne = null;
					this.afficheMessage(e.toString());

				}
			}
		}
	}

	public static void main(String[] args) {
		KolorLines kl = new KolorLines(new JoueurHumain("Toto"), 10);
		kl.launch();
	}

}
