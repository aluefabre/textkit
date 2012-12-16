package org.fabrelab.textbreaker.core.util;

public class CharUtil {
	
	public static boolean isSymbol(Character cc) {
		if(cc.charValue()=='&' || cc.charValue()=='#' || cc.charValue()=='@' 
				|| cc.charValue()=='-'	|| cc.charValue()=='_'
					|| cc.charValue()=='+'	|| cc.charValue()=='=' || cc.charValue()=='*'
					|| cc.charValue()==':'  || cc.charValue()=='~'	|| cc.charValue()=='^'
					|| cc.charValue()=='\\'	|| cc.charValue()=='/'	|| cc.charValue()=='|'
					|| cc.charValue()=='"'
					|| cc.charValue()=='('	|| cc.charValue()==')'
					|| cc.charValue()=='['	|| cc.charValue()==']'
					|| cc.charValue()=='{'	|| cc.charValue()=='}'
					|| cc.charValue()=='<'	|| cc.charValue()=='>'
					|| cc.charValue()=='!'|| cc.charValue()=='?'	|| cc.charValue()==',' || cc.charValue()=='.'){
			return true;
		}
		if(cc.charValue()=='“'	|| cc.charValue()=='”'
						|| cc.charValue()=='（'	|| cc.charValue()=='）'
						|| cc.charValue()=='【'	|| cc.charValue()=='】'
						|| cc.charValue()=='｛'	|| cc.charValue()=='｝'
						|| cc.charValue()=='《'	|| cc.charValue()=='》'
						|| cc.charValue()=='！'  || cc.charValue()=='？'	|| cc.charValue()=='，' || cc.charValue()=='。'
						|| cc.charValue()=='—'){
				return true;
		}
		return false;
	}

	public static boolean isLetterOrDigit(Character cc) {
		if(cc.charValue()>='0'&&cc.charValue()<='9'){
			return true;
		}
		if(cc.charValue()>='a'&&cc.charValue()<='z'){
			return true;
		}
		if(cc.charValue()>='A'&&cc.charValue()<='Z'){
			return true;
		}
		return false;
	}

	public static boolean containChinese(String key) {
		for(int i=0;i<key.length();i++){
			String ch = key.charAt(i)+"";
			if(ch.matches( "[\\u4E00-\\u9FA5]+")){
				return true;
			}
		}
		return false;
	}
	
	public static boolean containLetterOrDigitOrSymbol(String key) {
		for(int i=0;i<key.length();i++){
			Character ch = key.charAt(i);
			if(isSymbol(ch)||isLetterOrDigit(ch)){
				return true;
			}
		}
		return false;
	}
	
}
