var selectedTdClass = "red-cell";

$(document).ready(
    function() {
        //action cell click
        $('#monthCalendarContainerTable').on("click", "td", function() {
            if ($(this).text() != "") {
                $(this).toggleClass(selectedTdClass);
                showSelectForm();
            }
        } );

        //load all Schedule when page loaded
        showSchedule();

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
                showSchedule()
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
                showSchedule()
            }
        );
    }
);


function showSchedule() {
    $.getJSON(
        "/cabinet/clients/calendar/",
        {
            /*userId: $('#userInput').val(),*/
            year: $('#yearInput').val(),
            month: $('#monthInput').val()
        },
        function(data) {
            var calendarData = data.scheduleAll;
            var userScheduleData = data.userSchedule;
            var userSheduleArr = [];

            //get user schedule
            // $.each(userScheduleData, function(key, dateValue) {
            //     if (dateValue.sdate != null) {
            //         var day = dateValue.sdate.dayOfMonth;
            //         var month = dateValue.sdate.monthValue - 1; // Month is 0-indexed
            //         var year = dateValue.sdate.year;
            //         date = new Date(year, month, day);
            //         userSheduleArr.push( $.format.date(date, 'dd-MM-yyyy') );
            //     }
            // });

            //fill calendar with users schedule
            $('#monthCalendarContainerTable').html('<tr><th>Пн</th><th>Вт</th><th>Ср</th><th>Чт</th><th>Пт</th><th>Сб</th><th>Вс</th></tr>');
            $.each(data, function(key, valueList) {

                //fill table
                $('#monthCalendarContainerTable').append('<tr>');
                $.each(valueList, function(key2, calendarRecordDay) {
                    var date = new Date();
                    if (calendarRecordDay != null) {
                        var day = calendarRecordDay.date.dayOfMonth;
                        var month = calendarRecordDay.date.monthValue - 1; // Month is 0-indexed
                        var year = calendarRecordDay.date.year;
                        date = new Date(year, month, day);
                        var formatedDate = $.format.date(date, 'dd-MM-yyyy');
                        //select if has users schedule in this day
                        if ( (calendarRecordDay.users != null) && (calendarRecordDay.users.length > 0) ) {
                            $('#monthCalendarContainerTable tr:last').append('<td class="'+selectedTdClass+'">' + formatedDate + '</td>');
                        } else {
                            $('#monthCalendarContainerTable tr:last').append('<td>' + formatedDate + '</td>');
                        }
                    } else {
                        $('#monthCalendarContainerTable tr:last').append('<td></td>');
                    }
                });
                $('#monthCalendarContainerTable').append('</tr>');
            });

            //showSelectForm();
        }
    );
}


function showSelectForm() {
    $('#divSelectContainer').html('');
    var allTds = $("#monthCalendarContainerTable td.red-cell").map(function() {
        return this.innerHTML;
    }).get();

    for (var item in allTds) {
        $('#divSelectContainer').append('<input type="hidden" value="'+ allTds[item] +'" name="dates[]" />');
    }
}