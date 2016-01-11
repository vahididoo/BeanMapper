package com.appurate.intellij.plugin.atf.editor;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.actions.ATFXMLModel;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeCellRenderer;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeNode;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeSelecetionModel;
import com.appurate.intellij.plugin.atf.typesystem.*;
import com.appurate.intellij.plugin.atf.typesystem.java.JavaMappingHandler;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by vmansoori on 12/17/2015.
 */
public class ATFModellerView {
    private final Project project;
    //    private IDEAFile file;
    private ATFXMLModel xmlModel;
    private JPanel mainPanel;
    private Tree sourceTree;
    private Tree destinationTree;
    private JButton button1;
    private JButton button2;

    private ATFType destinationATFType;
    private ATFType sourceATFType;
    private JPanel destinationPanel;
    private JPanel actionPanel;
    private JPanel sourcePanel;
    private ATFTypeFactory typeFactory;
    private TreePath[] sourceSelectedFields;
    private TreePath[] destinationSelectedFields;
    private JavaMappingHandler _mappingHandler;


    public ATFModellerView(Project project, FileEditor editor, VirtualFile file) {
        this.project = project;
        typeFactory = ATFTypeManager.getInstance(project).getTypeFactory("java");
//        this.file = new IDEAFile(file);
        try {
            xmlModel = ATFXMLModel.readFile(file);
            ExecutionUtil.executeRead(new Computable() {
                public Object compute() {
                    ATFModellerView.this.init();
                    return null;
                }
            });
        } catch (Exception e) {
            Logger.getInstance(this.getClass()).error("Cannot create the XMLModel due to exception when marshalling " +
                    "the file", e.getMessage());
        }
    }


    public void init() {
        this.initializeModel();
    }

    private void initializeModel() {
        String sourceType = xmlModel.getSourceType();
        String destinationType = xmlModel.getDestinationType();
        String bindingClassType = xmlModel.getBindingClass();

        sourceATFType = typeFactory.getATFType(sourceType);
        destinationATFType = typeFactory.getATFType(destinationType);
        _mappingHandler = new JavaMappingHandler(project, (ATFClass) typeFactory.getATFType(bindingClassType));
    }

    protected JComponent generateMainPanel() {

        initializeModel();
//        this.mainPanel = new JPanel();
//        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        createSourcePanel();
        createActionPanel();
        createDestinationPanel();
        registerModelListener();
        return this.mainPanel;
    }

    private void createDestinationPanel() {
        this.destinationPanel.setLayout(new BoxLayout(this.destinationPanel,BoxLayout.PAGE_AXIS));
        this.destinationTree.setModel(new DefaultTreeModel(new ATFTreeNode(destinationATFType)));
        this.destinationTree.setCellRenderer(new ATFTreeCellRenderer(this.destinationTree));
        this.destinationTree.setRootVisible(true);
        this.destinationTree.setShowsRootHandles(false);
        this.destinationTree.setSelectionModel(new ATFTreeSelecetionModel(true));
        GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1,
                1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints
                        .BOTH, new Insets(0, 0, 0, 0), 0, 0);

        this.mainPanel.add(this.destinationPanel, gbc);
    }

    private void createSourcePanel() {
        this.sourcePanel.setLayout(new BoxLayout(this.sourcePanel,BoxLayout.PAGE_AXIS));
        this.sourceTree.setModel(new DefaultTreeModel(new ATFTreeNode(sourceATFType)));
        this.sourceTree.setCellRenderer(new ATFTreeCellRenderer(this.sourceTree));
        this.sourceTree.setRootVisible(true);
        this.sourceTree.setShowsRootHandles(false);
        this.sourceTree.setSelectionModel(new ATFTreeSelecetionModel(false));
        GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1,
                1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints
                        .BOTH, new Insets(0, 0, 0, 0), 0, 0);

        this.mainPanel.add(this.sourcePanel, gbc);
    }

    private void registerModelListener() {
        ATFTreeSelectionListener sourceSelectionListener = new ATFTreeSelectionListener();
        ATFTreeSelectionListener destinationSelectionListener = new ATFTreeSelectionListener();

        this.sourceTree.getSelectionModel().addTreeSelectionListener(sourceSelectionListener);
        this.destinationTree.getSelectionModel().addTreeSelectionListener(destinationSelectionListener);
    }


    private void createActionPanel() {
        this.actionPanel.setLayout(new BoxLayout(this.actionPanel,BoxLayout.Y_AXIS));
        button1.setIcon(AllIcons.ToolbarDecorator.Add);
        button1.addActionListener(new AddMappingActionListener());
        button2.setIcon(AllIcons.ToolbarDecorator.Remove);
//        GridBagConstraints gbc = new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1,
//                1, 0, 0,
//                GridBagConstraints.CENTER,
//                GridBagConstraints
//                        .NONE, new Insets(0, 0, 0, 0), 0, 0);
        actionPanel.add(button1);

        actionPanel.add(button2);
//        actionPanel.add(button3);
        GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1,
                1, 0, 0,
                GridBagConstraints.CENTER,
                GridBagConstraints
                        .BOTH, new Insets(0, 0, 0, 0), 0, 0);
        this.mainPanel.add(this.actionPanel, gbc);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }


    private class AddMappingActionListener implements ActionListener {
        private ATFProperty sourceParameters;

        @Override
        public void actionPerformed(ActionEvent e) {
            _mappingHandler.createOrUpdateMappingMethod(getDestinationFieldName(), getSourceParameters());
        }

        public String getDestinationFieldName() {
            TreePath field = destinationSelectedFields[destinationSelectedFields.length - 1];
            return ((ATFTreeNode) field.getLastPathComponent()).getBasedOn().getName();
        }

        public ATFProperty[] getSourceParameters() {
            java.util.List<ATFProperty> properties = new ArrayList<ATFProperty>(sourceSelectedFields.length);
            for (TreePath path : sourceSelectedFields) {
                ATFTreeNode node = (ATFTreeNode) path.getLastPathComponent();
                properties.add((ATFProperty) node.getBasedOn());
            }
            return properties.toArray(new ATFProperty[sourceSelectedFields.length]);
        }
    }


    public class ATFTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent event) {
            ATFTreeSelecetionModel source = (ATFTreeSelecetionModel) event.getSource();
            if (sourceTree.getSelectionModel() == source) {
                sourceSelectedFields = event.getPaths();
            } else {
                destinationSelectedFields = event.getPaths();
            }
        }
    }


}
