package absyn;

import java.io.FileWriter;
import java.io.IOException;

import bytecode.NEWSTRING;
import bytecode.VIRTUALCALL;
import semantical.TypeChecker;
import translation.Block;
import types.ClassType;
import types.TypeList;

public class Assert extends Command{

	private final Expression expression;
	private String fileName;
	private String errorPosition;
	
	public Assert(int pos, Expression expr) {
		super(pos);
		this.expression=expr;
	}
	
	protected void toDotAux(FileWriter where) throws IOException {
		linkToNode("expr", expression.toDot(where), where);
	}

	@Override
	protected TypeChecker typeCheckAux(TypeChecker checker) {
		fileName=checker.getFileName();
		errorPosition= checker.getPosition(getPos());
		
		expression.mustBeBoolean(checker);
		if(!checker.isAssertAllowed()){
			error("Assert can be used only in tests");
		}
		
		// we return the original type-checker. Hence local declarations
		// inside the then or _else are not visible after the conditional
		return checker;
	}

	@Override
	public boolean checkForDeadcode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block translate(Block continuation) {
		Block no= new NEWSTRING("test fallito @"+fileName+":"+errorPosition).followedBy(
				new VIRTUALCALL(ClassType.mk("String"), 
						ClassType.mk("String").
						methodLookup("output", TypeList.EMPTY)).followedBy(continuation));
		
		return expression.translateAsTest(continuation, no);
	}

}
