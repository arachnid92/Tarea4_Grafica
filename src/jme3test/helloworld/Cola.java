package jme3test.helloworld;
import java.util.ArrayDeque;


public class Cola{
	
	ArrayDeque <String> cola;
	
	public Cola(){
		cola = new ArrayDeque <String>();
	}
	
	public synchronized void enqueue(String item){
		cola.addLast(item);
	}
	
	public synchronized String dequeue(){
		return cola.pollFirst();
	}
	
}
