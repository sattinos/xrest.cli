package org.malsati.service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import org.malsati.models.EntityInfo;
import org.malsati.models.FieldInfo;

public class ProjectFileVisitor extends SimpleFileVisitor<Path> {
    private String targetAppPackageName;
    private String entityPath;
    private String appSrcPath;

    private List<EntityInfo> entityInfos = new ArrayList<>();

    public List<EntityInfo> getEntityInfos() {
        return entityInfos;
    }

    public EntityInfo getEntityInfoByName(String entityName) {
        List<EntityInfo> entity = entityInfos.stream().filter( e -> e.getName().equals(entityName)).collect(Collectors.toList());
        if( entity.isEmpty() ) {
            return null;
        }
        return entity.get(0);
    }

    public String getTargetAppPackageName() {
        return targetAppPackageName;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public String getAppSrcPath() {
        return appSrcPath;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
        if(  file.toString().endsWith(".java") ) {
            StaticJavaParser.getParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
            CompilationUnit compiledFile = StaticJavaParser.parse(file);

            compiledFile.accept(new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                    super.visit(n, arg);
                    n.getAnnotations().forEach(annotation -> {
                        if( annotation.getName().asString().equals("SpringBootApplication") ) {
                            var packageDeclaration = compiledFile.getPackageDeclaration().orElse(null);

                            if( packageDeclaration != null) {
                                targetAppPackageName = packageDeclaration.getNameAsString();
                                appSrcPath = file.getParent().toString();
                            }
                        }

                        if (annotation.getName().asString().equals("Entity")) {
                            var entityPackageName = "";
                            var packageDeclaration = compiledFile.getPackageDeclaration().orElse(null);

                            if( packageDeclaration != null) {
                                entityPackageName = packageDeclaration.getNameAsString();
                            }

                            entityPath = file.getParent().getParent().toString();

                            var entityInfo = new EntityInfo(entityPackageName, n.getNameAsString());
                            getFields(n, entityInfo);
                            entityInfos.add(entityInfo);
                        }
                    });
                }
            }, null);
        }
        return FileVisitResult.CONTINUE;
    }

    private static void getFields(ClassOrInterfaceDeclaration n, EntityInfo entityInfo) {
        for (FieldDeclaration field : n.getFields()) {
            field.getVariables().forEach(variable -> {
                String fieldName = variable.getNameAsString();
                String fieldType = resolveFullyQualifiedType(field.getElementType()).toString();
                entityInfo.getFields().add(new FieldInfo(fieldName, fieldType));
            });
        }
    }

    private static String resolveFullyQualifiedType(com.github.javaparser.ast.type.Type type) {
        if (type instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType classOrInterfaceType = (ClassOrInterfaceType) type;
            String typeName = classOrInterfaceType.getNameAsString();
            String scope = classOrInterfaceType.getScope().map(ClassOrInterfaceType::asString).orElse("");
            if (!scope.isEmpty()) {
                return scope + "." + typeName; // If it's a qualified type, combine scope and type name
            }
        }
        return type.asString(); // Return type name as is if not a qualified type
    }
}