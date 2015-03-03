import java.util.Scanner;


public class PolyList 
{
	Scanner input = new Scanner(System.in);
	
	PolyLinkedList[] polyarray = new PolyLinkedList[10];
	
	public static void main(String[] args)
	{
		PolyList plist = new PolyList();
		plist.makeList();
	}
	
    public void makeList()
    {
    	
    	String function = " ";
    	
    	while(function.equals("q") == false )
    	{
    		System.out.println("Please enter what you want: \n "
    				+ "i for input \n "
    				+ "a for add \n "
    				+ "s for subtract \n "
    				+ "m for multiply \n "
    				+ "e for evaluate \n "
    				+ "p for print \n "
    				+ "q for quit \n ");
    		
    		function = input.next();
    				
    		if(function.equals("i") == true)
    		{
                input();
    		}
    		
    		if(function.equals("a") == true)
    		{
    			add();
    		}
    		
    		if(function.equals("s") == true)
    		{
    			subtract();
    		}
    		
    		if(function.equals("m") == true)
    		{
    			multiply();
    		}
    		
    		if(function.equals("e") == true)
    		{
    			 System.out.println("Please indicate the indice of the polymial and the value to evaluate it at:");
    			 int loc = input.nextInt();
    			 int value = input.nextInt();
    			 
    			 evalPoly(polyarray[loc], value);
    		}
    		
    		if(function.equals("p"))
    		{
    			System.out.println("Please indicate the indice of the polymial to print");
    			int loc = input.nextInt();
    			
    			polyarray[loc].printList();
    		}
    	}
    }
    
    public void evalPoly(PolyLinkedList L1, int value)
    {
       int eval = 0;
       Node current = L1.header;
       
       while(current != null)
       {
    	   eval += current.data.getCoef() * (value^current.data.getExponent());
       }
       
       System.out.println("The evaluation is: " + eval);
    }
    
    public void add()
    {
    	System.out.println("Please indicate the indices of the polynomial 1 and 2 to be added and the indice of the resultant sum: ");
    	int loc1 = input.nextInt();
    	int loc2 = input.nextInt();
    	int loc3 = input.nextInt();
    	
    	polyarray[loc3] = new PolyLinkedList();
    	
    	Node current = polyarray[loc1].header;
    	
    	while(current != null)
    	{
    		polyarray[loc3].add(current.data);
    		current = current.next;
    	}
    	
    	current = polyarray[loc2].header;
    	
    	while(current != null)
    	{
    		polyarray[loc3].add(current.data);
    		current = current.next;
    	}
    	
    	polyarray[loc3].printList();
    	
    }
    
    public void subtract()
    {
    	System.out.println("Please indicate the indices of the polynomial 1 and 2 to be subtracted and the indice of the resultant sum: ");
    	int loc1 = input.nextInt();
    	int loc2 = input.nextInt();
    	int loc3 = input.nextInt();
    	
    	polyarray[loc3] = new PolyLinkedList();
    	
    	Node current = polyarray[loc1].header;
    	
    	while(current != null)
    	{
    		polyarray[loc3].add(current.data);
    		current = current.next;
    	}
    	
    	current = polyarray[loc2].header;
    	
    	while(current != null)
    	{
    		current.data.setPolyTerm(current.data.getCoef() * -1, current.data.getExponent());
    		polyarray[loc3].add(current.data);
    		current = current.next;
    	}
    	
    	polyarray[loc3].printList();
    }
    
    public void multiply()
    {
    	System.out.println("Please indicate the indices of the polynomial 1 and 2 to be multiplied and the indice of the resultant sum: ");
    	int loc1 = input.nextInt();
    	int loc2 = input.nextInt();
    	int loc3 = input.nextInt();
    	
    	polyarray[loc3] = new PolyLinkedList();
    	
    	Node current1 = polyarray[loc1].header;
    	Node current2;
    	
    	for(int x = 0; x < polyarray[loc1].size(); x++)
    	{
    		current2 = polyarray[loc2].header;
    		for(int y = 0; y < polyarray[loc2].size(); y++)
    		{
    		   PolyTerm newTerm = new PolyTerm(current1.data.getCoef() * current2.data.getCoef(), current1.data.getExponent() * current2.data.getExponent());	
    		   polyarray[loc3].add(newTerm);
    		   current2 = current2.next;
    		}
    		current1 = current1.next;
    	}
    	
    	polyarray[loc3].printList();
    }

    
    public void input()
    {
    	
    	System.out.println("input: enter index number of polynomial and how many terms");
		int index = input.nextInt();
		int terms = input.nextInt();
		double coef;
		int exp;
		PolyTerm term;
		
		polyarray[index] = new PolyLinkedList();
		
		for(int x = 0; x < terms; x++)
		{
			System.out.println("enter coef and exponent for term " + (x+1));
			coef = input.nextDouble();
			exp = input.nextInt();
			term = new PolyTerm(coef, exp);
			
			polyarray[index].add(term);
		}
		
		polyarray[index].printList();
    }
    }
