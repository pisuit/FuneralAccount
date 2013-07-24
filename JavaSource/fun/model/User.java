package fun.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import fun.customtype.DataStatus;

@Entity
@Table(name="FUNUSER")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class User {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="IDGENERATOR")
	private Long id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="SURENAME")
	private String surename;
	
	@Column(name="DATA_STATUS")
	@Enumerated(EnumType.STRING)
	private DataStatus status = DataStatus.NORMAL;
	
	@OneToMany(mappedBy="user")
	@NotFound(action=NotFoundAction.IGNORE)
	List<Authorization> authorizations;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurename() {
		return surename;
	}
	public void setSurename(String surename) {
		this.surename = surename;
	}
	public List<Authorization> getAuthorizations() {
		return authorizations;
	}
	public void setAuthorizations(List<Authorization> authorizations) {
		this.authorizations = authorizations;
	}
	public DataStatus getStatus() {
		return status;
	}
	public void setStatus(DataStatus status) {
		this.status = status;
	}
}
