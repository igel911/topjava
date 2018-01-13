var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$.ajaxSetup({
    converters: {
        "text json": function (obj) {
            return JSON.parse(obj, function (key, value) {
                if (key === 'dateTime') return value.substring(0, 16).replace("T", " ");
                return value;
            } );
        }
    }
});


$("#dateTime").datetimepicker({
    format: 'Y-m-d H:i'
});

$("#startDate, #endDate").datetimepicker({
    format: 'Y-m-d',
    timepicker: false
});

$("#endTime, #startTime").datetimepicker({
    format: 'H:i',
    datepicker: false
});

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
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
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            data.exceed ? $(row).addClass("exceeded") : $(row).addClass("normal");
        },
        "initComplete": makeEditable
    });
});