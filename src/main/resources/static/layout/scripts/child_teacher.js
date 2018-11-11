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