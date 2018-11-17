new Vue({
    el: '#getNews',
    data: {
        news: [],
        isNewsPresent: false
    },
    created: function () {
        this.$http.get(window.origin + "/tecza/rest/news/getAllAnnouncement",
            {
                headers: {'Authorization': $.cookie('token')}
            }).then(function (response) {
                this.news = response.body.map(mapNews);
                this.isNewsPresent = this.news.length > 0;
            },
            function (error) {
                console.log(error);
            })
    },
    methods: {
        getAllAnnouncement: function () {
            this.$http.get(window.origin + "/tecza/rest/news/getAllAnnouncement",
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.news = response.body.map(mapNews);
                    this.isNewsPresent = this.news.length > 0;
                },
                function (error) {
                    console.log(error);
                })
        },
        getAllAnnouncementForMaluchy: function () {
            this.$http.get(window.origin + "/tecza/rest/news/getAllAnnouncementForSpecificGroup/1",
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.news = response.body.map(mapNews);
                    this.isNewsPresent = this.news.length > 0;
                },
                function (error) {
                    console.log(error);
                })
        },
        getAllAnnouncementForSredniaki: function () {
            this.$http.get(window.origin + "/tecza/rest/news/getAllAnnouncementForSpecificGroup/2",
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.news = response.body.map(mapNews);
                    this.isNewsPresent = this.news.length > 0;
                },
                function (error) {
                    console.log(error);
                })
        },
        getAllAnnouncementForStarszaki: function () {
            this.$http.get(window.origin + "/tecza/rest/news/getAllAnnouncementForSpecificGroup/3",
                {
                    headers: {'Authorization': $.cookie('token')}
                }).then(function (response) {
                    this.news = response.body.map(mapNews);
                    this.isNewsPresent = this.news.length > 0;
                },
                function (error) {
                    console.log(error);
                })
        }
    }
});

function mapNews(value) {
    return {
        title: value.title,
        date: new Date(value.date).toLocaleDateString("pl-PL"),
        content: value.content,
        image: 'data:image/jpeg;base64,' + value.image,
        hasImage: value.image !== null
    }
}