package com.hr.global.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Byte2Image {
	public static byte[] image2Bytes(BufferedImage image, String format) {
		if(image==null)return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
		ImageIO.write(image, format, out);
		} catch (IOException e) {
		e.printStackTrace();
		}
		return out.toByteArray();
	}
	public static BufferedImage bytesToImage(byte[] bytes) {
		if(bytes==null||bytes.length==0)return null;
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);    //将b作为输入流；
		BufferedImage image = null;
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public static String chaPath(String path){
		path=path.replace("/", File.separator);
		path=path.replace("\\",File.separator);
		return path;
	}
	public static void main(String[] args){
		System.out.println(chaPath("dd/dd/dd\\dd\\dd\\dd"));
		System.out.println(new File("C:123"+File.separator+11));
	}
}
