package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import play.data.format.Formats;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import enums.AccountType;

@Entity
public class UserAccount extends Model {

	private static final long serialVersionUID = -3698872425235311089L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	@MaxLength(50)
	public String name;
	
	@MinLength(5)
	@MaxLength(60)
	@Column(unique=true)
	public String email;
	
	@Required
	@MinLength(6)
	@MaxLength(20)
	public String phone;
	
	@Required
	@MinLength(4)
	@MaxLength(20)
	public String password;
	
	@Enumerated(EnumType.STRING)
	public AccountType accountType = AccountType.Resident;
	
//	@Required
//	@Enumerated(EnumType.STRING)
//	public VerificationStatus verificationStatus;
	
	@Formats.DateTime(pattern="dd/MM/yyyy")
	public Date joinDate = new Date();
	
	@Valid
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="apartment_id")
	public Apartment apartment;
	
	public static Finder<Long, UserAccount> find = 
			new Finder<Long, UserAccount> (Long.class, UserAccount.class);

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + ", password=" + password
				+ ", accountType=" + accountType + ", joinDate=" + joinDate
				+ ", apartment=" + apartment + "]";
	}
	
}
