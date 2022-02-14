$(document).ready(function() {
//탭 버튼
    $(".tab_box > .re_btn").click(function() {
        var idx = $(this).index();
        console.log(idx);
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(idx).addClass("on");
        $(".mainWrap > .reservation").hide();
        $(".mainWrap > .reservation").eq(idx).show();
    });
//시간
    $(".time .timeChk").click(function() {
        var idx = $(this).closest('.time').find('.timeChk').index();
        $(".timeChk").removeClass("on");
        $(".time .col .timeChk").eq(idx).addClass("on");
    });
    /*$('input[type="checkbox"]').change(function() {
            if ("input[name='time']:checked"){
                $(this).css("color","red");
            } else {
                $(this).css("color","white");
            }
        });*/
//예약정보 넘기기
    $("#nextBtn").on("click", function(){
        // 시간
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
        $("#reservationDesigner").val(time);

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