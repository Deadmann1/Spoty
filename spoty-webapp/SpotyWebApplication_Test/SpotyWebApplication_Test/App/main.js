(function () {
    'use strict';

    angular
        .module('app')
        .controller('Main', main);

    function main()
    {
        var user = this;
    }
})();


(function ()
{
    'use strict';

    angular
        .module('app2')
        .controller('LoginCtrl', LoginCtrl);

    LoginCtrl.$inject = ['$window', 'loginSrv', 'notify'];

    function LoginCtrl($window, loginSrv, notify)
    {
        /* jshint validthis:true */
        var vm = this;
        vm.validateUser = function ()
        {
            loginSrv.validateLogin(vm.username, vm.password).then(function (data)
            {
                if (data.isValidUser) 
                {
                    $window.location.href = '/index2.html';
                }
                else
                {
                    alert('Login incorrect');
                }
            });
        }
    }
})();