
export function formatDateYYMMDDHHMM(date) {
  var yyyy = date.getFullYear()
  var mm = date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) // getMonth() is zero-based
  var dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  var hh = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
  var min = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  return ''.concat(yyyy).concat('-').concat(mm).concat('-').concat(dd).concat(' ')
    .concat(hh).concat(':').concat(min)
}

export function formatDateYYMMDDHHMMSS(date) {
  var yyyy = date.getFullYear()
  var mm = date.getMonth() < 9 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1) // getMonth() is zero-based
  var dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  var hh = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
  var min = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  var seconds = date.getSeconds < 10 ? '0' + date.getSeconds() : date.getSeconds()
  return ''.concat(yyyy).concat('-').concat(mm).concat('-').concat(dd).concat(' ')
    .concat(hh).concat(':').concat(min).concat(':').concat(seconds)
}

