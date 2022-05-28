$(document).ready(function() {
//탭 버튼
    $(".tab_box > .re_btn").click(function() {
        var idx = $(this).index();
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
        dates[i] = `<div id=${everyDay} class="date"><span class=${condition}>${date}</span><br><span class="holidayDesigner">&nbsp;</span></div>`;
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

$('.go-today').on('click', function(){
    date = new Date();
        renderCalendar();
});

});

//예약관리
function payComplete(obj) {
    var tr = obj.parentNode.parentNode;
    tr.children[0].submit();
   }

