$('.form-signin.ui.form').form({
	fields : {
		username : {
			identifier : 'username',
			rules : [ 
				{
					type : 'empty',
					prompt : 'Please choose a username'
				}
			]
		},
		email : {
			identifier : 'email',
			rules : [ 
				{
					type : 'email',
					prompt : 'Please enter a valid email'
				}
			]
		},
		password : {
			identifier : 'password',
			rules : [
				{
					type : 'empty',
					prompt : 'Please enter a password'
				},
				{
					type : 'minLength[6]',
					prompt : 'Your password must be at least {ruleValue} characters'
				} 
			]
		}
	}
});

$('#userAvatar').on('change',function(){
    //get the file name
    var fileName = $(this).val().split('\\').pop();
    //replace the "Choose a file" label
    $(this).next('.custom-file-label').html(fileName);
});


//Use the auto-complete feature of the Google Places API to help users fill in address.
var placeSearch, autocomplete;
var componentForm = {
	locality: 'long_name',
	country: 'long_name',
};

function initAutocomplete() {
	// Create the auto-complete object, restricting the search to geographical location types.
	autocomplete = new google.maps.places.Autocomplete(
			/** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
			{types: ['geocode']});

	// When the user selects an address from the drop-down, populate the address fields in the form.
	autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
	// Get the place details from the auto-complete object.
	var place = autocomplete.getPlace();

	for (var component in componentForm) {
		document.getElementById(component).value = '';
	}

	// Get each component of the address from the place details
	// and fill the corresponding field on the form.
	for (var i = 0; i < place.address_components.length; i++) {
		var addressType = place.address_components[i].types[0];
		if (componentForm[addressType]) {
			var val = place.address_components[i][componentForm[addressType]];
			document.getElementById(addressType).value = val;
		}
	}
	document.getElementById('lat').value = place.geometry.location.lat();
	document.getElementById('lng').value = place.geometry.location.lng();
}


function geolocateUser () {
	
	var submitButton = document.getElementById("registerSubmit");
	submitButton.disable = true;
	submitButton.classList.add("loading");
	
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			
			document.getElementById('registerSubmit').disable = true;
			
			
			
			var geolocation = {
					lat: position.coords.latitude,
					lng: position.coords.longitude
			};
			
			document.getElementById('lat').value = geolocation.lat;
			document.getElementById('lng').value = geolocation.lng;
			
			var geocoder = new google.maps.Geocoder;
			
			geocoder.geocode({'location': geolocation}, function(results, status) {
				if (status === 'OK') {
					if (results[0]) {
						for (var i = 0; i < results[0].address_components.length; i++) {
							var addressType = results[0].address_components[i].types[0];
							if (componentForm[addressType]) {
								var val = results[0].address_components[i][componentForm[addressType]];
								document.getElementById(addressType).value = val;
							}
						}
					}
				}
			});
		});
	}
	submitButton.disable = false;
	submitButton.classList.remove("loading");
}