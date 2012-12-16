package org.fabrelab.textbreaker.core.model;

import java.util.ArrayList;
import java.util.List;

public class Word {

	int startPoint;
	int endPoint;
	
	String word;
	List<PosTag> posTags;

	public Word(String word, List<PosTag> posTags) {
		this.word = word;
		this.startPoint = 0;
		this.endPoint = word.length()-1;
		this.posTags = posTags;
	}

	public Word(String word, PosTag posTag) {
		posTags = new ArrayList<PosTag>();
		posTags.add(posTag);
		this.word = word;
		this.startPoint = 0;
		this.endPoint = word.length()-1;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
		this.endPoint = startPoint + word.length() - 1;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
		this.startPoint = endPoint - word.length() + 1;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public String toString() {
		return "Word[" + word + "]";
	}

	public int getEndPoint() {
		return endPoint;
	}

	public List<PosTag> getPosTags() {
		return posTags;
	}

	public void setPosTags(List<PosTag> posTags) {
		this.posTags = posTags;
	}

	public Word duplicate() {
		Word copy = new Word(word, posTags);
		copy.setStartPoint(startPoint);
		return copy;
	}

	public String getAnnotation() {
		return posTags.get(0).getName();
	}
}
