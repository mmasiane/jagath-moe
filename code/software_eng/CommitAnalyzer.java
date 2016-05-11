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
	private String diff = "";
	ArrayList<MessageTermFrequency> collectionIndex = new ArrayList<MessageTermFrequency>();
	
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
		String diff = "";
		Boolean readNext = true;
		Boolean firstCommit = true;
		Boolean readMessage = false;
		
		
		CommitAnalyzer myCommitAnalyzer = new CommitAnalyzer();
		
		try{
			BufferedReader file = new BufferedReader(new FileReader("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/commits-chromium-withdiff.txt"));
			line = file.readLine();
			while(line!=null){
				if (!line.trim().isEmpty()){
					System.out.println("Line is: "+line);
					if ( line.trim().split(" ", 2)[0].length()>2){
						value = line.trim().split(" ", 2)[0];
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
				else if (value.toLowerCase().startsWith("commit")){System.out.println("halla: "+commit);
				readMessage = true;
				if (!firstCommit){
					//commit = commit + "\t";
					System.out.println("Creating commit object");
					myCommitAnalyzer.setFields(commit, author, 
							message, date, reviewUrl, git_svn_id, 
							bug, test, diff);
					
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
			else if (value.toLowerCase().startsWith("author")){
				if (line!=null){
					
						author = line.trim().substring(8).trim();
				}
			}
			else if (value.toLowerCase().startsWith("date")){
				if (line!=null){
						date = line.trim().substring(5).trim();
				}
			}
			else if (value.isEmpty()){
				
			}
			else if (value.toLowerCase().startsWith("bug")){
				System.out.println("Bug first word: "+line.trim().split(" ", 2)[0]);
			
				if (line!=null){ 
					message = message.trim() + "\t";
					bug = line.trim().substring(4).trim();		
				}
			}
				/*This if statement is case sensitive because a lot of commit messages have the text TEXT
				which causes false positives*/
			else if (value.startsWith("Test")){
				if (line!=null){ 
					test = line.trim().substring(5).trim();			
				}
			}
			else if (value.toLowerCase().startsWith("review")){
				
				if (line!=null){ 
					System.out.println("Hi");
						reviewUrl = line.trim().substring(11).trim();
				}
			}
			
			else if (value.toLowerCase().startsWith("git")){
				
				if (line!=null){ 
					git_svn_id = line.trim().substring(11).trim();
					
				}
			}
			else if (value.toLowerCase().startsWith("diff")){
				readMessage = false;
				if (line!=null){ 
					diff = line.trim();
					
				}
			}
			else {
				if (readMessage){
						message = message.trim() + " " + line.trim();
				}
				else{
					diff = diff.trim() + " <newline>" + line;
				//}
				}
			
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
					bug, test, diff);
			//Create a commit object
			myCommitAnalyzer.loadData();
			//reset field data
			myCommitAnalyzer.clearFields();
			
			file.close();
			myCommitAnalyzer.getCommits();
			myCommitAnalyzer.createCsv();
			myCommitAnalyzer.createCollectionIndex();
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
			String bug, String test, String diff){
		this.commit = commit;
		this.author = author;
		this.message = message;
		this.date = date;
		this.reviewUrl = reviewUrl;
		this.git_svn_id = git_svn_id;
		this.bug = bug;
		this.test = test;
		this.diff = diff;
		
	}
	/**
	 * Add commit data to the array list
	 */
	public void loadData(){
		CodeCommit myCodeCommit = new CodeCommit(this.commit, this.author, 
				this.message, this.date, this.reviewUrl, this.git_svn_id, 
				this.bug, this.test, this.diff);
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
	 * Create an index for the whole collection of commit messages
	 */
	public void createCollectionIndex(){
		for (int i=0; i<this.commits.size(); i++){
			this.collectionIndex.add(new MessageTermFrequency(this.commits.get(i)));
			System.out.println("\n"+"==========================");
			System.out.println("Commit# "+i);
			System.out.println(collectionIndex);
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
					+"\t"+this.commits.get(i).getReviewUrl().trim()+"\t"+this.commits.get(i).getGitSvnId().trim()
					+"\t"+this.commits.get(i).getDiff().trim());
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
