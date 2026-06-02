    $(document).ready(function(){
    const params = new URLSearchParams(window.location.search);
    const messageEl = document.getElementById('message');

    // URL 파라미터에 따른 메시지 표시
    if (params.get('logout')) {
        messageEl.textContent = '로그아웃 되었습니다.';
        messageEl.classList.add('success');
        messageEl.style.display = 'block';
    } else if (params.get('registered')) {
        messageEl.textContent = '회원가입이 완료되었습니다. 로그인해 주세요.';
        messageEl.classList.add('success');
        messageEl.style.display = 'block';
    } else if (params.get('resetRequested')) {
        messageEl.textContent = '비밀번호 초기화 요청이 접수되었습니다.';
        messageEl.classList.add('success');
        messageEl.style.display = 'block';
    }
    $("#__btn_login").click(function(){
        let id = $("#__username").val().trim();
        let pwd = $("#__password").val().trim();

        if (!id || !pwd) {
            messageEl.textContent = '아이디와 비밀번호를 입력해주세요.';
            messageEl.classList.remove('success');
            messageEl.style.display = 'block';
            return;
        }

        // Spring Security formLogin은 form-urlencoded 데이터를 기대하므로
        let formData = new FormData();
        formData.append("username", id);
        formData.append("password", pwd);
        formData.append("remember-me", $("#__remember-me").is(':checked') ? 'on' : '');

        $.ajax({
            url: "/admin/login",
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response){
                // 로그인 성공
                location.href = '/admin';
            },
            error: function(xhr, status, error){
                // 로그인 실패
                let errorMessage = '로그인에 실패했습니다.';
                
                try {
                    let response = JSON.parse(xhr.responseText);
                    if (response.message) {
                        errorMessage = response.message;
                    }
                } catch(e) {
                    console.error('Error parsing response:', e);
                }
                
                messageEl.textContent = errorMessage;
                messageEl.classList.remove('success');
                messageEl.style.display = 'block';
            }
        });
    });
});