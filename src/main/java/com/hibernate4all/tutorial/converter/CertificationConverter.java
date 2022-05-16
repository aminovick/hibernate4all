package com.hibernate4all.tutorial.converter;

import com.hibernate4all.tutorial.domaine.Certification;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Lenovo on 27/03/2022.
 */
@Converter(autoApply = true)
public class CertificationConverter implements AttributeConverter<Certification, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Certification attribute) {
       return Optional.ofNullable(attribute.getKey()).orElse(null);

    }

    @Override
    public Certification convertToEntityAttribute(Integer dbData) {
       return    Stream.of(Certification.values()).filter(certification -> certification.getKey().equals(dbData)).findFirst().orElse(null);
    }
}
