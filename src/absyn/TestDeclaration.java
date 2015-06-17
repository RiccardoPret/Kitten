package absyn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import bytecode.NEWSTRING;
import bytecode.RETURN;
import semantical.TypeChecker;
import translation.Block;
import types.ClassMemberSignature;
import types.ClassType;
import types.TestSignature;
import types.VoidType;

public class TestDeclaration extends CodeDeclaration{

	private final String name;
	
	public TestDeclaration(int pos, String name, Command body,
			ClassMemberDeclaration next) {
		super(pos, null, body, next);
		this.name=name;
	}

	@Override
	protected void toDotAux(FileWriter where) throws IOException {
		linkToNode("name", toDot(name, where), where);
		linkToNode("body", getBody().toDot(where), where);
	}

	@Override
	protected void addTo(ClassType clazz) {
		TestSignature tSign = new TestSignature(clazz, name, this);
		clazz.addTest(tSign);
		setSignature(tSign);
	}

	
	
	@Override
	public void translate(Set<ClassMemberSignature> done) {
		if (done.add(getSignature())) {
    		process(getSignature().getDefiningClass(), done);
    		
    		//It's the same translate of CodeDeclaration with the addition of the return an empty
    		//string if all the asserts in getBody are passed then the test is passed.
    		//If an assert isn't passed then the Assert return a non empty string
    		getSignature().setCode(getBody()
    				.translate(new NEWSTRING("").followedBy(new Block(new RETURN(ClassType.mk("String"))))));

    		translateReferenced(getSignature().getCode(), done, new HashSet<Block>());
    	}
	}

	@Override
	protected void typeCheckAux(ClassType currentClass) {
		TypeChecker checker = new TypeChecker(VoidType.INSTANCE, currentClass.getErrorMsg(), true);
		checker = checker.putVar("this", currentClass);
		
		// we type-check the body of the constructor in the resulting type-checker
		getBody().typeCheck(checker);

		// we check that there is no dead-code in the body of the constructor
		getBody().checkForDeadcode();

	}

}
