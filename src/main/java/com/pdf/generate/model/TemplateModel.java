package com.pdf.generate.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pdf.generate.model.common.JsonConvertor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class TemplateModel {
 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "Template",columnDefinition = "text")
	private String template;
	
	@NotBlank
	private String templateType;
	
	@Column(name = "waterMark",columnDefinition = "text")
	private String waterMark;
	
	@Column(name = "templateVariables",columnDefinition = "json")
	@Convert(attributeName = "data",converter = JsonConvertor.class)
	private List<TemplateVariables> templateVariables;
}
