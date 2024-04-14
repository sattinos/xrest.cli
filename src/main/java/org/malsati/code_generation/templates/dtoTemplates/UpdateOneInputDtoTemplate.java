package org.malsati.code_generation.templates.dtoTemplates;

public class UpdateOneInputDtoTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}

            package {PACKAGE_NAME};
            
            import org.malsati.xrest.entities.audit.interfaces.IdentityInfo;

            // TODO: add necessary imports here
          
            public class UpdateOne{ENTITY_NAME}InputDto extends CreateOne{ENTITY_NAME}OutputDto implements IdentityInfo<{ENTITY_KEY}> {
            }
            """;
}