package org.fabrelab.textbreaker.core.dic.node;

import java.util.HashMap;
import java.util.Map;

public class ZhCnCharNode implements CharNode, java.io.Serializable {
	Character ckey;
	Map<Character, CharNode> childMap = new HashMap<Character, CharNode>();;
	CharNode parent;
	private String word = null;
	
	public ZhCnCharNode(Character ckey, CharNode parent){
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
		return word;	
	}
	
	public boolean isLeaf() {
		return word!=null;
	}
	
	public void print(String blank) {
		String line = blank + "+" + ckey;
		if(isLeaf()){
			line+="(L)";
		}
		System.out.println(line);
		for(CharNode child : childMap.values()){
			child.print(blank+"   ");
		}
	}
	public void setWord(String word) {
		this.word   = word;
	}
	
	public CharNode getParent() {
		return parent;
	}	
}
