/**

* 

*/

package project;

import java.util.ArrayList;
import java.util.List;

/**

* This class is for the functioning of a player in the game 

*/

public class Guardian implements Comparable {

	public static final int GUARDIAN_NAME_MIN = 1;

	public static final int GUARDIAN_NAME_MAX = 25;

	public static final int BALANCE_MIN = 1;

	public static final int BALANCE_MAX = 2147483647;

	// instance vars for the Guardian

	private String guardianName;

	private int balance;

	private int position;
	
	private List<Area> ownedAreas; // New field to store owned areas
	
	private boolean hasTakenTurn; // to track if the guardian has taken their turn


	/**

	 * Default constructor

	 */

	public Guardian() {
 
		 ownedAreas = new ArrayList<>();
	}
 
 
	/**

	 * Constructor to create a Guardian

	 * @param guardianName

	 * @param id

	 * @param balance

	 * @param position

	 */

	public Guardian(String guardianName, int balance, int position) {
		this();
		this.guardianName = guardianName;

		this.balance = balance;

		this.position = position;
		
		this.setHasTakenTurn(false); // initialise to false when guardian is created

	}

	/**

	 * Returns the name of the Guardian as a string

	 * @return the guardianName

	 */

	public String getGuardianName() {

		return guardianName;

	}
 
 
	/**

	 * Sets the name of a Guardian. Name must be a letter and can't be empty.

	 * Must be minimum 1 character and maximum 25 characters.

	 * @param guardianName the guardianName to set

	 */

	public void setGuardianName(String guardianName) throws IllegalArgumentException {

		if(!guardianName.isEmpty() && (guardianName.length() >= GUARDIAN_NAME_MIN && guardianName.length() <= GUARDIAN_NAME_MAX)) {

			this.guardianName = guardianName;

		} else {

			throw new IllegalArgumentException("Sorry, don't recognise that as a valid name. Please try again!");

		}

	}
 
 
	/**

	 * Returns the balance of a guardian as an integer in Green Credits (GC)

	 * @return the balance

	 */

	public int getBalance() {

		return balance;

	}
 
 
	/**

	 * Sets the balance of a guardian as an integer in GC

	 * Ensures that the balance cannot be negative or exceed a certain number

	 * @param balance the balance to set

	 */

	public void setBalance(int balance) throws IllegalArgumentException {

		if (balance >= BALANCE_MIN && balance <= BALANCE_MAX) {

			this.balance = balance;

		} else {

			throw new IllegalArgumentException("Green Credit balance is out of bounds.");

		}

	}
 
 
	/**

	 * Returns the position of a guardian on the board as an integer 

	 * @return the position

	 */

	public int getPosition() {

		return position;

	}
 
 
	/**

	 * Sets the position of a guardian on the board 

	 * @param position the position to set

	 */

	public void setPosition(int position) {

		this.position = position;

	}
	
	/**
	 * @return the hasTakenTurn
	 */
	public boolean isHasTakenTurn() {
		return hasTakenTurn;
	}


	/**
	 * @param hasTakenTurn the hasTakenTurn to set
	 */
	public void setHasTakenTurn(boolean hasTakenTurn) {
		this.hasTakenTurn = hasTakenTurn;
	}
 
 
	

	public void addCredits(int credits) {

		this.balance = balance + credits;

	}

	public void loseCredits(int credits) {

		this.balance = balance - credits;

	}

	/*

	 * called in the moveGuardian method in the Game Master class

	 */

	public void passGoReward(Guardian guardian) {

		guardian.balance += 200;

	}

	/**

	 * Called in the moveGuardian method in the GM class before a Guardian is asked 

	 * if they want to buy an area.

	 * @param canAffordValue

	 * @return

	 */

	public boolean canAfford(int canAffordValue) {

		return balance >= canAffordValue;

	}

	/**

	 * returns descending order of guardians green credits balance

	 * @param compareAgainst

	 * @return

	 */

	public int compareTo(Object compareAgainst) {

		int compareBalance = ((Guardian) compareAgainst).getBalance();

		return compareBalance - this.balance;

	}
	
	

    public List<Area> getOwnedAreas() {
        return ownedAreas;
    }

    public void addOwnedArea(Area area) {
        ownedAreas.add(area);
    }
	
    
    public boolean canInvestInArea(InvestmentArea area) {
        Environment environment = area.getEnvironment();
        for (Area ownedArea : ownedAreas) {
            if (ownedArea instanceof InvestmentArea) {
                InvestmentArea ownedInvestment = (InvestmentArea) ownedArea;
                if (!ownedInvestment.getEnvironment().equals(environment)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidInvestmentLevel(int investmentLevel) {
        // Add your logic here to check if the investment level is valid
        return investmentLevel >= 1 && investmentLevel <= 4;
    }

    
    
    
    
    public void investInArea(InvestmentArea area, int investmentLevel) {
        if (this.canInvestInArea(area) && isValidInvestmentLevel(investmentLevel)) {
            // Check if the guardian owns all areas in the same environment
            Environment environment = area.getEnvironment();
            boolean ownsAllAreasInEnvironment = true;
            for (Area ownedArea : this.getOwnedAreas()) {
                if (ownedArea instanceof InvestmentArea) {
                    InvestmentArea ownedInvestment = (InvestmentArea) ownedArea;
                    if (!ownedInvestment.getEnvironment().equals(environment)) {
                        ownsAllAreasInEnvironment = false;
                        break;
                    }
                }
            }
            if (ownsAllAreasInEnvironment) {
                int investmentCost = area.getBuyCost(); // Cost to buy is the same as cost to invest
                if (this.canAfford(investmentCost)) {
                    // Deduct investment cost from guardian's balance
                    this.loseCredits(investmentCost);
                    // Implement your logic for investing in the area based on the investment level
                    area.setInvestmentLevel(investmentLevel);
                    System.out.println("You have successfully invested in " + area.getName() + "!");
                } else {
                    System.out.println("You cannot afford to invest in this area.");
                }
            } else {
                System.out.println("You must own all areas within the environment before you can invest in this area.");
            }
        } else {
            System.out.println("You cannot invest in this area.");
        }
    }



}
