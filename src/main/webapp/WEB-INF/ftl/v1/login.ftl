<#assign ContextPath=springMacroRequestContext.getContextPath() />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Central</title>

		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<link href="${ContextPath}/assets/v1/css/bootstrap.css" rel="stylesheet" />
		<link href="${ContextPath}/assets/v1/css/font-awesome.css" rel="stylesheet" />

		<!--[if IE 7]>
		<link href="${ContextPath}/assets/v1/css/font-awesome-ie7.css" rel="stylesheet" />
		<![endif]-->

		<!--fonts-->
		<link href="${ContextPath}/assets/v1/css/google-fonts-opensans.css" rel="stylesheet" />

		<!--ace styles-->
		<link href="${ContextPath}/assets/v1/css/ace.css" rel="stylesheet" />
		<link href="${ContextPath}/assets/v1/css/ace-responsive.css" rel="stylesheet" />

		<!--[if lt IE 9]>
		<link href="${ContextPath}/assets/v1/css/ace-ie.css" rel="stylesheet" />
		<![endif]-->

		<link href="${ContextPath}/assets/v1/css/application.css" rel="stylesheet" />
	</head>

	<body class="login-layout">
		<div class="container-fluid" id="main-container">
			<div id="main-content">
				<div class="row-fluid">
					<div class="span12">
						<div class="login-container">
							<div class="row-fluid">
								<div class="center">
									<h4 class="blue">Central</h4>
								</div>
							</div>

							<div class="space-6"></div>

							<div id="login-box" class="row-fluid">
								<div class="position-relative">
									<div class="<#if !(activation?exists)>visible</#if> widget-box no-border" data-form="login">
										<div class="widget-body">
											<div class="widget-main">
												<h4 class="header blue lighter bigger">
													<i class="icon-github-alt green"></i> 帐号信息
												</h4>

												<div class="space-6"></div>

												<form method="post" action="${ContextPath}/login">
													<fieldset>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="j_username" type="text" required="true" class="span12" placeholder="手机号" />
																<i class="icon-user"></i>
															</span>
														</label>

														<label>
															<span class="block input-icon input-icon-right">
																<input name="j_password" type="password" required="true" class="span12" placeholder="密码" />
																<i class="icon-lock"></i>
															</span>
														</label>

														<div class="space"></div>

														<div class="row-fluid">
															<div class="span8"></div>
															<button type="submit" class="span4 btn btn-small btn-primary">
																<i class="icon-key"></i> 登录
															</button>
														</div>
													</fieldset>
												</form>
											</div><!--/widget-main-->

											<div class="toolbar clearfix">
												<div style="width:100%;text-align:center;">
													<a class="user-signup-link" onclick="show('div[data-form=signup]'); return false;" href="#">
														我要注册 <i class="icon-arrow-right"></i>
													</a>
												</div>
											</div>
										</div><!--/widget-body-->
									</div>

									<div id="login-box" class="<#if activation?exists>visible</#if>widget-box no-border" data-form="activation">
										<div class="widget-body">
											<div class="widget-main">
												<h4 class="header blue lighter bigger">
													<i class="icon-github-alt green"></i> 激活帐号
												</h4>

												<div class="space-6"></div>

												<form method="post" action="${ContextPath}/signup/activate">
													<fieldset>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="mobile" value="${mobile!}" readonly="readonly" type="text" required="true" class="span12" placeholder="手机号" />
																<i class="icon-user"></i>
															</span>
														</label>

														<label>
															<span class="block input-icon input-icon-right">
																<input name="activationCode" type="text" required="true" class="span12" placeholder="激活码" />
																<i class="icon-lock"></i>
															</span>
														</label>

														<div class="space"></div>

														<div class="row-fluid">
															<div class="span8"></div>
															<button type="submit" class="span4 btn btn-small btn-primary" data-loading-text="激活中...">
																<i class="icon-key"></i> 激活
															</button>
														</div>
													</fieldset>
												</form>
											</div><!--/widget-main-->
										</div><!--/widget-body-->
									</div>

									<div id="signup-box" class="widget-box no-border" data-form="signup">
										<div class="widget-body">
											<div class="widget-main">
												<h4 class="header green lighter bigger">
													<i class="icon-group blue"></i> 新用户注册
												</h4>

												<div class="space-6"></div>
												<p>
													输入你的手机号/姓名/密码/确认密码
												</p>

												<form method="post" action="${ContextPath}/signup">
													<fieldset>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="mobile" type="text" required="true" class="span12" placeholder="手机号" />
																<i class="icon-mobile-phone"></i>
															</span>
														</label>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="fullname" type="text" required="true" class="span12" placeholder="姓名" />
																<i class="icon-user"></i>
															</span>
														</label>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="password" type="password" required="true" class="span12" placeholder="密码" />
																<i class="icon-key"></i>
															</span>
														</label>
														<label>
															<span class="block input-icon input-icon-right">
																<input name="repassword" type="password" required="true" class="span12" placeholder="确认密码" />
																<i class="icon-key"></i>
															</span>
														</label>
														<div class="row-fluid">
															<button type="submit" class="span6 offset6 btn btn-small btn-success" data-loading-text="注册中...">
																确认注册 <i class="icon-arrow-right icon-on-right"></i>
															</button>
														</div>
													</fieldset>
												</form>
											</div>

											<div class="toolbar center">
												<a href="#" onclick="show('div[data-form=login]'); return false;" class="back-to-login-link">
													<i class="icon-arrow-left"></i> 返回登录
												</a>
											</div>
										</div><!--/widget-body-->
									</div>
								</div><!--/position-relative-->
							</div>
						</div>
					</div><!--/span-->
				</div><!--/row-->
			</div>
		</div><!--/.fluid-container-->

		<script src="${ContextPath}/assets/v1/js/jquery.js" type="text/javascript"></script>
		<script src="${ContextPath}/assets/v1/js/jquery.form.js" type="text/javascript"></script>
		<script src="${ContextPath}/assets/v1/js/bootstrap.js" type="text/javascript"></script>
		<script src="${ContextPath}/assets/v1/js/bootbox.js" type="text/javascript"></script>

		<script type="text/javascript">
			function show(el) {
				$('.widget-box.visible').removeClass('visible');
				$(el).addClass('visible');
			}
			$('div[data-form=activation] form').submit(function() {
				$('button[type=submit]', this).button('loading');
				$(this).ajaxSubmit({
					success: function(response, statusText, xhr, form) {
						window.location = '${ContextPath}/redirect';
					},
					error: function(xhr, status, error, form) {
						bootbox.alert('<div class="alert alert-error">' + xhr.responseText + '</div>');
						$('button[type=submit]', form).button('reset');
					}
				}); 
				return false; 
			});
			$('div[data-form=signup] form').submit(function() {
				$('button[type=submit]', this).button('loading');
				$(this).ajaxSubmit({
					success: function(response, statusText, xhr, form) {
						bootbox.alert('<div class="alert alert-success">' + response + '</div>', function() {
							form.resetForm();
							$('button[type=submit]', form).button('reset');
							show('div[data-form=login]');
						});
					},
					error: function(xhr, status, error, form) {
						bootbox.alert('<div class="alert alert-error">' + xhr.responseText + '</div>', function() {
							$('button[type=submit]', form).button('reset');
						});
					}
				}); 
				return false; 
			});
		</script>
	</body>
</html>
