package absyn;

import java.io.FileWriter;
import java.io.IOException;

import semantical.TypeChecker;
import types.ClassType;
import types.FixtureSignature;
import types.VoidType;

public class FixtureDeclaration extends CodeDeclaration{

	public FixtureDeclaration(int pos,
			Command body, ClassMemberDeclaration next) {
		super(pos, null, body, next);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void toDotAux(FileWriter where) throws IOException {
		linkToNode("body", getBody().toDot(where), where);
	}

	@Override
	protected void addTo(ClassType clazz) {
		FixtureSignature fSign= new FixtureSignature(clazz, this);
		clazz.addFixture(fSign);
		setSignature(fSign);
	}

	@Override
	protected void typeCheckAux(ClassType currentClass) {
		TypeChecker checker = new TypeChecker(VoidType.INSTANCE, currentClass.getErrorMsg());
		checker = checker.putVar("this", currentClass);

		// we type-check the fixture of the constructor in the resulting type-checker
		getBody().typeCheck(checker);

		// we check that there is no dead-code in the body of the fixture
		getBody().checkForDeadcode();
	}

}
