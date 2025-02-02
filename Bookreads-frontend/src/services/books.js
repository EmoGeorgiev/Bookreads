import axiosInstance from './axiosInstance'

const baseUrl = '/books'

const getBooksByUserId = async userId => {
    const response = await axiosInstance.get(`${baseUrl}/${userId}`)
    return response.data
}

const addBook = async book => {
    const response = await axiosInstance.post(baseUrl, book)
    return response.data
}

const updateBook = async (id, book) => {
    const response = await axiosInstance.put(`${baseUrl}/${id}`, book)
    return response.data
}

const deleteBook = async id => {
    const response = await axiosInstance.delete(`${baseUrl}/${id}`)
    return response.data
}

export default { getBooksByUserId, addBook, updateBook, deleteBook }