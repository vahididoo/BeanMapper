package com.appurate.intellij.plugin.atf.generator;

import com.appurate.intellij.plugin.atf.typesystem.ATFType;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiClass;
import com.appurate.intellij.plugin.atf.typesystem.psi.ATFPsiProperty;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by vmansoori on 1/14/2016.
 */
public abstract class MappingGenerator {

    private static final String SETTER_PREFIX = "set";
    protected final PsiClass sourceClass;
    protected final PsiClass targetClass;
    protected final PsiClass mappingClass;
    protected final PsiElementFactory elementFactory;
    private PsiMethod mappingOrchestrator;
    private final Project project;

    public MappingGenerator(Project project, ATFType input, ATFType output, OutputType outputType) {
        this.project = project;
        elementFactory = getElementFactory();

        sourceClass = getPsiClass(input);
        targetClass = getPsiClass(output);
        mappingClass = this.createMappingClass(sourceClass, targetClass);
        mappingOrchestrator = addOrchestratorMethod(mappingClass);
    }


    public void map(String sourceFieldName, String targetFieldName) {
//        this.map(new String[]{sourceFieldName}, targetFieldName);
    }


    public void map(ATFPsiProperty[] sourceFields, ATFType targetField) {
        String methodName = SETTER_PREFIX + formatNameForMethod(targetField.getName());

        PsiMethod mappingMethod = elementFactory.createMethod(methodName, PsiType.VOID);
        for (ATFPsiProperty sourceField : sourceFields) {
            PsiParameter parameter = getPsiParameter(sourceField);
            mappingMethod.getParameterList().add(parameter);
        }
        PsiAnnotation annotation = elementFactory.createAnnotationFromText("@MappingMethod(" + targetField.getName() + ")", null);
        if (sourceFields.length > 1) {
            mappingMethod.getModifierList().setModifierProperty("abstract", true);
            mappingMethod.getBody().delete();
            if (!mappingClass.getModifierList().hasExplicitModifier("abstract")) {
                mappingClass.getModifierList().setModifierProperty("abstract", true);
            }
        } else {
            addConversionMethodBody(mappingMethod, sourceFields, targetField);
        }

        // TODO: 3/9/2016 Create the correct annotation

        mappingMethod.addBefore(annotation, mappingMethod.getFirstChild());

        PsiMethod[] allMethods = mappingClass.getAllMethods();
        for (int i = 0; i < mappingClass.getAllMethods().length; i++) {
            if (mappingClass.getAllMethods()[i].getName().equals(methodName)) {
                mappingClass.getAllMethods()[i].delete();
            }
        }
        mappingClass.addAfter(mappingMethod, allMethods.length - 1 > -1 ? allMethods[allMethods.length - 1] :
                mappingClass.getLBrace());

        addCallToOrchestrationMethod(mappingMethod);
    }

    @NotNull
    protected abstract PsiParameter getPsiParameter(ATFPsiProperty sourceField);

    @NotNull
    protected abstract PsiClass getPsiClass(ATFType atfType);

    private void cleanUpMethod(PsiMethod[] existingMethod) {
        PsiParameterList parameterList = existingMethod[0].getParameterList();
        for (PsiParameter psiParameter : parameterList.getParameters()) {
            parameterList.delete();
        }
    }

    protected PsiMethod addOrchestratorMethod(PsiClass mappingClass) {
        //Create the mapping orchestration method
        PsiMethod method = elementFactory.createMethod("doMap", PsiType.VOID);
        mappingClass.addBefore(method, mappingClass.getRBrace());
        return mappingClass.findMethodBySignature(method, true);
    }

    private void addCallToOrchestrationMethod(PsiMethod method) {
        StringBuilder methodCallText = new StringBuilder(method.getName()).append("(");
        for (PsiParameter psiParameter : method.getParameterList().getParameters()) {
            methodCallText.append("source.").append(psiParameter.getName()).append(",");
        }
        methodCallText.replace(methodCallText.lastIndexOf(","), methodCallText.lastIndexOf(",") + 1, ");");
        PsiCodeBlock methodBody = mappingOrchestrator.getBody();
        PsiStatement methodCallSttmnt = elementFactory.createStatementFromText("this." + methodCallText.toString(), methodBody.getContext());


        PsiStatement[] statements = methodBody.getStatements();
        for (int i = 0; i < statements.length; i++) {
            PsiStatement psiStatement = statements[i];
            if (psiStatement.getText().startsWith(method.getName()) && psiStatement.getText().equals(methodCallText.toString())) {
                statements[i].delete();

            }
        }
        methodBody.addAfter(methodCallSttmnt, statements.length > 0 ? statements[statements.length - 1] : methodBody.getLBrace());
    }


    public PsiClass getMappingClass() {
        return mappingClass;
    }

    private String formatNameForMethod(String targetFieldName) {
        return targetFieldName != null ? targetFieldName.length() > 2 ? targetFieldName.substring(0, 1) + targetFieldName.substring(1) : targetFieldName : targetFieldName;
    }

    protected PsiType getParameterType(String sourceFieldName, PsiClass sourceClass) {
        for (PsiField psiField : sourceClass.getFields()) {
            if (psiField.getName().equals(sourceFieldName)) {
                return psiField.getType();
            }
        }
        return null;
    }


    protected PsiClass createMappingClass(PsiClass source, PsiClass destination) {
        PsiClass aClass = elementFactory.createClass(formatNameForMethod(source.getName()) + "To" + formatNameForMethod(destination.getName()) + "Mapper");
        addConstructor(source, aClass, addField("source", source, aClass), addField("target", destination, aClass));
        return aClass;
    }

    @NotNull
    private PsiField addField(String fieldName, PsiClass source, PsiClass aClass) {
        PsiField sourceField = elementFactory.createField(fieldName, elementFactory.createTypeByFQClassName(source.getQualifiedName()));
        PsiElement lastField = aClass.getAllFields().length > 0 ? aClass.getAllFields()[aClass.getAllFields().length - 1] : aClass.getLBrace();
        aClass.addAfter(sourceField, lastField);
        return sourceField;
    }

    private void addConstructor(PsiClass source, PsiClass aClass, PsiField sourceField, PsiField targetField) {
        PsiMethod constructor = elementFactory.createConstructor();
        PsiParameter sourceParameter = elementFactory.createParameter("source", elementFactory.createTypeByFQClassName(source.getQualifiedName()));
        PsiParameter targetParameter = elementFactory.createParameter("target", elementFactory.createTypeByFQClassName(targetClass.getQualifiedName()));
        constructor.getParameterList().add(sourceParameter);
        constructor.getParameterList().add(targetParameter);
        PsiStatement sourceFldAssgnmnt = elementFactory.createStatementFromText("this." + sourceField.getName() + "=" + sourceParameter.getName() + ";", null);
        PsiStatement targetFldAssgnmnt = elementFactory.createStatementFromText("this." + targetField.getName() + "=" + targetParameter.getName() + ";", null);
        constructor.getBody().addAfter(targetFldAssgnmnt, constructor.getBody().getLBrace());
        constructor.getBody().addAfter(sourceFldAssgnmnt, constructor.getBody().getLBrace());
        aClass.addAfter(constructor, aClass.getLBrace());
    }


    protected Project getProject() {
        return project;
    }


    protected abstract void addConversionMethodBody(PsiMethod mappingMethod, ATFType[] sourceFields, ATFType targetParameter);

    protected abstract PsiElementFactory getElementFactory();
}
