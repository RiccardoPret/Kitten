package types;

import absyn.CodeDeclaration;
import translation.Block;

public class TestSignature extends CodeSignature{

	public TestSignature(ClassType clazz, String name, CodeDeclaration abstractSyntax) {
		super(clazz, VoidType.INSTANCE, TypeList.EMPTY, name, abstractSyntax);
	}

	@Override
	protected Block addPrefixToCode(Block code) {
		return code;
	}

}
