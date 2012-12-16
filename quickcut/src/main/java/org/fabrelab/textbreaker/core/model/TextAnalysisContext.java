package org.fabrelab.textbreaker.core.model;

import org.fabrelab.textbreaker.core.PhraseParser.PhraseParseResult;
import org.fabrelab.textbreaker.core.SentenceParser.SentenceParserResult;
import org.fabrelab.textbreaker.core.WordParser.WordParseResult;

public class TextAnalysisContext {

	String text;
	WordParseResult wordParseResult;
	PhraseParseResult phraseParseResult;
	SentenceParserResult sentenceParserResult;
	
	public TextAnalysisContext(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Sentence getResultSentence() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWordParseResult(WordParseResult wordParseResult) {
		this.wordParseResult = wordParseResult;
	}

	public WordParseResult getWordParseResult() {
		return wordParseResult;
	}

	public void setPhraseParseResult(PhraseParseResult phraseParseResult) {
		this.phraseParseResult = phraseParseResult;
	}

	public PhraseParseResult getPhraseParseResult() {
		return phraseParseResult;
	}

	public void setSentenceParserResult(SentenceParserResult sentenceParserResult) {
		this.sentenceParserResult = sentenceParserResult;
	}

	public SentenceParserResult getSentenceParserResult() {
		return sentenceParserResult;
	}
	
}
