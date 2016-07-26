(function() {
    'use strict';

    angular
        .module('contactsJhipsterApp')
        .controller('ContactsDialogController', ContactsDialogController);

    ContactsDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contacts'];

    function ContactsDialogController ($scope, $stateParams, $uibModalInstance, entity, Contacts) {
        var vm = this;
        vm.contacts = entity;
        vm.load = function(id) {
            Contacts.get({id : id}, function(result) {
                vm.contacts = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('contactsJhipsterApp:contactsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.contacts.id !== null) {
                Contacts.update(vm.contacts, onSaveSuccess, onSaveError);
            } else {
                Contacts.save(vm.contacts, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.modifiedOn = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
