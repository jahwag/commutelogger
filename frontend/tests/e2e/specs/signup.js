// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
  'verify that user can view signup': browser => {
    browser
      .init('https://localhost:8081/signup')
      .waitForElementVisible('#signup')
      .end()
  },
}
