package com.hr.global.util;

import java.awt.Image;  
import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.RandomAccessFile;  
import java.lang.reflect.Method;
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.swing.SwingUtilities;  

import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
import com.sun.pdfview.PDFFile;  
import com.sun.pdfview.PDFPage;  
public class PdfToJpg {  
    public static int setup(String pdfPath,String dirPath, int i) throws IOException {  
    	// load a pdf from a byte buffer  
    	File jpgfile = new File(dirPath);
    	if(!jpgfile.exists()) jpgfile.mkdir(); 
    	File file = new File(pdfPath);  
    	RandomAccessFile raf = new RandomAccessFile(file, "r");  
    	FileChannel channel = raf.getChannel();  
    	ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());  
    	PDFFile pdffile = new PDFFile(buf);  
    	System.out.println("页数： " + pdffile.getNumPages());  
    	// for (int i = 1; i <= pdffile.getNumPages(); i++) {  
    	// draw the first page to an image   
    	PDFPage page = pdffile.getPage(i);  
    	// get the width and height for the doc at the default zoom   
    	Rectangle rect = new Rectangle(0, 0, (int) page.getBBox()  
    			.getWidth(), (int) page.getBBox().getHeight());  
    	// generate the image   
    	Image img = page.getImage(rect.width, rect.height, // width &   
    			// height   
    			rect, // clip rect   
    			null, // null for the ImageObserver   
    			true, // fill background with white   
    			true // block until drawing is done   
    			);  
    	String jpgPath=dirPath  + i + ".jpg";
    	File files=new File(jpgPath);    
    	if(!files.exists()) files.createNewFile();    
    	BufferedImage tag = new BufferedImage(rect.width, rect.height,  
    			BufferedImage.TYPE_INT_RGB);  
    	tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height,  
    			null);  
    	FileOutputStream out = new FileOutputStream(jpgPath); // 输出到文件流   
    	JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
    	JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(tag);
    	param2.setQuality(1f, true);// 1f是提高生成的图片质量
    	encoder.setJPEGEncodeParam(param2);
    	encoder.encode(tag); // JPEG编码   
    	out.close();
    	
    	channel.close();
    	raf.close();
    	unmap(buf);
    	
    	return pdffile.getNumPages();
    	// }  
    	// show the image in a frame        
    	// JFrame frame = new JFrame("PDF Test");   
    	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    	// frame.add(new JLabel(new ImageIcon(img)));   
    	// frame.pack();   
    	// frame.setVisible(true);   
    }
  
    public static void main(final String[] args) {  
    	final String pdfPath="e:\\uploadFile\\xmrlb_23.pdf";
    	final String dirPath="d:\\picture\\123\\";
    	File file =new File(dirPath);    
    	if(!file .exists()  && !file .isDirectory())file .mkdir();    
        SwingUtilities.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    PdfToJpg.setup(pdfPath,dirPath, 1);  
                } catch (IOException ex) {  
                    ex.printStackTrace();  
                }  
            }  
        });  
    } 
    
    /**
     * 清空缓冲
     * @param buffer
     */

    public static <T> void unmap(final Object buffer) {
        AccessController.doPrivileged(new PrivilegedAction<T>(){
            @Override
        public T run() {                
            try {
            Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
            getCleanerMethod.setAccessible(true);
            sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
            cleaner.clean();
            } catch(Exception e) {
                e.printStackTrace();
            }               
            return null;
        }           
        });
    }
 
}  
