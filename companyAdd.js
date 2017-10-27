var tab_d = $('#cusTable');
// 页面初始化
$(document).ready(function() {


	$('#cooperationdate').datepicker({
		dateFormat: "yyyy-mm-dd",
	anguage: "zh-CN",
	onSelect: gotoDates
	}).on('changeDate',gotoDates);
	
	
	function gotoDates(){
		$('#forms').data('bootstrapValidator')
		.updateStatus('cooperationdate','NOT_VALIDATED',null)
		.validateField('cooperationdate');
   }
	
	 $("#reset").click(function() {
		 $('#forms')[0].reset();
	 });
});



$(function(){

	  $('form').bootstrapValidator({
          message: 'This value is not valid',
          feedbackIcons: {
              valid: 'glyphicon glyphicon-ok',
              invalid: 'glyphicon glyphicon-remove',
              validating: 'glyphicon glyphicon-refresh'
          },
          fields: {
              agentcom: {
                  message: '用户名验证失败',
                  validators: {
                      notEmpty: {
                          message: '用户名不能为空'
                      } ,
                      remote:{
                    	  message: '保险公司编码已存在，请重新输入',
                    	  url : path + "/InsuComController/distinctAgentCom.do",
                    	  delay:2000
                      }
                      
                      /*
                      stringLength: {
                          min: 1,
                          max: 5,
                          message: '保险公司编码必须在1到5位之间'
                      },
                      regexp: {
                          regexp: /^[a-zA-Z0-9_]+$/,
                          message: '用户名只能包含大写、小写、数字和下划线'
                      } */
                  }
              },
              name: {
                  validators: {
                      notEmpty: {
                          message: '保险公司名称不能为空'
                      }
                  }
              },
               jc:{
                validators: {
                      notEmpty: {
                          message: '是否添加投连账户不能为空'
                      }
                  }
               },
                upagentcom: {
                  validators: {
                      notEmpty: {
                          message: '保险公司外部编码不能为空'
                      }
                  }
              },

              cooperationdate:{
           	   validators: {
           		   date:{
           		format:'yyyy-mm-dd', 
           		message:'The format is yyyy-mm-dd'
           		   },
           		  notEmpty: {
                          message: '业务合作日期不能为空'
                      },
           	   },
              },
           
               address:{
                validators: {
                      notEmpty: {
                          message: '地址不能为空'
                      }
                  }
               },
               
               
               linkman:{
                validators: {
                      notEmpty: {
                          message: '联系人不能为空'
                      }
                  }
               },
               
               operator:{
                validators: {
                      notEmpty: {
                          message: '操作员不能为空'
                      }
                  }
               },
               
               
               states:{
                validators: {
                      notEmpty: {
                          message: '保险公司状态不能为空'
                      }
                  }
               },    
          },
          
         
          submitHandler: function (validator, form, submitButton) {
        	  var ids1 =form.serialize();
        	  if($("#jc").val()=='Y'){
        		  addParam(ids1);
        	  }else{
        		  addproduct(ids1);
        	  }
             
  		
  		
          }
      });
	  
	function addproduct(ids1){
		$.ajax({
			type : "POST",
			url:path+"/InsuComController/saveAddBean.do",// 后台请求URL地址
			data : ids1,
			dataType : "json",
			success : function(data) {
				if(data=="1"){
					alert("添加成功");
					url="companyadd.jsp";
					toback(url);
				}
			},
			error : function() {
				alert("plus failed");
			}
		});
	}

	  function addParam(ids1){
	  		// 去取参数
	  		var qualificationInfo = $("#com").children(
	  				"tr");
	  		 var ic=$("#agentcom").val();
	  		var count=false;
	  		for (var i = 0; i < qualificationInfo.length; i++) {

	  			var tdArr = qualificationInfo.eq(i).find("td");
	  			
	  			var fundname = tdArr.eq(0).find('input').val();
	  			var fundcode = tdArr.eq(1).find('input').val();
	  			var riskrating = tdArr.eq(2).find("select").children('option:selected').val();
	  		
	  			//校验
	  			if(riskrating == ''||riskrating == null){
	  				alert("风险等级不能为空");
	  				$('#form').bootstrapValidator('disableSubmitButtons', false);  
	  				count=false;
	  				return;
	  			}if(riskrating != ''||riskrating !=null){
	  				count=true;
	  				
	  			}
	  		
	   }
	  		if(count){
	  			for (var i = 0; i < qualificationInfo.length; i++) {

		  			var tdArr = qualificationInfo.eq(i).find("td");
		  			var fundname = tdArr.eq(0).find('input').val();
		  			var fundcode = tdArr.eq(1).find('input').val();
		  			var riskrating = tdArr.eq(2).find("select").children('option:selected').val();
		  		insertParam(ic,fundname,fundcode,riskrating,qualificationInfo.length-1,i,ids1);
	  			}
	  		}else{
  				//alert('false');
  			}
	  }


	  function insertParam(ic,fundname,fundcode,riskrating,i,j,ids1){
	  	
	  	$.ajax({
	  			type : "POST",
	  			url:path+"/JoinaccountaController/saveAddBean.do",// 后台请求URL地址
	  			data :"ic="+ic+"&fundname="+fundname+"&fundcode="+fundcode+"&riskrating="+riskrating,
	  			dataType : "json",
	  			success : function(data) {
	  				if(data=="1"&&i==j){
	  					alert("投连险账户添加成功");
	  					addproduct(ids1);
	  				}
	  			},
	  			error : function() {
	  				alert("plus failed");
	  			}
	  		});
	  }
	  

});
