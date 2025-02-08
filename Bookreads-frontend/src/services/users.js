import axiosInstance from './axiosInstance'

const baseUrl = '/users'

const getUser = async id => {
    const response = await axiosInstance.get(`${baseUrl}/${id}`)
    return response.data
}

const getUsers = async () => {
    const response = await axiosInstance.get(baseUrl)
    return response.data
}

const updateUser = async (id, user) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}`, user)
    return response.data
}

const updatePassword = async (id, updatePassword) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}/password`, updatePassword)
    return response.data
}

const deleteUser = async id => {
    const response = await axiosInstance.delete(`${baseUrl}/${id}`)
    return response.data
}

export default { getUser, getUsers, updateUser, updatePassword, deleteUser }