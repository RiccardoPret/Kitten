package absyn;

import java.io.FileWriter;
import java.io.IOException;

import semantical.TypeChecker;
import translation.Block;

public class Assert extends Command{

	private final Expression expr;
	
	public Assert(int pos, Expression expr) {
		super(pos);
		this.expr=expr;
	}
	
	protected void toDotAux(FileWriter where) throws IOException {
		linkToNode("expr", expr.toDot(where), where);
	}

	@Override
	protected TypeChecker typeCheckAux(TypeChecker checker) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkForDeadcode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block translate(Block continuation) {
		// TODO Auto-generated method stub
		return null;
	}

}
