new Vue({
    el: '#getTrustedPeople',
    data: {
        children: [],
        trustedPeople: [],
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
                this.trustedPeople = this.children[0].trustedPeople;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getChild: function (child) {
            this.childId = child.id;
            this.child = getSpecificChild(this.children, this.childId);
            this.trustedPeople = this.child.trustedPeople;
        },
        addPerson: function () {
            var data = {
                name: $("#name").val(),
                surname: $("#surname").val(),
                civilId: $("#civilid").val(),
                phoneNumber: $("#phone").val()
            };
            this.$http.post(window.origin + "/tecza/rest/parent/addTrustedPerson/" + this.childId, data,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function () {
                    alert("Dodano osobę upoważnioną");
                },
                function (error) {
                    console.log(error);
                });
        }
    }
});

function getSpecificChild(childList, childId) {
    return childList.find(function (child) {
        return child.id === childId;
    });
}