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
			return 'ñ';
		case '2':
			return 'ò';
		case '3':
			return 'ó';
		case '4':
			return 'ô';
		case '5':
			return 'õ';
		case '6':
			return 'ö';
		case '7':
			return '÷';
		case '8':
			return 'ø';
		case '9':
			return 'ù';
		case '0':
			return 'ð';
		case '.':
			return '.';
		case ',':
			return ',';
		default:
			return input;
		}
	}
}
