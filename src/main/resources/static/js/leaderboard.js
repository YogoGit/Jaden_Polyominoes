$(document).ready(function () {
        $('#leaderboards').DataTable({
            "processing": true,
            "serverSide": true,
            "searching": false,
            "order": [[2, 'desc']],
            "ajax": {
                "url": "/api/leaderboard",
                "type": "POST",
                "dataType": "json",
                "contentType": "application/json",
                "data": function (d) {
                    return JSON.stringify(d);
                }
            },
            "columns": [
                {"data": "index", "width": "5%", "orderable": false},
                {"data": "username", "width": "25%", "orderable": false},
                {"data": "score", "width": "15%"},
                {"data": "level", "width": "10%"},
                {"data": "rows", "width": "10%"},
                {"data": "pieces", "width": "10%"},
                {"data": "time", "width": "10%"},
                {"data": "date", "width": "15%"},
            ]
        });
    }
);