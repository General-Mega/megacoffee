$(document).ready(function(){
    const _message = $("#__message");
    const params = new URLSearchParams(window.location.search);

    let _cookieRememberMe = $.cookie('rememberMe');
    $("#__remember-me").prop('checked', _cookieRememberMe);
    if(_cookieRememberMe){
        let _cookieRememberMeUsername = $.cookie('rememberMeUsername');
        $("#__username").val(_cookieRememberMeUsername);
    }

    // URL 파라미터에 따른 메시지 표시
    if (params.get('logout')) {
        _message.text('로그아웃 되었습니다.');
        _message.addClass('success');
        _message.show();
    } else if (params.get('registered')) {
        _message.text('회원가입이 완료되었습니다. 로그인해 주세요.');
        _message.addClass('success');
        _message.show();
    } else if (params.get('resetRequested')) {
        _message.text('비밀번호 초기화 요청이 접수되었습니다.');
        _message.addClass('success');
        _message.show();
    }

    // 로그인 버튼 클릭 시
    $("#__btn_login").click(function(){
        let id = $("#__username").val().trim();
        let pwd = $("#__password").val().trim();
        let rememberMe = $("#__remember-me").is(':checked');

        if (!id || !pwd) {
            _message.text('아이디와 비밀번호를 입력해주세요.');
            _message.removeClass('success');
            _message.show();
            return;
        }

        // Spring Security formLogin은 form-urlencoded 데이터를 기대하므로
        let formData = new FormData();
        formData.append("username", id);
        formData.append("password", pwd);
        formData.append("remember-me", rememberMe ? 'on' : '');

        doPostForm('/admin/login', formData, function(response) {
            let code = response.code;
            let message = response.message;

            if( code == 200){

                if(rememberMe){
                    $.cookie('rememberMe', true, { expires: 30, path: '/' }); // 30일 동안 쿠키 유지
                    $.cookie('rememberMeUsername', id, { expires: 30, path: '/' });
                }
                else{
                    $.removeCookie('rememberMe', { path: '/' });
                    $.removeCookie('rememberMeUsername', { path: '/' });
                }
                // 로그인 성공 시 대시보드로 이동
                location.href = '/admin/dashboard';
            }
            else {
                _message.text(message || '로그인에 실패했습니다.');
                _message.removeClass('success');
                _message.show();
            }
        });
    });
});