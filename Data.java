//stabilire se data è corretta
import java.util.Scanner;
import java.lang.System;
public class Data{
  public static void main(String arg[]){
    Scanner in=new Scanner (System.in);
    String s, sg, sm, sa;
    int pos1, pos2, g, m, a;
    System.out.print ("Inserire data nel formato gg/mm/aa: ");
    s=in.nextLine();
    pos1=s.indexOf("/");
    sg=s.substring(0, pos1);
    pos2=s.indexOf("/",pos1+1);
    sm=s.substring(pos1+1, pos2);
    sa=s.substring(pos2+1);
    g=Integer.parseInt(sg);
    m=Integer.parseInt(sm);
    a=Integer.parseInt(sa);
    boolean ok1, ok2;
    ok1=g>0&&g<=31&&m>0&&m<=12;
    switch (m){
        case 4:
        case 6:
        case 9:
        case 11:
          ok2=g<=30;
          break;
      case 2:
        if (a%4==0&&(a%100!=0||a%400==0))         //condizione anno bisestile
          ok2=g<=29;
        else
          ok2=g<=28;
        break;
      default:
        ok2=g<=31;
      }
    if (ok1&&ok2)
      System.out.println("La data è corretta");
    else
      System.out.println("La data è errata");
  }
}
