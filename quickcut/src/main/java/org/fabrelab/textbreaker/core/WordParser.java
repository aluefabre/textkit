package org.fabrelab.textbreaker.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.model.TextAnalysisContext;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textbreaker.core.model.WordChain;
import org.fabrelab.textbreaker.core.util.DicLookupUtil;

public class WordParser {

	Dictionary dic;

	public WordParser(Dictionary dic) {
		this.dic = dic;
	}

	public void parseWord(TextAnalysisContext context) {
		LinkedList<Word> words = DicLookupUtil.fullCut(dic, context.getText());

		List<WordChain> wordChains = buildPossibleChains(context.getText(), words);
		
		for (WordChain wordChain : wordChains) {
			wordChain.fillGap();
		}

		WordParseResult wordParseResult = new WordParseResult(wordChains);

		context.setWordParseResult(wordParseResult);

	}

	private List<WordChain> buildPossibleChains(String text, LinkedList<Word> words) {
		List<WordChain> chains = new ArrayList<WordChain>();
		WordChain first = new WordChain(text);
		chains.add(first);
		buildPossibleChains(chains, first, words, -1);
		return chains;
	}

	private void buildPossibleChains(List<WordChain> chains, WordChain currentChain,
			LinkedList<Word> words, int endPoint) {
		LinkedList<Word> nextWords = new LinkedList<Word>();
		for (Word word : words) {
			if (word.getStartPoint() > endPoint
					&& !existWordBetween(words, endPoint, word.getStartPoint())) {
				nextWords.add(word);
			}
		}
		if (nextWords.size() == 0) {
			return;
		}
		if (nextWords.size() == 1) {
			Word nextWord = nextWords.get(0);
			currentChain.add(nextWord);
			buildPossibleChains(chains, currentChain, words, nextWord.getEndPoint());
		} else {
			Word firstWord = nextWords.get(0);
			for (int i = 1; i < nextWords.size(); i++) {
				Word nextWord = nextWords.get(i);
				WordChain newChain = currentChain.duplicate();
				newChain.add(nextWord);
				chains.add(newChain);
				buildPossibleChains(chains, newChain, words, nextWord.getEndPoint());
			}
			currentChain.add(firstWord);
			buildPossibleChains(chains, currentChain, words, firstWord.getEndPoint());
		}
	}


	private boolean existWordBetween(LinkedList<Word> words, int startPoint, int endPoint) {
		for (Word word : words) {
			if(word.getStartPoint()>startPoint && word.getEndPoint()<endPoint){
				return true;
			}
		}
		return false;
	}


	public class WordParseResult {
		List<WordChain> wordChains;

		public WordParseResult(List<WordChain> wordChains) {
			super();
			this.wordChains = wordChains;
		}

		public List<WordChain> getWordChains() {
			return wordChains;
		}

		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer("");
			for (WordChain wordChain : wordChains) {
				buffer.append(wordChain.toString()+"\n");
			}
			return buffer.toString();
		}

	}
}
