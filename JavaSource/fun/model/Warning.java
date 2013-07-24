package fun.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="FUNWARNING")
@GenericGenerator(strategy="fun.utils.HibernateCurrentTimeIDGenerator", name="IDGENERATOR")
public class Warning {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="IDGENERATOR")
	private Long id;
	
	@OneToOne
	@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")
	private Member member;
	
	@Column(name="WARNINGCOUNT")
	private int warningCount;
	
	@Column(name="WARNDATE_1")
	private Date warnDate1;
	
	@Column(name="WARNDATE_2")
	private Date warnDate2;
	
	@Column(name="WARNDATE_3")
	private Date warnDate3;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public int getWarningCount() {
		return warningCount;
	}
	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}
	public Date getWarnDate1() {
		return warnDate1;
	}
	public void setWarnDate1(Date warnDate1) {
		this.warnDate1 = warnDate1;
	}
	public Date getWarnDate2() {
		return warnDate2;
	}
	public void setWarnDate2(Date warnDate2) {
		this.warnDate2 = warnDate2;
	}
	public Date getWarnDate3() {
		return warnDate3;
	}
	public void setWarnDate3(Date warnDate3) {
		this.warnDate3 = warnDate3;
	}
}
