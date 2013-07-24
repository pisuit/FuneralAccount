package fun.customtype;

public enum Role {

	RETIRE("RETIRE","จัดการข้อมูลผู้เกษียณอายุ"),
	CALCULATE("CALCULATE","คำนวณเงินสงเคราะห์"),
	WARN_LETTER("WARN_LETTER","สร้างจดหมายแจ้งเตือน"),
	BALANCE_REPORT("BALANCE_REPORT","สร้างรายงานสรุปยอดคงเหลือ"),
	MONTHLY_REPORT("MONTHLY_REPORT","สร้างรายงานสรุปประจำเดือน"),
	ADMIN("ADMIN","ผู้ดูแลระบบ");
	
	private String id;
	private String value;
	
	Role(String aID, String aValue){
		id = aID;
		value = aValue;
	}
	
	public String getID(){
		return id;
	}
	
	public String getValue(){
		return value;
	}

	public String toString() {
		return value;
	}
	
	public static Role find(String aID){
		for (Role role : Role.values()) {
			if (role.id.equals(aID)){
				return role;
			}
		}
		return null;
	}
}
