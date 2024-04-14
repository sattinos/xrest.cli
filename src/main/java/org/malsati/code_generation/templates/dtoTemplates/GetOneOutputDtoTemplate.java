package org.malsati.code_generation.templates.dtoTemplates;

public class GetOneOutputDtoTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}
            
            package {PACKAGE_NAME};
                
            public class GetOne{ENTITY_NAME}OutputDto extends CreateOne{ENTITY_NAME}OutputDto {
            }
            """;
}
