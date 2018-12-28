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
	 * 将PDF文件的指定页码转换为指定路径的缩略图
	 *@param filepath 原文件路径，例如d:/test.pdf
	 *@param imagepath 图片生成路径，例如 d:/test-1.jpg
	 *@param pageNum 欲转换PDF文档的第几页，起始页号为0
	 *@param zoom     缩略图显示倍数，1表示不缩放，0.3则缩小到30%
	 *@param outputStream     如果out不为null，则在转换成图片的同时，还将图片输出到out流中
	 *@return 返回一个int类型数组。数组的第0位表示PDF文档总页数，第1位表示转换得到的图片宽度，第2位表示转换得到的图片高度
	 */
	public static int[] tranfer(String filepath, String imagepath, int pageNum, float zoom, OutputStream outputStream)
			throws PDFException, PDFSecurityException, IOException {
		Document document = null;
		float rotation = 0f;
		zoom=zoom*(1.336f);//修正缩放系数。这样当zoom等于1时，转换的图片就是实际大小
		document = new Document();
		document.setFile(filepath);
		int maxPages = document.getPageTree().getNumberOfPages();
		if(pageNum<0||pageNum>maxPages){
			throw new IllegalArgumentException("参数pageNum【"+pageNum+"】指示的页码不在待转换的PDF文件页码范围内！");
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
