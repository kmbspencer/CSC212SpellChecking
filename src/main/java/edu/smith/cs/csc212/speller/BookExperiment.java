package edu.smith.cs.csc212.speller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author jfoley
 *
 */
public class BookExperiment {
	
	static List<String> loadTextFileToWords(String fileName) {
		List<String> words = new ArrayList<>();
		try (BufferedReader reader = WordSplitter.readUTF8File(fileName)) {
			reader.lines().forEach((String line) -> {
				for (String word : WordSplitter.splitTextToWords(line)) {
					words.add(word);
				}
			});	
		} catch (IOException e) {
			throw new RuntimeException("Error reading file: "+fileName, e);
		}
		
		return words;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> wordsInFrankenstein = loadTextFileToWords("src/main/resources/frankenstein.txt");
		System.out.println("Frankenstein contains approximately: "+wordsInFrankenstein.size()+" words.");
		List<String> listofwords = CheckSpelling.loadDictionary();
		HashSet<String> h = new HashSet<>(listofwords);
		CheckSpelling.timeLookup(wordsInFrankenstein, h);
	}
}
