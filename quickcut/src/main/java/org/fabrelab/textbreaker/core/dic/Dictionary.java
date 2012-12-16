package org.fabrelab.textbreaker.core.dic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fabrelab.textbreaker.core.dic.node.CharNode;
import org.fabrelab.textbreaker.core.dic.node.RootCharNode;
import org.fabrelab.textbreaker.core.dic.node.ZhCnCharNode;
import org.fabrelab.textbreaker.core.model.PosTag;
import org.fabrelab.textbreaker.core.model.UnknownPosTag;

public class Dictionary implements java.io.Serializable {

	private static final long serialVersionUID = -1990264152456667701L;

	protected Map<String, String> wordsMap = new HashMap<String, String>();
	
	protected Set<Character> stopWords = new HashSet<Character>();

	private CharNode root = new RootCharNode(' ', null);
	
	public Dictionary(Map<String, String> wordsMap, Set<Character> stopWords) {
		this.wordsMap = wordsMap;
		this.stopWords = stopWords;
	}

	/**
	 *                              Root
	 *            |                  |               |
	 *            国                                                 年                                        艺
	 *      |            |        |  |  |         |  |  |    
	 *      中                               美                     青     中     少                       曲     卖    文
	 *   |  |   |                 |   
	 *   新    大       旧                                             艺
	 *                            |
	 *                            文  
	 * @return
	 */
	public CharNode buildTrie() {
		CharNode visitor = root;
		CharNode vparent = null;
		for(String word: wordsMap.keySet()){
			visitor = root;
			for(int i=word.length()-1;i>=0;i--){
				Character cc = Character.toLowerCase(word.charAt(i));
				if(!visitor.containsKey(cc)){
					vparent = visitor;
					visitor = new ZhCnCharNode(cc, vparent);
					vparent.addChild(cc, visitor);
				}else{
					visitor = visitor.get(cc);
				}
				if(i==0){
					visitor.setWord(word);
				}
			}	
		}	
		return root;
	}

	public CharNode getRoot() {
		return root;
	}

	public void print() {
		root.print("");
	}	

	public Set<Character> getStopWords() {
		return stopWords;
	}

	public Map<String, String> getWordsMap() {
		return wordsMap;
	}
	
	public String getWordAnnotation(String word){
		String annotation = wordsMap.get(word);
		if(annotation!=null){
			return annotation;
		}
		return "";
	}

	public List<PosTag> getPosTag(String word) {
		List<PosTag> result = new ArrayList<PosTag>();
		String annotation = wordsMap.get(word);
		if(annotation!=null){
			String[] parts = annotation.trim().split("&");
			for(String part : parts){
				PosTag posTag = new PosTag(part);
				result.add(posTag);
			}
		}else{
			result.add(new UnknownPosTag(word));
		}
		return result;
	}
}
