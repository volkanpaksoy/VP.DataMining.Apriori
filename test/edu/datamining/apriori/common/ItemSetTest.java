package edu.datamining.apriori.common;

import java.util.ArrayList;
import junit.framework.TestCase;


public class ItemSetTest extends TestCase {

	// Tests the default constructor with no parameters. 
	public void testConstruction1() {
		ItemSet itemset = new ItemSet();
		
		// Check if there are really no items in the itemset
		assertEquals(0, itemset.getCount());
		
		// Check if a random item cannot be found in the set. 
		// (A bit redundant...may be removed) 
		assertFalse(itemset.contains(new Item(1)));
	}
	
	// Tests constructor which takes indeterminate number of integers
	public void testConstruction2() {
		ItemSet itemset = new ItemSet(1, 2, 3);
		
		// Check if there are really 3 items in the itemset
		assertEquals(3, itemset.getCount());
		
		containsTest(itemset);
	}

	// Tests constructor which takes an arraylist
	public void testConstruction3() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item(1));
		items.add(new Item(2));
		items.add(new Item(3));
		
		ItemSet itemset = new ItemSet(items);
		
		// Check if there are really 3 items in the itemset
		assertEquals(3, itemset.getCount());
		
		containsTest(itemset);
	}
	
	// Tests constructor which takes indeterminate number of Item objects
	public void testConstruction4() {
		ItemSet itemset = new ItemSet(new Item(1), new Item(2), new Item(3));
		
		// Check if there are really 3 items in the itemset
		assertEquals(3, itemset.getCount());
		
		containsTest(itemset);
	}
	
	// Tests constructor which takes another itemset. The itemset which will be
	// be sent as parameter must be constructed by one of the previously tested
	// constructors, obviously.
	public void testConstruction5() {
		ItemSet originalItemset = new ItemSet(1, 2, 3);
		
		ItemSet itemset = new ItemSet(originalItemset);
		// Check if there are really 3 items in the itemset
		assertEquals(3, itemset.getCount());
		
		containsTest(itemset);
	}
	
	// Tests contains method.
	public void testContains() {
		ItemSet itemset = new ItemSet(1, 2, 3);
		containsTest(itemset);
	}
	
	// Performs general checks for the itemset containing items. 
	// In order this to work all the itemsets must be created with
	// the same parameters : 1, 2, 3
	// Since ItemSet class has 4 constructors taking parameters instead of copying 
	// these checks in all 4 test methods they are gathered in one method.
	// Actually it is contains() method's test..
	// This method is private so it is not run automatically, it's purpose
	// is to be called by other test methods. 
	// If needed it can be generalized later, as the expected items in th set  
	// can be sent in a different parameter.
	private void containsTest(ItemSet itemset) {
		// Check if all three added items are really in the itemset
		assertTrue(itemset.contains(itemset.getItem(0)));
		assertTrue(itemset.contains(itemset.getItem(1)));
		assertTrue(itemset.contains(itemset.getItem(2)));

		// Itemset must have items 1, 2 and 3. Check their validity with new items
		// with the same values.
		assertTrue(itemset.contains(new Item(1)));
		assertTrue(itemset.contains(new Item(2)));
		assertTrue(itemset.contains(new Item(3)));
		
		// Check if an item which must not be in the set is really not in the set. 
		assertFalse(itemset.contains(new Item(4)));		
	}
	
	// Tests addItem method
	public void testAddItem() {
		// In order to harness containsTest method, 
		// ItemSet will be created with Items 1 and 2.
		// 3 will be added
		ItemSet itemset = new ItemSet(1, 2);
		
		itemset.addItem(new Item(3));
		containsTest(itemset);
		
		// Do the same thing but add 1 this time
		itemset = new ItemSet(2, 3);
		
		itemset.addItem(new Item(1));
		containsTest(itemset);
	}
	
	// Tests remoteItem method
	public void testRemoveItem() {
		// In order to harness containsTest method, 
		// ItemSet will be created with Items 1, 2, 3 and 4.
		// 4 will be removed
		ItemSet itemset = new ItemSet(1, 2, 3, 4);
		
		itemset.removeItem(new Item(4));
		containsTest(itemset);
	}
	
	// Tests getCount method
	public void testGetCount() {
		ItemSet itemset = new ItemSet(new Item(1), new Item(2), new Item(3));
		
		// Check if there are really 3 items in the itemset
		assertEquals(3, itemset.getCount());		
	}
	
	// Tests get item method
	public void testGetItem() {
		
		// getItem() method is always used in loops. Actually, we don't know
		// which item is at which index. So without a reference point
		// this method cannot be tested on its own.
		// testSort() method can be used to test both methods.
		// Because, after sorting, we can be sure what to expect
		// in an itemset at a given index.
	}
	
	// Tests the sort method
	public void testSort() {
		ItemSet itemset = null;
		
		// Create an ItemSet, sort it, then check if all items are in expected indices.
		// Test 1 - Reverse order
		itemset = new ItemSet(5, 4, 3, 2, 1);
		itemset.sort();
		assertEquals(1, itemset.getItem(0).getValue());		
		assertEquals(2, itemset.getItem(1).getValue());
		assertEquals(3, itemset.getItem(2).getValue());
		assertEquals(4, itemset.getItem(3).getValue());
		assertEquals(5, itemset.getItem(4).getValue());
				
		// Test 2 - Random items 
		itemset = new ItemSet(3, 1, 5, 2, 4);
		itemset.sort();
		assertEquals(1, itemset.getItem(0).getValue());		
		assertEquals(2, itemset.getItem(1).getValue());
		assertEquals(3, itemset.getItem(2).getValue());
		assertEquals(4, itemset.getItem(3).getValue());
		assertEquals(5, itemset.getItem(4).getValue());
		
		// Test 3 - 1-itemset.. See if the item remains intact :)
		itemset = new ItemSet(1);
		itemset.sort();
		assertEquals(1, itemset.getItem(0).getValue());		
				
		// Test 4 - 2-itemset
		itemset = new ItemSet(2, 1);
		itemset.sort();
		assertEquals(1, itemset.getItem(0).getValue());
		assertEquals(2, itemset.getItem(1).getValue());
		
		// Test 5 - Already sorted. See if calling another sort doesn't break anything.
		itemset = new ItemSet(1, 2, 3, 4, 5);
		itemset.sort();
		assertEquals(1, itemset.getItem(0).getValue());		
		assertEquals(2, itemset.getItem(1).getValue());
		assertEquals(3, itemset.getItem(2).getValue());
		assertEquals(4, itemset.getItem(3).getValue());
		assertEquals(5, itemset.getItem(4).getValue());
	}
	
	// Tests toString method
	public void testToString() {
		ItemSet itemset = new ItemSet(1, 2, 3, 4);
		assertEquals("1,2,3,4", itemset.toString());
		
		itemset = new ItemSet(1);
		assertEquals("1", itemset.toString());
	}
	
	// Tests join method. Joining sets is a crucial step for generating candidates.
	// In order to join two sets, all the items before the last one must have the same value
	// and the last item of the first set must be smaller than the last item of the second set.
	// If these conditions are met, the last item of the second set is appended to the first 
	// set and a joined or expanded set is returned from the join method. If the sets could
	// not be joined a null value is expected.
	public void testJoin() {
		ItemSet itemset1 = null;
		ItemSet itemset2 = null;
		ItemSet joinedItemset = null;
		
		// Test 1 - Two 1-item sets that can be joined. Expected outcome is : 1, 2 
		itemset1 = new ItemSet(1);
		itemset2 = new ItemSet(2);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(1, joinedItemset.getItem(0).getValue());		
		assertEquals(2, joinedItemset.getItem(1).getValue());
		
		// Test 2 - Two 1-item sets that can NOT be joined. Expected outcome is null.
		itemset1 = new ItemSet(2);
		itemset2 = new ItemSet(1);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(null, joinedItemset);	
		
		// Test 3 - Two 2-item sets that can be joined. Expected outcome is : 1, 2, 3 
		itemset1 = new ItemSet(1, 2);
		itemset2 = new ItemSet(1, 3);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(1, joinedItemset.getItem(0).getValue());		
		assertEquals(2, joinedItemset.getItem(1).getValue());
		assertEquals(3, joinedItemset.getItem(2).getValue());		
		
		// Test 4 - Two 2-item sets that can NOT be joined. Expected outcome is null. 
		itemset1 = new ItemSet(1, 2);
		itemset2 = new ItemSet(2, 3);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(null, joinedItemset);
		
		// Test 5 - Same two 2-item sets. Expected outcome is null. 
		itemset1 = new ItemSet(1, 2);
		itemset2 = new ItemSet(1, 2);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(null, joinedItemset);
		
		// Test 6 - Two 3-item sets that can be joined. Expected outcome is : 1, 2, 3, 4 
		itemset1 = new ItemSet(1, 2, 3);
		itemset2 = new ItemSet(1, 2, 4);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(1, joinedItemset.getItem(0).getValue());		
		assertEquals(2, joinedItemset.getItem(1).getValue());
		assertEquals(3, joinedItemset.getItem(2).getValue());	
		assertEquals(4, joinedItemset.getItem(3).getValue());
		
		// Test 7 - Two 3-item sets that can be joined in random order. 
		// Expected outcome is : 1, 2, 3, 4 
		itemset1 = new ItemSet(3, 1, 2);
		itemset2 = new ItemSet(1, 4, 2);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(1, joinedItemset.getItem(0).getValue());		
		assertEquals(2, joinedItemset.getItem(1).getValue());
		assertEquals(3, joinedItemset.getItem(2).getValue());	
		assertEquals(4, joinedItemset.getItem(3).getValue());
		
		// Test 8 - Two 3-item sets that can NOT be joined. Expected outcome is null 
		itemset1 = new ItemSet(1, 2, 4);
		itemset2 = new ItemSet(1, 2, 3);
		joinedItemset = itemset1.join(itemset2);
		assertEquals(null, joinedItemset);
	}
	
	// Tests getSubsets method
	public void testGetSubsets() {
		ItemSet itemset = null;
		ItemSetCollection subsets = null;
		
		// Test 1 - Get 1-item subsets of a 2-item set.
		// Returned ItemSetCollection must have 2 elements which are 
		// 2 1-item sets (namely {1} and {2})
		itemset = new ItemSet(1, 2);
		subsets = itemset.getSubsets(1);
		// Check if there are really 2 items in the collection
		assertEquals(2, subsets.getCount());
		// Check the subsets' values
		subsets.contains(new ItemSet(1));
		subsets.contains(new ItemSet(2));
		
		// Test 2 - Get 2-item subsets of a 3-item set.
		// Returned ItemSetCollection must have 3 elements which are 
		// {1, 2}, {1, 3} and {2, 3}
		itemset = new ItemSet(1, 2, 3);
		subsets = itemset.getSubsets(2);
		// Check if there are really 3 items in the collection
		assertEquals(3, subsets.getCount());
		// Check the subsets' values
		subsets.contains(new ItemSet(1, 2));
		subsets.contains(new ItemSet(1, 3));
		subsets.contains(new ItemSet(2, 3));
		
		// Test 3 - Get 1-item subsets of a 3-item set.
		// Returned ItemSetCollection must have 3 elements which are 
		// {1}, {2} and {3}
		itemset = new ItemSet(1, 2, 3);
		subsets = itemset.getSubsets(1);
		// Check if there are really 3 items in the collection
		assertEquals(3, subsets.getCount());
		// Check the subsets' values
		subsets.contains(new ItemSet(1));
		subsets.contains(new ItemSet(2));
		subsets.contains(new ItemSet(3));
		
		// Test 4 - Get 2-item subsets of a 4-item set.
		// Returned ItemSetCollection must have 6 elements which are 
		// {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}
		itemset = new ItemSet(1, 2, 3, 4);
		subsets = itemset.getSubsets(2);
		// Check if there are really 6 items in the collection
		assertEquals(6, subsets.getCount());
		// Check the subsets' values
		subsets.contains(new ItemSet(1, 2));
		subsets.contains(new ItemSet(1, 3));
		subsets.contains(new ItemSet(1, 4));
		subsets.contains(new ItemSet(2, 3));
		subsets.contains(new ItemSet(2, 4));
		subsets.contains(new ItemSet(3, 4));
	}
	
	// Tests compareTo method
	public void testCompareTo() {
		ItemSet itemset1 = null;
		ItemSet itemset2 = null;
		int nResult = -1;
		
		// Test 1 - Compare two different itemsets. Expected result is -1
		itemset1 = new ItemSet(1, 2);
		itemset2 = new ItemSet(2, 3);
		nResult	= itemset1.compareTo(itemset2);
		assertEquals(-1, nResult);
		
		// Test 2 - Compare two same itemsets. Expected result is 0
		itemset1 = new ItemSet(1, 2);
		itemset2 = new ItemSet(1, 2);
		nResult	= itemset1.compareTo(itemset2);
		assertEquals(0, nResult);
		
		// Test 3 - Compare two same itemsets, in random order. Expected result is 0
		itemset1 = new ItemSet(3, 2, 1, 4);
		itemset2 = new ItemSet(1, 2, 4, 3);
		nResult	= itemset1.compareTo(itemset2);
		assertEquals(0, nResult);
	}
	
	// Tests hasInfrequentSubsets method
	// hasInfrequentSubsets method is used for pruning. If the itemset has any 
	// subsets that are not a member of previous frequent itemset list, then
	// it will be removed from the candidate list.
	public void testHasInfrequentSubsets() {
		ItemSet itemset = null;
		ItemSetCollection frequentItemsetCollection = null;
		boolean bResult = false;  
		
		// Test 1 - {1, 2, 3} has 3 2-item subsets: {1, 2}, {1, 3} and {2, 3}
		// Apparently, the subset {1, 3} could not make it to the L2.
		// So if {1, 3} is infrequent, there's no way {1, 2, 3} is frequent.
		// The expected result is true.
		itemset = new ItemSet(1, 2, 3);
		frequentItemsetCollection = new ItemSetCollection();
		frequentItemsetCollection.addSet(new ItemSet(1, 2), 2);
		frequentItemsetCollection.addSet(new ItemSet(1, 4), 3);
		frequentItemsetCollection.addSet(new ItemSet(2, 3), 4);
		frequentItemsetCollection.addSet(new ItemSet(2, 4), 2);
		frequentItemsetCollection.addSet(new ItemSet(3, 4), 2);
		bResult = itemset.hasInfrequentSubsets(frequentItemsetCollection);
		assertTrue(bResult);
		
		// Test 2 - {1, 2, 3} has 3 2-item subsets: {1, 2}, {1, 3} and {2, 3}
		// All subsets' are in the frequentItemsetCollection
		// So the expected result is false.
		itemset = new ItemSet(1, 2, 3);
		frequentItemsetCollection = new ItemSetCollection();
		frequentItemsetCollection.addSet(new ItemSet(1, 2), 2);
		frequentItemsetCollection.addSet(new ItemSet(1, 3), 4);
		frequentItemsetCollection.addSet(new ItemSet(1, 4), 3);
		frequentItemsetCollection.addSet(new ItemSet(2, 3), 4);
		frequentItemsetCollection.addSet(new ItemSet(3, 4), 2);
		bResult = itemset.hasInfrequentSubsets(frequentItemsetCollection);
		assertFalse(bResult);
	}
	
	
}
