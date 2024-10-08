/**
* 
*/
package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
* 
*/
public class GameMaster {

	// starting green credits for each Guardian and the reward for passing go
	private static final int STARTING_GREEN_CREDITS = 400; // 500
	private static final int PASS_GO = 150; // 200
	private static final int MIN_GUARDIANS = 2;
	private static final int MAX_GUARDIANS = 4;
	private static final int GAME_ROUNDS = 20;
	private static final int MAX_INVESTMENT_LEVEL = 4;
	private static final String TXT_FILE_PATH = "rules.txt";

	// tracking the number of areas that are owned by a Guardian
	// private int ownedAreas = 0;

	// counter to keep track of the current round
	private int currentRound = 0;

	private Scanner scanner = new Scanner(System.in);

	// Get the dice and the board
	private Board board = new Board();
	private Dice dice = new Dice();

	public void start() {

		

		// Load rules

		List<Rule> rules = loadRulesFromTXT(TXT_FILE_PATH);

		// get number of Guardians
		int numberOfGuardians = getNumberOfGuardians();

		// get Guardian's names
		List<Guardian> guardians = getGuardians(numberOfGuardians);

		int currentGuardian = 0;

		System.out.println();
		try {
			Thread.sleep(1500);
			System.out.println("Let the game...");
			Thread.sleep(1500);
			System.out.println("BEGIN!");
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		// loop through the rounds
		while (currentRound < GAME_ROUNDS) {
			System.out.println("\nROUND " + (currentRound + 1) + " OF " + GAME_ROUNDS);

			// loop over the guardians
			for (int i = 0; i < guardians.size(); i++) {
				Guardian guardian = guardians.get(i);
				System.out.println("\nCurrent Guardian is : \u001B[31m" + guardian.getGuardianName());

				// take turn
				boolean finishedTurn = takeTurn(guardian, guardians);

				// finish turn
				if (!finishedTurn) {
					finishGame(guardians);
					return; // end game if guardian goes bankrupt
				}
			}
			// increment the counter
			currentRound++;
		}
		finishGame(guardians);
	}

	/**
	 * Method to read in the txt file of game rules
	 * @param filePath
	 * @return
	 */
	public List<Rule> loadRulesFromTXT(String filePath) {
		List<Rule> rules = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line;

			while ((line = br.readLine()) != null) {

				String[] parts = line.split(":");

				if (parts.length >= 2) {

					String title = parts[0].trim();

					String description = parts[1].trim();

					rules.add(new Rule(title, description));

				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return rules;

	}

	/**
	 * 
	 * @param filePath
	 */
	public void displayRulesFromFile(String filePath) {

		List<Rule> rules = loadRulesFromTXT(filePath);
		
		System.out.println("\n");
		System.out.println("Best of luck & we hope you enjoy playing Guardians of Gaia! The rules are: ");
		System.out.println("\n");

		for (int i = 0; i < rules.size(); i++) {

			Rule rule = rules.get(i);

			System.out.println(rule.getTitle() + ": " + rule.getDescription());

		}
	}

	/**
	 * Method for the Guardian to take their turn
	 * @param guardian
	 * @param guardians
	 * @return
	 */
	public boolean takeTurn(Guardian guardian, List<Guardian> guardians) {
		if (!answeredYes("\u001B[0mWould you like to take your turn and roll the dice? (y/n)")) {
	        if (answeredYes("Do you want to end the game? (Y/N)")) {
	        	guardian.setHasTakenTurn(false);
	            return false; // End the game
	        } else {
	            // Recursively call takeTurn to continue the game
	            return takeTurn(guardian, guardians);
	        }
		}

		rollDiceAndMove(guardian);

		Area area = board.getArea(guardian.getPosition());
		System.out.println("You have landed on : " + area.getName());
		System.out.println("\nYour balance is : " + guardian.getBalance());
		if (area instanceof InvestmentArea) {
			InvestmentArea ownedArea = (InvestmentArea) area;
			System.out.println("\nEnvironment : " + area.getEnvironment());
	        List<Area> ownedAreasInSameEnvironment = getOwnedAreasInSameEnvironment(guardian, area.getEnvironment());
	        if (ownedAreasInSameEnvironment.isEmpty()) {
	            System.out.println("\nYou don't own any other areas in the " + area.getEnvironment() + " environment yet.");
	        } else {
	            System.out.println("\nYou own the following areas in the " + area.getEnvironment() + " environment:");
	            for (Area ownedAreas : ownedAreasInSameEnvironment) {
	                System.out.println("- " + ownedAreas.getName());
	            }
	        }
			// checking to see if the area is already owned
			if (ownedArea.getOwner() == null) {
				// if area is available then ask the guardian if they want to buy it
				System.out.println("\nThis area is unoccupied!");
				if (guardian.canAfford(ownedArea.getBuyCost())) {
					if (answeredYes("\nWould you like to buy this area for " + ownedArea.getBuyCost()
							+ " GreenCredits? Y or N?")) {

						guardian.loseCredits(ownedArea.getBuyCost());
						ownedArea.setOwner(guardian);
						guardian.addOwnedArea(ownedArea);
						System.out.println("Congratulations! You bought " + ownedArea.getName() + " and now have "
								+ guardian.getBalance() + " GreenCredits.");
					} else {
						conductAuction(guardian, ownedArea, guardians);
					}
					
				} else {
					System.out.println("You can't afford to buy " + ownedArea.getName());
				}

			} else if (ownedArea.getOwner().equals(guardian)) {
				System.out.println("\nYou already own this area- enjoy your visit!");

			} else {
				// Calculate the donation fee based on the investment level
				int donationFee = ownedArea.getDonationFee();
				int investmentLevel = ownedArea.getInvestmentLevel();
				for (int i = 0; i < investmentLevel; i++) {
					donationFee += 10; // Increase the donation fee by 10 for each investment level
				}

				System.out.println("\nThis area is owned by " + ownedArea.getOwner().getGuardianName()
						+ ". The donation fee is " + donationFee + " GreenCredits.");

				
				int canAfford = Math.min(guardian.getBalance(), donationFee);

				// transfer the donation fee to the area owner
				guardian.loseCredits(canAfford);
				ownedArea.getOwner().addCredits(canAfford);

				
				if (canAfford < donationFee) {
					System.out.println("You could only afford to pay " + canAfford
							+ " GreenCredits! You have ran out of GreenCredits and are now bankrupt.");
					return false;
				} else {
					System.out.println(guardian.getGuardianName() + ", you paid the donation fee in full and now have "
							+ guardian.getBalance() + " GreenCredits.");
					if (guardian.getBalance() == 0) {
						System.out.println("You are bankrupt! The game will now end.");
						return false;
					}
				}
			}
		} else if (area instanceof GreenGetaway) {
			GreenGetaway greenGetaway = (GreenGetaway) area;
			System.out.println("\n"+greenGetaway.getRandomResponse());

		} else if (area instanceof RewardArea) {
			RewardArea rewardArea = (RewardArea) area;
			System.out.println("\n" + rewardArea.getRandomResponse());
			System.out.println("\nCongratulations! You received " + rewardArea.getReward() + " GreenCredits!");
			guardian.addCredits(rewardArea.getReward());
			System.out.println("\nYour current balance is: " + guardian.getBalance() + " GreenCredits");

		} else if (area instanceof PunishmentArea) {
			PunishmentArea punishmentArea = (PunishmentArea) area;
			System.out.println("\n"+punishmentArea.getRandomResponse());
			System.out.println("\nOh no! You lost " + punishmentArea.getPunishment() + " GreenCredits!");
			guardian.loseCredits(punishmentArea.getPunishment());
			System.out.println("\nYour current balance is: " + guardian.getBalance() + " GreenCredits");

			if (guardian.getBalance() <= 0) { // Check if the balance is less than or equal to 0
				System.out.println("Unfortunately you are bankrupt - the game is over.");
				// finishGame(guardians); // print the leaderboard
				return false; // End the game
			}
		}

		System.out.println(
				"\n" + guardian.getGuardianName() + ", do you want to invest in any of your owned areas? (Y/N)");
		if (answeredYes("Enter Y to invest, N to reject investment and end turn:")) {
			investInOwnedAreas(guardian);
		}

		System.out.println(guardian.getGuardianName() + ", you have finished your turn.");
		System.out.println("________________________________________________________________________________________");
		guardian.setHasTakenTurn(true);

		return true;
	}


	/**
	 * Allows a guardian to invest in owned areas.
	 * 
	 * @param guardian The guardian who wants to invest.
	 */
	private void investInOwnedAreas(Guardian guardian) {
		System.out.println("List of areas you own:");
		List<Area> ownedAreas = guardian.getOwnedAreas();
		for (int i = 0; i < ownedAreas.size(); i++) {
			System.out.println((i + 1) + ". " + ownedAreas.get(i).getName());

			// Print investment level if the area is an InvestmentArea
			if (ownedAreas.get(i) instanceof InvestmentArea) {
				InvestmentArea investmentArea = (InvestmentArea) ownedAreas.get(i);
				int investmentLevel = investmentArea.getInvestmentLevel();
				System.out.println("Investment level of " + investmentArea.getName() + ": " + investmentLevel);
			}

		}

		// Collect areas based on their environment
		Map<Environment, List<InvestmentArea>> environmentAreasMap = new HashMap<>();
		for (Area area : ownedAreas) {
			if (area instanceof InvestmentArea) {
				InvestmentArea investmentArea = (InvestmentArea) area;
				environmentAreasMap.computeIfAbsent(investmentArea.getEnvironment(), k -> new ArrayList<>())
						.add(investmentArea);
			}
		}

		// Allow the guardian to invest in different areas within various environments
		for (Map.Entry<Environment, List<InvestmentArea>> entry : environmentAreasMap.entrySet()) {
			Environment currentEnvironment = entry.getKey();
			List<InvestmentArea> areasToInvest = entry.getValue();

			boolean ownsAllAreasInEnvironment = false;
			if (currentEnvironment.equals(Environment.RAINFOREST)) {
				ownsAllAreasInEnvironment = ownedAreas.stream().filter(area -> area instanceof InvestmentArea)
						.map(area -> (InvestmentArea) area).filter(area -> area.getName().equals("Amazon")
								|| area.getName().equals("Daintree") || area.getName().equals("Congo"))
						.count() == 3;
			} else if (currentEnvironment.equals(Environment.POLAR)) {
				ownsAllAreasInEnvironment = ownedAreas.stream().filter(area -> area instanceof InvestmentArea)
						.map(area -> (InvestmentArea) area)
						.filter(area -> area.getName().equals("Arctic") || area.getName().equals("Antarctica"))
						.count() == 2;
			} else if (currentEnvironment.equals(Environment.WATER)) {
				ownsAllAreasInEnvironment = ownedAreas.stream().filter(area -> area instanceof InvestmentArea)
						.map(area -> (InvestmentArea) area)
						.filter(area -> area.getName().equalsIgnoreCase("River Nile")
								|| area.getName().equalsIgnoreCase("Gulf of Mexico")
								|| area.getName().equalsIgnoreCase("Pacific Ocean"))
						.count() == 3;
			} else if (currentEnvironment.equals(Environment.URBAN)) {
				ownsAllAreasInEnvironment = ownedAreas.stream().filter(area -> area instanceof InvestmentArea)
						.map(area -> (InvestmentArea) area)
						.filter(area -> area.getName().equals("London") || area.getName().equals("Beijing"))
						.count() == 2;
			}

			if (ownsAllAreasInEnvironment) {
				System.out.println(
						"\nWhich area would you like to invest in for environment " + currentEnvironment + "?");
				for (int i = 0; i < areasToInvest.size(); i++) {
					InvestmentArea area = areasToInvest.get(i);
					int investmentCost = calculateInvestmentCost(area); // Calculate the investment cost
					System.out.println((i + 1) + ". " + area.getName() + " - Investment Cost: " + investmentCost
							+ " GreenCredits");
				}

				// Add an option for the guardian to choose not to invest and move to the next
				// environment
				System.out.println((areasToInvest.size() + 1) + ". Don't invest in this environment");

				// ORIGINAL
				// Handle the guardian's choice
				int choice;
				while (true) {
					try {
						choice = scanner.nextInt();
						if (choice >= 1 && choice <= areasToInvest.size()) {
							InvestmentArea chosenArea = areasToInvest.get(choice - 1);
							int investmentCost = calculateInvestmentCost(chosenArea); // Calculate the investment cost

//							System.out.println("Investment cost for " + chosenArea.getName() + ": " + investmentCost
//									+ " GreenCredits");

							if (guardian.canAfford(investmentCost)) {
//				           
								// Allow the guardian to invest

								if (chosenArea.getInvestmentLevel() < MAX_INVESTMENT_LEVEL) {

									guardian.loseCredits(investmentCost);

									chosenArea.setInvestmentLevel(chosenArea.getInvestmentLevel() + 1);

									System.out
											.println("You have successfully invested in " + chosenArea.getName() + "!");

									System.out.println(
											"Your updated balance is: " + guardian.getBalance() + " GreenCredits");

									if (chosenArea.getInvestmentLevel() == 4) {

										System.out.println("Congratulations you have made a major investment in "
												+ chosenArea.getName() + "!");

									}

								} else {

									System.out.println("You have reached the maximum investments for this Area");

								}

							} else {

								System.out.println("You cannot afford to invest in this area.");

							}

//							if (chosenArea.getInvestmentLevel() < MAX_INVESTMENT_LEVEL) {
//
//								System.out.println("Investment cost for " + chosenArea.getName() + ": " + investmentCost
//										+ " GreenCredits");
//
//							}

							break;

						} else if (choice == areasToInvest.size() + 1) {
							System.out.println("Moving to the next environment.");
							break; // Move to the next environment
						} else {
							System.out.println(
									"Invalid choice. Please enter a number between 1 and " + areasToInvest.size() + 1);
						}
					} catch (InputMismatchException e) {
						System.out.println("Invalid input. Please enter a number.");
						scanner.next();
					}
				}

			} else {
				System.out.println("\nUnfortunately, you must own all areas in environment " + currentEnvironment
						+ " before you can invest in any of these areas.");
			}
			// maybe a break or continue call end of players turn here?????
		}
	}

	// Method to calculate the investment cost based on the current investment level
	private int calculateInvestmentCost(InvestmentArea area) {
		int currentLevel = area.getInvestmentLevel();
		switch (currentLevel) {
		case 0:
			return area.getBuyCost();
		case 1:
			return area.getLevel1Cost();
		case 2:
			return area.getLevel2Cost();
		case 3:
			return area.getLevel3Cost();
		case 4:
			return area.getLevel4Cost();
		default:
			// Handle any other cases (optional)
			return -1; // or any other value indicating an error
		}
	}

	/**
	 * Counts the number of owned areas in the given environment.
	 *
	 * @param ownedAreas  List of owned areas
	 * @param environment Environment to count owned areas for
	 * @return Number of owned areas in the given environment
	 */
	private int countOwnedAreasInEnvironment(List<Area> ownedAreas, Environment environment) {
		int count = 0;
		for (Area ownedArea : ownedAreas) {
			if (ownedArea instanceof InvestmentArea && ((InvestmentArea) ownedArea).getEnvironment() == environment) {
				count++;
			}
		}
		return count;
	}

	private List<Area> getOwnedAreasInSameEnvironment(Guardian guardian, Environment environment) {
	    List<Area> ownedAreasInSameEnvironment = new ArrayList<>();
	    for (Area ownedArea : guardian.getOwnedAreas()) {
	        if (ownedArea instanceof InvestmentArea) {
	            InvestmentArea investmentArea = (InvestmentArea) ownedArea;
	            if (investmentArea.getEnvironment() == environment) {
	                ownedAreasInSameEnvironment.add(investmentArea);
	            }
	        }
	    }
	    return ownedAreasInSameEnvironment;
	}
	/**
	 * Method to have an auction of an area if the guardian who lands on it doesn't
	 * wish to buy it
	 * 
	 * @param originalOwner
	 * @param area
	 * @param guardians
	 */
	public void conductAuction(Guardian originalOwner, InvestmentArea area, List<Guardian> guardians) {
		System.out.println("Offering " + area.getName() + " for auction...");

		// Shuffle the list of guardians (excluding the original owner) to randomise the
		// order
		List<Guardian> shuffledGuardians = new ArrayList<>(guardians);
		shuffledGuardians.remove(originalOwner);
		Collections.shuffle(shuffledGuardians);

		// Iterate through shuffled guardians and ask each if they want to buy the area
		for (Guardian guardian : shuffledGuardians) {
			if (guardian.canAfford(area.getBuyCost())) {
				if (answeredYes(guardian.getGuardianName() + ", would you like to purchase " + area.getName() + " for "
						+ area.getBuyCost() + " GreenCredits? (y/n)")) {
					guardian.loseCredits(area.getBuyCost());
					area.setOwner(guardian); // Update the ownership of the area
					guardian.addOwnedArea(area);
					System.out.println("Congratulations! " + guardian.getGuardianName() + " bought " + area.getName()
							+ " and now has " + guardian.getBalance() + " GreenCredits.");
					return; // Auction ends as soon as a guardian buys the area
				}
			}
		}

		System.out.println("No one decided to purchase " + area.getName() + " in the auction. It remains unowned.");
	}

	/**
	 * The method to move a guardian around the board - also allows guardians to
	 * collect the GO reward if they land on it or pass it
	 * 
	 * @param guardian
	 */
	private void rollDiceAndMove(Guardian guardian) {
		int rollTotal = dice.rollTotal();

		System.out.println(rollTotal);

		int total = guardian.getPosition() + rollTotal;

		if (total >= board.getBoardSize()) {
			guardian.setBalance(guardian.getBalance() + PASS_GO);
			System.out.println("You passed GO and collected " + PASS_GO + " GreenCredits!");
		}

		int newPosition = total % board.getBoardSize();
		guardian.setPosition(newPosition);
	}

	private List<Guardian> getGuardians(int numberOfGuardians) {

		List<Guardian> guardianList = new ArrayList<Guardian>();

		String name;

		for (int i = 0; i < numberOfGuardians; i++) {
			try {
				System.out.println("Please enter your Guardian name (max 25 characters): " + (i + 1));
				name = scanner.nextLine().trim();
				if (name.isEmpty()) {
					throw new IllegalArgumentException("Invalid name. Please enter your name");
				}
				if (name.length() < 1 || name.length() > 25) {
					throw new IllegalArgumentException("Error - name must be between 1 and 25 characters");
				}
				boolean valid = true;
				for (int j = i - 1; j >= 0; j--) {
					if (name.equalsIgnoreCase(guardianList.get(j).getGuardianName())) {
						valid = false;
						break;
					}
				}
				if (valid) {
					guardianList.add(new Guardian(name, STARTING_GREEN_CREDITS, 0));
				} else {
					System.out.println("Name \"" + name + "\" has already been entered, please try again.");
					i--;
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				i--;
			}
		}

		return guardianList;
	}

	/**
	 * Will ask how many Guardians will be playing, and will only continue if 2-4 is
	 * picked
	 * 
	 * @return
	 */
	private int getNumberOfGuardians() {
		int numberOfGuardians;

		System.out.println("How many guardians will be saving Gaia? (Must be between 2 and 4)");

		while (true) {
			if (scanner.hasNextInt()) {
				numberOfGuardians = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				if (numberOfGuardians >= MIN_GUARDIANS && numberOfGuardians <= MAX_GUARDIANS) {
					return numberOfGuardians;
				} else {
					System.out.println("You need 2 - 4 Guardians to play! Please try again");
				}
			} else {
				System.out.println("Invalid input. Please enter a valid integer.");
				scanner.nextLine(); // Consume the invalid input
			}
		}
	}

	private void finishGame(List<Guardian> guardianList) {
		System.out.println("\nThe game has ended! Here is the leaderboard : ");
		
		// create a list to store active guardians who have taken their turn
		List<Guardian> activeGuardians = new ArrayList<Guardian>();
		
		// create variable to store the guardian that refused to take their turn
		Guardian refuseTurnGuardian = null;
		
		// separate the guardians that have taken their turn from the guardian that refused their turn
		for (Guardian guardian : guardianList) {
			if (guardian.isHasTakenTurn()) {
				activeGuardians.add(guardian);
			} else {
				refuseTurnGuardian = guardian;
			}
		}

		Collections.sort(activeGuardians);
		
		// display the leaderboard of the guardians that have completed their turn
		for (int i = 0; i < activeGuardians.size(); i++) {
			Guardian guardian = activeGuardians.get(i);
			System.out.println("Position " + (i + 1) + ": " + guardian.getGuardianName() + " finished with "
	                + guardian.getBalance() + " GreenCredits");
		} 
		
		// If there is a guardian that refused their turn, add them to the bottom with the description
		if (refuseTurnGuardian != null) {
			 System.out.println(refuseTurnGuardian.getGuardianName() + " - Does not place - refused to take turn");
		}
	}

	/**
	 * A scanner method to call when a Guardian is being asked a question throughout
	 * the game
	 * 
	 * @param question
	 * @return
	 */
	private boolean answeredYes(String question) {
		String answer;

		while (true) {
			System.out.println(question);
			answer = scanner.next();
			if (answer.equalsIgnoreCase("n")) {
				return false;
			} else if (answer.equalsIgnoreCase("y")) {
				return true;
			}
		}
	}

	/**
	 * When first starting the game, this will play
	 */
	public void welcomeMessage() {
		System.out.println("\t\t\t\tWelcome to Guardians of Gaia!\n");
		System.out.println(" \tAs you journey across the board, you'll encounter areas devastated by pollution,\n "
				+ "\teach representing a crucial part of our world. Your mission: to restore these places and\n "
				+ "\tsave our planet before it's too late. Are you ready to embark on this eco-friendly adventure?");
		System.out.println();

		int userOption = 0;
		String userDecision = null;

		try {
			System.out.println("Please choose one from the following options: ");
			System.out.println("1. Start New Game");
			System.out.println("2. Read the Rules");
			System.out.println("3. End Game");

			System.out.println();
			System.out.println("Please make your selection (enter 1-3) ...");

			do {
				if (scanner.hasNextInt()) {
					userOption = scanner.nextInt();

					switch (userOption) {
					case 1:
						System.out.println("You have chosen to start a New Game...");
						start();
						break;
					case 2:

						displayRulesFromFile(TXT_FILE_PATH);

						System.out.println("\n\n");

						welcomeMessage();
						break;

					case 3:
						System.out.println("Are you sure you want to end the game? y/n");
						userDecision = scanner.next();
						if (userDecision.equalsIgnoreCase("y")) {
							System.out.println("Sorry to see you go! The game has ended.");
							System.out.print("\n\n");
							System.exit(userOption);
						} else {
							welcomeMessage();
						}
						break;
					default:
						System.out.println();
					}

				} else {
					System.out.println("Invalid input. Please enter a valid number between 2-4.");
					scanner.next();
				}

			} while (true);

		} catch (InputMismatchException e) {
			System.out.println("Error");
			start();
		}
	}
}