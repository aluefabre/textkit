package org.fabrelab.textbreaker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextLoader {
	public static List<String> loadText(InputStream is, String encoding) {

		List<String> originalLines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				} else {
					originalLines.add(line);

				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return originalLines;
	}

}
