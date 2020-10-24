// Generated from Lang.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LangParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		ID=39, TYPE_NAME=40, INT=41, FLOAT=42, CHAR=43, WS=44, LINE_COMMENT=45, 
		BLOCK_COMMENT=46;
	public static final int
		RULE_prog = 0, RULE_data = 1, RULE_decl = 2, RULE_func = 3, RULE_params = 4, 
		RULE_type = 5, RULE_type1 = 6, RULE_btype = 7, RULE_cmd = 8, RULE_cmd1 = 9, 
		RULE_exp = 10, RULE_exp1 = 11, RULE_rexp = 12, RULE_rexp1 = 13, RULE_rexp2 = 14, 
		RULE_rexp3 = 15, RULE_aexp = 16, RULE_aexp1 = 17, RULE_aexp2 = 18, RULE_mexp = 19, 
		RULE_mexp1 = 20, RULE_mexp2 = 21, RULE_sexp = 22, RULE_pexp = 23, RULE_lvalue = 24, 
		RULE_lvalue1 = 25, RULE_lvalue2 = 26, RULE_exps = 27;
	public static final String[] ruleNames = {
		"prog", "data", "decl", "func", "params", "type", "type1", "btype", "cmd", 
		"cmd1", "exp", "exp1", "rexp", "rexp1", "rexp2", "rexp3", "aexp", "aexp1", 
		"aexp2", "mexp", "mexp1", "mexp2", "sexp", "pexp", "lvalue", "lvalue1", 
		"lvalue2", "exps"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'data'", "'{'", "'}'", "'::'", "';'", "'('", "')'", "':'", "','", 
		"'['", "']'", "'Int'", "'Char'", "'Bool'", "'Float'", "'if'", "'iterate'", 
		"'read'", "'print'", "'return'", "'='", "'<'", "'>'", "'else'", "'&&'", 
		"'=='", "'!='", "'+'", "'-'", "'*'", "'/'", "'%'", "'!'", "'true'", "'false'", 
		"'null'", "'new'", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, "ID", "TYPE_NAME", "INT", "FLOAT", "CHAR", "WS", "LINE_COMMENT", 
		"BLOCK_COMMENT"
	};
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
	public String getGrammarFileName() { return "Lang.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LangParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<DataContext> data() {
			return getRuleContexts(DataContext.class);
		}
		public DataContext data(int i) {
			return getRuleContext(DataContext.class,i);
		}
		public List<FuncContext> func() {
			return getRuleContexts(FuncContext.class);
		}
		public FuncContext func(int i) {
			return getRuleContext(FuncContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(56);
				data();
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(62);
				func();
				}
				}
				setState(67);
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

	public static class DataContext extends ParserRuleContext {
		public TerminalNode TYPE_NAME() { return getToken(LangParser.TYPE_NAME, 0); }
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public DataContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_data; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterData(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitData(this);
		}
	}

	public final DataContext data() throws RecognitionException {
		DataContext _localctx = new DataContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_data);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(T__0);
			setState(69);
			match(TYPE_NAME);
			setState(70);
			match(T__1);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(71);
				decl();
				}
				}
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(77);
			match(T__2);
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

	public static class DeclContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitDecl(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(ID);
			setState(80);
			match(T__3);
			setState(81);
			type();
			setState(82);
			match(T__4);
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

	public static class FuncContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public FuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitFunc(this);
		}
	}

	public final FuncContext func() throws RecognitionException {
		FuncContext _localctx = new FuncContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_func);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(ID);
			setState(85);
			match(T__5);
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(86);
				params();
				}
			}

			setState(89);
			match(T__6);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(90);
				match(T__7);
				setState(91);
				type();
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(92);
					match(T__8);
					setState(93);
					type();
					}
					}
					setState(98);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(101);
			match(T__1);
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << ID))) != 0)) {
				{
				{
				setState(102);
				cmd();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(108);
			match(T__2);
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

	public static class ParamsContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(LangParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(LangParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterParams(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitParams(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_params);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			match(ID);
			setState(111);
			match(T__3);
			setState(112);
			type();
			setState(119);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(113);
				match(T__8);
				setState(114);
				match(ID);
				setState(115);
				match(T__3);
				setState(116);
				type();
				}
				}
				setState(121);
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

	public static class TypeContext extends ParserRuleContext {
		public BtypeContext btype() {
			return getRuleContext(BtypeContext.class,0);
		}
		public Type1Context type1() {
			return getRuleContext(Type1Context.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			btype();
			setState(123);
			type1();
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

	public static class Type1Context extends ParserRuleContext {
		public Type1Context type1() {
			return getRuleContext(Type1Context.class,0);
		}
		public Type1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterType1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitType1(this);
		}
	}

	public final Type1Context type1() throws RecognitionException {
		Type1Context _localctx = new Type1Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_type1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(125);
				match(T__9);
				setState(126);
				match(T__10);
				setState(127);
				type1();
				}
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

	public static class BtypeContext extends ParserRuleContext {
		public TerminalNode TYPE_NAME() { return getToken(LangParser.TYPE_NAME, 0); }
		public BtypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_btype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterBtype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitBtype(this);
		}
	}

	public final BtypeContext btype() throws RecognitionException {
		BtypeContext _localctx = new BtypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_btype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << TYPE_NAME))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class CmdContext extends ParserRuleContext {
		public List<CmdContext> cmd() {
			return getRuleContexts(CmdContext.class);
		}
		public CmdContext cmd(int i) {
			return getRuleContext(CmdContext.class,i);
		}
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public Cmd1Context cmd1() {
			return getRuleContext(Cmd1Context.class,0);
		}
		public List<LvalueContext> lvalue() {
			return getRuleContexts(LvalueContext.class);
		}
		public LvalueContext lvalue(int i) {
			return getRuleContext(LvalueContext.class,i);
		}
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
		}
		public CmdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterCmd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitCmd(this);
		}
	}

	public final CmdContext cmd() throws RecognitionException {
		CmdContext _localctx = new CmdContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_cmd);
		int _la;
		try {
			setState(197);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				match(T__1);
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << ID))) != 0)) {
					{
					{
					setState(133);
					cmd();
					}
					}
					setState(138);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(139);
				match(T__2);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(140);
				match(T__15);
				setState(141);
				match(T__5);
				setState(142);
				exp();
				setState(143);
				match(T__6);
				setState(144);
				cmd();
				setState(145);
				cmd1();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(147);
				match(T__16);
				setState(148);
				match(T__5);
				setState(149);
				exp();
				setState(150);
				match(T__6);
				setState(151);
				cmd();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(153);
				match(T__17);
				setState(154);
				lvalue();
				setState(155);
				match(T__4);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(157);
				match(T__18);
				setState(158);
				exp();
				setState(159);
				match(T__4);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(161);
				match(T__19);
				setState(162);
				exp();
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(163);
					match(T__8);
					setState(164);
					exp();
					}
					}
					setState(169);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(170);
				match(T__4);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(172);
				lvalue();
				setState(173);
				match(T__20);
				setState(174);
				exp();
				setState(175);
				match(T__4);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(177);
				match(ID);
				setState(178);
				match(T__5);
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__28) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << CHAR))) != 0)) {
					{
					setState(179);
					exps();
					}
				}

				setState(182);
				match(T__6);
				setState(194);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__21) {
					{
					setState(183);
					match(T__21);
					setState(184);
					lvalue();
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__8) {
						{
						{
						setState(185);
						match(T__8);
						setState(186);
						lvalue();
						}
						}
						setState(191);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(192);
					match(T__22);
					}
				}

				setState(196);
				match(T__4);
				}
				break;
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

	public static class Cmd1Context extends ParserRuleContext {
		public CmdContext cmd() {
			return getRuleContext(CmdContext.class,0);
		}
		public Cmd1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmd1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterCmd1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitCmd1(this);
		}
	}

	public final Cmd1Context cmd1() throws RecognitionException {
		Cmd1Context _localctx = new Cmd1Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_cmd1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(199);
				match(T__23);
				setState(200);
				cmd();
				}
				break;
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

	public static class ExpContext extends ParserRuleContext {
		public RexpContext rexp() {
			return getRuleContext(RexpContext.class,0);
		}
		public Exp1Context exp1() {
			return getRuleContext(Exp1Context.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExp(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			rexp();
			setState(204);
			exp1();
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

	public static class Exp1Context extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Exp1Context exp1() {
			return getRuleContext(Exp1Context.class,0);
		}
		public Exp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExp1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExp1(this);
		}
	}

	public final Exp1Context exp1() throws RecognitionException {
		Exp1Context _localctx = new Exp1Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_exp1);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(206);
				match(T__24);
				setState(207);
				exp();
				setState(208);
				exp1();
				}
				break;
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

	public static class RexpContext extends ParserRuleContext {
		public AexpContext aexp() {
			return getRuleContext(AexpContext.class,0);
		}
		public Rexp1Context rexp1() {
			return getRuleContext(Rexp1Context.class,0);
		}
		public Rexp3Context rexp3() {
			return getRuleContext(Rexp3Context.class,0);
		}
		public RexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterRexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitRexp(this);
		}
	}

	public final RexpContext rexp() throws RecognitionException {
		RexpContext _localctx = new RexpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_rexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			aexp();
			setState(213);
			rexp1();
			setState(214);
			rexp3();
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

	public static class Rexp1Context extends ParserRuleContext {
		public AexpContext aexp() {
			return getRuleContext(AexpContext.class,0);
		}
		public Rexp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rexp1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterRexp1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitRexp1(this);
		}
	}

	public final Rexp1Context rexp1() throws RecognitionException {
		Rexp1Context _localctx = new Rexp1Context(_ctx, getState());
		enterRule(_localctx, 26, RULE_rexp1);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21) {
				{
				setState(216);
				match(T__21);
				setState(217);
				aexp();
				}
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

	public static class Rexp2Context extends ParserRuleContext {
		public AexpContext aexp() {
			return getRuleContext(AexpContext.class,0);
		}
		public Rexp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rexp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterRexp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitRexp2(this);
		}
	}

	public final Rexp2Context rexp2() throws RecognitionException {
		Rexp2Context _localctx = new Rexp2Context(_ctx, getState());
		enterRule(_localctx, 28, RULE_rexp2);
		try {
			setState(224);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__25:
				enterOuterAlt(_localctx, 1);
				{
				setState(220);
				match(T__25);
				setState(221);
				aexp();
				}
				break;
			case T__26:
				enterOuterAlt(_localctx, 2);
				{
				setState(222);
				match(T__26);
				setState(223);
				aexp();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Rexp3Context extends ParserRuleContext {
		public Rexp2Context rexp2() {
			return getRuleContext(Rexp2Context.class,0);
		}
		public Rexp3Context rexp3() {
			return getRuleContext(Rexp3Context.class,0);
		}
		public Rexp3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rexp3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterRexp3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitRexp3(this);
		}
	}

	public final Rexp3Context rexp3() throws RecognitionException {
		Rexp3Context _localctx = new Rexp3Context(_ctx, getState());
		enterRule(_localctx, 30, RULE_rexp3);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__25 || _la==T__26) {
				{
				setState(226);
				rexp2();
				setState(227);
				rexp3();
				}
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

	public static class AexpContext extends ParserRuleContext {
		public MexpContext mexp() {
			return getRuleContext(MexpContext.class,0);
		}
		public Aexp2Context aexp2() {
			return getRuleContext(Aexp2Context.class,0);
		}
		public AexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterAexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitAexp(this);
		}
	}

	public final AexpContext aexp() throws RecognitionException {
		AexpContext _localctx = new AexpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_aexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			mexp();
			setState(232);
			aexp2();
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

	public static class Aexp1Context extends ParserRuleContext {
		public MexpContext mexp() {
			return getRuleContext(MexpContext.class,0);
		}
		public Aexp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aexp1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterAexp1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitAexp1(this);
		}
	}

	public final Aexp1Context aexp1() throws RecognitionException {
		Aexp1Context _localctx = new Aexp1Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_aexp1);
		try {
			setState(238);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				match(T__27);
				setState(235);
				mexp();
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(236);
				match(T__28);
				setState(237);
				mexp();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Aexp2Context extends ParserRuleContext {
		public Aexp1Context aexp1() {
			return getRuleContext(Aexp1Context.class,0);
		}
		public Aexp2Context aexp2() {
			return getRuleContext(Aexp2Context.class,0);
		}
		public Aexp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aexp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterAexp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitAexp2(this);
		}
	}

	public final Aexp2Context aexp2() throws RecognitionException {
		Aexp2Context _localctx = new Aexp2Context(_ctx, getState());
		enterRule(_localctx, 36, RULE_aexp2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__27 || _la==T__28) {
				{
				setState(240);
				aexp1();
				setState(241);
				aexp2();
				}
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

	public static class MexpContext extends ParserRuleContext {
		public SexpContext sexp() {
			return getRuleContext(SexpContext.class,0);
		}
		public Mexp2Context mexp2() {
			return getRuleContext(Mexp2Context.class,0);
		}
		public MexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterMexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitMexp(this);
		}
	}

	public final MexpContext mexp() throws RecognitionException {
		MexpContext _localctx = new MexpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_mexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			sexp();
			setState(246);
			mexp2();
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

	public static class Mexp1Context extends ParserRuleContext {
		public SexpContext sexp() {
			return getRuleContext(SexpContext.class,0);
		}
		public Mexp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mexp1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterMexp1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitMexp1(this);
		}
	}

	public final Mexp1Context mexp1() throws RecognitionException {
		Mexp1Context _localctx = new Mexp1Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_mexp1);
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__29:
				enterOuterAlt(_localctx, 1);
				{
				setState(248);
				match(T__29);
				setState(249);
				sexp();
				}
				break;
			case T__30:
				enterOuterAlt(_localctx, 2);
				{
				setState(250);
				match(T__30);
				setState(251);
				sexp();
				}
				break;
			case T__31:
				enterOuterAlt(_localctx, 3);
				{
				setState(252);
				match(T__31);
				setState(253);
				sexp();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Mexp2Context extends ParserRuleContext {
		public Mexp1Context mexp1() {
			return getRuleContext(Mexp1Context.class,0);
		}
		public Mexp2Context mexp2() {
			return getRuleContext(Mexp2Context.class,0);
		}
		public Mexp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mexp2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterMexp2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitMexp2(this);
		}
	}

	public final Mexp2Context mexp2() throws RecognitionException {
		Mexp2Context _localctx = new Mexp2Context(_ctx, getState());
		enterRule(_localctx, 42, RULE_mexp2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31))) != 0)) {
				{
				setState(256);
				mexp1();
				setState(257);
				mexp2();
				}
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

	public static class SexpContext extends ParserRuleContext {
		public SexpContext sexp() {
			return getRuleContext(SexpContext.class,0);
		}
		public TerminalNode INT() { return getToken(LangParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(LangParser.FLOAT, 0); }
		public TerminalNode CHAR() { return getToken(LangParser.CHAR, 0); }
		public PexpContext pexp() {
			return getRuleContext(PexpContext.class,0);
		}
		public SexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterSexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitSexp(this);
		}
	}

	public final SexpContext sexp() throws RecognitionException {
		SexpContext _localctx = new SexpContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_sexp);
		try {
			setState(272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__32:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				match(T__32);
				setState(262);
				sexp();
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(263);
				match(T__28);
				setState(264);
				sexp();
				}
				break;
			case T__33:
				enterOuterAlt(_localctx, 3);
				{
				setState(265);
				match(T__33);
				}
				break;
			case T__34:
				enterOuterAlt(_localctx, 4);
				{
				setState(266);
				match(T__34);
				}
				break;
			case T__35:
				enterOuterAlt(_localctx, 5);
				{
				setState(267);
				match(T__35);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 6);
				{
				setState(268);
				match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 7);
				{
				setState(269);
				match(FLOAT);
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 8);
				{
				setState(270);
				match(CHAR);
				}
				break;
			case T__5:
			case T__36:
			case ID:
				enterOuterAlt(_localctx, 9);
				{
				setState(271);
				pexp();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class PexpContext extends ParserRuleContext {
		public LvalueContext lvalue() {
			return getRuleContext(LvalueContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public BtypeContext btype() {
			return getRuleContext(BtypeContext.class,0);
		}
		public SexpContext sexp() {
			return getRuleContext(SexpContext.class,0);
		}
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public ExpsContext exps() {
			return getRuleContext(ExpsContext.class,0);
		}
		public PexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterPexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitPexp(this);
		}
	}

	public final PexpContext pexp() throws RecognitionException {
		PexpContext _localctx = new PexpContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_pexp);
		int _la;
		try {
			setState(297);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				lvalue();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(275);
				match(T__5);
				setState(276);
				exp();
				setState(277);
				match(T__6);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(279);
				match(T__36);
				setState(280);
				btype();
				setState(285);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(281);
					match(T__9);
					setState(282);
					sexp();
					setState(283);
					match(T__10);
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(287);
				match(ID);
				setState(288);
				match(T__5);
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__5) | (1L << T__28) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << CHAR))) != 0)) {
					{
					setState(289);
					exps();
					}
				}

				setState(292);
				match(T__6);
				setState(293);
				match(T__9);
				setState(294);
				exp();
				setState(295);
				match(T__10);
				}
				break;
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

	public static class LvalueContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public Lvalue2Context lvalue2() {
			return getRuleContext(Lvalue2Context.class,0);
		}
		public LvalueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterLvalue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitLvalue(this);
		}
	}

	public final LvalueContext lvalue() throws RecognitionException {
		LvalueContext _localctx = new LvalueContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_lvalue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			match(ID);
			setState(300);
			lvalue2();
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

	public static class Lvalue1Context extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode ID() { return getToken(LangParser.ID, 0); }
		public Lvalue1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterLvalue1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitLvalue1(this);
		}
	}

	public final Lvalue1Context lvalue1() throws RecognitionException {
		Lvalue1Context _localctx = new Lvalue1Context(_ctx, getState());
		enterRule(_localctx, 50, RULE_lvalue1);
		try {
			setState(308);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				match(T__9);
				setState(303);
				exp();
				setState(304);
				match(T__10);
				}
				break;
			case T__37:
				enterOuterAlt(_localctx, 2);
				{
				setState(306);
				match(T__37);
				setState(307);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Lvalue2Context extends ParserRuleContext {
		public Lvalue1Context lvalue1() {
			return getRuleContext(Lvalue1Context.class,0);
		}
		public Lvalue2Context lvalue2() {
			return getRuleContext(Lvalue2Context.class,0);
		}
		public Lvalue2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lvalue2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterLvalue2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitLvalue2(this);
		}
	}

	public final Lvalue2Context lvalue2() throws RecognitionException {
		Lvalue2Context _localctx = new Lvalue2Context(_ctx, getState());
		enterRule(_localctx, 52, RULE_lvalue2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9 || _la==T__37) {
				{
				setState(310);
				lvalue1();
				setState(311);
				lvalue2();
				}
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

	public static class ExpsContext extends ParserRuleContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public ExpsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).enterExps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LangListener ) ((LangListener)listener).exitExps(this);
		}
	}

	public final ExpsContext exps() throws RecognitionException {
		ExpsContext _localctx = new ExpsContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_exps);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			exp();
			setState(320);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(316);
				match(T__8);
				setState(317);
				exp();
				}
				}
				setState(322);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\60\u0146\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\7\2<\n\2\f\2\16\2?\13\2\3"+
		"\2\7\2B\n\2\f\2\16\2E\13\2\3\3\3\3\3\3\3\3\7\3K\n\3\f\3\16\3N\13\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\5\5Z\n\5\3\5\3\5\3\5\3\5\3\5\7\5"+
		"a\n\5\f\5\16\5d\13\5\5\5f\n\5\3\5\3\5\7\5j\n\5\f\5\16\5m\13\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6x\n\6\f\6\16\6{\13\6\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\5\b\u0083\n\b\3\t\3\t\3\n\3\n\7\n\u0089\n\n\f\n\16\n\u008c\13\n"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00a8\n\n\f\n\16\n\u00ab\13\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00b7\n\n\3\n\3\n\3\n\3\n\3"+
		"\n\7\n\u00be\n\n\f\n\16\n\u00c1\13\n\3\n\3\n\5\n\u00c5\n\n\3\n\5\n\u00c8"+
		"\n\n\3\13\3\13\5\13\u00cc\n\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u00d5\n"+
		"\r\3\16\3\16\3\16\3\16\3\17\3\17\5\17\u00dd\n\17\3\20\3\20\3\20\3\20\5"+
		"\20\u00e3\n\20\3\21\3\21\3\21\5\21\u00e8\n\21\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\5\23\u00f1\n\23\3\24\3\24\3\24\5\24\u00f6\n\24\3\25\3\25\3"+
		"\25\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0101\n\26\3\27\3\27\3\27\5\27"+
		"\u0106\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30"+
		"\u0113\n\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31"+
		"\u0120\n\31\3\31\3\31\3\31\5\31\u0125\n\31\3\31\3\31\3\31\3\31\3\31\5"+
		"\31\u012c\n\31\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u0137"+
		"\n\33\3\34\3\34\3\34\5\34\u013c\n\34\3\35\3\35\3\35\7\35\u0141\n\35\f"+
		"\35\16\35\u0144\13\35\3\35\2\2\36\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\60\62\64\668\2\3\4\2\16\21**\2\u0158\2=\3\2\2\2\4F\3\2\2"+
		"\2\6Q\3\2\2\2\bV\3\2\2\2\np\3\2\2\2\f|\3\2\2\2\16\u0082\3\2\2\2\20\u0084"+
		"\3\2\2\2\22\u00c7\3\2\2\2\24\u00cb\3\2\2\2\26\u00cd\3\2\2\2\30\u00d4\3"+
		"\2\2\2\32\u00d6\3\2\2\2\34\u00dc\3\2\2\2\36\u00e2\3\2\2\2 \u00e7\3\2\2"+
		"\2\"\u00e9\3\2\2\2$\u00f0\3\2\2\2&\u00f5\3\2\2\2(\u00f7\3\2\2\2*\u0100"+
		"\3\2\2\2,\u0105\3\2\2\2.\u0112\3\2\2\2\60\u012b\3\2\2\2\62\u012d\3\2\2"+
		"\2\64\u0136\3\2\2\2\66\u013b\3\2\2\28\u013d\3\2\2\2:<\5\4\3\2;:\3\2\2"+
		"\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>C\3\2\2\2?=\3\2\2\2@B\5\b\5\2A@\3\2\2"+
		"\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2D\3\3\2\2\2EC\3\2\2\2FG\7\3\2\2GH\7*\2"+
		"\2HL\7\4\2\2IK\5\6\4\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2"+
		"\2NL\3\2\2\2OP\7\5\2\2P\5\3\2\2\2QR\7)\2\2RS\7\6\2\2ST\5\f\7\2TU\7\7\2"+
		"\2U\7\3\2\2\2VW\7)\2\2WY\7\b\2\2XZ\5\n\6\2YX\3\2\2\2YZ\3\2\2\2Z[\3\2\2"+
		"\2[e\7\t\2\2\\]\7\n\2\2]b\5\f\7\2^_\7\13\2\2_a\5\f\7\2`^\3\2\2\2ad\3\2"+
		"\2\2b`\3\2\2\2bc\3\2\2\2cf\3\2\2\2db\3\2\2\2e\\\3\2\2\2ef\3\2\2\2fg\3"+
		"\2\2\2gk\7\4\2\2hj\5\22\n\2ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln"+
		"\3\2\2\2mk\3\2\2\2no\7\5\2\2o\t\3\2\2\2pq\7)\2\2qr\7\6\2\2ry\5\f\7\2s"+
		"t\7\13\2\2tu\7)\2\2uv\7\6\2\2vx\5\f\7\2ws\3\2\2\2x{\3\2\2\2yw\3\2\2\2"+
		"yz\3\2\2\2z\13\3\2\2\2{y\3\2\2\2|}\5\20\t\2}~\5\16\b\2~\r\3\2\2\2\177"+
		"\u0080\7\f\2\2\u0080\u0081\7\r\2\2\u0081\u0083\5\16\b\2\u0082\177\3\2"+
		"\2\2\u0082\u0083\3\2\2\2\u0083\17\3\2\2\2\u0084\u0085\t\2\2\2\u0085\21"+
		"\3\2\2\2\u0086\u008a\7\4\2\2\u0087\u0089\5\22\n\2\u0088\u0087\3\2\2\2"+
		"\u0089\u008c\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008d"+
		"\3\2\2\2\u008c\u008a\3\2\2\2\u008d\u00c8\7\5\2\2\u008e\u008f\7\22\2\2"+
		"\u008f\u0090\7\b\2\2\u0090\u0091\5\26\f\2\u0091\u0092\7\t\2\2\u0092\u0093"+
		"\5\22\n\2\u0093\u0094\5\24\13\2\u0094\u00c8\3\2\2\2\u0095\u0096\7\23\2"+
		"\2\u0096\u0097\7\b\2\2\u0097\u0098\5\26\f\2\u0098\u0099\7\t\2\2\u0099"+
		"\u009a\5\22\n\2\u009a\u00c8\3\2\2\2\u009b\u009c\7\24\2\2\u009c\u009d\5"+
		"\62\32\2\u009d\u009e\7\7\2\2\u009e\u00c8\3\2\2\2\u009f\u00a0\7\25\2\2"+
		"\u00a0\u00a1\5\26\f\2\u00a1\u00a2\7\7\2\2\u00a2\u00c8\3\2\2\2\u00a3\u00a4"+
		"\7\26\2\2\u00a4\u00a9\5\26\f\2\u00a5\u00a6\7\13\2\2\u00a6\u00a8\5\26\f"+
		"\2\u00a7\u00a5\3\2\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa"+
		"\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ac\u00ad\7\7\2\2\u00ad"+
		"\u00c8\3\2\2\2\u00ae\u00af\5\62\32\2\u00af\u00b0\7\27\2\2\u00b0\u00b1"+
		"\5\26\f\2\u00b1\u00b2\7\7\2\2\u00b2\u00c8\3\2\2\2\u00b3\u00b4\7)\2\2\u00b4"+
		"\u00b6\7\b\2\2\u00b5\u00b7\58\35\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3\2"+
		"\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00c4\7\t\2\2\u00b9\u00ba\7\30\2\2\u00ba"+
		"\u00bf\5\62\32\2\u00bb\u00bc\7\13\2\2\u00bc\u00be\5\62\32\2\u00bd\u00bb"+
		"\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\u00c2\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c3\7\31\2\2\u00c3\u00c5\3"+
		"\2\2\2\u00c4\u00b9\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c8\7\7\2\2\u00c7\u0086\3\2\2\2\u00c7\u008e\3\2\2\2\u00c7\u0095\3\2"+
		"\2\2\u00c7\u009b\3\2\2\2\u00c7\u009f\3\2\2\2\u00c7\u00a3\3\2\2\2\u00c7"+
		"\u00ae\3\2\2\2\u00c7\u00b3\3\2\2\2\u00c8\23\3\2\2\2\u00c9\u00ca\7\32\2"+
		"\2\u00ca\u00cc\5\22\n\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc"+
		"\25\3\2\2\2\u00cd\u00ce\5\32\16\2\u00ce\u00cf\5\30\r\2\u00cf\27\3\2\2"+
		"\2\u00d0\u00d1\7\33\2\2\u00d1\u00d2\5\26\f\2\u00d2\u00d3\5\30\r\2\u00d3"+
		"\u00d5\3\2\2\2\u00d4\u00d0\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\31\3\2\2"+
		"\2\u00d6\u00d7\5\"\22\2\u00d7\u00d8\5\34\17\2\u00d8\u00d9\5 \21\2\u00d9"+
		"\33\3\2\2\2\u00da\u00db\7\30\2\2\u00db\u00dd\5\"\22\2\u00dc\u00da\3\2"+
		"\2\2\u00dc\u00dd\3\2\2\2\u00dd\35\3\2\2\2\u00de\u00df\7\34\2\2\u00df\u00e3"+
		"\5\"\22\2\u00e0\u00e1\7\35\2\2\u00e1\u00e3\5\"\22\2\u00e2\u00de\3\2\2"+
		"\2\u00e2\u00e0\3\2\2\2\u00e3\37\3\2\2\2\u00e4\u00e5\5\36\20\2\u00e5\u00e6"+
		"\5 \21\2\u00e6\u00e8\3\2\2\2\u00e7\u00e4\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"!\3\2\2\2\u00e9\u00ea\5(\25\2\u00ea\u00eb\5&\24\2\u00eb#\3\2\2\2\u00ec"+
		"\u00ed\7\36\2\2\u00ed\u00f1\5(\25\2\u00ee\u00ef\7\37\2\2\u00ef\u00f1\5"+
		"(\25\2\u00f0\u00ec\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1%\3\2\2\2\u00f2\u00f3"+
		"\5$\23\2\u00f3\u00f4\5&\24\2\u00f4\u00f6\3\2\2\2\u00f5\u00f2\3\2\2\2\u00f5"+
		"\u00f6\3\2\2\2\u00f6\'\3\2\2\2\u00f7\u00f8\5.\30\2\u00f8\u00f9\5,\27\2"+
		"\u00f9)\3\2\2\2\u00fa\u00fb\7 \2\2\u00fb\u0101\5.\30\2\u00fc\u00fd\7!"+
		"\2\2\u00fd\u0101\5.\30\2\u00fe\u00ff\7\"\2\2\u00ff\u0101\5.\30\2\u0100"+
		"\u00fa\3\2\2\2\u0100\u00fc\3\2\2\2\u0100\u00fe\3\2\2\2\u0101+\3\2\2\2"+
		"\u0102\u0103\5*\26\2\u0103\u0104\5,\27\2\u0104\u0106\3\2\2\2\u0105\u0102"+
		"\3\2\2\2\u0105\u0106\3\2\2\2\u0106-\3\2\2\2\u0107\u0108\7#\2\2\u0108\u0113"+
		"\5.\30\2\u0109\u010a\7\37\2\2\u010a\u0113\5.\30\2\u010b\u0113\7$\2\2\u010c"+
		"\u0113\7%\2\2\u010d\u0113\7&\2\2\u010e\u0113\7+\2\2\u010f\u0113\7,\2\2"+
		"\u0110\u0113\7-\2\2\u0111\u0113\5\60\31\2\u0112\u0107\3\2\2\2\u0112\u0109"+
		"\3\2\2\2\u0112\u010b\3\2\2\2\u0112\u010c\3\2\2\2\u0112\u010d\3\2\2\2\u0112"+
		"\u010e\3\2\2\2\u0112\u010f\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0111\3\2"+
		"\2\2\u0113/\3\2\2\2\u0114\u012c\5\62\32\2\u0115\u0116\7\b\2\2\u0116\u0117"+
		"\5\26\f\2\u0117\u0118\7\t\2\2\u0118\u012c\3\2\2\2\u0119\u011a\7\'\2\2"+
		"\u011a\u011f\5\20\t\2\u011b\u011c\7\f\2\2\u011c\u011d\5.\30\2\u011d\u011e"+
		"\7\r\2\2\u011e\u0120\3\2\2\2\u011f\u011b\3\2\2\2\u011f\u0120\3\2\2\2\u0120"+
		"\u012c\3\2\2\2\u0121\u0122\7)\2\2\u0122\u0124\7\b\2\2\u0123\u0125\58\35"+
		"\2\u0124\u0123\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0127"+
		"\7\t\2\2\u0127\u0128\7\f\2\2\u0128\u0129\5\26\f\2\u0129\u012a\7\r\2\2"+
		"\u012a\u012c\3\2\2\2\u012b\u0114\3\2\2\2\u012b\u0115\3\2\2\2\u012b\u0119"+
		"\3\2\2\2\u012b\u0121\3\2\2\2\u012c\61\3\2\2\2\u012d\u012e\7)\2\2\u012e"+
		"\u012f\5\66\34\2\u012f\63\3\2\2\2\u0130\u0131\7\f\2\2\u0131\u0132\5\26"+
		"\f\2\u0132\u0133\7\r\2\2\u0133\u0137\3\2\2\2\u0134\u0135\7(\2\2\u0135"+
		"\u0137\7)\2\2\u0136\u0130\3\2\2\2\u0136\u0134\3\2\2\2\u0137\65\3\2\2\2"+
		"\u0138\u0139\5\64\33\2\u0139\u013a\5\66\34\2\u013a\u013c\3\2\2\2\u013b"+
		"\u0138\3\2\2\2\u013b\u013c\3\2\2\2\u013c\67\3\2\2\2\u013d\u0142\5\26\f"+
		"\2\u013e\u013f\7\13\2\2\u013f\u0141\5\26\f\2\u0140\u013e\3\2\2\2\u0141"+
		"\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2\2\2\u01439\3\2\2\2"+
		"\u0144\u0142\3\2\2\2!=CLYbeky\u0082\u008a\u00a9\u00b6\u00bf\u00c4\u00c7"+
		"\u00cb\u00d4\u00dc\u00e2\u00e7\u00f0\u00f5\u0100\u0105\u0112\u011f\u0124"+
		"\u012b\u0136\u013b\u0142";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}