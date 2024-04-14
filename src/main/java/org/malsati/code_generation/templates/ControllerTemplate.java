package org.malsati.code_generation.templates;

public class ControllerTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}

            package {PACKAGE_NAME};            
          
            import {APP_PACKAGE}.dto.{ENTITY_INSTANCE_NAME}.*;
            import {ENTITY_PACKAGE};
            
            import org.springframework.web.bind.annotation.RequestMapping;
            import org.springframework.web.bind.annotation.RestController;
                        
            import {APP_PACKAGE}.services.{ENTITY_NAME}Service;
            import org.malsati.xrest.controller.CrudController;

            @RequestMapping("/app/api/{ENTITY_INSTANCE_NAME}")
            @RestController
            public class {ENTITY_NAME}Controller extends CrudController<
                    {ENTITY_NAME},
                    {ENTITY_KEY},
                    CreateOne{ENTITY_NAME}InputDto,
                    CreateOne{ENTITY_NAME}OutputDto,
                    UpdateOne{ENTITY_NAME}InputDto,
                    DeleteOne{ENTITY_NAME}OutputDto,
                    GetOne{ENTITY_NAME}OutputDto> {
                public {ENTITY_NAME}Controller({ENTITY_NAME}Service {ENTITY_INSTANCE_NAME}Service) {
                    super({ENTITY_INSTANCE_NAME}Service);
                }
            }          
            """;
}