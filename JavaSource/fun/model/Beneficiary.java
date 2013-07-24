package fun.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="FUNBENEFICIARY")
public class Beneficiary {
	
	@Id
	@Column(name="ID")
	private Long ID;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="RELATIONSHIP")
	private String relationship;
	
	@Column(name="BENEFIT_PERCENT")
	private Double benefitPercent;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", referencedColumnName="ID")
	private Member member;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public Double getBenefitPercent() {
		return benefitPercent;
	}
	public void setBenefitPercent(Double benefitPercent) {
		this.benefitPercent = benefitPercent;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
}
