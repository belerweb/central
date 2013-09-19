<!DOCTYPE html>
<htm>
	<#assign ContextPath=springMacroRequestContext.getContextPath() />
	<#assign User = securityContextHolder.getContext().getAuthentication().getPrincipal().getDetail() />
	<head>
		<meta charset="utf-8" />
		<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="${ContextPath}/assets/v1/css/bootstrap.css" rel="stylesheet" />
		<link href="${ContextPath}/assets/v1/css/datepicker.css" rel="stylesheet" />
		<link href="${ContextPath}/assets/v1/css/bootstrap-editable.css" rel="stylesheet" />
		<link href="${ContextPath}/assets/v1/css/jquery.plupload.queue.css" rel="stylesheet" />
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
		<script type="text/javascript">
			var ContextPath = '${ContextPath}';
		</script>
	</head>
	<body class="navbar-fixed">