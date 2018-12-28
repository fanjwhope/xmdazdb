package com.hr.global.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import com.hr.util.BaseDataOP;
import com.hr.util.ConfigInfo;
import com.hr.util.ConnectionPool;
import com.hr.util.HighrunConnectionPool;
import com.hr.util.IncString;
import com.hr.util.Log;
import com.hr.util.PreparedConnection;
import com.hr.util.TempConnectionPool;

public class GetRadomRecord {
    protected  HighrunConnectionPool pool;
    public String ExError ="";
    private int rowSum=0;
    private int f_page=0;
  
    public GetRadomRecord(Object highrunPool) {
        if (highrunPool == null) {
            pool = ConnectionPool.getPool();
        } else {
            if (highrunPool instanceof ConnectionPool) {
                pool = (ConnectionPool) highrunPool;
            } else if (highrunPool instanceof TempConnectionPool) {
                pool = (TempConnectionPool) highrunPool;
            }
        }
    }
    public String[][] getRadom(int number ,String sql){
       if(number<1)return new String[0][0];
       String[][] strArr = null;
       PreparedConnection pConn = null;
       try{
           pConn = pool.getConnection();
       }catch(SQLException ex){
                 Log.error("非事务 QueryRowAndCol 得到连接失败! ",ex);
       }
        String[][] rowAndCol=null;
        try{
            ResultSet rs = pConn.ExecQuery_SCROLL(sql);//得到带游标的结果集
            if(rs==null)return new String[0][0];
            ResultSetMetaData meta = null;
            String temp = null;
            int index1=0;
            long l=System.currentTimeMillis();
            rs.last();
            System.err.println(System.currentTimeMillis()-l);
            int rowCount = rs.getRow(); //行的数量
            System.out.println(rowCount);
            int colCount = rs.getMetaData().getColumnCount();
            if(rowCount==0)return null;
            if(rowCount<=number){//取全部结果集后返回
                 rowAndCol =new String[rowCount][colCount];
                 rs.beforeFirst();//到第一行
                 while(rs.next()){
                      for (int i = 1; i <= colCount; i++) {
                          temp = IncString.formatNull(getString(rs, i));
                          rowAndCol[index1][i - 1] = temp;
                      }
                      index1++;
                  }
                  return rowAndCol;
            }
             
            int begin= getRadomCursor(rowCount,number);
                	rs.absolute(begin);
                 while(rs.next()){
                	 String[][] tempArr=new String[1][colCount] ;
                     for (int i = 1; i <= colCount; i++) {
                         temp = IncString.formatNull(getString(rs, i));
                         tempArr[0][i - 1] = temp;
                     }
                     rowAndCol =_addToArr(rowAndCol,tempArr);
                     index1++;
                     if(index1>=number)break;
                 }
            
             
        }catch(Exception e){
           Log.error("非事务 QueryRowAndCol 执行 SQL出错! ",e);
           Log.error(sql);
        }
        finally{
             freeConn(pConn);
        }
        if(rowAndCol==null)return new String[0][0];
        return rowAndCol;
  }  

  private int  getRadomCursor(int leng,int number){
       if(number>leng)return 0;//如果少的话就全部看
       StringBuffer tempCurs =new StringBuffer(",");
       int addNum=0;
       int cursor=0;
       int toatl=leng/number;
       int lastPageRows=leng%number;
       if(lastPageRows>0)toatl ++;
       int radomPage= (int)(Math.random()*toatl);
       if(radomPage==0)radomPage=1;
       if(radomPage==toatl){//如果取到最大的时候，光标就要向上回几位,以保持与number一致
             if(toatl==1){//只有一页
                 
             }else{
                 cursor =leng-10-1; 
             }
       }else cursor =(radomPage-1)*number;
       return cursor;
  }    
    //取随机数，数组
  private int[] getRadomArr(int leng,int number){
       if(number>leng)return new int[0];//如果少的话就全部看
       StringBuffer tempCurs =new StringBuffer(",");
       int addNum=0;
       int[] iArr=new int[number];
       while(addNum<number){
              int CurNum= (int)(Math.random()*leng); 
              if(tempCurs.toString().indexOf(","+CurNum+",")==-1){
                    tempCurs.append(CurNum+",");
                    iArr[addNum]=CurNum;
                    addNum ++;
              }
       }
       return iArr;
    }  
   //将一个数组的数据加入到另外一个数组中
   private static String[][] _addToArr(String[][] destinctArr,String[][] addDataArr){
        if(addDataArr==null || addDataArr.length==0){
            if(destinctArr!=null)return (String[][])destinctArr.clone();
            else return null;
        }
        if(destinctArr==null || destinctArr.length==0){
            return (String[][])addDataArr.clone();
        }
        int len1 =destinctArr.length;
        int len2 =addDataArr.length;

        String msg[][]=new String[len1+len2][];
        System.arraycopy(destinctArr,0,msg,0,len1);
        System.arraycopy(addDataArr,0,msg,len1,len2);
        return msg;
   }    
   private String getString(ResultSet rs , int colIdx) throws Exception{
        String returnValue = null ;
        Clob clob = null ;
        Blob blob = null ;
        BufferedReader br = null ;
        if(ConfigInfo.currentDBType == 1){
              try{
                  if(Types.CLOB == rs.getMetaData().getColumnType(colIdx)){
                              clob = rs.getClob(colIdx) ;
                              if (clob!=null){
                              StringBuffer str=new StringBuffer();
                              br = new BufferedReader(clob.getCharacterStream()) ;
                              long Len=clob.length();
                              long t_sum =0;
                              char buf[]=new char[1024];
                              while(t_sum <Len){
                                      int n= br.read(buf);
                                      if(n<0)break;
                                      t_sum+=n;
                                      str.append(new String(buf,0,n))  ;
                              }
                              returnValue=str.toString() ;
                        } else {
                            returnValue = "" ;
                        }
  
                   } else if(Types.BLOB == rs.getMetaData().getColumnType(colIdx) ) {
                        //todo..
                   }else{
                      returnValue = IncString.formatNull(rs.getString(colIdx));
                   }
                }catch(SQLException e){
                    throw new Exception(e.getMessage());
                }catch(IOException e){
                    throw new Exception(e.getMessage());
                }finally{
                    if(br!=null){
                       try{
                         br.close();
                       }catch(IOException e){
                         throw new Exception(e.getMessage());
                       }
                    }
                }
  
         }else {
            try{
                returnValue = rs.getString(colIdx);
              }catch(SQLException e){
                throw e;
              }
         }
  
         return returnValue ;
  }  
    private void freeConn(PreparedConnection pConn){
          if(pool instanceof TempConnectionPool){
                  pConn.close(pool);
          }else{
                  pConn.close();
          }
   }    
   public static void main(String[] args){
         GetRadomRecord getRadomRecord=new GetRadomRecord(ConnectionPool.getPool());
         for(int i=0;i<5;i++){
         long l=System.currentTimeMillis();
        // com.hr.util.Log.debug(getRadomRecord.getRadom(10,"select * from T_INSURER") );
         String[][] ar1= getRadomRecord.getRadom(50,"select id from T_INSURER PARTITION(INSURER110102) A WHERE  A.CHECKOLDRESULT='0' AND A.CHECKOLDYEAR=TO_CHAR(SYSDATE,'YYYY')  ") ;
         System.out.println(System.currentTimeMillis()-l);
         System.out.println(ar1.length);
         }
        BaseDataOP op=new BaseDataOP(null);
        System.out.println( op.querySingleData("select count(*) from ANSWER"));
        String[][] ar2= getRadomRecord.getRadom(10,"select id from T_INSURER PARTITION(INSURER110105) A WHERE  A.CHECKOLDRESULT='0' AND A.CHECKOLDYEAR=TO_CHAR(SYSDATE,'YYYY')") ;
        String[][] ar3= getRadomRecord.getRadom(10,"select id from T_INSURER PARTITION(INSURER110105) A WHERE  A.CHECKOLDRESULT='0' AND A.CHECKOLDYEAR=TO_CHAR(SYSDATE,'YYYY')") ;
        String[][] ar4= getRadomRecord.getRadom(10,"select id from T_INSURER PARTITION(INSURER110105) A WHERE  A.CHECKOLDRESULT='0' AND A.CHECKOLDYEAR=TO_CHAR(SYSDATE,'YYYY')") ;
        for(int i=0;i<ar2.length;i++){
        	System.out.print("ar1 "+ar4[i][0]+" ");
        	System.out.print(" ar2 "+ar2[i][0]);
        	System.out.println(" ar3 "+ar3[i][0]);
        }
   }
}