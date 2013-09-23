<#macro config id configs class>
<div id="app-${id}" class="tab-pane ${class}" style="min-height:200px;">
	<div class="row-fluid text-right">
		<button type="button" class="btn btn-mini btn-primary" data-action="add" data-key="${app.key!}" data-config="${id}">增加配置</button>
	</div>
	<div class="space"> </div>
	<#if configs?has_content>
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th>Key</th>
				<th>Value</th>
				<th class="center" style="width:70px;">管理</th>
			</tr>
		</thead>
		<tbody>
			<#list configs as config>
			<tr>
				<td>
					<a href="#" class="editable" data-pk="${config.id}" data-name="${id}.key" data-type="text"
						data-url="${ContextPath}/app/update" data-value="${config.key!?html}">
					</a>
				</td>
				<td>
					<a href="#" class="editable" data-pk="${config.id}" data-name="${id}.value" data-type="textarea"
						data-url="${ContextPath}/app/update" data-value="${config.value!?html}">
					</a>
				</td>
				<td class="td-actions center">
					<div class="btn-group">
						<button type="button" class="btn btn-mini btn-danger" title="删除" data-action="delete"
							data-id="${config.id}" data-config="${id}" data-key="${config.key!?html}">
							<i class="icon-remove bigger-120"></i>
						</button>
					</div>
				</td>
			</tr>
			</#list>
		</tbody>
	</table>
	<#else>
	<div class="alert alert-info text-center" style="padding:100px 0;">
	你还没有任何配置。
	</div>
	</#if>
</div>
</#macro>