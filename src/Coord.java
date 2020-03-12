import java.awt.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;


public class Coord {

	double x=0;
	double y=0;
	public Coord()
	{
		x=0;
		y=0;
	}
	public Coord(double a,double b)
	{
		x=a;
		y=b;
	}
	public Coord(int a,int b)
	{
		x=a;
		y=b;
	}
	public void set (double X,double Y)
	{ 
		x=X;
		y=Y;
	}
	public double getX ()
	{
		return x;
	}
	public double getY ()
	{
		return y;
	}
}
