const Editor = toastui.Editor;

const editor = new Editor({
    el: document.querySelector('.editor'),
    height: '600px',
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    initialValue: $('#content').val()
});

let index = {

    init: function () {
        $("#write-btn").on("click", () => {
            this.write();
        });
    },

    write: function () {

        var data = {
            id: $("#board-id").val(),
            title: $("#title").val(),
            content: editor.getHTML()
        };

        console.log(data.id);


      $.ajax({
          type: "POST",
          url: "/board/modify",
          data: JSON.stringify(data),
          contentType: "application/json; charset=utf-8",
          dataType: "json",
          success: function (result) {

              alert("글 수정이 완료되었습니다.");

              location.href = "/";

          },
          error: function (error) {
              alert(JSON.stringify(error));
          }
      });
    },

};

index.init();