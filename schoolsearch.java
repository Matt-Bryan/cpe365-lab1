import java.util.*;
import java.io.*;

public class schoolsearch {

	private static ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	
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

	private static void infoPrint() {
		int grade[] = new int[7];

		for (ArrayList<String> current : data) {
			grade[Integer.parseInt(current.get(2))]++;
		}

		for (int i = 0; i < 7; i++) {
			System.out.println(i + ": " + grade[i]);
		}
	}

	public static void main(String[] args) {
		try {
			fileParse();
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(-1);
		}

		System.out.println("Command Menu:");
		System.out.println("S[tudent]: <lastname> [B[us]]");
		System.out.println("T[eacher]: <lastname>");
		System.out.println("B[us]: <number>");
		System.out.println("G[rade]: <number> [[H[igh]]|[L[ow]]]");
		System.out.println("A[verage]: <number>");
		System.out.println("I[nfo]");
		System.out.println("Q[uit]\n");

		Scanner in = new Scanner(System.in);
		System.out.println("Please enter a command:");
		String input = in.nextLine();
		while (!input.equals("Q") && !input.equals("Quit")) {
			String[] cmdParts = input.split(": ");
			if (cmdParts.length == 1) {
				if (cmdParts[0].equals("I") || cmdParts[0].equals("Info")) {
					infoPrint();
				}
				else {
					System.out.println("Invalid Command: Syntax Error");
				}
			}
			else if (cmdParts.length == 2) {
				//Everything else
				ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
				if (cmdParts[0].equals("S") || cmdParts[0].equals("Student")) {
					//Student
					for (ArrayList<String> cur : data) {
						if (cmdParts[1].equals(cur.get(0))) {
							results.add(cur);
						}
					}
					for (ArrayList<String> cur : results) {
						System.out.println("Name: " + cur.get(0) + "," + cur.get(1));
						System.out.println("Grade: " + cur.get(2));
						System.out.println("Classroom: " + cur.get(3));
						System.out.println("Teacher: " + cur.get(6) + "," + cur.get(7));
					}
				}
				else if (cmdParts[0].equals("T") || cmdParts[0].equals("Teacher")) {
					//Teacher
				}
				else if (cmdParts[0].equals("B") || cmdParts[0].equals("Bus")) {
					//Bus
				}
				else if (cmdParts[0].equals("G") || cmdParts[0].equals("Grade")) {
					//Grade
				}
				else if (cmdParts[0].equals("A") || cmdParts[0].equals("Average")) {
					//Average
				}
				else {
					System.out.println("Invalid Command");
				}
			}
			else {
				System.out.println("Command Syntax Error");
			}
			System.out.println("Please enter a command:");
			input = in.nextLine();
		}
	}
}
