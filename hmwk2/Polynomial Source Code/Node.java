
public class Node
{
   protected PolyTerm data;
   protected Node next;
   
   public Node(PolyTerm x, Node n)
   {
	   data = x;
	   next = n;
   }
   
   public Node()
   {
	   data = null;
	   next = null;
   }
}
