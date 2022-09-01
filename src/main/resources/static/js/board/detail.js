let index = {

    init: function () {
        $("#list-btn").on("click", () => {
            location.href = "/board/auth/list";
        });
    },

};

index.init();