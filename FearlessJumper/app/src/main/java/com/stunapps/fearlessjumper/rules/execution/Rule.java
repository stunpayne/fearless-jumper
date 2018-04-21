package com.stunapps.fearlessjumper.rules.execution;

/**
 * Created by sunny.s on 21/04/18.
 */

public abstract class Rule<Q extends RuleRequest, R extends RuleResponse>
{
	/**
	 * @param ruleRequest the RuleRequest for execution
	 * @param ruleResponse	the response wrapper
	 * @return boolean denoting whether execution should stop
	 */
	public abstract boolean execute(Q ruleRequest, R ruleResponse);
}
