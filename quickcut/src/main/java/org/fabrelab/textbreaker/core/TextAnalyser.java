package org.fabrelab.textbreaker.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textbreaker.core.model.TextAnalysisContext;

public class TextAnalyser {
	private WordParser wordParser = null;
	private PhraseParser phraseParser;
	private SentenceParser sentenceParser;
	
	Dictionary dic;
	Grammer grammer;
	public TextAnalyser(Dictionary dic, Grammer grammer){
		this.dic = dic;
		this.grammer = grammer;
		wordParser = new WordParser(dic);
		phraseParser = new PhraseParser(grammer);
		sentenceParser = new SentenceParser(grammer);
	}
	
	public List<TextAnalysisContext> analysis(String text){
		List<TextAnalysisContext> result = new ArrayList<TextAnalysisContext>();
		if(StringUtils.isNotBlank(text)){
			String[] sentenceTexts = sentenceParser.breakIntoSentences(text);
			for (String sentenceText : sentenceTexts) {
				if(StringUtils.isNotBlank(sentenceText)){
					TextAnalysisContext context = new TextAnalysisContext(sentenceText.trim());
					wordParser.parseWord(context);
					phraseParser.parsePhrase(context);
					sentenceParser.parseSentence(context);
					result.add(context);
				}
			}
		}
	
		return result;
	}
	
}
