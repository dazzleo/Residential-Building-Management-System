package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
	@ManyToOne
	public UserAccount sender;
	
	@Required
	@ManyToOne
	public UserAccount receiver;
	  
	@Valid
    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("timestamp")
    @JoinColumn(name="THREAD_ID", referencedColumnName="internal_id")
    public List<Message> messages = new ArrayList<Message>();
	
	public Thread(){};
	
	public Thread(String category, LocalDate date, String subject, UserAccount sender, UserAccount receiver) {
		this.category=category;
		this.date=date;
		this.subject=subject;
		this.sender=sender;
		this.receiver=receiver;
	}
	
	
}