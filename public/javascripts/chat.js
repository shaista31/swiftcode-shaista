var app = angular.module('chatApp', ['ngMaterial']);
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('purple')
        .accentPalette('red');
});

app.controller('chatController', function ($scope) {

    $scope.messages = [
        {
            'sender': 'USER',
            'text': 'Hello'
},
        {
            'sender': 'BOT',
            'text': 'What can i do for you?'
},
        {
            'sender': 'USER',
            'text': 'What is the date today?'
},
        {
            'sender': 'BOT',
            'text': 'Todays date is 14 - 04 - 18 '
}
];
});