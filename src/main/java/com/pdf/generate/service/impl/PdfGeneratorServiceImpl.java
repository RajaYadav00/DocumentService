package com.pdf.generate.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.pdf.generate.model.TemplateRequest;
import com.pdf.generate.repository.TemplateRepository;
import com.pdf.generate.service.IPdfGeneratorService;
import com.pdf.generate.service.ISetPasswordRules;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PdfGeneratorServiceImpl implements IPdfGeneratorService {

	@Autowired
	ISetPasswordRules iSetPasswordRules;

	@Autowired
	TemplateRepository templateRepository;

	public PdfGeneratorServiceImpl(ISetPasswordRules iSetPasswordRules, TemplateRepository templateRepository) {
		super();
		this.iSetPasswordRules = iSetPasswordRules;
		this.templateRepository = templateRepository;
	}

	/**
	 * In this method we fetch the template from database which is in base64
	 * formate, decode the template and replace the dynamic variable this method
	 * will generate pdf document
	 */
	@Override
	public String exportPdf(TemplateRequest request, HttpServletResponse response)
			throws IOException, DocumentException {

		String template = templateRepository.findByTemplateType(request.getTemplateType()).getTemplate();

		byte[] byteTemplate = Base64.getDecoder().decode(template);

		File path = new File("./src/main/resources/templates/documentTemplate.pdf");

		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(byteTemplate);
		}

		PdfReader reader = new PdfReader("./src/main/resources/templates/documentTemplate.pdf");
		int pNumbers = reader.getNumberOfPages();
		PRStream streams;

		for (int i = 1; i <= pNumbers; i++) {

			PdfDictionary pdfpage = reader.getPageN(i);

			PdfObject object = pdfpage.getDirectObject(PdfName.CONTENTS);
			streams = (PRStream) object;

			byte[] datas = PdfReader.getStreamBytes(streams);
			String dd = new String(datas);

			for (Map.Entry<String, String> map : request.getTemplateVariables().entrySet()) {
				dd = dd.replace(map.getKey(), map.getValue());
			}

			streams.setData(dd.getBytes());

		}

		PdfStamper stamper = new PdfStamper(reader,
				new FileOutputStream("./src/main/resources/templates/document.pdf"));

		Image img = Image.getInstance("./src/main/resources/img/int-circle-logo.png");
		float w = img.getScaledWidth();
		float h = img.getScaledHeight();

		// properties
		PdfContentByte over;
		Rectangle pagesize;
		float x;
		float y;

		// loop over every page
		int n = reader.getNumberOfPages();
		for (int i = 1; i <= n; i++) {

			// get page size and position
			pagesize = reader.getPageSizeWithRotation(i);
			x = (pagesize.getLeft() + pagesize.getRight()) / 2;
			y = (pagesize.getTop() + pagesize.getBottom()) / 2;
			over = stamper.getOverContent(i);
			over.saveState();

			// set transparency
			PdfGState state = new PdfGState();
			state.setFillOpacity(0.2f);
			over.setGState(state);

			// add watermark text and image

			over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));

			over.restoreState();
		}
		stamper.close();
		reader.close();

		InputStream inputStream = new BufferedInputStream(
				new FileInputStream(new File("./src/main/resources/templates/document.pdf")));

		String indusnet1 = iSetPasswordRules.generatePassword(request.getMetadata());
		String indusnet2 = request.getMetadata().getCreator();

		log.info("Your password is: " + indusnet1);

		PDDocument doc = PDDocument.load(new File("./src/main/resources/templates/document.pdf"));
		AccessPermission ap = new AccessPermission();
		StandardProtectionPolicy spp = new StandardProtectionPolicy(indusnet1, indusnet2, ap);
		spp.setEncryptionKeyLength(128);
		spp.setPermissions(ap);
		doc.protect(spp);
		doc.save(response.getOutputStream());
		doc.close();

		return "pdf generated successfully";

	}

	/**
	 * In this method we fetch the template from database which is in base64 Format,
	 * decode the template and replace the dynamic variable this method will
	 * generate docx file document
	 * 
	 */
	@Override
	public void exportPdfFromDocx(TemplateRequest tempVar, HttpServletResponse response) throws IOException {

		String template = templateRepository.findByTemplateType(tempVar.getTemplateType()).getTemplate();

		byte[] byteTemplate = Base64.getDecoder().decode(template);

		File path = new File("./doctest.docx");

		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(byteTemplate);
		}
		FileInputStream fiss = new FileInputStream("./doctest.docx");

		XWPFDocument document = new XWPFDocument(fiss);
		List<XWPFParagraph> xwpfParagraphList = document.getParagraphs();

		for (XWPFParagraph xwpfParagraph : xwpfParagraphList) {
			for (XWPFRun xwpfRun : xwpfParagraph.getRuns()) {
				if (xwpfRun != null) {

					String docText = xwpfRun.getText(0);
					if (docText != null) {
						for (Entry<String, String> x : tempVar.getTemplateVariables().entrySet()) {
							docText = docText.replace(x.getKey(), x.getValue());

						}
						xwpfRun.setText(docText, 0);

					}
				}
			}
		}

		try (FileOutputStream out = new FileOutputStream("./replacedDocs.docx")) {
			document.write(out);
		}
		InputStream inputStream = new BufferedInputStream(new FileInputStream(new File("./replacedDocs.docx")));

		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

	/**
	 * This method is used to convert the pdf in html formate
	 * 
	 * @throws IOException
	 */
	public void generateHTMLFromPDF() throws IOException {

		String filename = "./test.pdf";

		PDDocument pdf = PDDocument.load(new File(filename));

		Writer output = null;

		output = new PrintWriter("./pdf.html", "utf-8");
		output.flush();

		new PDFDomTree().writeText(pdf, output);

		output.close();
	}

}