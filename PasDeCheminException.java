public class PasDeCheminException extends Exception{
  public final Case depart;
  public final Case arrivee;
  
  public PasDeCheminException(Case depart, Case arrivee){
    this.depart = depart;
    this.arrivee = arrivee;
  }
  
  public String toString(){
    return "Il n'existe pas de chemin entre la case "+depart.toString()+" et la case "+arrivee.toString()+" !";
  }

}
