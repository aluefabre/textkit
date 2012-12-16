package org.fabrelab.textbreaker.core.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.dic.node.CharNode;
import org.fabrelab.textbreaker.core.model.Word;


public class DicLookupUtil {
	
	public static LinkedList<Word> invertedMaxCut(Dictionary dic, String text, boolean greedy) {
		if(!CharUtil.containLetterOrDigitOrSymbol(text)){
			return invertedMaxCutWithoutSymbol(dic, text, greedy);
		}
		
		LinkedList<Word> words = new LinkedList<Word>();
		
		List<Word> subTexts = splitByLetterOrDigitOrSymbol(dic, text);
		
		for(Word subText : subTexts){
			if(CharUtil.containChinese(subText.getWord())){
				List<Word> subWords = invertedMaxCutWithoutSymbol(dic, subText.getWord(),greedy);
				for(Word subWord : subWords){
					subWord.setStartPoint(subWord.getStartPoint()+subText.getStartPoint());
					words.add(subWord);
				}
			}else{
				words.add(subText);
			}
		}
		
		return words;
	}
	
	/**
	 * 文艺青年 卖艺青年
	 * 
	 * @param dic
	 * 
	 * @param text
	 * @param greedy
	 * @return
	 */
	private static LinkedList<Word> invertedMaxCutWithoutSymbol(Dictionary dic, String text, boolean greedy) {
		LinkedList<Word> words = new LinkedList<Word>();
		text = (char) 11 + text;
		CharNode node = dic.getRoot();
		int wbeginMarker = text.length() - 1;
		while (wbeginMarker >= 0) {
			Character cc = Character.toLowerCase(text.charAt(wbeginMarker));
			if (node.containsKey(cc)) {
				// 从上到下查找节点
				node = node.get(cc);
				wbeginMarker--;
			} else {
				boolean found = false;
				while (node != null) {
					if (!node.isLeaf()) {
						node = node.getParent();
						wbeginMarker++;
						continue;
					}
					String word = node.getWord();
					if (endWithSuffix(dic, word)) {
						int wend = wbeginMarker + word.length() - 1;
						if (existWord(dic, text, wend, word.length())) {
							node = node.getParent();
							wbeginMarker++;
							continue;
						}
					}
					found = true;
					break;
				}
				if (found) {
					String wordText = node.getWord();
					if (greedy) {
						List<Word> subWords = fullCutWithoutSymbol(dic, wordText);
						for(Word subWord : subWords){
							subWord.setStartPoint(subWord.getStartPoint()+wbeginMarker);
							words.addFirst(subWord);
						}
					} else {
						Word word = new Word(wordText, dic.getPosTag(wordText));
						word.setStartPoint(wbeginMarker);
						words.addFirst(word);
					}
				} else {
					wbeginMarker = wbeginMarker - 2;
				}
				node = dic.getRoot();
			}
		}
		return words;
	}

	private static boolean endWithSuffix(Dictionary dic, String word) {
		if (word.length() <= 1) {
			return false;
		}
		return dic.getStopWords().contains(word.charAt(word.length() - 1));
	}

	/*
	 * 
	 * text中是否存在一个结尾在wend，并且长度超过length的词
	 */
	private static boolean existWord(Dictionary dic, String text, int wend, int length) {
		CharNode p = dic.getRoot();
		int wbeginMarker = wend;
		while (wbeginMarker >= 0) {
			Character t = Character.toLowerCase(text.charAt(wbeginMarker));
			if (p.isLeaf()) {
				String word = p.getWord();
				if (word.length() >= length) {
					return true;
				}
			}
			if (!p.containsKey(t)) {
				return false;
			} else {
				p = p.get(t);
			}
			wbeginMarker--;
		}
		return false;
	}

	public static LinkedList<Word> fullCut(Dictionary dic, String text) {
		if(!CharUtil.containLetterOrDigitOrSymbol(text)){
			return fullCutWithoutSymbol(dic, text);
		}
		
		LinkedList<Word> words = new LinkedList<Word>();
		
		List<Word> subTexts = splitByLetterOrDigitOrSymbol(dic, text);
		
		for(Word subText : subTexts){
			if(CharUtil.containChinese(subText.getWord())){
				List<Word> subWords = fullCutWithoutSymbol(dic, subText.getWord());
				for(Word subWord : subWords){
					subWord.setStartPoint(subWord.getStartPoint()+subText.getStartPoint());
					words.add(subWord);
				}
			}else{
				words.add(subText);
			}
		}
		return words;
	}
	

	private static List<Word> splitByLetterOrDigitOrSymbol(Dictionary dic, String text) {
		if(text.length()<1){
			return new ArrayList<Word>(0);
		}
		List<Word> result = new ArrayList<Word>();
		
		boolean letterOrDigit = true;
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			Word word = new Word(builder.toString(), dic.getPosTag(builder.toString()));
			word.setEndPoint(i-1);
			if(CharUtil.isSymbol(text.charAt(i))){
				if(builder.length()>0){
					result.add(word);
					builder.setLength(0);
				}
				Word sword = new Word(text.charAt(i)+"", dic.getPosTag(text.charAt(i)+""));
				sword.setEndPoint(i);
				result.add(sword);
				continue;
			}
			if(CharUtil.isLetterOrDigit(text.charAt(i))!=letterOrDigit){
				letterOrDigit = !letterOrDigit;
				if(builder.length()>0){
					result.add(word);
					builder.setLength(0);
				}
			}
			builder.append(text.charAt(i));
		}
		if(builder.length()>0){
			Word word = new Word(builder.toString(), dic.getPosTag(builder.toString()));
			word.setEndPoint(text.length()-1);
			result.add(word);
		}
		return result;
	}

	private static LinkedList<Word> fullCutWithoutSymbol(Dictionary dic, String text) {
		LinkedList<Word> words = new LinkedList<Word>();
		for (int i = 0; i <= text.length(); i++) {
			String subText = text.substring(0, i);
			if (subText.length() > 1) {
				fullTailCut(dic, words, subText);
			}
		}
		return words;
	}

	private static void fullTailCut(Dictionary dic, LinkedList<Word> words, String text) {
		CharNode node = dic.getRoot();
		int wbegin = text.length() - 1;
		while (wbegin >= 0) {
			Character t = Character.toLowerCase(text.charAt(wbegin));
			if (!node.containsKey(t)) {
				break;
			} else {
				node = node.get(t);
			}
			if (node.isLeaf()) {
				Word word = new Word(node.getWord(), dic.getPosTag(node.getWord()));
				word.setStartPoint(wbegin);
				words.add(word);
			}
			wbegin--;
		}
	}

}
