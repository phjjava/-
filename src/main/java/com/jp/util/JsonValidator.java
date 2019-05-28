package com.jp.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonValidator {
	private static CharacterIterator it;
	private static char c;
    private static int col;
    
    public JsonValidator(){
    }
    
    /**
     * 验证一个字符串是否是合法的json格式
     * @param input
     * @return
     */
    public static boolean validate(String json) {
    	json = json.trim();
        boolean ret = valid(json);
        return ret;
    }
    
    private static boolean valid(String json) {
    	if ("".equals(json)) return true;
    	boolean ret = true;
    	it = new StringCharacterIterator(json);
    	c = it.first();
    	col = 1;
    	if (!value()) {
    		ret = error("value", 1);
    	} else {
    		skipWhiteSpace();
    		if (c != CharacterIterator.DONE) {
    			ret = error("end", col);
    		}
    	}
    	return ret;
    }
    
    private static boolean value() {
    	return literal("true") || literal("false") || literal("null") || string() || number() || object() || array();
    }
    
    private static boolean literal(String text) {
    	CharacterIterator ci = new StringCharacterIterator(text);
    	char t = ci.first();
    	if (c != t) return false;
    	int start = col;
    	boolean ret = true;
    	for (t = ci.next(); t != CharacterIterator.DONE; t = ci.next()) {
    		if (t != nextCharacter()) {
    			ret = false;
    			break;
    		}
    	}
    	nextCharacter();
    	if (!ret) error("literal " + text, start);
    	return ret;
    }
    
    private static boolean array() {
    	return aggregate('[', ']', false);
    }
    
    private static boolean object() {
    	return aggregate('{', '}', true);
    }
    
    private static boolean aggregate(char entryCharacter, char exitCharacter, boolean prefix) {
    	if (c != entryCharacter) return false;
    	nextCharacter();
    	skipWhiteSpace();
    	if (c == exitCharacter) {
    		nextCharacter();
    		return true;
    	}
    	for (;;) {
    		if (prefix) {
    			int start = col;
    			if (!string()) return error("string", start);
    			skipWhiteSpace();
    			if (c != ':') return error("colon", col);
    			nextCharacter();
    			skipWhiteSpace();
    		}
    		if (value()) {
    			skipWhiteSpace();
    			if (c == ',') {
    				nextCharacter();
    			} else if (c == exitCharacter) {
    				break;
    			} else {
    				return error("comma or " + exitCharacter, col);
    			}
    		}else {
    			return error("value", col);
    		}
    		skipWhiteSpace();
    	}
    	nextCharacter();
    	return true;
    }
    
    private static boolean number() {
    	if (!Character.isDigit(c) && c != '-') return false;
    		int start = col;
    		if (c == '-') nextCharacter();
    		if (c == '0') {
    		nextCharacter();
    		} else if (Character.isDigit(c)) {
    		while (Character.isDigit(c))
    		nextCharacter();
    		} else {
    		return error("number", start);
    		}
    		if (c == '.') {
	nextCharacter();
	if (Character.isDigit(c)) {
	while (Character.isDigit(c))
	nextCharacter();
	} else {
	return error("number", start);
	}
	}
	if (c == 'e' || c == 'E') {
	nextCharacter();
	if (c == '+' || c == '-') {
	nextCharacter();
	}
	if (Character.isDigit(c)) {
	 while (Character.isDigit(c))
	nextCharacter();
	} else {
	 return error("number", start);
	}
	}
	 return true;
    }

    private static boolean string() {
    	if (c != '"') return false;

    	int start = col;
    	boolean escaped = false;
    	for (nextCharacter(); c != CharacterIterator.DONE; nextCharacter()) {
    		if (!escaped && c == '\\') {
    			escaped = true;
    		} else if (escaped) {
    			if (!escape()) {
    				return false;
    			}
    			escaped = false;
    		} else if (c == '"') {
    			nextCharacter();
    			return true;
    		}
    	}
    	return error("quoted string", start);
    }


    private static boolean escape() {
    	int start = col - 1;
    	if (" \\\"/bfnrtu".indexOf(c) < 0) {
    		return error("escape sequence ?\\\",\\\\,\\/,\\b,\\f,\\n,\\r,\\t ?or ?\\uxxxx ", start);
    	}
    	if (c == 'u') {
    		if (!ishex(nextCharacter()) || !ishex(nextCharacter()) || !ishex(nextCharacter())
    				|| !ishex(nextCharacter())) {
    			return error("unicode escape sequence ?\\uxxxx ", start);
    		}
    	}
    	return true;
    }


    private static boolean ishex(char d) {
    	return "0123456789abcdefABCDEF".indexOf(c) >= 0;
    }


    private static char nextCharacter() {
    	c = it.next();
    	++col;
    	return c;
    }


    private static void skipWhiteSpace() {
    	while (Character.isWhitespace(c)) {
    		nextCharacter();
    	}
    }

    private static boolean error(String type, int col) {
    	System.out.printf("type: %s, col: %s%s", type, col, System.getProperty("line.separator"));
    	return false;
    }
    
    /**
     * 判断字符串是否可以转化为JSON数组
     * @param content
     * @return
     */
     public static boolean isJsonArray(String content) {
     	try {
 			JsonArray array = new JsonParser().parse(content).getAsJsonArray();
 			return true;
 		} catch (Exception e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
 			System.out.println(content+"非法的JSON数组");
 			return false;
 			
 		}
     }
     
     /**
      * 判断字符串是否可以转化为JSON对象
      * @param content
      * @return
      */
      public static boolean isJsonObject(String content) {
      	try {
      		JsonObject object = new JsonParser().parse(content).getAsJsonObject();
 			return true;
  		} catch (Exception e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
  			System.out.println(content+"非法的JSON字符串");
 			return false;
  		}
      }
    
}
