public class PasDeCheminException extends Exception{
  public final Plateau.Case depart;
  public final Plateau.Case arrivee;
  
  public PasDeCheminException(Plateau.Case depart, Plateau.Case arrivee){
    this.depart = depart;
    this.arrivee = arrivee;
  }
  
  public String toString(){
    return "Il n'existe pas de chemin entre la case "+depart.toString()+" et la case "+arrivee.toString()+" !";
  }

}