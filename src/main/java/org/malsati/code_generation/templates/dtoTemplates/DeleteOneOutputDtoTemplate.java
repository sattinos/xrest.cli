package org.malsati.code_generation.templates.dtoTemplates;

public class DeleteOneOutputDtoTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}
            
            package {PACKAGE_NAME};
            
            // TODO: add necessary imports here
                
            public class DeleteOne{ENTITY_NAME}OutputDto extends UpdateOne{ENTITY_NAME}InputDto {
            }
            """;
}
