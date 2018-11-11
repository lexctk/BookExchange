// Fix to top with data-toggle="affix"
//--------------------------------------------
$(document).ready(function() {

	let toggleAffix = function(affixElement, scrollElement, wrapper) {
  
		let height = affixElement.outerHeight(),
			top = wrapper.offset().top;
    
		if (scrollElement.scrollTop() >= top){
			wrapper.height(height);
			affixElement.addClass('affix');
		}
		else {
			affixElement.removeClass('affix');
			wrapper.height('auto');
		}
	};

	$('[data-toggle="affix"]').each(function() {
		let element = $(this),
			wrapper = $('<div></div>');

		element.before(wrapper);
		$(window).on('scroll resize', function() {
			toggleAffix(element, $(this), wrapper);
		});
	});
});
//--------------------------------------------

// Smooth scroll to a destination
//--------------------------------------------
let $root = $('html, body');

$('a[href^="#"]').click(function () {
    $root.animate({
        scrollTop: $( $.attr(this, 'href') ).offset().top
    }, 800);
    return false;
});
//--------------------------------------------

// Back to top button
//--------------------------------------------
jQuery(document).ready(function() {
    let offset = 250;
    let duration = 300;
    
    jQuery(window).scroll(function() {
        if (jQuery(this).scrollTop() > offset) {
            jQuery('.back-to-top').fadeIn(duration);
        } else {
            jQuery('.back-to-top').fadeOut(duration);
        }
    });
     
    jQuery('.back-to-top').click(function(event) {
        event.preventDefault();
        jQuery('html, body').animate({scrollTop: 0}, duration);
        return false;
    });
});
//--------------------------------------------

// Search API function
//--------------------------------------------
let index = 0;

$(function() {
	$('#searchAPI').on('submit', function(e) {
		e.preventDefault();
		index = 0;
		loadNextBooks()
	});
});

function loadNextBooks() {
	$('#searchAPIResults').html('<div class="ui active centered inline loader"></div>');
	 
	let query = $('#searchAPI :input').serializeArray();
	query.push({name: 'index', value: index});
	
	$.ajax({
		url: '../searchapi',
		type: 'GET',
		data: query,
		success: function(response) {
			$('#searchAPIResults').html(response);
			index = index + 12;
		}
	});
}
//--------------------------------------------


// Book List
//--------------------------------------------
let acknowledge = true;

// show user books
if ($('#book-list').length) {
	if (!$.trim($('#book-list').html()) && acknowledge) {
		let classes = $('#book-list').attr('class');
		$.ajax({
			url: 'booklist',
			type: 'GET',
			data:{'classes':classes},
			success: function(response) {
				acknowledge = false;
				processUserBooks(response);
				
			}
		})
	}
}

function processUserBooks (response) {
	$('#book-list').html(response);
	
	if ($('#book-list-filter').length) {
		$('.filtr-container').imagesLoaded( function() {
			var filterizr = $('.filtr-container').filterizr({
				layout: 'sameWidth',
				multifilterLogicalOperator: 'or'
			});
		});
	}
}
//--------------------------------------------
