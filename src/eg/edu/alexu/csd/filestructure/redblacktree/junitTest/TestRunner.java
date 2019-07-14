/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 */
package eg.edu.alexu.csd.filestructure.redblacktree.junitTest;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import org.junit.Assert;

public class TestRunner {
    private static Class<?> implementation;
    private static boolean Debug;

    static {
        Debug = false;
    }

    public static Object getImplementationInstanceForInterface(Class<?> interfaceToTest) {
        TestRunner.initaiteforInterface(interfaceToTest);
        try {
            for (Constructor<?> constructor : implementation.getDeclaredConstructors()) {
                if (constructor.getParameterTypes().length != 0) continue;
                constructor.setAccessible(true);
                return constructor.newInstance(null);
            }
        }
        catch (Throwable constructor) {
            // empty catch block
        }
        return null;
    }

    public static void initaiteforInterface(Class<?> interfaceToTest) {
        List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(interfaceToTest, interfaceToTest.getPackage());
        Class<?> studentClass = candidateClasses.get(0);
        implementation = studentClass;
    }

    public static void fail(String message, Throwable throwable) {
        try {
            StringBuffer log = new StringBuffer();
            if (message != null) {
                log.append(message).append("\n");
            }
            if (throwable != null) {
                log.append(TestRunner.showError(throwable));
            }
            Assert.fail((String)log.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String showError(Throwable e) throws IOException {
        StringBuffer buffer;
        block6 : {
            block5 : {
                if (e == null) {
                    return "Error!";
                }
                e.printStackTrace();
                buffer = new StringBuffer();
                if (Debug) {
                    buffer.append("\t\t\tError: " + e + " " + e.getMessage());
                } else {
                    buffer.append("\t\t\tError: " + e);
                }
                if (!Debug) break block5;
                for (StackTraceElement trace : e.getStackTrace()) {
                    buffer.append("\n" + trace.getClassName() + "." + trace.getMethodName() + "(): Line " + trace.getLineNumber());
                }
                break block6;
            }
            if (implementation == null) break block6;
            for (StackTraceElement trace : e.getStackTrace()) {
                if (!trace.getClassName().equals(implementation.getName())) continue;
                buffer.append("\n" + trace.getClassName() + "." + trace.getMethodName() + "(): Line " + trace.getLineNumber());
            }
        }
        return buffer.toString().replaceAll("\\n", "\n\t\t\t\t");
    }
}

