package com.pdf.generate.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.pdf.generate.model.TemplateVariables;
import com.pdf.generate.model.common.ResponseModel;
import com.pdf.generate.service.ITemplateService;
import lombok.extern.slf4j.Slf4j;

/**
 * This controller class is use to save the template
 * 
 * @author Raja Ram Yadav
 *
 */
@Slf4j
@RestController
public class TemplateController {

	@Autowired
	Gson gson;

	@Autowired
	ITemplateService iTemplateservice;

	/**
	 * This end point is used to save the template along with the template variable
	 * and the type of the template
	 * 
	 * @param template          this will hold the template in pdf format
	 * @param type              this is the type of template based on use of the
	 *                          particular template
	 * @param templateVariables these are the dynamic variable which are going to be
	 *                          replaced during template generation
	 * @param waterMark         this will hold the waterMark image or text
	 * @return it will return the success response with a message
	 */
	@PostMapping("/saveTemplate")
	public ResponseEntity<ResponseModel> saveTemplateFile(@RequestParam("template") MultipartFile template,
			@RequestParam("type") String type, @RequestParam("templateVariables") String templateVariables,
			@RequestParam("waterMark") MultipartFile waterMark) {

		log.info("we are inside the savetemplate method of the templateController");

		List<TemplateVariables> tempVar = gson.fromJson(templateVariables, List.class);

		return new ResponseEntity<>(iTemplateservice.saveTemplate(template, type, tempVar, waterMark), HttpStatus.OK);

	}

}
