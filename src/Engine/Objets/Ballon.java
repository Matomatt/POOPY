package Engine.Objets;
import java.io.IOException;

import javax.swing.ImageIcon;

import Data.ImageManager;
import Settings.globalVar;
import Utilitaires.*;

public class Ballon extends AnimatedObject {
	double vit = 5;
	int direc= 0;  // je mets un int de direction pour keep le dernier moove en mémoire 0 NE 1 NW 2 SW 3 SE  


	public Ballon(double _x, double _y, int _dir, boolean _selfMoved)
	{
		super((int)_x/globalVar.tileWidth, (int)_y/globalVar.tileHeight, 3, 3, ObjectType.BALLON, true, false, _selfMoved, false);// x donné pas bon ? j'ai set la position de dépat
		x = _x;
		y = _y;
		direc = _dir;
		System.out.println("Ballon loaded");
		InitBallon();
	}
	
	public Ballon(int _x, int _y, int _dir, boolean _selfMoved)
	{
		super(_x, _y, 3, 3, ObjectType.BALLON, true, false, _selfMoved, false);// x donné pas bon ? j'ai set la position de dépat
		x = getX() + globalVar.tileWidth/2;
		y = getY() + globalVar.tileWidth/2;
		direc = _dir;
		InitBallon();
	}
	
	public Ballon(int _x, int _y, boolean _selfMoved)
	{
		super(_x, _y, 3, 3, ObjectType.BALLON, true, false, _selfMoved, false);// x donné pas bon ? j'ai set la position de dépat
		x = getX() + globalVar.tileWidth/2;
		y = getY() + globalVar.tileWidth/2;
		InitBallon();
	}

	private void InitBallon()
	{
		vitesseUpdate();
		try {
			ChangeSpriteTo("/ballon/ballon.png", globalVar.tileWidth/2, globalVar.tileHeight/2);
		} catch (IOException e) {
			new ErrorMessage("Couldn't load ballon.png...\n" + e.getLocalizedMessage());
		}

		r = globalVar.tileWidth/4;
		coordType = CoordType.CENTER;
		alwaysMoving=true;
		solid = false;
		//this.setLocation((int)x-((coordType == CoordType.CENTER)?(int)r:0), (int)y-((coordType == CoordType.CENTER)?(int)r:0));
		//Draw();
		//System.out.println("Init speed : " + vitesse[0] + ", " + vitesse[1]+"direction initial" + direc);

		//stopMovements=true;
	}
	
//	public boolean hitboxfast (Objet tocheck)// Non fini / fonctionel
//	{
//		boolean result=false;
//		double  i;
//		double bx,by;
//		for (i=0;i<3.14;i+=3.14/8)// A opti en finissant si colli ? 
//		{
//			bx=  x+Math.cos(i);
//			by=  y+Math.sin(i);
//
//			if (bx<= tocheck.x+tocheck.r)
//			{
//				if (by<= tocheck.y+tocheck.r)
//				{
//					if (bx>= tocheck.x)
//					{	
//						if (by>= tocheck.y)
//						{
//							direc+=1;// changement de direction pas encore intelligent 
//							result=true;
//						}
//					}
//				}
//			}
//		}
//
//		return result ;
//	}
	public boolean hitboxslow (Objet tocheck, boolean bounce)// ATTENTION il faut que ballon ait ses coord sur son centre et que x y de l'objet carré soit en haut a gauche
	{
		//System.out.println("hitboxslow " + tocheck.x + " " + tocheck.y+" vitesse x "+vitesse [0]+ " vitesse y "+ vitesse[1] +" direction " +direc ) ;
		boolean test=false;
		int tmpDirec = direc;
		
		if (getX()-r<=0)
		{
			test=true;
			if(direc==1)
				direc=0;
			else {
				direc=3;
			}
		}
		if (getX()+r>=globalVar.tileWidth*globalVar.nbTilesHorizontally)
		{
			
			test=true;
			if(direc==0)
				direc=1;
			else {
				direc=2;
			}
		}
		if (getY()-r<=0)
		{
			//System.out.println("y : " + (y-r));
			test=true;
			if(direc==0)
				direc=3;
			else {
				direc=2;
			}
		}
		if (getY()+r>=globalVar.nbTilesVertically*globalVar.tileHeight)
		{
			test=true;
			if(direc==2)
				direc=1;
			else {
				direc=0;
			}
		}
		
		if(getY()>=tocheck.getY()) // Collision bord droit ou gauche du carré 
		{
			if(getY()<=tocheck.getY()+tocheck.r)
			{
				if((getX()+r>=tocheck.getX())&&(getX()+r<=tocheck.getX()+tocheck.r)) // Cas bord gauche
				{
					test=true;
					if (direc==0)
						direc=1;
					if (direc==3)
						direc=2;
				}

				else if (getX()-r<=tocheck.getX()+tocheck.r&&getX()-r>=tocheck.getX()) 	// Cas bord droit 
				{
					test=true;
					if (direc==1)
						direc=0;
					else if (direc==2)
						direc=3;
				}
			}
		}
		if (test!=true)
		{
			if (getX()>=tocheck.getX())  // cas bord haut ou bas
			{
				if (getX()<=tocheck.getX()+tocheck.r)
				{
					if ((getY()+r>=tocheck.getY())&&(getY()+r<=tocheck.getY()+tocheck.r))// Cas Haut du carré
					{
						test=true;
						if(direc==3)
							direc=0;
						else if (direc==2)
							direc=1;
					}
					else  if(getY()-r<=tocheck.getY()+tocheck.r&&getY()-r>=tocheck.getY()) //Cas Bas du carré 
					{
						test=true;
						if (direc==0)
							direc=3;
						else if (direc==1)
							direc=2;
					}
				}
			}
		}
		if(test!=true)
		{
			if (Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY())*(tocheck.getY()-getY()))<=r)// Distance par rapport aux coins HG
			{
				// si plus proche de sud ballon plutot que de east ballon 
				if (Math.sqrt((tocheck.getX()-getX()+r)*(tocheck.getX()-getX()+r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) > Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()+r)*(tocheck.getY()-getY()+r)))
						{
							direc+=1;
							if(direc==4)
								direc=3;
						}
				// si plus proche de East 
				else if (Math.sqrt((tocheck.getX()-getX()+r)*(tocheck.getX()-getX()+r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) < Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()+r)*(tocheck.getY()-getY()+r)))
				
				{
					direc-=1;
					if(direc==-1)
						direc=3;
				}
				else 
				{
					if (direc<2)
						direc+=2;
					else
						direc-=2;
				}
			
				test=true;
				//vitesseUpdate();
				//toPrintAtTheEnd = "BallonCollisionG"+direc + "  / x ballon "+x+" y ballon"+y;
			}
			else if (Math.sqrt(((tocheck.getX()+tocheck.r)-getX())*((tocheck.getX()+tocheck.r)-getX()) + (tocheck.getY()-getY())*(tocheck.getY()-getY()))<=r)//HD
			{
				// si plus proche de south ballon plutot que de WEST ballon 
				if (Math.sqrt((tocheck.getX()-getX()-r)*(tocheck.getX()-getX()-r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) > Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()+r)*(tocheck.getY()-getY()+r)))
						{
							direc+=1;
							if(direc==4)
								direc=3;
						}
				// si plus proche de West 
				else if (Math.sqrt((tocheck.getX()-getX()-r)*(tocheck.getX()-getX()-r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) > Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()+r)*(tocheck.getY()-getY()+r)))
				
				{
					direc-=1;
					if(direc==-1)
						direc=3;
				}
				else 
				{
					if (direc<2)
						direc+=2;
					else
						direc-=2;
				}
				test=true;
			}
			else if (Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + ((tocheck.getY()+tocheck.r)-getY())*((tocheck.getY()+tocheck.r)-getY()))<r)//BG
			{
				if (Math.sqrt((tocheck.getX()-getX()+r)*(tocheck.getX()-getX()+r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) > Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()-r)*(tocheck.getY()-getY()-r)))
				{
					direc+=1;
					if(direc==4)
						direc=3;
				}
		// si plus proche de East 
				else if (Math.sqrt((tocheck.getX()-getX()+r)*(tocheck.getX()-getX()+r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) < Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()-r)*(tocheck.getY()-getY()-r)))
		
				{
					direc-=1;
					if(direc==-1)
						direc=3;
				}
					else 
					{
						if (direc<2)
							direc+=2;
						else
							direc-=2;
					}
				test=true;
			}
			else if (Math.sqrt(((tocheck.getX()+tocheck.r)-getX())*((tocheck.getX()+tocheck.r)-getX()) + ((tocheck.getY()+tocheck.r)-getY())*((tocheck.getY()+tocheck.r)-getY()))<r)//BD
			{
				if (Math.sqrt((tocheck.getX()-getX()-r)*(tocheck.getX()-getX()-r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) > Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()-r)*(tocheck.getY()-getY()-r)))
				{
					direc+=1;
					if(direc==4)
						direc=3;
				}
		// si plus proche de East 
				else if (Math.sqrt((tocheck.getX()-getX()-r)*(tocheck.getX()-getX()-r) + (tocheck.getY()-getY())*(tocheck.getY()-getY())) < Math.sqrt((tocheck.getX()-getX())*(tocheck.getX()-getX()) + (tocheck.getY()-getY()-r)*(tocheck.getY()-getY()-r)))
		
				{
					direc-=1;
					if(direc==-1)
						direc=3;
				}
					else 
					{
						if (direc<2)
							direc+=2;
						else
							direc-=2;
					}
				test=true;
			}

		}


		// 		 if (x+r>=tocheck.x)// Lire les point cardinaux du ballon W
		// 		 {
		// 			 if(x-r<=tocheck.x+tocheck.r)//E
		// 			 {
		// 				if (y+r>=tocheck.r)//N
		// 				{
		// 		 			 if(y-r<=tocheck.y+tocheck.r)//S
		// 		 			 {
		// 		 				if (Math.sqrt((tocheck.x*tocheck.x-x*x + tocheck.y*tocheck.y-y*y))<r)// Distance par rapport aux coins HG
		// 		 				{
		// 		 					if (Math.sqrt((tocheck.x+tocheck.r)*(tocheck.x+tocheck.r) + tocheck.y*tocheck.y-y*y)<r)//HD
		// 	 		 				{
		// 		 		 				if (Math.sqrt((tocheck.x*tocheck.x-x*x + (tocheck.y+r)*(tocheck.y+r)-y*y))<r)//BG
		// 		 		 				{
		// 		 		 					if (Math.sqrt((tocheck.x+tocheck.r)*(tocheck.x+tocheck.r) + (tocheck.y+r)*(tocheck.y+r)-y*y)<r)//BD
		// 		 	 		 				{
		// 		 		 						if (direc<2)
		// 		 		 						direc+=2;// changement de direction pas encore intelligent 
		// 		 		 						else
		// 		 		 						direc-=2;
		// 		 		 						test=true;
		// 		 							}
		// 								}
		// 							}
		//						}
		// 		 			 }
		// 				}
		// 			 }
		// 			 else {
		// 			 }
		// 		 }

		// 			if (test==true)
		// 			{
		//				System.out.println("BallonCollision"+direc);
		//				System.out.println("x ballon "+x+"y ballon"+y);
		// 			}

		//Si il doit pas rebondir (collision avec un objet transparent) on modifie pas direc
		if (!bounce)
			direc = tmpDirec;
		if (test==true && bounce)
		{
			//System.out.println(toPrintAtTheEnd);
			vitesseUpdate();
			MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
		}

		return test;
	}


	void vitesseUpdate ()
	{
		if (direc == 0|| direc==3) //NE SE
			vitesse[0]=vit;  
		else	if (direc== 1|| direc== 2 )// NW SW 
			vitesse[0]=-vit;
		if (direc == 0|| direc==1) //NE NW
			vitesse[1]=-vit;
		else   if (direc== 3|| direc== 2 )// SE SW 
			vitesse[1]=vit;
		//move();
	}
	
	public String SavingInfo()
	{
		return (int)getX() + " " + (int)getY() + " " + direc;
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
