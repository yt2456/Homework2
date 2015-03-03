import java.util.*;
public class easyQueue{
private LinkedList queue;
public easyQueue( ) {
queue=new LinkedList();
}
public boolean isEmpty(){
if(queue.isEmpty()) return true;
else return false;
}
public void enQueue(Object i) {
queue.addLast(i);
}
public Object deQueue() {
if (queue.isEmpty()){
System.out.println("Queue is empty!");
return null;
} else {
Object result=queue.getFirst();
queue.removeFirst();
return result;
}
}
public void printQueue() {
System.out.println("the Queue is: " + queue);
}
}