package Engine;
import Utilitaires.*;

public class Ballon extends AnimatedObject {
		
	int vitesse = 3;
	int direc= 0;  // je met un int de direction pour keep le dernier moove en mémoire 0 NE 1 NW 2 SW 3 SE  


	public Ballon(int _x, int _y) 
	{
		super(_x, _y, 0.6, 0.6, ObjectType.BALLON, true, false);
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
							direc+=1;// changement de direction pas encore intelligent 
							result=true;
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
 		 		 						direc+=1;// changement de direction pas encore intelligent 
 		 		 						test=true;
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

 	 /*
 	 public int NextCaseX() //
 	 {
 		if (direc == 0|| direc==3) //NE SE
 			return xInMap+vitesse;  // le calcul xinmap+vitesse doit pas être fait indépendament ? j'ai repris le schéma pour +1
 		if (direc== 1|| direc== 2 )// NW SW 
 			return xInMap-vitesse;
 		return xInMap;
 	}
 	public int NextCaseY()
 	{
 		if (direc == 0 || direc== 1) // NE NW
 			return yInMap+vitesse;
 		if (direc== 2 || direc == 3) // SW SE 
 			return yInMap-vitesse;
 		return yInMap;
 	}
 	*/
 	 
 	 /*
 	public boolean Move(ArrayList<Objet> test)
	{
 		int i;
 		for (i=0;i<test.size();i++)
 			if(hitboxslow(test.get(i))==true)
 			{
 			// directionchange();	// coder la fonction pour bien changer de direction ici 
 			}
 				xInMap = NextCaseX();
 				yInMap = NextCaseY();
		return true;
	}*/
 	
 	 
}
