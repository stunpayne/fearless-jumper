package com.stunapps.fearlessjumper.rules.execution;

/**
 * Created by sunny.s on 19/04/18.
 */

public interface RuleExecutor<Q extends RuleRequest, R extends RuleResponse>
{
	void execute(Q ruleRequest, R ruleResponse);
}
