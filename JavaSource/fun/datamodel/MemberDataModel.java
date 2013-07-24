package fun.datamodel;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import fun.model.Member;

public class MemberDataModel extends ListDataModel<Member> implements SelectableDataModel<Member>{
	
	public MemberDataModel(){
		
	}
	
	public MemberDataModel(List<Member> data){
		super(data);
	}

	@Override
	public Member getRowData(String arg0) {
		// TODO Auto-generated method stub
		List<Member> memebrs = (List<Member>) getWrappedData();
		
		for(Member member : memebrs){
			if(member.getID().equals(arg0));
			return member;
		}
		return null;
	}

	@Override
	public Object getRowKey(Member arg0) {
		// TODO Auto-generated method stub
		return arg0.getID();
	}
}
