package com.appurate.intellij.plugin.atf.generator.java;


import com.appurate.intellij.plugin.atf.generator.MappingGenerator;
import com.appurate.intellij.plugin.atf.generator.OutputType;
import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 3/9/2016.
 */
public class JavaMappingGenerator extends MappingGenerator {

    public JavaMappingGenerator(Project project, ATFType source, ATFType destination, OutputType outputType) {
        super(project, source, destination, outputType);
    }

    @Override
    protected void addConversionMethodBody(PsiMethod mappingMethod, ATFType[] sourceFields, ATFType targetParameter) {
        if (mappingMethod.getParameterList().getParameters().length == 1) {
            PsiParameter parameter = mappingMethod.getParameterList().getParameters()[0];

            PsiIfStatement ifStatement = (PsiIfStatement) elementFactory.createStatementFromText("if(" + parameter.getName() + "==null){}", null);
            ifStatement.setThenBranch(elementFactory.createStatementFromText("return null;", mappingMethod.getBody().getContext()));
            mappingMethod.getBody().addAfter(ifStatement, mappingMethod.getBody().getLBrace());

            PsiStatement psiStatement = elementFactory.createStatementFromText("target." + targetParameter.getName() + "=" + sourceFields[0].getName(), null);

            PsiStatement[] statements = mappingMethod.getBody().getStatements();
            mappingMethod.getBody().addAfter(psiStatement, statements[statements.length - 1]);

        }
    }

    @Override
    protected PsiElementFactory getElementFactory() {
        return JavaPsiFacade.getInstance(getProject()).getElementFactory(this.getProject());
    }

    @Override
    protected PsiClass getPsiClass(ATFType atfType) {
        Project project = this.getProject();
        if (!(atfType instanceof ATFPsiClass)) {
            // TODO: 3/19/2016 Handle types other than ATFPsiClass
        }
        ATFPsiClass atfPsiClass = (ATFPsiClass) atfType;
        return JavaPsiFacade.getInstance(project).findClass(((PsiClass) atfPsiClass.getBasedOn()).getQualifiedName(), GlobalSearchScope.projectScope(project));
    }

    @Override
    @NotNull
    protected PsiParameter getPsiParameter(ATFPsiProperty sourceField) {
        PsiType type = getType(sourceField);
        return elementFactory.createParameter(sourceField.getName(), type);
    }

    private PsiType getType(ATFPsiProperty sourceField) {
        return ((PsiField) sourceField.getBasedOn()).getType();
    }
}
