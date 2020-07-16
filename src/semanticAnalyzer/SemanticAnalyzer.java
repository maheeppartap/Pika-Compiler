package semanticAnalyzer;

import parseTree.*;


public class SemanticAnalyzer {
	ParseNode ASTree;
	
	public static ParseNode analyze(ParseNode ASTree) {
		SemanticAnalyzer analyzer = new SemanticAnalyzer(ASTree);
		
		analyzer.PREPROCESSOR_ACCEPTOR();
		analyzer.analyze();

		return ASTree;
	}
	public SemanticAnalyzer(ParseNode ASTree) {
		this.ASTree = ASTree;
	}
	
	public void PREPROCESSOR_ACCEPTOR() {
		SemanticEarlyVisitor spv = new SemanticEarlyVisitor();
		ASTree.accept(spv);
	}
	
	public void analyze() {
		SemanticAnalysisVisitor sav = new SemanticAnalysisVisitor();
		ASTree.accept(sav);
		sav.promoter.promote();
	}
}
