new Vue({
    el: '#comments',
    data: {
        children: [],
        child: {},
        childId: {},
        isChildrenChosen: false
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/teacher/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getSpecificChild: function (child) {
            this.childId = child.id;
            this.child = getSpecificChildById(this.children, this.childId);
            this.isChildrenChosen = true;
        },
        addRemark: function () {
            var data = {
                isPositive: !!$("#sel1").val().localeCompare("negatywna"),
                comment: $("#comment").val(),
                subject: $("#subject").val()
            };

            if (data.comment === '' || data.subject === '') {
                alert('Prosze uzupełnić pola oznaczone gwiazdką')
            } else {
                this.$http.post(window.origin + "/tecza/rest/teacher/addRemark/" + this.childId, data,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function () {
                        alert("Dodano uwagę");
                        window.location.href = window.origin + "/tecza/remarks_teacher";
                    },
                    function (error) {
                        console.log(error);
                    });
            }
        }
    }
});