package org.malsati.code_generation.templates;

public class MapperTemplate {
    public static final String Template = """
            {AUTHOR_NOTE}

            package {PACKAGE_NAME};         
            
            import {ENTITY_DTO_PACKAGE}.*;
            import {ENTITY_PACKAGE};
    
            import org.malsati.xrest.mapper.IMapper;
            import org.mapstruct.*;

            import java.util.List;

            @Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
            public interface {ENTITY_NAME}Mapper extends IMapper<
                    {ENTITY_NAME},
                    {ENTITY_KEY},
                    CreateOne{ENTITY_NAME}InputDto,
                    CreateOne{ENTITY_NAME}OutputDto,
                    UpdateOne{ENTITY_NAME}InputDto,
                    DeleteOne{ENTITY_NAME}OutputDto,
                    GetOne{ENTITY_NAME}OutputDto> {
                    
                @Override
                {ENTITY_NAME} createOneInputDtoToEntity(CreateOne{ENTITY_NAME}InputDto createOne{ENTITY_NAME}InputDto);
            
                @Override
                @Named("toCreateOne")
                CreateOne{ENTITY_NAME}OutputDto entityToCreateOneOutputDto({ENTITY_NAME} {ENTITY_INSTANCE_NAME});
            
                @Override
                List<{ENTITY_NAME}> createManyInputDtoToEntities(Iterable<CreateOne{ENTITY_NAME}InputDto> createManyInputDto);
            
                @Override
                @IterableMapping(qualifiedByName = "toCreateOne")
                List<CreateOne{ENTITY_NAME}OutputDto> entitiesToCreateManyOutputDto(List<{ENTITY_NAME}> {ENTITY_INSTANCE_NAME}s);
            
                @Override
                void updateOneInputDtoToEntity(UpdateOne{ENTITY_NAME}InputDto updateOne{ENTITY_NAME}InputDto, @MappingTarget {ENTITY_NAME} {ENTITY_INSTANCE_NAME});
            
                @Override
                DeleteOne{ENTITY_NAME}OutputDto entityToDeleteOneOutputDto({ENTITY_NAME} {ENTITY_INSTANCE_NAME});
            
                @Override
                List<DeleteOne{ENTITY_NAME}OutputDto> entitiesToDeleteManyOutputDto(List<{ENTITY_NAME}> {ENTITY_INSTANCE_NAME});
            
                @Override
                GetOne{ENTITY_NAME}OutputDto entityToGetOneOutputDto({ENTITY_NAME} {ENTITY_INSTANCE_NAME});
            }
            """;
}
