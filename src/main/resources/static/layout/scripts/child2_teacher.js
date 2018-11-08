new Vue({
    el: '#getTrustedPeople',
    data: {
        children: [],
        trustedPeople: [],
        child: {},
        childId: ''
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/teacher/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.trustedPeople = this.children[0].trustedPeople;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getChild: function (child) {
            this.childId = child.id;
            this.child = getSpecificChildById(this.children, this.childId);
            this.trustedPeople = this.child.trustedPeople;
        }
    }
});
