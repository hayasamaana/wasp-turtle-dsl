#! /usr/bin/zsh
mv LTH1_Turtle/generated_mission.py ros_workspace/src/lth1_turtle_dsl/src/generated_mission.py

chmod +x ros_workspace/src/lth1_turtle_dsl/src/generated_mission.py

cd ros_workspace

catkin_make

cd ..

source ./ros_workspace/devel/setup.zsh

roslaunch lth1_turtle_dsl run_generated.launch
