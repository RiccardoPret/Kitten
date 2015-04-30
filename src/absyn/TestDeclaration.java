package absyn;

import java.io.FileWriter;
import java.io.IOException;

import types.ClassType;
import types.TestSignature;

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
	protected void typeCheckAux(ClassType currentClass) {
		// TODO Auto-generated method stub
		
	}

}
