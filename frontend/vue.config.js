module.exports = {
  'transpileDependencies': [
    'vuetify'
  ],
  'devServer': {
    'port': 80,
    'https': false,
    'proxy': {
      '^/api': {
        target: 'http://localhost:8080',
        'ws': true,
        'changeOrigin': true
      },
    }
  },
  // 'pwa': {
  //   'name': 'Commute Logger',
  //   'short_name': 'commute-logger',
  //   'start_url': '.',
  //   'display': 'standalone',
  //   'themeColor': '#65B01E',
  //   'msTileColor': '#064182',
  //   'appleMobileWebAppCapable': 'yes',
  //   'appleMobileWebAppStatusBarStyle': 'black',
  //   'workboxOptions': {}
  // }
}