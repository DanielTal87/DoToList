$(document).ready(function() {

	$('#dailyInput').hide();
	$('#dailySubmit').hide();

	$('#DailyTip').on('click', function() {
		$('#dailyInput').show();
		$('#dailySubmit').show();
	});

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	};

	$('#UpdateItem').on('click', function() {
		var id = $('#deleteId').val();
		$('#assingmentToUpdate').val($('#' + id).val());
		$('#categoryToUpdate').val($('#' + id).attr('name'));
		$(this).attr('data-target', '#UpdateIteModel');
	});

	$('#DeleteItem').on('click', function() {
		$('#delForm').submit();
	});

	$(document).on('click', '.checkIds', function() {
		if ($(this).prop('checked')) {
			$('#deleteId').val($(this).attr('id'));
			$('#updateId').val($(this).attr('id'));
		} else {
			$('#deleteId').val("");
			$('#updateId').val("");
		}
	});

	$('input[type="checkbox"]').on('change', function() {
		$('input[type="checkbox"]').not(this).prop('checked', false);
	});

	$('#logOut').on('click', function() {
		$('#logOutForm').submit();
	});
});
