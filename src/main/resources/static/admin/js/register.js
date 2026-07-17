let chkPwd = false;
let chkID = false;

$(document).ready(function(){
    checkButtonJoin();
    
    //ID 중복 확인
    $("#__btn_check_id").click(function(){
        validateID();
    });

    //ID 입력 시 검증
    $("#__username").keyup(function(event) {
        chkID = false;
        checkButtonJoin();
    });

    //비밀번호 입력 시 검증
    $("#__password").keyup(function(event) {
        validatePassword();
    });

    //비밀번호 확인 입력 시 검증
    $("#__password_confirm").keyup(function(event) {
        validatePassword();
    });

    // 회원가입 버튼 클릭 시
    $("#__btn_register").click(function(){
        let _username = $("#__username").val().trim();
        let _password = $("#__password").val().trim();

        if (!_username || !_password) {
            $("#__message").text('아이디와 비밀번호를 모두 입력해주세요.');
            $("#__message").css('color', 'red');
            return;
        }

        doPost('/admin/register', { userId: _username, password: _password }, function(response) {
            if (response.code === 200) {
                // 회원가입 성공 및 자동 로그인 후 첫 화면으로 이동
                var redirectUrl = '/admin/dashboard';
                if (response.data && response.data.redirectUrl) {
                    redirectUrl = response.data.redirectUrl;
                }
                location.href = redirectUrl;
            } else {
                // 회원가입 실패
                let errorMessage = response.message || '회원가입에 실패했습니다.';
                $("#__message").text(errorMessage);
                $("#__message").css('color', 'red');
            }
        });
    });
});

function validateID(){
    chkID = false;

    let id = $("#__username").val().trim();

    if(id == '') {
        $("#__message").text('아이디를 입력해주세요.');
        $("#__message").css('color', 'red');
        return;
    }

    doPost('/admin/check-id', { userId: id }, function(response) {
        let code = response.code;
        let message = response.message;

        if (code == 200) {
            $("#__message").text('사용 가능한 아이디입니다.');
            $("#__message").css('color', 'green');
            chkID = true;
        } else if (code == 409) {
            $("#__message").text('이미 존재하는 아이디입니다. 다른 아이디를 사용해 주세요.');
            $("#__message").css('color', 'red');
            chkID = false;
        }
        checkButtonJoin();
    });
}


function validatePassword() {
    chkPwd = false;

    checkButtonJoin();

    let pwd = $("#__password").val().trim();
    let rePwd = $("#__password_confirm").val().trim();

    $("#__password").val(pwd);
    $("#__password_confirm").val(rePwd);
    
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{10,20}$/;

    if (!passwordRegex.test(pwd)) {
        $("#__message").text('비밀번호는 10~20자, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.');
        $("#__message").css('color', 'red');
        return;
    }
    else{
        $("#__message").text('');
        $("#__message").css('color', 'green');
    }

    if(pwd == '') {
        $("#__message").text('비밀번호를 입력해주세요.');
        $("#__message").css('color', 'red');
        return;
    }

    if(pwd != rePwd) {
        $("#__message").text('비밀번호가 일치하지 않습니다.');
        $("#__message").css('color', 'red');
        return;
    }

    $("#__message").text('비밀번호가 일치합니다.');
    $("#__message").css('color', 'green');

    chkPwd = true;

    checkButtonJoin();
}

function checkButtonJoin(){
    if(chkID){
        $("#__btn_check_id").hide();
    } else {
        $("#__btn_check_id").show();
    }

    $("#__btn_register").prop('disabled', !(chkPwd && chkID));
}
