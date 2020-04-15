// Navigation controls
export default {
  bottom: {
    items: [
      {
        name: 'Trips',
        icon: 'mdi-train-car',
        destination: '/trips',
      },
      {
        name: 'Analytics',
        icon: 'mdi-chart-bar',
        destination: '/analytics',
      },
      {
        name: 'Competitions',
        icon: 'mdi-trophy-variant',
        destination: '/competitions',
      },
      {
        name: 'Carpooling',
        icon: 'mdi-car-2-plus',
        destination: '/carpooling',
      },
      {
        name: 'Admin',
        icon: 'mdi-database',
        destination: '/admin',
        admin: true,
      },
      {
        name: 'Account',
        icon: 'mdi-account',
        destination: '/account',
      },
    ]
  }
}