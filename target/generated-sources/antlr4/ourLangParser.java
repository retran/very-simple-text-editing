// Generated from ourLang.g4 by ANTLR 4.9.2

    package me.retran.skijaexample.javafxskija;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ourLangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LOAD=1, SAVE=2, LETTERS=3, NUMBER=4, EOS=5, WS=6;
	public static final int
		RULE_loadStatement = 0, RULE_saveStatement = 1, RULE_statements = 2;
	private static String[] makeRuleNames() {
		return new String[] {
			"loadStatement", "saveStatement", "statements"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "LOAD", "SAVE", "LETTERS", "NUMBER", "EOS", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ourLang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ourLangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class LoadStatementContext extends ParserRuleContext {
		public TerminalNode LOAD() { return getToken(ourLangParser.LOAD, 0); }
		public TerminalNode LETTERS() { return getToken(ourLangParser.LETTERS, 0); }
		public TerminalNode EOS() { return getToken(ourLangParser.EOS, 0); }
		public LoadStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loadStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).enterLoadStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).exitLoadStatement(this);
		}
	}

	public final LoadStatementContext loadStatement() throws RecognitionException {
		LoadStatementContext _localctx = new LoadStatementContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_loadStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(6);
			match(LOAD);
			setState(7);
			match(LETTERS);
			setState(8);
			match(EOS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SaveStatementContext extends ParserRuleContext {
		public TerminalNode SAVE() { return getToken(ourLangParser.SAVE, 0); }
		public TerminalNode LETTERS() { return getToken(ourLangParser.LETTERS, 0); }
		public TerminalNode EOS() { return getToken(ourLangParser.EOS, 0); }
		public SaveStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_saveStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).enterSaveStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).exitSaveStatement(this);
		}
	}

	public final SaveStatementContext saveStatement() throws RecognitionException {
		SaveStatementContext _localctx = new SaveStatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_saveStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			match(SAVE);
			setState(11);
			match(LETTERS);
			setState(12);
			match(EOS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public List<LoadStatementContext> loadStatement() {
			return getRuleContexts(LoadStatementContext.class);
		}
		public LoadStatementContext loadStatement(int i) {
			return getRuleContext(LoadStatementContext.class,i);
		}
		public List<SaveStatementContext> saveStatement() {
			return getRuleContexts(SaveStatementContext.class);
		}
		public SaveStatementContext saveStatement(int i) {
			return getRuleContext(SaveStatementContext.class,i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ourLangListener ) ((ourLangListener)listener).exitStatements(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LOAD || _la==SAVE) {
				{
				setState(16);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LOAD:
					{
					setState(14);
					loadStatement();
					}
					break;
				case SAVE:
					{
					setState(15);
					saveStatement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(20);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\b\30\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\7\4\23\n\4\f\4\16"+
		"\4\26\13\4\3\4\2\2\5\2\4\6\2\2\2\26\2\b\3\2\2\2\4\f\3\2\2\2\6\24\3\2\2"+
		"\2\b\t\7\3\2\2\t\n\7\5\2\2\n\13\7\7\2\2\13\3\3\2\2\2\f\r\7\4\2\2\r\16"+
		"\7\5\2\2\16\17\7\7\2\2\17\5\3\2\2\2\20\23\5\2\2\2\21\23\5\4\3\2\22\20"+
		"\3\2\2\2\22\21\3\2\2\2\23\26\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\7"+
		"\3\2\2\2\26\24\3\2\2\2\4\22\24";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}