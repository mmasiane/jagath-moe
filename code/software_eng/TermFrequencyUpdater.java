/**
 * 
 */
package software_eng;

import java.io.*;
import java.util.Scanner;

/**
 * @author Moeti
 *
 */
public class TermFrequencyUpdater {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageTermFrequency mtf = new MessageTermFrequency();
		Scanner input = null;
		boolean header = true;
		
		try {
			input = new Scanner(new File("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/Empirical Study Data.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error!!! Could not open input file");
			e.printStackTrace();
		}
		
		if (input.hasNextLine()){
			input.nextLine();
		}
		
		try {
			PrintWriter outFile = new PrintWriter("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/Empirical Study Data with Top K Terms.csv");
			while (input.hasNextLine()){
				String line = input.nextLine();
				if (!line.trim().equals("")){
						//System.out.println(line);
				String[] fields = line.split("\t");
				mtf.populateTermList(mtf.processMessage(fields[6].toLowerCase().split("\\[a-z]+^\\s")[0].split("[\\W]+")));
				//mtf.populateTermList(mtf.processMessage("Chrome security team IPC message owners".split("\\.*\\s+")));
				if (header){
					outFile.println("Project\tSecurity Bug Level\tBug Type\tCommit\tAuthor\tDate\tMessage\tTerm Frequency\tBug\tTest\tReview URL\tGIT SVN ID\tCode");
					header = false;
				}
				outFile.println(fields[0]+"\t"+fields[1]+"\t"+fields[2]+"\t"+fields[3]+"\t"+fields[4]+"\t"+fields[5]+"\t"+fields[6]+"\t"+mtf.topK_TermFrequencies(15)+"\t"+fields[7]+"\t"+fields[8]+"\t"+fields[9]+"\t"+fields[10]);
				//System.out.println(fields[6].split("\\w+^\\s")[0]);
				}
			}
			outFile.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Error!!! Could not create output file. Make sure it's not currently in use");
			e1.printStackTrace();
		}
		

	}

}
