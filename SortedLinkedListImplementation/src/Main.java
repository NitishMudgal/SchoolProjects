import java.util.Scanner;

public class Main {
	public static void main(String args[]){
		System.out.println("Enter the strings");
		Scanner sc = new Scanner(System.in);
		SortedLinkedList list = new SortedLinkedList();
		for(int i=0; i<5; i++){
			System.out.println("Enter: "+i+ " number");
			list.add(sc.next());
		}
		//System.out.println(list.size());
		//list.display();
		//System.out.println(list.isEmpty());
		//System.out.println(list.contains("nitish"));
		//System.out.println(list.removeFirst());
		System.out.println(list.removeAt(3));
		list.display();
		
	}
}
