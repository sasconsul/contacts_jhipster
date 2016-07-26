(function() {
    'use strict';
    angular
        .module('contactsJhipsterApp')
        .factory('Contacts', Contacts);

    Contacts.$inject = ['$resource', 'DateUtils'];

    function Contacts ($resource, DateUtils) {
        var resourceUrl =  'api/contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    data.modifiedOn = DateUtils.convertDateTimeFromServer(data.modifiedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
