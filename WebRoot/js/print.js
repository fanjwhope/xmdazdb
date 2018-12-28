function CLASS_PRINT()
{
    this.header        = null;
    this.content    = null;
    this.footer        = null;
    this.board        = null;
    this.pageSize    = 10;
    this.pageStyle   =null;

    var me            = this;

    //哈希表类
    function Hashtable()
    {
        this._hash        = new Object();
        this.add        = function(key,value){
                            if(typeof(key)!="undefined"){
                                if(this.contains(key)==false){
                                    this._hash[key]=typeof(value)=="undefined"?null:value;
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
        this.remove        = function(key){delete this._hash[key];}
        this.count        = function(){var i=0;for(var k in this._hash){i++;} return i;}
        this.items        = function(key){return this._hash[key];}
        this.contains    = function(key){return typeof(this._hash[key])!="undefined";}
        this.clear        = function(){for(var k in this._hash){delete this._hash[k];}}

    }

    //字符串转换为哈希表
    this.str2hashtable = function(key,cs){
        
            var _key    = key.split(/,/g);
            var _hash    = new Hashtable();
            var _cs        = true;
            if(typeof(cs)=="undefined"||cs==null){
                _cs = true;
            } else {
                _cs = cs;
            }

            for(var i in _key){
                if(_cs){
                    _hash.add(_key[i]);
                } else {
                    _hash.add((_key[i]+"").toLowerCase());
                }

            }
            return _hash;
        }

    this._hideCols    = this.str2hashtable("");
    this._hideRows    = this.str2hashtable("");

    this.hideCols = function(cols){
        me._hideCols = me.str2hashtable(cols)
    }

    this.isHideCols = function(val){    
        return    me._hideCols.contains(val);
    }

    this.hideRows = function(rows){
        me._hideRows = me.str2hashtable(rows)
    }

    this.isHideRows = function(val){    
        return    me._hideRows.contains(val);
    }

    this.afterPrint = function()
    {
        var table = me.content;        
        
        if(typeof(me.board)=="undefined"||me.board==null){        
            me.board = document.getElementById("divPrint");
            if(typeof(me.board)=="undefined"||me.board==null){
                me.board = document.createElement("div");
                document.body.appendChild(me.board);
            }
        }

        if(typeof(table)!="undefined"){
            for(var i =0;i<table.rows.length;i++){
                var tr = table.rows[i];
                for(var j=0;j<tr.cells.length;j++){
                    if(me.isHideCols(j)){
                        tr.cells[j].style.display = "";
                    }
                }
            }
        }

        me.content.style.display    = '';
        me.header.style.display        = '';
        me.footer.style.display        = '';
        me.board.innerHTML            = '';

    }

    this.beforePrint = function(){

        var table = me.content;   

        if(typeof(me.board)=="undefined"||me.board==null){        
            me.board = document.getElementById("divPrint");
            if(typeof(me.board)=="undefined"||me.board==null){
                me.board = document.createElement("div");
                document.body.appendChild(me.board);
            }
        }


        if(typeof(table)!="undefined"&&this.hideCols.length>0){        
            
            for(var i =0;i<table.rows.length;i++){
                var tr = table.rows[i];
                for(var j=0;j<tr.cells.length;j++){
                    if(me.isHideCols(j)){                    
                        tr.cells[j].style.display = "none";
                    }
                }
            }
        }
    
        
        ///开始分页    
        var pageSize = this.pageSize;
        var pageStyle=this.pageStyle;
        var head    = me.header;
        var foot    = me.footer;
        
        var page    = new Array();
        var rows    = "";    
        var rowIndex= 1;

        var cp        = 0;
        var tp        = 0;
        
        
        for(i=1;i<table.rows.length;i++){                
            if(this.isHideRows(i)==false){
                if((((rowIndex-1)%pageSize)==0&&rowIndex>1)||i==table.rows.length){                                
                    page[page.length] = getTable(head,table,rows,foot);
                                                    
                    rows    = getOuterHTML(table.rows[i]) + "\n" ; 
                    rowIndex= 2;
                                                                            
                } else {
                    rows    += getOuterHTML(table.rows[i]) + "\n"; 
                    rowIndex++;
                }
            }
        }
        
        if(rows.length>0){
            page[page.length] = getTable(head,table,rows,foot);
        }

        tp = page.length;

        for(var i=0;i<page.length;i++){
//            page[i] = page[i].replace(/\<\!--ct-->/g,(i+1)+'/' + tp).replace(/\<\!--cp--\>/g,i+1).replace(/\<\!--tp--\>/g,tp);
							if(pageStyle=='p/P'){
								page[i] = page[i].replace(/\<\!--ct-->/g,(i+1)+'/' + tp).replace(/\<\!--cp--\>/g,i+1).replace(/\<\!--tp--\>/g,tp);
							}else if(pageStyle=='第p页'){
								page[i] = page[i].replace(/\<\!--ct-->/g,'第'+(i+1)+'页');
							}
        }
        
                    
        head.style.display        = 'none';
        foot.style.display        = 'none';
        table.style.display        = 'none';
        if(page.length>1){
            me.board.innerHTML = page.join("\n<div class='pageNext'></div>");
        }else{
            me.board.innerHTML = page.join("");
        }
    }

function getOuterHTML (node) {

    if(typeof(node)!="undefined"&&typeof(node.outerHTML)!="undefined"){
        return node.outerHTML;
    }

    var emptyElements = {
      HR: true, BR: true, IMG: true, INPUT: true
    };
    var specialElements = {
      TEXTAREA: true
    };

    var html = '';
    switch (node.nodeType){
        case Node.ELEMENT_NODE:
            html += '<';
            html += node.nodeName;
            if (!specialElements[node.nodeName]) {
                for (var a = 0; a < node.attributes.length; a++)
                    html += ' ' + node.attributes[a].nodeName.toUpperCase() + '="' + node.attributes[a].nodeValue + '"';
                html += '>'; 
                if (!emptyElements[node.nodeName]){
                    html += node.innerHTML;
                    html += '<\/' + node.nodeName + '>';
                }
            }
            else 
                switch (node.nodeName){
                    case 'TEXTAREA':
                        var content = '';
                        for (var a = 0; a < node.attributes.length; a++)
                            if (node.attributes[a].nodeName.toLowerCase() != 'value')
                                html += ' ' + node.attributes[a].nodeName.toUpperCase() + '="' + node.attributes[a].nodeValue + '"';
                            else 
                                content = node.attributes[a].nodeValue;
                            html += '>'; 
                            html += content;
                            html += '<\/' + node.nodeName + '>';
                        break; 
                }
            break;
        case Node.TEXT_NODE:
            html += node.nodeValue;
            break;
        case Node.COMMENT_NODE:
            html += '<!' + '--' + node.nodeValue + '--' + '>';
            break;
    }
    return html;
}

    function getTable(header,table,content,footer){
        var htm = "";

        if(typeof(header)!="undefined"){
            htm += getOuterHTML(header);
        }

        if(typeof(table)!="undefined"){        
            htm += "\n<" + table.tagName;
            
            for(var i =0;i<table.attributes.length;i++){
                if(table.attributes[i].specified){
                    if(table.attributes[i].name=="style")
                        htm += " style='" + table.style.cssText + "'";
                    else
                        htm += " " + table.attributes[i].nodeName + "='" + table.attributes[i].nodeValue + "'";
                }        
            }    
            
            if(table.rows.length>0){
                htm += ">\n" + getOuterHTML(table.rows[0]) + content + "</" + table.tagName + ">";
            } else {
                htm += ">\n" + content + "</" + table.tagName + ">\n";
            }        
        }

        if(typeof(footer)!="undefined"){
            htm += getOuterHTML(footer);
        }
        
        return htm;
    }

    if(!window.attachEvent){
        window.attachEvent = function(){window.addEventListener(arguments[0].substr(2),arguments[1],arguments[2]);}
    }
}