const URL = process.env.REACT_APP_URL
const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/
export default URL
export { passwordRegex}
