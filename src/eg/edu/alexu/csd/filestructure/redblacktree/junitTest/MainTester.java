/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.junit.runner.JUnitCore
 *  org.junit.runner.Result
 *  org.junit.runner.notification.Failure
 */
package eg.edu.alexu.csd.filestructure.redblacktree.junitTest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTester {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses((Class[]) new Class[] { IntegrationTest.class });
		Result result2 = JUnitCore.runClasses((Class[]) new Class[] { UnitTest.class });
		int totalNumOfTests = result.getRunCount() + result2.getRunCount();
		int totalFailures = result.getFailureCount() + result2.getFailureCount();
		System.out.println("Total tests passed: " + (totalNumOfTests - totalFailures) + "/" + totalNumOfTests);
		ArrayList<Failure> failures = new ArrayList<>();
		failures.addAll(result.getFailures());
		failures.addAll(result2.getFailures());
		for (Failure failure : failures) {
			System.out.println(failure.toString());
		}
	}
}
