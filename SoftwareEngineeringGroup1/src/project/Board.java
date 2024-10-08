/**
* 
*/
package project;
 
import java.util.ArrayList;
import java.util.List;
 
/**
* 
*/
public class Board {
	private List<Area> board = new ArrayList<Area>();

	public Board() {
		board.add(new Area("GO"));
		board.add(new InvestmentArea("Arctic", 25, 50, Environment.POLAR, 60, 70, 80, 100));
		board.add(new RewardArea("Sustainable Surprise", 50));
		board.add(new InvestmentArea("Antarctica", 30, 60, Environment.POLAR, 70, 80, 90, 120));
		board.add(new PunishmentArea("Junktopia", 50));
		board.add(new InvestmentArea("Daintree", 35, 70, Environment.RAINFOREST, 80, 90, 100, 140));
		board.add(new InvestmentArea("Congo", 40, 80, Environment.RAINFOREST, 90, 100, 100, 160));
		board.add(new InvestmentArea("Amazon", 45, 90, Environment.RAINFOREST, 100, 110, 120, 180 ));
		board.add(new GreenGetaway("Green Getaway"));
		board.add(new InvestmentArea("River Nile", 60, 120, Environment.WATER, 130, 140, 150, 240));
		board.add(new InvestmentArea("Gulf of Mexico", 70, 140, Environment.WATER, 150, 160, 170, 280));
		board.add(new RewardArea("Renewable Rewards", 50));
		board.add(new InvestmentArea("Pacific Ocean", 80, 160, Environment.WATER, 170, 180, 190, 320));
		board.add(new PunishmentArea("Pollution Pit", 50));
		board.add(new InvestmentArea("London", 100, 200, Environment.URBAN, 210, 220, 230, 400));
		board.add(new InvestmentArea("Beijing", 125, 250, Environment.URBAN, 260, 270, 280, 500));

//		// Mary OPTION 1
//		board.add(new Area("GO"));
//		board.add(new InvestmentArea("Arctic", 25, 50, Environment.POLAR, 50, 50, 50, 50));
//		board.add(new RewardArea("Sustainable Surprise", 50));
//		board.add(new InvestmentArea("Antarctica", 30, 60, Environment.POLAR, 60, 60, 60, 60));
//		board.add(new PunishmentArea("Junktopia", 50));
//		board.add(new InvestmentArea("Daintree", 35, 70, Environment.RAINFOREST, 70, 70, 70, 70));
//		board.add(new InvestmentArea("Congo", 40, 80, Environment.RAINFOREST, 80, 80, 80, 80));
//		board.add(new InvestmentArea("Amazon", 45, 90, Environment.RAINFOREST, 90, 90, 90, 90 ));
//		board.add(new GreenGetaway("Green Getaway"));
//		board.add(new InvestmentArea("River Nile", 60, 120, Environment.WATER, 120, 120, 120, 120));
//		board.add(new InvestmentArea("Gulf of Mexico", 70, 140, Environment.WATER, 140, 140, 140, 140));
//		board.add(new RewardArea("Renewable Rewards", 50));
//		board.add(new InvestmentArea("Pacific Ocean", 80, 160, Environment.WATER, 160, 160, 160, 160));
//		board.add(new PunishmentArea("Pollution Pit", 50));
//		board.add(new InvestmentArea("London", 100, 200, Environment.URBAN, 200, 200, 200, 200));
//		board.add(new InvestmentArea("Beijing", 125, 250, Environment.URBAN, 250, 250, 250, 250));
		
		
		
		
		
	}
	public int getBoardSize() {
		return board.size();
	}
	public Area getArea(int position) {
		return board.get(position);
	}
 
}