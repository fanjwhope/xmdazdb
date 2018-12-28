package com.hr.global.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hr.util.BaseDataOP;
import com.hr.util.ConnectionPool;
import com.hr.util.d;

public class PictureSer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String path=request.getParameter("path");
		if(Validation.isEmpty(path)){
			return;
		}
		path=path.replace("/", File.separator);
		BaseDataOP op = new BaseDataOP(ConnectionPool.getPool());
		String pictureRealPath=path;
		File picture=new File(pictureRealPath);
		if(picture.exists()){
			BufferedImage bufferedImage = ImageIO.read(picture);
			OutputStream outputStream=response.getOutputStream();
			ImageIO.write(bufferedImage, "JPG", outputStream);
			outputStream.flush();
			outputStream.close();
		}
	}
}
