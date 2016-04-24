/**
 * 
 */
package software_eng;

/**
 * @author Moeti
 *
 */
public class TermFrequency implements Comparable<Object>{
	private String term;
	private int frequency;
	
	public TermFrequency(String term, int frequency){
		this.term = term;
		this.frequency = frequency;
	}
	
	public String getTerm(){
		return this.term;
	}
	
	public int getFrequency(){
		return this.frequency;
	}
	
	@Override
	public int compareTo(Object tf){
		int value = ((TermFrequency) tf).getFrequency();
		return this.getFrequency()- value;
	}

	

}
