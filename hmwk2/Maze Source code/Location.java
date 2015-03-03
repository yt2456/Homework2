
public class Location 
{
   private int x;
   private int y;
   
   public Location(int a, int b)
   {
	   x = a;
	   y = b;
   }
   
   public int getX()
	{ 
		return x; 
	}
	
	public int getY()
	{ 
		return y; 
	}
	
	public String toString()
	{ 
		return "(" + x + ", " + y + ")"; 
	}
}
