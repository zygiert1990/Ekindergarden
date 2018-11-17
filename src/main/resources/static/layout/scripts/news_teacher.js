new Vue({
    el: '#comments',
    methods: {
        addNews: function () {
            var childGroupIds = $("#childGroupList").val();

            var data = {
                title: $("#subject").val(),
                date: $("#date").val(),
                content: $("#comment").val(),
                image: {}
            };

            if (data.title === '' || data.date === '' || data.content === '') {
                alert('Prosimy o uzupełnienie tematu, daty i treści')
            } else {
                var imageFromHtml = $("#myFile").get(0).files[0];

                if (imageFromHtml) {

                    var reader = new FileReader();
                    reader.onload = function loaded(evt) {
                        data.image = arrayBufferToBase64(evt.target.result);
                    };

                    reader.readAsArrayBuffer(imageFromHtml);
                }

                this.$http.post(window.origin + "/tecza/rest/teacher/addNews/" + childGroupIds, data,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function () {
                        alert("Dodano aktualność");
                        window.location.href = window.origin + "/tecza/news_teacher";
                    },
                    function (error) {
                        console.log(error);
                    });
            }
        }
    }
});

function arrayBufferToBase64(arrayBuffer) {
    var bytes = new Uint8Array(arrayBuffer);
    var len = bytes.byteLength;
    var binary = '';
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
}