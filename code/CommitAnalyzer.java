/**
 * 
 */
package software_eng;
import java.util.*;
import java.io.*;
//import software_eng.CodeCommit;

/**
 * @author Moeti
 *
 */
public class CommitAnalyzer {
	/**
	 * List of fields used to create and store commit data 
	 */
	List<CodeCommit> commits = new ArrayList<CodeCommit>();
	private String commit = "";
	private String author = "";
	private String message = "";
	private String date = "";
	private String reviewUrl = "";
	private String git_svn_id = "";
	private String bug = "";
	private String test = "";
	
	/**
	 * Default constructor
	 */
	public CommitAnalyzer(){
		
	}

	/**
	 * @param args optional command line parameter
	 */
	public static void main(String[] args) throws EOFException {
		String line = "";
		String commit = "";
		String author = "";
		String message = "";
		String date = "";
		String reviewUrl = "";
		String git_svn_id = "";
		String bug = "";
		String test = "";
		String value = "";
		Boolean readNext = true;
		Boolean firstCommit = true;
		
		CommitAnalyzer myCommitAnalyzer = new CommitAnalyzer();
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/commits-chromium.txt"));
			line = file.readLine();
			while(line!=null){
				if (!line.trim().isEmpty()){
					System.out.println("Line is: "+line);
					if ( line.trim().split(" ", 2)[0].length()>2){
						value = line.trim().split(" ", 2)[0].substring(0, 3);
					}
					else{
						value = "mes";
					}
				}
				else {
					value = "";
				}
					
				System.out.println("Value is: "+value);
				if (line.isEmpty()){
					System.out.println("Empty line");
				}
				else if (value.equalsIgnoreCase("COM")){System.out.println("halla: "+commit);
				if (!firstCommit){
					//commit = commit + "\t";
					System.out.println("Creating commit object");
					myCommitAnalyzer.setFields(commit, author, 
							message, date, reviewUrl, git_svn_id, 
							bug, test);
					//Create a commit object
					myCommitAnalyzer.loadData();
					//reset field data
					myCommitAnalyzer.clearFields();
					//clear local variables
					commit = line.trim().substring(8).trim();
					line = "";
					author = "";
					message = "";
					date = "";
					reviewUrl = "";
					git_svn_id = "";
					bug = "";
					test = "";
					readNext = false;
					
					
				}
				else{
					commit = line.trim().substring(8).trim();
					firstCommit = false;
				}
				
				
			}
			else if (value.equalsIgnoreCase("AUT")){
				if (line!=null){
					
						author = line.trim().substring(8).trim();
				}
			}
			else if (value.equalsIgnoreCase("DAT")){
				if (line!=null){
						date = line.trim().substring(5).trim();
				}
			}
			else if (value.isEmpty()){
				
			}
			else if (value.equalsIgnoreCase("bug")){
				System.out.println("Bug first word: "+line.trim().split(" ", 2)[0]);
			
				if (line!=null){ 
					message = message.trim() + "\t";
					bug = line.trim().substring(4).trim();		
				}
			}
			else if (value.equalsIgnoreCase("tes")){
				if (line!=null){ 
					test = line.trim().substring(5).trim();			
				}
			}
			else if (value.equalsIgnoreCase("Rev")){
				
				if (line!=null){ 
					System.out.println("Hi");
						reviewUrl = line.trim().substring(11).trim();
				}
			}
			
			else if (value.equalsIgnoreCase("git")){
				
				if (line!=null){ 
					git_svn_id = line.trim().substring(11).trim();
					
				}
			}
			else {
				
						message = message.trim() + " " + line.trim();
					
			
			}
				if (readNext){
				line = file.readLine();
				}
				else{
					readNext=true;
				}
			}
			
			//add the final commit to the the array list
			myCommitAnalyzer.setFields(commit, author, 
					message, date, reviewUrl, git_svn_id, 
					bug, test);
			//Create a commit object
			myCommitAnalyzer.loadData();
			//reset field data
			myCommitAnalyzer.clearFields();
			
			file.close();
			myCommitAnalyzer.getCommits();
			myCommitAnalyzer.createCsv();
		}
		catch(IOException ioe){
			System.out.println("Error!!! Could not read input file.");
			ioe.printStackTrace();
		}
	}
	/**
	 * Copy local variable data to class fields
	 * @param commit Git commit ID
	 * @param author The person who submitted the changes
	 * @param message Git commit message
	 * @param date Date changes were commited
	 * @param reviewUrl URL to to the commit review
	 * @param Git_svn_id Git SVN ID
	 * @param bug Related bug information
	 * @param test Related test information
	 */
	public void setFields(String commit, String author, 
			String message, String date, String reviewUrl, String git_svn_id, 
			String bug, String test){
		this.commit = commit;
		this.author = author;
		this.message = message;
		this.date = date;
		this.reviewUrl = reviewUrl;
		this.git_svn_id = git_svn_id;
		this.bug = bug;
		this.test = test;
		
	}
	/**
	 * Add commit data to the array list
	 */
	public void loadData(){
		CodeCommit myCodeCommit = new CodeCommit(this.commit, this.author, 
				this.message, this.date, this.reviewUrl, this.git_svn_id, 
				this.bug, this.test);
		commits.add(myCodeCommit);
	}
	/**
	 * Clear field information after adding commit data to the array list
	 */
	void clearFields(){
		this.commit = "";
		this.author = "";
		this.message = "";
		this.date = "";
		this.reviewUrl = "";
		this.git_svn_id = "";
		this.bug = "";
		this.test = "";
	}
	/**
	 * Inspect list of commit messages
	 */
	void getCommits(){
		for (int i=0; i<this.commits.size(); i++){
			System.out.println(this.commits.get(i));
		}
	}
	/**
	 * Save all commit data in a file delimited by ";'" string
	 */
	void createCsv(){
		
		try{
			File file = new File("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/out.csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			
		BufferedWriter out = new BufferedWriter(new FileWriter
				(file.getAbsoluteFile()));
		out.write("Commit\t Author\t Date\t Message\t Bug\t Test\t Review URL\t Git SVN ID");
		out.newLine();
		for (int i=0; i<this.commits.size(); i++){
			out.write(this.commits.get(i).getCommit().trim()+"\t"+this.commits.get(i).getAuthor().trim()
					+"\t"+this.commits.get(i).getDate().trim()+"\t"+this.commits.get(i).getMessage().trim()
					+"\t"+this.commits.get(i).getBug().trim()+"\t"+this.commits.get(i).getTest().trim()
					+"\t"+this.commits.get(i).getReviewUrl().trim()+"\t"+this.commits.get(i).getGitSvnId().trim());
			out.newLine();
			//out.write("Hi");
			}
		out.close();
		}
		catch (IOException ioe){
			System.out.println("Error!!! Writing to file failed. ");
			ioe.printStackTrace();
		}
		}

}
