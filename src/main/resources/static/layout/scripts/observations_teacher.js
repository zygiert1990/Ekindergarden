new Vue({
    el: '#addEvaluation',
    data: {
        children: [],
        child: {},
        childId: {},
        data: [],
        isChildrenChosen: false
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/teacher/getAll",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.children = response.body;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getSpecificChild: function (child) {
            this.childId = child.id;
            this.child = getSpecificChildById(this.children, this.childId);
            this.isChildrenChosen = true;
        },
        addEvaluation: function () {

            var physics = [];
            var mental = [];
            var social = [];

            $('input[name^="physics"]:checked').each(function (i, selector) {
                physics[i] = selector.value;
            });
            $('input[name^="mental"]:checked').each(function (i, selector) {
                mental[i] = selector.value;
            });
            $('input[name^="social"]:checked').each(function (i, selector) {
                social[i] = selector.value;
            });

            var data = [
                {
                    progressCategory: "Fizyczny",
                    taskGradeDtos: [
                        {
                            task: 'Wykonuje czynnosci zwiazane z samoobsluga',
                            grade: physics[0]
                        },
                        {
                            task: 'Samodzielnie ubiera sie',
                            grade: physics[1]
                        },
                        {
                            task: 'Prawidlowo trzyma narzedzie do rysowania',
                            grade: physics[2]
                        },
                        {
                            task: 'Wycina nozyczkami po linii prostej',
                            grade: physics[3]
                        },
                        {
                            task: 'Wykonuje prace plastyczne',
                            grade: physics[4]
                        }
                    ]
                },
                {
                    progressCategory: "Mentalny",
                    taskGradeDtos: [
                        {
                            task: 'Opowiada co sie dzieje na obrazku',
                            grade: mental[0]
                        },
                        {
                            task: 'Wypowiada sie spontanicznie',
                            grade: mental[1]
                        },
                        {
                            task: 'Tworzy prace wlasne i wg wzoru',
                            grade: mental[2]
                        },
                        {
                            task: 'Rysuje postac ludzka',
                            grade: mental[3]
                        },
                        {
                            task: 'Skupia sie na zadaniu, doprowadza prace do konca',
                            grade: mental[4]
                        }
                    ]
                },
                {
                    progressCategory: "Moralno-spoleczny",
                    taskGradeDtos: [
                        {
                            task: 'Dba o wspolne zabawki i sprzety',
                            grade: social[0]
                        },
                        {
                            task: 'Wspoldziala w zespole',
                            grade: social[1]
                        },
                        {
                            task: 'Wie, w jakim mieszka miescie i na jakiej ulicy',
                            grade: social[2]
                        },
                        {
                            task: 'Wie, w jakim mieszka kraju i co jest jego stolica',
                            grade: social[3]
                        },
                        {
                            task: 'Zna godlo i barwy narodowe',
                            grade: social[4]
                        }
                    ]
                }
            ];


            this.$http.post(window.origin + "/tecza/rest/teacher/addEvaluation/" + this.childId, data,
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function () {
                    alert("Dodano kartę obserwacji");
                    window.location.href = window.origin + "/tecza/observations_teacher";
                },
                function (error) {
                    console.log(error);
                });
        }
    }
});