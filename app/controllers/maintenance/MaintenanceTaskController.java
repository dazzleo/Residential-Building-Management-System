package controllers.maintenance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.ApartmentBuilding;
import models.Bill;
import models.MaintenanceTask;
import models.UserAccount;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class MaintenanceTaskController extends Controller {
	
	static public Form<MaintenanceTask> taskForm  = Form.form(MaintenanceTask.class);
	
	public static Result createTask(){		
		return ok(views.html.maintenance.createTask.render(taskForm));
	}
	
	public static Result postTask() {
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		
		String taskType = params.get("taskType")[0];
		String description = params.get("description")[0];
		String status = params.get("status")[0];
		String dline = params.get("deadline")[0];
		Logger.info(dline);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date deadline = null;
		
		try {
			deadline = dateFormat.parse(dline);
			Logger.debug(deadline.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserAccount user =  UserAccount.find.byId(Long.parseLong(session("userId")));
		ApartmentBuilding building = user.apartment.apartmentBuilding;
    	
    	MaintenanceTask maintenanceTask = new MaintenanceTask(building, taskType, description, status, deadline);
    	maintenanceTask.save();
    	taskForm = taskForm.fill(maintenanceTask);
    	return ok(views.html.maintenance.postTask.render(taskForm));
    	//return ok("Registered " + bill.toString());
    }
	
	public static Result showAllTasks() {		
		List<MaintenanceTask> tasks = MaintenanceTask.find.all();
		return ok(views.html.maintenance.showTasks.render(tasks, "Maintenance Tasks"));
	}
	
	public static Result showActiveTasks() {
		Date now = new Date();
		List<MaintenanceTask> activeTasks = MaintenanceTask.find.where().eq("status", "Queued").findList();
		return ok(views.html.maintenance.showQueuedTasks.render(activeTasks, "Active Tasks"));
		//return ok(views.html.maintenance.viewactivetasks.render(activeTasks));
	}
	
	public static Result showArchivedTasks() {
		Date now = new Date();
		List<MaintenanceTask> archivedTasks = MaintenanceTask.find.where().lt("deadline", now).findList();
		return ok(views.html.maintenance.showTasks.render(archivedTasks, "Archived Tasks"));
		//return ok(views.html.maintenance.viewarchivedtasks.render(activeTasks));
	}
	
	public static Result setTaskCompleted(Long id) {
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		MaintenanceTask task = MaintenanceTask.find.byId(id);
		task.status = "Completed";
		task.save();
		return showActiveTasks();		
	}
	
}
