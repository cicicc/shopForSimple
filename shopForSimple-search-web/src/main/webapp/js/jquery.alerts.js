// jQuery Alert Dialogs Plugin
//
// Version 1.1
//
// Cory S.N. LaViska
// A Beautiful Site (http://abeautifulsite.net/)
// 14 May 2009
//
// Visit http://abeautifulsite.net/notebook/87 for more information
//
// Usage:
//		jAlert( message, [title, callback] )
//		jConfirm( message, [title, callback] )
//		jPrompt( message, [value, title, callback] )
// 
// History:
//
//		1.00 - Released (29 December 2008)
//
//		1.01 - Fixed bug where unbinding would destroy all resize events
//
// License:
// 
// This plugin is dual-licensed under the GNU General Public License and the MIT License and
// is copyright 2008 A Beautiful Site, LLC. 
//
(function($) {
	/*
	 * 遮罩层
	 */
	var Shade=new function()
	{
	  var handle={};
	  var shade;
	  handle.show=function(func)
	  {
		  if(!shade)
		  {
			  shade=document.createElement('div');
			  shade.style.display = 'none';
			  shade.style.zIndex = 99997;
			  shade.style.filter = 'alpha(opacity = 20)';
			  shade.style.left = 0;
			  shade.style.width = '100%';
			  shade.style.position = 'absolute';
			  shade.style.top = 0;
			  shade.style.backgroundColor = '#989898';
			  shade.style.opacity = .2;
			  document.body.appendChild(shade);
		  }
		  with((document.compatMode=='CSS1Compat')?document.documentElement:document.body)
		  {
			  var ch=clientHeight,sh=scrollHeight;
			  shade.style.height=(sh>ch?sh:ch)+'px';
			  
			  var cw = clientWidth,sw = scrollWidth, ow=offsetWidth;
			  var width = cw > sw ? cw : sw;
			  width = width > ow ? width : ow;
			  
			  shade.style.width=width+'px';
			  shade.style.display='block';
		  }
		  
		  if(func){
			 func(); 
		  }
	  };
	  handle.hide=function(func){
		  shade.style.display='none';
		  if(func){
			 func(); 
		  }
	  };
	  
	  return handle;
	}
	
	$.alerts = {
		
		// These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time
		
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .01,                // transparency level of overlay
		overlayColor: '#89652b',               // base color of overlay
		draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)
		okButton: '&nbsp;确定&nbsp;',         // text for the OK button
		cancelButton: '&nbsp;取消&nbsp;', // text for the Cancel button
		dialogClass: null,                  // if specified, this class will be applied to all dialogs
		
		// Public methods
		
		alert: function(message, title, callback) {
			if( title == null ) title = '提示信息';
			$.alerts._show(title, message, null, 'alert', function(result) {
				if( callback ) callback(result);
			});
		},
		
		confirm: function(message, title, callback) {
			if( title == null ) title = '确认信息';
			$.alerts._show(title, message, null, 'confirm', function(result) {
				if( callback ) callback(result);
			});
		},
			
		prompt: function(message, value, title, callback) {
			if( title == null ) title = '输入信息';
			$.alerts._show(title, message, value, 'prompt', function(result) {
				if( callback ) callback(result);
			});
		},
		
		// Private methods
		
		_show: function(title, msg, value, type, callback) {
			Shade.show();
			$.alerts._hide();
			$.alerts._overlay('show');
			
			$("BODY").append(
			  '<div id="popup_container" class="window">' +
			    '<div class="titlehead"><h3 id="popup_title"></h3></div>' +
			    '<div id="popup_content" class="content">' +
			      '<div id="popup_message"></div>' +
				'</div>' +
			  '</div>');
			
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);
			
			// IE6 Fix
			var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed'; 
			
			$("#popup_container").css({
				position: pos,
				zIndex: 100000,
				padding: 0,
				margin: 0
			});
			
			if ($.browser.msie && $.browser.version < 7) {
				$ie6Fix = $('<iframe frameborder="0" src="#" id="shadow"></iframe>').css({
					position: "absolute",
					zIndex: 99999
				}).insertBefore("#popup_container")
			}
			
			$("#popup_title").text(title);
			$("#popup_content").addClass(type);
			$("#popup_message").text(msg);
			$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
			
			$("#popup_container").css({
				minWidth: $("#popup_container").outerWidth(),
				maxWidth: $("#popup_container").outerWidth()
			});
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);
			
			switch( type ) {
				case 'alert':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="submit1" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						Shade.hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'confirm':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="submit1"/> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" class="submit1"/></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback(true);
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback(false);
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
				break;
				case 'prompt':
					$("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="submit1"/> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" class="submit1"/></div>');
					$("#popup_prompt").width( $("#popup_message").width() );
					$("#popup_ok").click( function() {
						var val = $("#popup_prompt").val();
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback( val );
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback( null );
					});
					$("#popup_prompt, #popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
					if( value ) $("#popup_prompt").val(value);
					$("#popup_prompt").focus().select();
				break;
			}
			
			// Make draggable
			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ handle: $("#popup_title") });
					$("#popup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		
		_showNew: function(showX, showP, width_m, title, msg, value, type, callback) {
			Shade.show();
			$.alerts._hide();
			$.alerts._overlay('show');
			
			if(showX==1){
				titlehead = '<div class="titlehead"><h3 id="popup_title"></h3><span id="titlehead_x" class="sd_close"></span></div>';	
			}else{
				titlehead = '<div class="titlehead"><h3 id="popup_title"></h3></div>';
			}
			
			if(width_m == null){
				width_m = $("#popup_container").outerWidth();
			}
			
			if(showP==1){
				sd_img = '<div class="sd_img"></div>';	
			}else{
				sd_img = '';	
			}
			
			$("BODY").append(
			  '<div id="popup_container" class="window sd_window">' +
			    titlehead +
			    '<div id="popup_content" class="content">' +
			      '<div class="dig_content">'+
			      	sd_img +
			      	'<div id="popup_message" class="popup_message">' +
			      		//'<div id="popup_message"></div>' +
			      	'</div>' +
			      '</div>' +
				'</div>' +
			  '</div>');
			
			if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);
			
			// IE6 Fix
			var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed'; 
			
			$("#popup_container").css({
				position: pos,
				zIndex: 100000,
				margin: 0
			});
			
			if ($.browser.msie && $.browser.version < 7) {
				$ie6Fix = $('<iframe frameborder="0" src="#" id="shadow"></iframe>').css({
					position: "absolute",
					zIndex: 99999
				}).insertBefore("#popup_container")
			}
			
			$("#popup_title").text(title);
			$("#popup_content").addClass(type);
			/*if(msg!=null){
				$("#popup_message").text(msg);
			}*/
			$("#popup_message").text(msg);
			$("#popup_message").html( $("#popup_message").text().replace(/\n/g, '<br />') );
			
			$("#popup_container").css({
				minWidth: width_m,
				maxWidth: width_m
			});	
			
			$.alerts._reposition();
			$.alerts._maintainPosition(true);

			switch( type ) {
				case 'alert':
					$("#titlehead_x").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback(true);
					});
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="sd_btn" /></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback(true);
					});
					$("#popup_ok").focus().keypress( function(e) {
						if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');
					});
				break;
				case 'confirm':
					$("#titlehead_x").click( function() {
						$.alerts._hide();
						Shade.hide();
						top.location.reload();
					});
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="' + $.alerts.okButton + '" id="popup_ok" class="sd_btn sd_floatleft"/> <input type="button" value="' + $.alerts.cancelButton + '" id="popup_cancel" class="sd_btn1 sd_floatleft"/></div>');
					$("#popup_ok").click( function() {
						$.alerts._hide();
						Shade.hide();
						if( callback ) callback(true);
					});
					$("#popup_cancel").click( function() {
						$.alerts._hide();
						Shade.hide();
						top.location.reload();
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress( function(e) {
						if( e.keyCode == 13 ) $("#popup_ok").trigger('click');
						if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');
					});
				break;
			}
			
			// Make draggable
			if( $.alerts.draggable ) {
				try {
					$("#popup_container").draggable({ handle: $("#popup_title") });
					$("#popup_title").css({ cursor: 'move' });
				} catch(e) { /* requires jQuery UI draggables */ }
			}
		},
		
		_hide: function() {
			if ($.browser.msie && $.browser.version < 7) {
				$("#shadow").remove();
			}
			$("#popup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},
		
		_overlay: function(status) {
			switch( status ) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="popup_overlay"></div>');
					$("#popup_overlay").css({
						position: 'absolute',
						zIndex: 99998,
						top: '0px',
						left: '0px',
						width: '100%',
						height: $(document).height(),
						background: $.alerts.overlayColor,
						opacity: $.alerts.overlayOpacity
					});
				break;
				case 'hide':
					$("#popup_overlay").remove();
				break;
			}
		},
		
		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;
			var height = $("#popup_container").outerHeight(true);
			var width = $("#popup_container").outerWidth(true);
			if( top < 0 ) top = 0;
			if( left < 0 ) left = 0;
			
			// IE6 fix
			if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();
			
			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height( $(document).height() );
			$("#shadow").css({
				top: top + 'px',
				left: left + 'px',
				height: height + 34 + 'px',
				width: width + 'px'
			});
		},
		
		_maintainPosition: function(status) {
			if( $.alerts.repositionOnResize ) {
				switch(status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
		
	}
	
	// Shortuct functions
	jAlert = function(message, title, callback) {
		$.alerts.alert(message, title, callback);
	}
	
	jConfirm = function(message, title, callback) {
		$.alerts.confirm(message, title, callback);
	};
		
	jPrompt = function(message, value, title, callback) {
		$.alerts.prompt(message, value, title, callback);
	};

	jAlertNew = function(showX, showP, width, message, title, callback) {
		if( title == null ) title = '提示信息';
		$.alerts._showNew(showX, showP, width, title, message, null, 'alert', function(result) {
			if( callback ) callback(result);
		});
	}

	jConfirmNew = function(showX, showP, width, message, title, callback) {
		if( title == null ) title = '提示信息';
		$.alerts._showNew(showX, showP, width, title, message, null, 'confirm', function(result) {
			if( callback ) callback(result);
		});
	};
	
})(jQuery);