package com.pdf.generate.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.pdf.generate.model.TemplateVariables;
import com.pdf.generate.model.common.ResponseModel;

public interface ITemplateService {

	/**
	 * This service method is used to save the template along with the template
	 * variable and the type of the template
	 * 
	 * @param template          this will hold the template in pdf format
	 * @param type              this is the type of template based on use of the
	 *                          particular template
	 * @param templateVariables these are the dynamic variable which are going to be
	 *                          replaced during template generation
	 * @param waterMark         this will hold the waterMark image or text
	 * @return it will return the success response with a message
	 */
	public ResponseModel saveTemplate(MultipartFile template, String type, List<TemplateVariables> templateVariables,
			MultipartFile waterMark);

}
