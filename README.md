# Guardians of Gaia - A Virtual Board Game
## CSC7053 Software Engineering Project 2023-2024

### Overview
Guardians of Gaia is a text-based virtual board game developed as part of a group project for the Software
Engineering module in my MSc in Software Development. Built using Java and adhering to Object-Oriented 
Principles (OOP), the game challenges players to take on the role of Guardians striving to protect 
the environment while strategically managing resources.

### Features
- Turn-Based Gameplay: Players roll dice, move across the board, and interact with different types of areas.
- Auction System: Conducted when players land on unowned areas, allowing strategic investments.
- Investment Mechanism: Players can upgrade owned areas to increase their environmental impact.
- Dynamic Board Interactions: Unique situations that provide rewards, penalities and opportunities.
- Leaderboard: Tracks players' progress based on their Green Credit balance.

### Technologies Used
- Java
- JUnit
- Eclipse IDE
- Git & GitLab
- Jira for Agile project management

### Setup & Installation
#### Prerequisites
Have the following installed:
- JDK17 or later
- Eclipse IDE (or another Java supported IDE)
- Git for cloning the repository

#### Steps to Run Locally
Import into Eclipse (Manual Java Project Setup)

1. Download the ZIP of this repo or clone it
2. Open Eclipse IDE.
3. Go to File > New > Java Project.
4. Name the project (e.g. VirtualBoardGame) and uncheck “Use default location”.
5. Click Browse and select the folder that contains the src/ directory.
6. Click Finish.
7. If prompted, allow Eclipse to set the src folder as a source folder.
8. In the Project Folder, open Game.java (located in src).
9. Right-click on Game.java > Run As > Java Application.

Enjoy the game — follow the in-terminal prompts to play!

### My Contributions
- Implemented most of the game's core functionality, including the player moving around the board mechanics, conductAuction() method, game rounds and player
exclusion mechanics.
- Identified and resolved multiple bugs.
- Created key UML diagrams such as use case, activity and sequence diagrams to support design and development.
- Designed all artwork for the project.
- Took a proactive role in facilitating group discussions and booking study rooms to work collaboratively.

### Note
I removed the bin folder from the repo because it just contained compiled files that Eclipse generates automatically. The important stuff, the actual source code, is all in the src folder. This keeps the project clean and makes it easier to read and run!
