package edu.datamining.apriori;

import java.io.*;
import edu.datamining.apriori.common.*;


public class APrioriImplementation {

	// THe path of the database (input CSV file)
	private String m_strInputFilePath = null;
	// Returns the path of the input file  
	public String getInputFilePath() {
		return m_strInputFilePath;
	}
	// Sets the path of the input file
	public void setInputFilePath(String inputFilePath) {
		m_strInputFilePath = inputFilePath;
	}

	// The value that defines a frequent itemset. It's given by user.
	private int m_nMinimumSupportCount = -1;
	// Returns the min.sup. count 
	public int getMinimumSupportCount() {
		return m_nMinimumSupportCount;
	}
	// Sets the min.sup. count
	public void setMinimumSupportCount(int minimumSupportCount) {
		m_nMinimumSupportCount = minimumSupportCount;
	}
	
	// Main method. First, gets frequent 1-itemsets. Then generates 2-itemset candidates
	// by using this collection. And keeps searching for larger frequent itemsets until
	// the result is empty set.
	public ItemSetCollection apriori() {
		ItemSetCollection result = new ItemSetCollection();
		
		// First, find frequent 1-itemsets. APriori algorithm requires us to
		// gather the 1-item frequent subsets prior to running. 
		ItemSetCollection oneItemFrequentSets = findFrequentOneItemSets();
		
		// Since frequent 1-itemsets are part of the resultset add them to the output
		for (ItemSet itemset : oneItemFrequentSets) {
			result.addSet(itemset);
		}
		
		ItemSetCollection frequentItemSetlist = oneItemFrequentSets;
		int k = 2;
		
		do {
			ItemSetCollection candidates = generateCandidates(frequentItemSetlist);
			if (candidates.getCount() == 0) {
				// If there are no more candidates break out of the loop
				break;
			}
			
			ItemSetCollection tempResultSet = new ItemSetCollection();
			
			// Scan database
			try {
				File inputFile = new File(getInputFilePath());
				FileReader fileReader = new FileReader(inputFile);
				BufferedReader reader = new BufferedReader(fileReader);
				String strLine = reader.readLine();
				if (strLine == null) {
					// If there are no records, don't bother...
					break;
				}
				
				do // For each transaction in db...
				{
					Transaction trnx = Transaction.fromRow(strLine);
					// If the k is greater than the items in the transaction 
					// skip this transaction.
					if (trnx.getPurchasedItems().getCount() < k) {
						strLine = reader.readLine();
						continue;
					}
					
					trnx.getPurchasedItems().sort();
					
					ItemSetCollection subsetsOfTrnx = null;
					try	{
						// Get all k-itemsets of the current trnx
 						subsetsOfTrnx = trnx.getPurchasedItems().getSubsets(k);
					} catch (ApplicationException ex) {
						// If the number of items in transaction is less than k, than
						// GetSubsets will throw an RuntimeException. 
						// In this case, we should read the next line and move on...
						System.out.println(ex.getMessage());
						strLine = reader.readLine();
						continue;
					}

					// Count the appearances of candidates
					// For each canidate in the list...
					for (ItemSet cand : candidates)
					{
						// Loop thru all subsets of the transaction.
						for (ItemSet trnxSubset : subsetsOfTrnx)
						{
							// If the candidate matches with one of the subsets
							// then add it to the temporary list 
							// (it will added to the output list if it's support count is enough)
							if (cand.compareTo(trnxSubset) == 0) {
								tempResultSet.addSetOrUpdateSupportCount(cand);
							}
						}
					}
					
					strLine = reader.readLine();
				}
				while (strLine != null);
				
				// We're done with the previous results... 
				frequentItemSetlist.clear();
			} catch(IOException ex) {
				throw new ApplicationException("Error while reading database");
			}
			
			// Loop thru temporary list and add the items to the result which
			// have a support count bigger than or equal to min. sup. count 
			int nSupportCount = 0;
			for (ItemSet itemset : tempResultSet) {
				nSupportCount = tempResultSet.getSupportCount(itemset);
				if (nSupportCount >= getMinimumSupportCount()) {
					result.addSet(itemset, nSupportCount);
					frequentItemSetlist.addSet(itemset); // Add this set to the freq. item list. This list will be 
														 // used to generate next candidate generation. Also it's count
														 // is used as condition to break loop. if there are no
														 // frequent itemsets found in this round, there's no need to go
														 // any further.
				}
			}
			
			k++;
			
		} while (frequentItemSetlist.getCount() > 0); // While L(k) is not empty set.
		
		
		return result;
	}
	
	// Scans the database and returns the frequent 1-itemsets.
	public ItemSetCollection findFrequentOneItemSets() {
		ItemSetCollection resultSet = new ItemSetCollection();
		ItemSetCollection tempResultSet = new ItemSetCollection();
		
		// Scan database and count all 1-itemsets 
		try {
			File inputFile = new File(getInputFilePath());
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String strLine = reader.readLine();
			if (strLine == null) {
				return resultSet;
			}
			
			do {
				// Parse database record and create a transaction object
				Transaction trnx = Transaction.fromRow(strLine);
				for (Item item : trnx.getPurchasedItems())
				{
					ItemSet newSet = new ItemSet(item);
					tempResultSet.addSetOrUpdateSupportCount(newSet);
				}
				
				strLine = reader.readLine();
			} while (strLine != null);
		} catch(IOException ex) {
			throw new ApplicationException("Error while reading database");
		}
		
		// After counting all the itemsets, we can now determine if they'are
		// eligible to be frequent.
		int nSupportCount = 0;
		for (ItemSet itemset : tempResultSet) {
			nSupportCount = tempResultSet.getSupportCount(itemset);
			if (nSupportCount >= getMinimumSupportCount()) {
				resultSet.addSet(itemset, nSupportCount);
			}
		}
		
		return resultSet;
	}
	
	// APriori_Gen. Generate candidates from the previous frequent item list.
	public ItemSetCollection generateCandidates(ItemSetCollection lastFrequentItemlist)
	{
		ItemSetCollection candidateItemSets = new ItemSetCollection();
		lastFrequentItemlist.sort();
		
		for (ItemSet subset1 : lastFrequentItemlist) {
			for (ItemSet subset2 : lastFrequentItemlist) {
				// Since we are taking subset1 and subset2 from the same list, same subset 
				// will be taken as both subset1 and subset2. Omit this case...
				if (subset1 == subset2)	{
					continue;
				}
				
				ItemSet joinedSet =	subset1.join(subset2);
				// If sets are not joined, joinedSet will be null. If so, no action required...
				if (joinedSet != null) {
					// Prune...
					// If the new candidate has any known infrequent items, eliminate it. 
					// In other words, make it a candidate only if it has no infrequent subsets.
					if (!joinedSet.hasInfrequentSubsets(lastFrequentItemlist)) {
						candidateItemSets.addSet(joinedSet);
						}
				}
			}
		}
        
		return candidateItemSets;
	}

	
}
