var ajaxUrl = "ajax/meals/";
var datatableApi;

$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

$("#filterForm").submit(function () {
    updateTable();
    return false;
});

$('#dateTime').datetimepicker();

function updateTable() {
    var form = $("#filterForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function (result) {
            fillTable(result);
        }
    });
}

function fillTable(data) {
    datatableApi.clear().rows.add(data).draw();
}

function clearFilters() {
    $("#filterForm")[0].reset();
    $.get(ajaxUrl, fillTable);
}