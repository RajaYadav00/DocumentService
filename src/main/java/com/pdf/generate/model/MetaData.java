package com.pdf.generate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaData {
	
	private String title;
	private String subject;
	private String creationDate;
	private String updatedDate;
	private String creator;

}
