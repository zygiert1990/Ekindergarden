new Vue({
    el: '#childResult',
    data: {
        children: [],
        child: {},
        name: '',
        surname: '',
        pesel: '',
        birthday: '',
        sex: ''
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/teacher/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.name = this.children[0].name;
                this.surname = this.children[0].surname;
                this.pesel = this.children[0].pesel;
                this.birthday = getBirthDateFromPesel(this.pesel);
                this.sex = getSexFromPesel(this.pesel);
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getChildInfo: function (child) {
            this.name = child.name;
            this.surname = child.surname;
            this.pesel = child.pesel;
            this.birthday = getBirthDateFromPesel(child.pesel);
            this.sex = getSexFromPesel(child.pesel);
        }
    }
});

function getBirthDateFromPesel(pesel) {
    var year = parseInt(pesel.substring(0, 2), 10);
    var month = parseInt(pesel.substring(2, 4), 10) - 20;
    var day = parseInt(pesel.substring(4, 6), 10);
    return new Date("20" + year + "-" + month + "-" + day).toLocaleDateString("pl-PL");
}

function getSexFromPesel(pesel) {
    var sex = parseInt(pesel.substring(9, 10), 10);
    return sex % 2 === 1 ? "chłopiec" : "dziewczynka";
}