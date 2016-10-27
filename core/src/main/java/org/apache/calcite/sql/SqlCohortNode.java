package org.apache.calcite.sql;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.pretty.SqlPrettyWriter;
import org.apache.calcite.sql.util.SqlVisitor;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorScope;
import org.apache.calcite.util.Litmus;



/**
 * A <code>SqlCohortNode</code> is the parent class of all nodes of cohort query.
 */

public class SqlCohortNode extends SqlNode {
	
	//Constructor
	public SqlCohortNode() {
		super(new SqlParserPos(0, 0));
	}
	
	//Implementation of abstract methods inheritated from SqlNode
	public <R> R accept(SqlVisitor<R> visitor) {
		return null;
	}
	
	public boolean equalsDeep(SqlNode node, Litmus litmus) {
		return true;
	}
	
	public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {}
	
	public void validate(SqlValidator validator, SqlValidatorScope scope) {}
	
}
	
	