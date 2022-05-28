$(document).ready(function() {
//탭 버튼
    $(".tab_box > .re_btn").click(function() {
        var idx = $(this).index();
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(idx).addClass("on");
        $(".mainWrap > .tab:not(:eq(idx))").hide();
        $(".mainWrap > .tab").eq(idx).show();
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
                url:`admin/holiday/${date}`,
            }).done(function(resp){
                $('#holidayDesigner').text(resp);
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

//휴무정보 유효성검사
    $("#nextBtn").on("click", function(){
        //날짜
        var date = $("#date").val();
        if (date == "null"){
            alert("날짜를 선택하세요.");
            return;
        }
        $("#date").val(date);

        //디자이너
        var designer = $("input[name='designer']:checked").val();
        if (designer == null){
            alert("디자이너를 선택하세요.");
            return;
        }
        $("#reservationDesigner").val(designer);
        $("#holidayInfo").submit();
    });



});

function changeGrade(selectedAuth){
    //멤버십 선택된 value
    var gradeVal = selectedAuth.value;

    var tr = selectedAuth.parentNode.parentNode.parentNode;
    var username = tr.children[1].innerText;

    selectedAuth.parentNode.children[0].value = username;
    selectedAuth.parentNode.submit();
}



function changeAuth(selectedGrade){
    //멤버십 선택된 value
    var gradeVal = selectedGrade.value;

    var tr = selectedGrade.parentNode.parentNode.parentNode;
    var username = tr.children[1].innerText;

    selectedGrade.parentNode.children[0].value = username;
    selectedGrade.parentNode.submit();
}