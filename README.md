# xrest.cli
This is the official cli of [XRest](https://github.com/sattinos/xrest) library

# Motivation
After understanding how to use the library, you want to scaffold the list of classes needed for an entity CRUD functionality.
This cli is mainly concerned about scaffolding.

# Prerequisites
1. Java 17 runtime or higher 
1. the cli jar: xrest-cli.1.0.1.jar
2. your target web project should have:
   - [Spring Boot](https://spring.io/projects/spring-boot)
   - [Mapstruct](https://mapstruct.org/)
   - JPA
   - [XRest](https://github.com/sattinos/xrest) 1.0.13 or higher 

# How to use
1. Download the cli JAR file (xrest-cli.1.0.1.jar)
2. Trigger the cli code generation command by writing this on a terminal:
```shell
java -jar xrest-cli.1.0.1.jar -e YOUR_ENTITY_NAME -p PATH_OF_YOUR_PROJECT
``` 

Once triggered, the cli scans your project and searches for an entity with the name that your submitted. <br>

If found, the following classes will be generated:
1. dto classes for all the CRUD endpoints (input/output dtos)
2. repository/service/mapper/controller for the chosen entity.
 
once scaffolded, you need to add your custom logic and follow the XRest steps as usual.

**example:**
- you have a web project at the path: d:/programs/reporter
- this project contains an entity named "Contract"
- by invoking the command:
```shell
java -jar xrest-cli.1.0.1.jar -e Contract -p d:/programs/reporter
``` 

The scaffold process will start and a new set of classes and folders will be created inside your target project:<br>

Let's assume:

   srcPath = d:/programs/reporter/src/main/java/com/malsati/reporter
  
         dto
            contract
               {srcPath}/dto/contract/CreateOneContractInputDto.java
               {srcPath}/dto/contract/CreateOneContractOutputDto.java
               {srcPath}/dto/contract/UpdateOneContractInputDto.java
               {srcPath}/dto/contract/DeleteOneContractOutputDto.java
               {srcPath}/dto/contract/GetOneContractOutputDto.java
         infrastructure
            {srcPath}/infrastructure/ContractRepository.java
         mapper
            {srcPath}/mapper/ContractMapper.java           
         services
            {srcPath}/services/ContractService.java
         controllers
            {srcPath}/controllers/ContractController.java

This is a sample cli output:
