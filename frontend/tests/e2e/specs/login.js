// For authoring Nightwatch tests, see
// https://nightwatchjs.org/guide

module.exports = {
  'verify that email, password, login, signup are shown': browser => {
    browser
      .init('https://localhost:8081/')
      .waitForElementVisible('#login')
      .waitForElementVisible('#email')
      .waitForElementVisible('#password')
      .waitForElementVisible('#submit')
      .waitForElementVisible('#signup')
      .end()
  },
  'verify that clicking sign up goes to new page': browser => {
    browser
      .init('https://localhost:8081/')
      .click('a[id=signup]')
      .waitForElementVisible('#signup')
      .end()
  },
  'verify that user cannot login with invalid credentials': browser => {
    browser
      .init('https://localhost:8081/')
      .click('button[id=submit]')
      .waitForElementVisible('#rejected')
      .end()
  },
}
