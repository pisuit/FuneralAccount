package fun.customtype;

public enum MemberStatus {
	M("M","Member"),
//	N("N","Non Member"),
	F("F","Fired"),
	D("D","Dead"),
	R("R","Resign")
	;
	
	private String id;
	private String value;
	
	MemberStatus(String aID, String aValue){
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
	
	public static MemberStatus find(String aID){
		for (MemberStatus status : MemberStatus.values()) {
			if (status.id.equals(aID)){
				return status;
			}
		}
		return null;
	}
}
