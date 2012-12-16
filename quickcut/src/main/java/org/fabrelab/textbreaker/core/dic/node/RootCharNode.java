package org.fabrelab.textbreaker.core.dic.node;

import java.util.HashMap;
import java.util.Map;

public class RootCharNode implements CharNode, java.io.Serializable {
	Character ckey;
	Map<Character, CharNode> childMap = new HashMap<Character, CharNode>();;
	CharNode parent;
	private boolean leaf = false;
	
	public RootCharNode(Character ckey, CharNode parent){
		this.ckey = ckey;
		this.parent = parent;
	}
	public void addChild(Character k, CharNode visitor) {
		childMap.put(k, visitor);
	}
	public boolean containsKey(Character cc) {
		return childMap.containsKey(cc);
	}
	public CharNode get(Character cc) {
		return childMap.get(cc);
	}
	
	public String getWord() {
		return "";	
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public void print(String blank) {
		String line = blank + "+" + ckey;
		if(leaf){
			line+="(L)";
		}
		System.out.println(line);
		for(CharNode child : childMap.values()){
			child.print(blank+"   ");
		}
	}
	
	public void setWord(String word) {
		
	}
	
	public CharNode getParent() {
		return parent;
	}	
}
