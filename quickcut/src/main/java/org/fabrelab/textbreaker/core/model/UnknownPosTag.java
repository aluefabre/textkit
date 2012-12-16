package org.fabrelab.textbreaker.core.model;

public class UnknownPosTag extends PosTag {

	
	String word;

	public UnknownPosTag(String word) {
		super("UNKNOWN");
		this.word = word;
	}

	public String getName() {
		return word;
	}

}
