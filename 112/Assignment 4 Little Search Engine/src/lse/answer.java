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

        HashMap<String,Occurrence> kws = new HashMap<String, Occurrence>();

        Scanner sc = new Scanner(new File(docFile));
        while (sc.hasNext()) {
            String word = sc.next();
            if (!kws.containsKey(getKeyWord(word))){
                String newWord = getKeyWord(word);
                if (newWord!=null){
                    kws.put(newWord,new Occurrence(docFile, 1));
                }
            } else {
                Occurrence occ = kws.get(getKeyWord(word));
                occ.frequency++;
                String newWord = getKeyWord(word);
                if (newWord!=null){
                    kws.put(newWord,occ);
                }
            }
        }
        return kws;
    }
   
    public void mergeKeyWords(HashMap<String,Occurrence> kws) {
        Set<String> keys = kws.keySet(); // Set is of type String since keys are Strings
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (this.keywordsIndex.containsKey(key)){
                this.keywordsIndex.get(key).add(kws.get(key));
                ArrayList<Integer> intList = insertLastOccurrence(this.keywordsIndex.get(key));
            } else {
                ArrayList<Occurrence> oList = new ArrayList<Occurrence>();
                oList.add(kws.get(key));
                this.keywordsIndex.put(key, oList);
            }
        }
    }

    public String getKeyWord(String word) {

        word = word.toLowerCase();

        if (word.charAt(0) == '.' || word.charAt(0) == ',' || word.charAt(0) == '?' || word.charAt(0) == ':' ||
                word.charAt(0) == ';' || word.charAt(0) == '!' || word.charAt(0) == '\''|| word.charAt(0) == '_' ||
                word.charAt(0) == ')' || word.charAt(0) == '(' || word.charAt(0) == '"'){
            for (int i = 1; i < word.length(); i++){
                if (word.charAt(i) != '.' && word.charAt(i) != ',' && word.charAt(i) != '?' &&
                        word.charAt(i) != ':' && word.charAt(i) != ';' && word.charAt(i) != '!' &&
                        word.charAt(i) != '\''&& word.charAt(i) != '_' && word.charAt(i) != ')' &&
                        word.charAt(i) != '(' && word.charAt(i) != '"'){
                    word = word.substring(i);
                }
            }
        }

        if (word.charAt(word.length()-1) == '.' || word.charAt(word.length()-1) == ',' || word.charAt(word.length()-1) == '?' ||
                word.charAt(word.length()-1) == ':' || word.charAt(word.length()-1) == ';' || word.charAt(word.length()-1) == '!' ||
                word.charAt(word.length()-1) == '\''|| word.charAt(word.length()-1) == '_' || word.charAt(word.length()-1) == ')' ||
                word.charAt(word.length()-1) == '(' || word.charAt(word.length()-1) == '"'){
            for (int i = word.length()-2; i >= 0; i--){
                if (word.charAt(i) != '.' && word.charAt(i) != ',' && word.charAt(i) != '?' &&
                        word.charAt(i) != ':' && word.charAt(i) != ';' && word.charAt(i) != '!' &&
                        word.charAt(i) != '\''&& word.charAt(i) != '_' && word.charAt(i) != ')' &&
                        word.charAt(i) != '(' && word.charAt(i) != '"'){
                    word = word.substring(0, i+1);
                }
            }
        }

        for(int i = 0; i <= word.length()-1; i++){
            if (word.charAt(i) == '.' || word.charAt(i) == ',' || word.charAt(i) == '?' ||
                    word.charAt(i) == ':' || word.charAt(i) == ';' || word.charAt(i) == '!' ||
                    word.charAt(i) == '.' || word.charAt(i) == ',' || word.charAt(i) == '?' ||
                    word.charAt(i) == ':' || word.charAt(i) == ';' || word.charAt(i) == '!' || word.charAt(i) == '\''){
                return null;
            }
        }

        for (int i = 0; i <= noiseWords.size()-1; i++){
            if (noiseWords.contains(word)){
                return null;
            }
        }

        return word;
    }

    public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
        int i = 0; int f = occs.size()-2; int m = (f-i)/2; int target = occs.get(occs.size()-1).frequency;

        while (true){
            if (occs.get(m).frequency == target || (i == m && target > occs.get(m).frequency)){
                occs.add(m, occs.get(occs.size()-1));
                occs.remove(occs.size()-1);
                break;
            } else if (i == m && target < occs.get(m).frequency){
                if (target < occs.get(f).frequency){
                    break;
                } else {
                    occs.add(m+1, occs.get(occs.size()-1));
                    occs.remove(occs.size()-1);
                    break;
                }
            } else if (occs.get(m).frequency < target){
                f = m; m = (f-i)/2 + i;
            } else {
                i = m; m = (f-i)/2 + i;
            }
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int k = 0; k < occs.size(); k++){
            list.add(occs.get(k).frequency);
        }
        return list;
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
            mergeKeyWords(kws);
        }
        sc.close();
    }

    public ArrayList<String> top5search(String kw1, String kw2) {

        ArrayList<Occurrence> docs = new ArrayList<Occurrence>();

        if (!keywordsIndex.containsKey(kw1) && !keywordsIndex.containsKey(kw2)){return null;}

        if (keywordsIndex.containsKey(kw1) && keywordsIndex.containsKey(kw2)){
            for (int i = 0; i < keywordsIndex.get(kw1).size() && i < 5; i++){
                docs.add(keywordsIndex.get(kw1).get(i));
            }
            for (int i = 0; i < keywordsIndex.get(kw2).size() && i < 5; i++){
                boolean added = false; boolean existed = false;
                for (int j = 0; j < docs.size() && !added; j++){
                    if (docs.get(j).document == keywordsIndex.get(kw2).get(i).document){
                        existed = true;
                        if (keywordsIndex.get(kw2).get(i).frequency > docs.get(j).frequency){
                            docs.remove(j);
                            for (int k = 0; k < docs.size(); k++){
                                if (keywordsIndex.get(kw2).get(i).frequency >= docs.get(k).frequency){
                                    docs.add(k, keywordsIndex.get(kw2).get(i));
                                    added = true;
                                    break;
                                }
                            }
                            if (!added && docs.size() < 5){
                                docs.add(keywordsIndex.get(kw2).get(i));
                            }
                        }
                    }
                }
                for (int k = 0; k < docs.size() && !added && !existed; k++){
                    if (keywordsIndex.get(kw2).get(i).frequency >= docs.get(k).frequency){
                        docs.add(k, keywordsIndex.get(kw2).get(i));
                        added = true;
                        break;
                    }
                }
                if (!added && docs.size() < 5 && !existed){
                    docs.add(keywordsIndex.get(kw2).get(i));
                }
            }
        } else if (keywordsIndex.containsKey(kw1) && !keywordsIndex.containsKey(kw2)){
            for (int i = 0; i < keywordsIndex.get(kw1).size() && i < 5; i++){
                docs.add(keywordsIndex.get(kw1).get(i));
            }
        } else if (!keywordsIndex.containsKey(kw1) && keywordsIndex.containsKey(kw2)){
            for (int i = 0; i < keywordsIndex.get(kw2).size() && i < 5; i++){
                docs.add(keywordsIndex.get(kw2).get(i));
            }
        }

        ArrayList<String> docsList = new ArrayList<String>();
        System.out.println(docs);

        for (int i = 0; i < docs.size(); i++){docsList.add(docs.get(i).document);}

        return docsList;
    }
}