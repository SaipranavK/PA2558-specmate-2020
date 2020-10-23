package com.specmate.cause_effect_patterns.parse.wrapper;

public class BinaryMatchResultTreeNode extends MatchResultTreeNode {
	private MatchResultTreeNode left_var;
	private MatchResultTreeNode right_var;
	private RuleType type;
	
	public BinaryMatchResultTreeNode(MatchResultTreeNode left, MatchResultTreeNode right, RuleType type) {
		this.left_var = left;
		this.right_var = right;
		this.type = type;
	}
	
	public MatchResultTreeNode getFirstArgument() {
		return this.left_var;
	}
	
	public void setFirstArguement(MatchResultTreeNode node) {
		this.left_var = node;
	}
	
	public MatchResultTreeNode getSecondArgument() {
		return this.right_var;
	}
	
	public void setSecondArguement(MatchResultTreeNode node) {
		this.right_var = node;
	}
	
	protected void setType(RuleType type) {
		this.type = type;
	}
	
	public void leftSwap() {		
		BinaryMatchResultTreeNode left = (BinaryMatchResultTreeNode) getFirstArgument();
		MatchResultTreeNode right = getSecondArgument();
		
		MatchResultTreeNode childLeft = left.getFirstArgument();
		MatchResultTreeNode childRight = left.getSecondArgument();
		
		//Swap Types so the one with higher precedents gets shifted down
		RuleType tmp = this.type;
		this.type = left.getType();
		left.setType(tmp);
		
		this.left_var  = childLeft;
		this.right_var = left;
		left.left_var  = childRight;
		left.right_var = right;
	}

	public void rightSwap() {
		MatchResultTreeNode left = getFirstArgument();
		BinaryMatchResultTreeNode right = (BinaryMatchResultTreeNode) getSecondArgument();
		
		MatchResultTreeNode childLeft = right.getFirstArgument();
		MatchResultTreeNode childRight = right.getSecondArgument();
		
		RuleType tmp = this.type;
		this.type = right.getType();
		right.setType(tmp);
		
		this.left_var  = right;
		this.right_var = childRight;
		right.left_var  = left;
		right.right_var = childLeft;
	}

	@Override
	public RuleType getType() {
		return type;
	}
	
	@Override
	public void acceptVisitor(MatchTreeVisitor visitor) {
		visitor.visit(this);
	}
}
