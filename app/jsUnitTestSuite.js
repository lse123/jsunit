function jsUnitTestSuite() {
    this.isjsUnitTestSuite = true;
    this.testPages = Array();
    this.pageIndex = 0;

    for (var i = 0; i < arguments.length; i++) {
        if (arguments[i].testPages) {
            this.addTestSuite(arguments[i]);
        } else {
            this.addTestPage(arguments[i]);
        }
    }
}

jsUnitTestSuite.prototype.addTestPage = function (pageName) {
    this.testPages[this.testPages.length] = pageName;
}

jsUnitTestSuite.prototype.addTestSuite = function (suite) {
    for (var i = 0; i < suite.testPages.length; i++)
        this.addTestPage(suite.testPages[i]);
}

jsUnitTestSuite.prototype.containsTestPages = function () {
    return this.testPages.length > 0;
}

jsUnitTestSuite.prototype.nextPage = function () {
    return this.testPages[this.pageIndex++];
}

jsUnitTestSuite.prototype.hasMorePages = function () {
    return this.pageIndex < this.testPages.length;
}

jsUnitTestSuite.prototype.clone = function () {
    var clone = new jsUnitTestSuite();
    clone.testPages = this.testPages;
    return clone;
}
