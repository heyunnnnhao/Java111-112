package lse;

import java.io.*;
import java.util.*;

public class LittleSearchEngine {
	
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	HashSet<String> noiseWords;
	
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}	
	
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		
		return null;
	}
	
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
	}
		
	public String getKeyword(String word) {
		
		return null;
	}
	
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
		return null;
	}
	
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	public ArrayList<String> top5search(String kw1, String kw2) {
	
		return null;
	
	}
}
