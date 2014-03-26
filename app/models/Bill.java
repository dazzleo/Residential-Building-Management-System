package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.format.Formats;

import play.db.ebean.*;

import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
public class Bill extends Model {

	private static final long serialVersionUID = -8662085771427157296L;

	@Id
	@GeneratedValue
	public Long id;

	@ManyToOne
	public Apartment apartment;

	@Required
	public String description;

	@Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date dateOfIssuing = new Date();

	@Required
	// @Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date deadline;

	@Required
	public String status;

	@Required
	public Double amount;

	public static Finder<Long, Bill> find = new Finder<Long, Bill>(Long.class,
			Bill.class);

	public Bill() {
	}

	@Override
	public String toString() {
		return "Bill\n" + description + ", Date Issued : " + dateOfIssuing
				+ ", Deadline = " + deadline + ", status = " + status
				+ ", Amount = " + amount;
	}
}
