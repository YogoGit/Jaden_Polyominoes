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
                {"data": "username", "width": "23%", "orderable": false},
                {"data": "score", "width": "18%"},
                {"data": "polyominoes", "width": "18%"},
                {"data": "time", "width": "18%"},
                {"data": "date", "width": "18%"},
            ]
        });
    }
);