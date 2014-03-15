package edu.datamining.apriori.common;

import junit.framework.TestCase;

public class ItemSetCollectionTest extends TestCase {

	// Tests the default constructor with no parameters.
	public void testConstruction1() {
		ItemSetCollection collection = new ItemSetCollection();
		
		// Check if there are really no itemsets in the collection
		assertEquals(0, collection.getCount());
	}

	// Tests the default constructor which takes varaible number of itemsets
	public void testConstruction2() {
		ItemSetCollection collection = new ItemSetCollection(new ItemSet(1, 2), new ItemSet(1, 3));
		
		// Check if there are really 2 items in the collection
		assertEquals(2, collection.getCount());
		
		// Collection must have itemsets {1, 2} and {1, 3}. Check their validity with new items
		// with the same values.
		assertTrue(collection.contains(new ItemSet(1, 2)));
		assertTrue(collection.contains(new ItemSet(1, 3)));
		
		// Check if an item which must not be in the set is really not in the set. 
		assertFalse(collection.contains(new ItemSet(2, 3)));	
	}
	
	// Tests the addSet method taking only ItemSet parameter
	public void testAddSetItemSet() {
		ItemSetCollection collection = new ItemSetCollection();
		ItemSet newSet = new ItemSet(1, 2, 3);
		collection.addSet(newSet);
		
		// Check if there is really 1 itemset in the collection
		assertEquals(1, collection.getCount());
		
		// ...and that itemset is really what we added
		assertTrue(collection.contains(newSet));
		
		// ...and its support count is 0
		assertEquals(0, collection.getSupportCount(newSet));
	}

	// Tests the addSet method taking ItemSet parameter and support count parameters
	public void testAddSetItemSetInt() {
		ItemSetCollection collection = new ItemSetCollection();
		ItemSet newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		
		// Check if there is really 1 itemset in the collection
		assertEquals(1, collection.getCount());
		
		// ...and that itemset is really what we added
		assertTrue(collection.contains(newSet));
		
		// ...and its support count is 0
		assertEquals(5, collection.getSupportCount(newSet));
	}

	// Tests the removeSet method
	public void testRemoveSet() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		
		// Verify items are really added.
		assertEquals(2, collection.getCount());
		
		// Remove the itemset {1, 2}
		collection.removeSet(new ItemSet(1, 2));
		
		// Verify the count is decreased by one
		assertEquals(1, collection.getCount());
		
		// Check if the given itemset is really removed
		assertFalse(collection.contains(new ItemSet(1, 2)));
		
		// ...and the other is left intact
		assertTrue(collection.contains(new ItemSet(1, 3)));
	}
	
	// Tests the updateSet method
	public void testUpdateSet() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet, 1);
		
		// Update {1, 3}'s support count to 7 
		collection.updateSet(new ItemSet(1, 3), 7);
		
		// Verify its new count is 7
		assertEquals(7, collection.getSupportCount(new ItemSet(1, 3)));
		
		//...and the others are not touched.
		assertEquals(5, collection.getSupportCount(new ItemSet(1, 2)));
		assertEquals(1, collection.getSupportCount(new ItemSet(2, 3)));
		
		//...and there are still 3 itemsets in the collection
		// (just to handle the case, where instead of updating current count
		// a new itemset is inserted)
		assertEquals(3, collection.getCount());
	}
	
	// Tests addSetOrUpdateSupportCount method.
	public void testAddSetOrUpdateSupportCount() {
		// If a set is in the collection its support count is incremented
		// otherwise it is added with support count 1.
		
		// Create collection and add itemsets
		ItemSetCollection collection = new ItemSetCollection();
		collection.addSet(new ItemSet(1, 2), 1);
		collection.addSet(new ItemSet(1, 3), 5);
		collection.addSet(new ItemSet(2, 3), 3);
		
		// Test 1 - Update support count
		// {1, 2} is an existing ItemSet. So it's support count
		// must be updated as 2.
		collection.addSetOrUpdateSupportCount(new ItemSet(1, 2));
		// Check if it succeeded.
		assertEquals(2,	collection.getSupportCount(new ItemSet(1, 2)));
		
		// Test 2 - Add new itemset
		// {2, 4} is a new ItemSet. So it's support count
		// must be add as 1.
		collection.addSetOrUpdateSupportCount(new ItemSet(2, 4));
		// Check if it succeeded.
		assertEquals(1,	collection.getSupportCount(new ItemSet(2, 4)));
		
	}
	
	// Tests the getCount method
	public void testGetCount() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add item and check count
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		assertEquals(1, collection.getCount());
		
		// Add 2 more and check count
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet, 1);
		assertEquals(3, collection.getCount());
		
		// Remove an item and check count
		collection.removeSet(new ItemSet(1,3));
		assertEquals(2, collection.getCount());
	}

	// Tests the sort method
	public void testSort() {
		// Currently what sort does not make much sense. It calls all
		// itemsets sort methods. Since ItemSet.sort() has its own 
		// unit test, it's redundant to check it here again.
		
		// If the implementation changes this method must be modified
		// accordingly.
	}

	// Tests the clear method
	public void testClear() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet, 1);

		// Verify items are really added.
		assertEquals(3, collection.getCount());
		
		collection.clear();
		
		// Verify all items are really removed 
		assertEquals(0, collection.getCount());
	}

	// Tests contains() method 
	public void testContains() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet, 1);

		// Verify if the added sets can be found in the collection
		assertTrue(collection.contains(new ItemSet(1, 2)));		
		assertTrue(collection.contains(new ItemSet(1, 3)));
		assertTrue(collection.contains(new ItemSet(2, 3)));
		
		// Verify that another itemset is not found
		assertFalse(collection.contains(new ItemSet(2, 4)));
	}

	// Tests the getSupportCount method
	public void testGetSupportCount() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet, 1);
		
		// Verify the support counts are really what they are supposed to be..
		assertEquals(5, collection.getSupportCount(new ItemSet(1, 2)));
		assertEquals(3, collection.getSupportCount(new ItemSet(1, 3)));
		assertEquals(1, collection.getSupportCount(new ItemSet(2, 3)));
	}
	
	// Tests the getSubCollection method
	public void testGetSubCollection() {
		ItemSetCollection collection = new ItemSetCollection();
		// Add some itemsets first
		ItemSet newSet = null; 
		newSet = new ItemSet(1, 2);
		collection.addSet(newSet, 5);
		newSet = new ItemSet(1, 3);
		collection.addSet(newSet, 3);
		newSet = new ItemSet(2, 3);
		collection.addSet(newSet);
		newSet = new ItemSet(2, 3, 4);
		collection.addSet(newSet);
		newSet = new ItemSet(2, 3, 5);
		collection.addSet(newSet);
		newSet = new ItemSet(1);
		collection.addSet(newSet);
		newSet = new ItemSet(5);
		collection.addSet(newSet);
		newSet = new ItemSet(7);
		collection.addSet(newSet);
		newSet = new ItemSet(9);
		collection.addSet(newSet);
		// We added 3 2-itemset, 2 3-itemsets and 4 1-itemsets
		// Verify if these numbers are correct 
		assertEquals(4, collection.getSubCollection(1).getCount());
		assertEquals(3, collection.getSubCollection(2).getCount());
		assertEquals(2, collection.getSubCollection(3).getCount());
		
		// Verify if the subsets' contents are correct
		ItemSetCollection subCollection = null; 
		// Check 1-temsets
		subCollection = collection.getSubCollection(1);
		assertTrue(subCollection.contains(new ItemSet(1)));
		assertTrue(subCollection.contains(new ItemSet(5)));
		assertTrue(subCollection.contains(new ItemSet(7)));
		assertTrue(subCollection.contains(new ItemSet(9)));
		// Check 2-itemsets		
		subCollection = collection.getSubCollection(2);
		assertTrue(subCollection.contains(new ItemSet(1, 2)));
		assertTrue(subCollection.contains(new ItemSet(1, 3)));
		assertTrue(subCollection.contains(new ItemSet(2, 3)));
		// Check 3-itemsets
		subCollection = collection.getSubCollection(3);
		assertTrue(subCollection.contains(new ItemSet(2, 3, 4)));
		assertTrue(subCollection.contains(new ItemSet(2, 3, 5)));
	}
	
	
	
}
