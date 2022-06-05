$(document).ready(function() {
//탭 버튼
    var tabFlag = $('#tabFlag').val();
    if (tabFlag != 'null'){
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(1).addClass("on");
        $(".mainWrap > .tab:not(:eq(1))").hide();
        $(".mainWrap > .tab").eq(1).show();
        $('#tabFlag').val(null);
        tabFlag = $('#tabFlag').val();
    } else {
        $(".tab_box > .re_btn").click(function() {
            var idx = ($(this).index() -1);
            $(".tab_box > .re_btn").removeClass("on");
            $(".tab_box > .re_btn").eq(idx).addClass("on");
            $(".mainWrap > .tab:not(:eq(idx))").hide();
            $(".mainWrap > .tab").eq(idx).show();
        });
    }

    $(".tab_box > .re_btn").click(function() {
        var idx = ($(this).index() -1);
        $(".tab_box > .re_btn").removeClass("on");
        $(".tab_box > .re_btn").eq(idx).addClass("on");
        $(".mainWrap > .tab:not(:eq(idx))").hide();
        $(".mainWrap > .tab").eq(idx).show();
    });

//달력
    let date = new Date();
    const renderCalendar = () => {
        $.ajax({
            url : '/holidayCheck',
            method : 'GET',
            success : function(resp){
                $.each(resp, function(idx, item){
                    $('#' + idx).find('.holidayDesigner').text(resp[idx]);
                });
            }
        });
        const viewYear = date.getFullYear();
        const viewMonth = date.getMonth();

        document.querySelector('.year-month').textContent = `${viewYear} 년 ${viewMonth + 1} 월`;

        const prevLast = new Date(viewYear, viewMonth, 0);
        const thisLast = new Date(viewYear, viewMonth +1, 0);

        const PLDate = prevLast.getDate();
        const PLDay = prevLast.getDay();

        const TLDate = thisLast.getDate();
        const TLDay = thisLast.getDay();

        const prevDates = [];
        const thisDates = [...Array(TLDate + 1).keys()].slice(1);
        const nextDates = [];

        if (PLDay !== 6){
            for (let i=0; i<PLDay + 1; i++){
                prevDates.unshift(PLDate - i);
            }
        }

        for (let i=1; i<7 - TLDay; i++){
            nextDates.push(i);
        }

        const dates = prevDates.concat(thisDates, nextDates);
        const firstDateIndex = dates.indexOf(1);
        const lastDateIndex = dates.lastIndexOf(TLDate);

        dates.forEach((date, i) => {
            const condition = i >= firstDateIndex && i < lastDateIndex + 1 ? 'this' : 'other';

            var month = viewMonth + 1;
            if (condition == 'other' && i > lastDateIndex) {
             //   month = i >= firstDateIndex && i < lastDateIndex + 1 ? month : month + 1;
                month = month + 1;
            } else if (condition == 'other' && i < firstDateIndex){
                month = month - 1;
            }

            if(date<10) {
                date='0'+date;
            }
            if(month<10) {
                month='0'+month;
            }
            const everyDay = viewYear + '-' + month + '-' + date;
            dates[i] = `<div id=${everyDay} class="date" value=${everyDay}><span class=${condition}>${date}</span><br><span class="holidayDesigner">&nbsp;</span></div>`;
        });

        document.querySelector('.dates').innerHTML = dates.join('');

        const today = new Date();
        if (viewMonth === today.getMonth() && viewYear === today.getFullYear()){
            for (let date of document.querySelectorAll('.this')){
                if(+date.innerText === today.getDate()){
                    date.classList.add('today');
                    break;
                }
            }
        }
    }
    renderCalendar();

    $('.go-prev').on('click', function(){
        date.setMonth(date.getMonth() - 1);
        renderCalendar();
    });

    $('.go-next').on('click', function(){
        date.setMonth(date.getMonth() + 1);
        renderCalendar();
    });

//휴무정보 유효성검사
    $("#nextBtn").on("click", function(){
        var date = $("#date").val();
        if (date == "null"){
            alert("날짜를 선택하세요.");
            return;
        }
        $("#date").val(date);

        var designer = $("input[name='designer']:checked").val();
        if (designer == null){
            alert("디자이너를 선택하세요.");
            return;
        }
        $("#reservationDesigner").val(designer);
        $("#holidayInfo").submit();
    });

    $('#delBtn').on("click", function(){
        var date = $("#date").val();
        if (date == "null"){
            alert("날짜를 선택하세요.");
            return;
        }
        $("#date").val(date);
        var delDate = $("#date").val();
        $('#holidayInfo').attr('action', '/admin/holiday/' + delDate);
        $('#holidayInfo').submit();
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

//날짜 선택
$(document).on('click', '.date', function(){
    var selectedDate = $(this).context.id;
    console.log(selectedDate);
    $(this).css({"height":"60px", "background":"#fcef7e", "border-radius":"100%"});
    $('.dates').find('.date').not($(this)).css('background','white');
    alert('선택하신 날짜는 ' + selectedDate + " 입니다.");
    $('#date').val(selectedDate);
});