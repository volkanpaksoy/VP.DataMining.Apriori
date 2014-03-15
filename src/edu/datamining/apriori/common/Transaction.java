package edu.datamining.apriori.common;

public class Transaction {
	
	// Itemset containing the items in the transaction
	private ItemSet m_PurchasedItems = new ItemSet();
	
	// Returns the items in the transaction
	public ItemSet getPurchasedItems() {
		return m_PurchasedItems;
	}
	
	// Returns string representation of the transaction
	@Override
	public String toString() {
		return this.getPurchasedItems().toString();
	}
	
	// Creates a Transaction object from the given string
	public static Transaction fromRow(String strLine) {
		return fromRow(strLine, null);
	}
	
	// Parses the given string and creates a Transaction object
	// This overload also takes an ItemSet parameter which can be used
	// to perform consistency checks. 
	// If all the possible items are known, than it can be assured
	// that the transaction does not contain any other items. 
	public static Transaction fromRow(String strLine, ItemSet itemsetAll) {
		Transaction trnx = new Transaction();

		String[] strItems = strLine.split(SystemDefinitions.SEPARATOR);
		
		for (String str : strItems) {
			int nRes = Integer.parseInt(str);
			Item itemParsed = new Item(nRes);

			if (itemsetAll != null && !itemsetAll.contains(itemParsed)) {
				throw new ApplicationException(String.format("Error while parsing data. Item %d is not on the market.",
												itemParsed.getValue()));
			}

			trnx.getPurchasedItems().addItem(itemParsed);
		}
		return trnx;
	}

	// This method is implemented in the C# version.
	// It is used while producing random input files.
	// Since file generator is not implemented in Java,
	// this method is useles. But if it is migrated to Java
	// this method must be implemented as well.
	public static Transaction generateTrnx(ItemSet items) {
		return null;
	}
	
}
