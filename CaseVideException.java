public class CaseVideException extends Exception{
  public final Case c;
  
  public CaseVideException(Case c){
    this.c = c;
  }
  
  public String toString(){
    return "La case "+c.toString()+" est vide !";
  }
}
