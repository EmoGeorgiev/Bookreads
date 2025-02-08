import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../Auth/AuthContext'
import BookForm from './BookForm'
import bookService from '../../services/books'
import { defaultBookErrors } from '../../util/Errors'

const AddBook = () => {
    const [errors, setErrors] = useState(defaultBookErrors)
    const { user } = useAuth()
    const navigate = useNavigate()

    const addBook = async book => {
        try {
            await bookService.addBook(book)
            navigate(`/users/${user.id}/books`)
        } catch (error) {
            if (error.status === 400) {
                setErrors({ ...errors, ...error.response.data})
            } else if (error.status === 409) {
                setErrors({ ...errors, ...error.response.data, 'title': error.response.data.message})
            }
            setTimeout(() => {
                setErrors(defaultBookErrors)
            }, 3000)
        }
    }

    return (
        <div>
            <h1 className='m-10 text-4xl text-center font-semibold'>Add Book</h1>
            <BookForm book={{ 
                    'title': '', 
                    'author': '', 
                    'pageCount': '', 
                    'rating': '', 
                    'bookshelf': 'READ', 
                    'review': '', 
                    'dateRead': '' }} save={addBook} handleCancel={() => window.location.reload()} errors={errors} />
        </div>
    )
}

export default AddBook