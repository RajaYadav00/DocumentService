package com.pdf.generate.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateRequest {

	private String templateType;
	private String waterMark;
	private String waterMarktype;
	private MetaData metadata;
	private Map<String,String> templateVariables;
}
