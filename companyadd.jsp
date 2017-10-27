<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="Xenon Boostrap Admin Panel" />
	<meta name="author" content="" />
	<title>保险产品新增</title>
	
	<%@ include file="../../../main/jsp/top.jsp" %>

</head>
<body class="page-body skin-facebook" >
	<div class="page-container">    
		
		<div class="main-content">
			<%@ include file="../../../main/jsp/nav.jsp" %>
			<div class="row">
				<div class="col-sm-12">
					<div class="panel panel-default">
						<div class="panel-body">
							<form id="forms">
								<div class="form-group">
									<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
										<label class="col-sm-5 control-label" for="agentcom">保险公司编码</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="agentcom" id="agentcom" >
                                  		</div>
									</div>
										</div>
									
									<div class="form-group">
									<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
										<label class="col-sm-5 control-label" for="name">保险公司名称</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="name" id="name" >
										</div>
									</div>
									</div>
									<div class="form-group">
									<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
									<label class="col-sm-5 control-label" for="makedate">业务合作日期</label>
										<div class="col-sm-7">
											<input type="text" name="cooperationdate"  id="cooperationdate" class="form-control datepicker" data-format="yyyy-mm-dd"  >
										</div>
									</div>
									</div>
									<div class="form-group">
									<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
										<label class="col-sm-5 control-label" for="states">业务合作状态</label>
										<div class="col-sm-7">
										<select  class="form-control" id="states" name="states">
												<option></option>
												<option value="1">暂停</option>
												<option value="2">终止</option>
												<option value="3">正常</option>
										</select>
									</div>
									</div>
									</div>
									<div class="form-group">
									<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
										<label class="col-sm-5 control-label" for="operator">操作员</label>
										<div class="col-sm-7">
											<input type="text" class="form-control" name="operator" id="operator" value="${userLoginInfo.emplID}" readonly="readonly">
										</div>
									</div>
									</div>
							<div class="form-group">
							 <div class="col-lg-6 col-md-6 col-xs-6 marginbottom15">
										<label class="col-sm-5 control-label" for="jc">是否添加投连账户</label>
									<div class="col-sm-7">
										<select id="jc" name="jc" onchange="var n=$('#jc').val();if(n=='Y'){document.getElementById('type').style.display='';}if(n=='N'){ document.getElementById('type').style.display='none';}if(n!='Y'&&n!='N'){ document.getElementById('type').style.display='none';}">
												<option ></option>
												<option value="Y">是</option>
												<option value="N">否</option>
										</select>
									</div>
									</div>
						  </div>
							 <div class="col-lg-6 col-md-6 col-xs-6 marginbottom15" style="width: 100%" >
										<label class="col-sm-5 control-label" for="makedate" >备注</label>
										<div class="col-sm-7" style="width: 100%">
											<textarea rows="3" cols="100%" name="remark"  id="remark" class="form-control" ></textarea>
										</div>
							  </div>
							
						<div class="col-lg-6 col-md-6 col-xs-6 marginbottom15" style="margin-top: 5%;margin-left: 45%">
                            <button type="submit" class="btn btn-primary" >提交</button>
                            <button type="reset" class="btn btn-primary" id="reset">清空</button>
                        </div>
					      	<div id='type' style="display: none">
						<table class="table table-hover"  border="1">
						<thead>
						   <tr align="center"> 
							   
							    <td>投连账户类型</td>
							    <td>投连账户类型编码</td>
							    <td>风险等级</td>
						   </tr>
						   </thead>
						   <tbody id="com">
						   <tr align="center"> 
							  
							    <td><input type="text" value="货币型投资账户" readonly="readonly"></td>
							    <td><input type="text" value="1" readonly="readonly"></td>
							     <td><!-- <input type="text" name="a1"> -->
							    <select  class="form-control" id="a1" name="a1">
												<option value="1">一级</option>
												<option value="2">二级</option>
												<option value="3">三级</option>
												<option value="4">四级</option>
												<option value="5">五级</option>
								</select>
							    </td>
						   </tr>
						    <tr align="center"> 
							  
							    <td><input type="text" value="债券型投资账户" readonly="readonly"></td>
							    <td><input type="text" value="2" readonly="readonly"></td>
							    <td><!-- <input type="text" name="a2"> -->
							    <select  class="form-control" id="a2" name="a2">
												<option value="1">一级</option>
												<option value="2">二级</option>
												<option value="3">三级</option>
												<option value="4">四级</option>
												<option value="5">五级</option>
								</select>
							    </td>
						   </tr>
						    <tr align="center"> 
							   
							    <td><input type="text" value="混合偏债型投资账户" readonly="readonly"></td>
							    <td><input type="text" value="3" readonly="readonly"></td>
							     <td><!-- <input type="text" name="a3"> -->
							    <select  class="form-control" id="a3" name="a3">
												<option value="1">一级</option>
												<option value="2">二级</option>
												<option value="3">三级</option>
												<option value="4">四级</option>
												<option value="5">五级</option>
								</select>
							    </td>
						   </tr>
						    <tr align="center"> 
							  
							    <td><input type="text" value="混合偏股型投资账户" readonly="readonly"></td>
							    <td><input type="text" value="4" readonly="readonly"></td>
							    <td><!-- <input type="text" name="a4"> -->
							    <select  class="form-control" id="a4" name="a4">
												<option value="1">一级</option>
												<option value="2">二级</option>
												<option value="3">三级</option>
												<option value="4">四级</option>
												<option value="5">五级</option>
								</select>
							    </td>
						   </tr>
						    <tr align="center"> 
							  
							    <td><input type="text" value="股票型投资账户" readonly="readonly"></td>
							    <td><input type="text" value="5" readonly="readonly"></td>
							    <td><!-- <input type="text" name="a5"> -->
							    <select  class="form-control" id="a5" name="a5">
												<option value="1">一级</option>
												<option value="2">二级</option>
												<option value="3">三级</option>
												<option value="4">四级</option>
												<option value="5">五级</option>
								</select>
							    </td>
						   </tr>
						   </tbody>
						</table>
						</div>
			
					</form>
 					</div>
					
					</div>
				</div>
			</div>
			
			<%@ include file="../../../main/jsp/footer.jsp" %>
		</div>
	</div>
	<%@ include file="../../../main/jsp/bottom.jsp" %>
	<script src="<%=request.getContextPath() %>/include/js/datepicker/bootstrap-datepicker.js"></script>
	
		
	<script src="<%=request.getContextPath() %>/include/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath() %>/include/js/bootstrapValidator.js"></script>
	<script src="<%=request.getContextPath() %>/include/js/bootstrapValidator.min.js"></script>
	
	
	<script src="<%=request.getContextPath() %>/application/insurance/newinsurance/res/js/companyAdd.js" type="text/javascript" charset="utf-8"></script>

</body>
</html>