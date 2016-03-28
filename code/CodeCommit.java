/**
 * 
 */
package software_eng;

/**
 * @author Moeti
 *
 */
public class CodeCommit {
	/**
	 * List of fields that are commit attributes
	 */
	private String commit = "";
	private String author = "";
	private String message = "";
	private String date = "";
	private String reviewUrl = "";
	private String git_svn_id = "";
	private String bug = "";
	private String test = "";

	/**
	 * Constructor used to create a commit
	 * 
	 * @param commit Git commit ID
	 * @param author The person who submitted the changes
	 * @param message Git commit message
	 * @param date Date changes were commited
	 * @param reviewUrl URL to to the commit review
	 * @param Git_svn_id Git SVN ID
	 * @param bug Related bug information
	 * @param test Related test information
	 */
	public CodeCommit(String commit, String author, 
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
	 * 
	 * @return Returns the commit ID string
	 */
	public String getCommit(){
		return commit;
	}
	/**
	 * 
	 * @return Returns the commit author information string
	 */
	public String getAuthor(){
		return author;
	}
	/**
	 * 
	 * @return Returns the commit message string
	 */
	public String getMessage(){
		return message;
	}
	/**
	 * 
	 * @return Returns the commit date string
	 */
	public String getDate(){
		return date;
	}
	/**
	 * 
	 * @return Returns the commit URL for the related change review string
	 */
	public String getReviewUrl(){
		return reviewUrl;
	}
	/**
	 * 
	 * @return Returns the commit GIT SVN ID string
	 */
	public String getGitSvnId(){
		return git_svn_id;
	}
	/**
	 * 
	 * @return Returns the commit bug string
	 */
	public String getBug(){
		return bug;
	}
	/**
	 * 
	 * @return Returns the commit test string
	 */
	public String getTest(){
		return test;
	}
	
	/**
	 * @override Overrides the toTring method
	 */
	public String toString(){
		return "\nAuthor: "+author+"\t\nCommit Date: "+
				date+"\t\nCommit Message: "+message+"\t\nReview URL: "+
				reviewUrl+"\t\nGIT SVN ID: "+git_svn_id+"\t\n";
	}
	
}
