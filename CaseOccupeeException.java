public class CaseOccupeeException extends Exception{
  public final Case c;
  
  public CaseOccupeeException(Case c){
    this.c = c;
  }
  
  public String toString(){
    return "La case "+c.toString()+" est occupée !";
  }

}
