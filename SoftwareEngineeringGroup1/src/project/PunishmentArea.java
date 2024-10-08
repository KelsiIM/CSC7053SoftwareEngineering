/**
 * 
 */
package project;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the two punishment squares on the board - Junktopia and Pollution Pit
 * If a Guardian lands on one of these squares, they are deducted 50 GC
 * 
 */
public class PunishmentArea extends Area {
	
	private int punishment;

	/**
	 * @param name
	 * @param punishment
	 */
	public ArrayList<String> punishmentList;
	public PunishmentArea(String name, int punishment) {
		super(name);
		this.punishment = punishment;
		this.punishmentList = new ArrayList<String>();
		initialisePunishmentList();
	}
	public void initialisePunishmentList() {
	punishmentList.add("You've Been Caught Littering! Remember, every piece of litter contributes to environmental pollution.");
	punishmentList.add("Shame on You for Wasting Water! You should find ways to conserve water in your daily activities to make up for your wastefulness.");
	punishmentList.add("You've Left Lights On Unnecessarily! You should understand the importance of conserving energy and reducing your carbon footprint.");
	punishmentList.add("Caught Using Single-Use Plastics! Plastic pollution harms marine life and pollutes our oceans.");
	punishmentList.add("You've Been Overusing Disposable Products! Use only reusable items to reduce waste and minimize your environmental impact.");
	punishmentList.add("You've Been Wasting Food! Volunteer at a local food bank or soup kitchen to help combat food waste and support those in need.");
	punishmentList.add("Caught Using Harmful Chemicals! Switch to eco-friendly cleaning products to protect waterways and wildlife from pollution.");
	punishmentList.add("You've Been Ignoring Recycling! Educate yourself on proper recycling practices to reduce landfill waste.");
}
public void onGuardianLanding(Guardian guardian) {
	String randomResponse = getRandomResponse();
	System.out.println(randomResponse);
}
public String getRandomResponse() {
	Random random = new Random();
	int index = random.nextInt(punishmentList.size());
	return punishmentList.get(index);
}
	/**
	 * @return the punishment
	 */
	public int getPunishment() {
		return punishment;
	}

	/**
	 * @param punishment the punishment to set
	 */
	public void setPunishment(int punishment) {
		this.punishment = punishment;
	}

}

