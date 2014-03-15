
package edu.datamining.apriori;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.datamining.apriori.common.ApplicationException;
import edu.datamining.apriori.common.ItemSetCollection;

public class APrioriClient {
	
	public static void main(String [] args) {
		System.out.println("---------------------------------");
		System.out.println(" APriori Algorithm Implementation");
		System.out.println("---------------------------------");
		
		APrioriImplementation aprioriImpl = new APrioriImplementation();
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		String strParam = null;
		try {
			System.out.print("Enter the path of database: ");
			strParam = in.readLine();
			aprioriImpl.setInputFilePath(strParam);
			
			System.out.print("Enter the minimum support count: ");
			strParam = in.readLine();
			aprioriImpl.setMinimumSupportCount(Integer.parseInt(strParam));
			

		} catch (Exception ex) {
			System.out.println("Error while reading parameters : " + ex.getMessage());
			return;
		}
		
		System.out.println("---------------------------------");
		System.out.println("--    Frequent Itemsets          ");
		System.out.println("---------------------------------");
		
		ItemSetCollection result = null;
		try {
			result = aprioriImpl.apriori();
			System.out.println(result.toString());			
		} catch(ApplicationException ex) {
			System.out.println(ex.getMessage());
		}
	
		
	}
	
	
}

