let index = {

    init: function () {
        $("#list-btn").on("click", () => {
            location.href = "/board/auth/list";
        });
        $("#modify-btn").on("click", () => {
            location.href = "/board/modify?id=" + $("#board-id").val();
        });
        $(".add-reply").on("click", () => {
            this.addReply();
        });
    },

    addReply : function() {

        var data = {
            username: $("#username").val(),
            boardId: $("#board-id").val(),
            content: $("#reply-input").val()
        };

        $.ajax({

            url: "/reply/add",
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            success: function (result) {
                alert("댓글이 등록되었습니다.");

                location.href = "/board/detail?id=" + data.boardId;
            },
            error: function (error) {
                console.log(JSON.stringify(error));
                alert("댓글이 정상적으로 등록되지않았습니다. 다시 시도해주세요.");
            }

        });

    }

};


index.init();