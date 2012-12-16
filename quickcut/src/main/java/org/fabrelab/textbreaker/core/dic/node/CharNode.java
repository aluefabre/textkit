package org.fabrelab.textbreaker.core.dic.node;


public interface CharNode {

	public void print(String string);

	public String getWord();
	
	public void addChild(Character k, CharNode visitor);
	
	public boolean containsKey(Character cc);
	
	public CharNode get(Character cc);

	public boolean isLeaf();

	public CharNode getParent();

	public void setWord(String word);
}
