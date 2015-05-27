package types;

import absyn.CodeDeclaration;
import translation.Block;

public class FixtureSignature extends CodeSignature{
	
	public FixtureSignature(ClassType clazz, CodeDeclaration abstractSyntax) {
		super(clazz, VoidType.INSTANCE, TypeList.EMPTY, "fixture"+count++, abstractSyntax);
	}

	@Override
	protected Block addPrefixToCode(Block code) {
		return code;
	}

	private static int count;
	/*
	public String toString(){
		return "FixturePret"+count++;
	}
	*/
}
