public class CaseVideException extends Exception{
  public final Plateau.Case c;
  
  public CaseVideException(Plateau.Case c){
    this.c = c;
  }
  
  public String toString(){
    return "La case "+c.toString()+" est vide !";
  }
}
