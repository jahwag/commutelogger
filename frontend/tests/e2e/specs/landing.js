// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
  'verify that clicking login icon on landing page brings you to login': browser => {
    browser
      .init()
      .waitForElementVisible('#app')
      .click('a[id=login-btn]')
      .waitForElementVisible('#login')
      .end()
  },

}
