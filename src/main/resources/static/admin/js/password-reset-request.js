$(document).ready(function() {
    $("#__btnRequest").click(function(){
        let _username = $("#__username").val().trim();
        if (!_username) {
            showAlert('입력 오류', '아이디를 입력해주세요.', function(){
                $("#__username").val("").focus();
            });
            return;
        }

        doPost('/admin/password-reset-request', { userId: _username }, function(response) {
            if (response.code === 200) {
                showAlert('비밀번호 초기화 요청', '비밀번호 초기화 요청이 접수되었습니다. 관리자가 처리합니다.', function(){
                    window.location.href = '/admin/login?resetRequested=true';
                });
            } else {
                showAlert('비밀번호 초기화 요청 실패', '비밀번호 초기화 요청에 실패했습니다. 다시 시도해 주세요.', function(){
                    $("#__username").val("").focus();
                });
            }
        });
    });
});