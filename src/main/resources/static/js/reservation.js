$(document).ready(function() {
    var reservations = null;
    var holidayDesigners = [];

//탭 버튼
    $(".tab_box > .re_btn").click(function() {
        var idx = $(this).index();
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(idx).addClass("on");
        $(".mainWrap > .reservation").hide();
        $(".mainWrap > .reservation").eq(idx).show();
    });
//datepicker
    var tomorrowDate = tomorrowDate();
    $('#calendar').datepicker({
        dateFormat: 'yy-mm-dd',
		dayNamesMin : ['일','월','화','수','목','금','토'],
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		minDate: new Date(tomorrowDate),
		beforeShowDay: function(date){
            return [(date.getDay() != 0)];
        },
		onSelect: function(dateText, inst) {
            var date = $(this).val();
            alert('선택하신 날짜는'+date);
            $('#date').val(date);

            $.ajax({
                type:"GET",
                url:`reservation/${date}`,
            }).done(function(resp){
                holidayDesigners.splice(0, holidayDesigners.length);
                reservations = resp.reservations;

                var radioDesigners = [];
                for (var i=1; i<5; i++){
                    radioDesigners.push($('#designer' + i).val());
                }
                $('input[name=designer]').attr('disabled', false);
                if (resp.status != null){
                    $('#dayOffDesigner').val(resp.status.name);
                    /* 휴무 디자이너 비활성화 */
                    $.each(radioDesigners, function(idx, item){
                        if(radioDesigners[idx] === resp.status.name){
                            if ($('input[name=designer]').eq(idx).attr('checked', true)){
                                $('input[name=designer]').eq(idx).attr('checked', false);
                            }
                            $('input[name=designer]').eq(idx).attr('disabled', true);
                        }
                    });
                }

                $.each(radioDesigners, function(idx, item){
                    if (!$('input[name=designer]').eq(idx).attr('disabled')){
                        holidayDesigners.push(radioDesigners[idx]);
                    } else {
                        holidayDesigners.push('휴무');
                    }
                });
            }).fail(function(error){
                 console.log(JSON.stringify(error));
            });
        }
	});
    function tomorrowDate(){
        var tomorrowDate ="";
        var today = new Date();
        var dd = today.getDate()+1;
        var mm = today.getMonth()+1;
        var yyyy = today.getFullYear();
        if(dd<10) {
            dd='0'+dd
        }
        if(mm<10) {
            mm='0'+mm
        }
        tomorrowDate = yyyy+'-'+mm+'-'+dd;
        return tomorrowDate;
    }

   $('.btn-check').on('change', function(){
        //선택한 시간 값 가져오기
        var selectedTime = $(this).val();
        var radioDesigners = [];
        for (var i=1; i<5; i++){
           radioDesigners.push($('#designer' + i).val());
        }

        var rt = [];
        $.each(reservations, function(idx, item){
            rt.push(reservations[idx].time);
        });
        if (rt.indexOf(selectedTime) > -1){
            $.each(reservations, function(idx, item){
                var rd = [];
                if (selectedTime == reservations[idx].time){
                    rd.push(reservations[idx].designer);
                    $.each(holidayDesigners, function(idxH, itemH){
                        $.each(rd, function(i, it){
                                if (holidayDesigners[idxH] == '휴무'){
                                    $('input[name=designer]').eq(idxH).attr('disabled', true);
                                } else if (holidayDesigners[idxH] != '휴무'){
                                    $('input[name=designer]').eq(idxH).attr('disabled', false);
                                if (rd[i] == holidayDesigners[idxH]){
                                    $('input[name=designer]').eq(idxH).attr('disabled', true);
                                }
                            }
                        });
                    });
                }
            });
        } else {
            $.each(holidayDesigners, function(idxH, itemH){
                if (holidayDesigners[idxH] == '휴무'){
                    $('input[name=designer]').eq(idxH).attr('disabled', true);
                } else {
                    $('input[name=designer]').eq(idxH).attr('disabled', false);
                }
            });
        }
   });

    $('.form-check-label').on('click', function(){
        var price = $(this).children('span:eq(0)').text();
        $('#price').val(price);
    });
//시간
    $('.time').find('.timeChk').bind('click',function () {
        $except = $(this);
        $except.css({'background':'#fcef7e', 'border-radius':'100%'});
        $('.time').find('.timeChk').not($(this)).css('background','white');
    });

//예약정보 유효성검사
    $("#nextBtn").on("click", function(){
        //날짜
        var date = $("#date").val();
        if (date == "null"){
            alert("날짜를 선택하세요.");
            return;
        }
        $("#date").val(date);

        //시간
        var time = $("input[name='time']:checked").val();
        if (time == null){
            alert("시간을 선택하세요.");
            return;
        }
        $("#reservationTime").val(time);

        //디자이너
        var designer = $("input[name='designer']:checked").val();
        if (designer == null){
            alert("디자이너를 선택하세요.");
            return;
        }
        $("#reservationDesigner").val(designer);

        //시술
        var style = $("input[name='style']:checked").val();
        if (style == null){
            alert("시술을 선택하세요.");
            return;
        }
        $("#reservationStyle").val(style);

        $("#reservationInfo").submit();
    });
});