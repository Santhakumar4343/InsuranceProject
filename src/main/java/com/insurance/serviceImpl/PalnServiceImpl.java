package com.insurance.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.insurance.entity.InsurancePlan;
import com.insurance.repository.PlanRepository;
import com.insurance.service.PlanService;
import com.insurance.util.MailSending;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PalnServiceImpl implements PlanService {
	@Autowired
	PlanRepository planRepo;
	@Autowired
	private	MailSending mailSender;
	//To save new insurance plan 
	@Override
	public InsurancePlan savePlan(InsurancePlan plan) {
		return planRepo.save(plan);
	}
	//To get list of  insurance plans 
	@Override
	public List<InsurancePlan> getAllPlans() {
		return planRepo.findAll();
	}
	@Override
	public List<String> getPlanName() {

		return planRepo.getPlanName();
	}
	@Override
	public List<String> getPlanStatus() {

		return planRepo.getPlanStatus();
	}
	@Override
	public boolean generateExcel(HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook =new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet("PlansData");
		HSSFRow heraderRow=sheet.createRow(0);
		heraderRow.createCell(0).setCellValue("id");
		heraderRow.createCell(1).setCellValue("planName");
		heraderRow.createCell(2).setCellValue("planStatus");
		heraderRow.createCell(3).setCellValue("gender");
		heraderRow.createCell(4).setCellValue("startDate");
		heraderRow.createCell(5).setCellValue("endDate");
		List<InsurancePlan> records=planRepo.findAll();
		int dataRowIndex=1;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Replace with your desired date format
		for (InsurancePlan plan : records) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(plan.getId());
			dataRow.createCell(1).setCellValue(plan.getPlanName());
			dataRow.createCell(2).setCellValue(plan.getPlanStatus());
			dataRow.createCell(3).setCellValue(plan.getGender());

			// Format and set the date values
			HSSFCell startDateCell = dataRow.createCell(4);
			startDateCell.setCellValue(plan.getStartDate());
			HSSFCellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd")); // Use your desired date format
			startDateCell.setCellStyle(dateCellStyle);

			HSSFCell endDateCell = dataRow.createCell(5);
			endDateCell.setCellValue(plan.getEndDate());
			endDateCell.setCellStyle(dateCellStyle);

			dataRowIndex++;
		}
		FileOutputStream fos = new FileOutputStream(new File("plans.xls"));
		workbook.write(fos);
		fos.close();

		// Convert the generated file to a MultipartFile
		File file = new File("plans.xls");
		FileInputStream input = new FileInputStream(file);

		MultipartFile multipartFile = new MultipartFile() {
			@Override
			public String getName() {
				return file.getName();
			}

			@Override
			public String getOriginalFilename() {
				return file.getName();
			}

			@Override
			public String getContentType() {
				return "application/octet-stream";
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public long getSize() {
				return file.length();
			}

			@Override
			public byte[] getBytes() throws IOException {
				return Files.readAllBytes(file.toPath());
			}

			@Override
			public InputStream getInputStream() throws IOException {
				return new FileInputStream(file);
			}

			@Override
			public void transferTo(File dest) throws IOException, IllegalStateException {
				try (FileChannel sourceChannel = new FileInputStream(file).getChannel();
						FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
					destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
				}
			}
		};

		// Send email with the attachment
		mailSender.sendMailWithAttachment("Plans", "Hello mr prem kumar", "@gmail.com", multipartFile);

		workbook.close();
		return true;	 
	}
	@Override
	public boolean generatePdf(HttpServletResponse response) throws Exception {
		List<InsurancePlan> records = planRepo.findAll();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Paragraph title = new Paragraph("Exporting Plans Data as PDF");
		document.add(title);

		// Add data to the PDF
		for (InsurancePlan plan : records) {
			Paragraph planInfo = new Paragraph(
					"Plan Name: " + plan.getPlanName() + "\n" +
							"Plan Status: " + plan.getPlanStatus() + "\n" +
							"Gender: " + plan.getGender() + "\n" +
							"Start Date: " + plan.getStartDate() + "\n" +
							"End Date: " + plan.getEndDate() + "\n\n"
					);
			document.add(planInfo);
		}
		document.close();
		return true;
	}

}
