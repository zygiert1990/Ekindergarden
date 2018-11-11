new Vue({
    el: '#absence',
    data: {
        children: [],
        child: {},
        childId: ''
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/parent/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getChildInfo: function (child) {
            this.childId = child.id;
        },
        addAbsence: function () {
            var data = [];

            var startDate = getDate($("#startAbsence").val());
            var endDate = getDate($("#endAbsence").val());
            var reason = $("#reason").val();
            if (reason === '') {
                reason = 'Nie podano';
            }

            if (startDate.isAfter(endDate)) {
                alert('Data początku nieobecności nie może późniejsza niż data końca');
            } else {
                data = [{absenceDate: startDate.format("YYYY-MM-DD"), reason: reason}];

                while (startDate.isBefore(endDate)) {
                    startDate = startDate.add(1, 'd');
                    data.push({absenceDate: startDate.format("YYYY-MM-DD"), reason: reason});
                }
                this.$http.post(window.origin + "/tecza/rest/childinfo/addAbsenceRecord/" + this.childId, data,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function (result) {
                        window.location.href = window.origin + "/tecza/absence";
                    },
                    function (error) {
                        console.log(error);
                    });
            }

        }
    }
});

function getDate(date) {
    var properDate = date.split('/');
    return moment('20' + properDate[2] + '-' + properDate[1] + '-' + properDate[0]);
}