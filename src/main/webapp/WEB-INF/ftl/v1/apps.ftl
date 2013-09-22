<#assign ContextPath=springMacroRequestContext.getContextPath() />
<div id="page-content" class="clearfix">
	<div class="hide">
		<form method="post" action="${ContextPath}/app/create" class="form-horizontal">
			<div class="control-group">
				<label class="control-label">应用名称</label>
				<div class="controls">
					<input name="name" type="text" placeholder="不超过32个字符" class="input-xlarge">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">应用代码</label>
				<div class="controls">
					<input name="key" type="text" placeholder="如app，只有由应为字母和数字组成" class="input-xlarge">
				</div>
			</div>
		</form>
	</div>
	<div class="row-fluid">
		<h3 class="header smaller lighter blue">
			我的应用
			<button type="button" class="btn btn-mini btn-primary pull-right" data-action="add">创建应用</button>
		</h3>
		<#if apps?has_content>
		<table id="main-list-table" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th>应用名称</th>
					<th>应用代码</th>
					<th>AppKey</th>
					<th>AppSecret</th>
					<th class="center" style="width:70px;">管理</th>
				</tr>
			</thead>
			<tbody>
				<#list apps as app>
				<tr>
					<td>${app.name!}</td>
					<td>${app.key!}</td>
					<td>${app.appKey!}</td>
					<td>${app.appSecret!}</td>
					<td class="td-actions center">
						<div class="btn-group">
							<button type="button" class="btn btn-mini" title="管理" data-action="manage" data-key="${app.key!}">
								<i class="icon-cogs bigger-120"></i>
							</button>
						</div>
					</td>
				</tr>
				</#list>
			</tbody>
		</table>
		<#else>
		<div class="alert alert-info text-center" style="padding:100px 0;">
		你还没有应用。
		</div>
		</#if>
	</div>
	<script type="text/javascript">
		$('#page-content button[data-action=add]').click(function(){
			var dialog = bootbox.dialog('', [{
				label: '创建',
				callback: function() {
					var success = false;
					$('form', dialog).ajaxSubmit({
						forceSync:true,
						async:false,
						success: function(response, status, xhr, form) {
							success = true;
						},
						error: function(xhr, status, response, form) {
							bootbox.alert('<div class="alert alert-error">' + xhr.responseText + '</div>');
						}
					});
					App.reload('#main-content');
					return success;
				}
			}], {
				header: '创建应用'
			});
			$('.modal-body', dialog).append($('#page-content form').parent().html());
		});
		$('#page-content button[data-action=manage]').click(function(){
			App.go('#main-content', '${ContextPath}/app/' + $(this).data('key'));
		});
	</script>
</div>
