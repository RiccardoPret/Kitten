package absyn;

import java.io.FileWriter;
import java.io.IOException;

import semantical.TypeChecker;
import translation.Block;
import types.ClassType;
import bytecode.NEWSTRING;
import bytecode.RETURN;


public class Assert extends Command{

	private final Expression expression;
	//private String fileName;
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
		//fileName=checker.getFileName();
		errorPosition= checker.getPosition(getPos());
		
		expression.mustBeBoolean(checker);
		
		//Check if the assert is in a test
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
		continuation.doNotMerge();
		
		//If any assert fails, its test fails and we don't check the other asserts
		//and we return the error position to print in the main of CTest
		Block no= new NEWSTRING(""+errorPosition)
				//.followedBy(new VIRTUALCALL(ClassType.mk("String"), ClassType.mk("String").methodLookup("output", TypeList.EMPTY))
				//.followedBy(new CONST(false)
				.followedBy(new Block(new RETURN(ClassType.mk("String"))));//));
		
		return expression.translateAsTest(continuation, no);
	}

}
