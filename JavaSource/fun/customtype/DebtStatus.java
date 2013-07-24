package fun.customtype;

public enum DebtStatus {
	PAID("PAID","Paid"),
	IN_DEBT("IN_DEBT","In Debt"),
	WARNED("WARNED","Warned"),
	BAD_DEBT("BAD_DEBT","Bad Debt");
	
	private String id;
	private String value;
	
	DebtStatus(String aID, String aValue){
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
	
	public static DebtStatus find(String aID){
		for (DebtStatus status : DebtStatus.values()) {
			if (status.id.equals(aID)){
				return status;
			}
		}
		return null;
	}
}
