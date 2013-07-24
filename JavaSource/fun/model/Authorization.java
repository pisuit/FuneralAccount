package fun.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import fun.customtype.Action;
import fun.customtype.Role;

@Entity
@Table(name="FUNAUTHORIZATION")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class Authorization {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="IDGENERATOR")
	private Long id;
	
	@Column(name="SYSTEM_ROLE")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="ACTION")
	@Enumerated(EnumType.STRING)
	private Action action = Action.EDIT;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private User user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
