/**
 * 
 */
package software_eng;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
//import java.util.Map.Entry;


/**
 * @author Moeti
 *
 */
public class MessageTermFrequency{
	MessageTermFrequencyData index = new MessageTermFrequencyData(new ArrayList<TermFrequency>());
	String stopList[] = {"the", "a", "in", "and", "to"};

	public MessageTermFrequency(CodeCommit commit){
		System.out.println("********************* "+commit.getCommit()+" ***********************");
		this.populateTermList(processMessage(commit.getMessage().split("\\s+")));
		
	}
	
	public MessageTermFrequency(){
		
		
	}
	
	public Map<String, Integer> processMessage(String[] message){
		Map<String, Integer> wordsSeen = new HashMap<String, Integer>();
		for (String word: message){
			if (wordsSeen.get(word.toLowerCase())==null){
				wordsSeen.put(word.toLowerCase(), 1);
			}
			else{
				wordsSeen.replace(word.toLowerCase(), wordsSeen.get(word.toLowerCase()).intValue()+1);
			}
		}

		return wordsSeen;
	}
	
	
	
	public void populateTermList(Map<String, Integer> wordsSeen){
		for (HashMap.Entry<String, Integer> entryPair: wordsSeen.entrySet()){
			index.messageTerms.add(new TermFrequency(entryPair.getKey().toString(), entryPair.getValue().intValue()));
			
			Collections.sort(index.messageTerms, new Comparator<TermFrequency>() {
			    @Override
			    public int compare(TermFrequency a1, TermFrequency a2) {
			        return a2.getFrequency() - a1.getFrequency();
			    }
			});
			
		}
		
		
		
	}
	
	public String toString(){
		boolean notInStopList = true;
		String terms = "";
		for (TermFrequency tm: index.messageTerms){
			for (String current: this.stopList){
				if (current.toLowerCase().equals(tm.getTerm().toLowerCase())){
					notInStopList = false;
					break;
				}
			}
			if (notInStopList){
				terms += tm.getTerm()+" "+tm.getFrequency()+"; \n";
			}
			notInStopList = true;
		}
		return terms;
	}
	
	public String topK_TermFrequencies(int k){
		boolean notInStopList = true;
		String terms = "";
		int size = index.messageTerms.size();
		int length = (size-1 < k) ? size : k;
		String term = "";
		for (int i =0; i<length; i++){
			term = index.messageTerms.get(i).getTerm();
			for (String current: this.stopList){
				if (current.toLowerCase().equals(term.toLowerCase())){
					notInStopList = false;
					if (length + 1 < size){
						length++;
					}
					break;
				}
			}
			if (notInStopList){
				terms += term+" "+index.messageTerms.get(i).getFrequency()+"; ";
			}
			notInStopList = true;
		}
		return terms;
	}
	
	
	public static void main(String[] args){
		MessageTermFrequency mtf = new MessageTermFrequency();
		/*Scanner input = null;
		
		try {
			input = new Scanner(new File("C:/Users/Moeti/workspace/CommitAnalyzer/src/software_eng/out.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error!!! Could not open input file");
			e.printStackTrace();
		}*/
		mtf.populateTermList(mtf.processMessage("Initialize COM and configure security settings in the daemon This CL initializes a singlethreaded apartment on the main thread in the daemon and configures COM security in the following way  The daemon authenticates that all data received is from the expected client  The daemon can impersonate clients to check their identity but cannot act on their behalf  The caller's identity on every call (Dynamic cloaking)  Activations where the activated COM server would run under the daemon's identity are prohibited".split("\\s+")));
		/*
		while (input.hasNextLine()){
			System.out.println(input.nextLine());
		}*/
		
		System.out.println(mtf.topK_TermFrequencies(10));
		System.out.println(mtf);
	}

	
}
