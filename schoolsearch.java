import java.util.*;
import java.io.*;

public class schoolsearch {

	private static ArrayList<ArrayList<String>> studentData = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> teacherData = new ArrayList<ArrayList<String>>();
   
   //Traceability: implements requirement R13
	public static void studentFileParse() throws FileNotFoundException {
		Scanner in = new Scanner(new File("list.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] elements = line.split(",");
			if (elements.length != 6)
				continue;
			ArrayList<String> row = new ArrayList<String>();
			for (int i = 0; i < elements.length; i++) {
				if (i == 1)
					row.add((elements[i]).trim());
				else
					row.add(elements[i]);
			}
			studentData.add(row);
		}
		in.close();
	}

   //Traceability: implements requirement R13
	public static void teacherFileParse() throws FileNotFoundException {
		Scanner in = new Scanner(new File("teachers.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] elements = line.split(", ");
			if (elements.length != 3)
				continue;
			ArrayList<String> row = new ArrayList<String>();
			for (int i = 0; i < elements.length; i++)
				row.add(elements[i]);
			teacherData.add(row);
		}
		in.close();
	}

	//Traceabililty: implements requirement R11
	private static void infoPrint() {
		int grade[] = new int[7];

		for (ArrayList<String> current : studentData) {
			grade[Integer.parseInt(current.get(2))]++;
		}

		for (int i = 0; i < 7; i++) {
			System.out.println(i + ": " + grade[i]);
		}
	}

	//Traceability: implements requirement NR4
	private static void enrollPrint() {
		ArrayList<Integer> rooms = new ArrayList<Integer>();

		for (ArrayList<String> cur : teacherData) {
			Integer curInt = Integer.parseInt(cur.get(2));
			if (!rooms.contains(curInt))
				rooms.add(curInt);
		}

		Collections.sort(rooms);
		Integer[] roomData = new Integer[rooms.size()];
		for (int i = 0; i < roomData.length; i++)
			roomData[i] = 0;

		for (ArrayList<String> cur : studentData) {
			int index = rooms.indexOf(Integer.parseInt(cur.get(3)));
			roomData[index]++;
		}

		for (Integer cur : rooms) {
			System.out.println(cur + ": " + roomData[rooms.indexOf(cur)]);
		}
	}

	//Traceabililty: implements requirement NR2
	private static ArrayList<ArrayList<String>> getTeachers(String room) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> current : teacherData) {
			if (current.get(2).equals(room)) {
				result.add(current);
			}
		}
		return result;
	}

	//Traceabililty: implements requirements R2, R3
	private static void printMenu() {
		System.out.println("Command Menu:");
		System.out.println("S[tudent]: <lastname> [B[us]]");
		System.out.println("T[eacher]: <lastname> [A[nalytics]]");
		System.out.println("C[lassroom]: <number> <S[tudent] | T[eacher]>");
		System.out.println("B[us]: <number> [A[nalytics]]");
		System.out.println("G[rade]: <number> [[H[igh]] | [L[ow]] | [T[eacher]] | [A[nalytics]]]");
		System.out.println("A[verage]: <number>");
		System.out.println("E[nrollments]");
		System.out.println("I[nfo]");
		System.out.println("Q[uit]\n");
	}
	//Traceabililty: implements requirements R4, R5
	private static void studentCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : studentData) {
			if (splitString[0].equals(cur.get(0))) {
				results.add(cur);
			}
		}
		if (splitString.length == 2 && (splitString[1].equals("B") || splitString[1].equals("Bus"))) {
			for (ArrayList<String> cur : results) {
				System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(4));
			}
		}
		else if (splitString.length == 1) {
			for (ArrayList<String> cur : results) {
				ArrayList<String> teacher = (getTeachers(cur.get(3))).get(0);
				System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(2) + "," + cur.get(3) + "," + teacher.get(0) + "," + teacher.get(1));
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

	//Traceabililty: implements requirement R9, NR5
	private static void gradeCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : studentData) {
			if (splitString[0].equals(cur.get(2))) {
				results.add(cur);
			}
		}
		if (results.isEmpty())
			return;

		if (splitString.length == 2 && (splitString[1].equals("H") || splitString[1].equals("High"))) {
			//High
			ArrayList<String> cur = findGpa(results, 0);
			ArrayList<String> teacher = getTeachers(cur.get(3)).get(0);

			System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(4) + "," + cur.get(5) + "," + teacher.get(0) + "," + teacher.get(1));
		}
		else if (splitString.length == 2 && (splitString[1].equals("L") || splitString[1].equals("Low"))) {
			//Low			
			ArrayList<String> cur = findGpa(results, 1);
			ArrayList<String> teacher = getTeachers(cur.get(3)).get(0);

			System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(4) + "," + cur.get(5) + "," + teacher.get(0) + "," + teacher.get(1));
		}
		else if (splitString.length == 2 && (splitString[1].equals("T") || splitString[1].equals("Teacher"))) {
			//Teacher
			ArrayList<String> rooms = new ArrayList<String>();
			for (ArrayList<String> cur : results) {
				if (!rooms.contains(cur.get(3))) {
					rooms.add(cur.get(3));
				}
			}
			for (String cur : rooms) {
				results = getTeachers(cur);
				for (ArrayList<String> curTeacher : results)
					System.out.println(curTeacher.get(0) + "," + curTeacher.get(1));
			}
		}
		else if (splitString.length == 2 && (splitString[1].equals("A") || splitString[1].equals("Analytics"))) {
			ArrayList<String> gpa = new ArrayList<String>();
			for (ArrayList<String> cur : results) {
				gpa.add(cur.get(5));
			}
			for (int i = 0; i < gpa.size()-1; i++) {
				System.out.print(gpa.get(i) + ", ");
			}
			if (gpa.size() > 0)
				System.out.println(gpa.get(gpa.size()-1));
		}
		else {
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

		for (ArrayList<String> cur : studentData) {
			if (cmdParts[1].equals(cur.get(2))) {
				results.add(cur);
			}
		}

		float avg = findAvg(results);
		if (!Float.isNaN(avg))
			System.out.println(cmdParts[1] + ": " + avg);
	}

	//Traceabililty: implements requirement R6, NR5
	private static void teacherCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		ArrayList<String> teacher = new ArrayList<String>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : teacherData) {
			if (splitString[0].equals(cur.get(0))) {
				teacher = cur;
				break;
			}
		}
		if (teacher.size() == 0)
			return;
		for (ArrayList<String> cur : studentData) {
			if (teacher.get(2).equals(cur.get(3))) {
				results.add(cur);
			}
		}
		if (splitString.length == 2 && (splitString[1].equals("A") || splitString[1].equals("Analytics"))) {
			ArrayList<String> gpa = new ArrayList<String>();
			for (ArrayList<String> cur : results) {
				gpa.add(cur.get(5));
			}
			for (int i = 0; i < gpa.size()-1; i++) {
				System.out.print(gpa.get(i) + ", ");
			}
			if (gpa.size() > 0)
				System.out.println(gpa.get(gpa.size()-1));
		}
		else if (splitString.length == 2) {
			System.out.println("Command Syntax Error");
		}
		else {
			for (ArrayList<String> cur : results) {
				System.out.println(cur.get(0) + "," + cur.get(1));
			}
		}
	}

	//Traceabililty: implements requirement R8, NR5
	private static void busCmd(String[] cmdParts) {		
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		for (ArrayList<String> cur : studentData) {
			if (splitString[0].equals(cur.get(4))) {
				results.add(cur);
			}
		}

		if (splitString.length == 2 && (splitString[1].equals("A") || splitString[1].equals("Analytics"))) {
			ArrayList<String> gpa = new ArrayList<String>();
			for (ArrayList<String> cur : results) {
				gpa.add(cur.get(5));
			}
			for (int i = 0; i < gpa.size()-1; i++) {
				System.out.print(gpa.get(i) + ", ");
			}
			if (gpa.size() > 0)
				System.out.println(gpa.get(gpa.size()-1));
		}
		else if (splitString.length == 2) {
			System.out.println("Command Syntax Error");
		}
		else {
			for (ArrayList<String> cur : results) {
				System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(2) + "," + cur.get(3));
			}
		}
	}

	//Traceability: implements requirements NR1, NR2
	private static void classroomCmd(String[] cmdParts) {
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		String[] splitString = cmdParts[1].split("\\s+");

		if (splitString.length == 2 && (splitString[1].equals("S") || splitString[1].equals("Student"))) {
			//Student

			for (ArrayList<String> cur : studentData) {
				if (splitString[0].equals(cur.get(3))) {
					results.add(cur);
				}
			}
			if (results.isEmpty())
				return;

			for (ArrayList<String> cur : results) {
				ArrayList<String> teacher = getTeachers(splitString[0]).get(0);
				System.out.println(cur.get(0) + "," + cur.get(1) + "," + cur.get(4) + "," + cur.get(5) + "," + teacher.get(0) + "," + teacher.get(1));
			}

		}
		else if (splitString.length == 2 && (splitString[1].equals("T") || splitString[1].equals("Teacher"))) {
			//Teacher	

			for (ArrayList<String> cur : teacherData) {
				if (splitString[0].equals(cur.get(2))) {
					results.add(cur);
				}
			}
			if (results.isEmpty())
				return;

			for (ArrayList<String> cur : results) {
				System.out.println(cur.get(0) + "," + cur.get(1));
			}
		}
		else {
			System.out.println("Please specify either S[tudent] or T[eacher]");
		}
	}

	//Traceabililty: implements requirements R1, R2, R12, E1
	public static void main(String[] args) {
		try {
			studentFileParse();
			teacherFileParse();
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
				//Must be Info, Enrollments, or an error
				if (cmdParts[0].equals("I") || cmdParts[0].equals("Info")) {
					infoPrint();
				}
				else if (cmdParts[0].equals("E") || cmdParts[0].equals("Enrollments")) {
					enrollPrint();
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
				else if (cmdParts[0].equals("C") || cmdParts[0].equals("Classroom")) {
					//Classroom
					classroomCmd(cmdParts);
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
