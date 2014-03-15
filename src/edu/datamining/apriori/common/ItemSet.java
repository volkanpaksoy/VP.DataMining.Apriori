package edu.datamining.apriori.common;

import java.util.ArrayList;
import java.util.Iterator;


// ItemSet is a list of items.  
public class ItemSet implements Iterable<Item>{

	private ArrayList<Item> m_lstItemList;
	
	// Default constructor. Creates an empty ItemSet.
	public ItemSet() {
		m_lstItemList = new ArrayList<Item>();
	}
	
	// Creates an ItemSet with the given integer list. 
	// The number of parameters is indeterminate.
	public ItemSet(int... items) {
		m_lstItemList = new ArrayList<Item>();
		
		// Loop through the sent items and add them to the list   
		if (items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {

				// Before adding the new item check if it is already added
				// TODO Instead of looping try m_lstItemList.contains() 
				for (Item itemCurrent : m_lstItemList) {
					   if (itemCurrent.getValue() == items[i]) {
						   continue; // If item is already on the list, skip..
						   			 // Throw an exception instead ??
					   }
					}
 
				m_lstItemList.add(new Item(items[i]));
			}
		}
	}
	
	// Creates an ItemSet with the given Item arraylist.
	public ItemSet(ArrayList<Item> items) {
		m_lstItemList = new ArrayList<Item>();
		
		if (items != null && !items.isEmpty()) {
			for (Item itemNew : items) {
				// Before adding the new item check if it is already added
				// TODO Instead of looping try m_lstItemList.contains() 
				for (Item itemCurrent : m_lstItemList) {
					if (itemCurrent.getValue() == itemNew.getValue()) {
						continue; // If item is already on the list, skip..
					   			 // Throw an exception instead ??
					}
				}
 
				m_lstItemList.add(itemNew);
			}
		}
	}

	// Creates an ItemSet with the given integer list. 
	// The number of parameters is indeterminate.
	public ItemSet(Item... items) {
		m_lstItemList = new ArrayList<Item>();
		
		// Loop through the sent items and add them to the list   
		if (items != null && items.length > 0) {
			for (int i = 0; i < items.length; i++) {

				// Before adding the new item check if it is already added
				// TODO Instead of looping try m_lstItemList.contains() 
				for (Item itemCurrent : m_lstItemList) {
				   if (itemCurrent.getValue() == items[i].getValue()) {
					   continue; // If item is already on the list, skip..
					   			 // Throw an exception instead ??
				   }
				}
 
				m_lstItemList.add(items[i]);
			}
		}
	}	
	
	// Creates a new ItemSet from an existing one.
	public ItemSet(ItemSet origSet) {
		m_lstItemList = new ArrayList<Item>();
		
		for (Item item : origSet) {
			m_lstItemList.add(item);
		}
	}

	// Method from Iterable interface. Must be implemented so that
	// items in an ItemSet object can be used in for-each loops. 
	@Override
	public Iterator<Item> iterator() {
		Iterator<Item> iterator = m_lstItemList.iterator(); 
		return iterator;
	}
	
	// Adds a new item object to the current list if it's not already
	// in the list. Does not take any action if given item is already in the 
	// list.
	public void addItem(Item newItem) {
		if (!m_lstItemList.contains(newItem)) {
			m_lstItemList.add(newItem);
		}
	}
	
	// Removes the given item from the current item list
	public void removeItem(Item itemToRemove) {
		m_lstItemList.remove(itemToRemove);
	}
	
	// Returns the number of items in the itemset.
	public int getCount() {
		return m_lstItemList.size();
	}
	
	// Using indexer would look much nicer to access the items in the set
	// but apparently Java doesn't support indexers.. getItem serves the same
	// purpose.
	public Item getItem(int index) {
		return m_lstItemList.get(index);
	}
	
	// Sorts the items in the itemset in ascending order.
	// Uses bubble sort. To improve performance, It would be  
	// better to replace with a more efficient algorithm.
	public void sort() {
		Item tempItem = null;
		
		for (int i = m_lstItemList.size() - 1; i >= 0; i--) {
			for (int j = 1; j <= i; j++) {
				if (m_lstItemList.get(j - 1).getValue() > m_lstItemList.get(j).getValue() ) {
					tempItem = m_lstItemList.get(j - 1);
					m_lstItemList.set(j - 1, m_lstItemList.get(j));
					m_lstItemList.set(j, tempItem);
				}
			}
		}
	}
	
	// Checks if the given item is in the itemset
	public boolean contains(Item itemToSearch) {
		return m_lstItemList.contains(itemToSearch);
	}
	
	// Prepares and returns the String representation of ItemSet object
	// which is all items concatenated and separated with separator character.  
	@Override
	public String toString() {
		// TODO Before appending items to the string builder, first a new list
		// must be created and sorted. The items' values should be appended.
		// after sorting. 
		// m_lstItemList.sort() must not be called to perform sort operation
		// because this would change the state of the object and may 
		// cause problems (even tough not very likely, but working on a temporary list
		// seems the best way to go.)
		// toString() is not used for computational purposes, so this can left as is for
		// the time being.
		StringBuilder sb = new StringBuilder();
		for(Item item : m_lstItemList) {
			sb.append(item.toString());
			sb.append(SystemDefinitions.SEPARATOR);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	// Joins the current set with the given set. 
	public ItemSet join(ItemSet itemsetToJoinWith) {
		ItemSet joinedSet = null;
		boolean bIsExpandible = true; 
		
		// Make sure all items in the sets are in order.
		this.sort();
		itemsetToJoinWith.sort();
		
		// Compare all the corresponding items in the sets except for the last one.
		// (the last one will be checked below this loop)
		for (int i = 0; i < this.getCount() - 1; i++)
		{
			if (this.getItem(i).getValue() != itemsetToJoinWith.getItem(i).getValue())
			{
				// If the items with the same index have different values   
				bIsExpandible = false;
				break;
			}
		}

		// If all the items are equal (bIsExpandible = true), compare the last items
		// If last item of the compared set is greater than this set, 
		// expand this set with the last item in the given set.
		if (bIsExpandible && this.getItem(getCount() - 1).getValue() < itemsetToJoinWith.getItem(itemsetToJoinWith.getCount() - 1).getValue())
		{
			// All conditions are met. We can join the sets now but we should not 
			// alter this set, it would create confusion when looping through items.
			// Copy this set to a new one..
			joinedSet = new ItemSet(this); 
			// Add the item to join to the new set
			joinedSet.addItem(itemsetToJoinWith.getItem(itemsetToJoinWith.getCount() - 1));
		}
		
		return joinedSet;
	}
	
	// Returns the k-item subsets as a ItemSetCollection
	public ItemSetCollection getSubsets(int k) {
		if (k > this.getCount()) {
			throw new ApplicationException("Cannot generate subsets. Given number is greater than the set's item count");
		}
		
		this.sort();
		ItemSetCollection subsetCollection = new ItemSetCollection();
		// CombinationGenerator class provides an integer array which
		// can be used as the indices of items in the subset. 
		CombinationGenerator combGen = new CombinationGenerator(this.getCount(), k);
		while (combGen.hasMore()) {
			// Get the list of item indices
			int[] indicesOfItems = combGen.getNext();
			ItemSet subset = new ItemSet();
			// Get every item in the specified index and add it to the subset
			for (int i = 0; i < indicesOfItems.length; i++) {
				subset.addItem(this.getItem(indicesOfItems[i]));
			}
			// Add subset to the output
			subsetCollection.addSet(subset);
		}
		return subsetCollection;
	}
	
	// Compares two itemsets. Returns 0 if they have the same items, -1 otherwise
	public int compareTo(Object obj){
		int nResult = 0;
		ItemSet otherItemSet = (ItemSet)obj;
		if (otherItemSet == null) {
			throw new ApplicationException("Compared object is not of type ItemSet");
		}

		// Check item count first. If they are different no need to investigate any further.
		if (this.getCount() != otherItemSet.getCount()) {
			nResult = -1;
			return nResult;
		}

		// Make sure items are sorted before comparing
		// TODO Remove the following sort() calls.
		// Copy the itemsets into temporary sets and sort them
		// Calling compareTo must not change the order of items
		// of the current set
		this.sort();
		otherItemSet.sort();

		// For 2 itemsets to be equal all corresponding Item objects must be equal
		for (int i = 0; i < this.getCount(); i++) {
			if (this.getItem(i).getValue() != otherItemSet.getItem(i).getValue())
			{
				nResult = -1;
				break;
			}
		}

		return nResult;
	}
	
	// Iterates through the list and checks if it contains all its subsets
	public boolean hasInfrequentSubsets(ItemSetCollection lastFrequentItemlist)
	{
		boolean bHasInfrequentSubsets = false;
		ItemSetCollection collSubsets = this.getSubsets(this.getCount() - 1);
		for (ItemSet subsetToCheck : collSubsets) {
			if (!lastFrequentItemlist.contains(subsetToCheck)) {
				bHasInfrequentSubsets = true;
				break;
			}
		}

		return bHasInfrequentSubsets;
	}
}
