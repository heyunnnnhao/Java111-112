package trie;

import java.util.ArrayList;

public class Trie {
	
	private Trie() { }
	
	public static TrieNode buildTrie(String[] allWords) {
      
		if (allWords.length == 0) return null;

		TrieNode ptr = null;
		TrieNode root = new TrieNode(null, null, null);
	   
		for (int i = 0; i < allWords.length; i++) {
	   
			Indexes temp;

			if (i == 0) {
				temp = new Indexes( i, (short) 0, (short) (allWords[0].length() - 1) );
				root.firstChild = new TrieNode(temp, null, null);
				ptr = root.firstChild;
			} else {
				ptr = root.firstChild;
				TrieNode prev = root;
				TrieNode prefixPtr = null;
				boolean toChild = true;
				boolean toPrefix = false;
				boolean insert = false;
			   
				while (ptr != null) {				   
				
					String word1 = allWords[ptr.substr.wordIndex].substring(0, ptr.substr.endIndex+1);
					String word2 = allWords[i];				   
					int commonLongestPrefix = Count(word1, word2);
					
					if (commonLongestPrefix == -1) {
						prev = ptr;
						ptr = ptr.sibling;
						toChild = false;
					} else {
						if (commonLongestPrefix - 1 == ptr.substr.endIndex) {
							prev = ptr;
							ptr = ptr.firstChild;
							toChild = true;
							toPrefix = true;
							prefixPtr = prev;
						} else {
							Indexes prefix = null;
							TrieNode word = null;						   
							if (toPrefix == false) {
								prefix = new Indexes(ptr.substr.wordIndex, (short) 0, (short) (commonLongestPrefix - 1));
								Indexes newWord = new Indexes(i, (short) commonLongestPrefix, (short) (allWords[i].length() - 1));
								Indexes update = new Indexes(ptr.substr.wordIndex, (short) commonLongestPrefix, (short) ptr.substr.endIndex);
								ptr = new TrieNode(update, ptr.firstChild, ptr.sibling);
								word = new TrieNode(newWord, null, null);
							   
								TrieNode newPrefix2= new TrieNode(prefix, ptr, ptr.sibling);
								ptr.sibling = word;
								if(toChild == false) {
									prev.sibling = newPrefix2;
								}
								else {
									prev.firstChild = newPrefix2;
								}
								insert = true;
								break;
							} else {
								if (commonLongestPrefix - 1 != prefixPtr.substr.endIndex) {
									prefix = new Indexes(ptr.substr.wordIndex, (short) (prefixPtr.substr.endIndex + 1), (short) (commonLongestPrefix - 1));
									Indexes newWord = new Indexes(i, (short) commonLongestPrefix, (short) (allWords[i].length() - 1));
									Indexes update = new Indexes(ptr.substr.wordIndex, (short) commonLongestPrefix, (short) ptr.substr.endIndex);
									ptr = new TrieNode(update, ptr.firstChild, ptr.sibling);
									word = new TrieNode(newWord, null, null);
							   
									TrieNode newPrefix = new TrieNode(prefix, ptr, ptr.sibling);
									ptr.sibling = word;
							   
									if (toChild == false) {
										prev.sibling = newPrefix;
									} else {
										prev.firstChild = newPrefix;
									}

									insert = true;
									break;
								} else {
									prev = ptr;
									ptr = ptr.sibling;
									toChild = false;
								}
							}
						}  
					}
				}

				if (insert == false) {
					if (toPrefix) {
						Indexes newWord = new Indexes(i, (short) (prefixPtr.substr.endIndex+1), (short) (allWords[i].length() - 1));
						TrieNode word2 = new TrieNode(newWord, null, null);
						prev.sibling = word2;
					} else {
						Indexes newWord = new Indexes(i, (short) 0, (short) (allWords[i].length() - 1));
						TrieNode word2 = new TrieNode(newWord, null, null);
						prev.sibling = word2;
					}
				}
			}		   
		}
		
		return root;

	}
	
	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
		
		ArrayList<TrieNode> holder = new ArrayList<TrieNode>();
		TrieNode ptr = root.firstChild;
		
		int length = prefix.length();
	
		return Search(ptr, holder, allWords, length, prefix);
	}

	private static int Count(String one, String two) {
		
		int length = (one.length() > two.length()) ? two.length() : one.length();
		int temp = 0;
		   
		for (int i = 0; i < length; i++) {
			if (one.charAt(0) == two.charAt(0)) {
				temp ++;
			} else if (one.charAt(i) != two.charAt(i)) {
				return -1;
			}
		}
		return temp;
	}
	
	private static ArrayList<TrieNode> Search(TrieNode ptr, ArrayList<TrieNode> holder, String[]allWords, int length, String prefix){
      
		while (ptr != null) {
			String word = allWords[ptr.substr.wordIndex].substring(0, ptr.substr.endIndex + 1);
			int commonLongestPrefix = Count(word, prefix);
			
			if(commonLongestPrefix == -1) {
				ptr = ptr.sibling;
			} else {
				if (commonLongestPrefix < length) {
					if(ptr.firstChild != null) {
						holder = Search(ptr.firstChild, holder, allWords, length, prefix);
						if(holder == null) {
							holder = new ArrayList<TrieNode>();
						}
						ptr = ptr.sibling;
					} else {
						ptr = ptr.sibling;
					}
				} else {
					if (ptr.firstChild != null) {
						holder = Search(ptr.firstChild, holder, allWords, length, prefix);
						ptr = ptr.sibling;
						if(holder==null) {
							holder = new ArrayList<TrieNode>();
						}
					} else {
						holder.add(ptr);
						ptr = ptr.sibling;
					}  
				}  
			}
		}

		if (holder==null || holder.isEmpty() == true) {
			return null;
		}

		return holder;
	}
   
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		
		if (root == null) {
			return;
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
			.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		System.out.print(" ---");
		
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
}
