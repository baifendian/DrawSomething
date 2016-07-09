$ (function () {
	
	var dropbox = $ ('#dropbox'), message = $ ('.message', dropbox);
	var id;
	dropbox.filedrop ({
	    // The name of the $_FILES entry:
	    paramname : 'image',
	    
	    maxfiles : 1,
	    maxfilesize : 2,
	    url : basePath + 'uploadPhoto',
	    
	    uploadFinished : function (i, file, response) {
		    // $.data (file).addClass ('done');
		    $ (".result").text ("上传完成!");
		    id = response.id;
		    // response is the JSON object that post_file.php returns
	    },
	    
	    error : function (err, file) {
		    switch (err) {
			    case 'BrowserNotSupported':
				    showMessage ('您的浏览器不支持本上传服务!');
				    break;
			    case 'TooManyFiles':
				    alert ('每次只能选择一张图片');
				    break;
			    case 'FileTooLarge':
				    alert (file.name + ' 图片大小最大为2M');
				    break;
			    default:
				    break;
		    }
	    },
	    
	    // Called before each upload is started
	    beforeEach : function (file) {
		    if (!file.type.match (/^image\//)){
			    alert ('只能选择图片类型的文件!');
			    
			    // Returning false will cause the
			    // file to be rejected
			    return false;
		    }
	    },
	    
	    uploadStarted : function (i, file, len) {
		    if ($ (".preview").length > 0){
			    // alert ("请先清空旧的图片");
			    $ (".preview").remove ();
		    }
		    createImage (file);
	    },
	    
	    progressUpdated : function (i, file, progress) {
		    if (progress == 100){
			    
			    $ (".result").text ("上传完成！");
		    }
		    else{
			    $ (".result").text ("正在上传中：" + progress + "%");
		    }
		    // $.data (file).find ('.progress').width (progress);
	    }
	
	});
	
	var template = '<div class="preview">' + '<span class="imageHolder">' + '<img />'
	        + '<span class="uploaded"></span>' + '</span>' + '<span class="result">' + '</span><br/>' +

	        '</div>';
	
	function createImage (file) {
		
		var preview = $ (template), image = $ ('img', preview);
		
		var reader = new FileReader ();
		
		image.width = 100;
		image.height = 100;
		
		reader.onload = function (e) {
			
			// e.target.result holds the DataURL which
			// can be used as a source of the image:
			
			image.attr ('src', e.target.result);
		};
		
		// Reading the file as a DataURL. When finished,
		// this will trigger the onload function above:
		reader.readAsDataURL (file);
		
		message.hide ();
		preview.appendTo (dropbox);
		
		// Associating a preview container
		// with the file, using jQuery's $.data():
		
		$.data (file, preview);
	}
	
	function showMessage (msg) {
		message.html (msg);
	}
	
	$ ("#validate").click (function () {
		var type = $ ("input[name='s_type']:checked").val ();
		if ($ (".preview").length < 1){
			alert ("请先上传图片！");
			return;
		}
		if (type == "" || type == undefined){
			alert ("请选择图片类型！");
			return;
		}
		var params = "type=" + type;
		params += "&id=" + id;
		$.ajax ({
		    url : basePath + 'validate',
		    type : "post",
		    // contentType : "text",
		    data : params,
		    beforeSend : function () {
			    $ (".result").text ("正在识别中...");
			    
		    },
		    // complete:function(){
		    // $("#submit").attr("disabled",false);
		    //	        	
		    // },
		    success : function (data) {
			    
			    if (data.code == "1"){
				    if (data.result == ""){
					    
					    $ (".result").html ("<font color='red'>系统出小差了，换张图片试试吧！</font>");
				    }
				    else{
					    
					    $ (".result").html ("识别结果为：<font color='red'>" + data.result + "</font>");
				    }
			    }
			    else{
				    $ (".result").text ("识别失败,请重新识别...");
			    }
			    
		    },
		    error : function (data) {
			    $ (".result").text ("系统异常，请稍后重试...");
		    }
		});
	});
});
