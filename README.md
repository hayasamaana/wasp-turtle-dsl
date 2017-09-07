[Assignement (PDF)](http://wasp-sweden.org/custom/uploads/2017/03/Wasp_Turtlebot_DSL.pdf)

# Installation
1. Download [Eclipse Neon](https://www.eclipse.org/neon/)
    - Install Xtext plugin:
URL: http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/
Choose Help -> Install New Software... from the menu bar and Add....
Insert one of the update site URLs above. This site aggregates all the necessary and optional components and dependencies of Xtext.
Select the Xtext SDK from the category Xtext and complete the wizard by clicking the Next button until you can click Finish.
After a quick download and a restart of Eclipse, Xtext is ready to use.

 - Install modeling tools the same way as above:
	http://download.eclipse.org/ecoretools/updates/releases/3.2.0/neon

# Environment
This have been implemented in ubuntu 16.04 LTS and with ROS Kinetic with the turtlesim packages.

# Setting up the environment
1. Start Eclipse
2. Import Existing Project from Directory
3. Select `CodeFrame` in root of this repository
3. Deselect `CodeFrame` from list.
4. Finish. The IDE Should load.
5. Right click on
 ``` se.chalmers.turtlebotmission.xtext/src/se/chalmers/TurtleBotMissionDSL.xtext```
 and select Run As... / Generate Xtet Artifacts
 6. Right Click on ```se.chalmers.turtlebotmission.xtext``` and Run as.../Eclipse Application
 7. Your TurtleBot DSL IDE / Editor should start.
 8. Import Project from Directory
 9. Select `LTH1_Turtle`
 10. Right click one of the DSL files and select `Open width`
 11. Find `TurtleBotMission DSL Editor` and associate it as Default for all files with the same extension.
 12. Click on the TurtleBot-button
 13. A file called `generated_mission.py` is created in is located in `LTH1_Turtle` directory
 14. run `./run_simulator.zsh`
