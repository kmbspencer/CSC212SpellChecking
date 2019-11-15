package edu.smith.cs.csc212.speller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class contains experimentation code.
 * @author jfoley
 *
 */
public class CheckSpelling {
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		// Read all lines from a file:
		// This file has one word per line.
		List<String> words = WordSplitter.readUTF8File("src/main/resources/words")
				.lines()
				.collect(Collectors.toList());
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			} /**else {
				System.out.println(w);
			}**/
		}
		
		long endLookup = System.nanoTime();
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println("  "+dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
		
	}
	
	/**
	 * This is **an** entry point of this assignment.
	 * @param args - unused command-line arguments.
	 */
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		
		// --- Create a bunch of data structures for testing:
		long startTreeSetFill = System.nanoTime();
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		/**TreeSet<String> treeOfWords = new TreeSet<>();
		for(String w :listOfWords) {
			treeOfWords.add(w);
		}**/
		long endTreeSetFill = System.nanoTime();
		double treeSetFillTime = (endTreeSetFill  - startTreeSetFill) / 1e9;
		int treeEFill = (int) ((int) ((treeSetFillTime/1e-9)/listOfWords.size()));
		
		long startHashSetFill = System.nanoTime();
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		/**HashSet<String> hashOfWords = new HashSet<>();
		for(String w :listOfWords) {
			hashOfWords.add(w);
		}**/
		long endHashSetFill = System.nanoTime();
		double hashSetFillTime = (endHashSetFill  - startHashSetFill) / 1e9;
		int hashEFill = (int) ((int) ((hashSetFillTime/1e-9)/listOfWords.size()));
		
		long startBSLFill = System.nanoTime();
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		long endBSLFill = System.nanoTime();
		double bslFillTime = (endBSLFill  - startBSLFill) / 1e9;
		int bslEFill = (int) ((int) ((bslFillTime/1e-9)/listOfWords.size()));
		
		CharTrie trie = new CharTrie();
		long startTrieFill = System.nanoTime();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		long endTrieFill = System.nanoTime();
		double trieFillTime = (endTrieFill  - startTrieFill) / 1e9;
		int trieEFill = (int) ((int) ((trieFillTime/1e-9)/listOfWords.size()));
		
		LLHash hm100k = new LLHash(1000000);
		long startLLHashFill = System.nanoTime();
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		long endLLHashFill = System.nanoTime();
		double llHashFillTime = (endLLHashFill  - startLLHashFill) / 1e9;
		int llhashEFill = (int) ((int) ((llHashFillTime/1e-9)/listOfWords.size()));
		
		// --- Make sure that every word in the dictionary is in the dictionary:
		//     This feels rather silly, but we're outputting timing information!
		System.out.println("\n  It took "+ treeSetFillTime + " seconds to fill the TreeSet");
		System.out.println("  That is "+treeEFill + " ns/item");
		timeLookup(listOfWords, treeOfWords);
		
		System.out.println("\n  It took "+ hashSetFillTime + " seconds to fill the HashSet");
		System.out.println("  That is "+hashEFill + " ns/item");
		timeLookup(listOfWords, hashOfWords);
		
		System.out.println("\n  It took "+ bslFillTime + " seconds to fill the bsl");
		System.out.println("  That is "+bslEFill + " ns/item");
		timeLookup(listOfWords, bsl);
		
		System.out.println("\n  It took "+ trieFillTime + " seconds to fill the Trie");
		System.out.println("  That is "+trieEFill + " ns/item");
		timeLookup(listOfWords, trie);
		
		System.out.println("\n  It took "+ llHashFillTime + " seconds to fill the LLHash");
		System.out.println("  That is "+llhashEFill + " ns/item");
		timeLookup(listOfWords, hm100k);
		
		
		
	
		// --- print statistics about the data structures:
		System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());

		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		System.out.println("Done!");
	}
}
