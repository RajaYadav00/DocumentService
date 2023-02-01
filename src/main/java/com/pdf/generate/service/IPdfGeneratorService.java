package com.pdf.generate.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.DocumentException;
import com.pdf.generate.model.TemplateRequest;

public interface IPdfGeneratorService {

	/**
	 * this service method is used to return the pdf document after replacing
	 * dynamic variable and applying the watermark
	 * 
	 * @param templateRequest this variable will holds the detail of like dynamic
	 *                        variables and metadata
	 * @param response        this is used to return pdf in response to download
	 * @return
	 * @throws IOException this is used to handle input/output Exception
	 */
	public String exportPdf(TemplateRequest templateRequest, HttpServletResponse response)
			throws IOException, DocumentException;

	/**
	 * this service method is used to generate document on docx file format
	 * 
	 * @param templateRequest this variable will holds the detail of like dynamic
	 *                        variables and metadata
	 * @param response        this is used to return docx in response to download
	 * @throws IOException this is used to handle input/output Exception
	 */
	public void exportPdfFromDocx(TemplateRequest templateRequest, HttpServletResponse response) throws IOException;

}
