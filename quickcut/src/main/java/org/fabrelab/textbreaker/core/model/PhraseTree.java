package org.fabrelab.textbreaker.core.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fabrelab.textbreaker.core.util.CharUtil;

public class PhraseTree {
	LinkedList<Phrase> phrases = new LinkedList<Phrase>();
	
	
	public void recalculate() {
		int start = 0;
		for (Phrase phrase : phrases) {
			phrase.setStartPoint(start);
			start = start + phrase.getPosTag().getName().length();
		}		
	}

	public String getAbstractedText() {
		String abstractedText = "";
		if(StringUtils.isBlank(abstractedText)){
			for (Phrase phrase : phrases) {
				abstractedText+=phrase.getPosTag().getName();
			}
		}
		return abstractedText;
	}

	public void merge(Phrase mergedPhrase) {
		Iterator<Phrase> iterator = phrases.iterator();

		int startPoint = mergedPhrase.getStartPoint();
		int endPoint = startPoint + mergedPhrase.getFromTag().length() - 1;
		
		while(iterator.hasNext()){
			Phrase phrase = iterator.next();
			if(phrase.getStartPoint()>=startPoint
					&& phrase.getEndPoint()<=endPoint){
				mergedPhrase.addChild(phrase);
				iterator.remove();
			}
		}	
		boolean inserted = false;
		for (int i = 0; i < phrases.size(); i++) {
			Phrase phrase = phrases.get(i);
			if(phrase.getStartPoint()>endPoint){
				phrases.add(i, mergedPhrase);
				inserted = true;
				break;
			}
		}
		if(!inserted){
			phrases.add(mergedPhrase);
		}
		recalculate();
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getScore() + "[" + getAbstractedText() + "]\n");
		int indent = 0;
		buildTree(result, indent, phrases);
		return result.toString();
	}

	private void buildTree(StringBuffer result, int indent, List<Phrase> phrases) {
		for (Phrase phrase : phrases) {
			for(int i=0;i<indent;i++){
				result.append(" ");
			}
			result.append(phrase.toString()).append("\n");
			
			buildTree( result, indent+4, phrase.getChildren());
		}
	}

	public PhraseTree duplicate() {
		PhraseTree result = new PhraseTree();
		for(Phrase phrase : phrases){
			result.addPhrase(phrase.duplicate());
		}
		return result;
	}

	public void addPhrase(Phrase phrase) {
		phrases.add(phrase);
		recalculate();
	}

	public LinkedList<Phrase> getPhrases() {
		return phrases;
	}

	public int getScore() {
		String abstractedText = getAbstractedText();
		int chCharCount = 0;
		for (int i = 0; i < abstractedText.length(); i++) {
			char ch = abstractedText.charAt(i);
			if(CharUtil.containChinese(ch+"")){
				chCharCount++;
			}
		}
		int score = - phrases.size() - chCharCount;
		return score;
	}
}
