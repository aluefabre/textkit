package org.fabrelab.textbreaker.address;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class AddressBuilderUtil {

	public static void main(String[] argv) throws FileNotFoundException {
		Set<String> words = new HashSet<String>();
		
		processFile(words, "cityarea.txt");
		
		PrintStream out = new PrintStream(new FileOutputStream(new File("address.txt")));
		for(String word : words){
			System.out.println(word);
			out.println(word);
		}
		out.close();
	}

	private static void processFile(Set<String> words, String fileName) {
		try {
			InputStream is = AddressBuilderUtil.class.getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while(true){
				String line = reader.readLine();
				if(line==null || line.equals("")){
					break;
				}
				processLine(words, line);
			}
			is.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void processLine(Set<String> words, String line) {
		if(StringUtils.isBlank(line)){
			return;
		}
		if(line.length()==1){
			return;
		}
		if(line.length()==2){
			addIn(words, line);
			return;
		}
		
		if(depart(words, line, "社区")){
			return;
		}
		if(depart(words, line, "回族自治区")){
			return;
		}
		if(depart(words, line, "维吾尔自治区")){
			return;
		}
		if(depart(words, line, "壮族自治区")){
			return;
		}
		if(depart(words, line, "自治区")){
			return;
		}
		if(depart(words, line, "藏族自治州")){
			return;
		}
		if(depart(words, line, "蒙古族自治州")){
			return;
		}
		if(depart(words, line, "蒙古自治州")){
			return;
		}
		if(depart(words, line, "藏族羌族自治州")){
			return;
		}
		
		if(depart(words, line, "布依族苗族自治州")){
			return;
		}
		if(depart(words, line, "苗族自治州")){
			return;
		}
		if(depart(words, line, "彝族自治州")){
			return;
		}
		if(depart(words, line, "白族自治州")){
			return;
		}
		if(depart(words, line, "朝鲜族自治州")){
			return;
		}
		if(depart(words, line, "自治州")){
			return;
		}
		if(depart(words, line, "保安族东乡族撒拉族自治县")){
			return;
		}
		if(depart(words, line, "苗族土家族自治县")){
			return;
		}
		if(depart(words, line, "毛南族自治县")){
			return;
		}
		if(depart(words, line, "藏族自治县")){
			return;
		}
		if(depart(words, line, "苗族侗族自治县")){
			return;
		}
		if(depart(words, line, "仡佬族苗族自治县")){
			return;
		}
		if(depart(words, line, "哈萨克族自治县")){
			return;
		}
		if(depart(words, line, "佤族自治县")){
			return;
		}
		if(depart(words, line, "哈萨克自治县")){
			return;
		}
		if(depart(words, line, "黎族苗族自治县")){
			return;
		}
		if(depart(words, line, "哈萨克自治县")){
			return;
		}
		if(depart(words, line, "瑶族自治县")){
			return;
		}
		if(depart(words, line, "黎族自治县")){
			return;
		}
		if(depart(words, line, "羌族自治县")){
			return;
		}
		if(depart(words, line, "满族自治县")){
			return;
		}
		if(depart(words, line, "傣族彝族自治县")){
			return;
		}
		if(depart(words, line, "哈尼族彝族自治县")){
			return;
		}
		if(depart(words, line, "彝族自治县")){
			return;
		}
		if(depart(words, line, "回族自治县")){
			return;
		}
		if(depart(words, line, "土家族自治县")){
			return;
		}
		if(depart(words, line, "锡伯自治县")){
			return;
		}
		if(depart(words, line, "壮族瑶族自治县")){
			return;
		}
		if(depart(words, line, "彝族哈尼族拉祜族自治县")){
			return;
		}
		if(depart(words, line, "朝鲜族自治县")){
			return;
		}
		if(depart(words, line, "塔吉克自治县")){
			return;
		}
		if(depart(words, line, "侗族自治县")){
			return;
		}
		if(depart(words, line, "自治县")){
			return;
		}
		if(depart(words, line, "特别行政区")){
			return;
		}
		if(depart(words, line, "地区")){
			return;
		}
		if(depart(words, line, "省")){
			return;
		}
		if(depart(words, line, "市")){
			return;
		}
		if(depart(words, line, "新区")){
			return;
		}
		if(depart(words, line, "区")){
			return;
		}
		if(depart(words, line, "县")){
			return;
		}
		if(depart(words, line, "乡")){
			return;
		}
		if(depart(words, line, "镇")){
			return;
		}
		if(depart(words, line, "屯")){
			return;
		}
		if(depart(words, line, "村")){
			return;
		}
		if(depart(words, line, "街道")){
			return;
		}
		addIn(words, line);
	}

	private static void addIn(Set<String> words, String line) {
		if(line!=null && line.trim().length()>1){
			words.add(line.trim());
		}
	}

	private static boolean depart(Set<String> words, String line, String suffix) {
		if(line.contains(suffix)){
			String[] parts = line.split(suffix);
			for(String part : parts){
				processLine(words, part);
			}
			addIn(words, parts[0]+suffix);
			return true;
		}
		return false;
	}
	
}
