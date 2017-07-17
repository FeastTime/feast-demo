/**
 * 
 */
package com.feast.demo.hibernate;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author lijt
 *
 */
public class Mysql5BitBooleanDialect extends MySQL5InnoDBDialect {
	
	public Mysql5BitBooleanDialect() {
		super();
		
		registerColumnType(java.sql.Types.BOOLEAN, "bit");
		
		// mariadb dynamic column 函数支持
		// 使用方法eg: select column_get(dynamicMetrics,viewable,int) from EffectDay where id=1
		registerFunction("column_get", new SQLFunctionTemplate(
				StandardBasicTypes.STRING, "column_get(?1, '?2' as ?3)"));
		registerFunction("column_json", new SQLFunctionTemplate(
				StandardBasicTypes.STRING, "column_json(?1)"));

		// concat_ws(sep,s1,s2...,sn) 将s1,s2...,sn连接成字符串，用sep字符间隔。使用时，sep需要用引号，例如','
		registerFunction("concat_ws", new StandardSQLFunction("concat_ws",
				StandardBasicTypes.STRING));
		
		// find_in_set(str,list) 分析逗号分隔的list列表，如果发现str，返回str在list中的位置。
		registerFunction("find_in_set", new StandardSQLFunction("find_in_set",
				StandardBasicTypes.INTEGER));
		
		// substring_index(str,delim,count) 根据delim分隔字符串str，如果count大于0则返回左侧count个元素；如果count小于0，则返回右侧count个元素。
		registerFunction("substring_index", new StandardSQLFunction(
				"substring_index"));
		
		// ifnull(arg1,arg2) 如果arg1不是空，返回arg1，否则返回arg2
		registerFunction("ifnull", new StandardSQLFunction("ifnull"));
		
		// least(arg1, arg2) 取arg1和arg2中最小的值
		registerFunction("least", new StandardSQLFunction("least"));
	}
}
