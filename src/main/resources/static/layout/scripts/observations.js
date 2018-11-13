new Vue({
    el: '#getEvaluation',
    data: {
        children: [],
        child: {},
        childId: '',
        firstProgressCategory: '',
        secondProgressCategory: '',
        thirdProgressCategory: '',
        firstProgressCategoryList: [],
        secondProgressCategoryList: [],
        thirdProgressCategoryList: [],
        hasEvaluation: false
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/parent/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
                this.child = this.children[0];
                this.childId = this.child.id;
                this.$http.get(window.origin + "/tecza/rest/childinfo/getProgrressEvaluation/" + this.childId,
                    {
                        headers: {'Authorization': $.cookie('token')}
                    }).then(function (response) {
                        this.firstProgressCategory = response.body[0].progressCategory;
                        this.secondProgressCategory = response.body[1].progressCategory;
                        this.thirdProgressCategory = response.body[2].progressCategory;
                        this.firstProgressCategoryList = response.body[0].taskGradeDtos;
                        this.secondProgressCategoryList = response.body[1].taskGradeDtos;
                        this.thirdProgressCategoryList = response.body[2].taskGradeDtos;
                        this.hasEvaluation = response.body[0].taskGradeDtos.length !== 0;
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
            this.$http.get(window.origin + "/tecza/rest/childinfo/getProgrressEvaluation/" + this.childId,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.firstProgressCategory = response.body[0].progressCategory;
                    this.secondProgressCategory = response.body[1].progressCategory;
                    this.thirdProgressCategory = response.body[2].progressCategory;
                    this.firstProgressCategoryList = response.body[0].taskGradeDtos;
                    this.secondProgressCategoryList = response.body[1].taskGradeDtos;
                    this.thirdProgressCategoryList = response.body[2].taskGradeDtos;
                    this.hasEvaluation = response.body[0].taskGradeDtos.length !== 0;
                },
                function (error) {
                    console.log(error);
                })
        }
    }
});