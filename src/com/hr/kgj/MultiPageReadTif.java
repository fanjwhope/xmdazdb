package com.hr.kgj;

import com.hr.util.Log;
import com.hr.util.d;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.TIFFDecodeParam;
import com.sun.media.jai.codec.TIFFEncodeParam;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.IOException;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

public class MultiPageReadTif
{
  int getAllPagesNum;

  public static void main(String[] args)
    throws IOException
  {
  }

  public void doitJAI(String filePath, String jpgSavePath,String filename, int i)
    throws IOException
  {
	  File jpg = new File(jpgSavePath);
	  if(!jpg.exists())
		  jpg.mkdir();
    FileSeekableStream ss = new FileSeekableStream(filePath);
    TIFFDecodeParam param0 = null;
    TIFFEncodeParam param = new TIFFEncodeParam();
    JPEGEncodeParam param1 = new JPEGEncodeParam();
    ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, param0);
    int count = dec.getNumPages();
    this.getAllPagesNum = count;
    param.setCompression(4);
    param.setLittleEndian(false);
    int pageNum = i;
      RenderedImage page = dec.decodeAsRenderedImage(i - 1);
      File f = new File(filename);
      ParameterBlock pb = new ParameterBlock();
      pb.addSource(page);
      pb.add(f.toString());
      pb.add("JPEG");
      param1.setQuality(1f);
      pb.add(param1);
      RenderedOp r = JAI.create("filestore", pb);
      r.dispose();

  }
  
  public int getPages(String tifPath) throws IOException{
	  FileSeekableStream ss = new FileSeekableStream(tifPath);
	    TIFFDecodeParam param0 = null;
	    ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, param0);
	    int count = dec.getNumPages();
	    return count;
  }
}