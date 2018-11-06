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
})