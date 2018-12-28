/**
 * �������õķ�����easyui����չ
 * author �Ʒ�
 */

var glb = $.extend({}, glb);/* ����ȫ�ֶ��������������ռ��������� */

/**
 * ʹpanel��datagrid�ڼ���ʱ��ʾ
 * 
 * @requires jQuery,EasyUI
 */
$.fn.panel.defaults.loadingMessage = '������....';
$.fn.datagrid.defaults.loadMsg = '������....';

/**
 * ������֤tip��Ļ��ƫ
 * 
 * @requires jQuery,EasyUI
 */
var removeEasyuiTipFunction = function() {
	window.setTimeout(function() {
		$('div.validatebox-tip').remove();
	}, 0);
};
$.fn.panel.defaults.onClose = removeEasyuiTipFunction;
$.fn.window.defaults.onClose = removeEasyuiTipFunction;
$.fn.dialog.defaults.onClose = removeEasyuiTipFunction;

/**
 * ͨ�ô�����ʾ ����datagrid��treegrid��tree��combogrid��combobox��form�������ݳ���ʱ�Ĳ���
 * 
 * @requires jQuery,EasyUI
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.alert('����', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * ��ֹpanel��window��dialog�������������߽�
 * 
 * @requires jQuery,EasyUI
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* �������λ�� */
		left : l,
		top : t
	});
};
//$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
//$.fn.window.defaults.onMove = easyuiPanelOnMove;
//$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * panel�ر�ʱ�����ڴ�
 * 
 * @requires jQuery,EasyUI
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	} catch (e) {
	}
};

/**
 * ��չvalidatebox�������֤�������빦��
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '������������벻һ�£�'
	}
});

/**
 * ��չvalidatebox�������֤��¼�û�������
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	loginName : {
		validator : function(value, param) {
			if (value.length < param[0]) {  
                $.fn.validatebox.defaults.rules.loginName.message = '��¼�û�������Ҫ' + param[0] + 'λ����';  
                return false;  
            }
			if (!/^[\w]+$/.test(value)) {  
                $.fn.validatebox.defaults.rules.loginName.message = '�û���ֻ��Ӣ����ĸ�����ּ��»��ߵ���ϣ�';  
                return false;  
            }
			if(document.activeElement.name!=param[3]){
				var arr=[param[1],param[2]];
				if(!$.fn.validatebox.defaults.rules.remote.validator(value, arr)){
					$.fn.validatebox.defaults.rules.loginName.message = '���û����Ѿ���ע��';
					return false;
				}
			}
			return true;
		},
		message : ''
	}
});

/**
 * ��չvalidatebox�������֤��Ĺ���
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	year : {
			validator : function(value) {
				var regExp = /\d{4}$/;
				return regExp.test(value);
			},
			message : '��������λ������ţ��磺"2008"��'
		}
	});

/**
 * ��չvalidatebox�������֤�绰�����ֻ����빦��
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	telOrCell : {
			validator : function(value) {
				var regExp = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
				return regExp.test(value);
			},
			message : '������Ϸ��ĵ绰��������ֻ����룡'
		}
	});

/**
 * ��չvalidatebox�������֤���֤���빦��
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
		idCard : {
			validator : function(value) {
				var aCity = {
					11 : "����",
					12 : "���",
					13 : "�ӱ�",
					14 : "ɽ��",
					15 : "���ɹ�",
					21 : "����",
					22 : "����",
					23 : "������",
					31 : "�Ϻ�",
					32 : "����",
					33 : "�㽭",
					34 : "����",
					35 : "����",
					36 : "����",
					37 : "ɽ��",
					41 : "����",
					42 : "����",
					43 : "����",
					44 : "�㶫",
					45 : "����",
					46 : "����",
					50 : "����",
					51 : "�Ĵ�",
					52 : "����",
					53 : "����",
					54 : "����",
					61 : "����",
					62 : "����",
					63 : "�ຣ",
					64 : "����",
					65 : "�½�",
					71 : "̨��",
					81 : "���",
					82 : "����",
					91 : "����"
				};
				// ����λ���Ⱥ�ĩλУ��
				if (!/^\d{17}(\d|x)$/i.test(value)) {
					return false;
				}
				// ���б���У��
				if (aCity[parseInt(value.substr(0, 2))] == null) {
					return false;
				}
				// ����У��
				birthday = value.substr(6, 4) + "-"
						+ Number(value.substr(10, 2)) + "-"
						+ Number(value.substr(12, 2));
				var d = new Date(birthday.replace(/-/g, "/"));
				if (birthday != (d.getFullYear() + "-" + (d.getMonth() + 1)
						+ "-" + d.getDate())) {
					return false;
				}
				// У��λУ�飬��ʽ����ʱ�ٴ�
				/*var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9,
						10, 5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6',
						'5', '4', '3', '2');
				var cardTemp = 0, i, valnum;
				for (i = 0; i < 17; i++) {
					cardTemp += value.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[cardTemp % 11];
				if (valnum != value.substr(17, 1)) {
					return false;
				}*/

				return true;
			},
			message : '���֤��Ϣ���Ϸ���'
		}
	});

/**
 * ��չdatagrid����Ӷ�̬���ӻ�ɾ��Editor�ķ���
 * 
 * �������£��ڶ����������������� datagrid.datagrid('removeEditor', 'cpwd');
 * datagrid.datagrid('addEditor', [ { field : 'ccreatedatetime', editor : { type :
 * 'datetimebox', options : { editable : false } } }, { field :
 * 'cmodifydatetime', editor : { type : 'datetimebox', options : { editable :
 * false } } } ]);
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.datagrid.methods, {
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	}
});

/**
 * ��չdatagrid��editor ���Ӵ���ѡ��������� ��������ʱ�����editor ���Ӷ�ѡcombobox���
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			var editor = $('<input />').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', glb.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	},
	datetimebox : {
		init : function(container, options) {
			var editor = $('<input />').appendTo(container);
			editor.datetimebox(options);
			return editor;
		},
		destroy : function(target) {
			$(target).datetimebox('destroy');
		},
		getValue : function(target) {
			return $(target).datetimebox('getValue');
		},
		setValue : function(target, value) {
			$(target).datetimebox('setValue', value);
		},
		resize : function(target, width) {
			$(target).datetimebox('resize', width);
		}
	},
	multiplecombobox : {
		init : function(container, options) {
			var editor = $('<input />').appendTo(container);
			options.multiple = true;
			editor.combobox(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combobox('destroy');
		},
		getValue : function(target) {
			return $(target).combobox('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combobox('setValues', glb.getList(value));
		},
		resize : function(target, width) {
			$(target).combobox('resize', width);
		}
	}
});

/**
 * ����һ��ģ̬��dialog���رպ����ٸ�dialog
 * 
 * @requires jQuery,EasyUI
 * @param options
 */
glb.dialog = function(options) {
	var opts = $.extend({
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	return $('<div/>').dialog(opts);
};

/**
 * ����EasyUI����ķ�����ע�����������<link>��ǩ�������id="easyuiTheme"��
 * 
 * @param themeName
 *            ��������
 * @requires jQuery,EasyUI,jQuery cookie plugin
 */
glb.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName
			+ '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#easyuiTheme').attr('href', href);
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});
};

/**
 * �����Ŀ·�����������������˿ںź���Ŀ�������� ʹ�÷�����glb.hostAndCxt();
 * 
 * @returns ��Ŀ��·��
 */
glb.hostAndCxt = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * ʹ�÷���:glb.cxt();
 * 
 * @returns ��Ŀ��������(/Hypertension)
 */
glb.cxt = function() {
	return window.document.location.pathname.substring(0,
			window.document.location.pathname.indexOf('/', 1));
};

/**
 * ����formatString���� ʹ�÷�����glb.formatString('�ַ���{0}�ַ���{1}�ַ���','��һ������','�ڶ�������');
 * 
 * @returns ��ʽ������ַ���
 */
glb.formatString = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * ���������ռ书��
 * 
 * ʹ�÷�����glb.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
glb.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * ����UUID
 * 
 * @returns UUID�ַ���
 */
glb.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
glb.UUID = function() {
	return (glb.random4() + glb.random4() + "-" + glb.random4() + "-"
			+ glb.random4() + "-" + glb.random4() + "-" + glb.random4()
			+ glb.random4() + glb.random4());
};

/**
 * ���URL����
 * 
 * @returns ��Ӧ���Ƶ�ֵ
 */
glb.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * ����һ���Զ��ŷָ���ַ���������List��list��ÿһ���һ���ַ���
 * 
 * @returns list
 */
glb.getList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* ��������ID�������� */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * �ж�������Ƿ���IE���Ұ汾С��8
 * 
 * @requires jQuery
 * @returns true or false
 */
glb.isLessThanIe8 = function() {
	return ($.browser.msie && $.browser.version < 8);
};

/**
 * ��form��Ԫ�ص�ֵ���л��ɶ���
 * 
 * @requires jQuery
 * @returns object
 */
glb.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

/**
 * 
 * ��JSON����ת�����ַ���
 * 
 * @param o
 * @returns string
 */
glb.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\""
				+ o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
						.replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + obj2str(o[i]));
			if (!!document.all
					&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
							.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for ( var i = 0; i < o.length; i++)
				r.push(obj2str(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};

/**
 * ��ȡ����������������Լ�������ֵ������������ǰ����ϸ������ַ���
 * @returns ֻ�������Լ���ֵ�Ķ���
 */
glb.prosNameAlter = function(obj, appendStr) {
	var returnObj={};
	 for(var p in obj){  
         if(typeof(obj[p])!="function"){  
        	 returnObj[""+appendStr+p]= obj[p];
         }
     } 
	 return returnObj;
};

/**
 * 
 * ��ʽ������ʱ��
 * 
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	if (isNaN(this.getMonth())) {
		return '';
	}
	if (!format) {
		format = "yyyy-MM-dd hh:mm:ss";
	}
	var o = {
		/* month */
		"M+" : this.getMonth() + 1,
		/* day */
		"d+" : this.getDate(),
		/* hour */
		"h+" : this.getHours(),
		/* minute */
		"m+" : this.getMinutes(),
		/* second */
		"s+" : this.getSeconds(),
		/* quarter */
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		/* millisecond */
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

/**
 * �ı�jQuery��AJAXĬ�����Ժͷ���
 * 
 * @requires jQuery
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.alert('����', XMLHttpRequest.responseText);
	}
});
