(function(b, c) {
	var a = "multiple" in document.createElement("INPUT");
	b.fn.ace_file_input = function(d) {
		var e = b.extend({
			style : false,
			no_file : "No File ...",
			no_icon : "icon-upload-alt",
			btn_choose : "Choose",
			btn_change : "Change",
			icon_remove : "icon-remove",
			droppable : false,
			thumbnail : false,
			before_change : null,
			before_remove : null
		}, d);
		var f = !!window.FileList;
		this
				.each(function() {
					var k = this;
					var m = b(this);
					var i = !!e.icon_remove;
					var j = m.attr("multiple") && a;
					var q = e.style == "well" ? true : false;
					m.wrap("<div class='ace-file-input" + (q ? " ace-file-multiple" : "") + "' />");
					m.after('<label data-title="' + e.btn_choose + '" for="' + b(this).attr("id")
							+ '"><span data-title="' + e.no_file + '">'
							+ (e.no_icon ? '<i class="' + e.no_icon + '"></i>' : "") + "</span></label>"
							+ (i ? '<a class="remove" href="#"><i class="' + e.icon_remove + '"></i></a>' : ""));
					var n = m.next();
					if (b.browser.mozilla) {
						n.on("click", function() {
							if (!k.disabled && !m.attr("readonly")) {
								m.click()
							}
						})
					}
					if (i) {
						n.next("a").on("click", function() {
							var s = true;
							if (e.before_remove) {
								s = e.before_remove.call(k)
							}
							if (!s) {
								return false
							}
							return g()
						})
					}
					if (e.droppable && f) {
						var o = this.parentNode;
						b(o).on("dragenter", function(s) {
							s.preventDefault();
							s.stopPropagation()
						}).on("dragover", function(s) {
							s.preventDefault();
							s.stopPropagation()
						}).on("drop", function(y) {
							y.preventDefault();
							y.stopPropagation();
							var x = y.originalEvent.dataTransfer;
							var w = x.files;
							if (!j && w.length > 1) {
								var v = [];
								v.push(w[0]);
								w = v
							}
							var s = true;
							if (e.before_change) {
								s = e.before_change.call(k, w, true)
							}
							if (!s || s.length == 0) {
								return false
							}
							if (s instanceof Array || (f && s instanceof FileList)) {
								w = s
							}
							m.data("ace_input_files", w);
							m.data("ace_input_method", "drop");
							var u = [];
							for ( var t = 0; t < w.length; t++) {
								u.push(w[t].name)
							}
							h(u);
							m.triggerHandler("change", [ true ]);
							return true
						})
					}
					m.on("change.inner_call", function(y, u) {
						if (u === true) {
							return
						}
						var t = true;
						if (e.before_change) {
							t = e.before_change.call(k, this.files || this.value, false)
						}
						if (!t || t.length == 0) {
							if (!m.data("ace_input_files")) {
								r()
							}
							return false
						}
						var x = (t instanceof Array || (f && t instanceof FileList)) ? t : this.files;
						m.data("ace_input_method", "select");
						var w = [];
						if (x) {
							m.data("ace_input_files", x);
							for ( var v = 0; v < x.length; v++) {
								var s = b.trim(x[v].name);
								if (!s) {
									continue
								}
								w.push(s)
							}
						} else {
							var s = b.trim(this.value);
							if (s) {
								w.push(s)
							}
						}
						if (w.length == 0) {
							return false
						}
						h(w);
						return true
					});
					var h = function(x) {
						var w = m.data("ace_input_files");
						if (q) {
							n.find("span").remove();
							if (!e.btn_change) {
								n.addClass("hide-placeholder")
							}
						}
						n.attr("data-title", e.btn_change).addClass("selected");
						for ( var v = 0; v < x.length; v++) {
							var t = x[v];
							var u = t.lastIndexOf("\\") + 1;
							if (u == 0) {
								u = t.lastIndexOf("/") + 1
							}
							t = t.substr(u);
							var s = "icon-file";
							if ((/\.(jpe?g|png|gif|svg|bmp|tiff?)$/i).test(t)) {
								s = "icon-picture"
							} else {
								if ((/\.(mpe?g|flv|mov|avi|swf|mp4|mkv|webm|wmv|3gp)$/i).test(t)) {
									s = "icon-film"
								} else {
									if ((/\.(mp3|ogg|wav|wma|amr|aac)$/i).test(t)) {
										s = "icon-music"
									}
								}
							}
							if (!q) {
								n.find("span").attr({
									"data-title" : t
								}).find('[class*="icon-"]').attr("class", s)
							} else {
								n.append('<span data-title="' + t + '"><i class="' + s + '"></i></span>');
								var y = e.thumbnail && w && w[v].type.match("image") && !!window.FileReader;
								if (y) {
									p(w[v], m)
								}
							}
						}
						return true
					};
					var p = function(x, v) {
						var t = n.find("span:last");
						var w = 50;
						if (e.thumbnail == "large") {
							w = 150
						} else {
							if (e.thumbnail == "fit") {
								w = t.width()
							}
						}
						t.addClass(w > 50 ? "large" : "").prepend("<img align='absmiddle' style='display:none;' />");
						var u = t.find("img:last").get(0);
						var s = new FileReader();
						s.onload = (function(y) {
							return function(z) {
								b(y)
										.one(
												"load",
												function() {
													var B = l(y, w, x.type);
													var A = B.w, C = B.h;
													if (e.thumbnail == "small") {
														A = C = w
													}
													b(y)
															.css({
																"background-image" : "url(" + B.src + ")",
																width : A,
																height : C
															})
															.attr(
																	{
																		src : "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg=="
																	}).show()
												});
								y.src = z.target.result
							}
						})(u);
						s.readAsDataURL(x);
						s = null
					};
					var g = function() {
						n.attr({
							"data-title" : e.btn_choose,
							"class" : ""
						}).find("span:first").attr({
							"data-title" : e.no_file,
							"class" : ""
						}).find('[class*="icon-"]').attr("class", e.no_icon).prev("img").remove();
						if (!e.no_icon) {
							n.find('[class*="icon-"]').remove()
						}
						n.find("span").not(":first").remove();
						if (m.data("ace_input_files")) {
							m.removeData("ace_input_files");
							m.removeData("ace_input_method")
						}
						r();
						return false
					};
					var r = function() {
						m.wrap("<form>").closest("form").get(0).reset();
						m.unwrap()
					};
					var l = function(t, x, z) {
						var u = document.createElement("canvas");
						var s = t.width, y = t.height;
						if (s > x || y > x) {
							if (s > y) {
								y = parseInt(x / s * y);
								s = x
							} else {
								s = parseInt(x / y * s);
								y = x
							}
						}
						u.width = s;
						u.height = y;
						var v = u.getContext("2d");
						v.drawImage(t, 0, 0, t.width, t.height, 0, 0, s, y);
						return {
							src : u.toDataURL(z == "image/jpeg" ? z : "image/png", 10),
							w : s,
							h : y
						}
					}
				});
		return this
	}
})(jQuery);
(function(a, b) {
	a.fn.ace_spinner = function(c) {
		this
				.each(function() {
					var f = c.icon_up || "icon-chevron-up";
					var i = c.icon_down || "icon-chevron-down";
					var e = c.btn_up_class || "";
					var g = c.btn_down_class || "";
					var d = c.max || 999;
					d = ("" + d).length;
					var j = a(this)
							.addClass("spinner-input")
							.css("width", (d * 10) + "px")
							.wrap('<div class="ace-spinner">')
							.after(
									'<div class="spinner-buttons btn-group btn-group-vertical">						<button type="button" class="btn spinner-up btn-mini '
											+ e
											+ '">						<i class="'
											+ f
											+ '"></i>						</button>						<button type="button" class="btn spinner-down btn-mini '
											+ g + '">						<i class="' + i + '"></i>						</button>						</div>')
							.closest(".ace-spinner").spinner(c).wrapInner("<div class='input-append'></div>");
					a(this).on("mousewheel DOMMouseScroll", function(k) {
						var l = k.originalEvent.detail < 0 || k.originalEvent.wheelDelta > 0 ? 1 : -1;
						j.spinner("step", l > 0);
						j.spinner("triggerChangedEvent");
						return false
					});
					var h = a(this);
					j.on("changed", function() {
						h.trigger("change")
					})
				});
		return this
	}
})(jQuery);
(function(a, b) {
	a.fn.ace_wizard = function(c) {
		this.each(function() {
			var h = a(this);
			var d = h.find("li");
			var e = d.length;
			var f = parseFloat((100 / e).toFixed(1)) + "%";
			d.css({
				"min-width" : f,
				"max-width" : f
			});
			h.removeClass("hidden").wizard();
			var g = h.nextAll(".wizard-actions").eq(0);
			var i = h.data("wizard");
			i.$prevBtn = g.find(".btn-prev").eq(0).on("click", function() {
				h.wizard("previous")
			});
			i.$nextBtn = g.find(".btn-next").eq(0).on("click", function() {
				h.wizard("next")
			});
			i.nextText = i.$nextBtn.text()
		});
		return this
	}
})(jQuery);
(function(a, b) {
	a.fn.ace_colorpicker = function(c) {
		var d = a.extend({
			pull_right : false,
			caret : true
		}, c);
		this
				.each(function() {
					var g = a(this);
					var e = "";
					var f = "";
					a(this)
							.hide()
							.find("option")
							.each(
									function() {
										var h = "colorpick-btn";
										if (this.selected) {
											h += " selected";
											f = this.value
										}
										e += '<li><a class="' + h + '" href="#" style="background-color:' + this.value
												+ ';" data-color="' + this.value + '"></a></li>'
									})
							.end()
							.on("change.inner_call", function() {
								a(this).next().find(".btn-colorpicker").css("background-color", this.value)
							})
							.after(
									'<div class="dropdown dropdown-colorpicker"><a data-toggle="dropdown" class="dropdown-toggle" href="#"><span class="btn-colorpicker" style="background-color:'
											+ f
											+ '"></span></a><ul class="dropdown-menu'
											+ (d.caret ? " dropdown-caret" : "")
											+ (d.pull_right ? " pull-right" : "")
											+ '">' + e + "</ul></div>").next().find(".dropdown-menu").on("click",
									function(j) {
										var h = a(j.target);
										if (!h.is(".colorpick-btn")) {
											return false
										}
										h.closest("ul").find(".selected").removeClass("selected");
										h.addClass("selected");
										var i = h.data("color");
										g.val(i).change();
										j.preventDefault();
										return true
									})
				});
		return this
	}
})(jQuery);
(function(a, b) {
	a.fn.ace_tree = function(d) {
		var c = {
			"open-icon" : "icon-folder-open",
			"close-icon" : "icon-folder-close",
			selectable : true,
			"selected-icon" : "icon-ok",
			"unselected-icon" : "tree-dot"
		};
		c = a.extend({}, c, d);
		this
				.each(function() {
					var e = a(this);
					e
							.html('<div class = "tree-folder" style="display:none;">				<div class="tree-folder-header">					<i class="'
									+ c["close-icon"]
									+ '"></i>					<div class="tree-folder-name"></div>				</div>				<div class="tree-folder-content"></div>				<div class="tree-loader" style="display:none"></div>			</div>			<div class="tree-item" style="display:none;">				'
									+ (c["unselected-icon"] == null ? "" : '<i class="' + c["unselected-icon"]
											+ '"></i>') + '				<div class="tree-item-name"></div>			</div>');
					e.addClass(c.selectable == true ? "tree-selectable" : "tree-unselectable");
					e.tree(c)
				});
		return this
	}
})(jQuery);