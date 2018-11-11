new Vue({
    el: '#getBalance',
    data: {
        children: [],
        child: {},
        childId: '',
        balance: '',
        month: ''
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/parent/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
                this.$http.get(window.origin + "/tecza/rest/parent/getBalance/" + this.childId,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function (response) {
                        this.balance = response.body;
                        this.month = getMonthFromNumber(new Date().getMonth());
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
            this.$http.get(window.origin + "/tecza/rest/parent/getBalance/" + this.childId,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.balance = response.body;
                    this.month = getMonthFromNumber(new Date().getMonth());
                },
                function (error) {
                    console.log(error);
                })
        }
    }
});

function getMonthFromNumber(number) {
    switch (number) {
        case 0:
            return 'styczeń';
        case 1:
            return 'luty';
        case 2:
            return 'marzec';
        case 3:
            return 'kwiecień';
        case 4:
            return 'maj';
        case 5:
            return 'czerwiec';
        case 6:
            return 'lipiec';
        case 7:
            return 'sierpień';
        case 8:
            return 'wrzesień';
        case 9:
            return 'październik';
        case 10:
            return 'listopad';
        default:
            return 'grudzień';
    }
}