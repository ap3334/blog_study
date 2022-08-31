let index = {
    init: function () {
        $("#join-btn").on("click", () => {
            this.join();
        });
        /*$("#login-btn").on("click", () => {
            this.login();
        });*/
    },

    join: function () {

        var data = {
            username: $("#user_name").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };


      $.ajax({
          type: "POST",
          url: "/user/auth/join",
          data: JSON.stringify(data),
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          success: function (result) {

              alert("회원가입이 완료되었습니다.");

              // TODO 마이페이지로 이동하게 수정 필요
              location.href = "/";

          },
          error: function (error) {
              alert(JSON.stringify(error));
          }
      });
    },


    /*login: function () {

        var data = {
            username: $("#user_name").val(),
            password: $("#password").val()
        };

        $.ajax({
            type: "POST",
            url: "/user/auth/login",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {
                alert("로그인이 완료되었습니다.");

                // TODO 마이페이지로 이동하게 수정 필요

                location.href = "/board";
            },
            error: function (error) {
                alert(JSON.stringify(error));
            }
        })

    },*/

};

index.init();