package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.JUnitCore.*;

public class UnitTest 
{
	public static void main(String[] args)
	{
		System.out.print("Testing....\n");
		
		String[] testClasses = new String[] {
			"test.FascadeTest",
			"test.TransformerPollerTest",
			"test.ServerProxyTest",
            "test.ModelTranslatorTest",
            "test.CommandsTest"
			};
		org.junit.runner.JUnitCore.main(testClasses);
		//"test.TransformerPollerTest""test.ServerProxyTest"
	}
}
