package se.chalmers.turtlebotmission.rosstarter.handlers;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import turtlebotmission.LineTask;
import turtlebotmission.Mission;
import turtlebotmission.ReturnToStartTask;
import turtlebotmission.ShortestPathTask;
import turtlebotmission.Task;
import turtlebotmission.TurtleBot;
import turtlebotmission.WayPoint;
import turtlebotmission.impl.TurtleBotImpl;

/**
 * A handler that is called when the user clicks on the turtlebot icon in the menu.
 * Allows to calculate a list of waypoints to visit and then initiates the creation of the Python file.
 * 
 */
public class CreatePythonFromModelHandler extends AbstractHandler {

	/**
	 * Called whenever a user clicks on the turtlebot icon
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (editor instanceof XtextEditor) {
			IXtextDocument doc = ((XtextEditor) editor).getDocument();

			doc.modify(new IUnitOfWork<Void, XtextResource>() {
				@Override
				public java.lang.Void exec(XtextResource archimodel) {
					// now we access the model 
					for (EObject modelObject : archimodel.getContents()) {
						if (modelObject instanceof TurtleBotImpl) {
							
							//Now you have the access to your model:
							TurtleBot turtle = (TurtleBot) modelObject;
							
							
							IWorkbenchWindow window;
							try {
								window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
								MessageDialog.openInformation(window.getShell(), "Launch ROS",
										"You will find the resulting file in this workspace, in generated_mission.py");
								
								
								//This is were you should parse the model, create a plan, and fill the string template
								//Don't hesitate to use extra classes and methods to structure your code
								String pythoncode = "This is a placeholder";
								
								
								// https://stackoverflow.com/questions/28001536/read-templates-from-a-file-stringtemplate
								// Load the file
								final STGroup stGroup = new STGroupFile("RosTurtleTemplate.stg");
								
								// Pick the correct template
								final ST pythonTurtleTemplate = stGroup.getInstanceOf("pythonTurtleTemplate");
								
								// Fill the start position of the Turtle
								int startX = turtle.getBot_start().getCoord_x();
								int startY = turtle.getBot_start().getCoord_y();
								pythonTurtleTemplate.add("bot_start", "(" + startX + ", " + startY + ")" );
								
								// Get the Waypoints
								for (WayPoint wp : turtle.getWaypoints()) {
									pythonTurtleTemplate.add("waypoints", "\"" + wp.getName() + "\":(" + wp.getCoord_x() +"," + wp.getCoord_y() + ")");
								}
								
								// TODO: Get the Missions
								for (Mission ms : turtle.getMissions()) {
									
									ArrayList<String> task_list = new ArrayList<>();
									
									for (Task tsk : ms.getTask()) {
										
										ArrayList<String> wp_list = new ArrayList<>();
										
										String taskName = tsk.getClass().getSimpleName();
										
										// Remove the "Impl" from the end of the task name
										int n = "Impl".length();
										taskName = taskName.substring(0, taskName.length() - n);
										
										switch(taskName) {
											case "LineTask":{
												LineTask task = (LineTask) tsk;
												for (WayPoint wp : task.getWaypoints()) {
													wp_list.add("\"" + wp.getName() + "\"");
												}
												//task_list.add("[\"" + taskName + "\", " + wp_list + "]");
												break;	
											}
											case "ShortestPathTask":{
												ShortestPathTask task = (ShortestPathTask) tsk;
												for (WayPoint wp : task.getWaypoints()) {
													wp_list.add("\"" + wp.getName() + "\"");
												}
												//task_list.add("[\"" + taskName + "\", " + wp_list + "]");
												break;
											}
											case "ReturnToStartTask":{
												//task_list.add("[\"" + taskName + "\", " + "[]" + "]");
												break;
											}
										} // End Switch
										
										task_list.add("[\"" + taskName + "\", " + wp_list + "]");
										
									} // End for tasks
									pythonTurtleTemplate.add("missions", "[\"" + ms.getName() + "\"," + task_list + "]");
								}
								
								
								IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
								IProject myProject = myWorkspaceRoot.getProjects()[0];
								// create a new file
								IFile resultFile = myProject.getFile("generated_mission.py");
								try {
									if (!resultFile.exists())
										resultFile.create(new ByteArrayInputStream(new byte[0]), false, null);
									
									//fill the file
									resultFile.setContents(new ByteArrayInputStream(pythonTurtleTemplate.render().getBytes("UTF-8")), 0, null);
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
							} catch (ExecutionException e) {
								e.printStackTrace();
							}
						}
					}
					return null;
				}
			});
		}

		return null;
	}


}
