package org.fabrelab.textbreaker.core.grammer.rule;

import org.fabrelab.textbreaker.core.model.PhraseTree;

public interface PhraseRule {

	public boolean match(PhraseTree phraseTree);

	public void apply(PhraseTree phraseTree);
	 
}
