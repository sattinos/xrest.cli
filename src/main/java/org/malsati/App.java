package org.malsati;

import org.apache.commons.cli.*;
import org.malsati.code_generation.SourceCodeGenerator;
import org.malsati.service.ProjectScanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        process(args);
    }

    private static void process(String[] args) {
        var options = new Options();
        options.addOption("e", "entity", true, "the entity name to generate CRUD for.");
        options.addOption("p", "projectPath", true, "the root path of the project. If not specified, it will consider the current path.");

        if(args.length < 1) {
            displayHelp(options);
            return;
        }

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            var projectPath = "";

            var skip = false;
            if( !cmd.hasOption("entity") ) {
                System.err.println("missing entity argument");
                skip = true;
            }
            if( !cmd.hasOption("projectPath") ) {
                projectPath = System.getProperty("user.dir");
                System.out.printf("projectPath argument is not passed. System will default to current working directory\n");
            }

            if( skip ) {
                displayHelp(options);
                return;
            }

            var entityName = cmd.getOptionValue("entity");
            if( projectPath.isBlank() ) {
                projectPath = cmd.getOptionValue("projectPath");
            }

            var projectPathObj = Paths.get(projectPath);
            if(!Files.exists(projectPathObj)) {
                System.err.printf("invalid path: '%s'\naborting...", projectPath);
                return;
            }

            System.out.printf("\nprojectPath: %s\n", projectPath);
            System.out.printf("generating CRUD for entity: %s\n", entityName);

            var projectScanner = new ProjectScanner();
            projectScanner.scanEntities(projectPath);
            projectScanner.logStats();

            var entityInfo = projectScanner.getEntityByName(entityName);
            var targetAppPackageName = projectScanner.getFileVisitor().getTargetAppPackageName();
            if( entityInfo != null && !targetAppPackageName.isBlank() ) {
                SourceCodeGenerator sourceCodeGenerator = new SourceCodeGenerator(projectScanner.getFileVisitor().getAppSrcPath(), targetAppPackageName);
                sourceCodeGenerator.generateCRUD(entityInfo);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            displayHelp(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayHelp(Options options) {
        var helpFormatter = new HelpFormatter();
        var header = """
                
                This is a cli tool for xrest.
                It generates (DTOs, Mapper, Service, Controller) classes for a specific entity in your project.
                
                """;
        var footer = "\nPlease report issues at https://example.com/issues";
        helpFormatter.printHelp("xrest.cli", header, options, footer, true);
    }
}