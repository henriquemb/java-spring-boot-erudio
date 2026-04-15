package com.github.henriquemb.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.model.Person;
import com.github.henriquemb.serializer.GenderDozerConverter;

import java.util.ArrayList;
import java.util.List;

import static com.github.dozermapper.core.loader.api.FieldsMappingOptions.customConverter;
import static com.github.dozermapper.core.loader.api.TypeMappingOptions.oneWay;

public class ObjectMapper {
    private static final Mapper mapper = DozerBeanMapperBuilder
            .create()
            .withMappingBuilder(new BeanMappingBuilder() {
                @Override
                protected void configure() {
                    mapping(Person.class, PersonDTO.class, oneWay()).fields("gender", "gender", customConverter(GenderDozerConverter.class));
                }
            }).build();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinations = new ArrayList<>();
        origin.forEach(d -> destinations.add(mapper.map(d, destination)));

        return destinations;
    }
}
