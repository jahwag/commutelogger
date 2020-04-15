// Function for returning icons for transports
export function transportIcons (transport) {
  switch (transport) {
    case 'Walk':
      return 'mdi-walk'
    case 'Bicycle':
      return 'mdi-bike'
    case 'Motorcycle 125cc':
      return 'mdi-motorbike'
    case 'Motorcycle < 500cc':
      return 'mdi-motorbike'
    case 'Motorcycle > 500cc':
      return 'mdi-motorbike'
    case 'Car':
      return 'mdi-car'
    case 'Electric vehicle':
      return 'mdi-car-electric'
    case 'Bus':
      return 'mdi-bus'
    case 'Train or subway':
      return 'mdi-subway-variant'
    case 'Other':
      return 'help-circle'
    default:
      return ''
  }
}