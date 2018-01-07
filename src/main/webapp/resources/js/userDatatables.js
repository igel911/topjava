var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function changeStatus(checkbox, id) {
    var status = checkbox.is(':checked');
    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "status=" + status,
        success: function () {
            status ? checkbox.closest('tr').removeClass('disabled') : checkbox.closest('tr').addClass('disabled');
            successNoty("Status Changed");
        }
    });
}