/*
 * Decompiled with CFR 0.139.
 */
package eg.edu.alexu.csd.filestructure.redblacktree.junitTest;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

public class ReflectionHelper {
    public static List<Class<?>> findClassesImplementing(Class<?> interfaceClass, Package fromPackage) {
        if (interfaceClass == null) {
            System.out.println("Unknown subclass.");
            return null;
        }
        if (fromPackage == null) {
            System.out.println("Unknown package.");
            return null;
        }
        ArrayList rVal = new ArrayList();
        try {
            Class<?>[] targets = ReflectionHelper.getAllClassesFromPackage(fromPackage.getName());
            if (targets != null) {
                for (Class<?> aTarget : targets) {
                    if (aTarget == null || aTarget.equals(interfaceClass) || !interfaceClass.isAssignableFrom(aTarget)) continue;
                    rVal.add(aTarget);
                }
            }
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error reading package name.");
        }
        catch (IOException e) {
            System.out.println("Error reading classes in package.");
        }
        return rVal;
    }

    private static Class<?>[] getAllClassesFromPackage(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert (classLoader != null);
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        ArrayList<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (File directory : dirs) {
            classes.addAll(ReflectionHelper.findClasses(directory, packageName));
        }
        return (Class<?>[]) classes.toArray(new Class[classes.size()]);
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        File[] files;
        ArrayList classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        for (File file : files = directory.listFiles()) {
            if (file.isDirectory()) {
                assert (!file.getName().contains("."));
                classes.addAll(ReflectionHelper.findClasses(file, String.valueOf(packageName) + "." + file.getName()));
                continue;
            }
            if (!file.getName().endsWith(".class")) continue;
            classes.add(Class.forName(String.valueOf(packageName) + '.' + file.getName().substring(0, file.getName().length() - 6)));
        }
        return ReflectionHelper.filterConcerteClasses(classes);
    }

    private static List<Class<?>> filterConcerteClasses(List<Class<?>> classes) {
        ArrayList filteredClasses = null;
        for (Class<?> fetchedClass : classes) {
            Integer modifiers = fetchedClass.getModifiers();
            if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers) || !Modifier.isPublic(modifiers)) continue;
            if (filteredClasses == null) {
                filteredClasses = new ArrayList();
            }
            filteredClasses.add(fetchedClass);
        }
        return filteredClasses;
    }
}

