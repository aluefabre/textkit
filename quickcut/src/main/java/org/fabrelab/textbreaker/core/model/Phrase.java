package org.fabrelab.textbreaker.core.model;

import java.util.ArrayList;
import java.util.List;

public class Phrase {
	
	PosTag posTag;
	int startPoint;
	int endPoint;
	String fromTag;
	
	Phrase parent = null;
	List<Phrase> children = new ArrayList<Phrase>();
	
	public Phrase(String fromText, PosTag posTag) {
		this.fromTag = fromText;
		this.posTag = posTag;
	}

	public PosTag getPosTag() {
		return posTag;
	}

	public void setPosTag(PosTag posTag) {
		this.posTag = posTag;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
		this.endPoint = startPoint + posTag.getName().length() - 1;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
		this.startPoint = endPoint - posTag.getName().length() + 1;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public int getEndPoint() {
		return endPoint;
	}

	public void addChild(Phrase phrase) {
		children.add(phrase);
		phrase.setParent(this);
	}

	public Phrase getParent() {
		return parent;
	}

	public void setParent(Phrase parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		String result = "[" + posTag.getName()  + "]";
		if(children.size()==0){
			result = result + fromTag;
		}
		return result;
	}

	public String getFromTag() {
		return fromTag;
	}
	
	public String getFromText() {
		if(children.isEmpty()){
			return fromTag;
		}
		String result = "";
		for(Phrase child : children){
			result = result + child.getFromText();
		}
		return result;
	}
	
	public List<Phrase> getChildren() {
		return children;
	}

	public Phrase duplicate() {
		Phrase copy = new Phrase(fromTag, posTag);
		copy.setStartPoint(startPoint);
		return copy;
	}
}
