/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author CPU
 */
public final class Json {

	private void shallowClone(Json pop) {
//        System.out.println(pop.toJson());
		this.array = pop.array;
		this.isArray = pop.isArray;
		this.isObject = pop.isObject;
		this.isString = pop.isString;
		this.keys = pop.keys;
		this.string = pop.string;
	}

	public static enum Type {

		string(0),
		array(1),
		object(2);

		private int value;

		private Type(int value) {
			this.value = value;
		}
	}

	private static final String JSON__STATUS__ERROR = "Json Status Error";

	private boolean isString;
	private boolean isArray;
	private boolean isObject;

	private String string;

	private List<Json> array = new ArrayList<>();

	private List<String> keys = new ArrayList<>();

	public Json(Type type) {
		switch (type.value) {
			case 0:
				this.setString();
				break;
			case 1:
				this.setArray();
				break;
			case 2:
				this.setObject();
				break;
			default:
				throw new RuntimeException(JSON__STATUS__ERROR);
		}
	}

	public Json(Type type, String string) {
		this(type);
		this.setString(string);
	}

	/**
	 * 从一个json字符串构造一棵json树，并返回root节点的引用
	 *
	 * @param json
	 */
	public Json(String json) {
		System.out.println("Parsing>" + json);
        // 0 - 等待开头大括号或者开头中括号（对象或数组）
		// 1 - 等待开头双引号（对象的属性名）
		// 2 - 等待结束双引号，以结束字符串（对象属性名）
		// 3 - 等待分号（对象属性名和值的分隔符）
		// 4 - 等待开头大括号、开头中括号或者开头双引号（对象属性值）
		// 5 - 等待结束双引号，以结束字符串（对象属性值）
		// 6 - 等待逗号或者结束大括号（对象内另起一个属性或者对象结束）
		// 7 - 等待开头双引号、逗号或者结束中括号（对象内另起一个属性、数组内新起另一个对象或者结束数组）
		// 8 - 等待开头双引号、开头大括号或者开头中括号（数组内一切对象的开头）
		// 9 - 等待结束双引号（数组内字符串对象的结束）
		// 10 - 等待逗号或者结束中括号（数组内另起一个对象或者结束当前数组）
		int status = 0;
		Stack<Json> stack = new Stack<>();
		StringBuilder key = null, value = null;
		boolean isSpecialSlash = false;
		for (char c : json.toCharArray()) {
			switch (status) {
				case 0:
					switch (c) {
						case '{':
							stack.push(new Json(Type.object));
							status = 1;
							break;
						case '[':
							stack.push(new Json(Type.array));
							status = 8;
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				case 1:
					if ('\"' != c) {
						throw new RuntimeException(JSON__STATUS__ERROR);
					}
					key = new StringBuilder();
					status = 2;
					break;
				case 2:
					if ('\"' == c) {
						status = 3;
					} else {
						key.append(c);
					}
					break;
				case 3:
					if (':' != c) {
						throw new RuntimeException(JSON__STATUS__ERROR);
					}
					status = 4;
					break;
				case 4:
					Json peek = stack.peek();
					Json json1 = null;
					switch (c) {
						case '{':
							json1 = new Json(Type.object);
							peek.addKeyValue(key.toString(), json1);
							stack.push(json1);
							status = 1;
							break;
						case '[':
							json1 = new Json(Type.array);
							peek.addKeyValue(key.toString(), json1);
							stack.push(json1);
							status = 8;
							break;
						case '\"':
							value = new StringBuilder();
							status = 5;
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				case 5:
					if ('\"' == c) {
						if (isSpecialSlash) {
							value.append(c);
							isSpecialSlash = false;
						} else {
							Json peek2 = stack.peek();
							String strKey = key.toString();
							String strValue = value.toString();
	//                        System.out.print(peek2 + "+" + strKey + "+" + strValue);
							peek2.addKeyValue(strKey, new Json(Type.string, strValue));
							status = 6;
						}
					} else if ('\\' == c) {
						isSpecialSlash = true;
					} else {
						value.append(c);
					}
					break;
				case 6:
					switch (c) {
						case ',':
							status = 1;
							break;
						case '}':
							shallowClone(stack.pop());
							status = 7;
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				case 7:
					switch (c) {
						case ',':
							Json peek1 = stack.peek();
							if (peek1.isObject) {
								status = 1;
							} else if (peek1.isArray) {
								status = 8;
							} else {
								throw new RuntimeException(JSON__STATUS__ERROR);
							}
							break;
						case ']':
							shallowClone(stack.pop());
							break;
						case '}':
							shallowClone(stack.pop());
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				case 8:
					Json json2 = null;
					switch (c) {
						case '\"':
							value = new StringBuilder();
							status = 9;
							break;
						case '{':
							json2 = new Json(Type.object);
							stack.peek().addJson(json2);
							stack.push(json2);
							status = 1;
							break;
						case '[':
							json2 = new Json(Type.array);
							stack.peek().addJson(json2);
							stack.push(json2);
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				case 9:
					if ('\"' == c) {
						stack.peek().addJson(new Json(Type.string, value.toString()));
						status = 10;
					} else {
						value.append(c);
					}
					break;
				case 10:
					switch (c) {
						case ',':
							status = 8;
							break;
						case ']':
							shallowClone(stack.pop());
							status = 7;
							break;
						default:
							throw new RuntimeException(JSON__STATUS__ERROR);
					}
					break;
				default:
					throw new RuntimeException(JSON__STATUS__ERROR);
			}
		}
	}

	private void setString() {
		if (this.isArray || this.isObject) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		this.isString = true;
	}

	private void setArray() {
		if (this.isString || this.isObject) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		this.isArray = true;
	}

	private void setObject() {
		if (this.isString || this.isArray) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		this.isObject = true;
	}

	public void setString(String str) {
		if (!isString) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		this.string = str;
	}

	public Json addKeyValue(String key, Json value) {
		if (!isObject) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		this.keys.add(key);
		this.array.add(value);
		return this;
	}

	public Json addJson(Json arrayEle) {
		if (!isArray) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
//        if (0 == this.array.size()) {
//            this.array.add(arrayEle);
//            return;
//        }
//        Json firstEle = this.array.get(0);
//        if (!firstEle.isShallowEquelWith(arrayEle)) {
//            throw new RuntimeException("Json Status Error");
//        }
		this.array.add(arrayEle);
		return this;
	}

//    private boolean isShallowEquelWith(Json json) {
//        for (int i = 0; i < json.keys.size(); i++) {
//            if (!json.keys.get(i).equals(this.keys.get(i))) {
//                return false;
//            }
//        }
//        return true;
//    }
	public String toJson() {
		if (isString) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		return this.wrapWithJson();
	}

	private String wrapWithJson() {
		if (isString) {
			return "\"" + this.string + "\"";
		} else if (isObject) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.keys.size(); i++) {
				sb.append(",\"").append(this.keys.get(i)).append("\":").append(this.array.get(i).wrapWithJson());
			}
			return "{" + (sb.length() > 1 ? sb.substring(1) : "") + "}";
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.array.size(); i++) {
				sb.append(",").append(this.array.get(i).wrapWithJson());
			}
			return "[" + (sb.length() > 1 ? sb.substring(1) : "") + "]";
		}
	}

	public void each(Each each) {
		if (isString) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		for (int i = 0; i < array.size(); i++) {
			Json json = array.get(i);
			each.next(i, isObject ? keys.get(i) : null, json);
		}
	}

	public Json get(String key) {
		if (!isObject) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		return array.get(keys.indexOf(key));
	}

	public String string() {
		if (!isString) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		return this.string;
	}

	public List<String> strList() {
		if (isObject) {
			throw new RuntimeException(JSON__STATUS__ERROR);
		}
		List<String> strArray = new ArrayList<>();
		if (isString) {
			strArray.add(string());
		} else {
			for (int i = 0; i < array.size(); i++) {
				Json json = array.get(i);
				strArray.add(json.string());
			}
		}
		return strArray;
	}

}
