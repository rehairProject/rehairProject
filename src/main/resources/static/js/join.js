$(document).ready(function() {
    // birth 오늘날짜 설정
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;              //1월=0이기 때문에 +1 해줌
    var yyyy = today.getFullYear();

    if (dd<10){
        dd = '0'+dd
    }
    if (mm<10){
        mm = '0'+mm
    }
    today = yyyy+'-'+mm+'-'+dd;
    $( '#birth' ).attr( 'value', today);

    // 회원가입 유효성검사
    $("#signupBtn").on("click", function(){
        var regExp = /^[a-zA-Z0-9]{4,12}$/;		// username 과 password 유효성검사 정규식
        var n_RegExp = /^[가-힣]{2,15}$/; 		// 이름 유효성검사 정규식
        var p_RegExp = /^\d{3}-\d{3,4}-\d{4}$/; 			//전화번호 유효성검사 정규식

        var username = document.getElementById("username");
        var password = document.getElementById("password");
        var passwordCheck = document.getElementById("passwordCheck");
        var name = document.getElementById("name");
        var phoneNumber = document.getElementById("phoneNumber");

        // username 유효성검사
        if (username.value ==''){
            alert("username을 입력하세요.");
            username.focus();
            return;
        }
        if (!regExp.test(username.value)){
            alert("username은 4~12자의 영문 대소문자와 숫자로만 입력하세요.");
            username.focus();
            return;
        }

        // password 유효성검사
        if (password.value == ''){
            alert("password를 입력하세요.");
            password.focus();
            return;
        }
        if (!regExp.test(password.value)){
            alert("password는 4~12자의 영문 대소문자와 숫자로만 입력하세요.");
            password.focus();
            return;
        }
        if (password.value != passwordCheck.value){
            alert("password와 passwordCheck가 다릅니다. 확인 후 다시 입력하세요.");
            passwordCheck.focus();
            return;
        }

        // 이름 유효성검사
        if(name.value ==''){
            alert("name을 입력하세요.");
            name.focus();
            return;
        }
        if(!n_RegExp.test(name.value)){
            alert("name은 특수문자,영어,숫자는 사용할수 없습니다. 한글만 입력하세요.");
            name.focus();
            return;
        }

        // 전화번호 유효성검사
        if (phoneNumber.value == ''){
            alert("phoneNumber를 입력하세요.");
            phoneNumber.focus();
            return;
        }
        if (!p_RegExp.test(phoneNumber.value)){
            alert("phoneNumber는 11자리의 숫자를 입력하세요. ('-'포함)");
            phoneNumber.focus();
            return;
        }
        document.joinForm.submit();
    });

    // 중복검사 버튼 클릭 시
    $("#duplicateBtn").on("click", function () {
        var username = $("#username").val();
        if (username == ""){
            alert("username을 입력하세요.");
            $("#username").focus();
            return false;
        }
        var data = {
            "username" : username
        }

        // csrf 관련 설정
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            url: "/account/duplicateUsername",
            type: "POST",
            dataType: "text",
            contentType: "application/json; charset=UTF-8",
            data: data.username
        }).done(function(result){
            if (result === "notExist") {
                alert("사용가능한 username 입니다.");
            } else {
                alert("이미 존재하는 username 입니다.");
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    });
});