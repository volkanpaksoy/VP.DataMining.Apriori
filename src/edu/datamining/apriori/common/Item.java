package edu.datamining.apriori.common;

// Item represents things that are purchased in a transaction.
// Our itemsets are composed of integer values only. So currently 
// Item is nothing more than an integer wrapper. It may be convenient
// if additional properties are to be added.
public class Item implements Comparable<Item>{
	
	// Wrapped integer which is the value of an item. 
	private int m_nValue;
	
	// Getter for value
	public int getValue() {
		return m_nValue;
	}
	
	// Setter for value 
	public void setValue(int value) {
		m_nValue = value;
	}
	
	// Constructor of an Item object. Passing an integer is mandatory.
	// A no-argument constructor can also be supplied if needed. 
	public Item(int value) {
		m_nValue = value;
	}
	
	// Compares two item objects. Returns 0 if their underlying integer
	// values are equal to each other.
	@Override
	public int compareTo(Item otherItem) throws ApplicationException {
		int nResult = -1;
	//	Item otherItem = (Item)obj;
 
		if (otherItem == null) {
			throw new ApplicationException("Compared object is not of type Item");
		}

		if (this.getValue() == otherItem.getValue()) {
			nResult = 0;
		}
		else 
		if (this.getValue() > otherItem.getValue()) {
			nResult = 1;
		}
		else
		if (this.getValue() < otherItem.getValue()) {
			nResult = -1;
		}

		return nResult;
	}

	// String representation of an Item is same with the wrapped integer.  
	public String toString() {
		return new Integer(m_nValue).toString();
	}

	// Another way to compare two Item objects. Returns true if the items
	// are identical.
	@Override
	public boolean equals(Object obj) throws ApplicationException {
		boolean bResult = false;
		Item otherItem = (Item)obj;
		if (otherItem == null) {
			throw new ApplicationException("Compared object is not of type Item");
		}

		if (this.getValue() == otherItem.getValue()) {
			bResult = true;
		}

		return bResult;
	}
	
}
