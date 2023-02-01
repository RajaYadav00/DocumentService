package com.pdf.generate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateVariables {

	private String variable;
	private String type;
	private String isMandatory;
	private String defaultValue;

}
