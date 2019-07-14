package eg.edu.alexu.csd.filestructure.redblacktree.cs19_cs61;

import org.junit.Assert;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class Main {

	public static void main(String[] args) {
		
		@SuppressWarnings("unchecked")
		IRedBlackTree<Integer, Integer> rbt = new RedBlackTree<>();
		
		
		rbt.insert(1, 1);
		@SuppressWarnings("rawtypes")
		INode root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(2, 2);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(3, 3);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		
		/*rbt.insert(20, 20);
		@SuppressWarnings("rawtypes")
		INode root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(15, 15);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(10, 10);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(15, 15);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(7, 7);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(9, 9);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(12, 12);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(24, 24);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(22, 22);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(13, 13);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());
		rbt.insert(11, 11);
		root = rbt.getRoot();
		System.out.println("ROOT IS : " + root.getValue());*/
		
		
		/*rbt.insert(11, 11); 
		rbt.insert(1, 1); 
		rbt.insert(14, 14); 
		rbt.insert(2, 2); 
		rbt.insert(7, 7); 
		rbt.insert(5, 5); 
		
		System.out.println(rbt.search(11));
		System.out.println(rbt.search(1));
		System.out.println(rbt.search(14));
		System.out.println(rbt.search(2));
		System.out.println(rbt.search(7));
		System.out.println(rbt.search(5));*/


		
		
		/*for (int i = 1; i <= 7; i++) {
			//rbt.insert(i, i);
			System.out.println(rbt.search(i));
		}*/
		
		/*for (int i = 1; i <= 6; i++) {
			System.out.println(rbt.search(i));
			//rbt.delete(i);
		}*/

	}

}
