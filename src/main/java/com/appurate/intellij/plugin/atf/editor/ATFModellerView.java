package com.appurate.intellij.plugin.atf.editor;

import com.appurate.intellij.plugin.atf.ExecutionUtil;
import com.appurate.intellij.plugin.atf.actions.ATFXMLModel;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeCellRenderer;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeNode;
import com.appurate.intellij.plugin.atf.editor.tree.ATFTreeSelecetionModel;
import com.appurate.intellij.plugin.atf.generator.MappingGenerator;
import com.appurate.intellij.plugin.atf.generator.OutputType;
import com.appurate.intellij.plugin.atf.generator.java.JavaMappingGenerator;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeFactory;
import com.appurate.intellij.plugin.atf.typesystem.ATFTypeManager;
import com.appurate.intellij.plugin.atf.typesystem.ATFProperty;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.rt.execution.application.MainAppClassLoader;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vmansoori on 12/17/2015.
 */
public class ATFModellerView {
    private final Project project;
    private JavaMappingGenerator generator;
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
    private JButton refreshButton;
    private ATFTypeFactory typeFactory;
    private TreePath[] sourceSelectedFields;
    private TreePath[] destinationSelectedFields;


    public ATFModellerView(Project project, FileEditor editor, VirtualFile file) {
        this.project = project;
        typeFactory = ATFTypeManager.getInstance(project).getTypeFactory("java");
        try {
            xmlModel = ATFXMLModel.readFile(file);
            String mappingClassName = xmlModel.getBindingClass();

            PsiFile[] filesByName = FilenameIndex.getFilesByName(project, mappingClassName + ".java", GlobalSearchScope
                    .projectScope
                            (project));
            if (filesByName.length > 0) {
                Class<?> aClass = Class.forName(mappingClassName, false, MainAppClassLoader.getSystemClassLoader());

            }
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
        generator = new JavaMappingGenerator(project, sourceATFType, destinationATFType, OutputType.JAVA);
    }

    protected JComponent generateMainPanel() {

        initializeModel();
        createSourcePanel();
        createActionPanel();
        createDestinationPanel();
        registerModelListener();
        return this.mainPanel;
    }

    private void createDestinationPanel() {
        this.destinationPanel.setLayout(new BoxLayout(this.destinationPanel, BoxLayout.PAGE_AXIS));
        this.destinationTree.setModel(new DefaultTreeModel(new ATFTreeNode(destinationATFType)));
        this.destinationTree.setCellRenderer(new ATFTreeCellRenderer(this.destinationTree));
        this.destinationTree.setRootVisible(true);
        this.destinationTree.setShowsRootHandles(false);
        this.destinationTree.setSelectionModel(new ATFTreeSelecetionModel(true));
        this.refreshButton.addActionListener(new RefreshActionListener(this.destinationTree));
        GridBagConstraints gbc = new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE, 1,
                1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints
                        .BOTH, new Insets(0, 0, 0, 0), 0, 0);

        this.mainPanel.add(this.destinationPanel, gbc);
    }

    private void createSourcePanel() {
        this.sourcePanel.setLayout(new BoxLayout(this.sourcePanel, BoxLayout.PAGE_AXIS));
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
        System.out.printf("CreateActionPanel Called.\n");
//        this.actionPanel.setLayout(new BoxLayout(this.actionPanel, BoxLayout.Y_AXIS));
        button1.setIcon(AllIcons.ToolbarDecorator.Add);
        button1.addActionListener(new AddMappingActionListener());
        button2.setIcon(AllIcons.ToolbarDecorator.Remove);
        actionPanel.add(button1);
        actionPanel.add(button2);
        for (ActionListener actionListener : button1.getActionListeners()) {
            System.out.printf(actionListener.toString() + "\n");
        }
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
        private MappingGenerator generator;

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.printf("Action performed called. " + e.getActionCommand());
            if (sourceTree.getSelectionPaths() == null || sourceTree.getSelectionPaths().length == 0 || destinationTree.getSelectionPaths() == null || destinationTree.getSelectionPaths().length == 0) {
                return;
            }

            java.util.List<ATFType> sourceFields = new ArrayList<>();
            for (TreePath treePath : sourceTree.getSelectionPaths()) {
                ATFType field = (((ATFTreeNode) treePath.getLastPathComponent())).getBasedOn();
                sourceFields.add(field);
            }

            JavaMappingGenerator generator = ATFModellerView.this.generator;
            ATFPsiProperty[] sourceFieldsArray = sourceFields.toArray(new ATFPsiProperty[sourceFields.size()]);
            ATFType destinationField = ((ATFTreeNode) destinationTree.getLastSelectedPathComponent()).getBasedOn();
            generator.map(sourceFieldsArray, destinationField);
            PsiClass mappingClass = generator.getMappingClass();

            try {
                xmlModel.addMapping(sourceFieldsArray, destinationField);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            mappingClass = (PsiClass) CodeStyleManager.getInstance(project).reformat(mappingClass);

            FileWriter writer = null;
            try {
                writer = new FileWriter(new File("/tmp/vahid.java"));
                writer.write(mappingClass.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        private MappingGenerator getMappingGenerator() throws ClassNotFoundException {
            if (generator == null) {
                generator = new JavaMappingGenerator(project, sourceATFType, destinationATFType, OutputType.JAVA);
            }
            return generator;
        }


        public String[] getSourceParamerNames() {

            String[] sourceParamerNames = new String[getSourceParameters().length];

            for (int i = 0; i < getSourceParameters().length; i++) {
                sourceParamerNames[i] = getSourceParameters()[i].getName();
            }
            return sourceParamerNames;
        }


        private ATFProperty[] getSources() {
            ATFProperty[] sources = new ATFProperty[sourceSelectedFields.length];
            if (sourceSelectedFields != null && sourceSelectedFields.length > 0) {
                for (int i = 0; i < sourceSelectedFields.length; i++) {
                    sources[i] = (ATFProperty) ((ATFTreeNode) sourceSelectedFields[i].getLastPathComponent())
                            .getBasedOn();
                }
            }
            return sources;

        }

        @Nullable
        private ATFProperty getDestination() {
            ATFType destination = null;
            TreePath destinationSelectedField;
            if (destinationSelectedFields != null && destinationSelectedFields.length > 0) {
                destinationSelectedField = destinationSelectedFields[destinationSelectedFields.length - 1];
                ATFTreeNode lastPathComponent = (ATFTreeNode) destinationSelectedField.getLastPathComponent();
                destination = lastPathComponent.getBasedOn();
            }
            return (ATFProperty) destination;
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


    private class RefreshActionListener implements ActionListener {
        public RefreshActionListener(Tree destinationTree) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTreeModel model = (DefaultTreeModel) destinationTree.getModel();
            model.setRoot(new ATFTreeNode(destinationATFType));
        }
    }
}
