package com.hr.kgj;

import com.hr.util.d;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import javax.swing.SwingUtilities;

public class MultiPageReadPdf
{
  static int getAllPagesNum;

  public static void doitJAI(String filePath, String jpgSavePath)
    throws IOException
  {
    File file = new File(filePath);
    RandomAccessFile raf = new RandomAccessFile(file, "r");
    FileChannel channel = raf.getChannel();
    ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
    PDFFile pdffile = new PDFFile(buf);
    getAllPagesNum = pdffile.getNumPages();
    int pageNum = 1;
    for (int i = 1; i <= pdffile.getNumPages(); ++i) {
      pageNum = i;
      PDFPage page = pdffile.getPage(i);
      Rectangle rect = new Rectangle(0, 0, (int)page.getBBox().getWidth(), (int)page.getBBox().getHeight());
      Image img = page.getImage(rect.width, rect.height, rect, null, true, true);
      BufferedImage tag = new BufferedImage(rect.width, rect.height, 1);
      tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height, null);
      FileOutputStream out = new FileOutputStream(jpgSavePath + d.fgh() + pageNum + ".jpg");
      JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
      encoder.encode(tag);
      out.close();
    }
  }

  public static void main(String[] args) throws IOException {
	  MultiPageReadPdf mprp=new MultiPageReadPdf();
	  mprp.doitJAI("e:\\xmrlb_23.pdf", "e:\\xmrlb_23");
  }
}