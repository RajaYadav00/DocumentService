package com.pdf.generate.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.DocumentException;
import com.pdf.generate.model.TemplateRequest;
import com.pdf.generate.service.IPdfGeneratorService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/template")
public class GeneratePdfController {


	@Autowired
	IPdfGeneratorService iPdfservice;

	/**
	 * This method is used to generate pdf document from template using variables
	 * given during generating pdf
	 * 
	 * @param templateRequest this variable will holds the detail of like dynamic
	 *                        variables and metadata
	 * @param response        this is used to return pdf in response to download
	 * @return
	 * @throws IOException       this is used to handle input/output Exception
	 * @throws DocumentException Signals that an error has occurred in a Document
	 */
	@PostMapping(value = "/generateDocument")
	public ResponseEntity<String> generatePdf(@RequestBody TemplateRequest templateRequest,
			HttpServletResponse response) throws IOException, DocumentException {

		log.info("working fine upto generatepdf controller");

		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=pdf_" + templateRequest.getMetadata().getTitle() + "_"
				+ currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		iPdfservice.exportPdf(templateRequest, response);

		return new ResponseEntity<>("Successfully", HttpStatus.OK);

	}

}
