function addPlaceholder($this,text){
	if(text!=null){
		$this.attr("placeholder",text);
	}
	if ("ActiveXObject" in window) {
		var txt_placeholder = $this.attr('placeholder');
		if ($this.val() === '') {
			$this.val(txt_placeholder).addClass('txt-placeholder');
		}
		$this.off('.placeholder');
		$this.on('focus.placeholder', function() {
			if ($this.val() === txt_placeholder) {
				$this.val('').removeClass('txt-placeholder');
			}
		}).on('blur.placeholder', function() {
			if ($this.val() === '') {
				$this.val(txt_placeholder).addClass('txt-placeholder');
			}
		});
	}
}

(function() {
	if (!Array.prototype.indexOf) {
		Array.prototype.indexOf = function(elt /* , from */) {
			var len = this.length >>> 0;
			var from = Number(arguments[1]) || 0;
			from = (from < 0) ? Math.ceil(from) : Math.floor(from);
			if (from < 0)
				from += len;
			for (; from < len; from++) {
				if (from in this && this[from] === elt)
					return from;
			}
			return -1;
		};
	}

	if ("ActiveXObject" in window) {
		$('input[placeholder],textarea[placeholder]').each(function() {
			addPlaceholder($(this),null);
		});
	}
})(jQuery);