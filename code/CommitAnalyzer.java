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
		
		CommitAnalyzer myCommitAnalyzer = new CommitAnalyzer();
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/commits-hive.txt"));
			line = file.readLine();
			while(line!=null){
			if (line.trim().split(" ", 2)[0].equalsIgnoreCase("COMMIT")){
				if (line!=null){
					
						commit = commit + " " + line.trim().substring(7).trim();
						line=file.readLine();
				}
			}
			if (line.trim().split(" ", 2)[0].equalsIgnoreCase("AUTHOR:")){
				if (line!=null){
					commit = commit + ";";
						author = author + " " + line.trim().substring(8).trim();
						line=file.readLine();
				}
			}
			if (line.trim().split(" ", 2)[0].equalsIgnoreCase("DATE:")){
				if (line!=null){
					author = author + ";";
						date = date + " " + line.trim().substring(5).trim();
						line=file.readLine();
				}
			}
			if (line.trim().split("=", 2)[0].equalsIgnoreCase("bug")){
				if (line!=null){ 
					date = date + ";";
					message = message.trim() + ";";
					bug = bug + " " + line.trim().substring(4).trim();
					line=file.readLine();					
				}
			}
			if (line.trim().split("=", 2)[0].equalsIgnoreCase("test")){
				if (line!=null){ 
					test = test + " " + line.trim().substring(5).trim();
					line=file.readLine();					
				}
			}
			if (line.trim().split(" ", 2)[0].equalsIgnoreCase("Review")){
				if (line!=null){ 
						bug = bug + ";";
						test = test + ";";
						reviewUrl = reviewUrl + " " + line.trim().substring(11).trim();
						line=file.readLine();
				}
			}
			if (line.trim().split(" ", 2)[0].equalsIgnoreCase("git-svn-id:")){
				if (line!=null){ 
					reviewUrl = reviewUrl + ";";
					git_svn_id = git_svn_id + " " + line.trim().substring(11).trim();
					git_svn_id = git_svn_id + ";";
					line=file.readLine();	
					//Copy local variable data to fields
					myCommitAnalyzer.setFields(commit, author, 
							message, date, reviewUrl, git_svn_id, 
							bug, test);
					//Create a commit object
					myCommitAnalyzer.loadData();
					//reset field data
					myCommitAnalyzer.clearFields();
					//clear local variables
					line = "";
					commit = "";
					author = "";
					message = "";
					date = "";
					reviewUrl = "";
					git_svn_id = "";
					bug = "";
					test = "";
				}
			}
			else{
				message = message.trim() + " " + line.trim();
				line = file.readLine();
			}
			
			}
			
			
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
		out.write("Commit; Author; Date; Message; Bug; Test; Review URL; Git SVN ID");
		out.newLine();
		for (int i=0; i<this.commits.size(); i++){
			out.write(this.commits.get(i).getCommit().trim()+" "+this.commits.get(i).getAuthor().trim()
					+" "+this.commits.get(i).getDate().trim()+" "+this.commits.get(i).getMessage().trim()
					+" "+this.commits.get(i).getBug().trim()+" "+this.commits.get(i).getTest().trim()
					+" "+this.commits.get(i).getReviewUrl().trim()+" "+this.commits.get(i).getGitSvnId().trim());
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
