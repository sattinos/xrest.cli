package org.malsati.service;

import org.malsati.models.EntityInfo;
import org.malsati.service.logging.ConsoleColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectScanner {
    private ProjectFileVisitor fileVisitor;

    public void scanEntities(String projectDirectoryPath) throws IOException {
        Path projectPath = Paths.get(projectDirectoryPath);
        fileVisitor = new ProjectFileVisitor();
        Files.walkFileTree(projectPath, fileVisitor);
    }

    public ProjectFileVisitor getFileVisitor() {
        return fileVisitor;
    }

    public void logStats() {
        if( fileVisitor.getEntityInfos().isEmpty() ) {
            System.out.println("no entities found.");
            return;
        }
        System.out.println("-----------------------------");
        System.out.printf("target app package name: %s %s\n", ConsoleColor.GREEN, fileVisitor.getTargetAppPackageName());
        System.out.print(ConsoleColor.RESET);
        System.out.printf("target app src path: %s %s\n", ConsoleColor.GREEN, fileVisitor.getAppSrcPath());
        System.out.print(ConsoleColor.RESET);
        System.out.printf("%d entities found\n", fileVisitor.getEntityInfos().size());
        for (var entity: fileVisitor.getEntityInfos()) {
            System.out.printf("\t- %s\n", entity.getName());
        }
        System.out.println("-----------------------------");
    }

    public EntityInfo getEntityByName(String entityName) {
        if( fileVisitor.getEntityInfos().isEmpty() ) {
            System.out.printf("no entities found.\nunable to find entity of name: %s\n", entityName);
            return null;
        }
        for (var entity: fileVisitor.getEntityInfos()) {
            if( entity.getName().equals(entityName) ) {
                return entity;
            }
        }
        System.out.printf("unable to find entity of name: %s\n", entityName);
        return null;
    }


}
