(function() {
    'use strict';

    angular
        .module('contactsJhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contacts', {
            parent: 'entity',
            url: '/contacts?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Contacts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacts/contacts.html',
                    controller: 'ContactsController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('contacts-detail', {
            parent: 'entity',
            url: '/contacts/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Contacts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacts/contacts-detail.html',
                    controller: 'ContactsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Contacts', function($stateParams, Contacts) {
                    return Contacts.get({id : $stateParams.id});
                }]
            }
        })
        .state('contacts.new', {
            parent: 'contacts',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacts/contacts-dialog.html',
                    controller: 'ContactsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                email: null,
                                profession: null,
                                employed: null,
                                createdOn: null,
                                modifiedOn: null,
                                deleted: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contacts', null, { reload: true });
                }, function() {
                    $state.go('contacts');
                });
            }]
        })
        .state('contacts.edit', {
            parent: 'contacts',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacts/contacts-dialog.html',
                    controller: 'ContactsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contacts', function(Contacts) {
                            return Contacts.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacts.delete', {
            parent: 'contacts',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacts/contacts-delete-dialog.html',
                    controller: 'ContactsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contacts', function(Contacts) {
                            return Contacts.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacts', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
