package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.TblAttach;
import service.FileService;

public class FileHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/file/fileUploadForm.jsp";
	private FileService fileService = new FileService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String uploadPath = req.getRealPath("upload");
		System.out.println("path=" + uploadPath);

		int size = 10 * 1024 * 1024;
		String name = "";
		String subject = "";
		String filename1 = "";
		String filename2 = "";
		String origfilename1 = "";
		String origfilename2 = "";
		
		TblAttach tblAttach = null;
		List<TblAttach> tblAttachList = new ArrayList<TblAttach>();

		try {
			MultipartRequest multi = new MultipartRequest(req, uploadPath, size, "utf-8",
					new DefaultFileRenamePolicy());

			name = multi.getParameter("name");
			subject = multi.getParameter("subject");

			Enumeration files = multi.getFileNames();

			//String file2 = (String) files.nextElement();
			//filename2 = multi.getFilesystemName(file2);
			//origfilename2 = multi.getOriginalFileName(file2);

			//String file1 = (String) files.nextElement();
			//filename1 = multi.getFilesystemName(file1);
			//origfilename1 = multi.getOriginalFileName(file1);
			
			String file, filename, origfilename;
			
			for(int i=1; i<3; i++) {
				file = (String) files.nextElement();
				filename = multi.getFilesystemName(file);
				origfilename = multi.getOriginalFileName(file);
				
				tblAttach = new TblAttach(filename,1,new Date());
				tblAttachList.add(tblAttach);
			}
			
			fileService.upload(tblAttachList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		req.getSession().setAttribute("name", name);
		req.getSession().setAttribute("subject", subject);
		req.getSession().setAttribute("tblAttachList", tblAttachList);

		return "/WEB-INF/view/file/fileCheck.jsp";
	}

}
