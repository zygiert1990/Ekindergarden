new Vue({
    el: '#getTrustedPeople',
    data: {
        children: [],
        trustedPeople: [],
        child: {},
        childId: '',
        hasTrustedPeople: false,
        trustedPersonId: ''
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/parent/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
                this.$http.get(window.origin + "/tecza/rest/parent/getTrustedPerson/" + this.childId,
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
            this.$http.get(window.origin + "/tecza/rest/parent/getTrustedPerson/" + this.childId,
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
                    window.location.href = window.origin + "/tecza/child2";
                },
                function (error) {
                    console.log(error);
                });
        },
        getId: function (id) {
            var tmpId = '';
            $('input[type="radio"]').each(function (element) {
                if ($(this).is(':checked')) {
                    tmpId = id;
                }
            });
            this.trustedPersonId = tmpId;
        },
        deletePerson: function () {
            if (this.trustedPersonId === '') {
                alert('Nie wybrano żadnej osoby');
            } else {
                this.$http.get(window.origin + "/tecza/rest/parent/deleteTrustedPerson/" + this.childId + "/" + this.trustedPersonId,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function () {
                        alert("Usunięto osobę upoważnioną");
                        window.location.href = window.origin + "/tecza/child2";
                    },
                    function (error) {
                        console.log(error);
                    });
            }
        }
    }
});
