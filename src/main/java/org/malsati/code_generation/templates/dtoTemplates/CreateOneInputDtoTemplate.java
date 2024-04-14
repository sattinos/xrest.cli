package org.malsati.code_generation.templates.dtoTemplates;

public class CreateOneInputDtoTemplate {
    public static final String Template = """
{AUTHOR_NOTE}

package {PACKAGE_NAME};

import lombok.Data;                

// TODO: add necessary imports here

@Data
public class CreateOne{ENTITY_NAME}InputDto {
    /*
        // TODO: uncomment fields as required
        {FIELDS_HERE}
    */
}
                """;
}
