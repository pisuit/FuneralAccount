package fun.customtype;

public enum EmployeeStatus {
	PROBATION("PROBATION","Probation"),
	PERMANENT("PERMANENT","Permanent"),
	DECEASED("DECEASED","Deceased"),
	RESIGNATION("RESIGNATION","Resignation"),
	RETIREMENT("RETIREMENT","Retirement"),
	UNKNOWN("UNKNOWN","Unknown");
	;
	
	private String id;
	private String value;
	
	EmployeeStatus(String aID, String aValue){
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
	
	public static EmployeeStatus find(String aID){
		for (EmployeeStatus status : EmployeeStatus.values()) {
			if (status.id.equals(aID)){
				return status;
			}
		}
		return null;
	}
}
