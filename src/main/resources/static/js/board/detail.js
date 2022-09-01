let index = {

    init: function () {
        $("#list-btn").on("click", () => {
            location.href = "/board/auth/list";
        });
        $("#modify-btn").on("click", () => {
            location.href = "/board/modify?id=" + $("#board-id").val();
        });
    },

};

index.init();