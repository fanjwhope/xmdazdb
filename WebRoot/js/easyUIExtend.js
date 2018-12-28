/**
 * 包含常用的方法和easyui的扩展
 * author 黄飞
 */

var glb = $.extend({}, glb);/* 定义全局对象，类似于命名空间或包的作用 */

/**
 * 使panel和datagrid在加载时提示
 * 
 * @requires jQuery,EasyUI
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * 避免验证tip屏幕跑偏
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
 * 通用错误提示 用于datagrid、treegrid、tree、combogrid、combobox、form加载数据出错时的操作
 * 
 * @requires jQuery,EasyUI
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * 防止panel、window、dialog组件超出浏览器边界
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
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
//$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
//$.fn.window.defaults.onMove = easyuiPanelOnMove;
//$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * panel关闭时回收内存
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
 * 扩展validatebox，添加验证两次密码功能
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '两次输入的密码不一致！'
	}
});

/**
 * 扩展validatebox，添加验证登录用户名功能
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	loginName : {
		validator : function(value, param) {
			if (value.length < param[0]) {  
                $.fn.validatebox.defaults.rules.loginName.message = '登录用户名至少要' + param[0] + '位数！';  
                return false;  
            }
			if (!/^[\w]+$/.test(value)) {  
                $.fn.validatebox.defaults.rules.loginName.message = '用户名只能英文字母、数字及下划线的组合！';  
                return false;  
            }
			if(document.activeElement.name!=param[3]){
				var arr=[param[1],param[2]];
				if(!$.fn.validatebox.defaults.rules.remote.validator(value, arr)){
					$.fn.validatebox.defaults.rules.loginName.message = '该用户名已经被注册';
					return false;
				}
			}
			return true;
		},
		message : ''
	}
});

/**
 * 扩展validatebox，添加验证年的功能
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	year : {
			validator : function(value) {
				var regExp = /\d{4}$/;
				return regExp.test(value);
			},
			message : '请输入四位数的年号，如："2008"！'
		}
	});

/**
 * 扩展validatebox，添加验证电话或者手机号码功能
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
	telOrCell : {
			validator : function(value) {
				var regExp = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
				return regExp.test(value);
			},
			message : '请输入合法的电话号码或者手机号码！'
		}
	});

/**
 * 扩展validatebox，添加验证身份证号码功能
 * 
 * @requires jQuery,EasyUI
 */
$.extend($.fn.validatebox.defaults.rules, {
		idCard : {
			validator : function(value) {
				var aCity = {
					11 : "北京",
					12 : "天津",
					13 : "河北",
					14 : "山西",
					15 : "内蒙古",
					21 : "辽宁",
					22 : "吉林",
					23 : "黑龙江",
					31 : "上海",
					32 : "江苏",
					33 : "浙江",
					34 : "安徽",
					35 : "福建",
					36 : "江西",
					37 : "山东",
					41 : "河南",
					42 : "湖北",
					43 : "湖南",
					44 : "广东",
					45 : "广西",
					46 : "海南",
					50 : "重庆",
					51 : "四川",
					52 : "贵州",
					53 : "云南",
					54 : "西藏",
					61 : "陕西",
					62 : "甘肃",
					63 : "青海",
					64 : "宁夏",
					65 : "新疆",
					71 : "台湾",
					81 : "香港",
					82 : "澳门",
					91 : "国外"
				};
				// 数字位长度和末位校验
				if (!/^\d{17}(\d|x)$/i.test(value)) {
					return false;
				}
				// 城市编码校验
				if (aCity[parseInt(value.substr(0, 2))] == null) {
					return false;
				}
				// 生日校验
				birthday = value.substr(6, 4) + "-"
						+ Number(value.substr(10, 2)) + "-"
						+ Number(value.substr(12, 2));
				var d = new Date(birthday.replace(/-/g, "/"));
				if (birthday != (d.getFullYear() + "-" + (d.getMonth() + 1)
						+ "-" + d.getDate())) {
					return false;
				}
				// 校验位校验，正式上线时再打开
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
			message : '身份证信息不合法！'
		}
	});

/**
 * 扩展datagrid，添加动态增加或删除Editor的方法
 * 
 * 例子如下，第二个参数可以是数组 datagrid.datagrid('removeEditor', 'cpwd');
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
 * 扩展datagrid的editor 增加带复选框的下拉树 增加日期时间组件editor 增加多选combobox组件
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
 * 返回一个模态的dialog，关闭后将销毁该dialog
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
 * 更换EasyUI主题的方法（注意引入主题的<link>标签必须带上id="easyuiTheme"）
 * 
 * @param themeName
 *            主题名称
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
 * 获得项目路径，包括主机名、端口号和项目部署名称 使用方法：glb.hostAndCxt();
 * 
 * @returns 项目根路径
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
 * 使用方法:glb.cxt();
 * 
 * @returns 项目部署名称(/Hypertension)
 */
glb.cxt = function() {
	return window.document.location.pathname.substring(0,
			window.document.location.pathname.indexOf('/', 1));
};

/**
 * 增加formatString功能 使用方法：glb.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
glb.formatString = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：glb.ns('jQuery.bbb.ccc','jQuery.eee.fff');
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
 * 生成UUID
 * 
 * @returns UUID字符串
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
 * 获得URL参数
 * 
 * @returns 对应名称的值
 */
glb.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
glb.getList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

/**
 * 判断浏览器是否是IE并且版本小于8
 * 
 * @requires jQuery
 * @returns true or false
 */
glb.isLessThanIe8 = function() {
	return ($.browser.msie && $.browser.version < 8);
};

/**
 * 将form表单元素的值序列化成对象
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
 * 将JSON对象转换成字符串
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
 * 获取给定对象的所有属性及其属性值，并在属性名前面加上给定的字符串
 * @returns 只含有属性及其值的对象
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
 * 格式化日期时间
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
 * 改变jQuery的AJAX默认属性和方法
 * 
 * @requires jQuery
 */
$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
});
