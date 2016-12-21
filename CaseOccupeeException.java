public class CaseOccupeeException extends Exception{
  public final Plateau.Case c;
  
  public CaseOccupeeException(Plateau.Case c){
    this.c = c;
  }
  
  public String toString(){
    return "La case "+c.toString()+" est occupée !";
  }

}