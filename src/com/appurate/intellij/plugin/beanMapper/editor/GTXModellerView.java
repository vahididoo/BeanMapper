package com.appurate.intellij.plugin.beanMapper.editor;

import com.appurate.guidewire.studio.entityTransform.gtx.GTXModellerXMLModel;
import com.guidewire.configcenter.editor.StudioEditor;
import com.guidewire.configcenter.view.AbstractView;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import gw.lang.reflect.ITypeLoaderListener;
import gw.lang.reflect.RefreshRequest;
import gw.lang.reflect.module.IModule;
import gw.plugin.ij.filesystem.IDEAFile;
import gw.plugin.ij.util.ExecutionUtil;
import gw.plugin.ij.util.SafeCallable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Created by vmansoori on 12/17/2015.
 */
public class GTXModellerView extends AbstractView implements ITypeLoaderListener {
    private final IDEAFile file;
    private final GTXModellerXMLModel xmlModel;
    private JPanel mainPanel;
    private JPanel sourceFilterPanel;
    private JPanel destinationFilterPanel;
    private JTextField sourceName;
    private JTextField destinationName;
    private JButton generateEntityTreeBtn;
    private JButton generateBeanTreeBtn;
    private Tree sourceTree;

    private Tree destinationTree;


    public GTXModellerView(StudioEditor editor, VirtualFile file) {
        super(editor, false);
        this.file = new IDEAFile(file);
        xmlModel = GTXModellerXMLModel.readFile(file);
//        TypeSystem.addTypeLoaderListenerAsWeakRef(this);
        final IModule module = this.getModule();
        ExecutionUtil.execute(new SafeCallable(module) {
            public Object execute() {
                GTXModellerView.this.init();
                return null;
            }
        });
    }


    @Override
    protected JComponent generateMainPanel() {
        //setup the main panel
        this.mainPanel = new JPanel();

        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        //setup the Tree components
        this.sourceTree = new Tree();
        this.destinationTree = new Tree();

        //Setup the Tree panels
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setSize(new Dimension(50,50));
        leftPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(0, 0, 5, 0));


        //setup the text boxes
        leftPanel.add(makeSourceFilterPane(), "North");
        leftPanel.add(new JBScrollPane(this.sourceTree), "Center");
        rightPanel.add(makeDestinationFilterPanel(), "North");
        rightPanel.add(new JBScrollPane(this.destinationTree), "Center");

        //Add panels to main panel
        this.mainPanel.add(leftPanel,BorderLayout.EAST);
        this.mainPanel.add(rightPanel,BorderLayout.WEST);

/*
        //configure source Tree
        this.sourceTree.setRootVisible(true);
        this.sourceTree.setShowsRootHandles(false);

        //configure destination Tree
        this.destinationTree.setRootVisible(true);
        this.destinationTree.setShowsRootHandles(false);

        //Setting up a splitter
        Splitter leftSplitter = new Splitter(false, 0.5F);
        leftSplitter.setFirstComponent(leftPanel);
        leftSplitter.setSecondComponent(rightPanel);
*/


        return this.mainPanel;
    }

    private JPanel makeDestinationFilterPanel() {
        this.destinationFilterPanel = new JPanel(new BorderLayout(5, 5));
        destinationFilterPanel.add(new JLabel("Source Class"), "West");
        this.destinationName = new JTextField();

        this.destinationName.setBorder(null);
        destinationFilterPanel.add(this.sourceName, "Center");
        this.generateBeanTreeBtn = new JButton("Ok");
        destinationFilterPanel.add(this.generateBeanTreeBtn, "East");
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 0));
        destinationFilterPanel.add(spacer, "South");

        return destinationFilterPanel;
    }

    private JPanel makeSourceFilterPane() {
        this.sourceFilterPanel = new JPanel(new BorderLayout(5, 5));
        sourceFilterPanel.add(new JLabel("Source Class"), BorderLayout.WEST);
        this.sourceName = new JTextField();
        this.sourceName.setBorder(null);
        sourceFilterPanel.add(this.sourceName, BorderLayout.CENTER);
        this.generateEntityTreeBtn = new JButton("Ok");
        sourceFilterPanel.add(this.generateEntityTreeBtn, BorderLayout.EAST);
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 0));
        sourceFilterPanel.add(spacer, BorderLayout.SOUTH);
        return sourceFilterPanel;
    }

    @Override
    public void commitChanges() throws IOException {

    }

    @Override
    public void dispose() {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public Icon getIcon(int i) {
        return null;
    }

    @Override
    public void refreshedTypes(RefreshRequest refreshRequest) {

    }

    @Override
    public void refreshed() {

    }


}
