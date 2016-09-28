		   //定义异步请求对象
           var httpRequest=false;
           
           //创建异步请求对象
           function createXMLHttp(){
           		
           		//判断为ie浏览器
                if(window.ActiveXObject){
                    try{
                       httpRequest=new ActiveXObject("msxml2.XMLHTTP");
                    }catch(e1){
                       try{
                       	httpRequest=new ActiveXObject("Microsoft.XMLHTTP");
                       }catch(e2){
                          httpRequest=false;
                          alert("在ie浏览器中创建异步请求对象失败！");
                       }
                    }
                    
                //判断为非ie浏览器
                }else if(window.XMLHTTPRequest){
                    try{
                       httpRequest=new XMLHTTPRequest();
                    }catch(e3){
                       httpRequest=false;
                       alert("在非ie浏览器中创建异步请求对象失败！");
                    }
                }
           }
           
           //准备并向服务器发送异步请求(请求路径：url,请求方式：get:null,post:提交数据;回调方法名称：method)
           function doCommandAjax(url,postData,mothed){
                
                //创建异步请求对象
                createXMLHttp();
                
                if(!postData){
                    //get方式提交
                    httpRequest.open("get",url,true);
                }else{
                    
                    //post方式提交
                    httpRequest.open("post",url,true);
                }
                
                //注册事件
                httpRequest.onreadystatechange=callBack(httpRequest,mothed);
                
                //不管是get还是post请求都要设置请求头
                httpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                
                //发送请求
                httpRequest.send(postData);
           }
           
           //回调的启动方法
           function callBack(httpRequest,mothed){
               return function(){
                   //表示请求服务器完成
                   try{
	                   if(httpRequest.readyState==4){
	                       //alert(httpRequest.status);
	                       if(httpRequest.status==200){
	                          eval(mothed+"();");
	                       }
	                   }
                   }catch(e4){
                       //alert("请求失败");
                   }
               }
           }
           
           //获取post提交数据
		   function getData(element){
			       //encodeURIComponet()对表单数据进行编码
			       //有多个元素的时候
			       //alert("是否为数字："+isNaN(element.length));
			      
			       if(!isNaN(element.length)&&element.length>1){
			           for(var i=0;i<element.length;i++){
						   //alert("是否选择:"+element[i].checked);
						   if(element[i].checked){
						        getData(element[i]);
						        //break;
						   }
				 	   }
			       }
			       
			       if(isNaN(element.length)){
				       var sug=encodeURIComponent(element.name);
				           sug+="=";
				           sug+=encodeURIComponent(element.value);
				           sug+="&";
				       postData+=sug;
				       //alert(postData+"  :888");
				   }
		  }