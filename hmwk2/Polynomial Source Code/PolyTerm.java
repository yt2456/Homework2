
public class PolyTerm 
{
	private double coef;
	private int exponent;
	
	PolyTerm()
	{
		coef=0.0;
		exponent=0;
	}
	
	PolyTerm(double c, int e)
	{
		coef=c;
		exponent=e;
	}
	
	public void setPolyTerm(double c, int e)
	{
		coef=c;
		exponent=e;
	}
	
	public int getExponent()
	{ 
		return exponent; 
	}
	
	public double getCoef()
	{ 
		return coef; 
	}
	
	public String toString()
	{ 
		return coef + " x^" + exponent; 
	}
}