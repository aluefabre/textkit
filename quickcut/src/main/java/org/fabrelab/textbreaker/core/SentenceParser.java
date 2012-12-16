package org.fabrelab.textbreaker.core;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.core.PhraseParser.PhraseParseResult;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textbreaker.core.model.PhraseTree;
import org.fabrelab.textbreaker.core.model.TextAnalysisContext;

public class SentenceParser {
	private Grammer grammer;

	public SentenceParser(Grammer grammer) {
		this.grammer = grammer;
	}

	void parseSentence(TextAnalysisContext context) {
		PhraseParseResult phraseParseResult = context.getPhraseParseResult();
		List<PhraseTree> phrases = phraseParseResult.getPhrases();
		int max = Integer.MIN_VALUE;
		PhraseTree selected = null;
		for (PhraseTree phraseTree : phrases) {
			int score = phraseTree.getScore();
			if(score>max){
				max = score;
				selected = phraseTree;
			}
		}
		context.setSentenceParserResult(new SentenceParserResult(selected));
	}

	public String[] breakIntoSentences(String text) {
		return StringUtils.split(text, "。！!？?\n");
	}

	public static class SentenceParserResult{
		PhraseTree phraseTree = null;

		public SentenceParserResult(PhraseTree selected) {
			super();
			this.phraseTree = selected;
		}

		public PhraseTree getPhraseTree() {
			return phraseTree;
		}
		
	}
}
