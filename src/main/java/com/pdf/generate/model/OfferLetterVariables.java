package com.pdf.generate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferLetterVariables {

	private String employeeName;
	private String companyName;
	private String date;
	private String title;
	private String executiveName;
	private String departmentName;
	private String salaryAmount;
	private String stackAmount;
	private String startingDate;

}
