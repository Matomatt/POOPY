import java.awt.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class Ballon extends Objet{

	



 	 public boolean hitboxfast (Objet tocheck)
{
	 boolean result=false;
	 double  i;
	 double bx,by;
	 for (i=0;i<3.14;i+=3.14/8)// A opti en finissant si colli ? 
	 {
		bx=  x+Math.cos(i);
		by=  y+Math.sin(i);
	 
	 if (bx<= tocheck.x+tocheck.r)
	 {
		 if (by<= tocheck.y+tocheck.r)
		 {
			 if (bx>= tocheck.x)
			 {
				 if (by>= tocheck.y)
				 {
					 
				 }
			 }
		 }
	 }
	 }
	 
	 return result ;
}
 	 public boolean hitboxslow (Objet tocheck)
 	 {
 		 boolean test=false;
 		 if (x+r>=tocheck.r)
 		 {
 			 if(x-r<=tocheck.x+tocheck.r)
 			 {
 				if (y+r>=tocheck.r)
 				{
 		 			 if(y-r<=tocheck.y+tocheck.r)
 		 			 {
 		 				if (Math.sqrt((tocheck.x*tocheck.x-x*x + tocheck.y*tocheck.y-y*y))<r)
 		 				{
 		 					if (Math.sqrt((tocheck.x+tocheck.r)*tocheck.x-x*x + tocheck.y*tocheck.y-y*y)<r)
 	 		 				{
 		 						
 							}
						}
 		 			 }
 				}
 			 }
 		 }
 			 
 		 
 		 return test;
 	 }

}
