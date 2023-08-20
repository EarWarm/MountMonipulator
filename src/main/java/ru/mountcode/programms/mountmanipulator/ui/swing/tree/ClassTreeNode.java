package ru.mountcode.programms.mountmanipulator.ui.swing.tree;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.programms.mountmanipulator.utils.Strings;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Comparator;

public class ClassTreeNode extends DefaultMutableTreeNode implements Comparator<TreeNode> {

    public ClassNode member;
    private String text;

    public ClassTreeNode(ClassNode classNode) {
        this.member = classNode;
        updateClassName();
    }

    public ClassTreeNode(String pckg) {
        this.member = null;
        this.text = pckg;
    }

    public void updateClassName() {
        if (member != null) {
            String topName = getTopName();
            this.text = "<html>" + topName;
        }
    }

    private String getTopName() {
        String[] split = member.name.split("/");
        return Strings.min(split[split.length - 1], 50);
    }

    public void sort() {
        if (children != null) {
            children.sort(this);
        }
    }

    @Override
    public String toString() {
        return text;
    }

    public void combinePackage(ClassTreeNode pckg) {
        if (pckg.member != null) {
            throw new IllegalArgumentException("cannot merge package with file");
        }
        if (pckg == this) {
            throw new IllegalArgumentException("cannot merge itself");
        }
        if (!children.contains(pckg)) {
            throw new IllegalArgumentException("package is not a child");
        }
        if (this.getChildCount() != 1) {
            throw new IllegalArgumentException("child count over 1");
        }
        text += "." + pckg.text; // combine package names
        this.removeAllChildren(); // remove old package

        // to avoid dirty OOB exceptions
        new ArrayList<>(pckg.children).forEach(m -> this.add((ClassTreeNode) m));
    }

    @Override
    public int compare(TreeNode node1, TreeNode node2) {
        boolean leaf1 = ((ClassTreeNode) node1).member != null;
        boolean leaf2 = ((ClassTreeNode) node2).member != null;

        if (leaf1 != leaf2) {
            return leaf1 ? 1 : -1;
        }
        return ((ClassTreeNode) node1).text.compareTo(((ClassTreeNode) node2).text);
    }
}
