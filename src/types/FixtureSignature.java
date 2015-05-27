package types;

import absyn.CodeDeclaration;
import translation.Block;

public class FixtureSignature extends CodeSignature{
	
	public FixtureSignature(ClassType clazz, CodeDeclaration abstractSyntax) {
		super(clazz, VoidType.INSTANCE, TypeList.EMPTY, "fixture", abstractSyntax);
	}

	@Override
	protected Block addPrefixToCode(Block code) {
		return code;
	}
/*
	private int count;
	
	public String toString(){
		System.out.println("############");
		return "Fixture"+count++;
	}
*/
}
