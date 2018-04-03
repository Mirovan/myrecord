var workdayTdClass = "workday-cell";
var vacationdayTdClass = "vacationday-cell";

$(document).ready(
    function() {
        //action cell click
        $('#monthCalendarContainerTable').on("click", "td", function() {
            if ($(this).text() != "") {
                var day = parseInt($(this).attr("data-day"));
                var month = parseInt($('#monthInput').val());
                var year = parseInt($('#yearInput').val());
                var productId = parseInt($('#productId').val());
                var workerId = parseInt($('#workerId').val());

                var url = "/cabinet/clients/record-day/";
                if ( !isNaN(day) && !isNaN(month) && !isNaN(year) ) {
                    url += + day + "/" + month + "/" + year + "/";
                }
                if ( !isNaN(workerId) ) {
                    url += "worker/" + workerId + "/";
                } else if ( !isNaN(productId) ) {
                    url += "product/" + productId + "/";
                }

                $(location).attr(
                    'href',
                    url
                );
            }
        } );

        //load all Schedule when page loaded
        showCalendar();

        //action - prev month click
        $('#aPrev').click (
            function () {
                var month = parseInt($('#monthInput').val()) - 1;
                var year = parseInt($('#yearInput').val());
                if (month <= 0) {
                    month = 12;
                    year--;
                    if (year < 0) year = 0;
                }
                $('#monthInput').val(month);
                $('#yearInput').val(year);
                showCalendar();
            }
        );

        //action - next month click
        $('#aNext').click (
            function () {
                var month = parseInt($('#monthInput').val()) + 1;
                var year = parseInt($('#yearInput').val());
                if (month > 12) {
                    month = 1;
                    year++;
                }
                $('#monthInput').val(month);
                $('#yearInput').val(year);
                showCalendar();
            }
        );

        $('#productId').change (
            function () {
                changeUserSelectList();
                showCalendar();
            }
        );

        $('#workerId').change (
            function () {
                //var workerId = parseInt($('#workerId').val());
                showCalendar();
            }
        );
    }
);


function showCalendar() {
    $.getJSON(
        "/cabinet/clients/json-calendar/",
        {
            year: $('#yearInput').val(),
            month: $('#monthInput').val(),
            productId: $('#productId').val(),
            workerId: $('#workerId').val()
        },
        function(data) {
            //fill calendar with users schedule
            $('#monthCalendarContainerTable').html('<tr><th>Пн</th><th>Вт</th><th>Ср</th><th>Чт</th><th>Пт</th><th>Сб</th><th>Вс</th></tr>');

            var dayOfWeek = 0;
            $.each(data, function(key, calendarItem) {
                dayOfWeek++;

                if (dayOfWeek == 1) {
                    $('#monthCalendarContainerTable').append('<tr>');
                }

                if (calendarItem == null) {
                    $('#monthCalendarContainerTable tr:last').append('<td></td>');
                } else {
                    var day = calendarItem.date.dayOfMonth;
                    var month = calendarItem.date.monthValue - 1; // Month is 0-indexed
                    var year = calendarItem.date.year;
                    var date = new Date(year, month, day);
                    var formatedDate = $.format.date(date, 'dd-MM-yyyy');
                    var users = calendarItem.data;
                    if ( (users != null) && (users.length > 0) ) {
                        $('#monthCalendarContainerTable tr:last')
                            .append('<td class="'+workdayTdClass+'" data-day="' + day + '">' + formatedDate + '</td>');
                    } else {
                        $('#monthCalendarContainerTable tr:last')
                            .append('<td class="'+vacationdayTdClass+'" data-day="' + day + '">' + formatedDate + '</td>');
                    }
                }

                if (dayOfWeek >= 7) {
                    dayOfWeek = 0;
                    $('#monthCalendarContainerTable').append('</tr>');
                }
            });

        }
    );
}


function changeUserSelectList() {
    $.getJSON(
        "/cabinet/clients/json-users-by-product/",
        {
            productId: $('#productId').val(),
        },
        function(data) {
            $('#workerId').html('');
            $('#workerId').append('<option value="">-- Все мастера --</option>');
            $.each(data, function(key, item) {
                $('#workerId').append('<option value="' + item.id + '">' + item.name + ' ' + item.sirname + '</option>');
            });
        }
    );
}
