/**
* 
*/
package project;
 
import java.util.ArrayList;
import java.util.Random;
 
/**
* This class represents one square on the board called Green Getaway
* When a Guardian lands on this - there are no consequences 
* Maybe print a guideline on how someone can help the environment!
*/
public class GreenGetaway extends Area {

public ArrayList<String> responseList;
	public GreenGetaway(String name) {
		super(name);
		this.responseList = new ArrayList<String>();
		initialiseResponseList();
	}
	public void initialiseResponseList() {
		responseList.add("Experienced a stay in an eco-friendly treehouse resort, surrounded by lush greenery and connected with nature");
		responseList.add("Enjoy a simulated getaway to an off-grid cabin, powered by renewable energy sources like solar panels and wind turbines");
		responseList.add("Indulge in a virtual spa retreat that follows eco-friendly practices, using natural and organic products and incorporating energy-efficient technologies");
		responseList.add("You went on a wildlife sanctuary safari!");
		responseList.add("Enjoy a virtual carbon-neutral transportation experience during your stay in the Green Getaway, utilising electric vehicles, bicycles, or walking");
		responseList.add("Turning off lights when not in use helps conserve energy");
		responseList.add("Reduce, reuse, recycle - the planet thanks you");
		responseList.add("You planted a tree today! Keep up the good work");
	}
	public void onGuardianLanding(Guardian guardian) {
		String randomResponse = getRandomResponse();
		System.out.println(randomResponse);
	}
	public String getRandomResponse() {
		Random random = new Random();
		int index = random.nextInt(responseList.size());
		return responseList.get(index);
	}
}

