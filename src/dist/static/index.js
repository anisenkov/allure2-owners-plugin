'use strict';

allure.api.addTranslation('en', {
    tab: {
        owners: {
            name: 'Owners'
        }
    }
});

allure.api.addTranslation('ru', {
    tab: {
        owners: {
            name: 'Ответственные'
        }
    }
});

allure.api.addTab('owners', {
    title: 'tab.owners.name', icon: 'fa fa-trophy',
    route: 'owners(/)(:testGroup)(/)(:testResult)(/)(:testResultTab)(/)',
    onEnter: (function (testGroup, testResult, testResultTab) {
    var routeParams = Array.prototype.slice.call(arguments);
    return new allure.components.TreeLayout({
        routeParams: routeParams,
        tabName: 'tab.owners.name',
        baseUrl: 'owners',
        url: 'data/owners.json'
        });
    })
});