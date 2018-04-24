package com.stunapps.fearlessjumper.rules.execution.generation;

import com.stunapps.fearlessjumper.rules.execution.RuleExecutor;

import org.roboguice.shaded.goole.common.collect.Lists;

import java.util.List;

/**
 * Created by sunny.s on 19/04/18.
 */

public class GenerationRuleExecutor implements
		RuleExecutor<GenerationRuleRequest, GenerationRuleResponse>
{
	private List<GenerationRule> rules;

	public GenerationRuleExecutor()
	{
		rules = Lists.newArrayList();
	}

	public GenerationRuleExecutor(List<GenerationRule> rules)
	{
		this.rules = rules;
	}

	public void addRule(GenerationRule rule)
	{
		if (rules == null || rules.isEmpty())
		{
			rules = Lists.newArrayList();
		}
		rules.add(rule);
	}

	@Override
	public void execute(GenerationRuleRequest ruleRequest, GenerationRuleResponse ruleResponse)
	{
		for (GenerationRule rule : rules)
		{
			if (rule.execute(ruleRequest, ruleResponse))
			{
				return;
			}
		}
	}
}
