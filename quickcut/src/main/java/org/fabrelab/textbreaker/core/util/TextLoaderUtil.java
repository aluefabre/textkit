package org.fabrelab.textbreaker.core.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class TextLoaderUtil {
	
	public static Map<String, String> loadWords(Class clazz, String wordFileName, String type, String encoding) {
		Map<String, String> words = new HashMap<String, String>();
		loadWords(clazz, wordFileName, words, type, encoding);
		return words;
	}
	
	public static void loadWords(Class clazz, String wordFileName, final Map<String, String> words, final String type, String encoding) {
		LineVisitor visitor = new LineVisitor(){
			@Override
			public void visit(String line) {
				words.put(line.trim(), type);
			}
		};
		traverse(clazz, wordFileName, encoding, visitor);
	}
	
	public static Map<String, String> loadWords(Class clazz, String wordFileName, String encoding) {
		Map<String, String> words = new HashMap<String, String>();
		loadWords(clazz, wordFileName, words, encoding);
		return words;
	}

	public static void loadWords(Class clazz, String wordFileName, final Map<String, String> words, String encoding) {
		LineVisitor visitor = new LineVisitor(){
			@Override
			public void visit(String line) {
				String[] parts = StringUtils.split(line);
				words.put(parts[0].trim(), parts[1].trim());
			}
		};
		traverse(clazz, wordFileName, encoding, visitor);
	}
	
	public static Set<Character> loadStopWords(Class clazz, String suffixFileName, String encoding) {
		final Set<Character> stopWords = new HashSet<Character>();
		LineVisitor visitor = new LineVisitor(){
			@Override
			public void visit(String line) {
				stopWords.add(line.trim().charAt(0));
			}
		};
		traverse(clazz, suffixFileName, encoding, visitor);
		return stopWords;
	}

	
	public static void loadStopWords(Set<Character> words, InputStream is, String encoding) {
		BufferedReader reader  = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, encoding));
			while(true){
				String word = reader.readLine();
				if(word==null || word.equals("")){
					break;
				}
				words.add(word.trim().charAt(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(reader!=null){
					reader.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	public static void traverse(Class clazz, String fileName, String encoding, LineVisitor visitor) {
		InputStream is = null;
		try {
			is = clazz.getResourceAsStream(fileName);
			BufferedReader reader  = null;
			try {
				reader = new BufferedReader(new InputStreamReader(is, encoding));
				while(true){
					String line = reader.readLine();
					if(StringUtils.isBlank(line)){
						break;
					}
					visitor.visit(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					if(reader!=null){
						reader.close();
					}
				} catch (Exception e) {
				}
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	public static interface LineVisitor{

		public void visit(String line);
		
	}
	
	
}
