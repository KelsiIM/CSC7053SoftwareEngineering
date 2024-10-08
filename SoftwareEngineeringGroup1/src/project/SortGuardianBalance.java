/**
 * 
 */
package project;

import java.util.Comparator;

/**
 * 
 */
public class SortGuardianBalance implements Comparator<Guardian> {
	
	@Override
	public int compare(Guardian g1, Guardian g2) {
		
		return g1.getBalance() - g2.getBalance();
	}

}
