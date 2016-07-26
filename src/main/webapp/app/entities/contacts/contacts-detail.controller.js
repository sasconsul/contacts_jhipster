(function() {
    'use strict';

    angular
        .module('contactsJhipsterApp')
        .controller('ContactsDetailController', ContactsDetailController);

    ContactsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Contacts'];

    function ContactsDetailController($scope, $rootScope, $stateParams, entity, Contacts) {
        var vm = this;
        vm.contacts = entity;
        vm.load = function (id) {
            Contacts.get({id: id}, function(result) {
                vm.contacts = result;
            });
        };
        var unsubscribe = $rootScope.$on('contactsJhipsterApp:contactsUpdate', function(event, result) {
            vm.contacts = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
