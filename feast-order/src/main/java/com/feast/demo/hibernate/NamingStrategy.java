/**
 * 
 */
package com.feast.demo.hibernate;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * hibernate命名策略，全部小写，下划线分割
 * @see: http://redmine.ipinyou.com.cn/projects/optimus/wiki/Database_Criterion
 * @author lijt
 * 
 */
public class NamingStrategy extends ImprovedNamingStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 572419927702734699L;

	/**
	 * 
	 */
	public NamingStrategy() {
	}

	private String tablePrefix = "";

	/**
	 * @param tablePrefix
	 *            要设置的 tablePrefix。
	 */
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.hibernate.cfg.NamingStrategy#classToTableName(java.lang.String)
	 */
	@Override
	public String classToTableName(String arg0) {
		return classToTableNameWithoutPlural(arg0);
//		String s = classToTableNameWithoutPlural(arg0);
//		if (s.endsWith("s") || s.endsWith("x") || s.endsWith("sh")
//				|| s.endsWith("ch") || s.endsWith("z")) {
//			return s + "es";
//		} else if (s.endsWith("ly") || s.endsWith("ry") || s.endsWith("my")
//				|| s.endsWith("ty")) {
//			return s.substring(0, s.length() - 1) + "ies";
//		} else {
//			return s + "s";
//		}
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.NamingStrategy#propertyToColumnName(java.lang.String)
	 */
	@Override
	public String propertyToColumnName(String arg0) {
		return classToTableNameWithoutPlural(arg0);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.hibernate.cfg.NamingStrategy#tableName(java.lang.String)
	 */
	@Override
	public String tableName(String arg0) {
		return tablePrefix + arg0.toLowerCase();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see org.hibernate.cfg.NamingStrategy#columnName(java.lang.String)
	 */
	@Override
	public String columnName(String arg0) {
		return arg0.toLowerCase();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#logicalCollectionTableName(java
	 * .lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String logicalCollectionTableName(String tableName,
			String ownerEntityTable, String associatedEntityTable,
			String propertyName) {
		return tablePrefix
				+ super.logicalCollectionTableName(tableName, ownerEntityTable,
						associatedEntityTable, propertyName).toLowerCase();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#collectionTableName(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String collectionTableName(String ownerEntity,
			String ownerEntityTable, String associatedEntity,
			String associatedEntityTable, String propertyName) {
		return tablePrefix
				+ super.collectionTableName(ownerEntity, ownerEntityTable,
						associatedEntity, associatedEntityTable, propertyName)
						.toLowerCase();
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#foreignKeyColumnName(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String foreignKeyColumnName(String propertyName,
			String propertyEntityName, String propertyTableName,
			String referencedColumnName) {
		return propertyToColumnName(super.foreignKeyColumnName(propertyName,
				propertyEntityName, propertyTableName, referencedColumnName));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#joinKeyColumnName(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return propertyToColumnName(super.joinKeyColumnName(joinedColumn,
				joinedTable));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#logicalCollectionColumnName(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String logicalCollectionColumnName(String columnName,
			String propertyName, String referencedColumn) {
		return propertyToColumnName(super.logicalCollectionColumnName(
				columnName, propertyName, referencedColumn));
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * org.hibernate.cfg.ImprovedNamingStrategy#logicalColumnName(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public String logicalColumnName(String columnName, String propertyName) {
		return propertyToColumnName(super.logicalColumnName(columnName,
				propertyName));
	}

	private static String classToTableNameWithoutPlural(String className) {
		int loc = className.lastIndexOf(".");
		String name = (loc < 0) ? className : className.substring(className
				.lastIndexOf(".") + 1);
		StringBuffer buf = new StringBuffer(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1))
					&& Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase();
	}

}
