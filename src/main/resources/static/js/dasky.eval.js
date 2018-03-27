!
function(a) {
	a.Dasky = function(b, c) {
		var d = this;
		d.$el = a(b),
		d.$el.data("dasky", d),
		d._init = function(b) {
			var c = document.body,
			e = navigator.userAgent.toLowerCase(),
			f = function() {
				var a = document.createElement("dummy").style,
				b = "Webkit Moz O ms Khtml".split(" "),
				c = {};
				return function(d) {
					var e, f, g;
					if ("undefined" == typeof c[d]) {
						e = d.charAt(0).toUpperCase() + d.substr(1),
						f = (d + " " + b.join(e + " ") + e).split(" "),
						c[d] = null;
						for (g in f) if (void 0 !== a[f[g]]) {
							c[d] = f[g];
							break
						}
					}
					return c[d]
				}
			} (),
			g = null !== f("perspective") && c.classList && c.dataset && -1 === e.search(/(iphone)|(ipod)|(android)/);
			if (g) {
				if (d.$el.addClass("dsk-supported"), !a.jmpress) return alert("The page do not include jmpress js file,Dasky will not work!"),
				void 0;
				d.options = a.extend(!0, {},
				a.Dasky.defaults, b),
				d.$steps = d.$el.children("div"),
				d._layout(),
				d._initJmpress(),
				d._loadEvents(),
				d.options.autoplay && d._startSlider()
			} else d.$el.addClass("dsk-not-supported")
		},
		d._layout = function() {
			d.$slideWrapper = d.$steps.wrapAll('<div class="dsk-wrapper"></div>').parent(),
			d.$slideWrapper.wrap('<div class="dsk-linewrapper"></div>'),
			d.options.nav && (d.$nav = a('<div class="dsk-nav"></div>'), d.$pre = a('<span class="dsk-pre"></span>').appendTo(d.$nav), d.$next = a('<span class="dsk-next"></span>').appendTo(d.$nav), d.$pause = a('<span class="dsk-autoplay"></span>').appendTo(d.$nav), d.$el.append(d.$nav)),
			d.options.fullscreen && (a("html,body").css({
				height: "100%"
			}), d.$el.css({
				height: "100%"
			}))
		},
		d._initJmpress = function() {
			a.jmpress("template", "daskytpl", {
				children: function(a, b) {
					var d = b.has(".dsk-titlenode").size() > 0;
					return {
						y: 0,
						x: d ? 200 * (a - .5) : 200 * a,
						scale: d > 0 ? .5 : 1
					}
				}
			}),
			d.$slideWrapper.attr("data-template", "daskytpl").jmpress({
				viewPort: {
					height: 550,
					width: 1e3,
					maxScale: 1
				},
				fullscreen: d.options.fullscreen,
				keyboard: {
					use: d.options.keyboard
				}
			})
		},
		d._startSliderTimer = function() {
			d.slideTimer = setTimeout(function() {
				d.$slideWrapper.jmpress("next"),
				d.options.autoplay && d._startSliderTimer()
			},
			d.options.interval)
		},
		d._startSlider = function() {
			d.options.autoplay = !0,
			d._startSliderTimer(),
			d.options.nav && d.$pause.addClass("playing")
		},
		d._stopSlider = function() {
			d.options.autoplay && (clearTimeout(d.slideTimer), d.options.autoplay = !1, d.options.nav && d.$pause.removeClass("playing"))
		},
		d._loadEvents = function() {
			d.options.nav && (d.$pre.on("click.dasky",
			function() {
				d._stopSlider(),
				d.$slideWrapper.jmpress("prev")
			}), d.$next.on("click.dasky",
			function() {
				d._stopSlider(),
				d.$slideWrapper.jmpress("next")
			}), d.$pause.on("click.dasky",
			function() {
				d.options.autoplay ? d._stopSlider() : d._startSlider()
			}), d.$slideWrapper.on("click.dasky",
			function() {
				d._stopSlider()
			}));
			var a = function(a, b) {
				d._stopSlider(),
				0 > b ? d.$slideWrapper.jmpress("next") : d.$slideWrapper.jmpress("prev"),
				a.preventDefault()
			};
			d.options.mousewheel && (d.$slideWrapper.on("mousewheel.dasky", a), d.$el.on("mousewheel.dasky", a))
		},
		d._init(c)
	},
	a.Dasky.defaults = {
		autoplay: !0,
		nav: !0,
		interval: 4e3,
		mousewheel: !0,
		keyboard: !0,
		fullscreen: !0
	},
	a.fn.Dasky = function(b) {
		return this.each(function() {
			new a.Dasky(this, b)
		})
	}
} (jQuery),
function(a) {
	function d(b) {
		var c = b || window.event,
		d = [].slice.call(arguments, 1),
		e = 0,
		g = 0,
		h = 0;
		return b = a.event.fix(c),
		b.type = "mousewheel",
		c.wheelDelta && (e = c.wheelDelta / 120),
		c.detail && (e = -c.detail / 3),
		h = e,
		void 0 !== c.axis && c.axis === c.HORIZONTAL_AXIS && (h = 0, g = -1 * e),
		void 0 !== c.wheelDeltaY && (h = c.wheelDeltaY / 120),
		void 0 !== c.wheelDeltaX && (g = -1 * c.wheelDeltaX / 120),
		d.unshift(b, e, g, h),
		(a.event.dispatch || a.event.handle).apply(this, d)
	}
	var c, b = ["DOMMouseScroll", "mousewheel"];
	if (a.event.fixHooks) for (c = b.length; c;) a.event.fixHooks[b[--c]] = a.event.mouseHooks;
	a.event.special.mousewheel = {
		setup: function() {
			if (this.addEventListener) for (var a = b.length; a;) this.addEventListener(b[--a], d, !1);
			else this.onmousewheel = d
		},
		teardown: function() {
			if (this.removeEventListener) for (var a = b.length; a;) this.removeEventListener(b[--a], d, !1);
			else this.onmousewheel = null
		}
	},
	a.fn.extend({
		mousewheel: function(a) {
			return a ? this.bind("mousewheel", a) : this.trigger("mousewheel")
		},
		unmousewheel: function(a) {
			return this.unbind("mousewheel", a)
		}
	})
} (jQuery);