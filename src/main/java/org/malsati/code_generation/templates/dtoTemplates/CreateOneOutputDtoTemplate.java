package org.malsati.code_generation.templates.dtoTemplates;

public class CreateOneOutputDtoTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}
                
            package {PACKAGE_NAME};

            import lombok.AllArgsConstructor;
            import lombok.Data;
            import lombok.NoArgsConstructor;
            import lombok.EqualsAndHashCode;
            
            // TODO: add necessary imports here
                        
            @Data
            @EqualsAndHashCode(callSuper = true)
            @AllArgsConstructor
            @NoArgsConstructor
            public class CreateOne{ENTITY_NAME}OutputDto extends CreateOne{ENTITY_NAME}InputDto {
                private {ENTITY_KEY} id;
            }
            """;
}