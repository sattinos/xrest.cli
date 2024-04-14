package org.malsati.code_generation.templates;

public class RepositoryTemplate {
    public static String Template = """
            {AUTHOR_NOTE}

            package {PACKAGE_NAME};
                                   
            import {ENTITY_PACKAGE};
            
            import org.springframework.data.jpa.repository.JpaRepository;
            import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
            import org.springframework.stereotype.Repository;
                        
            @Repository
            public interface {ENTITY_NAME}Repository extends JpaRepository<{ENTITY_NAME}, {ENTITY_KEY}>,
                                                             JpaSpecificationExecutor<{ENTITY_KEY}> {
                // TODO: add new repository methods here
            }
            """;
}
