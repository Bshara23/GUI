package Entities;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class SqlObject implements Serializable {

	/**
	 * Configure the data base fields types
	 */
	public static class Config {
		public static final String sqlLong = "BIGINT(8)";
		public static final String sqlInt = "INT";
		public static final String sqlDate = "DATE";
		public static final String sqlBoolean = "BOOLEAN";
		public static final String sqlString = "VARCHAR(256)";
		public static final String sqlLongText = "LONGTEXT";
		public static final String sqlDefault = "VARCHAR(45)";
		public static String schemaName = "icm";

		// Use this suffix in the field name of a class where the field should be a long
		// text
		public static String longTextSuffix = "LT";

	}

	private String tableName;

	String getForeignKeyName() {
		return foreignKeyName;
	}

	void setForeignKeyName(String foreignKeyName) {
		this.foreignKeyName = foreignKeyName;
	}

	private int primaryKeyIndex, foreignKeyIndex;
	private String primaryKeyName, foreignKeyName, referenceTable;
	private Field[] fields;
	private String[] fieldsNames;

	public SqlObject() {
		this(0, -1);
	}

	int getPrimaryKeyIndex() {
		return primaryKeyIndex;
	}

	void setPrimaryKeyIndex(int primaryKeyIndex) {
		this.primaryKeyIndex = primaryKeyIndex;
	}

	int getForeignKeyIndex() {
		return foreignKeyIndex;
	}

	void setForeignKeyIndex(int foreignKeyIndex) {
		this.foreignKeyIndex = foreignKeyIndex;
	}

	void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public SqlObject(int primaryKeyIndex, int foreignKeyIndex) {
		
		this.primaryKeyIndex = primaryKeyIndex;
		this.foreignKeyIndex = primaryKeyIndex;

		fields = this.getClass().getFields();
		this.foreignKeyName = "";
		tableName = this.getClass().getName();

		// Table name should not contain dots.
		if (tableName.contains(".")) {
			// To remove the packages names and keep the class name only,
			// Set the tableName to the substring after the last dot.
			tableName = tableName.substring(tableName.lastIndexOf('.') + 1);

		}

		primaryKeyName = this.getClass().getFields()[primaryKeyIndex].getName();
		if (foreignKeyIndex != -1)
			foreignKeyName = this.getClass().getFields()[foreignKeyIndex].getName();

		fieldsNames = calFieldsNames();
	}

	public Field[] getFields() {
		return fields;
	}

	public String getTableName() {
		return "`" + Config.schemaName + "`.`" + tableName + "`";
	}

	/**
	 * Returns the info in SQL format of this object. Note: this is not executed per
	 * object since this function is mainly used for creating a table for this
	 * object, which should happen only once.
	 */
	public String tableInfo() {

		StringBuilder sb = new StringBuilder(50);

		// Adding the table name
		sb.append(getTableName() + " ( ");

		// Adding the fields names with types

		Field[] fields = getClass().getFields();

		for (int i = 0; i < fields.length; i++) {

			String fieldName = fields[i].getName();
			sb.append("`" + fieldName + "` ");

			String fieldType = fields[i].getType().toString();

			switch (fieldType) {

			case "long":
				sb.append(Config.sqlLong);

				break;
			case "int":
				sb.append(Config.sqlInt);

				break;

			case "boolean":
				sb.append(Config.sqlBoolean);
				break;

			case "class java.sql.Date":
				sb.append(Config.sqlDate);

				break;

			case "class java.lang.String":

				// If the field name ends with this suffix then configure it as a long text
				// in the database.
				if (fieldName.endsWith(Config.longTextSuffix)) {
					sb.append(Config.sqlLongText);

				} else {
					sb.append(Config.sqlString);

				}

				break;

			default:
				sb.append(Config.sqlDefault);

				break;
			}

			sb.append(" NOT NULL, ");

		}

		// Adding the primary key

		sb.append("PRIMARY KEY (`" + getPrimaryKeyName() + "`))");

		if (foreignKeyIndex != -1) {
			sb.deleteCharAt(sb.length() - 1);
			sb.append(",FOREIGN KEY ('" + getForeignKeyName() + "') REFERENCES " + getReferenceTable() + " ('"
					+ getForeignKeyName() + "') ON DELETE CASCADE ON UPDATE CASCADE");
		}

		return sb.toString();
	}

	/**
	 * By default, the key is the first public field in the class. Override this
	 * method to change the key.
	 */
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public String getReferenceTable() {
		return referenceTable;
	}

	void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}


	/**
	 * returns the value of the primary key as an String object.
	 */
	public String getKeyValue() {
		try {
			return getClass().getFields()[primaryKeyIndex].get(this).toString();
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String[] getFieldsAndValues() {

		Field[] fields = getClass().getFields();
		String[] results = new String[fields.length * 2];

		for (int i = 0, j = 0; i < fields.length; i++, j += 2) {
			results[j] = fields[i].getName();
			try {
				results[j + 1] = fields[i].get(this).toString();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return results;
	}

	private String[] calFieldsNames() {

		Field[] fields = getClass().getFields();
		String[] results = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			results[i] = fields[i].getName();
		}
		return results;
	}

	public String[] getFieldsNames() {
		return fieldsNames;
	}

	public String[] getFieldsValues() {

		Field[] fields = getClass().getFields();
		String[] results = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			try {
				results[i] = "'" + fields[i].get(this).toString() + "'";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return results;
	}

}
