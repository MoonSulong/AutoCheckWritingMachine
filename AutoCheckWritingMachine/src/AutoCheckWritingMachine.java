import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AutoCheckWritingMachine {

	private final String[] ZEROTONINETEEN = { "", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen",
			"nineteen" };
	private final String[] TENS = { "", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety" };
	private final String[] THOUSANDS = { "", "thousand", "million", "billion", "trillion" };

	/**
	 * Convert the input into a phonetic text
	 * 
	 * For example: $42,093.23 -> Forty-two thousand ninety-three and 23/100
	 * dollars. $98,432,905,593.12 -> Ninety-eight billion four hundred thirty-two
	 * million nine hundred five thousand five hundred ninety-three and 12/100
	 * dollars.
	 * 
	 * @param dollar
	 * @return the corresponding phonetic text
	 * @throws IllegalArguemtnException if the input is invalid
	 */
	public String cvtDollar(String dollar) {
		// Check input format
		if (dollar == null || dollar.length() <= 1 || dollar.charAt(0) != '$') {
			throw new IllegalArgumentException("The formate of input dollar ammount is invalid.");
		}
		// Clean input string
		StringBuilder intNum = new StringBuilder();
		StringBuilder floatNum = new StringBuilder();
		int dotCnt = 0;
		for (int i = 1; i < dollar.length(); i++) {
			char c = dollar.charAt(i);
			// Check ',' '.' and digit number
			if (dotCnt > 1 || !(Character.isDigit(c) || c == '.' || c == ',')) {
				throw new IllegalArgumentException("The formate of input dollar ammount is invalid.");
			}
			if (c == '.') {
				dotCnt++;
			}
			if (Character.isDigit(c)) {
				if (dotCnt == 0) {
					intNum.append(c);
				} else {
					floatNum.append(c);
				}
			}
		}

		// Format num before dot
		String intStr = formatIntNum(intNum.toString());
		// Format num after dot
		if (dotCnt == 1) {
			String floatStr = formatFloatNum(floatNum.toString());
			return intStr + floatStr + " dollars";
		}
		return intStr + " dollars";
	}

	/**
	 * Format the number before dot which is the integral part
	 * 
	 * @param intNum String type of intNum
	 * @return the corresponding phonetic text
	 */
	private String formatIntNum(String intNum) {
		long num = Long.valueOf(intNum);
		if (num == 0) {
			return "Zero";
		}

		int idxOfThousands = 0;
		String res = "";
		while (num > 0) {
			if (num % 1000 != 0) {
				res = formatUnit(num % 1000) + THOUSANDS[idxOfThousands] + " " + res;
			}
			num = num / 1000;
			idxOfThousands++;
		}

		// Capitalize the first letter and trim the trailing spaces
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < res.length(); i++) {
			char c = res.charAt(i);
			if (i == 0) {
				s.append(Character.toUpperCase(c));
			} else {
				s.append(c);
			}
		}
		return s.toString().trim();
	}

	/**
	 * A recursive function that process the integral number in a unit of 3 digits
	 * 
	 * @param num Long type of intNum
	 * @return the corresponding phonetic text
	 */
	private String formatUnit(long num) {
		if (num == 0) {
			return "";
		}
		if (num < 20) {
			return ZEROTONINETEEN[(int) num] + " ";
		}
		if (num < 100) {
			if (num % 10 == 0) {
				return TENS[(int) num / 10];
			}
			return TENS[(int) num / 10] + "-" + formatUnit(num % 10);
		}
		return ZEROTONINETEEN[(int) num / 100] + " hundred " + formatUnit(num % 100);
	}

	/**
	 * Format the number after dot
	 * 
	 * @param String type of floatNum
	 * @return the corresponding phonetic text
	 */
	private String formatFloatNum(String floatNum) {
		return " and " + floatNum + "/100";
	}

	/**
	 * Read input from a file in system and convert the contents
	 * 
	 * @param file the name of the file
	 */
	public void scanFile(File file) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String s = scanner.nextLine();
				System.out.println(s + " -> " + cvtDollar(s));
			}
		} catch (FileNotFoundException e) {
			System.out.print("File doesn't exist.");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	/**
	 * A main method that create an autoCheckWritingMachine object Run two tests,
	 * one with legal input dollar amounts The other with illegal input and
	 * exceptions are caught
	 * 
	 * @param args default
	 */
	public static void main(String[] args) {
		AutoCheckWritingMachine autoMachine = new AutoCheckWritingMachine();
		// test the output with legal input
		System.out.println("General Test: ");
		String[] testNums = { "$0.32", "$19", "$42,093.23", "$98,432,905,593.12", "$1,098,432,905,593.12" };

		for (String s : testNums) {
			System.out.println(s + " -> " + autoMachine.cvtDollar(s));
		}

		// test the exception with invalid input
		System.out.println("\nIllegal Test: ");
		String[] illegalNums = { "", "123", "$1.2.3", "$1,&^" };
		for (String s : illegalNums) {
			try {
				autoMachine.cvtDollar(s);

			} catch (IllegalArgumentException e) {
				System.out.println(s + " -> " + e.getMessage());
			}
		}

		// test scan file
		System.out.println("\nInput from a file: ");
		File file = new File("example.txt");
		autoMachine.scanFile(file);
		
		// check the absolute path in different OS 
		// String absPath = file.getAbsolutePath();
		// System.out.println(absPath);
	}
}
