package fun.utils;

public class ThaiNumberConverter {
	
	public static String arabicToThai(String input){
		String output = "";
		for(int i=0;i<input.length();i++){
			char c = input.charAt(i);
			output = output + getThaiNumber(c);
		}
		return output;
	}
	
	private static char getThaiNumber(char input){
		switch (input) {
		case '1':
			return '�';
		case '2':
			return '�';
		case '3':
			return '�';
		case '4':
			return '�';
		case '5':
			return '�';
		case '6':
			return '�';
		case '7':
			return '�';
		case '8':
			return '�';
		case '9':
			return '�';
		case '0':
			return '�';
		case '.':
			return '.';
		case ',':
			return ',';
		default:
			return input;
		}
	}
}
