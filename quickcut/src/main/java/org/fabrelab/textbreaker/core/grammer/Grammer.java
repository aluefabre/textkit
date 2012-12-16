package org.fabrelab.textbreaker.core.grammer;

import java.util.ArrayList;
import java.util.List;

import org.fabrelab.textbreaker.core.grammer.rule.PhraseRule;
import org.fabrelab.textbreaker.core.grammer.rule.SentenceRule;

public class Grammer {

	List<PhraseRule> phraseRules = new ArrayList<PhraseRule>();
	List<SentenceRule> sentenceRules = new ArrayList<SentenceRule>();

	public Grammer(List<PhraseRule> phraseRules, List<SentenceRule> sentenceRules) {
		super();
		this.phraseRules = phraseRules;
		this.sentenceRules = sentenceRules;
	}

	public List<PhraseRule> getPhraseRules() {
		return phraseRules;
	}

	public void setPhraseRules(List<PhraseRule> phraseRules) {
		this.phraseRules = phraseRules;
	}

	public List<SentenceRule> getSentenceRules() {
		return sentenceRules;
	}

	public void setSentenceRules(List<SentenceRule> sentenceRules) {
		this.sentenceRules = sentenceRules;
	}

}
