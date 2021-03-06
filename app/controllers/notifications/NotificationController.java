package controllers.notifications;

import java.util.ArrayList;
import java.util.List;

import models.BillNotification;
import models.MessageNotification;
import models.Notification;
import play.mvc.Controller;
import play.mvc.Result;
import enums.NotificationStatus;

public class NotificationController extends Controller {

    public static Result index() {
        List<Notification> notifications = new ArrayList<Notification>();
        
        List<BillNotification> billNotifications = BillNotification.find
        		.where()
        		.eq("status", NotificationStatus.Unread)
        		.eq("receiver_id", session("userId"))
        		.findList();
        
        List<MessageNotification> messageNotifications = MessageNotification.find
        		.where()
        		.eq("status", NotificationStatus.Unread)
        		.eq("receiver_id", session("userId"))
        		.findList();
        
        for (BillNotification billNotification : billNotifications) {
        	billNotification.status = NotificationStatus.Read;
        	billNotification.save();
        }
        
        for (MessageNotification messageNotification : messageNotifications) {
        	messageNotification.status = NotificationStatus.Read;
        	messageNotification.save();
        }
        
        notifications.addAll(billNotifications);
        notifications.addAll(messageNotifications);
        
        return ok(views.html.notification.notification.render(notifications));
    }
}
