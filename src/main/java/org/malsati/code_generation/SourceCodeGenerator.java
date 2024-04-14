package org.malsati.code_generation;

import org.malsati.models.EntityInfo;
import org.malsati.code_generation.templates.*;
import org.malsati.code_generation.templates.dtoTemplates.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SourceCodeGenerator {

    private static class PlaceHolders {
        public static final String ENTITY_NAME = "\\{ENTITY_NAME\\}";
        public static final String ENTITY_KEY = "\\{ENTITY_KEY\\}";
    }

    private String projectPath;
    private String appPackageName;

    public SourceCodeGenerator(String projectPath, String packageName) {
        this.projectPath = projectPath;
        this.appPackageName = packageName;
    }

    public void generateCRUD(EntityInfo entityInfo) {
        try {
            var dtoPath = String.format("%s/dto/%s", projectPath, entityInfo.getName().toLowerCase());

            var infrastructurePath = String.format("%s/infrastructure", projectPath);
            var mapperPath = String.format("%s/mapper", projectPath);
            var servicesPath = String.format("%s/services", projectPath);
            var controllersPath = String.format("%s/controllers", projectPath);

            Files.createDirectories(Paths.get(dtoPath));
            Files.createDirectories(Paths.get(infrastructurePath));
            Files.createDirectories(Paths.get(mapperPath));
            Files.createDirectories(Paths.get(servicesPath));
            Files.createDirectories(Paths.get(controllersPath));


            var dtoPackageName = String.format("%s.dto.%s", appPackageName, entityInfo.getName().toLowerCase());
            var infraPackageName = String.format("%s.infrastructure", appPackageName);
            var mapperPackageName = String.format("%s.mapper", appPackageName);
            var servicePackageName = String.format("%s.services", appPackageName);
            var controllerPackageName = String.format("%s.controllers", appPackageName);

            var createOneInputDto = generateCreateOneInputDto(entityInfo, dtoPackageName);
            var createOneOuttputDtoCode = generateCreateOneOutputDto(entityInfo, dtoPackageName);
            var updateOneInputDtoCode = generateUpdateOneInputDto(entityInfo, dtoPackageName);
            var deleteOneOutputDtoCode = generateDeleteOneOutputDto(entityInfo, dtoPackageName);
            var getOneOutputDtoCode = generateGetOneOutputDto(entityInfo, dtoPackageName);
            var repositoryCode = generateRepositoryCode(entityInfo, infraPackageName);
            var mapperCode = generateMapperCode(entityInfo, mapperPackageName);
            var serviceCode = generateServiceCode(entityInfo, servicePackageName);
            var controllerCode = generateControllerCode(entityInfo, controllerPackageName);

            System.out.println(" ------------------");
            System.out.println("| generating files |");
            System.out.println(" ------------------");

            System.out.println("dto");

            // projectPath/dto/entityName/CreateOne{entityName}InputDto
            var destinationPath = Paths.get(String.format("%s/CreateOne%sInputDto.java", dtoPath, entityInfo.getName()));
            Files.write(destinationPath, createOneInputDto.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            // projectPath/dto/entityName/CreateOne{entityName}OutputDto
            destinationPath = Paths.get(String.format("%s/CreateOne%sOutputDto.java", dtoPath, entityInfo.getName()));
            Files.write(destinationPath, createOneOuttputDtoCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            // projectPath/dto/entityName/UpdateOne{entityName}InputDto
            destinationPath = Paths.get(String.format("%s/UpdateOne%sInputDto.java", dtoPath, entityInfo.getName()));
            Files.write(destinationPath, updateOneInputDtoCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            // projectPath/dto/entityName/DeleteOne{entityName}OutputDto
            destinationPath = Paths.get(String.format("%s/DeleteOne%sOutputDto.java", dtoPath, entityInfo.getName()));
            Files.write(destinationPath, deleteOneOutputDtoCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            // projectPath/dto/entityName/GetOne{entityName}OutputDto
            destinationPath = Paths.get(String.format("%s/GetOne%sOutputDto.java", dtoPath, entityInfo.getName()));
            Files.write(destinationPath, getOneOutputDtoCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            System.out.println("infrastructure");

            // projectPath/Infrastructore/{entityName}Repository
            destinationPath = Paths.get(String.format("%s/%sRepository.java", infrastructurePath, entityInfo.getName()));
            Files.write(destinationPath, repositoryCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            System.out.println("mapper");

            // projectPath/Mapper/{entityName}Mapper
            destinationPath = Paths.get(String.format("%s/%sMapper.java", mapperPath, entityInfo.getName()));
            Files.write(destinationPath, mapperCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            System.out.println("services");
            // projectPath/services/{entityName}Service
            destinationPath = Paths.get(String.format("%s/%sService.java", servicesPath, entityInfo.getName()));
            Files.write(destinationPath, serviceCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            System.out.println("controllers");
            // projectPath/controllers/{entityName}Controller
            destinationPath = Paths.get(String.format("%s/%sController.java", controllersPath, entityInfo.getName()));
            Files.write(destinationPath, controllerCode.getBytes());
            System.out.printf("\t %s\n", destinationPath);

            System.out.println("------------------------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateCreateOneInputDto(EntityInfo entityInfo, String packageName) {
        return generateCode(CreateOneInputDtoTemplate.Template, entityInfo, packageName);
    }

    private String generateCreateOneOutputDto(EntityInfo entityInfo, String packageName) {
        return generateCode(CreateOneOutputDtoTemplate.Template, entityInfo, packageName);
    }

    private String generateUpdateOneInputDto(EntityInfo entityInfo, String packageName) {
        return generateCode(UpdateOneInputDtoTemplate.Template, entityInfo, packageName);
    }

    private String generateDeleteOneOutputDto(EntityInfo entityInfo, String packageName) {
        return generateCode(DeleteOneOutputDtoTemplate.Template, entityInfo, packageName);
    }

    private String generateGetOneOutputDto(EntityInfo entityInfo, String packageName) {
        return generateCode(GetOneOutputDtoTemplate.Template, entityInfo, packageName);
    }

    private String generateRepositoryCode(EntityInfo entityInfo, String packageName) {
        return generateCode(RepositoryTemplate.Template, entityInfo, packageName);
    }

    private String generateMapperCode(EntityInfo entityInfo, String packageName) {
        return generateCode(MapperTemplate.Template, entityInfo, packageName);
    }

    private String generateServiceCode(EntityInfo entityInfo, String packageName) {
        return generateCode(ServiceTemplate.Template, entityInfo, packageName);
    }

    private String generateControllerCode(EntityInfo entityInfo, String packageName) {
        return generateCode(ControllerTemplate.Template, entityInfo, packageName);
    }


    private String generateCode(String template,
                                EntityInfo entityInfo,
                                String packageName) {
        var dtoPackageName = String.format("%s.dto.%s", appPackageName, entityInfo.getName().toLowerCase());

        var baked = template.replaceAll("\\{ENTITY_NAME\\}", entityInfo.getName());
        for (var fieldInfo : entityInfo.getFields()) {
            if (fieldInfo.name().contentEquals("id")) {
                baked = baked.replaceAll("\\{ENTITY_KEY\\}", fieldInfo.type());
            }
        }

        if (template.contains("{FIELDS_HERE}")) {
            var fieldsCode = generateCodeForFieldsSection(entityInfo);
            baked = baked.replace("{FIELDS_HERE}", fieldsCode.toString());
        }

        if( template.contains("{AUTHOR_NOTE}") ) {
            baked = baked.replace("{AUTHOR_NOTE}", AuthorNoteTemplate.Template);
        }

        if( template.contains("{PACKAGE_NAME}")) {
            baked = baked.replace("{PACKAGE_NAME}", packageName);
        }

        if( template.contains("{ENTITY_PACKAGE}")) {
            baked = baked.replace("{ENTITY_PACKAGE}", String.format("%s.%s", entityInfo.getPackageName(), entityInfo.getName()));
        }

        if( template.contains("{ENTITY_DTO_PACKAGE}") && dtoPackageName != null) {
            baked = baked.replace("{ENTITY_DTO_PACKAGE}", dtoPackageName);
        }

        if( template.contains("{APP_PACKAGE}") ) {
            baked = baked.replace("{APP_PACKAGE}", appPackageName);
        }

        if( template.contains("{ENTITY_INSTANCE_NAME}") ) {
            baked = baked.replace("{ENTITY_INSTANCE_NAME}", entityInfo.getName().toLowerCase());
        }

        return baked;
    }

    private String generateCodeForFieldsSection(EntityInfo entityInfo) {
        var fieldTemplate = "private {FIELD_TYPE} {FIELD_NAME};";

        var fieldsBuffer = new StringBuilder();
        for (var fieldInfo : entityInfo.getFields()) {
            var field = fieldTemplate.replace("{FIELD_TYPE}", fieldInfo.type());
            field = field.replace("{FIELD_NAME}", fieldInfo.name());
            fieldsBuffer.append(String.format("%s\n", field));
        }
        return fieldsBuffer.toString();
    }
}