/**
 * 
 */
package project;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the two reward squares on the board 
 * Sustainable surprise and Renewable Rewards
 * The Guardian will receive 50 GC for landing on this square
 */
public class RewardArea extends Area {
	
	private int reward;

	/**
	 * @param name
	 * @param reward
	 */
	public ArrayList<String> rewardList;
	public RewardArea(String name, int reward) {
		super(name);
		this.reward = reward;
		this.rewardList = new ArrayList<String>();
		initialiseRewardList();
	}
	public void initialiseRewardList() {
	rewardList.add("You Recycled Instead of Throwing Away!");
	rewardList.add("Congratulations for Conserving Water! Treat yourself to a relaxing bath knowing you've helped conserve water.");
	rewardList.add("You've Been Using Energy Wisely! Enjoy an eco-friendly movie night with friends.");
	rewardList.add("You Opted for Reusable Products! Why not buy yourself a stylish reusable water bottle.");
	rewardList.add("Congratulations for Choosing Sustainable Transportation! Treat yourself to a scenic bike ride or hike in nature.");
	rewardList.add("You've Been Reducing Food Waste!");
	rewardList.add("You Switched to Eco-Friendly Cleaning Products! Reward yourself with a relaxing day outdoors.");
	rewardList.add("You've Been Promoting Environmental Awareness! You can share tips and inspiration for living more sustainably.");
}
public void onGuardianLanding(Guardian guardian) {
	String randomResponse = getRandomResponse();
	System.out.println(randomResponse);
}
public String getRandomResponse() {
	Random random = new Random();
	int index = random.nextInt(rewardList.size());
	return rewardList.get(index);
}

	/**
	 * @return the reward
	 */
	public int getReward() {
		return reward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(int reward) {
		this.reward = reward;
	}
}




