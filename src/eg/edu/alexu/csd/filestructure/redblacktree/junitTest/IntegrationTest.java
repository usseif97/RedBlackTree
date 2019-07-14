/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  org.junit.Assert
 *  org.junit.Test
 */
package eg.edu.alexu.csd.filestructure.redblacktree.junitTest;

import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {
    private final Class<?> redBlackTreeInterfaceToTest = IRedBlackTree.class;
    private final Class<?> treeMapInterfaceToTest = ITreeMap.class;

    @Test
    public void testCreationRedBlackTree() {
        List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(this.redBlackTreeInterfaceToTest, this.redBlackTreeInterfaceToTest.getPackage());
        Assert.assertNotNull((String)("Failed to create instance using interface '" + this.redBlackTreeInterfaceToTest.getName() + "' !"), candidateClasses);
        Assert.assertEquals((String)"You have more than one public implementation of the interface", (long)1L, (long)candidateClasses.size());
    }

    @Test
    public void testCreationTreeMap() {
        List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(this.treeMapInterfaceToTest, this.treeMapInterfaceToTest.getPackage());
        Assert.assertNotNull((String)("Failed to create instance using interface '" + this.treeMapInterfaceToTest.getName() + "' !"), candidateClasses);
        Assert.assertEquals((String)"You have more than one public implementation of the interface", (long)1L, (long)candidateClasses.size());
    }
}

