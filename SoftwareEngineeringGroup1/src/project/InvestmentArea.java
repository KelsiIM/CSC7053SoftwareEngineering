/**
* 
*/
package project;

/**
 * Represents an Area on the board that can be owned by a Guardian
 */
public class InvestmentArea extends Area {
	private int donationFee;
	private int buyCost;
	private Environment environment;
	private Guardian owner;
	private int investmentLevel;
	private int level1cost;
	private int level2cost;
	private int level3cost;
	private int level4cost;

	public InvestmentArea() {
	}

	/**
	 * @param donationFee
	 * @param buyCost
	 * @param environment
	 * @param owner
	 */
	public InvestmentArea(String name, int donationFee, int buyCost, Environment environment, int level1cost,
			int level2cost, int level3cost, int level4cost) {
		super(name);
		this.donationFee = donationFee;
		this.buyCost = buyCost;
		this.environment = environment;
		this.investmentLevel = 0;
		this.setLevel1Cost(buyCost + 20);
		this.setLevel2Cost(buyCost + 30);
		this.setLevel3Cost(buyCost + 40);
		this.setLevel4Cost(buyCost + 100);

	}

	/**
	 * @return the donationFee
	 */
	public int getDonationFee() {
		return donationFee;
	}

	/**
	 * @param donationFee the donationFee to set
	 */
	public void setDonationFee(int donationFee) {
		this.donationFee = donationFee;
	}

	/**
	 * @return the buyCost
	 */
	public int getBuyCost() {
		return buyCost;
	}

	/**
	 * @param buyCost the buyCost to set
	 */
	public void setBuyCost(int buyCost) {
		this.buyCost = buyCost;
	}

	/**
	 * @return the environment
	 */
	public Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * @return the owner
	 */
	public Guardian getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Guardian owner) {
		this.owner = owner;
	}

	/**
	 * @return the investmentLevel
	 */
	public int getInvestmentLevel() {
		return investmentLevel;
	}

	/**
	 * @param investmentLevel the investmentLevel to set
	 */
	public void setInvestmentLevel(int investmentLevel) {
		this.investmentLevel = investmentLevel;
	}

	/**
	 * @return the level1
	 */
	public int getLevel1Cost() {
		return level1cost;
	}

	public void setLevel1Cost(int buyCost) {
		this.level1cost = buyCost + 10;
	}

	/**
	 * @return the level2
	 */
	public int getLevel2Cost() {
		return level2cost;
	}

	/**
	 * @param level2cost the level2 to set
	 */
	public void setLevel2Cost(int buyCost) {
		this.level2cost = buyCost + 20;
	}

	/**
	 * @return the level3
	 */
	public int getLevel3Cost() {
		return level3cost;
	}

	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3Cost(int buyCost) {
		this.level3cost = buyCost + 30;
	}

	/**
	 * @return the level4
	 */
	public int getLevel4Cost() {
		return level4cost;
	}

	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4Cost(int buyCost) {
		this.level4cost = buyCost * 2; // corrected from +60 to *2
	}

}
