new Vue({
    el: '#getRemarks',
    data: {
        children: [],
        child: {},
        childId: '',
        remarks: []
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/parent/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
                this.$http.get(window.origin + "/tecza/rest/childinfo/getChildRemarksWithAuthorNameAndSurname/" + this.childId,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function (response) {
                        this.remarks = response.body.map(mapRemark);
                    },
                    function (error) {
                        console.log(error);
                    })
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getChild: function (child) {
            this.childId = child.id;
            this.child = getSpecificChildById(this.children, this.childId);
            this.$http.get(window.origin + "/tecza/rest/childinfo/getChildRemarksWithAuthorNameAndSurname/" + this.childId,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.remarks = response.body.map(mapRemark);
                },
                function (error) {
                    console.log(error);
                })
        }
    }
});

function mapRemark(value) {
    return {
        author: value.author,
        comment: value.comment,
        date: value.date,
        id: value.id,
        positive: value.positive === true ? 'pozytywna' : 'negatywna',
        subject: value.subject
    }
}