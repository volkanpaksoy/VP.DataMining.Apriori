package edu.datamining.apriori.common;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;


public class ItemSetCollection implements Iterable<ItemSet> {

	private Hashtable<ItemSet, Integer> m_htItemSets = null;

	// Default constructor. Initializes an empty set list.
	public ItemSetCollection() {
		m_htItemSets = new Hashtable<ItemSet, Integer>();
	}

	// Creates an itemsetcollection with a variable number of itemsets.
	public ItemSetCollection(ItemSet... newItemSets) {
		m_htItemSets = new Hashtable<ItemSet, Integer>();
		if (newItemSets != null && newItemSets.length > 0) {
			for (ItemSet itemSetNew : newItemSets) {
				Enumeration<ItemSet> currentSets = m_htItemSets.keys();
				ItemSet set;
				while (currentSets.hasMoreElements()) {
					set = currentSets.nextElement();
					if (itemSetNew != null && itemSetNew != set) {
						continue; // If item is already on the list, skip..
						// Throw an exception if needed.
					}
				}

				m_htItemSets.put(itemSetNew, 0);
			}
		}
	}

	// Adds the given itemset to the collection,
	// This overload takes no support count value and uses
	// 0 as default. 
	public void addSet(ItemSet newSet) {
		addSet(newSet, 0);
	}

	// Adds the given itemset to the collection, 
	// if it's not already in the list. 
	public void addSet(ItemSet newSet, int nCount) {
		if (!this.contains(newSet)) {
			m_htItemSets.put(newSet, new Integer(nCount));
		}
	}

	// Removes the given itemset from the collection
	public void removeSet(ItemSet setToRemove) {
		Enumeration<ItemSet> currentSets = m_htItemSets.keys();
		ItemSet set;
		while (currentSets.hasMoreElements()) {
			set = currentSets.nextElement();
			if (set.compareTo(setToRemove) == 0) {
				m_htItemSets.remove(set);
				break;
			}
		}
	}
	
	// Updates the support count of an existing itemset
	public void updateSet(ItemSet setToCheck, int newCount) {
		removeSet(setToCheck);
		m_htItemSets.put(setToCheck, new Integer(newCount));
	}
	
	// If the given set is not in the collection it is added with 
	// support count 1.
	// If it is in the list, its support count is incremented by 1.
	public void addSetOrUpdateSupportCount(ItemSet itemset) {
		if (contains(itemset)) {
			int nCurrentValue = getSupportCount(itemset);
			nCurrentValue++;
			updateSet(itemset, nCurrentValue);
		}
		else {
			addSet(itemset, 1);
		}
	}
	
	// Returns the number of Itemsets in the collection
	public int getCount() {
		return m_htItemSets.size();
	}

	// Calls all child subsets' sort methods. In fact what it should do is to
	// loop thru all itemsets and sort them within themselves. But currently it's not
	// needed.
	public void sort() {
		Enumeration<ItemSet> currentSets = m_htItemSets.keys();
		ItemSet set;
		while (currentSets.hasMoreElements()) {
			set = currentSets.nextElement();
			set.sort();
		}
	}

	// Deletes all items from the collection
	public void clear() {
		m_htItemSets.clear();
	}

	// Checks if the given itemset is in the collection.
	public boolean contains(ItemSet itemsetToCheck) {
		boolean bResult = false;
		Enumeration<ItemSet> currentSets = m_htItemSets.keys();
		ItemSet set;
		while (currentSets.hasMoreElements()) {
			set = currentSets.nextElement();
			if (set.compareTo(itemsetToCheck) == 0) {
				bResult = true;
				break;
			}
		}

		return bResult;
	}

	// To use for-each loops on ItemSetCollection's
	@Override
	public Iterator<ItemSet> iterator() {
		return m_htItemSets.keySet().iterator();
	}

	// TODO Implement equals or compareTo...

	// Searches the given itemset and returns the support count of it
	// Returns 0 if the itemset is not in the collection.
	public int getSupportCount(ItemSet itemset) {
		int nResult = 0;
		Enumeration<ItemSet> currentSets = m_htItemSets.keys();
		ItemSet set;
		while (currentSets.hasMoreElements()) {
			set = currentSets.nextElement();
			if (set.compareTo(itemset) == 0) {
				nResult = m_htItemSets.get(set).intValue();
				break;
			}
		}
		return nResult;
	}

	// Creates a string representation of the collection to be 
	// used in output reports
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int nFrequentItemsetCount = -1;
		int k = 1;
		ItemSetCollection subCollection = null;
		
		while (nFrequentItemsetCount != 0) {
			subCollection = getSubCollection(k);
			nFrequentItemsetCount = subCollection.getCount();
			if (nFrequentItemsetCount == 0)
				break;
			
			sb.append(String.format("Frequent %d-itemsets:", k));
			
			for (ItemSet itemset : subCollection) {
				sb.append("{" + itemset.toString() + "}");
				sb.append(", ");
			}
			// TODO Remove the trailing ", "
			sb.append("\n");
			
			k++;
		}

		return sb.toString();
	}
	
	// Returns a subcollection that consists of "k"-itemsets   
	public ItemSetCollection getSubCollection(int k) {
		ItemSetCollection result = new ItemSetCollection();
		
		// Iterate through all itemsets in the collection.
		// If the itemset's item count is equal to k, add it 
		// to the output list.
		Enumeration<ItemSet> currentSets = m_htItemSets.keys();
		ItemSet set;
		while (currentSets.hasMoreElements()) {
			set = currentSets.nextElement();
			if (set.getCount() == k) {
				result.addSet(set);
			}
		}

		return result;
	}

}
