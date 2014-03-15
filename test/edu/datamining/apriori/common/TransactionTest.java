package edu.datamining.apriori.common;

import org.omg.PortableInterceptor.SUCCESSFUL;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

	// Tests fromRow method which only takes string parameter
	public void testFromRowString() {
		String strLine = "1,2,3,4,5";
		Transaction trnx = Transaction.fromRow(strLine);
		
		// Check if there are really five items in the transaction
		assertEquals(5, trnx.getPurchasedItems().getCount());
		
		//...and the items are correct.
		assertTrue(trnx.getPurchasedItems().contains(new Item(1)));
		assertTrue(trnx.getPurchasedItems().contains(new Item(2)));
		assertTrue(trnx.getPurchasedItems().contains(new Item(3)));
		assertTrue(trnx.getPurchasedItems().contains(new Item(4)));
		assertTrue(trnx.getPurchasedItems().contains(new Item(5)));
	}
	
	//  Tests fromRow method which takes string and itemset as parameter
	public void testFromRowStringItemSet() {
		// Create an itemset that contains all the items on the market. 
		// A transaction cannot have any item which is not in this set.
		ItemSet allPossibleItems = new ItemSet(1,2,3,4,5);
		
		// Test 1 - Valid transaction. All purchased items are valid.  
		String strLine = "2,4,1,3";
		try {
			Transaction trnx = Transaction.fromRow(strLine, allPossibleItems);
			
			// Check if there are really four items in the transaction
			assertEquals(4, trnx.getPurchasedItems().getCount());
			
			//...and the items are correct.
			assertTrue(trnx.getPurchasedItems().contains(new Item(1)));
			assertTrue(trnx.getPurchasedItems().contains(new Item(2)));
			assertTrue(trnx.getPurchasedItems().contains(new Item(3)));
			assertTrue(trnx.getPurchasedItems().contains(new Item(4)));
			
		} catch (ApplicationException ex) {
			// This transaction must be parsed. If it threw an exception, test fails
			fail("A valid transaction could not be parsed");
		}
		
		// Test 2 - Invalid transaction. Item 6 does not exist on the market.
		strLine = "2,4,1,3,6";
		try {
			Transaction trnx = Transaction.fromRow(strLine, allPossibleItems);
			
			fail("Code must never reach here. It must have thrown an exception");
			
		} catch (ApplicationException ex) {
			
		}
		
		
		
	}
	
	
}
