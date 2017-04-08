import java.util.*;
import java.io.*;

public class schoolsearch {

	private static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
   
   //Traceability: implements requirement R13
	public static void fileParse() throws FileNotFoundException {
		Scanner in = new Scanner(new File("students.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] elements = line.split(",");
			if (elements.length != 8)
				continue;
			ArrayList<String> row = new ArrayList<String>();
			for (int i = 0; i < elements.length; i++)
				row.add(elements[i]);
			data.add(row);
		}
		in.close();
	}

	//Traceabililty: implements requirement R11
	private static void infoPrint() {
		int grade[] = new int[7];

		for (ArrayList<String> current : data) {
			grade[Integer.parseInt(current.get(2))]++;
		}

		for (int i = 0; i < 7; i++) {
			System.out.println(i + ": " + grade[i]);
		}
	}

	//Traceabililty: implements requirements R2, R3
	private static void printMenu() {
		System.out.println("Command Menu:");
		System.out.println("S[tudent]: <lastname> [B[us]]");
		System.out.println("T[eacher]: <lastname>");
		System.out.println("B[us]: <number>");
		System.out.println("G[rade]: <number> [[H[igh]]|[L[ow]]]");
		System.out.println("A[verage]: <number>");
		System.out.println("I[nfo]");
		System.out.println("Q[uit]\n");
	}
	//Traceabililty: implements requirements R4, R5
	private static void studentCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : data) {
			if (splitString[0].equals(cur.get(0))) {
				results.add(cur);
			}
		}
		if (splitString.length == 2 && (splitString[1].equals("B") || splitString[1].equals("Bus"))) {
			for (ArrayList<String> cur : results) {
				System.out.println("Name: " + cur.get(0) + "," + cur.get(1));
				System.out.println("Bus route: " + cur.get(4));
			}
		}
		else if (splitString.length == 1) {
			for (ArrayList<String> cur : results) {
				System.out.println("Name: " + cur.get(0) + "," + cur.get(1));
				System.out.println("Grade: " + cur.get(2));
				System.out.println("Classroom: " + cur.get(3));
				System.out.println("Teacher: " + cur.get(6) + "," + cur.get(7));
			}
		}
		else
			System.out.println("Command Syntax Error");
	}

	//Traceabililty: implements requirement R9
	private static ArrayList<String> findGpa(ArrayList<ArrayList<String>> results, int option) {
		ArrayList<String> curBest = results.get(0);

		if (option == 0) {
			for (ArrayList<String> cur : results) {
				if (Float.parseFloat(cur.get(5)) > Float.parseFloat(curBest.get(5)))
					curBest = cur;
			}
		}
		else {
			for (ArrayList<String> cur : results) {
				if (Float.parseFloat(cur.get(5)) < Float.parseFloat(curBest.get(5)))
					curBest = cur;
			}
		}

		return curBest;
	}

	//Traceabililty: implements requirement R9
	private static void gradeCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : data) {
			if (splitString[0].equals(cur.get(2))) {
				results.add(cur);
			}
		}

		if (splitString.length == 2 && (splitString[1].equals("H") || splitString[1].equals("High"))) {
			//High
			ArrayList<String> newRes = findGpa(results, 0);

			System.out.println("Highest GPA in grade " + splitString[0] + ": ");
			System.out.println("Name: " + newRes.get(0) + "," + newRes.get(1));
			System.out.println("GPA: " + newRes.get(5));
			System.out.println("Teacher: " + newRes.get(6) + "," + newRes.get(7));
			System.out.println("Bus: " + newRes.get(4));
		}
		else if (splitString.length == 2 && (splitString[1].equals("L") || splitString[1].equals("Low"))) {
			//Low			
			ArrayList<String> newRes = findGpa(results, 1);

			System.out.println("Lowest GPA in grade " + splitString[0] + ": ");
			System.out.println("Name: " + newRes.get(0) + "," + newRes.get(1));
			System.out.println("GPA: " + newRes.get(5));
			System.out.println("Teacher: " + newRes.get(6) + "," + newRes.get(7));
			System.out.println("Bus: " + newRes.get(4));
		}
		else {
			System.out.println("List of students: ");
			for (ArrayList<String> cur : results) {
				System.out.println(cur.get(0) + "," + cur.get(1));
			}
		}
	}

	//Traceabililty: implements requirement R10
	private static float findAvg(ArrayList<ArrayList<String>> results) {
		float total = 0;
		int count = 0;

		for (ArrayList<String> cur : results) {
			total += Float.parseFloat(cur.get(5));
			count++;
		}

		float avg = total / count;

		return avg;
	}
	//Traceabililty: implements requirement R10
	private static void averageCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> cur : data) {
			if (cmdParts[1].equals(cur.get(2))) {
				results.add(cur);
			}
		}

		System.out.println("Average GPA for grade " + cmdParts[1] + ": " + findAvg(results));
	}

	//Traceabililty: implements requirement R6
	private static void teacherCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> cur : data) {
			if (cmdParts[1].equals(cur.get(6))) {
				results.add(cur);
			}
		}
		System.out.println("List of students: ");
		for (ArrayList<String> cur : results) {
			System.out.println(cur.get(0) + "," + cur.get(1));
		}
	}

	//Traceabililty: implements requirement R8
	private static void busCmd(String[] cmdParts) {		
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();

		for (ArrayList<String> cur : data) {
			if (cmdParts[1].equals(cur.get(4))) {
				results.add(cur);
			}
		}

		System.out.println("List of students: ");		
		
		for (ArrayList<String> cur : results) {
			System.out.println("Name: " + cur.get(0) + "," + cur.get(1));
			System.out.println("Grade: " + cur.get(2));
			System.out.println("Classroom: " + cur.get(3));
		}
	}

	//Traceabililty: implements requirements R1, R2, R12, E1
	public static void main(String[] args) {
		try {
			fileParse();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(-1);
		}

		printMenu();

		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a command:");
		String input = in.nextLine();
		while (!input.equals("Q") && !input.equals("Quit")) {
			String[] cmdParts = input.split(":");
			for (int i = 0; i < cmdParts.length; i++)
				cmdParts[i] = cmdParts[i].trim();
			if (cmdParts.length == 1) {
				//Must be either Info or an error
				if (cmdParts[0].equals("I") || cmdParts[0].equals("Info")) {
					infoPrint();
				}
				else {
					System.out.println("Command Syntax Error");
				}
			}
			else if (cmdParts.length == 2) {
				//Everything else
				if (cmdParts[0].equals("S") || cmdParts[0].equals("Student")) {
					//Student
					studentCmd(cmdParts);
				}
				else if (cmdParts[0].equals("T") || cmdParts[0].equals("Teacher")) {
					//Teacher
					teacherCmd(cmdParts);
				}
				else if (cmdParts[0].equals("B") || cmdParts[0].equals("Bus")) {
					//Bus
					busCmd(cmdParts);
				}
				else if (cmdParts[0].equals("G") || cmdParts[0].equals("Grade")) {
					//Grade
					gradeCmd(cmdParts);
				}
				else if (cmdParts[0].equals("A") || cmdParts[0].equals("Average")) {
					//Average
					averageCmd(cmdParts);
				}
				else {
					System.out.println("Invalid Command");
				}
			}
			else {
				System.out.println("Command Syntax Error");
			}
			System.out.println("\nPlease enter a command:");
			input = in.nextLine();
		}
	}
}
