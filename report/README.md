# Group LTH-1
Christian Nelson, Xuhong Li, Aleksander Fabijan

## Xtext grammar
 ```
 CodeFrame/se.chalmers.turtlebotmission.xtext/src/se/chalmers/TurtleBotMissionDSL.xtext
 ```

The DSL grammar was changed to be able to correctly handle the provided example files.

## Meta-model
```
CodeFrame/se.chalmers.turtlebotmission/model/turtlebotmission.ecore
```
```
CodeFrame/se.chalmers.turtlebotmission/model/turtlebotmission.genmodel
```
The metamodel suggests the following (above files):
- 1 Name
- 0..1 Area
- 1 starting position (a Waypoint)
- 1..* WaypointTypes
- 1..* Waypoints
- 0..* Missions

## Code generation and StringTemplates
```
CodeFrame/se.chalmers.turtlebotmission.rosstarter/RosTurtleTemplate.stg
```

The logic for the shortest path and distance calculation is done in the ROS node.
