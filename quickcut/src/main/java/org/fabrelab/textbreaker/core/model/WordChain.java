package org.fabrelab.textbreaker.core.model;

import java.util.LinkedList;
import java.util.List;

public class WordChain {
	
	LinkedList<Word> words = new LinkedList<Word>();
	String text;
	
	public WordChain(String text) {
		this.text = text;
	}

	public WordChain duplicate() {
		WordChain result = new WordChain(text);
		for(Word word : words){
			result.add(word.duplicate());
		}
		return result;
	}


	public void add(Word nextWord) {
		words.add(nextWord);
	}
	
	public void fillGap() {
		int start = 0;
		for (int i = 0; i < words.size(); i++) {
			Word lookupResult = words.get(i);
			if (lookupResult.getStartPoint() != start) {
				String gapWord = text.substring(start, lookupResult.getStartPoint());
				Word gapResult = new Word(gapWord, new UnknownPosTag(gapWord));
				gapResult.setStartPoint(start);
				words.add(i, gapResult);
				i++;
			}
			start = lookupResult.getEndPoint() + 1;
		}
		if (words.size() > 0) {
			if (words.getLast().getEndPoint() != text.length() - 1) {
				start = words.getLast().getEndPoint() + 1;
				String gapWord = text.substring(start, text.length());
				Word gapResult = new Word(gapWord, new UnknownPosTag(gapWord));
				gapResult.setStartPoint(start);
				words.add(gapResult);
			}
		}
	}

	@Override
	public String toString() {
		return "WordChain [words=" + words + "]";
	}

	public List<Word> getWords() {
		return words;
	}
	
}
