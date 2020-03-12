import java.awt.*;
import java.util.*;
import java.lang.*;
import java.math.*;

public class Ballon extends Objet{

	


	public Ballon(double _x, double _y) 
	{
		super(_x, _y);
	}
 	 
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
 		 if (x+r>=tocheck.r)// Lire les point cardinaux du ballon W
 		 {
 			 if(x-r<=tocheck.x+tocheck.r)//E
 			 {
 				if (y+r>=tocheck.r)//N
 				{
 		 			 if(y-r<=tocheck.y+tocheck.r)//S
 		 			 {
 		 				if (Math.sqrt((tocheck.x*tocheck.x-x*x + tocheck.y*tocheck.y-y*y))<r)// Distance par rapport aux coins HG
 		 				{
 		 					if (Math.sqrt((tocheck.x+tocheck.r)*(tocheck.x+tocheck.r) + tocheck.y*tocheck.y-y*y)<r)//HD
 	 		 				{
 		 		 				if (Math.sqrt((tocheck.x*tocheck.x-x*x + (tocheck.y+r)*(tocheck.y+r)-y*y))<r)//BG
 		 		 				{
 		 		 					if (Math.sqrt((tocheck.x+tocheck.r)*(tocheck.x+tocheck.r) + (tocheck.y+r)*(tocheck.y+r)-y*y)<r)//BD
 		 	 		 				{
 		 		 						
 		 							}
 								}
 							}
						}
 		 			 }
 				}
 			 }
 		 }
 			 
 		 
 		 return test;
 	 }

}
