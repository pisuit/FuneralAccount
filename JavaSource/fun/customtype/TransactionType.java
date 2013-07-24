package fun.customtype;

public enum TransactionType {
	ADD("ADD","Add"),
	DEDUCT("DEDUCT","Deduct"),
	INITIAL("INITIAL","Initial")
	;
	
	private String id;
	private String value;
	
	TransactionType(String aID, String aValue){
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
	
	public static TransactionType find(String aID){
		for (TransactionType type : TransactionType.values()) {
			if (type.id.equals(aID)){
				return type;
			}
		}
		return null;
	}
}
