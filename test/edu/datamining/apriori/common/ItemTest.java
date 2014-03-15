package edu.datamining.apriori.common;

import junit.framework.TestCase;

// Class to test Item objects
public class ItemTest extends TestCase {

	// Tests Item constructor. getValue() must return 
	// the value given in constructor.
	public void testItemConstruction(){
		Item item = new Item(3);
		assertEquals(3, item.getValue());
	}
	
	// Creates two Item objects with same integer values and compares
	// them. Result must be 0.
	public void testItemEquality() {
		Item item1 = new Item(5);
		Item item2 = new Item(5);
		
		int comparisonResult = item1.compareTo(item2);
		assertEquals(0, comparisonResult);
		
		boolean bResult = item1.equals(item2);
		assertEquals(true, bResult);	
	}
	
	// Creates two Item objects and compares them.
	public void testItemInequality() {
		// item2 is greater than item1. So comparison must return -1. 
		Item item1 = new Item(3);
		Item item2 = new Item(5);
		
		int comparisonResult = item1.compareTo(item2);
		assertEquals(-1, comparisonResult);
		
		// item1 is greater than item2. So comparison must return 1.
		item1 = new Item(9);
		item2 = new Item(4);
		
		comparisonResult = item1.compareTo(item2);
		assertEquals(1, comparisonResult);
	}
}
