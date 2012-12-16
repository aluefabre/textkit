package org.fabrelab.textbreaker.basic;

import java.util.ArrayList;
import java.util.List;

import org.fabrelab.textbreaker.core.dic.Dictionary;
import org.fabrelab.textbreaker.core.model.Word;
import org.fabrelab.textbreaker.core.util.DicLookupUtil;

public class BasicTextCutter {
	private static Dictionary dic = null;

	public List<String> cut(String text){
		List<String> result = new ArrayList<String>();
		List<Word> lookupResults =  this.invertCut(text, false);
		for(Word lookupResult:lookupResults){
			result.add(lookupResult.getWord());
		}
		return result;
	}
	
	public List<Word> invertCut(String text, boolean greedy){
		if(dic==null){
			dic = BasicDictionaryLoader.loadDictionary();
		}
		List<Word> result = DicLookupUtil.invertedMaxCut(dic, text, greedy);
		return result;
	}
	
	public List<Word> fullCut(String text){
		if(dic==null){
			dic = BasicDictionaryLoader.loadDictionary();
		}
		List<Word> result = DicLookupUtil.fullCut(dic, text);
		return result;
	}
}
