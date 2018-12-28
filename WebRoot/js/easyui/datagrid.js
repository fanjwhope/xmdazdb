//���class��ʼ
function Grid(){ 
			this.defaultConf={
							title:'',
							iconCls:'icon-menu',
							width:700,
							height:350,
							nowrap: true,
							autoRowHeight: false,
							striped: false,
							collapsible:true,
							url:'datagrid_data.json',
							sortName: 'code',
							sortOrder: 'desc',
							remoteSort: false,
							idField:'id',
							frozenColumns:[],
							columns:[],
							pagination:true,
							rownumbers:true
			};
			this.addJsonButton= function(jsonObj){
				    var leng=0;  
				    var currentToolbar=this.defaultConf.toolbar;
				    var isBeenAdd =false;
				     
				    if(currentToolbar==null)currentToolbar=[];
		        else{
  	        	   leng=currentToolbar.length;
		        	   var thisObjID=jsonObj.id;
                 for(var i=0;i<leng;i++){//�ж��ظ�
							     	   var v=currentToolbar[i];
							     	   if(v!=null &&  v.id!=null){
							     	     	 if(v.id=="btn"+thisObjID){
							     	     	       isBeenAdd =true;
							     	     	 }
							     	   }
			           }		       
		         } 
		          
		         if(!isBeenAdd){
		           	  currentToolbar[leng]=jsonObj;
		           	  this.defaultConf.toolbar=currentToolbar;
		         }
		  };
			this.addDefaultButton = function(buttonType,clickFunction){ 
		        if(buttonType=="add"){
		       	    this.addButton(buttonType,"��¼",clickFunction);
		       	}else if(buttonType=="delete" || buttonType=="remove"){
		       		  this.addButton("remove","ɾ��",clickFunction); 
		       	}else if(buttonType=="save"){
		       		  this.addButton("save","����",clickFunction); 
		       	}else if(buttonType=="-"){
		       		  this.addButton(buttonType,"",clickFunction);
		       	}
		  };	
			this.addButton = function(buttonType,cnName,clickFunction){ 
				   var currentToolbar=this.defaultConf.toolbar;
			     var leng=0;
			     if(currentToolbar==null)currentToolbar=[];
			     else  leng=currentToolbar.length;
			     var isBeenAdd =false;
			      
			     for(var i=0;i<leng;i++){//�ж��ظ�
			     	   var v=currentToolbar[i];
			     	   if(v.id!="undefind"){
			     	     	 if(v.id=="btn"+buttonType){
			     	     	       isBeenAdd =true;
			     	     	 } 
			     	   }
			     }
			      
			     if(isBeenAdd)return;
			     else{
			     	   if(buttonType=="-"){
			            currentToolbar[leng]="-";
			         }else{
			         	   currentToolbar[leng]={id:'btn'+buttonType,text:cnName,disabled:false,iconCls:'icon-'+buttonType,handler:function(){eval(clickFunction)}};
			         }
			     }
			     this.defaultConf.toolbar=currentToolbar;
			};
			this.paintGrid=function(tabName,url,columns,otherConf){
					  if(otherConf!=null){
								  for(var p in otherConf){  
									   this.defaultConf[p]= otherConf[p];
							    }
				    }
				    this.defaultConf.url=url;
					  this.defaultConf.columns=columns;
				    $('#'+tabName).datagrid(this.defaultConf);
			};
}//���class����

//�����
function UIWin(winName){
	  this.winN=winName;
   	this.defaultConf={
				buttons:[{
					text:'ȷ��',
					iconCls:'icon-ok',
					handler:function(){
						alert('ok');
					}
				},{
					text:'ȡ��',
					handler:function(){
						$('#'+this.winN).dialog('close');
					}
				}]
			};
			this.open=function(otherConf){
				    if(otherConf!=null){
								  for(var p in otherConf){  
									   this.defaultConf[p]= otherConf[p];
							    }
				    }
			    	$('#'+this.winN).dialog(this.defaultConf);
			}
		
}
//������������
function checkIEIsOK(){ 
    var ua = navigator.userAgent;var isOKExp=true;var s = "MSIE";var i = ua.indexOf(s);if (i >= 0){var ver = parseFloat(ua.substr(i + s.length)); if(ver<6){alert("����IE�������"+ver+",�汾����6.0����");isOKExp=false;}}else {alert("������������IE");isOKExp=false;} 
    //alert(window.screen.width+"*"+window.screen.height);
    return isOKExp;
} 
