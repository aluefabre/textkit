package org.fabrelab.textbreaker.core;

import java.util.ArrayList;
import java.util.List;

import org.fabrelab.textbreaker.core.WordParser.WordParseResult;
import org.fabrelab.textbreaker.core.grammer.Grammer;
import org.fabrelab.textbreaker.core.grammer.rule.PhraseRule;
import org.fabrelab.textbreaker.core.model.Phrase;
import org.fabrelab.textbreaker.core.model.PhraseTree;
import org.fabrelab.textbreaker.core.model.PosTag;
import org.fabrelab.textbreaker.core.model.TextAnalysisContext;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textbreaker.core.model.WordChain;

public class PhraseParser {
	private Grammer grammer;

	public PhraseParser(Grammer grammer) {
		this.grammer = grammer;
	}

	public void parsePhrase(TextAnalysisContext context) {
		WordParseResult wordParseResult = context.getWordParseResult();
		List<WordChain> wordChains = wordParseResult.getWordChains();
		
		List<PhraseTree> phrases = new ArrayList<PhraseTree>();
		for (WordChain chain : wordChains) {
			List<PhraseTree> phraseTrees = buildPhraseTrees(chain);
			phrases.addAll(phraseTrees);
		}
		
		PhraseParseResult phraseParseResult = new PhraseParseResult(phrases);
		
		for(PhraseTree phraseTree : phraseParseResult.getPhrases()){
			boolean apply = false;
			do{
				apply = false;
				for (PhraseRule rule : grammer.getPhraseRules()) {
					if (rule.match(phraseTree)) {
						rule.apply(phraseTree);
						apply = true;
						break;
					}
				}
			}while(apply);
		}
		
		
		context.setPhraseParseResult(phraseParseResult);
	}

	private List<PhraseTree> buildPhraseTrees(WordChain chain) {
		List<PhraseTree> result = new ArrayList<PhraseTree>();
		PhraseTree first = new PhraseTree();
		result.add(first);
		buildPossibleTrees(result, first, chain, 0);
		return result;
	}

	private void buildPossibleTrees(List<PhraseTree> trees, PhraseTree currentTree, WordChain chain, int index) {
		List<Word> words = chain.getWords();
		if (words.size()<=index) {
			return;
		}
		Word nextWord = words.get(index);
		List<PosTag> posTags = nextWord.getPosTags();
		if (posTags.size() == 1) {
			PosTag posTag = posTags.get(0);
			currentTree.addPhrase(new Phrase(nextWord.getWord(), posTag));
			buildPossibleTrees(trees, currentTree, chain, index+1);
		} else {
			PosTag firstPosTag = posTags.get(0);
			for (int i = 1; i < posTags.size(); i++) {
				PosTag nextPosTag = posTags.get(i);
				PhraseTree newTree = currentTree.duplicate();
				newTree.addPhrase(new Phrase(nextWord.getWord(), nextPosTag));
				trees.add(newTree);
				buildPossibleTrees(trees, newTree, chain, index+1);
			}
			currentTree.addPhrase(new Phrase(nextWord.getWord(), firstPosTag));
			buildPossibleTrees(trees, currentTree, chain, index+1);
		}
	}

	
	public static class PhraseParseResult {
		List<PhraseTree> phraseTrees = new ArrayList<PhraseTree>();
		
		public PhraseParseResult(List<PhraseTree> phrases) {
			this.phraseTrees = phrases;
		}
		
		public List<PhraseTree> getPhrases() {
			return phraseTrees;
		}
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer("");
			for (PhraseTree phraseTree : phraseTrees) {
				buffer.append(phraseTree.toString()+"\n");
			}
			return buffer.toString();
		}
	}
}
