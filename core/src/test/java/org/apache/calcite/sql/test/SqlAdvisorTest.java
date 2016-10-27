/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.sql.test;

import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.sql.advise.SqlAdvisor;
import org.apache.calcite.sql.advise.SqlAdvisorValidator;
import org.apache.calcite.sql.advise.SqlSimpleParser;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParserUtil;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlMoniker;
import org.apache.calcite.sql.validate.SqlMonikerType;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorWithHints;
import org.apache.calcite.test.MockCatalogReader;
import org.apache.calcite.test.SqlValidatorTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Concrete child class of {@link SqlValidatorTestCase}, containing unit tests
 * for SqlAdvisor.
 */
public class SqlAdvisorTest extends SqlValidatorTestCase {
  //~ Static fields/initializers ---------------------------------------------

  private static final List<String> STAR_KEYWORD =
      Arrays.asList(
          "KEYWORD(*)");

  protected static final List<String> FROM_KEYWORDS =
      Arrays.asList(
          "KEYWORD(()",
          "KEYWORD(LATERAL)",
          "KEYWORD(TABLE)",
          "KEYWORD(UNNEST)");

  protected static final List<String> SALES_TABLES =
      Arrays.asList(
          "TABLE(CATALOG.SALES.EMP)",
          "TABLE(CATALOG.SALES.EMP_20)",
          "TABLE(CATALOG.SALES.EMP_ADDRESS)",
          "TABLE(CATALOG.SALES.DEPT)",
          "TABLE(CATALOG.SALES.DEPT_NESTED)",
          "TABLE(CATALOG.SALES.BONUS)",
          "TABLE(CATALOG.SALES.ORDERS)",
          "TABLE(CATALOG.SALES.SALGRADE)",
          "TABLE(CATALOG.SALES.SHIPMENTS)",
          "TABLE(CATALOG.SALES.PRODUCTS)",
          "TABLE(CATALOG.SALES.SUPPLIERS)");

  private static final List<String> SCHEMAS =
      Arrays.asList(
          "CATALOG(CATALOG)",
          "SCHEMA(CATALOG.SALES)",
          "SCHEMA(CATALOG.CUSTOMER)");

  private static final List<String> AB_TABLES =
      Arrays.asList(
          "TABLE(A)",
          "TABLE(B)");

  private static final List<String> EMP_TABLE =
      Arrays.asList(
          "TABLE(EMP)");

  protected static final List<String> FETCH_OFFSET =
      Arrays.asList(
          "KEYWORD(FETCH)",
          "KEYWORD(LIMIT)",
          "KEYWORD(OFFSET)");

  protected static final List<String> EXPR_KEYWORDS =
      Arrays.asList(
          "KEYWORD(()",
          "KEYWORD(+)",
          "KEYWORD(-)",
          "KEYWORD(?)",
          "KEYWORD(ABS)",
          "KEYWORD(ARRAY)",
          "KEYWORD(AVG)",
          "KEYWORD(CARDINALITY)",
          "KEYWORD(CASE)",
          "KEYWORD(CAST)",
          "KEYWORD(CEIL)",
          "KEYWORD(CEILING)",
          "KEYWORD(CHARACTER_LENGTH)",
          "KEYWORD(CHAR_LENGTH)",
          "KEYWORD(COALESCE)",
          "KEYWORD(COLLECT)",
          "KEYWORD(CONVERT)",
          "KEYWORD(COUNT)",
          "KEYWORD(COVAR_POP)",
          "KEYWORD(COVAR_SAMP)",
          "KEYWORD(CUME_DIST)",
          "KEYWORD(CURRENT)",
          "KEYWORD(CURRENT_CATALOG)",
          "KEYWORD(CURRENT_DATE)",
          "KEYWORD(CURRENT_DEFAULT_TRANSFORM_GROUP)",
          "KEYWORD(CURRENT_PATH)",
          "KEYWORD(CURRENT_ROLE)",
          "KEYWORD(CURRENT_SCHEMA)",
          "KEYWORD(CURRENT_TIME)",
          "KEYWORD(CURRENT_TIMESTAMP)",
          "KEYWORD(CURRENT_USER)",
          "KEYWORD(CURSOR)",
          "KEYWORD(DATE)",
          "KEYWORD(DENSE_RANK)",
          "KEYWORD(ELEMENT)",
          "KEYWORD(EXISTS)",
          "KEYWORD(EXP)",
          "KEYWORD(EXTRACT)",
          "KEYWORD(FALSE)",
          "KEYWORD(FIRST_VALUE)",
          "KEYWORD(FLOOR)",
          "KEYWORD(FUSION)",
          "KEYWORD(GROUPING)",
          "KEYWORD(INTERVAL)",
          "KEYWORD(LAST_VALUE)",
          "KEYWORD(LN)",
          "KEYWORD(LOCALTIME)",
          "KEYWORD(LOCALTIMESTAMP)",
          "KEYWORD(LOWER)",
          "KEYWORD(MAX)",
          "KEYWORD(MIN)",
          "KEYWORD(MOD)",
          "KEYWORD(MULTISET)",
          "KEYWORD(NEW)",
          "KEYWORD(NEXT)",
          "KEYWORD(NOT)",
          "KEYWORD(NULL)",
          "KEYWORD(NULLIF)",
          "KEYWORD(OCTET_LENGTH)",
          "KEYWORD(OVERLAY)",
          "KEYWORD(PERCENT_RANK)",
          "KEYWORD(POSITION)",
          "KEYWORD(POWER)",
          "KEYWORD(RANK)",
          "KEYWORD(REGR_SXX)",
          "KEYWORD(REGR_SYY)",
          "KEYWORD(ROW)",
          "KEYWORD(ROW_NUMBER)",
          "KEYWORD(SESSION_USER)",
          "KEYWORD(SPECIFIC)",
          "KEYWORD(SQRT)",
          "KEYWORD(SUBSTRING)",
          "KEYWORD(STDDEV_POP)",
          "KEYWORD(STDDEV_SAMP)",
          "KEYWORD(SUM)",
          "KEYWORD(SYSTEM_USER)",
          "KEYWORD(TIME)",
          "KEYWORD(TIMESTAMP)",
          "KEYWORD(TRANSLATE)",
          "KEYWORD(TRIM)",
          "KEYWORD(TRUE)",
          "KEYWORD(UNKNOWN)",
          "KEYWORD(UPPER)",
          "KEYWORD(USER)",
          "KEYWORD(VAR_POP)",
          "KEYWORD(VAR_SAMP)");

  protected static final List<String> SELECT_KEYWORDS =
      Arrays.asList(
          "KEYWORD(ALL)",
          "KEYWORD(DISTINCT)",
          "KEYWORD(STREAM)",
          "KEYWORD(*)");

  private static final List<String> ORDER_KEYWORDS =
      Arrays.asList(
          "KEYWORD(,)",
          "KEYWORD(ASC)",
          "KEYWORD(DESC)",
          "KEYWORD(NULLS)");

  private static final List<String> EMP_COLUMNS =
      Arrays.asList(
          "COLUMN(EMPNO)",
          "COLUMN(ENAME)",
          "COLUMN(JOB)",
          "COLUMN(MGR)",
          "COLUMN(HIREDATE)",
          "COLUMN(SAL)",
          "COLUMN(COMM)",
          "COLUMN(DEPTNO)",
          "COLUMN(SLACKER)");

  private static final List<String> DEPT_COLUMNS =
      Arrays.asList(
          "COLUMN(DEPTNO)",
          "COLUMN(NAME)");

  protected static final List<String> PREDICATE_KEYWORDS =
      Arrays.asList(
          "KEYWORD(()",
          "KEYWORD(*)",
          "KEYWORD(+)",
          "KEYWORD(-)",
          "KEYWORD(.)",
          "KEYWORD(/)",
          "KEYWORD(<)",
          "KEYWORD(<=)",
          "KEYWORD(<>)",
          "KEYWORD(=)",
          "KEYWORD(>)",
          "KEYWORD(>=)",
          "KEYWORD(AND)",
          "KEYWORD(BETWEEN)",
          "KEYWORD(IN)",
          "KEYWORD(IS)",
          "KEYWORD(LIKE)",
          "KEYWORD(MEMBER)",
          "KEYWORD(MULTISET)",
          "KEYWORD(NOT)",
          "KEYWORD(OR)",
          "KEYWORD(SIMILAR)",
          "KEYWORD(SUBMULTISET)",
          "KEYWORD([)",
          "KEYWORD(||)");

  private static final List<String> WHERE_KEYWORDS =
      Arrays.asList(
          "KEYWORD(EXCEPT)",
          "KEYWORD(FETCH)",
          "KEYWORD(OFFSET)",
          "KEYWORD(LIMIT)",
          "KEYWORD(GROUP)",
          "KEYWORD(HAVING)",
          "KEYWORD(INTERSECT)",
          "KEYWORD(ORDER)",
          "KEYWORD(UNION)",
          "KEYWORD(WINDOW)");

  private static final List<String> A_TABLE =
      Arrays.asList(
          "TABLE(A)");

  protected static final List<String> JOIN_KEYWORDS =
      Arrays.asList(
          "KEYWORD(FETCH)",
          "KEYWORD(OFFSET)",
          "KEYWORD(LIMIT)",
          "KEYWORD(UNION)",
          "KEYWORD(FULL)",
          "KEYWORD(ORDER)",
          "KEYWORD(()",
          "KEYWORD(EXTEND)",
          "KEYWORD(AS)",
          "KEYWORD(USING)",
          "KEYWORD(RIGHT)",
          "KEYWORD(GROUP)",
          "KEYWORD(CROSS)",
          "KEYWORD(,)",
          "KEYWORD(NATURAL)",
          "KEYWORD(INNER)",
          "KEYWORD(HAVING)",
          "KEYWORD(LEFT)",
          "KEYWORD(EXCEPT)",
          "KEYWORD(JOIN)",
          "KEYWORD(WINDOW)",
          "KEYWORD(.)",
          "KEYWORD(TABLESAMPLE)",
          "KEYWORD(ON)",
          "KEYWORD(INTERSECT)",
          "KEYWORD(WHERE)");

  private static final List<String> SETOPS =
      Arrays.asList(
          "KEYWORD(EXCEPT)",
          "KEYWORD(INTERSECT)",
          "KEYWORD(ORDER)",
          "KEYWORD(UNION)");

  private static final String EMPNO_EMP =
      "COLUMN(EMPNO)\n"
          + "TABLE(EMP)\n";

  //~ Constructors -----------------------------------------------------------

  public SqlAdvisorTest() {
    super();
  }

  //~ Methods ----------------------------------------------------------------

  protected List<String> getFromKeywords() {
    return FROM_KEYWORDS;
  }

  protected List<String> getSelectKeywords() {
    return SELECT_KEYWORDS;
  }

  /**
   * Returns a list of the tables in the SALES schema. Derived classes with
   * extended SALES schemas may override.
   *
   * @return list of tables in the SALES schema
   */
  protected List<String> getSalesTables() {
    return SALES_TABLES;
  }

  protected List<String> getJoinKeywords() {
    return JOIN_KEYWORDS;
  }

  private void assertTokenizesTo(String sql, String expected) {
    SqlSimpleParser.Tokenizer tokenizer =
        new SqlSimpleParser.Tokenizer(sql, "xxxxx");
    StringBuilder buf = new StringBuilder();
    while (true) {
      SqlSimpleParser.Token token = tokenizer.nextToken();
      if (token == null) {
        break;
      }
      buf.append(token).append("\n");
    }
    Assert.assertEquals(expected, buf.toString());
  }

  protected void assertHint(
      String sql,
      List<String>... expectedLists) throws Exception {
    List<String> expectedList = plus(expectedLists);
    Collections.sort(expectedList);
    assertHint(sql, toString(expectedList));
  }

  /**
   * Checks that a given SQL statement yields the expected set of completion
   * hints.
   *
   * @param sql             SQL statement
   * @param expectedResults Expected list of hints
   * @throws Exception on error
   */
  protected void assertHint(
      String sql,
      String expectedResults) throws Exception {
    SqlValidatorWithHints validator =
        (SqlValidatorWithHints) tester.getValidator();
    SqlAdvisor advisor = tester.getFactory().createAdvisor(validator);

    SqlParserUtil.StringAndPos sap = SqlParserUtil.findPos(sql);

    List<SqlMoniker> results =
        advisor.getCompletionHints(
            sap.sql,
            sap.pos);
    Assert.assertEquals(
        expectedResults, convertCompletionHints(results));
  }

  /**
   * Tests that a given SQL statement simplifies to the salesTables result.
   *
   * @param sql      SQL statement to simplify. The SQL statement must contain
   *                 precisely one caret '^', which marks the location where
   *                 completion is to occur.
   * @param expected Expected result after simplification.
   */
  protected void assertSimplify(String sql, String expected) {
    SqlValidatorWithHints validator =
        (SqlValidatorWithHints) tester.getValidator();
    SqlAdvisor advisor = tester.getFactory().createAdvisor(validator);

    SqlParserUtil.StringAndPos sap = SqlParserUtil.findPos(sql);
    String actual = advisor.simplifySql(sap.sql, sap.cursor);
    Assert.assertEquals(expected, actual);
  }

  protected void assertComplete(
      String sql,
      List<String>... expectedResults) {
    List<String> expectedList = plus(expectedResults);
    Collections.sort(expectedList);
    String expected = toString(expectedList);
    assertComplete(sql, expected, null);
  }

  /**
   * Tests that a given SQL which may be invalid or incomplete simplifies
   * itself and yields the salesTables set of completion hints. This is an
   * integration test of {@link #assertHint} and {@link #assertSimplify}.
   *
   * @param sql             SQL statement
   * @param expectedResults Expected list of hints
   * @param expectedWord    Word that we expect to be replaced, or null if we
   *                        don't care
   */
  protected void assertComplete(
      String sql,
      String expectedResults,
      String expectedWord) {
    SqlValidatorWithHints validator =
        (SqlValidatorWithHints) tester.getValidator();
    SqlAdvisor advisor = tester.getFactory().createAdvisor(validator);

    SqlParserUtil.StringAndPos sap = SqlParserUtil.findPos(sql);
    final String[] replaced = {null};
    List<SqlMoniker> results =
        advisor.getCompletionHints(sap.sql, sap.cursor, replaced);
    assertNotNull(replaced[0]);
    assertNotNull(results);
    Assert.assertEquals(
        expectedResults, convertCompletionHints(results));
    if (expectedWord != null) {
      Assert.assertEquals(expectedWord, replaced[0]);
    }
  }

  protected void assertEquals(
      String[] actualResults,
      List<String>... expectedResults) throws Exception {
    List<String> expectedList = plus(expectedResults);
    Map<String, String> uniqueResults = new HashMap<String, String>();
    for (String actualResult : actualResults) {
      uniqueResults.put(actualResult, actualResult);
    }
    if (!(expectedList.containsAll(uniqueResults.values())
        && (expectedList.size() == uniqueResults.values().size()))) {
      fail(
          "SqlAdvisorTest: completion hints results not as salesTables:\n"
              + uniqueResults.values() + "\nExpected:\n"
              + expectedList);
    }
  }

  private String convertCompletionHints(List<SqlMoniker> hints) {
    List<String> list = new ArrayList<String>();
    for (SqlMoniker hint : hints) {
      if (hint.getType() != SqlMonikerType.FUNCTION) {
        list.add(hint.id());
      }
    }
    Collections.sort(list);
    return toString(list);
  }

  /**
   * Converts a list to a string, one item per line.
   *
   * @param list List
   * @return String with one item of the list per line
   */
  private static <T> String toString(List<T> list) {
    StringBuilder buf = new StringBuilder();
    for (T t : list) {
      buf.append(t).append("\n");
    }
    return buf.toString();
  }

  @Override public SqlTester getTester() {
    return new SqlTesterImpl(new AdvisorTesterFactory());
  }

  /**
   * Concatenates several lists of the same type into a single list.
   *
   * @param lists Lists to concatenate
   * @return Sum list
   */
  protected static <T> List<T> plus(List<T>... lists) {
    final List<T> result = new ArrayList<T>();
    for (List<T> list : lists) {
      result.addAll(list);
    }
    return result;
  }

  @Test public void testFrom() throws Exception {
    String sql;

    sql = "select a.empno, b.deptno from ^dummy a, sales.dummy b";
    assertHint(sql, SCHEMAS, getSalesTables(), getFromKeywords()); // join

    sql = "select a.empno, b.deptno from ^";
    assertComplete(sql, SCHEMAS, getSalesTables(), getFromKeywords());
    sql = "select a.empno, b.deptno from ^, sales.dummy b";
    assertComplete(sql, SCHEMAS, getSalesTables(), getFromKeywords());
    sql = "select a.empno, b.deptno from ^a";
    assertComplete(sql, SCHEMAS, getSalesTables(), getFromKeywords());

    sql = "select a.empno, b.deptno from dummy a, ^sales.dummy b";
    assertHint(sql, SCHEMAS, getSalesTables(), getFromKeywords()); // join
  }

  @Test public void testFromComplete() {
    String sql = "select a.empno, b.deptno from dummy a, sales.^";
    assertComplete(sql, getSalesTables());
  }

  @Test public void testGroup() {
    // This test is hard because the statement is not valid if you replace
    // '^' with a dummy identifier.
    String sql = "select a.empno, b.deptno from emp group ^";
    assertComplete(sql, Arrays.asList("KEYWORD(BY)"));
  }

  @Test public void testJoin() throws Exception {
    String sql;

    // from
    sql =
        "select a.empno, b.deptno from ^dummy a join sales.dummy b "
            + "on a.deptno=b.deptno where empno=1";
    assertHint(sql, getFromKeywords(), SCHEMAS, getSalesTables());

    // from
    sql = "select a.empno, b.deptno from ^ a join sales.dummy b";
    assertComplete(sql, getFromKeywords(), SCHEMAS, getSalesTables());

    // REVIEW: because caret is before 'sales', should it ignore schema
    // name and present all schemas and all tables in the default schema?
    // join
    sql =
        "select a.empno, b.deptno from dummy a join ^sales.dummy b "
            + "on a.deptno=b.deptno where empno=1";
    assertHint(sql, getFromKeywords(), SCHEMAS, getSalesTables());

    sql = "select a.empno, b.deptno from dummy a join sales.^";
    assertComplete(sql, getSalesTables()); // join
    sql = "select a.empno, b.deptno from dummy a join sales.^ on";
    assertComplete(sql, getSalesTables()); // join

    // unfortunately cannot complete this case: syntax is too broken
    sql = "select a.empno, b.deptno from dummy a join sales.^ on a.deptno=";
    assertComplete(sql, EXPR_KEYWORDS); // join
  }

  @Test public void testJoinKeywords() {
    // variety of keywords possible
    List<String> list = getJoinKeywords();
    String sql = "select * from dummy join sales.emp ^";
    assertSimplify(sql, "SELECT * FROM dummy JOIN sales.emp _suggest_");
    assertComplete(sql, list);
  }

  @Test public void testOnCondition() throws Exception {
    String sql;

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on ^a.deptno=b.dummy where empno=1";
    assertHint(sql, AB_TABLES, EXPR_KEYWORDS); // on left

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.^";
    assertComplete(sql, EMP_COLUMNS); // on left

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=^b.dummy where empno=1";
    assertHint(sql, EXPR_KEYWORDS, AB_TABLES); // on right

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.^ where empno=1";
    assertComplete(sql, DEPT_COLUMNS); // on right

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.^";
    assertComplete(sql, DEPT_COLUMNS); // on right
  }

  @Test public void testFromWhere() throws Exception {
    String sql;

    sql =
        "select a.empno, b.deptno from sales.emp a, sales.dept b "
            + "where b.deptno=^a.dummy";
    assertHint(sql, AB_TABLES, EXPR_KEYWORDS); // where list

    sql =
        "select a.empno, b.deptno from sales.emp a, sales.dept b "
            + "where b.deptno=a.^";
    assertComplete(sql, EMP_COLUMNS); // where list

    // hints contain no columns, only table aliases, because there are >1
    // aliases
    sql =
        "select a.empno, b.deptno from sales.emp a, sales.dept b "
            + "where ^dummy=1";
    assertHint(sql, AB_TABLES, EXPR_KEYWORDS); // where list

    sql =
        "select a.empno, b.deptno from sales.emp a, sales.dept b "
            + "where ^";
    assertComplete(sql, AB_TABLES, EXPR_KEYWORDS); // where list

    // If there's only one table alias, we allow both the alias and the
    // unqualified columns
    assertComplete(
        "select a.empno, a.deptno from sales.emp a "
            + "where ^",
        A_TABLE,
        EMP_COLUMNS,
        EXPR_KEYWORDS);
  }

  @Test public void testWhereList() throws Exception {
    String sql;

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where ^dummy=1";
    assertHint(sql, EXPR_KEYWORDS, AB_TABLES); // where list

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where ^";
    assertComplete(sql, EXPR_KEYWORDS, AB_TABLES); // where list

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where ^a.dummy=1";
    assertHint(sql, EXPR_KEYWORDS, AB_TABLES); // where list

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where a.^";
    assertComplete(sql, EMP_COLUMNS);

    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where a.empno ^ ";
    assertComplete(sql, PREDICATE_KEYWORDS, WHERE_KEYWORDS);
  }

  @Test public void testSelectList() throws Exception {
    String sql;

    sql =
        "select ^dummy, b.dummy from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where empno=1";
    assertHint(
        sql, getSelectKeywords(), EXPR_KEYWORDS, AB_TABLES, SETOPS,
        FETCH_OFFSET);

    sql = "select ^ from (values (1))";
    assertComplete(
        sql,
        getSelectKeywords(),
        EXPR_KEYWORDS,
        SETOPS,
        FETCH_OFFSET,
        Arrays.asList("TABLE(EXPR$0)", "COLUMN(EXPR$0)"));

    sql = "select ^ from (values (1)) as t(c)";
    assertComplete(
        sql,
        getSelectKeywords(),
        EXPR_KEYWORDS,
        SETOPS,
        FETCH_OFFSET,
        Arrays.asList("TABLE(T)", "COLUMN(C)"));

    sql = "select ^, b.dummy from sales.emp a join sales.dept b ";
    assertComplete(
        sql, getSelectKeywords(), EXPR_KEYWORDS, SETOPS, AB_TABLES,
        FETCH_OFFSET);

    sql =
        "select dummy, ^b.dummy from sales.emp a join sales.dept b "
            + "on a.deptno=b.deptno where empno=1";
    assertHint(sql, EXPR_KEYWORDS, STAR_KEYWORD, AB_TABLES);

    sql = "select dummy, b.^ from sales.emp a join sales.dept b on true";
    assertComplete(sql, STAR_KEYWORD, DEPT_COLUMNS);

    // REVIEW: Since 'b' is not a valid alias, should it suggest anything?
    // We don't get through validation, so the only suggestion, '*', comes
    // from the parser.
    sql = "select dummy, b.^ from sales.emp a";
    assertComplete(sql, STAR_KEYWORD);

    sql = "select ^emp.dummy from sales.emp";
    assertHint(
        sql,
        getSelectKeywords(),
        EXPR_KEYWORDS,
        EMP_COLUMNS,
        SETOPS,
        FETCH_OFFSET,
        Arrays.asList("TABLE(EMP)"));

    sql = "select emp.^ from sales.emp";
    assertComplete(sql, EMP_COLUMNS, STAR_KEYWORD);
  }

  @Test public void testOrderByList() throws Exception {
    String sql;

    sql = "select emp.empno from sales.emp where empno=1 order by ^dummy";
    assertHint(sql, EXPR_KEYWORDS, EMP_COLUMNS, EMP_TABLE);

    sql = "select emp.empno from sales.emp where empno=1 order by ^";
    assertComplete(sql, EXPR_KEYWORDS, EMP_COLUMNS, EMP_TABLE);

    sql =
        "select emp.empno\n"
            + "from sales.emp as e(\n"
            + "  mpno,name,ob,gr,iredate,al,omm,eptno,lacker)\n"
            + "where e.mpno=1 order by ^";
    assertComplete(
        sql,
        EXPR_KEYWORDS,
        Arrays.asList(
            "COLUMN(MPNO)",
            "COLUMN(NAME)",
            "COLUMN(OB)",
            "COLUMN(GR)",
            "COLUMN(IREDATE)",
            "COLUMN(AL)",
            "COLUMN(OMM)",
            "COLUMN(EPTNO)",
            "COLUMN(LACKER)"),
        Arrays.asList(
            "TABLE(E)"));

    sql =
        "select emp.empno from sales.emp where empno=1 order by empno ^, deptno";
    assertComplete(sql, PREDICATE_KEYWORDS, ORDER_KEYWORDS, FETCH_OFFSET);
  }

  @Test public void testSubQuery() throws Exception {
    String sql;
    final List<String> xyColumns =
        Arrays.asList(
            "COLUMN(X)",
            "COLUMN(Y)");
    final List<String> tTable =
        Arrays.asList(
            "TABLE(T)");

    sql =
        "select ^t.dummy from (select 1 as x, 2 as y from sales.emp) as t where t.dummy=1";
    assertHint(
        sql, EXPR_KEYWORDS, getSelectKeywords(), xyColumns, tTable, SETOPS,
        FETCH_OFFSET);

    sql = "select t.^ from (select 1 as x, 2 as y from sales.emp) as t";
    assertComplete(sql, xyColumns, STAR_KEYWORD);

    sql =
        "select t.x from (select 1 as x, 2 as y from sales.emp) as t where ^t.dummy=1";
    assertHint(sql, EXPR_KEYWORDS, tTable, xyColumns);

    sql =
        "select t.x from (select 1 as x, 2 as y from sales.emp) as t where t.^";
    assertComplete(sql, xyColumns);

    sql =
        "select t.x from (select 1 as x, 2 as y from sales.emp) as t where ^";
    assertComplete(sql, EXPR_KEYWORDS, tTable, xyColumns);

    // with extra from item, aliases are ambiguous, so columns are not
    // offered
    sql =
        "select a.x from (select 1 as x, 2 as y from sales.emp) as a, dept as b where ^";
    assertComplete(sql, EXPR_KEYWORDS, AB_TABLES);

    // note that we get hints even though there's a syntax error in
    // select clause ('t.')
    sql =
        "select t. from (select 1 as x, 2 as y from (select x from sales.emp)) as t where ^";
    String simplified =
        "SELECT * FROM ( SELECT 0 AS x , 0 AS y FROM ( SELECT 0 AS x FROM sales.emp ) ) as t WHERE _suggest_";
    assertSimplify(sql, simplified);
    assertComplete(sql, EXPR_KEYWORDS, tTable, xyColumns);

    sql = "select t.x from (select 1 as x, 2 as y from sales.^) as t";
    assertComplete(sql, getSalesTables());
  }

  @Test public void testSubQueryInWhere() {
    String sql;

    // Aliases from enclosing subqueries are inherited: hence A from
    // enclosing, B from same scope.
    // The raw columns from dept are suggested (because they can
    // be used unqualified in the inner scope) but the raw
    // columns from emp are not (because they would need to be qualified
    // with A).
    sql =
        "select * from sales.emp a where deptno in ("
            + "select * from sales.dept b where ^)";
    String simplifiedSql =
        "SELECT * FROM sales.emp a WHERE deptno in ("
            + " SELECT * FROM sales.dept b WHERE _suggest_ )";
    assertSimplify(sql, simplifiedSql);
    assertComplete(
        sql,
        AB_TABLES,
        DEPT_COLUMNS,
        EXPR_KEYWORDS);
  }

  @Test public void testSimpleParserTokenizer() {
    String sql =
        "select"
            + " 12"
            + " "
            + "*"
            + " 1.23e45"
            + " "
            + "("
            + "\"an id\""
            + ","
            + " "
            + "\"an id with \"\"quotes' inside\""
            + ","
            + " "
            + "/* a comment, with 'quotes', over\nmultiple lines\nand select keyword */"
            + "\n "
            + "("
            + " "
            + "a"
            + " "
            + "different"
            + " "
            + "// comment\n\r"
            + "//and a comment /* containing comment */ and then some more\r"
            + ")"
            + " "
            + "from"
            + " "
            + "t"
            + ")"
            + ")"
            + "/* a comment after close paren */"
            + " "
            + "("
            + "'quoted'"
            + " "
            + "'string with ''single and \"double\"\" quote'"
            + ")";
    String expected =
        "SELECT\n"
            + "ID(12)\n"
            + "ID(*)\n"
            + "ID(1.23e45)\n"
            + "LPAREN\n"
            + "DQID(\"an id\")\n"
            + "COMMA\n"
            + "DQID(\"an id with \"\"quotes' inside\")\n"
            + "COMMA\n"
            + "COMMENT\n"
            + "LPAREN\n"
            + "ID(a)\n"
            + "ID(different)\n"
            + "COMMENT\n"
            + "COMMENT\n"
            + "RPAREN\n"
            + "FROM\n"
            + "ID(t)\n"
            + "RPAREN\n"
            + "RPAREN\n"
            + "COMMENT\n"
            + "LPAREN\n"
            + "SQID('quoted')\n"
            + "SQID('string with ''single and \"double\"\" quote')\n"
            + "RPAREN\n";
    assertTokenizesTo(sql, expected);

    // Tokenizer should be lenient if input ends mid-token
    assertTokenizesTo("select /* unfinished comment", "SELECT\nCOMMENT\n");
    assertTokenizesTo("select // unfinished comment", "SELECT\nCOMMENT\n");
    assertTokenizesTo(
        "'starts with string'",
        "SQID('starts with string')\n");
    assertTokenizesTo("'unfinished string", "SQID('unfinished string)\n");
    assertTokenizesTo(
        "\"unfinished double-quoted id",
        "DQID(\"unfinished double-quoted id)\n");
    assertTokenizesTo("123", "ID(123)\n");
  }

  @Test public void testSimpleParser() {
    String sql;
    String expected;

    // from
    sql = "select * from ^where";
    expected = "SELECT * FROM _suggest_";
    assertSimplify(sql, expected);

    // from
    sql = "select a.empno, b.deptno from ^";
    expected = "SELECT * FROM _suggest_";
    assertSimplify(sql, expected);

    // select list
    sql = "select ^ from (values (1))";
    expected = "SELECT _suggest_ FROM ( values ( 1 ) )";
    assertSimplify(sql, expected);

    sql = "select emp.^ from sales.emp";
    expected = "SELECT emp. _suggest_ FROM sales.emp";
    assertSimplify(sql, expected);

    sql = "select ^from sales.emp";
    expected = "SELECT _suggest_ FROM sales.emp";
    assertSimplify(sql, expected);

    // remove other expressions in select clause
    sql = "select a.empno ,^  from sales.emp a , sales.dept b";
    expected = "SELECT _suggest_ FROM sales.emp a , sales.dept b";
    assertSimplify(sql, expected);

    sql = "select ^, a.empno from sales.emp a , sales.dept b";
    expected = "SELECT _suggest_ FROM sales.emp a , sales.dept b";
    assertSimplify(sql, expected);

    sql = "select dummy, b.^ from sales.emp a , sales.dept b";
    expected = "SELECT b. _suggest_ FROM sales.emp a , sales.dept b";
    assertSimplify(sql, expected);

    // join
    sql = "select a.empno, b.deptno from dummy a join ^on where empno=1";
    expected = "SELECT * FROM dummy a JOIN _suggest_ ON TRUE";
    assertSimplify(sql, expected);

    // join
    sql =
        "select a.empno, b.deptno from dummy a join sales.^ where empno=1";
    expected = "SELECT * FROM dummy a JOIN sales. _suggest_";
    assertSimplify(sql, expected);

    // on
    sql =
        "select a.empno, b.deptno from sales.emp a join sales.dept b "
            + "on a.deptno=^";
    expected =
        "SELECT * FROM sales.emp a JOIN sales.dept b "
            + "ON a.deptno= _suggest_";
    assertSimplify(sql, expected);

    // where
    sql =
        "select a.empno, b.deptno from sales.emp a, sales.dept b "
            + "where ^";
    expected = "SELECT * FROM sales.emp a , sales.dept b WHERE _suggest_";
    assertSimplify(sql, expected);

    // order by
    sql = "select emp.empno from sales.emp where empno=1 order by ^";
    expected = "SELECT emp.empno FROM sales.emp ORDER BY _suggest_";
    assertSimplify(sql, expected);

    // subquery in from
    sql =
        "select t.^ from (select 1 as x, 2 as y from sales.emp) as t "
            + "where t.dummy=1";
    expected =
        "SELECT t. _suggest_ "
            + "FROM ( SELECT 0 AS x , 0 AS y FROM sales.emp ) as t";
    assertSimplify(sql, expected);

    sql =
        "select t. from (select 1 as x, 2 as y from "
            + "(select x from sales.emp)) as t where ^";
    expected =
        "SELECT * FROM ( SELECT 0 AS x , 0 AS y FROM "
            + "( SELECT 0 AS x FROM sales.emp ) ) as t WHERE _suggest_";
    assertSimplify(sql, expected);

    sql =
        "select ^from (select 1 as x, 2 as y from sales.emp), "
            + "(select 2 as y from (select m from n where)) as t "
            + "where t.dummy=1";
    expected =
        "SELECT _suggest_ FROM ( SELECT 0 AS x , 0 AS y FROM sales.emp ) "
            + ", ( SELECT 0 AS y FROM ( SELECT 0 AS m FROM n ) ) as t";
    assertSimplify(sql, expected);

    // Note: completes the missing close paren; wipes out select clause of
    // both outer and inner queries since not relevant.
    sql = "select t.x from ( select 1 as x, 2 as y from sales.^";
    expected = "SELECT * FROM ( SELECT * FROM sales. _suggest_ )";
    assertSimplify(sql, expected);

    sql = "select t.^ from (select 1 as x, 2 as y from sales)";
    expected =
        "SELECT t. _suggest_ FROM ( SELECT 0 AS x , 0 AS y FROM sales )";
    assertSimplify(sql, expected);

    // subquery in where; note that:
    // 1. removes the SELECT clause of subquery in WHERE clause;
    // 2. keeps SELECT clause of subquery in FROM clause;
    // 3. removes GROUP BY clause of subquery in FROM clause;
    // 4. removes SELECT clause of outer query.
    sql =
        "select x + y + 32 from "
            + "(select 1 as x, 2 as y from sales group by invalid stuff) as t "
            + "where x in (select deptno from emp where foo + t.^ < 10)";
    expected =
        "SELECT * FROM ( SELECT 0 AS x , 0 AS y FROM sales ) as t "
            + "WHERE x in ( SELECT * FROM emp WHERE foo + t. _suggest_ < 10 )";
    assertSimplify(sql, expected);

    // if hint is in FROM, can remove other members of FROM clause
    sql = "select a.empno, b.deptno from dummy a, sales.^";
    expected = "SELECT * FROM sales. _suggest_";
    assertSimplify(sql, expected);

    // function
    sql = "select count(1) from sales.emp a where ^";
    expected = "SELECT * FROM sales.emp a WHERE _suggest_";
    assertSimplify(sql, expected);

    sql =
        "select count(1) from sales.emp a "
            + "where substring(a.^ FROM 3 for 6) = '1234'";
    expected =
        "SELECT * FROM sales.emp a "
            + "WHERE substring ( a. _suggest_ FROM 3 for 6 ) = '1234'";
    assertSimplify(sql, expected);

    // missing ')' following subquery
    sql =
        "select * from sales.emp a where deptno in ("
            + "select * from sales.dept b where ^";
    expected =
        "SELECT * FROM sales.emp a WHERE deptno in ("
            + " SELECT * FROM sales.dept b WHERE _suggest_ )";
    assertSimplify(sql, expected);

    // keyword embedded in single and double quoted string should be
    // ignored
    sql =
        "select 'a cat from a king' as foobar, 1 / 2 \"where\" from t "
            + "group by t.^ order by 123";
    expected = "SELECT * FROM t GROUP BY t. _suggest_";
    assertSimplify(sql, expected);

    // skip comments
    sql =
        "select /* here is from */ 'cat' as foobar, 1 as x from t group by t.^ order by 123";
    expected = "SELECT * FROM t GROUP BY t. _suggest_";
    assertSimplify(sql, expected);

    // skip comments
    sql =
        "select // here is from clause\n 'cat' as foobar, 1 as x from t group by t.^ order by 123";
    expected = "SELECT * FROM t GROUP BY t. _suggest_";
    assertSimplify(sql, expected);
  }

  @Test public void testSimpleParserQuotedId() {
    String sql;
    String expected;

    // unclosed double-quote
    sql = "select * from t where \"^";
    expected = "SELECT * FROM t WHERE _suggest_";
    assertSimplify(sql, expected);

    // closed double-quote
    sql = "select * from t where \"^\" and x = y";
    expected = "SELECT * FROM t WHERE _suggest_ and x = y";
    assertSimplify(sql, expected);

    // closed double-quote containing extra stuff
    sql = "select * from t where \"^foo\" and x = y";
    expected = "SELECT * FROM t WHERE _suggest_ and x = y";
    assertSimplify(sql, expected);
  }

  @Test public void testPartialIdentifier() {
    String sql = "select * from emp where e^ and emp.deptno = 10";
    final String expected =
        "COLUMN(EMPNO)\n"
            + "COLUMN(ENAME)\n"
            + "KEYWORD(ELEMENT)\n"
            + "KEYWORD(EXISTS)\n"
            + "KEYWORD(EXP)\n"
            + "KEYWORD(EXTRACT)\n"
            + "TABLE(EMP)\n";
    assertComplete(sql, expected, "e");

    // cursor in middle of word and at end
    sql = "select * from emp where e^";
    assertComplete(sql, expected, null);

    // longer completion
    sql = "select * from emp where em^";
    assertComplete(sql, EMPNO_EMP, null);

    // word after punctuation
    sql = "select deptno,em^ from emp where 1+2<3+4";
    assertComplete(sql, EMPNO_EMP, null);

    // inside double-quotes, no terminating double-quote.
    // Only identifiers should be suggested (no keywords),
    // and suggestion should include double-quotes
    sql = "select deptno,\"EM^ from emp where 1+2<3+4";
    assertComplete(sql, EMPNO_EMP, "\"EM");

    // inside double-quotes, match is case-sensitive
    sql = "select deptno,\"em^ from emp where 1+2<3+4";
    assertComplete(sql, "", "\"em");

    // eat up following double-quote
    sql = "select deptno,\"EM^ps\" from emp where 1+2<3+4";
    assertComplete(sql, EMPNO_EMP, "\"EM");

    // closing double-quote is at very end of string
    sql = "select * from emp where 5 = \"EM^xxx\"";
    assertComplete(sql, EMPNO_EMP, "\"EM");

    // just before dot
    sql = "select emp.^name from emp";
    assertComplete(sql, EMP_COLUMNS, STAR_KEYWORD);
  }

  @Test public void testInsert() throws Exception {
    String sql;
    sql = "insert into emp(empno, mgr) select ^ from dept a";
    assertComplete(
        sql,
        getSelectKeywords(),
        EXPR_KEYWORDS,
        A_TABLE,
        DEPT_COLUMNS,
        SETOPS,
        FETCH_OFFSET);

    sql = "insert into emp(empno, mgr) values (123, 3 + ^)";
    assertComplete(sql, EXPR_KEYWORDS);

    // Wish we could do better here. Parser gives error 'Non-query
    // expression encountered in illegal context' and cannot suggest
    // possible tokens.
    sql = "insert into emp(empno, mgr) ^";
    assertComplete(sql, "", null);
  }

  @Test public void testUnion() throws Exception {
    // we simplify set ops such as UNION by removing other queries -
    // thereby avoiding validation errors due to mismatched select lists
    String sql =
        "select 1 from emp union select 2 from dept a where ^ and deptno < 5";
    String simplified =
        "SELECT * FROM dept a WHERE _suggest_ and deptno < 5";
    assertSimplify(sql, simplified);
    assertComplete(sql, EXPR_KEYWORDS, A_TABLE, DEPT_COLUMNS);

    // UNION ALL
    sql =
        "select 1 from emp union all select 2 from dept a where ^ and deptno < 5";
    assertSimplify(sql, simplified);

    // hint is in first query
    sql = "select 1 from emp group by ^ except select 2 from dept a";
    simplified = "SELECT * FROM emp GROUP BY _suggest_";
    assertSimplify(sql, simplified);
  }

  /** Factory that creates testers. */
  private static class AdvisorTesterFactory extends DelegatingSqlTestFactory {
    public AdvisorTesterFactory() {
      super(DefaultSqlTestFactory.INSTANCE);
    }

    @Override public SqlValidator getValidator(SqlTestFactory factory) {
      final RelDataTypeFactory typeFactory =
          new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
      final SqlConformance conformance = (SqlConformance) get("conformance");
      final boolean caseSensitive = (Boolean) factory.get("caseSensitive");
      return new SqlAdvisorValidator(
          SqlStdOperatorTable.instance(),
          new MockCatalogReader(typeFactory, caseSensitive).init(),
          typeFactory,
          conformance);
    }

    @Override public SqlAdvisor createAdvisor(SqlValidatorWithHints validator) {
      return new SqlAdvisor(validator);
    }
  }
}

// End SqlAdvisorTest.java
