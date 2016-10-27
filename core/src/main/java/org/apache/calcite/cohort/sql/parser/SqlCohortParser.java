package org.apache.calcite.cohort.sql.parser;

import org.apache.calcite.cohort.sql.parser.impl.*;
import org.apache.calcite.sql.SqlNode;

import java.io.Reader;
import java.io.StringReader;


public class SqlCohortParser {
	
	private SqlCohortParserImpl parser;
	
	public SqlCohortParser(String sql) {
		Reader reader = new StringReader(sql);
		parser = new SqlCohortParserImpl(reader); 
	}
	
	public SqlNode parseQuery() throws Exception {
		return parser.SqlStmtEof();
	}
	
	public SqlNode parseQuery(String sql) throws Exception {
		parser.ReInit(new StringReader(sql));
		return parseQuery();
	}
}
