'use strict'

angular.module('eleave.admin.services', []).factory('adminLeaveTypesService', function() {
    return {
        leaveTypes: [
            {
                id: 1,
                name: 'Name 1'
            },
            {
                id: 2,
                name: 'Name 2'
            }
        ],
        getAll: function() {
            return this.leaveTypes;
        },
        getLeaveTypeById: function(id) {
            return this.leaveTypes.find(function(element){
                return element.id === id;
            });
        }
    };
});

