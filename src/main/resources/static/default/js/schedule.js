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
        "/cabinet/users/json-month-schedule/",
        {
            userId: $('#userInput').val(),
            year: $('#yearInput').val(),
            month: $('#monthInput').val()
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
                    var schedule = calendarItem.data;
                    if ( schedule != null ) {
                        $('#monthCalendarContainerTable tr:last')
                            .append('<td class="'+selectedTdClass+'">' + formatedDate + '</td>');
                    } else {
                        $('#monthCalendarContainerTable tr:last').append('<td>' + formatedDate + '</td>');
                    }
                }

                if (dayOfWeek >= 7) {
                    dayOfWeek = 0;
                    $('#monthCalendarContainerTable').append('</tr>');
                }
            });

            //showSelectForm();
        }
        // function(data) {
        //     var calendarData = data.scheduleAll;
        //     var userScheduleData = data.userSchedule;
        //     var userSheduleArr = [];
        //
        //     //get user schedule
        //     $.each(userScheduleData, function(key, dateValue) {
        //         if (dateValue.sdate != null) {
        //             var day = dateValue.sdate.dayOfMonth;
        //             var month = dateValue.sdate.monthValue - 1; // Month is 0-indexed
        //             var year = dateValue.sdate.year;
        //             date = new Date(year, month, day);
        //             userSheduleArr.push( $.format.date(date, 'dd-MM-yyyy') );
        //         }
        //     });
        //
        //     //fill calendar with users schedule
        //     $('#scheduleContainer').html('<tr><th>Пн</th><th>Вт</th><th>Ср</th><th>Чт</th><th>Пт</th><th>Сб</th><th>Вс</th></tr>');
        //     $.each(calendarData, function(key, valueList) {
        //
        //         //fill table
        //         $('#scheduleContainer').append('<tr>');
        //         $.each(valueList, function(key2, scheduleDay) {
        //             var date = new Date();
        //             if (scheduleDay.sdate != null) {
        //                 var day = scheduleDay.sdate.dayOfMonth;
        //                 var month = scheduleDay.sdate.monthValue - 1; // Month is 0-indexed
        //                 var year = scheduleDay.sdate.year;
        //                 date = new Date(year, month, day);
        //                 var formatedDate = $.format.date(date, 'dd-MM-yyyy');
        //                 if ( userSheduleArr.indexOf(formatedDate) >= 0 ) {
        //                     $('#scheduleContainer tr:last').append('<td class="'+selectedTdClass+'">' + formatedDate + '</td>');
        //                 } else {
        //                     $('#scheduleContainer tr:last').append('<td>' + formatedDate + '</td>');
        //                 }
        //             } else {
        //                 $('#scheduleContainer tr:last').append('<td></td>');
        //             }
        //         });
        //         $('#scheduleContainer').append('</tr>');
        //     });
        //
        //     showSelectForm();
        // }
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