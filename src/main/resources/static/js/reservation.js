$(document).ready(function() {
//탭 버튼
    $(".tab_box > .re_btn").click(function() {
        var idx = $(this).index();
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(idx).addClass("on");
        $(".mainWrap > .reservation").hide();
        $(".mainWrap > .reservation").eq(idx).show();
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
        var treatment = new Array();
        $("input:checkbox[name='treatment']:checked").each(function(){
            treatment.push(this.value);
        });
        if (treatment.length = 0){
            alert("시술을 선택하세요.");
            return;
        }
        $("#reservationTreatments").val(treatment);

        $("#reservationInfo").submit();
    });
});