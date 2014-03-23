package controllers.communications;


import play.*;
import play.mvc.*;
import static play.mvc.Results.*;
import play.data.*;
import play.libs.*;
import models.*;
import play.data.validation.*;
import static play.data.validation.Constraints.*;

import javax.validation.*;

import views.html.*;

import java.util.*;

import org.joda.time.LocalDate;

import controllers.Message;
import controllers.routes;

public class Threads extends Controller {
	  static public Form<Thread>  threadForm  = Form.form(Thread.class);

	  /*public static Result registerChat() {
	      Map<String, String> m = new HashMap<String, String>();
	      int nextOccurrence =
	        Chat.occurrencesFor(LocalDate.now())+1;
	      m.put("occurrence", ""+nextOccurrence);
	      return ok(views.html.chat.render(chatForm.bind(m)));
	  }*/

	  public static Result loadThread() {
	    Map<String,String[]> queryString = request().queryString();
	    Long threadId = Long.parseLong(queryString.get("threadid")[0]);

	    Thread thread = Thread.find
	                      .where()
	                        .eq("internalId", ThreadId)
	                        .join("messages")
	                          .fetch("messages.sender")
	                          .fetch("messages.receiver")
	                        .findUnique();

	    return ok(
	      views.html.thread.render(thread, messageForm)
	    );
	  }

	  public static Result allThreads() {
	      return ok(
	          views.html.threads.render(Thread.find.all())
	      );
	  }

	  public static Result createThread() {
	      Form<Thread> boundForm = threadForm.bindFromRequest();
	      if (boundForm.hasErrors()) {
	          return badRequest("Cannot create thread : "
	            + boundForm.errorsAsJson().toString());
	      } else {
	          Thread thread = boundForm.get();

	          thread.save();
	          return redirect( routes.Threads.allThreads() );
	      }
	  }

	  static public Form<Message>  messageForm  = Form.form(Message.class);
	  
	  public static Result talk(Long threadId) {
	    UserAccount sender = UserAccount.find.byId(session("email"));
	    Thread Thread = Thread.find
	                      .where()
	                        .eq("internalId", threadId)
	                      .join("messages")
	                    .findUnique();
	    
	    Form<Item> boundForm = messageForm.bindFromRequest();
	    Message message = messageForm.bindFromRequest().get();
	    message.sender = sender;
	    thread.messages.add(message);

	    thread.save();

	    return ok(
	        views.html.thread.render(thread, messageForm)
	    );
	  }


	  /*public static Form<Image> imageForm = Form.form(Image.class);
	  public static Result receiveImage(Long chatId) {
	    User user = User.find.byId(session("email"));
	    Chat chat = Chat.find
	                      .where()
	                        .eq("internalId", chatId)
	                      .join("items")
	                    .findUnique();

	    //  GET SOME DATA FROM THEN URL FORM ENCODED DATA
	    Form<Image> filledForm = imageForm.bindFromRequest();
	    if(filledForm.hasErrors()) {
	      return badRequest(
	          filledForm.errors().toString()
	      );
	    } else {
	      Http.MultipartFormData body;
	      //  RECOVER THE WHOLE BODY AS MULTIPART
	      body = request().body().asMultipartFormData();

	      //  THE PLAY2 API PROVIDES A WAY TO GET THE FILE
	      //    +> SO EASILY !!!
	      Http.MultipartFormData.FilePart pic = body.getFile("pic");
	      //  CHECK THE IMAGE TYPE IS VALID -- part of the enum
	      if(Image.ImageType.get(pic.getContentType()) == null) {
	        return badRequest(
	          views.html.chatroom.render(chat, itemForm, imageForm)
	        );
	      }

	      Image image = filledForm.get();

	      image.pic = pic.getFile();
	      image.filePath = pic.getFile().getPath();
	      image.user = user;
	      chat.images.add(image);
	      chat.save();

	      return ok(
	          views.html.chatroom.render(chat, itemForm, imageForm)
	      );
	    }*/
}
	