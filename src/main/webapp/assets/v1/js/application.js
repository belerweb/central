/**
 * Date formate
 */
Date.prototype.format = function(format) {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

plupload.addI18n({
	'Select files' : '选择文件',
	'Add files to the upload queue and click the start button.' : '添加文件到上传队列然后点击开始按钮',
	'Filename' : '文件名',
	'Status' : '状态',
	'Size' : '尺寸',
	'Add Files' : '添加文件',
	'Stop Upload' : '停止上传',
	'Start Upload' : '开始上传',
	'Add files' : '添加文件',
	'Stop current upload' : '停止当前上传',
	'Start uploading queue' : '开始上传队列',
	'Stop upload' : '停止上传',
	'Start upload' : '上传',
	'Uploaded %d/%d files' : '已上传 %d/%d 文件',
	'N / A' : 'N / A',
	'Drag files here.' : '拖拽文件到这儿或者点击选择按钮选择文件，支持多选',
	'File extension error' : '文件扩展名错误',
	'File size error' : '文件尺寸错误',
	'File count error' : '文件数量错误',
	'Init error' : '初始化错误',
	'HTTP Error.' : '网络错误',
	'Security error' : '安全错误',
	'Generic error' : '一般错误',
	'IO error' : 'IO 错误',
	'File : % s' : '文件 % s',
	'Close' : '关闭',
	'% d files queued' : '已排队 % d 文件',
	'File : % f, size : % s, max file size : % m' : '文件 : % f, 尺寸 : % s, 最大文件尺寸 : % m',
	'Upload element accepts only % d file (s) at a time. Extra files were stripped' : '一次接受 % d 个文件。多余文件将被忽略。',
	'Upload URL might be wrong or doesn \'t exist ' : '上传地址错误或不存在',
	'Error : File too large :' : '错误 : 文件太大',
	'Error: Invalid file extension: ' : '错误：不允许的文件类型：'
});

bootbox.setLocale('zh_CN');
var App = {
	go : function(el, url) {
		var history = $(el).data('history') || [];
		if (!!url) { // load
			history.push(url);
			$(el).data('history', history);
			$(el).load(url);
		} else { // history.go(-1);
			history.pop();
			var url = history.pop();
			if (!!url) {
				history.push(url);
				$(el).data('history', history);
				$(el).load(url);
			}
		}
		if (el === '#main-content') {
			url = url.match(/^([^\?\&]+)[?&]*.*$/)[1];
			$('#sidebar li').removeClass('active');
			$('#sidebar a[href="' + url + '"]').parents('li').addClass('active');
		}
	},
	reload : function(el, func) {
		var history = $(el).data('history') || [];
		var url = history.pop();
		if (!!url) {
			history.push(url);
			$(el).data('history', history);
			$(el).load(url, func || function() {
			});
		}
	}
};

$(function() {
	(function() {
		$('#sidebar .nav-list a').click(function(event) {
			event.preventDefault();
			event.stopPropagation();
			if ($(this).is(".dropdown-toggle")) {
				var submenu = $(this).next().get(0);
				if ($(submenu).is(':visible')) {
					$(submenu).slideUp(200).parent().removeClass("open")
				} else {
					$(submenu).slideToggle(200).parent().toggleClass('open');
				}
			} else {
				$('#sidebar .active').removeClass('active');
				$(this).parent().addClass('active');
				$(this).closest('.submenu').parent().addClass('active');
				App.go('#main-content', $(this).attr('href'));
			}
		});
	})();
});