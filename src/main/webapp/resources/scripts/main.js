$(document).ready(function() {

  var toggleAffix = function(affixElement, scrollElement, wrapper) {
  
    var height = affixElement.outerHeight(),
        top = wrapper.offset().top;
    
    if (scrollElement.scrollTop() >= top){
        wrapper.height(height);
        affixElement.addClass("affix");
    }
    else {
        affixElement.removeClass("affix");
        wrapper.height('auto');
    }
      
  };

  $('[data-toggle="affix"]').each(function() {
    var element = $(this),
        wrapper = $('<div></div>');
    
    element.before(wrapper);
    $(window).on('scroll resize', function() {
        toggleAffix(element, $(this), wrapper);
    });
    
    // initialize
    toggleAffix(element, $(window), wrapper);
  });
  
});


//Smooth scroll to a destination
var $root = $('html, body');

$('a[href^="#"]').click(function () {
    $root.animate({
        scrollTop: $( $.attr(this, 'href') ).offset().top
    }, 800);
    return false;
});

// Back to top button
jQuery(document).ready(function() {
    var offset = 250;
    var duration = 300;
    
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

