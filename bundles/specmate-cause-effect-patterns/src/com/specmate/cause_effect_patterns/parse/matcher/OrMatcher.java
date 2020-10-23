package com.specmate.cause_effect_patterns.parse.matcher;

import com.specmate.cause_effect_patterns.parse.DependencyParsetree;


import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

/**
 * Matcher that matches to the first of its options.
 * Does not do any backtracking.
 * 
 * @author Dominik
 *
 */
public class OrMatcher extends MatcherBase{
	private MatcherBase[] options;
	
	public OrMatcher(MatcherBase...options) {
		this.options = options;
	}
	
	@Override
	public String getRepresentation() {
		String result = "(";
		StringBuilder sb_outer=new StringBuilder();
		for (MatcherBase matcher : this.getArcChildren()) {
			if(result.length() > 1) {
				StringBuilder sb=new StringBuilder();
				result+=sb.append(" | ").toString();
			}
			result+= matcher.toString();
		}
		return result+sb_outer.append(")").toString();
	}
	
	@Override
	public MatchResult match(DependencyParsetree data, Token head) {
		for(MatcherBase option: options) {
			MatchResult res = option.match(data, head);
			if(res.isSuccessfulMatch()) {
				res.addSubtree(super.match(data, head));
				return res;
			}
		}
		return MatchResult.unsuccessful();
	}
}
