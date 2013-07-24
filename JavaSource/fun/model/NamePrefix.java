package fun.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NAMEPREFIX")
public class NamePrefix {
	
	@Id
	@Column(name="ID")
	private Long ID;
	
	@Column(name="THAISHORTNAME")
	private String thaiShortName;
	
	@Column(name="THAIFULLNAME")
	private String thaiFullName;
	
	@Column(name="ENGSHORTNAME")
	private String engShortName;
	
	@Column(name="ENGFULLNAME")
	private String engFullname;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getThaiShortName() {
		return thaiShortName;
	}
	public void setThaiShortName(String thaiShortName) {
		this.thaiShortName = thaiShortName;
	}
	public String getThaiFullName() {
		return thaiFullName;
	}
	public void setThaiFullName(String thaiFullName) {
		this.thaiFullName = thaiFullName;
	}
	public String getEngShortName() {
		return engShortName;
	}
	public void setEngShortName(String engShortName) {
		this.engShortName = engShortName;
	}
	public String getEngFullname() {
		return engFullname;
	}
	public void setEngFullname(String engFullname) {
		this.engFullname = engFullname;
	}
}
