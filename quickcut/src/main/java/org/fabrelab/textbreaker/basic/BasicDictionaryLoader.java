package org.fabrelab.textbreaker.basic;

import java.util.Map;
import java.util.Set;

import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.util.TextLoaderUtil;


public class BasicDictionaryLoader {

	static String wordFileName = "/basic/main.dic";
	static String suffixFileName = "/basic/suffix.dic";
	
	public static Dictionary loadDictionary() {
		Map<String, String> words = TextLoaderUtil.loadWords(BasicDictionaryLoader.class, wordFileName, "BASIC", "utf-8");
		Set<Character> stopWords = TextLoaderUtil.loadStopWords(BasicDictionaryLoader.class, suffixFileName, "utf-8");
		Dictionary dic = new Dictionary(words, stopWords);
		dic.buildTrie();
		return dic;
	}
}
