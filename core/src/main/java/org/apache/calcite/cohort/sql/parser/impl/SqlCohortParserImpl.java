/* Generated By:JavaCC: Do not edit this line. SqlCohortParserImpl.java */
package org.apache.calcite.cohort.sql.parser.impl;

import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.calcite.cohort.sql.*;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlCohortNode;



/**
 * SQL parser, generated from Parser.jj by JavaCC.
 *
 * <p>The public wrapper for this parser is {@link SqlParser}.
 */
public class SqlCohortParserImpl implements SqlCohortParserImplConstants {

  final public SqlNode SqlStmtEof() throws ParseException {
    SqlNode stmt;
    stmt = SqlStmt();
    jj_consume_token(0);
        {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

  final public SqlNode SqlStmt() throws ParseException {
    SqlNode stmt;
    stmt = SqlCohortSelect();
        {if (true) return stmt;}
    throw new Error("Missing return statement in function");
  }

/**
 * Production for cohort query
 */
  final public CohortSelectNode SqlCohortSelect() throws ParseException {
        List<SqlNode> selectList;
        final String fromClause;
        final String birthFrom;
        final List<SqlNode> birthFilter;
        final List<SqlNode> ageFilter;
        final List<String> cohortBy;
    jj_consume_token(CSELECT);
    selectList = CohortSelectList();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case FROM:
      jj_consume_token(FROM);
      fromClause = CohortFromClause();
      birthFrom = BirthFrom();
      birthFilter = BirthFilterOpt();
      ageFilter = AgeFilterOpt();
      cohortBy = CohortBy();
      break;
    default:
      jj_la1[0] = jj_gen;
      E();
                        fromClause = null;
                        birthFrom = null;
                        birthFilter = null;
                        ageFilter = null;
                        cohortBy = null;
    }
                {if (true) return new CohortSelectNode(selectList, fromClause, birthFrom,
                                                                        birthFilter, ageFilter, cohortBy);}
    throw new Error("Missing return statement in function");
  }

  final public List<SqlNode> CohortSelectList() throws ParseException {
    List<SqlNode> list = new ArrayList<SqlNode>();
    SqlNode item;
    item = CohortSelectItem();
                               list.add(item);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      jj_consume_token(COMMA);
      item = CohortSelectItem();
                                         list.add(item);
    }
        {if (true) return list;}
    throw new Error("Missing return statement in function");
  }

  final public SqlNode CohortSelectItem() throws ParseException {
        SqlNode e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AGE:
    case COHORTSIZE:
    case USERCOUNT:
    case MIN:
    case MAX:
    case AVG:
    case SUM:
      e = CohortAggItem();
                {if (true) return e;}
      break;
    case IDENTIFIER:
      e = CohortIdentifier();
                {if (true) return e;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public SqlNode CohortAggItem() throws ParseException {
        CohortAggItemNode aggItem;
        String type;
        Token x, y;
    type = CohortAggFunc();
                aggItem = new CohortAggItemNode(type);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAREN:
      jj_consume_token(LPAREN);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        x = jj_consume_token(IDENTIFIER);
                                if (x != null) {
                                        aggItem.setFieldName(x.toString().trim());
                                }
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      y = jj_consume_token(IDENTIFIER);
                        if (y !=null) {
                                aggItem.setAlias(y.toString().trim());
                        }
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
                {if (true) return aggItem;}
    throw new Error("Missing return statement in function");
  }

  final public String CohortAggFunc() throws ParseException {
        String name;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COHORTSIZE:
      jj_consume_token(COHORTSIZE);
                               name = "COHORTSIZE";
      break;
    case AGE:
      jj_consume_token(AGE);
                        name = "AGE";
      break;
    case AVG:
      jj_consume_token(AVG);
                        name = "AVG";
      break;
    case MIN:
      jj_consume_token(MIN);
                        name = "MIN";
      break;
    case MAX:
      jj_consume_token(MAX);
                        name = "MAX";
      break;
    case SUM:
      jj_consume_token(SUM);
                        name = "SUM";
      break;
    case USERCOUNT:
      jj_consume_token(USERCOUNT);
                              name = "USERCOUNT";
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return name;}
    throw new Error("Missing return statement in function");
  }

  final public SqlNode CohortIdentifier() throws ParseException {
        CohortIdentifierNode e;
        Token x, y;
    x = jj_consume_token(IDENTIFIER);
                e = new CohortIdentifierNode(x.toString().trim());
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      y = jj_consume_token(IDENTIFIER);
                        if (y != null) {
                                e.setAlias(y.toString().trim());
                        }
      break;
    default:
      jj_la1[7] = jj_gen;
      ;
    }
          {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public String CohortFromClause() throws ParseException {
    Token e;
    e = jj_consume_token(IDENTIFIER);
        {if (true) return e.toString().trim();}
    throw new Error("Missing return statement in function");
  }

  final public String BirthFrom() throws ParseException {
        Token e;
    jj_consume_token(BIRTH);
    jj_consume_token(FROM);
    jj_consume_token(ACTION);
    jj_consume_token(EQ);
    e = jj_consume_token(QUOTE_STRING);
                {if (true) return removeQuote(e.toString().trim());}
    throw new Error("Missing return statement in function");
  }

  final public List<SqlNode> BirthFilterOpt() throws ParseException {
        List<SqlNode> condition;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AND:
      jj_consume_token(AND);
      condition = CohortFilter();
        {if (true) return condition;}
      break;
    default:
      jj_la1[8] = jj_gen;
        {if (true) return null;}
    }
    throw new Error("Missing return statement in function");
  }

  final public List<SqlNode> AgeFilterOpt() throws ParseException {
        List<SqlNode> condition;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AGE:
      jj_consume_token(AGE);
      jj_consume_token(ACTIVITIES);
      jj_consume_token(IN);
      condition = CohortFilter();
        {if (true) return condition;}
      break;
    default:
      jj_la1[9] = jj_gen;
        {if (true) return null;}
    }
    throw new Error("Missing return statement in function");
  }

  final public List<SqlNode> CohortFilter() throws ParseException {
        CohortFilterNode a, b;
        List<SqlNode> filters = null;
    a = SingleFilter();
                if (a != null) {
                        filters = new ArrayList<SqlNode>();
                        filters.add(a);
                }
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_2;
      }
      jj_consume_token(AND);
      b = SingleFilter();
                        if (filters == null) {
                                filters = new ArrayList<SqlNode>();
                        } else if (b != null) {
                                filters.add(b);
                        }
    }
          {if (true) return filters;}
    throw new Error("Missing return statement in function");
  }

  final public CohortFilterNode SingleFilter() throws ParseException {
        CohortFilterNode filter;
        List<String> list;
        Token lhs, operator;
        Token a, b;
        String x;
    lhs = jj_consume_token(IDENTIFIER);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BETWEEN:
      operator = jj_consume_token(BETWEEN);
      a = jj_consume_token(QUOTE_STRING);
      jj_consume_token(AND);
      b = jj_consume_token(QUOTE_STRING);
                        filter = new CohortFilterNode(
                                                                operator.toString().toUpperCase().trim());
                        filter.setDim(lhs.toString().trim());
                        list = new ArrayList<String>();
                        if (a != null) list.add(removeQuote(a.toString().trim()));
                        if (b != null) list.add(removeQuote(b.toString().trim()));
                        filter.setValues(list);
      break;
    case IN:
      operator = jj_consume_token(IN);
      jj_consume_token(LBRACKET);
      a = jj_consume_token(QUOTE_STRING);
                                filter = new CohortFilterNode(
                                                operator.toString().toUpperCase().trim());
                                filter.setDim(lhs.toString().trim());
                                list = new ArrayList<String>();
                                if (a != null) {
                                        list.add(removeQuote(a.toString().trim()));
                                }
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[11] = jj_gen;
          break label_3;
        }
        jj_consume_token(COMMA);
        b = jj_consume_token(QUOTE_STRING);
                                        if (b != null) {
                                                list.add(removeQuote(b.toString().trim()));
                                        }
      }
      jj_consume_token(RBRACKET);
                                filter.setValues(list);
      break;
    case EQ:
    case GT:
    case LT:
    case LE:
    case GE:
    case NE:
      x = BinaryOperator();
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BIRTH:
        jj_consume_token(BIRTH);
        jj_consume_token(LPAREN);
        a = jj_consume_token(IDENTIFIER);
        jj_consume_token(RPAREN);
                                filter = new CohortFilterNode(x);
                                filter.setBirthFlag(true);
                                filter.setDim(lhs.toString().trim());
                                list = new ArrayList<String>();
                                list.add(a.toString().trim());
                                filter.setValues(list);
        break;
      case QUOTE_STRING:
        a = jj_consume_token(QUOTE_STRING);
                                filter = new CohortFilterNode(x);
                                filter.setBirthFlag(false);
                                filter.setDim(lhs.toString().trim());
                                list = new ArrayList<String>();
                                list.add(a.toString().trim());
                                filter.setValues(list);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return filter;}
    throw new Error("Missing return statement in function");
  }

  final public String BinaryOperator() throws ParseException {
    if (jj_2_1(2)) {
      jj_consume_token(EQ);
                {if (true) return "EQ";}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LT:
        jj_consume_token(LT);
                {if (true) return "LT";}
        break;
      case GT:
        jj_consume_token(GT);
                {if (true) return "GT";}
        break;
      case LE:
        jj_consume_token(LE);
                {if (true) return "LE";}
        break;
      case GE:
        jj_consume_token(GE);
                {if (true) return "GE";}
        break;
      case NE:
        jj_consume_token(NE);
                {if (true) return "NE";}
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  final public List<String> CohortBy() throws ParseException {
        Token x, y;
        List<String> list;
    jj_consume_token(COHORT);
    jj_consume_token(BY);
    x = jj_consume_token(IDENTIFIER);
                list = new ArrayList<String>();
                if (x != null) list.add(x.toString().trim());
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_4;
      }
      jj_consume_token(COMMA);
      y = jj_consume_token(IDENTIFIER);
                        if (y != null) list.add(y.toString().trim());
    }
          {if (true) return list;}
    throw new Error("Missing return statement in function");
  }

  String removeQuote(String x) throws ParseException {
        if (x == null) return x;
        if (x.charAt(0) == '\u005c'') x = x.substring(1);
        if (x.charAt(x.length()-1) == '\u005c'') x = x.substring(0, x.length()-1);
        return x;
  }

  void E() throws ParseException {
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3_1() {
    if (jj_scan_token(EQ)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public SqlCohortParserImplTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80,0x0,0x5f1000,0x0,0x80000000,0x2000,0x5f1000,0x2000,0x20000000,0x1000,0x20000000,0x0,0x200,0x1fa08000,0x1f000000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x20,0x40,0x40,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x20,0x800,0x0,0x0,0x20,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SqlCohortParserImpl(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SqlCohortParserImpl(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SqlCohortParserImplTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SqlCohortParserImpl(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new SqlCohortParserImplTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SqlCohortParserImpl(SqlCohortParserImplTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SqlCohortParserImplTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[49];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 49; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}