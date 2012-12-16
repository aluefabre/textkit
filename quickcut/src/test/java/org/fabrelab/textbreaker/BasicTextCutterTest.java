package org.fabrelab.textbreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.fabrelab.textbreaker.basic.BasicTextCutter;
import org.fabrelab.textbreaker.core.model.Word;

public class BasicTextCutterTest {
	public static void main(String[] args) {
		tryCut("Sharp（夏普） ");
		tryCut(("进行有偿家教谋取私利的行为。续梅表示，在极少数教师身上存在“该讲的内容上课不讲，而是放到课后有偿家教的时候讲”的情况"));
		alot();
	}

	private static void alot() {
		tryCut("日照香炉生紫烟，遥看瀑布挂前川。飞流直下三千尺，疑是银河落九天。");
		tryCut("伊藤洋华堂总府店");
		tryCut("工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作");
		tryCut("我购买了道具和服装。草泥马");
		tryCut("我爱北京天安门");
		tryCut("中国科学院");
		tryCut("雷猴是个好网站");
		tryCut(("总经理完成了这件事情"));
		tryCut(("电脑修好了"));
		tryCut(("做好了这件事情就一了百了了"));
		tryCut(("人们审美的观点是不同的"));
		tryCut(("我们买了一个美的空调"));
		tryCut(("中国的首都是北京"));
		tryCut(("买水果然后来世博园"));
		tryCut(("还需要很长的路要走"));
		tryCut(("进行有偿家教谋取私利的行为。续梅表示，在极少数教师身上存在“该讲的内容上课不讲，而是放到课后有偿家教的时候讲”的情况"));
		tryCut(("60周年首都阅兵"));
		tryCut(("永和服装饰品有限公司"));
		tryCut(("很好但主要是基于网页形式"));
		tryCut(("但是后来我才知道你是对的"));
		tryCut(("为什么我不能拥有想要的生活"));
		tryCut(("使用了它就可以解决一些问题"));
		tryCut(("“Microsoft”一词由“MICROcomputer（微型计算机）”和“SOFTware（软件）”两部分组成"));
		
		
		try {
			InputStream is = BasicTextCutterTest.class.getResourceAsStream("/text.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while(true){
				String line = reader.readLine();
				if(line==null){
					break;
				}
				else{
					tryCut((line));
					
				}
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void tryCut(String text) {
		System.out.println(text);
		BasicTextCutter breaker = new BasicTextCutter();
		List<Word> invertCut = breaker.invertCut(text, false);
		System.out.println(invertCut);
		checkPosition(text, invertCut);
		List<Word> invertCut2 = breaker.invertCut(text, true);
		System.out.println(invertCut2);
		checkPosition(text, invertCut2);
		List<Word> fullCut = breaker.fullCut(text);
		System.out.println(fullCut);
		checkPosition(text, fullCut);
	}

	private static void checkPosition(String text, List<Word> fullCut) {
		for(Word subWord : fullCut){
			if(subWord.getWord().charAt(0)!=text.charAt(subWord.getStartPoint())){
				System.err.println("ERROR");
			}
		}
	}
}
