angular.module('registration-service', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http) {

    $scope.newRegistration = function () {
        $http.post('http://localhost:8188/registration-service/registration',$scope.registrationUserDto)
            .then(function (response) {
                $scope.registrationUserDto = null;
                alert('Login: ' + response.data.login);
            }, function errorCallback(response) {
                alert('Error');
            });
    }

    $scope.changePassword = function () {
        $http.put('http://localhost:8188/registration-service/registration',$scope.updateUserPasswordDto)
            .then(function (response) {
                $scope.updateUserPasswordDto = null;
                alert("ОК");
            }, function errorCallback(response) {
                alert('Error');
            });
    }

    $scope.deleteUser = function () {
        $http.post('http://localhost:8188/registration-service/registration/delete',$scope.deleteUserDto)
            .then(function (response) {
                $scope.deleteUserDto = null;
                alert("ОК");
            }, function errorCallback(response) {
                alert('Error');
            });
    }

});