		</div><!--/.fluid-container#main-container-->
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/date.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/jquery.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/bootstrap.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/bootbox.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/bootstrap-editable.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/bootstrap-datepicker.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/bootstrap-datepicker.zh-CN.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/fuelux.wizard.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/jquery.maskedinput.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/plupload.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/plupload.flash.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/plupload.html4.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/plupload.html5.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/jquery.plupload.queue.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/ace-elements.js"></script>
		<script type="text/javascript" src="${ContextPath}/assets/v1/js/application.js"></script>
		<script type="text/javascript">
		$(function(){
			document.title = '${Title!''}';
			var page = window.PageContext||{};
			if (page.init && $.isFunction(page.init)) {
				page.init();
			}
		});
		</script>
	</body>
</html>