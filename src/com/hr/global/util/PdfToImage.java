package com.hr.global.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

public class PdfToImage {

	public static final String FILETYPE_JPG = "jpg";
	public static final String SUFF_IMAGE = "." + FILETYPE_JPG;

	/**
	 * ��PDF�ļ���ָ��ҳ��ת��Ϊָ��·��������ͼ
	 *@param filepath ԭ�ļ�·��������d:/test.pdf
	 *@param imagepath ͼƬ����·�������� d:/test-1.jpg
	 *@param pageNum ��ת��PDF�ĵ��ĵڼ�ҳ����ʼҳ��Ϊ0
	 *@param zoom     ����ͼ��ʾ������1��ʾ�����ţ�0.3����С��30%
	 *@param outputStream     ���out��Ϊnull������ת����ͼƬ��ͬʱ������ͼƬ�����out����
	 *@return ����һ��int�������顣����ĵ�0λ��ʾPDF�ĵ���ҳ������1λ��ʾת���õ���ͼƬ��ȣ���2λ��ʾת���õ���ͼƬ�߶�
	 */
	public static int[] tranfer(String filepath, String imagepath, int pageNum, float zoom, OutputStream outputStream)
			throws PDFException, PDFSecurityException, IOException {
		Document document = null;
		float rotation = 0f;
		zoom=zoom*(1.336f);//��������ϵ����������zoom����1ʱ��ת����ͼƬ����ʵ�ʴ�С
		document = new Document();
		document.setFile(filepath);
		int maxPages = document.getPageTree().getNumberOfPages();
		if(pageNum<0||pageNum>maxPages){
			throw new IllegalArgumentException("����pageNum��"+pageNum+"��ָʾ��ҳ�벻�ڴ�ת����PDF�ļ�ҳ�뷶Χ�ڣ�");
		}
		BufferedImage img = (BufferedImage) document.getPageImage(pageNum,
				GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation,
				zoom);
		
		if(null!=outputStream){
			ImageIO.write(img, "JPEG", outputStream);   
		}
		
		Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
		ImageWriter writer = (ImageWriter) iter.next();
		File outFile = new File(imagepath);
		FileOutputStream out = null;
		ImageOutputStream outImage = null;
		try {
			out = new FileOutputStream(outFile);
			outImage = ImageIO.createImageOutputStream(out);
			writer.setOutput(outImage);
			writer.write(new IIOImage(img, null, null));
			writer.dispose();
		} finally {
			if(null!=out){
				out.close();
			}
			if(null!=outImage){
				outImage.close();
			}
		}
		return new int[]{maxPages,img.getWidth(),img.getHeight()};
	}
	
//	public static void main(String args[]){
//		try {
//			long b=System.currentTimeMillis();
//			tranferWholeFile("D:\\12.pdf", "D:\\12", 1.336f);
//			System.out.println(System.currentTimeMillis()-b);
//		} catch (PDFException e) {
//			e.printStackTrace();
//		} catch (PDFSecurityException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
