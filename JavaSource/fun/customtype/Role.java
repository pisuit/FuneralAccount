package fun.customtype;

public enum Role {

	RETIRE("RETIRE","�Ѵ��â����ż�����³����"),
	CALCULATE("CALCULATE","�ӹǳ�Թʧ������"),
	WARN_LETTER("WARN_LETTER","���ҧ����������͹"),
	BALANCE_REPORT("BALANCE_REPORT","���ҧ��§ҹ��ػ�ʹ�������"),
	MONTHLY_REPORT("MONTHLY_REPORT","���ҧ��§ҹ��ػ��Ш���͹"),
	ADMIN("ADMIN","�������к�");
	
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
