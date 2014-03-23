package models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.LocalDate;

import play.data.validation.Constraints.Required;

@Entity
public class Thread extends play.db.ebean.Model {

	private static final long serialVersionUID = 6633572079396129103L;

	@Id
	@GeneratedValue
	public Long internalId;
	
	@Required
	public String category;
	
	@Required 
	public LocalDate date;
	
	public String subject="(No Subject)";
	
	@Required
	public Long senderId;
	
	@Required
	public Long receiverId;
	  
	@Valid
    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("timestamp")
    @JoinColumn(name="THREAD_ID", referencedColumnName="internal_id")
    public List<Message> items = new ArrayList<Message>();
	
	public Thread(){};
	
	public Thread(String category, LocalDate date, String subject, Long senderId, long receiverId) {
		this.category=category;
		this.date=date;
		this.subject=subject;
		this.senderId=senderId;
		this.receiverId=receiverId;
	}
	
	
}
