package fun.customtype;

public enum Action {
	EDIT("EDIT","EDIT");
	
	private String id;
	private String value;
	
	Action(String aID, String aValue){
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
	
	public static Action find(String aID){
		for (Action action : Action.values()) {
			if (action.id.equals(aID)){
				return action;
			}
		}
		return null;
	}
}
