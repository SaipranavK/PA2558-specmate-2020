package com.specmate.cause_effect_patterns.parse.matcher;

import java.util.ArrayList;
import java.util.List;


import com.specmate.cause_effect_patterns.parse.DependencyParsetree;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchUtil {
	/**
	 * Applies a list of matchers on the given dependency data object.
	 * 
	 * @param rules
	 * @param data
	 * @return A List of Results for each head of the dependency data object.
	 */
	public static List<MatchResult> evaluateRuleset(List<MatchRule> rules, DependencyParsetree data) {
		List<MatchResult> result = new ArrayList<>();
		for(Token head: data.getHeads()) {
			result.add(MatchUtil.evaluateRuleset(rules, data, head));
		}
		return result; 
	}
	
	private MatchUtil()
	{
		
	}
	
	
	/**
	 * Recursively applies the list of matchers on the given dependency data object starting at the head token.
	 * 
	 * @param rules
	 * @param data
	 * @param head
	 * @return
	 */
	public static MatchResult evaluateRuleset(List<MatchRule> rules, DependencyParsetree data, Token head) {
		return evaluateRuleset(rules, data, head, 0);
	}
		
	private static MatchResult evaluateRuleset(List<MatchRule> rules, DependencyParsetree data, Token head, int ruleOffset) {
		MatchResult result = MatchResult.unsuccessful();
		
		List<MatchRule> offsetedRules = rules.subList(ruleOffset, rules.size());
		for(MatchRule rule: offsetedRules) {
			result = rule.match(data, head);
			if(result.isSuccessfulMatch()) {
				evaluateRulesetOnSubtrees(rules, rule, data, head, result);
				break;
			}
		}
		
		return result;
	}
	
	private static void evaluateRulesetOnSubtrees(List<MatchRule> rules, MatchRule currentRule, 
			DependencyParsetree data, Token head, MatchResult result) {
		for(String submatchName: result.getSubmatchNames()) {
			MatchResult sub = result.getSubmatch(submatchName);
			DependencyParsetree subData = sub.getMatchTree();
			Token subHead = subData.getHeads().stream().findFirst().get();
			
			MatchResult recursiveCall;
			if(subData.getTreeFragmentText().equals(data.getTreeFragmentText()) && subHead.equals(head)) {
				int newOffset = rules.indexOf(currentRule) + 1;
				recursiveCall = evaluateRuleset(rules, subData, subHead, newOffset);
			} else {
				recursiveCall = evaluateRuleset(rules, subData, subHead);
			}
			
			if(recursiveCall.isSuccessfulMatch()) {
				sub.setRuleName(recursiveCall.getRuleName());
				for( String subKey: recursiveCall.getSubmatchNames()) {
					MatchResult subRes =  recursiveCall.getSubmatch(subKey);
					if(!subRes.hasRuleName() ) {
						subRes.setRuleName(recursiveCall.getRuleName());
					}
					sub.addSubmatch(subKey, subRes);
				}
			}
		}
	}
	
}
