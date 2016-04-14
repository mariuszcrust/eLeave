(function () {
    function RemoveConfirmationController($uibModalInstance, row, dialog) {
        var row = row;
        angular.extend(this, {
            dialog: dialog,
            yes: yes,
            no: no
        });

        function yes() {
            $uibModalInstance.close(row);
        }

        function no() {
            $uibModalInstance.dismiss('cancel');
        }
    }
    angular.module('eLeave.admin.controllers')
            .controller('RemoveConfirmationController', ['$uibModalInstance', 'row', 'dialog', RemoveConfirmationController]);
}());


