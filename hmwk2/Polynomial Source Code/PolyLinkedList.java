
public class PolyLinkedList 
{
   protected Node header;
   
   public PolyLinkedList()
   {
	  header = null;   
   }
   
   public Node insertInOrder(Node current, Node nod)
   {
	   Node next = current.next;
	   
	   if(current.next == null && current == header)
	   {
		   if(nod.data.getExponent() == current.data.getExponent())
		   {
			   current.data.setPolyTerm(nod.data.getCoef() + current.data.getCoef(),current.data.getExponent());
			   return null;
		   }
		   
		   if(nod.data.getExponent() < current.data.getExponent())
		   {
			   return current;
		   }
		   else
		   {
			   if( nod.data.getExponent() > current.data.getExponent())
			   {
				   return nod;
			   }
		   }
	   }
	   else
	   {
		   while (current != null)
		   {
			   if(nod.data.getExponent() == current.data.getExponent())
			   {
				   current.data.setPolyTerm(nod.data.getCoef() + current.data.getCoef(),current.data.getExponent());
				   return null;
			   }
			   
			   if( nod.data.getExponent() > current.data.getCoef() && current == header)
			   {
				   return nod;
			   }
			   
			   if( next != null)
			   {
				   if(nod.data.getExponent() < current.data.getExponent() && nod.data.getExponent() > next.data.getExponent())
				   {
					   return current;
				   }
				   
				   current = current.next;
				   next = current.next;
			   }
			   else
			   {
				   current = current.next;
			   }
		   
		   }
	   }
	   
	   
	   return current;
  
    }
	   
	  
   
   public void add (PolyTerm term)
   {
	   Node nod = new Node(term, null);
	   Node loc;
	   Node current;
   
	   if (header == null)
	   {
		   header = nod;
	   }
	   else
	   {
		   current = header;
		   
		   loc = insertInOrder(current, nod);
		   
		   if( loc != null)
		   {
			   if( loc == nod)
			   {
				   nod.next = current;
				   header = nod;
			   }
			   else
			   {
				   nod.next = loc.next;
				   loc.next = nod;
			   }
		   }
	   }
   }

   public int size()
   {
	   int dataSize = 0;
	   Node current = header;
	   
	   while(current.next != null)
	   {
	      dataSize++;
		  current = current.next;
	   }
	   
	   return dataSize;
   }
   
   public void printList()
   {
	   String content = "Data Contents: ";
	   Node current = header;
	   
	   while(current != null)
	   {
		   content += current.data;
		   if( current.next != null)
		   {
			   content += " + ";
		   }
		   current = current.next;
	   }
	   
	   System.out.println(content + "\n");
   }
   
   public boolean isContained(PolyTerm data)
   {
	   Node current = header;
	   
	   while(current != null)
	   {
		   if( data.equals(current.data))
		   {
			   return true;
		   }
		   
		   current = current.next;
	   }
	   
	   return false;
   }
   
 
}
