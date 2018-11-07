new Vue({
    el: '#comments',
    data: {
        children: [],
        child: {},
        childId: {}
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
        },
        addRemark: function () {
            var data = {
                isPositive: !!$("#sel1").val().localeCompare("negatywna"),
                comment: $("#comment").val(),
                subject: $("#subject").val()
            };
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
});