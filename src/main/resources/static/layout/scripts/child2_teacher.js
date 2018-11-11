new Vue({
    el: '#getTrustedPeople',
    data: {
        children: [],
        trustedPeople: [],
        child: {},
        childId: '',
        hasTrustedPeople: false
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/teacher/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
                this.$http.get(window.origin + "/tecza/rest/teacher/getTrustedPerson/" + this.childId,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function (response) {
                        this.trustedPeople = response.body;
                        this.hasTrustedPeople = this.trustedPeople.length !== 0;
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
            this.$http.get(window.origin + "/tecza/rest/teacher/getTrustedPerson/" + this.childId,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.trustedPeople = response.body;
                    this.hasTrustedPeople = this.trustedPeople.length !== 0;
                },
                function (error) {
                    console.log(error);
                })
        }
    }
});
