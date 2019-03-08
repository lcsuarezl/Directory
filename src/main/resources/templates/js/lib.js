

var usersList = [];

var basepath = "http://localhost:8082/";

function getAllUsers() {  
    $.ajax({  
          url: basepath+"users",  
          method: "GET",  
          headers: { "Accept": "application/json" },  
          success: function (data)  
          {   
            $.each(data, function(index, item){
              		usersList.push(item);
              	});
        	window.localStorage.setItem('users', JSON.stringify(usersList));
        	renderUsers();
      },  
      error: function (data)  
      {   
          console.log("error");  
      }  
	});
}


function showUpdateUser(id){
	console.log("showUpdateUser");
	$.ajax({
		url: basepath+'user/'+id,
		type:'GET',
		success: function(data){
			console.log(data);
			$("#editId").val(data.id);
			$("#editFirstName").val(data.firstName);
			$("#editLastName").val(data.lastName);
			$("#editEmail").val(data.email);
		}
	})
	$("#editUserModal").modal('show');
}


$("#editUserForm").submit(function(event){
	console.log("edit user")
	event.preventDefault();
	var id=$("#editId").val();
	var str = {  
					"id": id,
				   	"firstName": $("#editFirstName").val(),
				   	"lastName": $("#editLastName").val(),
				   	"email":$("#editEmail").val()
				};
	$.ajax({
		url: basepath+'user/'+id,
		type:'PUT',
		beforeSend: function(request) {
    		request.setRequestHeader("Content-Type", "application/json");
  		},
		dataType:'json',
		contenType:'application/json',
		success: function(data){
			usersList = usersList.filter(function(value, index, arr){
    						return value.id != id;
    					});
			usersList.push(data);
			window.localStorage.setItem('users', JSON.stringify(usersList));
			$("#editUserModal").modal('hide');
			cleanEditUserForm();
			renderUsers();
		},
		data: JSON.stringify(str)
	})
});

function cleanEditUserForm(){
	$("#editId").val("");
	$("#editFirstName").val("");
	$("#editLastName").val("");
	$("#editEmail").val("");
}


function showDeleteUser(id){
	$("#deleteUserModal").modal('show');
	$("#deletionIds").val(id);
}

$("#delUserForm").submit(function(event){
	console.log("del user")
	event.preventDefault();
	var id = $("#deletionIds").val();
	$.ajax({
		url: basepath+'user/'+id,
		type:'DELETE',
		success: function(data){
			usersList = usersList.filter(function(value, index, arr){
    						return value.id != id;
    					});
			window.localStorage.setItem('users', JSON.stringify(usersList));
			$("#deleteUserModal").modal('hide');
			renderUsers();
		}
	})
});

function userTemplate(user){
	var str='<tr><td>'+user.id+'</td>'; 
		str+='<td>'+user.firstName+'</td>'; 
		str+='<td>'+user.lastName+'</td>';
		str+='<td>'+user.email+'</td>';
		str+='<td><a href="#editUserModal" class="edit"' 
		str+='onclick="showUpdateUser('+user.id+')"';
		str+='><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>';
		str+='</a><a href="#deleteUserModal" class="delete"';
		str+='onclick="showDeleteUser('+user.id+')';
		str+='"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a></td></tr>';
	return str;
}

function renderUsers() {
	console.log("renderUsers");
	var lst = "<tbody>";
	$.each(usersList, function(i, user){
		lst += userTemplate(user);
	})
	$("#userList tbody").replaceWith(lst+"</tbody>");
}

function cleanAddUserForm(){
	$("#addFirstName").val("");
	$("#addLastName").val("");
	$("#addEmail").val("");
}

$("#addUserForm").submit(function(event){
	console.log("add user")
	event.preventDefault();
	var str = {  
				   "firstName": $("#addFirstName").val(),
				   "lastName": $("#addLastName").val(),
				   "email":$("#addEmail").val()
				};
	$.ajax({
		url: basepath+'user',
		type:'POST',
		beforeSend: function(request) {
    		request.setRequestHeader("Content-Type", "application/json");
  		},
		dataType:'json',
		contenType:'application/json',
		success: function(data){
			usersList.push(data);
			window.localStorage.setItem('users', JSON.stringify(usersList));
			$("#addUserModal").modal('hide');
			cleanAddUserForm();
			renderUsers();
		},
		error: function(data){

			$.each(data.responseJSON.errors, function(index, item){
					var id=item.field.charAt(0).toUpperCase() + item.field.slice(1);
					$("#add"+id).appendTo('<span>'+item.defaultMessage+'</span>');
					console.log(id);
					console.log(item.defaultMessage);
			});			
			console.log(data); 
		},
		data: JSON.stringify(str)
	})
	
});

$(document).ready(function(){
	getAllUsers();
});
