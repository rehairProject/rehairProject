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
    console.log("000");
    $(".timeChk").removeClass("on");
    $(".time .col .timeChk").eq(idx).addClass("on");
    });


});