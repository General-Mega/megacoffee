const params = new URLSearchParams(window.location.search);
// const messageEl = document.getElementById('message');
// if (params.get('error')) {
//     messageEl.textContent = '아이디 또는 비밀번호가 올바르지 않습니다.';
// } else if (params.get('logout')) {
//     messageEl.textContent = '로그아웃 되었습니다.';
//     messageEl.classList.add('success');
// } else if (params.get('registered')) {
//     messageEl.textContent = '회원가입이 완료되었습니다. 로그인해 주세요.';
//     messageEl.classList.add('success');
// } else if (params.get('resetRequested')) {
//     messageEl.textContent = '비밀번호 초기화 요청이 접수되었습니다.';
//     messageEl.classList.add('success');π
// }
$(document).ready(function(){
    $("#__btn_login").click(function(){
        let id = $("#__username").val().trim();
        let pwd = $("#__password").val().trim();

        let param = {"username" : id, "password" : pwd};

        doPost("/login", param
            , function(result){
                let code = result.code;
                let message = result.message;
                let data = result.data;

                //login 성공.
                //이동 화면 정보(data)가 있을 경우 해당 화면으로
                //없으면 메인 화면으로 이동
                location.href = '/';
        });
    });
});