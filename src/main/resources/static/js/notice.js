$(document).ready(function() {
//탭 버튼
    $(".tab_box > .re_btn").click(function() {
    var idx = $(this).index();
    console.log(idx);
    $(".tab_box > .re_btn").removeClass("on");
    $(".tab_box > .re_btn").eq(idx).addClass("on");
    $(".noticeContainer > .notice").hide();
    $(".noticeContainer > .notice").eq(idx).show();
    });
});