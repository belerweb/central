<#assign ContextPath=springMacroRequestContext.getContextPath() />
<div id="page-content" class="clearfix">
	<div class="row-fluid">
		<h3 class="header smaller lighter blue">
			我的应用
			<small>
				<i class="icon-double-angle-right"></i> ${app.name!}
			</small>
			<button type="button" class="btn btn-mini btn-primary pull-right" onclick="App.go('#main-content');">返回</button>
		</h3>
	</div>
	<div class="row-fluid">
		<div class="tabbable tabs-left">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="javascript:void(0);" data-target="#app-development" data-toggle="tab">
						<i class="pink icon-bug bigger-110"></i> 开发环境
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-target="#app-test" data-toggle="tab">
						<i class="blue icon-tumblr bigger-110"></i> 测试环境
					</a>
				</li>
				<li>
					<a href="javascript:void(0);" data-target="#app-production" data-toggle="tab">
						<i class="green icon-cloud bigger-110"></i> 产品环境
					</a>
				</li>
			</ul>
			<div class="tab-content">
				<#include 'app_config.ftl'>
				<@config 'development' app.development 'active' />
				<@config 'test' app.test '' />
				<@config 'production' app.production '' />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#page-content a.editable').editable({
			inputclass: 'input-xlarge',
			emptytext: '未设置',
			mode: 'inline',
			send: 'always'
		});
		$('#page-content button[data-action=add]').click(function(){
			var config = $(this).data('config');
			$.post('${ContextPath}/app/config/add', {
				key: $(this).data('key'),
				config: config
			}).done(function(){
				App.reload('#main-content', function(){
					$('#page-content a[data-target="#app-' + config + '"]').tab('show');
				});
			}).fail(function(){
				bootbox.alert('<div class="alert alert-error">操作失败。</div>');
			});
		});
		$('#page-content button[data-action=delete]').click(function(){
			var row = $(this).closest('tr');
			var config = $(this).data('config');
			var configId = $(this).data('id');
			bootbox.confirm('确认删除配置[' + $(this).data('key') + ']?', '取消', '确认', function(result) {
				if (result) {
					$.post('${ContextPath}/app/config/delete', {
						config: config,
						configId: configId
					}).done(function(){
						row.remove();
					}).fail(function(){
						bootbox.alert('<div class="alert alert-error">删除失败。</div>');
					});
				}
			});
		});
	</script>
</div>
