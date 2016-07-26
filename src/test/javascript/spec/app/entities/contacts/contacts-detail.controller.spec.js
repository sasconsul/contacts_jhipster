'use strict';

describe('Controller Tests', function() {

    describe('Contacts Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContacts;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContacts = jasmine.createSpy('MockContacts');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Contacts': MockContacts
            };
            createController = function() {
                $injector.get('$controller')("ContactsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'contactsJhipsterApp:contactsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
