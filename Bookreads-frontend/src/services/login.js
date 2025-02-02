import axios from 'axios'

const baseUrl = 'http://localhost:8080/api/login'

const login = async credentials => {
    const headers = {
        headers : {
            'Authorization' : 'Basic ' + btoa(`${credentials.username}:${credentials.password}`)
        }
    }
    const response = await axios.post(baseUrl, null, headers)
    return response.data
}   

export default { login }