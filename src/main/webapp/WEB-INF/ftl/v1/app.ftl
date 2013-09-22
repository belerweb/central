<#assign ContextPath=springMacroRequestContext.getContextPath() />
<div id="page-content" class="clearfix">
	<div class="row-fluid">
		<h3 class="header smaller lighter blue">
			我的应用
			<small>
				<i class="icon-double-angle-right"></i>
				${app.name!}
			</small>
			<button type="button" class="btn btn-mini btn-primary pull-right" onclick="App.go('#main-content');">返回</button>
		</h3>
	</div>
	<script type="text/javascript">
	</script>
</div>
