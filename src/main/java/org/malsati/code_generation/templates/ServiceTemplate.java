package org.malsati.code_generation.templates;

public class ServiceTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}

            package {PACKAGE_NAME};            
          
            import {APP_PACKAGE}.dto.{ENTITY_INSTANCE_NAME}.*;
            import {ENTITY_PACKAGE};
            
            import {APP_PACKAGE}.infrastructure.{ENTITY_NAME}Repository;
            import {APP_PACKAGE}.mapper.{ENTITY_NAME}Mapper;
                        
            import org.malsati.xrest.dto.errors.AppError;
            import org.malsati.xrest.dto.errors.ErrorCode;
            import org.malsati.xrest.service.CrudServiceORM;
            import org.malsati.xrest.utilities.tuples.Pair;
                        
            import org.springframework.beans.factory.annotation.Autowired;
            import org.springframework.stereotype.Service;
                        
            import java.util.ArrayList;
            import java.util.Objects;
                        
            @Service
            public class {ENTITY_NAME}Service extends CrudServiceORM<
                    {ENTITY_NAME},
                    {ENTITY_KEY},
                    CreateOne{ENTITY_NAME}InputDto,
                    CreateOne{ENTITY_NAME}OutputDto,
                    UpdateOne{ENTITY_NAME}InputDto,
                    DeleteOne{ENTITY_NAME}OutputDto,
                    GetOne{ENTITY_NAME}OutputDto> {
                public {ENTITY_NAME}Service(
                        {ENTITY_NAME}Repository entityRepository,
                        {ENTITY_NAME}Mapper mapper) {
                    super(entityRepository, mapper);
                }
                        
                // Uncomment to add custom logic here
                /*
                @Override
                public ArrayList<AppError> validateCreateOneInput(CreateOne{ENTITY_NAME}InputDto createOne{ENTITY_NAME}InputDto) {                                        
                    ArrayList<AppError> validations = new ArrayList<>();
                    // Add validation rules here as required
                    return validations;
                }
                */
                        
                // Uncomment to add custom logic here
                /*
                @Override
                public ArrayList<AppError> validateUpdateOneInput(UpdateOne{ENTITY_NAME}InputDto updateOne{ENTITY_NAME}InputDto, {ENTITY_NAME} {ENTITY_INSTANCE_NAME}) {
                    ArrayList<AppError> validations = new ArrayList<>();
                    // Add validation rules here as required                    
                    return validations;
                }
                */
                
                // Uncomment to add custom logic here
                /*                       
                @Override
                protected void onPreCreateOne(CreateOne{ENTITY_NAME}InputDto createOne{ENTITY_NAME}InputDto, {ENTITY_NAME} {ENTITY_INSTANCE_NAME}) {
                    // Add custom logic as required here                    
                }
                */
                        
                // Uncomment to add custom logic here
                /*
                @Override
                protected void onPreUpdateOne(UpdateOne{ENTITY_NAME}InputDto updateOne{ENTITY_NAME}InputDto, {ENTITY_NAME} {ENTITY_INSTANCE_NAME}) {
                    // Add custom logic as required here   
                }
                */
                        
                // Uncomment to add custom logic here
                /*
                @Override
                protected void onPreDeleteOne({ENTITY_NAME} {ENTITY_INSTANCE_NAME}) {
                   // Add custom logic as required here   
                }
                */
            }          
            """;
}
