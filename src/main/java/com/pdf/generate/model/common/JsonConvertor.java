package com.pdf.generate.model.common;

import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdf.generate.model.TemplateVariables;
import java.util.Collections;

@Converter(autoApply = true)
public class JsonConvertor implements AttributeConverter<List<TemplateVariables>, String> {
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<TemplateVariables> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			return null;
		}

	}

	@Override
	public List<TemplateVariables> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<List<TemplateVariables>>() {
			});
		} catch (JsonProcessingException e) {

			return Collections.emptyList();
		}
	}

}
