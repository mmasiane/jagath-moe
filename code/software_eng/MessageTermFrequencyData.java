package software_eng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageTermFrequencyData {
	public List<TermFrequency> messageTerms = new ArrayList<TermFrequency>();

	public MessageTermFrequencyData(List<TermFrequency> messageTerms) {
		this.messageTerms = messageTerms;
		this.sort();
	}
	
	public void sort(){
		if (this.messageTerms!= null && this.messageTerms.size()>0){
			System.out.println("List is not being sorted");
			Collections.sort(this.messageTerms);
		}
	}
	
}