<#include "header.ftl">
<#assign Title='Central' />
<#include "header_top.ftl">
<#assign Menu=[{
	'name':'我的应用',
	'url':'/app'
}] />
<#include "sidebar.ftl">
<div id="main-content" class="clearfix" data-init="${ContextPath}/app">
</div>
<script type="text/javascript">
var PageContext = {
	init: function(){
	}
};
</script>
<#include "footer.ftl">