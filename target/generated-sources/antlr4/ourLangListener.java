// Generated from ourLang.g4 by ANTLR 4.9.2

    package me.retran.skijaexample.javafxskija;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ourLangParser}.
 */
public interface ourLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ourLangParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoadStatement(ourLangParser.LoadStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ourLangParser#loadStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoadStatement(ourLangParser.LoadStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ourLangParser#saveStatement}.
	 * @param ctx the parse tree
	 */
	void enterSaveStatement(ourLangParser.SaveStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ourLangParser#saveStatement}.
	 * @param ctx the parse tree
	 */
	void exitSaveStatement(ourLangParser.SaveStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ourLangParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(ourLangParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ourLangParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(ourLangParser.StatementsContext ctx);
}